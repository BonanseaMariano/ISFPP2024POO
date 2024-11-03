package config;

import controller.Coordinator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The Config class is responsible for loading configuration properties and setting up
 * the application's locale and resource bundle.
 */
public class Config {

    /**
     * Singleton instance of the Config class.
     */
    private static Config configuration = null;

    /**
     * Coordinator instance.
     */
    private Coordinator coordinator;

    /**
     * Resource bundle for internationalization.
     */
    private ResourceBundle resourceBundle;

    /**
     * Returns the singleton instance of the Config class.
     *
     * @return the singleton instance of the Config class
     */
    public static Config getConfig() {
        if (configuration == null) {
            configuration = new Config();
        }
        return configuration;
    }

    /**
     * Private constructor to load configuration properties and set up locale and resource bundle.
     */
    private Config() {
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
            Locale.setDefault(new Locale(prop.getProperty("language"), prop.getProperty("country")));
            resourceBundle = ResourceBundle.getBundle(prop.getProperty("labels"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the resource bundle for internationalization.
     *
     * @return the resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Sets the coordinator instance.
     *
     * @param coordinator the coordinator to set
     */
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

}