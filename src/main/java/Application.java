import manager.EConParams;
import manager.EMailProtocol;
import manager.MailManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zebzeev-sv
 * @version 29.08.2019 21:44
 */
public class Application {

	private static final Map<String, String> params = new HashMap<String, String>()
	{{
		put(EConParams.mailHost.key, "pop.gmail.com");
		put(EConParams.mailPort.key, "995");
		put(EConParams.mailLogin.key, "sergey.zebzeev@gmail.com");
		put(EConParams.mailPassword.key, "");
		put(EConParams.fileMask.key, "**********_**********_**_***");
	}};

	public static void main(String[] args) {
		new MailManager(params, EMailProtocol.POP3S).process();
	}

}
