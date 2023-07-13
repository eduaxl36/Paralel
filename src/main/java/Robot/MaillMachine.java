package Robot;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;

public class MaillMachine {

    private final String Tipo;
    private final String CaminhoFlag;

    public MaillMachine(String Tipo, String CaminhoFlag) {
        this.Tipo = Tipo;
        this.CaminhoFlag = CaminhoFlag;
    }

    public void enviarEmail() throws Exception {

        final String username = "eduardo.fernando@kantaribopemedia.com";
        final String password = "Mortadela@7";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "10.11.3.8");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("reginalTeste@kantaribopemedia.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("eduardo.fernando@kantaribopemedia.com"));

            // Adicionar destinatários de cópia
            message.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(new HandleTo().getEnderecos()));

            message.setSubject("Panama | Cambios en " + Tipo);

            // Criar o corpo da mensagem
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(FileUtils.readFileToString(new File(CaminhoFlag)));

            // Criar o anexo
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeMultipart multipart = new MimeMultipart();

            if (FileUtils.readFileToString(new File(CaminhoFlag)).toLowerCase().contains("hechos")) {

                String filePath = new HandleAnexos(Tipo).getAnexo();
                DataSource source = new FileDataSource(filePath);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(source.getName());
                 multipart.addBodyPart(attachmentPart);

            }

            // Criar a parte da mensagem que contém o corpo e o anexo
           
            multipart.addBodyPart(messageBodyPart);
           
            // Definir o conteúdo da mensagem como a parte multipart
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email enviado com sucesso!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
