package Mail;

import java.io.File;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class JavaMailApp1 {

    
    public static String identificarAutor(){
    
    
        return "";
    
    
    }
    
    
    public static void criarMsgEnviarEmail(File Arq){
    
    
   
    
    
    
    }
    
    
    
    public static void main(String[] args) throws Exception {

        final String username = "eduardo.fernando@kantaribopemedia.com";
        final String password = "Mortadela@7";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "10.11.3.8");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("reginalTeste@kantaribopemedia.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("eduardo.fernando@kantaribopemedia.com"));
            
            // Adicionar destinatários de cópia
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("reinaldo.monteiro@kantaribopemedia.com, gisleine.fogli@kantaribopemedia.com"));
            
//            // Adicionar destinatários de cópia oculta
//            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("teste3@example.com, teste4@example.com"));
//            
            message.setSubject("Test");

            // Criar o corpo da mensagem
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Olá,\n\nteste anexo.");

            // Criar o anexo
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String filePath = "C:\\teste\\arquivo.txt"; // Insira o caminho do arquivo que você deseja anexar
            DataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(source.getName());

            // Criar a parte da mensagem que contém o corpo e o anexo
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Definir o conteúdo da mensagem como a parte multipart
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
