package com.github.joaoh4547.taskmanager.config;

public class ApplicationContext {

    private static final InheritableThreadLocal<String> terracotaUrl = new InheritableThreadLocal<>();

    public static String getTerracotaUrl() {
        return terracotaUrl.get();
    }

    public static void setTerracotaUrl(String url) {
        terracotaUrl.set(url);
    }
}
