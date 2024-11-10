package com.github.joaoh4547.data;

import com.zaxxer.hikari.HikariDataSource;

public class DataBaseContext {

    private static final InheritableThreadLocal<HikariDataSource> dataSource = new InheritableThreadLocal<>();

    public static HikariDataSource getDataSource() {
        return dataSource.get();
    }

    public static void setDataSource(HikariDataSource ds) {
        dataSource.set(ds);
    }


}
