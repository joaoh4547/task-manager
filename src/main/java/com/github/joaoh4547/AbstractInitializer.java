package com.github.joaoh4547;

import com.github.joaoh4547.data.DataBaseContext;
import com.github.joaoh4547.utils.Bundler;
import com.zaxxer.hikari.HikariDataSource;

public class AbstractInitializer implements Initializer {

    private static final String DATABASE_BUNDLE = "database";


    @Override
    public void onInitialize() {
        initDatabase();
    }

    protected void initDatabase() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(Bundler.getValue("database.url", DATABASE_BUNDLE));
        ds.setUsername(Bundler.getValue("database.user", DATABASE_BUNDLE));
        ds.setPassword(Bundler.getValue("database.password", DATABASE_BUNDLE));
        DataBaseContext.setDataSource(ds);
    }
}
