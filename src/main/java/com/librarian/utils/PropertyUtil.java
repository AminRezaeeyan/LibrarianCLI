package com.librarian.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

// Utility class for managing application properties
public class PropertyUtil {
    private static Properties properties;

    // Reads properties from the "app.properties" file
    private static void readProperties() {
        if (properties != null) return;

        try {
            properties = new Properties();
            properties.load(PropertyUtil.class.getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the properties", e);
        }
    }

    //  Saves properties to the "app.properties" file
    private static void saveProperties() {
        try {
            properties.store(new FileOutputStream("app.properties"), null);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the properties", e);
        }
    }

    // Retrieves an integer value from properties using dot-separated keys
    public static int getValue(String... keys) {
        readProperties();
        return Integer.parseInt((String) properties.get(String.join(".", keys)));
    }
}
