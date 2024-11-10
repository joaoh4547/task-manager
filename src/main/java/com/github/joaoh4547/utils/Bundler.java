package com.github.joaoh4547.utils;

import java.util.ResourceBundle;

/**
 * This class provides utility methods to retrieve values from bundles. The default bundle is "app".
 */
public class Bundler {

    /**
     * This variable holds the default bundle name that is used in the methods of the Bundler class for retrieving values.
     * By default, the value is set to "app".
     */
    private static final String defaultBundle = "app";

    /**
     * Retrieves the value from the specified bundle for the given key.
     *
     * @param key    the key used to retrieve the value from the bundle
     * @param bundle the name of the bundle to retrieve the value from
     * @return the value associated with the given key in the specified bundle
     */
    public static String getValue(String key, String bundle) {
        return getBundle(bundle).getString(key);
    }

    /**
     * Retrieves the value from the default bundle for the given key.
     *
     * @param key the key used to retrieve the value from the default bundle
     * @return the value associated with the given key in the default bundle
     */
    public static String getValue(String key) {
        return getValue(key, defaultBundle);
    }


    /**
     * Retrieves an integer value from the specified bundle for the given key.
     *
     * @param key    the key used to retrieve the integer value from the bundle
     * @param bundle the name of the bundle to retrieve the integer value from
     * @return the integer value associated with the given key in the specified bundle
     */
    public static int getInt(String key, String bundle) {
        return Integer.parseInt(getValue(key, bundle));
    }


    /**
     * Retrieves an integer value from the specified bundle for the given key.
     *
     * @param key the key used to retrieve the integer value from the bundle
     * @return the integer value associated with the given key in the specified bundle
     */
    public static int getInt(String key) {
        return getInt(key, defaultBundle);
    }


    /**
     * Retrieves the resource bundle with the specified name.
     *
     * @param name the name of the resource bundle to retrieve
     * @return the resource bundle with the specified name
     */
    private static ResourceBundle getBundle(String name) {
        return ResourceBundle.getBundle(name);
    }
}
