package tools;

public class Regex
{
	public static boolean isValidEmail(String mail)
	{
		return mail.matches("^[a-zA-Z0-9-_.]+@[\\w]{1,20}.[a-zA-Z]{2,4}$");
	}
}