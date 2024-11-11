package com.github.joaoh4547.taskmanager.migration;

/**
 * Represents the status of a migration process.
 */
public enum MigrationStatus {

    /**
     * Represents the status of a migration process.
     * PENDING status indicates that a migration process is pending.
     */
    PENDING(0),

    /**
     * Represents an error status in a migration process.
     */
    ERROR(1),

    /**
     * Represents a successful status in a migration process.
     */
    SUCCESS(2);

    /**
     * Private final integer variable representing a code value associated with a specific object instance.
     */
    private final int code;

    /**
     * Constructs a MigrationStatus object with the specified code value.
     *
     * @param code the integer code value associated with the MigrationStatus
     */
    MigrationStatus(int code) {
        this.code = code;
    }

    /**
     * Retrieves the code associated with the MigrationStatus object.
     *
     * @return the integer code value associated with the MigrationStatus
     */
    public int getCode() {
        return code;
    }

    /**
     * Retrieves the MigrationStatus enum object corresponding to the given code.
     *
     * @param code the integer code value to match with a MigrationStatus object
     * @return the MigrationStatus object corresponding to the code, or null if no match found
     */
    public static MigrationStatus fromCode(int code) {
        for (MigrationStatus status : MigrationStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
