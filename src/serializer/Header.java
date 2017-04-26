package serializer;

import java.util.ArrayList;

/**
 * Header for serialized file
 * @author Deadlocker8
 *
 */
public class Header
{
	/**
	 * versionCode = 2 digits long integer (leading zeros if neccessary) representing version of the header
	 * elements = list containing all Strings that should be serialized
	 * headerParts = list containing sizes of every element in the serialized File (after reading it, needed for de-serialization)
	 */
	private int versionCode;
	private ArrayList<String> elements; 
	private ArrayList<Integer> headerParts;
	
	/**
	 * Constructor for Serialization
	 * @param versionCode int - version code of the header
	 */
	public Header(int versionCode)
	{
		this.versionCode = versionCode;
		elements = new ArrayList<>();
	}
	
	/**
	 * Constructor for De-Serialization
	 * @param data - loaded String
	 */
	public Header(String data)
	{
		headerParts = new ArrayList<>();
		
		versionCode = Integer.parseInt(data.substring(0,2));
		int numberOfElements = Integer.parseInt(data.substring(2,6));	
		
		for(int i = 0; i < numberOfElements; i++)
		{
			headerParts.add(Integer.parseInt(data.substring(6 + i *4, 6 + i*4 +4)));
		}
	}
	
	/**
	 * Adds a String to elements
	 * @param newElement String - String that should be added to serialized file
	 */
	public void addElement(String newElement)
	{
		elements.add(newElement);
	}	

	/**
	 * Returns the Headersize in bytes
	 * @return int - size of header in bytes
	 */
	public int getHeaderSize()
	{
		//2 digits versionCode + 4 digits numberOfElements
		return 2 + 4 + headerParts.size() * 4;
	}
	
	/**
	 * Returns the number of elements
	 * @return int - number of elements
	 */
	public int getNumberOfElements()
	{
		return  headerParts.size();
	}
	
	/**
	 * Returns the header part at a specific position
	 * @param position int - position
	 * @return int - size of the element in the serialized file in bytes
	 */
	public int getHeaderPart(int position)
	{
		return headerParts.get(position);
	}
	
	/**
	 * Generates the complete header String
	 * @return String  - header String
	 */
	public String createHeader()
	{
		String header = String.format("%02d", versionCode) + String.format("%04d", elements.size());
		
		for(String current : elements)
		{
			header +=  String.format("%04d", current.length());
		}
		
		return header;
	}
}