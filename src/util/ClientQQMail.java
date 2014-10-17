package util;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * Created by peter on 4/9/
 */
public class ClientQQMail {

    static int port = 25;

    static String server = "smtp.qq.com";

    static String from = "116352437@qq.com";

    static String user = "116352437@qq.com";

    static String password = "sWAM9fre2008";


    public static void main(String[] args)


    {
        sendEmail(new String[]{"116352437@qq.com"}, "测试QQ邮箱群发功能", "来自微锤子");

    }


    public static void sendEmail(String[] email, String subject, String body) {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = new Properties();
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.put("mail.smtp.host", server);
            //props.put("mail.smtp.port", String.valueOf(port));
            props.put("mail.smtp.auth", "true");//验证
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", false);
            Transport transport = null;
            MyAuthenticator myauth = new MyAuthenticator(from, password);
            Session session = Session.getDefaultInstance(props, myauth);
            session.setDebug(true);
            transport = session.getTransport("smtp");
            transport.connect(server, user, password);
            MimeMessage msg = new MimeMessage(session);
            msg.setSentDate(new Date());
            InternetAddress fromAddress = new InternetAddress(from);
            msg.setFrom(fromAddress);
            InternetAddress[] toAddress = new InternetAddress[email.length];
            for (int i = 0; i < email.length; i++) {
                toAddress[i] = new InternetAddress(email[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject);
            msg.setText(body);
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


class MyAuthenticator extends javax.mail.Authenticator {

    private String strUser;

    private String strPwd;


    public MyAuthenticator(String user, String password) {
        this.strUser = user;
        this.strPwd = password;

    }


    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(strUser, strPwd);

    }

}
