package edu.mit.cor.f3.util;

/**
 * Created by cor on 9/17/16.
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

//import com.libmailcore.*;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;


public class Mail
{
    public static void main(String[] args) {
        read();
    }


    public static Food[] read() {
        Properties props = new Properties();
        try {
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", "smtp.gmail.com");
            mailProps.put("mail.smtp.socketFactory.port", 465);
            mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            mailProps.put("mail.smtp.auth", true);
            mailProps.put("mail.smtp.port",465);
            Session session = Session.getDefaultInstance(mailProps, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "mitfindfreefood@gmail.com","sherman02139");

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            Food[] foods = new Food[messages.length];

            for (int q=0; q<messages.length; q++) {
                Message message = messages[q];
                //this is a sketchy and gross line but seems to extract the body
                String raw = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();

                //strip some useless data from the email body (usually useless)
                String text = (raw.replaceAll("To:.*\r\n", "").replaceAll("From:.*\r\n", "").replaceAll("Date:.*\r\n","").replaceAll("Subject:.*\r\n",""));

                //figure out how long it's been (in ms) since the message was sent out
                long timeDelta = ((new java.util.Date()).getTime() - message.getSentDate().getTime());

                //todo: pass this text variable to the parser
                //if(timeDelta < 36000000) {
                    System.out.println( message.getSubject() + "\n" + text);
                    Parser p = new Parser( message.getSubject() + "\n" + text);
                    //System.out.println("Location: " + p.getLocation());
                    double[] coord = Find.getCoordinates(p.isNum(), p.getLocation());
                    if(coord != null)
                        System.out.println(coord[0] + " " + coord[1]);
                    else
                        System.out.println("you botched it");
                //}
                foods[q] = new Food(message.getSubject(), text, coord, timeDelta);
                System.out.println("***************************************************");
            }
            inbox.close(true);
            store.close();
            return foods;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
