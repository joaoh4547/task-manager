package com.github.joaoh4547.taskmanager.core.task;

import com.github.joaoh4547.taskmanager.core.task.event.TaskEventEmitter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TaskContext {

    private final String processName;
    private final Integer workerCount;

    private TaskEventEmitter eventEmitter;



    TaskContext(String processName, Integer workerCount, TaskEventEmitter eventEmitter) {
        this.processName = processName;
        this.workerCount = workerCount;
    }

    public Integer getWorkerCount() {
        return workerCount;
    }

    public String getProcessName() {
        return processName;
    }

    public TaskEventEmitter getEventEmitter() {
        return eventEmitter;
    }

    public void setEventEmitter(TaskEventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    public static TaskContext create(String processName, Integer workerCount, TaskEventEmitter eventEmitter) {
        return new TaskContext(processName, workerCount, eventEmitter);
    }

    public static TaskContext create(String processName, TaskEventEmitter eventEmitter) {
        return new TaskContext(processName, -1, eventEmitter);
    }

    public static TaskContext create(String processName, Integer workerCount) {
        return new TaskContext(processName, workerCount, null);
    }

    public static TaskContext create(String processName) {
        return new TaskContext(processName, -1, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TaskContext that)) return false;

        return new EqualsBuilder().append(processName, that.processName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(processName).toHashCode();
    }
}
