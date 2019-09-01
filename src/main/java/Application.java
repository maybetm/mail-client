import manager.EMailProtocol;
import manager.MailManager;

/**
 * @author zebzeev-sv
 * @version 29.08.2019 21:44
 */
public class Application {

	final static String host = "";
	final static String login = "zebzeev-sv";
	final static String password = "";
	final static EMailProtocol protocol = EMailProtocol.IMAPS;
	final static String fileMask = "**_***.zip";

	public static void main(String[] args) {
		new MailManager(fileMask, host, login, password, protocol).process();
	}

}
