package com.github.joaoh4547.utils;

import java.util.ResourceBundle;

public class Bundler {

    private static final String defaultBundle = "app";

    public static String getValue(String key, String bundle) {
        return getBundle(bundle).getString(key);
    }

    public static String getValue(String key) {
        return getValue(key, defaultBundle);
    }


    public static int getInt(String key, String bundle) {
        return Integer.parseInt(getValue(key, bundle));
    }


    public static int getInt(String key) {
        return getInt(key, defaultBundle);
    }


    private static ResourceBundle getBundle(String name) {
        return ResourceBundle.getBundle(name);
    }
}
