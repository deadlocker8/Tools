package tools;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * 
 * @author Robert
 *
 */
public class ErrorMessage 
{
	private static String[] errorCodes;
	private static String result;
	
	/**
	 * erzeugt eine neue Fehlermeldung für den entsprechenden Fehlercode
	 * @param errorCode int - Fehlercode (laut FehlercodeDatei)
	 */
	public static void showErrorMessage(int errorCode)
	{	
		//liest die Datei mit den Fehlercodes ein
		loadErrorMessages();
		
		if(errorCode > 0)
		{		
			try
			{
				//ersetzt alle Tabs und splittet den String an allen "|"
				String[] message = errorCodes[errorCode].replace("\t", "").split("\\|");
				String type = message[1]; 
				String head = message[3];
				String error = message[4];			
				
				//sollte der Head "_" sein, dann wird der entfernt
				if(head.equals("_"))
				{
					head = null;
				}				
			
			@SuppressWarnings("unused")
			boolean flag = false;
				
			//legt den AlertType fest
			AlertType alertType;
			String title;
			switch (type) 
			{
			case "INFO":	alertType  = AlertType.INFORMATION;
        					title = "Information";
        					break;
			case "WARN": 	alertType  = AlertType.WARNING;
        					title = "Warnung";
        					break;      
			case "ERR": 	alertType  = AlertType.ERROR;
        					title = "Fehler";
        					break;
			case "CONF":	alertType  = AlertType.CONFIRMATION;
        					title = "Bestätigung";
        					flag = true;
        					result = confirmDialog(head, error);
        					flag = true;
        					break;
			default: 		alertType  = AlertType.INFORMATION;
        					title = "Information";
        					break;
			}
			
			if(flag = false)
			{			
				//generiert den Alert
				Alert alert = new Alert(alertType);	
				alert.setTitle(title);
				alert.setHeaderText(head);
				alert.setContentText(error);
				alert.showAndWait();	
			}
			
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.err.println("Fehlercode " + errorCode + " konnte nicht gefunden werden!");
			
			}	
		}
		else
		{
			System.err.println("Fehlercode muss größer 0 sein");
		}
	}
	
	/**
	 * 
	 * @param head String - Head laut Fehlercodedatei 
	 * @param message String - Message laut Fehlercodedatei
	 * @return
	 */
	public static String confirmDialog(String head, String message)
	{
		//generiert den Alert
		Alert alert = new Alert(AlertType.CONFIRMATION);	
		alert.setTitle("Bestätigung");
		alert.setHeaderText(head);
		alert.setContentText(message);			
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
		    return "OK";
		} 
		else 
		{
		    return "CANCEL";
		}
	}
	
	
	/**
	 * liest die Fehlercodedatei ein
	 */
	public static void loadErrorMessages()
	{
		errorCodes = Read.readErrorCodesFromJar();
	}
	
	/**
	 * gibt das Ergebnis des Confirmationdialogs zurück
	 * @return result - String
	 */
	public static String getResult()
	{
		return result;
	}
}