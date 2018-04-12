package Util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;

public class SendMail {

	private String smtpHost = "localhost";

	public SendMail(String sujet, String mail, String output) throws MessagingException {
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", "infinity.pidev@gmail.com");
		props.put("mail.smtp.password", "infinity2018");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.ssl.trust", "smtp.esprit.tn");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		//
	    /*
	    Properties properties = System.getProperties();
	    properties.setProperty("mail.smtp.host", smtpHost);*/
	    //
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress("infinity.pidev@gmail.com");

		InternetAddress addressTo = new InternetAddress();

		addressTo = new InternetAddress(mail);

		msg.setRecipient(Message.RecipientType.TO, addressTo);
		// Setting the Subject and Content Type
		msg.setFrom(addressFrom);

		msg.setSubject(sujet);
		msg.setText(output);
		Transport t = session.getTransport("smtp");
		t.connect("smtp.gmail.com","infinity.pidev@gmail.com","infinity2018");
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
		System.out.println("sent");
	}

	public SendMail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	public void messageMail()
	{
		System.out.println("sending");
	}

}
