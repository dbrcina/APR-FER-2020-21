package hr.fer.zemris.apr.hw02.optimization;

import java.util.Properties;

/**
 * Singleton provider for {@link Properties} object.
 *
 * @author dbrcina
 */
public class PropertiesProvider {

    private static Properties properties;

    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
        }
        return properties;
    }

}
