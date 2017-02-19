package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * stellt Methoden zum Einlesen von Dateien zur Verfügung
 * @author Robert
 *
 */
public class Read 
{
	/**
	 * liest eine Datei zeilenweise ein
	 * @param directory String - Dateipfad + Dateiname
	 * @return String[] - Zeilen
	 */
	public static String[] readFromFile(String directory) 
	{		
		try 
		{			
			FileInputStream fis = new FileInputStream(directory);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		
			//erzeugt eine neue ArrayList vom Typ String
			List<String> list = new ArrayList<>();
		
			String line;
			//fügt der ArrayList jede Zeile der Datei als einzelnes Element hinzu
			while ((line = reader.readLine()) != null) 
			{
				list.add(line);
			}
			//generiert ein String-Array mit der Länge der ArrayList
			String[] lines = list.toArray(new String[list.size()]);
	   
			//schließt die Quelldatei
			reader.close();
	    
			//gibt das Array zurück
			return lines;
		}
		catch(IOException e)
		{
			System.err.println("Fehler beim Einlesen der Datei   " + directory);
		}
	    return null;
	}	
	
	/**
	 * liest eine Datei, die sich in der Jar befindet
	 * @param directory String - Dateipfad Packagename + Dateiname
	 * @return String[] - Zeilen
	 */
	public static String[] readFromJar(String directory) 
	{		
		try 
		{			
			InputStream input = Read.class.getResourceAsStream("/" + directory);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
			//erzeugt eine neue ArrayList vom Typ String
			List<String> list = new ArrayList<>();
		
			String line;
			//fügt der ArrayList jede Zeile der Datei als einzelnes Element hinzu
			while ((line = reader.readLine()) != null) 
			{
				list.add(line);
			}
			//generiert ein String-Array mit der Länge der ArrayList
			String[] lines = list.toArray(new String[list.size()]);
	   
			//schließt die Quelldatei
			reader.close();
	    
			//gibt das Array zurück
			return lines;
		}
		catch(IOException e)
		{
			System.err.println("Fehler beim Einlesen der Datei   " + directory);
		}
	    return null;
	}	
	
	/**
	 * liest die Datei mit den Fehlercodes ein (Die Datei muss im src-Verzeichnis liegen)
	 * @return String[] - Zeilen
	 */
	public static String[] readErrorCodesFromJar() 
	{		
		try 
		{			
			InputStream input = Read.class.getResourceAsStream("/errorCodes");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
			//erzeugt eine neue ArrayList vom Typ String
			List<String> list = new ArrayList<>();
		
			String line;
			//fügt der ArrayList jede Zeile der Datei als einzelnes Element hinzu
			while ((line = reader.readLine()) != null) 
			{
				list.add(line);
			}
			//generiert ein String-Array mit der Länge der ArrayList
			String[] lines = list.toArray(new String[list.size()]);
	   
			//schließt die Quelldatei
			reader.close();
	    
			//gibt das Array zurück
			return lines;
		}
		catch(IOException e)
		{
			System.err.println("Fehler beim Einlesen der Fehlercodedatei!");
		}
	    return null;
	}	
	
	/**
	 * liest eine Datei von einer URL zeilenweise ein
	 * @param url String - Pfad zur Datei (z.B.: http://www.google.de/textfile.txt)
	 * @return String[] - Zeilen
	 */
	public static String[] readFromURL(String url) throws Exception
	{						
		URL webseite = new URL(url);
		URLConnection connection = webseite.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
        
        ArrayList<String> text = new ArrayList<String>();        

        while ((line = br.readLine()) != null)
        {
        	text.add(line);            		
        }  
        
        String[] lines = text.toArray(new String[text.size()]);                    
		br.close(); 
		
		return lines;	
	}
	
	/**
	 * converts an InputStream to a String
	 * @param is - InputStream
	 * @return String
	 */
	public static String getStringFromInputStream(InputStream is)
	{
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try
		{
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while((line = br.readLine()) != null)
			{
				sb.append(line);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try
				{
					br.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}