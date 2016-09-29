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
}