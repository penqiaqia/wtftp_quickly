package com.ftp.qanlong.ftpfactory;

import java.util.Locale;

import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.ParserInitializationException;

public class FTPFactoryOverWriter extends DefaultFTPFileEntryParserFactory {
	@Override
	public FTPFileEntryParser createFileEntryParser(String key) {
		if (key == null) {
			throw new ParserInitializationException("Parser key cannot be null");
		}
		Class parserClass = null;
		FTPFileEntryParser parser = null;
		try {
			parserClass = Class.forName(key);
			parser = (FTPFileEntryParser) parserClass.newInstance();
		} catch (ClassNotFoundException e) {
			try {
				String ukey = null;
				if (null != key) {
					ukey = key.toUpperCase(Locale.ENGLISH);
				}
				System.out.println("--------------------the ukey is :" + ukey);
				if ((ukey.indexOf("UNIX") >= 0) || (ukey.indexOf("TYPE: L8") >= 0)) {
					System.out.println("the ukey is :" + ukey);
					parser = createUnixFTPEntryParser();
				} else if (ukey.indexOf("VMS") >= 0) {
					parser = createVMSVersioningFTPEntryParser();
				} else if (ukey.indexOf("WINDOWS") >= 0 || ukey.indexOf("MSDOS") >= 0
						|| (ukey.indexOf("FTP SERVER, HUAWEI CO") >= 0) || ukey.indexOf("HELLO") >= 0) {
					parser = createNTFTPEntryParser();
				} else if (ukey.indexOf("OS/2") >= 0) {
					parser = createOS2FTPEntryParser();
				} else if ((ukey.indexOf("OS/400") >= 0) || (ukey.indexOf("AS/400") >= 0)) {
					parser = createOS400FTPEntryParser();
				} else if (ukey.indexOf("MVS") >= 0) {
					parser = createMVSEntryParser();
				} else if (ukey.indexOf("NETWARE") >= 0) {
					parser = createNetwareFTPEntryParser();
				} else {
					throw new ParserInitializationException("Unknown parser type: " + key);
				}
			} catch (NoClassDefFoundError nf) {
				throw new ParserInitializationException("Error initializing parser", nf);
			}

		} catch (NoClassDefFoundError e) {
			throw new ParserInitializationException("Error initializing parser", e);
		} catch (ClassCastException e) {
			throw new ParserInitializationException(parserClass.getName() + " does not implement the interface "
					+ "org.apache.commons.net.ftp.FTPFileEntryParser.", e);
		} catch (Throwable e) {
			throw new ParserInitializationException("Error initializing parser", e);
		}

		return parser;
	}
}
