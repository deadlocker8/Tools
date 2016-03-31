package tools;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * stellt Methoden zum Speichern von Dateien zur Verfügung
 * @author Robert
 *
 */
public class Save
{
	/**
	 * hängt einen String an eine Datei an
	 * @param text Text, der gespeichert werden soll
	 * @param directory Dateipfad
	 */
	public static void SaveToFile(String text, String directory) 
	{		
		try 
		{
			File datei= new File (directory);
            BufferedWriter out;           
            
            //falls die Datei noch nicht existiert wird eine neue angelegt
            if (datei.exists())
			{
            	//true appends to file instead of overwrite
            	out = new BufferedWriter(new FileWriter(datei, true));
			}
			else
			{				
			  datei.createNewFile();
			  out = new BufferedWriter(new FileWriter(datei, true));			  
			}
            out.write(text);
            out.newLine();
            out.close();
        }
        catch (IOException e)
        {
        	System.err.println("Fehler beim Speichern der Datei   " + directory);       
        }
    }
	
	/**
	 * überschreibt eine bestehende Datei
	 * @param text Text, der gespeichert werden soll
	 * @param directory Dateipfad
	 */
	public static void OverwriteFile(String text, String directory) 
	{		
		try 
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(directory));
            out.write(text);
            out.newLine();
            out.close();
        }
        catch (IOException e)
        {
        	System.err.println("Fehler beim Speichern der Datei   " + directory);   
        }
    }	
	
	/**
	 * speichert einen Screenshot
	 * @param savePath Speicherpfad
	 * @param fileName Name der Datei
	 * @param type Dateityp
	 * --- "jpg" - erstellt eine JPEG-Datei
	 * --- "png" - erstellt eine PNG-Datei
	 * --- "" - erstellt eine PNG-Datei
	 * @param width	Breite des Auschnitts
	 * @param height Höhe des Ausschnitts
	 * @param xPos horizontale Startposition des Screenshots (von oberer linker Ecke des Bildschirms)
	 * @param yPos vertikale Startposition des Screenshots (von oberer linker Ecke des Bildschirms)
	 */
	public static void saveScreenshot(String savePath, String fileName, String type, int width, int height, int xPos, int yPos)
	{
		Robot rob;
		try 
		{
			rob = new Robot();
			Rectangle recht = new Rectangle();
			 
			recht.setFrame(xPos, yPos, width, height);
 
			RenderedImage shot = rob.createScreenCapture(recht); 			
							
			if(type.equals("jpg"))
			{	
				File datei = new File(savePath + fileName + ".jpg");
				ImageIO.write(shot, "jpg", datei);
			}
			else if (type.equals("png") || type.equals(""))
			{
				File datei = new File(savePath + fileName + ".png");
				ImageIO.write(shot, "png", datei);
			}
			else 
			{
				System.err.println("Ungültiger Dateityp");					
			}
		}
		catch (AWTException | IOException e) 
		{
			System.err.println("Fehler beim Erstellen des Screenshots");
		}			
	}
}