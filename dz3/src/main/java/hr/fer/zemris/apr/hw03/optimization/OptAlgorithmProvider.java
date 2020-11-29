package hr.fer.zemris.apr.hw03.optimization;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * The {@link IOptAlgorithm} interface implementation provider. Instances are accessible through
 * {@link #getInstance(String)} method. This is a simple model of the factory design pattern.
 *
 * @author dbrcina
 */
public class OptAlgorithmProvider {

    /**
     * Creates an implementation of the {@link IOptAlgorithm} interface whose simple class name is equal to the provided one.
     * Instances of classes are created through the <i>Reflection API</i>.
     * <br>
     * When an implementation is returned, be sure to use {@link IOptAlgorithm#configure(Properties)} method or certain
     * setters to configure that implementation.
     *
     * @param simpleClassName simple class name of an optimization algorithm.
     * @return an instance of the {@link IOptAlgorithm} interface.
     * @throws Exception if errors occurs while creating an instance through the <i>Reflection API</i>.
     */
    public static IOptAlgorithm getInstance(String simpleClassName) throws Exception {
        String packageName = IOptAlgorithm.class.getPackageName();
        String className = packageName + "." + simpleClassName;
        Class<IOptAlgorithm> clazz = (Class<IOptAlgorithm>) Class.forName(className);
        Constructor<IOptAlgorithm> constructor = clazz.getDeclaredConstructor();
        // This works because constructors are declared with protected modifier.
        // setAccessible(true) should be called otherwise.
        return constructor.newInstance();
    }

}
