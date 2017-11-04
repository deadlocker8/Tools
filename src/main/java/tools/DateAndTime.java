package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * stellt Methoden zur Arbeit mit Datum und Uhrzeit zur Verf�gung
 * @author Robert
 *
 */
public class DateAndTime 
{
	/**
	 * Gibt die aktuelle Uhrzeit zur�ck (ohne Millisekunden)
	 * @return String - Uhrzeit (HH:MM:SS)
	 */
	public static String getTimeShort()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("HH:mm:ss");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt die aktuelle Uhrzeit zur�ck (mit Millisekunden)
	 * @return String - Uhrzeit (HH:MM:SS.SSS)
	 */
	public static String getTimeLong()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("HH:mm:ss.SSS");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt das aktuelle Datum zur�ck (zweistelliges Jahr)
	 * @return String - Uhrzeit (dd.mm.yy)
	 */
	public static String getDateShort()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("dd.MM.yy");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt das aktuelle Datum zur�ck (vierstelliges Jahr)
	 * @return String - Uhrzeit (dd.mm.yyyy)
	 */
	public static String getDateLong()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("dd.MM.yyyy");			
			return ausgabe.format(cal.getTime());
	}
	
	/**
	 * Gibt das aktuelle Datum und die aktuelle Uhrzeit zur�ck
	 * @return String - Uhrzeit (dd.MM.yyyy HH:mm:ss)
	 */
	public static String getTimestamp()
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat ausgabe = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");			
			return ausgabe.format(cal.getTime());
	}		
}