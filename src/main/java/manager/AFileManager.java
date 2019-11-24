package manager;

import org.apache.log4j.Logger;

import java.util.regex.Pattern;

/**
 * @author zebzeev-sv
 * @version 30.08.2019 20:44
 */
abstract class AFileManager{

	private final String fileMask;

	final Logger logger = Logger.getLogger(this.getClass());

	AFileManager(String fileMask)
	{
		this.fileMask = fileMask;
	}


	public abstract void process();

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
