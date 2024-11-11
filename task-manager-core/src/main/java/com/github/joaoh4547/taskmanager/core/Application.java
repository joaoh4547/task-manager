package com.github.joaoh4547.taskmanager.core;

/**
 * Application class represents the main entry point for running the application logic.
 */
public class Application extends AbstractInitializer {


    /**
     * This method starts the application by initializing the database and executing the main logic.
     */
    public void start() {
        onInitialize();
        run();
    }

    /**
     * This method is responsible for executing the main logic of the application.
     */
    public void run() {

    }
}
