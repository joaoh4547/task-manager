package com.github.joaoh4547.taskmanager.core;

/**
 * An interface for classes that need to perform initialization tasks.
 */
public interface Initializer {

    /**
     * This method is used to perform initialization tasks. Classes implementing the Initializer interface
     * should override this method to provide their own initialization logic.
     */
    void onInitialize();
}
