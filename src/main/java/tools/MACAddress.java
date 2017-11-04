package tools;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * Class to get current system's MAC-Address
 * @author deadlocker8
 *
 */
public class MACAddress
{
	private String macAddress;
	
	public MACAddress()
	{
		try
		{
			macAddress = getMacAddressFromSystem();
		}
		catch(Exception e)
		{
			macAddress = "0";
		}
	}
	
	private String getMacAddressFromSystem() throws Exception
	{
		InetAddress ip = InetAddress.getLocalHost();
		
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		byte[] mac = network.getHardwareAddress();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < mac.length; i++)
		{
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		}
		
		return sb.toString();
	}
	
	public String getMacAddress()
	{
		return macAddress;
	}
}