package manager;

/**
 * @author zebzeev-sv
 * @version 23.11.2019 17:18
 */
public enum EConParams {

	fileMask("PATTERN", "маска файла"),
	mailHost("MAIL_ADDRESS", "Адрес почтового сервера"),
	mailLogin("MAIL_LOGIN", "Логин для подключения к mail-серверу"),
	mailPassword("MAIL_PASS", "Пароль для подключения к mail-серверу"),
	mailPort("MAIL_PORT", "Порт для подключения к почтовому серверу");

	public String key;
	public String info;

	EConParams(String code, String info) {
		this.key = code;
		this.info = info;
	}
}
