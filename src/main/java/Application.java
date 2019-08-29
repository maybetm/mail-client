import javax.mail.*;
import java.util.Properties;

/**
 * @author zebzeev-sv
 * @version 29.08.2019 21:44
 */
public class Application {

	final static String host = "mail.dartit.ru";
	final static String login = "";
	final static String password = "&";


	public static void main(String[] args) {
		connectMail();
	}

	private static void connectMail() {

		Properties properties = System.getProperties();
		properties.setProperty("mail.imaps.port", "993");
		properties.setProperty("mail.imaps.connectiontimeout", "5000");
		properties.setProperty("mail.imaps.timeout", "5000");

		try {
			Session session = Session.getInstance(properties);
			session.setDebug(true);
			Store store = session.getStore("imaps");
			store.connect(host, login, password);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			System.out.println("Количество сообщений : " + inbox.getMessageCount());

			for (Message message : inbox.getMessages()) {
				System.out.println("Message: " + message.getFrom()[0].getType());
				Address address = message.getFrom()[0];
				System.out.println("From: " + message.getSubject());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
