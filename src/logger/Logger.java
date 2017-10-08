package logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

/**
 * Simple Logger (Console and File Output)
 * @author Robert
 *
 */
public class Logger
{
	private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.YY - HH:mm:ss");
	private static LogLevel level;
	private static File folder;
	private static PrintStream outStream;
	private static PrintStream errorStream;
	private static FileOutputMode fileOutputMode;
	
	
	public static LogLevel getLevel()
	{
		return level;
	}
	
	public static File getFolder()
	{
		return folder;
	}
	
	public static void setLevel(LogLevel newLevel)
	{
		level = newLevel;
		System.out.println("Logger running in LogLevel: " + level);
	}
	
	public static void setLevel(String newLevel)
	{
		try
		{			
			setLevel(LogLevel.valueOf(newLevel.toUpperCase()));
		}
		catch(Exception e)
		{			
			setLevel(LogLevel.NORMAL);
			error(newLevel + " is no valid LogLevel - set LogLevel to NORMAL");
		}		
	}	

	public static void appInfo(String appName, String versionName, String versionCode, String versionDate)
	{
		log(LogLevel.INFO, appName + " - v" + versionName + " - (versioncode: " + versionCode + ") from " + versionDate + ")");
	}
	
	public static void info(Object message)
	{
		log(LogLevel.INFO, message.toString());
	}
	
	public static void debug(Object message)
	{
		log(LogLevel.DEBUG, message.toString());
	}
	
	public static void warning(Object message)
	{
		log(LogLevel.WARNING, message.toString());
	}
	
	public static void error(Object message)
	{
		log(LogLevel.ERROR, message.toString());
	}
	
	public static void error(Exception exception)
	{
		log(LogLevel.ERROR, getStringFromException(exception));
	}
		
	private static void log(LogLevel logLevel, String message)
	{		
		if(level == null)
		{
			level = LogLevel.OFF;
		}
		String logMessage = createLogMessage(logLevel, message);
		
		if(level.getLevel() >= logLevel.getLevel())	
		{
			if(logLevel.equals(LogLevel.ERROR))
			{		
				logErrorToConsole(logMessage);				
			}
			else
			{
				logToConsole(logMessage);
			}
		}
	}
	
	private static String createLogMessage(LogLevel logLevel, String message)
	{
		StackTraceElement element = Thread.currentThread().getStackTrace()[4];
		
		return "[" + logLevel + "] - " + format.format(System.currentTimeMillis()) + " - " + element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber() + "  -  " + message;
	}
	
	private static void logToConsole(String logMessage)
	{
		System.out.println(logMessage);
	}
	
	private static void logErrorToConsole(String logMessage)
	{
		System.err.println(logMessage);
	}
	
	public static void enableFileOutput(File folder, PrintStream outStream, PrintStream errorStream, FileOutputMode fileOutputMode)
	{
		Logger.folder = folder;	
		Logger.outStream = outStream;
		Logger.errorStream = errorStream;
		Logger.fileOutputMode = fileOutputMode;
		
		File outLog = new File(folder.getAbsolutePath() , "out.log");
		File errorLog = new File(folder.getAbsolutePath() , "error.log");
		
		if(fileOutputMode == FileOutputMode.COMBINED)
		{
			outLog = errorLog;
		}
		
		try
		{
			System.setOut(new ConsoleFileStream(outLog, System.out));
			System.setErr(new ConsoleFileStream(errorLog, System.err));	
			
			if(fileOutputMode == FileOutputMode.COMBINED)
			{
				info("File output enabled (" + errorLog.getAbsolutePath() + ")");
			}
			else
			{
				info("File output enabled (" + outLog.getAbsolutePath() + "\n" + errorLog.getAbsolutePath() + ")");
			}			
		}
		catch(FileNotFoundException e)
		{
			disableFileOutput();
			error("Can't log to folder " + folder + "(" + e.getMessage() + ") FILEOUTPUT NOW DISABLED!");
		}		
	}	

	public static void disableFileOutput()
	{		
		System.setOut(outStream);
		System.setErr(errorStream);		
	}
	
	public static void deleteLogfile()
	{		
		try
		{
			File outLog = new File(folder.getAbsolutePath() , "/out.log");
			Files.deleteIfExists(outLog.toPath());
			File errorLog = new File(folder.getAbsolutePath() , "/error.log");
			Files.deleteIfExists(errorLog.toPath());
		}
		catch(IOException e)
		{
			error("Can't delete log file(s)");
			e.printStackTrace();
		}	
	}
	
	public static void clearLogFile()
	{		
		disableFileOutput();
		try
		{
			File outLog = new File(folder.getAbsolutePath() , "/out.log");
			if(outLog.exists())
			{
				Files.write(outLog.toPath(), "".getBytes());
			}
			File errorLog = new File(folder.getAbsolutePath() , "/error.log");
			if(errorLog.exists())
			{
				Files.write(errorLog.toPath(), "".getBytes());
			}
		}
		catch(IOException e)
		{
			error("Can't clear log file(s)");
			e.printStackTrace();
		}	
		enableFileOutput(folder, outStream, errorStream, fileOutputMode);		
	}
	
	private static String getStringFromException(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}