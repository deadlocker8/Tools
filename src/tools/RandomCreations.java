package tools;

import java.util.Random;

/**
 * diverse Zufallsgenerierungen
 * @author Robert
 *
 */
public class RandomCreations
{
	/**
	 * generiert einen zufälligen hexadezimalen Farbcode
	 * @return String - Hexcode
	 */
	public static String generateRandomHexColor()
	{
		Random rand = new Random();
		
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		
		return "#" + String.format("%02X", r) + String.format("%02X", g) + String.format("%02X", b);		
	}
	
	/**
	 * generates a random uppercase String with length chars
	 * @param length - number of chars
	 * @param enableDigits - allow digits
	 * @return String - random String
	 */
	public static String generateRandomUppercaseString(int length, boolean enableDigits)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++)
		{
			sb.append(generateRandomUppercaseChar(enableDigits));
		}
		
		return sb.toString();
	}
	
	/**
	 * generates a random mixed case String with length chars
	 * @param length - number of chars
	 * @param enableDigits - allow digits
	 * @return String - random String
	 */
	public static String generateRandomMixedCaseString(int length, boolean enableDigits)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++)
		{
			Random random = new Random();
			if(random.nextInt(2) == 0)
			{
				sb.append(generateRandomUppercaseChar(enableDigits).toLowerCase());
			}
			else
			{
				sb.append(generateRandomUppercaseChar(enableDigits));
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * generates a random uppercase  char	
	 * @param enableDigits - allow digits
	 * @return String - random char
	 */
	public static String generateRandomUppercaseChar(boolean enableDigits)
	{
		String alphabet;
		if(enableDigits)
		{
			alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		}
		else
		{
			alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
	    int N = alphabet.length();

	    Random r = new Random();
	   
	    return String.valueOf(alphabet.charAt(r.nextInt(N)));		
	}
		
	/**
	 * generates a random BASE58 char	
	 * @return String - random char
	 */
	public static String geberateRandomBase58Char()
	{
		final String alphabet = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
	    final int N = alphabet.length();

	    Random r = new Random();
	   
	    return String.valueOf(alphabet.charAt(r.nextInt(N)));
	}
}