package manager;

/**
 * Перечисление поддерживаемых почтовых протоколов
 *
 * @author zebzeev-sv
 * @version 30.08.2019 22:50
 */
public enum EMailProtocol {

	POP("pop3"),
	POP3S("pop3s"),
	IMAP("imap"),
	IMAPS("imaps");

	public final String value;

	EMailProtocol(String value) {
		this.value = value;
	}
}
