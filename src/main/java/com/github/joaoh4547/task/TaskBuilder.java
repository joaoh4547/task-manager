package com.github.joaoh4547.task;

import com.github.joaoh4547.utils.Builder;

import java.util.ArrayList;
import java.util.List;

public class TaskBuilder<T> implements Builder<Task<T>> {

    private TaskBuilder() {
    }

    private TaskAction<T> action;
    private final List<Action> beforeActions = new ArrayList<>();
    private final List<Action> afterActions = new ArrayList<>();
    private ActionErrorHandler errorHandler = TaskError::new;


    public TaskBuilder<T> withBeforeAction(Action action) {
        beforeActions.add(action);
        return this;
    }

    public TaskBuilder<T> withAfterAction(Action action) {
        afterActions.add(action);
        return this;
    }

    private TaskBuilder<T> withErrorHandler(ActionErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public TaskBuilder<T> withAction(TaskAction<T> action) {
        this.action = action;
        return this;
    }

    private TaskContext context;

    public TaskBuilder<T> withContext(TaskContext context) {
        this.context = context;
        return this;
    }


    public static <T> TaskBuilder<T> builder() {
        return new TaskBuilder<>();
    }


    @Override
    public Task<T> build() {
        Task<T> task = new Task<T>(action);
        task.setContext(context);
//        task.
//        task.
        return task;
    }
}
