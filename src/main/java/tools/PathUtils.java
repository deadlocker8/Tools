package tools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Stellt Methoden zur Arbeit mit Pfaden zur Verfügung
 * @author Robert
 *
 */
public class PathUtils
{
	/**
	 * prüft ob der Ordner existiert und legt ihn an falls nicht
	 * @param file
	 */
	public static void checkFolder(File file)
	{
		if(!file.exists())
		{
			file.mkdirs();
		}
	}
	
	/**
	 * gibt den Pfad zum "Appdata"-Ordner des jeweiligen aktuellen Nutzers an
	 * @return String - OS unabhängiger Pfad
	 */
	public static String getOSindependentPath()
	{
		switch(OS.getType())
		{
			case Windows:
				return Paths.get(System.getenv("APPDATA")).toAbsolutePath() + "/";
			case MacOSX:
				return Paths.get(System.getProperty("user.home"), "Library/Application Support/") .toAbsolutePath() + "/.";
			case Linux:
				return Paths.get(System.getProperty("user.home")).toAbsolutePath() + "/.";
			default:
				return null;
		}
	}
	
	/**
	 * gibt den Ordner zurück, in dem sich die aktuell ausgeführte jar befindet
	 * @return Path - Ordner
	 * @throws URISyntaxException
	 */
	public static Path getCurrentLocation() throws URISyntaxException
	{	
		return Paths.get(PathUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
	}
	
	/**
	 * öffnet ein Explorerfenster und selektiert die angegebene Datei
	 * @param path - Pfad zur Datei
	 * @throws IOException
	 */
	public static void selectFileInExplorer(String path) throws IOException
	{
		path = path.replace("/", "\\");
		Runtime.getRuntime().exec(new String[] 
		{
				"explorer.exe",
		        "/select,", 
		        "\"" + path + "\""
		});		
	}
}