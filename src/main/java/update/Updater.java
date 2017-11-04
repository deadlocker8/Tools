package update;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nativeWindows.NativeLauncher;
import tools.Read;

/**
 * Stellt Methoden zum Updaten von Programmen zur Verfügung
 * @author Robert
 *
 */
public class Updater
{
	private static String[] versionInfo;
	
	/**
	 * liest Versionsinformationen vom Server ein
	 * @param url String - URL zu den Versionsinformationen	
	 */
	private static void getVersionInfo(String url)
	{		
		try
		{
			versionInfo =  Read.readFromURL(url);
		}
		catch(Exception e)
		{
			versionInfo = null;
		}	
	}
	
	/**
	 * gibt die Versionsnummer zurück
	 * @return version int - maschinenlesbare Versionsnummer
	 */
	private static int getVersion()
	{
		if(versionInfo == null)
		{
			return -1;
		}
		else
		{
			return Integer.parseInt(versionInfo[0]);
		}
	}
	
	/**
	 * gibt den Downloadpfad zurück
	 * @return Downloadpfad String - Pfad, von dem heruntergeladen werden soll
	 */
	private static String getDownloadPath()
	{
		if(versionInfo == null)
		{
			return null;
		}
		else
		{
			return versionInfo[1];
		}
	}	
	
	/**
	 * startet die Updater.jar mit den notwendigen Parametern	
	 * @param appName String - Name des Programms, für das Updates installiert werden sollen
	 * @param folderName String - Name des Ordners in APPDATA, indem die Updater.jar liegt (Optional - wenn null, dann wird als Ordner appName verwendet)
	 * @throws Exception - Fehler beim Starten der Updater.jar
	 */
	private static void update(String appName, String folderName) throws Exception
	{			
		String savePath = "";		
		String url = getDownloadPath();	
		String pathToUpdater = "";
		if(folderName == null)
		{
			savePath = System.getenv("APPDATA") + "\\" + appName + "\\";
			pathToUpdater = System.getenv("APPDATA") + "\\" + appName + "\\Updater.exe";   			
		}
		else
		{
			savePath =  folderName + "\\";
			pathToUpdater = folderName + "\\Updater.exe";   
		}	
		
		savePath = savePath.replace(" ", "%20");	
		System.out.println("savePath: " + savePath + " appName: " + appName + " url: " + url + " pathToUpdater: " + pathToUpdater);
		String params = savePath + " " + appName + " " + url;
				
//		ProcessBuilder pb = new ProcessBuilder("java", "-jar", pathToUpdater, savePath, appName, PID, type, url); 				
//		Process p = pb.start();	
		
		NativeLauncher.executeAsAdministrator(pathToUpdater, params);	
		System.exit(0);
	}
	
	/**
	 * Prüft ob Updates für das Programm vorhanden sind, und lädt diese herunter, wenn vom Nutzer gewünscht
	 * @param urlToVersionInfo String - URL zu den Versionsinformationen
	 * @param appName String - Name des Programms, für das Updates installiert werden sollen
	 * @param folderName String - Name des Ordners in APPDATA, indem die Updater.jar liegt (Optional - wenn null, dann wird als Ordner appName verwendet)
	 * @param currentVersion - aktulle Versionsnummer des Programms
	 * @param stage - aktuelle Stage des Programms (benötigt für das Anzeigen von Alerts) 
	 * @param icon - Image welches als Icon für die Alerts angezeigt werden soll (üblicherweise selbes Icon wie von der aktuellen Stage)
	 * @return updateAvailable boolean - true, wenn Update verfügbar, false, wenn kein Update verfügbar
	 */
	public static boolean checkForUpdates(String urlToVersionInfo, String appName, String folderName, int currentVersion, Stage stage, Image icon)
	{
		getVersionInfo(urlToVersionInfo);
		int version = getVersion();
		if(version > 0)
		{
			if(getVersion() > currentVersion)
			{
				Platform.runLater(()->{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Update");
					alert.setHeaderText("");
					alert.setContentText("Ein Update ist verfügbar. Jetzt herunterladen?");
					alert.initOwner(stage);
					Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
					dialogStage.getIcons().add(icon);
		
					ButtonType buttonTypeOne = new ButtonType("Aktualisieren");
					ButtonType buttonTypeTwo = new ButtonType("Abbrechen");
		
					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		
					Optional<ButtonType> result = alert.showAndWait();
					if(result.get() == buttonTypeOne)
					{
						try
						{				          
							Updater.update(appName, folderName);
						}
						catch(Exception e)
						{
							alert.close();
							
							Alert alert2 = new Alert(AlertType.ERROR);
							alert2.setTitle("Fehler");
							alert2.setHeaderText("");
							alert2.setContentText("Update konnte nicht gestartet werden!");
							alert2.initOwner(stage);
							Stage dialogStage2 = (Stage)alert2.getDialogPane().getScene().getWindow();
							dialogStage2.getIcons().add(icon);
							alert2.showAndWait(); 
						}
					}
					else
					{
						alert.close();
					}		
				});
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}