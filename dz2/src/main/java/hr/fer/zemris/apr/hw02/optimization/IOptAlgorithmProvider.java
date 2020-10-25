package hr.fer.zemris.apr.hw02.optimization;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Provider for implementations of {@link IOptAlgorithm} interface. Instances are accessible through
 * {@link #getInstance(String)} method. This is a simple model of the singleton/factory design pattern.
 *
 * @author dbrcina
 */
public class IOptAlgorithmProvider {

    /**
     * Map which stores implementations by their simple names.
     */
    private static final Map<String, IOptAlgorithm> ALG_FOR_NAME = new HashMap<>();

    /**
     * Retrieves an implementation of IOptAlgorithm interface whose simple class name is equal to the provided one and
     * stores it in an internal map for the future. Instances of classes are created through the <i>Reflection API</i>.
     * <br>
     * When an implementation is returned, be sure to use {@link IOptAlgorithm#configure(String)} method or certain
     * setters to configure that algorithm.
     *
     * @param simpleClassName simple class name that implements some optimization algorithm.
     * @return an instance of IOptAlgorithm interface.
     * @throws Exception if errors occurs while creating an instance of an IOptAlgorithm interface through the
     *                   <i>Reflection API</i>.
     */
    public static IOptAlgorithm getInstance(String simpleClassName) throws Exception {
        IOptAlgorithm alg = ALG_FOR_NAME.get(simpleClassName);
        if (alg == null) {
            String packageName = IOptAlgorithm.class.getPackageName();
            String className = packageName + "." + simpleClassName;
            Class<IOptAlgorithm> clazz = (Class<IOptAlgorithm>) Class.forName(className);
            Constructor<IOptAlgorithm> constructor = clazz.getDeclaredConstructor();
            // This works because constructors are declared with protected modifier.
            // setAccessible(true) should be called otherwise.
            alg = constructor.newInstance();
            ALG_FOR_NAME.put(simpleClassName, alg);
        }
        return alg;
    }

}
