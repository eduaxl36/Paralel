package Mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailApp {

    
    public static void enviarEmail() throws Exception{
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
            message.setFrom(new InternetAddress("regional.latam@kantaribopemedia.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("gisleine.fogli@kantaribopemedia.com"));
            message.setSubject("Test");
            message.setText("isso seria o oficial");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
    
    
    
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
            message.setFrom(new InternetAddress("regional.latam@kantaribopemedia.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("gisleine.fogli@kantaribopemedia.com"));
            message.setSubject("Test");
            message.setText("isso seria o oficial");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
