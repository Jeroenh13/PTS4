/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i18n;

import java.util.*;

/**
 *
 * @author jeroe
 */
public class localeSettings {

    private localeSettings() {}

    public static String lang = "en";
    public static String country = "US";

    public static Locale getLocale() {
        return new Locale(lang, country);
    }
    
    public static void setLocale(String lang, String country)
    {
        localeSettings.lang = lang;
        localeSettings.country = country;
    }
    
    public static ResourceBundle getResourceBundle()
    {
        ResourceBundle rb = ResourceBundle.getBundle("Bundle", getLocale());
        return rb;
    }
}
