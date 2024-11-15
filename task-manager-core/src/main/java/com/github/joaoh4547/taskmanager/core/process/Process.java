package com.github.joaoh4547.taskmanager.core.process;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Represents a process instance that contains a task, logs, and an id.
 */
@Entity
@Table(name = "TM_PROCESS")
public class Process {

    /**
     * Represents the unique identifier of an entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "STARTED_TIME", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime startTime;

    @Column(name = "END_TIME", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime endTime;

    @Column(name = "NOM_PROCESS")
    private String name;


    @Column(name = "PROCESS_STATE")
    private ProcessState state = ProcessState.QUEUED;

    /**
     * Represents a synchronized collection of ProcessLog instances associated with a Process.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "process", targetEntity = ProcessLog.class)
    private Collection<ProcessLog> logs = Collections.synchronizedCollection(new ArrayList<>());

    public Process() {
    }

    /**
     * Adds a ProcessLog entry to the collection of logs associated with this Process.
     *
     * @param log the ProcessLog to add to the logs collection
     */
    public void addLog(ProcessLog log) {
        logs.add(log);
    }

    /**
     * Retrieves an unmodifiable collection of ProcessLog instances associated with this Process instance.
     * This method allows read-only access to the {@link #logs} collection.
     *
     * @return an unmodifiable collection of ProcessLog instances
     */
    public Collection<ProcessLog> getLogs() {
        return Collections.unmodifiableCollection(logs);
    }

    /**
     * Retrieves the unique identifier of this process instance.
     *
     * @return the UUID identifier of this process instance
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this process instance.
     *
     * @param id the UUID identifier to set for this process instance
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Creates a new instance of Process.
     *
     * @param <T> the type of task associated with the process
     * @return a new Process instance
     */
    public static <T> Process newInstance() {
        return new Process();
    }
}
