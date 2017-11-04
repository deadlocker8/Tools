package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * stellt Methoden zur Arbeit mit Datum und Uhrzeit zur Verfügung
 * @author Robert
 *
 */
public class DateAndTime 
{
	/**
	 * Gibt die aktuelle Uhrzeit zurück (ohne Millisekunden)
	 * @return String - Uhrzeit (HH:MM:SS)
	 */
	public static String getTimeShort()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("HH:mm:ss");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt die aktuelle Uhrzeit zurück (mit Millisekunden)
	 * @return String - Uhrzeit (HH:MM:SS.SSS)
	 */
	public static String getTimeLong()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("HH:mm:ss.SSS");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt das aktuelle Datum zurück (zweistelliges Jahr)
	 * @return String - Uhrzeit (dd.mm.yy)
	 */
	public static String getDateShort()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("dd.MM.yy");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt das aktuelle Datum zurück (vierstelliges Jahr)
	 * @return String - Uhrzeit (dd.mm.yyyy)
	 */
	public static String getDateLong()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("dd.MM.yyyy");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt das aktuelle Datum und die aktuelle Uhrzeit zurück
	 * @return String - Uhrzeit (dd.MM.yyyy HH:mm:ss)
	 */
	public static String getTimestamp()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");			
			return ausgabe.format(cal.getTime());
	}		
}