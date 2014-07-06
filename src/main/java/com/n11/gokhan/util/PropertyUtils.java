package com.n11.gokhan.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtils {

    private static Logger logger = Logger.getLogger(PropertyUtils.class);

    private static String PROP_FILE = "messages.properties";

    private Properties property;

    public PropertyUtils() {

        try {
            InputStream is = PropertyUtils.class.getResourceAsStream("/" + PROP_FILE);
            property = new Properties();
            property.load(is);
            is.close();

        } catch (IOException e) {
            logger.error(e.toString());
        }

    }

    /**
     * Returns the property value for the given key.
     * 
     * @param key
     * @return property value for the given key
     * @throws NullPointerException
     *             if the key is not found in the property list
     */
    public String readFromProperty(String key) {
        return property.getProperty(key);
    }

    public Properties getProperty() {
        return property;
    }

}
