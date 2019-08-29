import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

/**
 * @author zebzeev-sv
 * @version 29.08.2019 21:44
 */
public class Application {

	final static String host = "imap.gmail.com";
	final static String login = "sergey.zebzeev@gmail.com";
	final static String password = "iltdpodznfmzwkye";


	public static void main(String[] args) {
		connectMail();
	}

	private static void connectMail () {

		Properties properties = System.getProperties();
		properties.setProperty("mail.imaps.port", "993");
		properties.put("mail.imap.ssl.enable", "true");
		properties.setProperty("mail.imaps.connectiontimeout", "5000");
		properties.setProperty("mail.imaps.timeout", "5000");

		try{
			Session session = Session.getInstance(properties);
			session.setDebug(false);
			Store store = session.getStore("imaps");
			store.connect(host, login, password);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			System.out.println("Количество сообщений : " +inbox.getMessageCount());
		}catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
