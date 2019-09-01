package manager;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author zebzeev-sv
 * @version 30.08.2019 20:44
 */
abstract class AFileManager<E>{

	/**
	 * Пользовательская маска для архива реестров
	 */
	private final String fileMask;

	final Logger logger = Logger.getLogger(this.getClass());

	AFileManager(String fileMask)
	{
		this.fileMask = fileMask;
	}

	/**
	 * Выполнение сверки реестров платежей
	 */
	public abstract void process();

	/**
	 * Сохранение архифа с платежами после сверки в еспп
	 */
	protected void save()
	{

	}


	/**
	 * Извлечение реестров из архива
	 *
	 * @param isArchive - архив с реестрами платежей
	 * @return Возвращает мапу из имени файла и его содержимое
	 */
	protected List<E> extractFile(final InputStream isArchive)
	{
		final ZipInputStream zis = new ZipInputStream(isArchive);
		final List<E> filesXls = new ArrayList<>();

		ZipEntry zipEntry;
		try {
			while ((zipEntry = zis.getNextEntry()) != null) {
				filesXls.add(null);
			}
		} catch (IOException ex) {
			logger.info("Не удалось прочитать архив; " + ex);
		}

		return filesXls;
	}

	private static InputStream convertToInputStream(final ZipInputStream inputStreamIn) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(inputStreamIn, out);
		return new ByteArrayInputStream(out.toByteArray());
	}

	/**
	 * Используется для поиска архивов с рееестром платежей удовлетворяющих маске,
	 * заданной пользователем
	 *
	 * @param fileName - имя архива с реестрами
	 * @return - вернём истину, если файл соответствует пользовательской маске.
	 */
	protected boolean checkFileByMask (final String fileName) {

		final StringBuilder regEx = new StringBuilder();
		final char[] charArrayFileMask = fileMask.toCharArray();

		for (char aCharArrayFileMask : charArrayFileMask) {
			if (aCharArrayFileMask == '*') {
				regEx.append("\\w");
			} else {
				regEx.append(aCharArrayFileMask);
			}
		}

		return Pattern.matches(regEx.toString(), fileName);
	}
}
