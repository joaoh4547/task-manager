package com.github.joaoh4547.taskmanager.core.process;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Represents a log entry for a process.
 */
@Entity
@Table(name = "TM_PROCESS_LOG")
public class ProcessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "LOG_ID")
    private UUID id;

    @Column(name = "DSC_MESSAGE")
    private String message;

    @Column(name = "LOG_TYPE")
    private LogType type;

    @JoinColumn(name = "PROCESS_ID")
    @ManyToOne(targetEntity = Process.class)
    private Process process;

    public ProcessLog() {
    }

    public ProcessLog(String message, LogType type, Process process) {
        this.message = message;
        this.type = type;
        this.process = process;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
