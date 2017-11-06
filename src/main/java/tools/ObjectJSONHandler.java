package tools;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class ObjectJSONHandler
{
	/**
	 * loads a class with GSON from a JSON File located in a folder inside systems temp directory (appdata, ...)
	 * @param folderName - name of the folder(s) inside systems temp folder
	 * @param fileName - filename (without file ending)
	 * @param objectype - object type to convert JSON to
	 * @return
	 */
	public static Object loadObjectFromJSON(String folderName, String fileName, Object objectype)
	{
		try
		{
			Gson gson = new Gson();
			Reader reader = Files.newBufferedReader(Paths.get(PathUtils.getOSindependentPath() + folderName + "/" + fileName + ".json"), Charset.forName("UTF-8"));
			Object preferences = gson.fromJson(reader, objectype.getClass());
			reader.close();
			return preferences;
		}
		catch(IOException e)
		{
			return null;
		}
	}

	/**
	 * saves an object with GSON to a JSON File located in a folder inside systems temp directory (appdata, ...)
	 * @param folderName - name of the folder(s) inside systems temp folder
	 * @param fileName - filename (without file ending)
	 * @param objectype - object to save
	 * @return
	 */
	public static void saveObjectToJSON(String folderName, String fileName, Object objectToSave) throws IOException
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(objectToSave);
		PathUtils.checkFolder(new File(PathUtils.getOSindependentPath() + folderName));
		Writer writer = Files.newBufferedWriter(Paths.get(PathUtils.getOSindependentPath() + folderName + "/" + fileName + ".json"), Charset.forName("UTF-8"));
		writer.write(jsonString);
		writer.close();
	}
}