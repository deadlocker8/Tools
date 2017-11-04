package serializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import logger.LogLevel;
import logger.Logger;

public class Example
{
	private static final File SAVE_PATH = new File("C:/Users/ROGO2/Desktop/data.dat");
	
	public static void main(String[] args)
	{		
		Logger.setLevel(LogLevel.ALL);
		String text = "Lorem Ipsum";
		String number = "5";
		String text2 = "AABBCCDDEE";
		
		Header header = new Header(1);
		header.addElement(text);
		header.addElement(number);
		header.addElement(text2);		
		
		Logger.debug("Header: " + header.createHeader());
		
		String completeText = text + number + text2;
		completeText = header.createHeader() + completeText;	
		
		Logger.debug("Complete Text: " + completeText);
		
		try
		{
			byte[] b = completeText.getBytes("UTF-8");
			Logger.debug("bytes: " + Arrays.toString(b));
			
			Serializer.serializeToFile(SAVE_PATH.getAbsolutePath(), b);
			
			//Deserialize			
			String data = new String(Serializer.deserializeFromFile(SAVE_PATH.getAbsolutePath()));				
			header = new Header(data);		
			
			ArrayList<String> parts = new ArrayList<>();
			int position = header.getHeaderSize();
			
			for(int i = 0; i < header.getNumberOfElements(); i++)
			{
				int currentSize = header.getHeaderPart(i);
				parts.add(data.substring(position, position + currentSize));
				position =  position + currentSize;
			}
			
			Logger.debug(parts);
		}
		catch(Exception e)
		{
			Logger.error(e);
		}	
	}
}
