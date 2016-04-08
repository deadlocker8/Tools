package tools;

/**
 * Stellt Methoden zur Validierung von Strings mit Hilfe von Regex zur Verfügung
 * @author Robert
 *
 */
public class Regex
{
	/**
	 * Regex zum Validieren von Emailadressen
	 * @param mail String - mailadresse, die validiert werden soll
	 * @return boolean ist valide
	 */
	public static boolean isValidEmail(String mail)
	{
		return mail.matches("^[a-zA-Z0-9-.]+@[a-zA-Z0-9-.]{1,20}.[a-zA-Z]{2,4}$");
	}
}