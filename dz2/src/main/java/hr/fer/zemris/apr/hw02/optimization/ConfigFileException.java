package hr.fer.zemris.apr.hw02.optimization;

/**
 * Exception that gets thrown when some configuration file is invalid.
 *
 * @author dbrcina
 */
public class ConfigFileException extends RuntimeException {

    public ConfigFileException() {
        super();
    }

    public ConfigFileException(String message) {
        super(message);
    }

}
