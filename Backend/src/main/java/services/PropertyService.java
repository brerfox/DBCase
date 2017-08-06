package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {

//    manual testing
    public static void main(String[] args) {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        PropertyService propertyService = new PropertyService();
        System.out.println(propertyService.getProperties());
    }

    static private final Properties properties;
    static private final String pathToFile = "./config/dbConnector.properties";

    static {
        properties = new Properties();
        try (InputStream input = new FileInputStream(pathToFile)) {
            properties.load(input);

            System.out.println("Get Properties: ");
            System.out.println(properties.stringPropertyNames());

        } catch (IOException ex) {
            System.out.println("Problem with property file !!!");
            ex.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}