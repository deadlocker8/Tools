package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	
	public static void log(LogLevel logLevel, String message)
	{		
		if(level == null)
		{
			level = LogLevel.OFF;
		}
		String logMessage = createLogMessage(logLevel, message);
		
		switch(logLevel)
		{
			case INFO:	if(level.equals(LogLevel.ALL) || level.equals(LogLevel.INFO) || level.equals(LogLevel.NORMAL))	
						{
							logToConsole(logMessage);
							if(fileOutput)
							{
								logToFile(logMessage);
							}
						}
						break;
			case DEBUG:	if(level.equals(LogLevel.ALL) || level.equals(LogLevel.DEBUG))	
						{
							logToConsole(logMessage);
							if(fileOutput)
							{
								logToFile(logMessage);
							}
						}
						break;		
			case ERROR:	if(level.equals(LogLevel.ALL) || level.equals(LogLevel.ERROR) || level.equals(LogLevel.NORMAL))	
						{
							logErrorToConsole(logMessage);
							if(fileOutput)
							{
								logToFile(logMessage);
							}
						}
						break;	
			default:	break;							
		}	
	}
	
	private static String createLogMessage(LogLevel logLevel, String message)
	{
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		
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
			BufferedWriter out;
			if(savePath.exists())
			{            	
            	out = new BufferedWriter(new FileWriter(savePath, true));
			}
			else
			{				
				savePath.createNewFile();
				out = new BufferedWriter(new FileWriter(savePath, true));			  
			}
            out.write(logMessage);
            out.newLine();
            out.close();
		}
		catch(Exception e)
		{
			fileOutput = false;
			createLogMessage(LogLevel.ERROR, "Can't log to file " + savePath + " fileOutput now disabled!");
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
}