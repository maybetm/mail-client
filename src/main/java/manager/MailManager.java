package manager;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SubjectTerm;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Класс для выгрузки файлов для сверки с почты
 * и последующего сохранения.
 *
 * @author zebzeev-sv
 * @version 30.08.2019 20:43
 */
public final class MailManager extends AFileManager<Object> {

	private final String host;
	private final String login;
	private final String password;
	private final EMailProtocol protocol;

	public MailManager(String fileMask, String host, String login, String password, EMailProtocol protocol) {
		super(fileMask);
		this.host = host;
		this.login = login;
		this.password = password;
		this.protocol = protocol;
	}

	@Override
	public void process() {
		// задаём параметры подключения, возможно от этого можно отказаться
		final Properties properties = getProperties();
		try {
			// устанавливаем параметры соединения
			Session session = Session.getInstance(properties);
			session.setDebug(false);
			// устанавлифаем соединение
			Store store = session.getStore(protocol.value);
			store.connect(host, login, password);
			// переходим в папку входящих сообщений
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			// выполняем поиск письма по теме, в папке "входящие"
			Message[] messages = searchMessageBySubject(inbox);

			// тут выполняется парсинг писем
			for (Message message : messages) {
				if (message.isMimeType("multipart/*") && isOneAttachment(message)) {
					final InputStream inputStream = getArchiveInputStream(message);
					System.out.println("Одно вложение");
					// тут должна происходить вся общая херня
				} else {
					System.out.println("fatalError");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Принимает на вход сообщение с вложением
	 * и возвращает архив в открытом потоке.
	 *
	 * @param message - сообщение найденное по теме.
	 * @return - возвращает архив с реестром платежей в архиве.
	 */
	private InputStream getArchiveInputStream(final Message message) throws IOException, MessagingException {
		final MimeMultipart mimeMultipart = getMultipart(message);
		InputStream inputStream = null;
		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.getFileName() != null && checkFileByMask(bodyPart.getFileName())) {
				inputStream = bodyPart.getInputStream();
			}
		}
		return inputStream;
	}

	/**
	 * Метод должен сверить имена файлов вложенных в письмо
	 * по пользовательскому шаблону.
	 *
	 * @param message - объект письма
	 * @return Вернём истину, если найден только один файл по пользовательской маске.
	 */
	private boolean isOneAttachment(final Message message) throws MessagingException, IOException {
		final MimeMultipart mimeMultipart = getMultipart(message);
		// количество вложений удавлетворяющих пользовательской маске
		int countAttachments = 0;
		// перемещаемся в цикле по телу контекта в письме
		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.getFileName() != null && checkFileByMask(bodyPart.getFileName())) {
				++countAttachments;
			}
		}
		return countAttachments == 1;
	}

	private MimeMultipart getMultipart(final Message message) throws IOException, MessagingException {
		return (MimeMultipart) message.getContent();
	}

	/**
	 * Используется для поиска писем по их теме
	 *
	 * @param folder - директория для поиска писем
	 * @return возвращает массив писем
	 */
	private Message[] searchMessageBySubject(final Folder folder) {
		final String subject = "";
		Message[] messages = null;
		try {
			messages = folder.search(new SubjectTerm(subject));
		} catch (MessagingException e) {
			logger.info("Ошибка поиска поиска реестров; " + e);
		}
		return messages;
	}

	/**
	 * В зависимости от почтового протокола
	 * могут менять и параметры подключения к почтовому серверу
	 *
	 * @return возвращает модифицированные параметры подключения
	 */
	private Properties getProperties() {
		Properties properties = System.getProperties();
		properties.setProperty("mail." + protocol.value + ".port", "993");
		properties.setProperty("mail." + protocol.value + ".connectiontimeout", "5000");
		properties.setProperty("mail." + protocol.value + ".timeout", "5000");
		return properties;
	}
}
