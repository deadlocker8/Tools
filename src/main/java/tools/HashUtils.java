package tools;

import java.security.MessageDigest;

/**
 * Utility Class for hashing a String with a given salt
 * @author deadlocker8
 *
 */
public class HashUtils
{
	/**
	 * Generates a SHA-512 Hash from the given String with the given salt
	 * @param input String - text to hash
	 * @param salt String - salt
	 * @return String - hashed String 
	 */
	public static String hash(String input, String salt)
	{			
		String hashed = null;
		
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(input.getBytes("UTF-8"));
			byte[] bytes = md.digest(salt.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashed = sb.toString();		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		return hashed;
	}
}