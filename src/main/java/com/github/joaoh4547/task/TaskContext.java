package com.github.joaoh4547.task;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

public class TaskContext {

    private final String processName;
    private final Integer workerCount;



    TaskContext(String processName, Integer workerCount) {
        this.processName = processName;
        this.workerCount = workerCount;
    }

    public Integer getWorkerCount() {
        return workerCount;
    }

    public String getProcessName() {
        return processName;
    }

    public static TaskContext create(String processName, Integer workerCount) {
        return new TaskContext(processName, workerCount);
    }

    public static TaskContext create(String processName) {
        return new TaskContext(processName, -1);
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
