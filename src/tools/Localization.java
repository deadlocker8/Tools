package tools;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization 
{
    private static ResourceBundle bundle;
    private static String base;
    
    public static ResourceBundle getBundle()
    {
        return Localization.bundle;
    }   
    
    public static void init(String base)
    {
        Localization.base = base;
    }
    
    public static void loadLanguage(Locale locale)
    {       
        if(base == null)
        {
            throw new NullPointerException("ResourceBundle base is null. Call Localization.init() first");
        }
        bundle = ResourceBundle.getBundle(base, locale);
    }
    
    public static String getString(String key)
    {
        if(bundle == null)
        {
            throw new NullPointerException("ResourceBundle is null. Call Localization.init() and Localization.loadLanguage() first");
        }
        
        if(bundle.containsKey(key))
        {
            return bundle.getString(key);
        }
        else
        {
            System.err.println("Unknown key for ResourceBundle: " + key);
            return key;
        }
    }
    
    public static String getString(String key, Object... args)
    {        
        if(bundle == null)
        {
            throw new NullPointerException("ResourceBundle is null. Call Localization.init() and Localization.loadLanguage() first");
        }
        
        if(bundle.containsKey(key))
        {
            return MessageFormat.format(bundle.getString(key), args);          
        }
        else
        {
            System.err.println("Unknown key for ResourceBundle: " + key);
            return key;
        }
    }
}