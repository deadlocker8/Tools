package tools;

/**
 * Stellt Methoden zur Arbeit in Abhängigkeit vom Betriebssystem zur Verfügung
 * @author Robert
 *
 */
public class OS
{
	public enum OSType
	{
		Windows, MacOSX, Linux, Other;
	}

	private static OSType type;
	
	static
	{
		if(System.getProperty("os.name").contains("Windows"))
		{
			type = OSType.Windows;
		}
		else if(System.getProperty("os.name").contains("OS X"))
		{
			type = OSType.MacOSX;
		}
		else if(System.getProperty("os.name").contains("Linux"))
		{
			type = OSType.Linux;
		}
		else
		{
			type = OSType.Other;
		}
	}

	/**
	 * gibt den Typ des aktuellen Betriebssystems zurück
	 * @return
	 */
	public static OSType getType()
	{
		return type;
	}
}