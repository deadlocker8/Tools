package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import tools.PathUtils;

/**
 * Simple Logger (Console and File Output)
 * @author Robert
 *
 */
public class Logger
{
	static SimpleDateFormat format = new SimpleDateFormat("dd.MM.YY - HH:mm:ss");
	public static LogLevel level; 
	public static boolean fileOutput;
	public static File savePath;
	
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
		
		switch(logLevel)
		{
			case INFO:		if(level.equals(LogLevel.ALL) || level.equals(LogLevel.INFO) || level.equals(LogLevel.NORMAL))	
							{
								logToConsole(logMessage);
								if(fileOutput)
								{
									logToFile(logMessage);
								}
							}
							break;
			case DEBUG:		if(level.equals(LogLevel.ALL) || level.equals(LogLevel.DEBUG))	
							{
								logToConsole(logMessage);
								if(fileOutput)
								{
									logToFile(logMessage);
								}
							}
							break;		
			case WARNING:	if(level.equals(LogLevel.ALL) || level.equals(LogLevel.WARNING) || level.equals(LogLevel.NORMAL))	
							{
								logToConsole(logMessage);
								if(fileOutput)
								{
									logToFile(logMessage);
								}
							}
							break;		
			case ERROR:		if(level.equals(LogLevel.ALL) || level.equals(LogLevel.ERROR) || level.equals(LogLevel.NORMAL))	
							{
								logErrorToConsole(logMessage);
								if(fileOutput)
								{
									logToFile(logMessage);
								}
							}
							break;	
			default:		break;							
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
	
	private static void logToFile(String logMessage)
	{
		PathUtils.checkFolder(savePath.getParentFile());
		
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(savePath, true));
            out.write(logMessage);
            out.newLine();          
            out.close();         
		}
		catch(Exception e)
		{		
			fileOutput = false;			
			logErrorToConsole(createLogMessage(LogLevel.ERROR, "Can't log to file " + savePath + " FILEOUTPUT NOW DISABLED!"));
		}    
	}

	public static void setLevel(LogLevel newLevel)
	{
		level = newLevel;
	}
	
	public static void enableFileOutput(File path)
	{
		fileOutput = true;
		savePath = path;
	}

	public static void disableFileOutput()
	{
		fileOutput = false;
	}
	
	public static void deleteLogfile()
	{
		savePath.delete();
	}	
	
	private static String getStringFromException(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}