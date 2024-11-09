package com.github.joaoh4547.task;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskKeyGenerator {

    private static final InheritableThreadLocal<AtomicInteger> taskIdContext = new InheritableThreadLocal<AtomicInteger>();


    public static synchronized Integer getTaskId() {
        synchronized (taskIdContext) {
            AtomicInteger taskId = taskIdContext.get();
            if (taskId == null) {
                taskId = new AtomicInteger(1);
                taskIdContext.set(taskId);
            }
            return taskId.getAndIncrement();
        }
    }

}
