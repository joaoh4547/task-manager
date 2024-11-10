package com.github.joaoh4547.task;

//import com.github.joaoh4547.task.notification.TaskNotificator;

import com.github.joaoh4547.task.event.TaskEventEmitter;
import com.github.joaoh4547.data.entities.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Task<T> {

    private final Integer taskId;
    private final Process<T> process;

    private List<Action> beforeActions = new ArrayList<>();
    private List<Action> afterActions = new ArrayList<>();
    private ActionErrorHandler errorHandler = TaskError::new;


    private TaskContext context;

    private final TaskAction<T> action;

    public Task(Process<T> process, TaskAction<T> action) {
        this.process = process;
        this.process.setTask(this);
        this.taskId = TaskKeyGenerator.getTaskId();
        this.action = action;
    }

    public void addBeforeAction(Action action) {
        beforeActions.add(action);
    }

    public void addAfterAction(Action action) {
        afterActions.add(action);
    }

    public List<Action> getAfterActions() {
        return afterActions;
    }

    public void setAfterActions(List<Action> afterActions) {
        this.afterActions = afterActions;
    }

    public ActionErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ActionErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public List<Action> getBeforeActions() {
        return beforeActions;
    }

    public void setBeforeActions(List<Action> beforeActions) {
        this.beforeActions = beforeActions;
    }

    private Optional<TaskEventEmitter> getEventEmitter() {
        if (context.getEventEmitter() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(context.getEventEmitter());
    }

    public TaskResult<T> execute() {
        TaskResult<T> result = null;
        try {
            if(getEventEmitter().isPresent()) {
                TaskEventEmitter emitter = getEventEmitter().get();

            }
            for (Action beforeAction : beforeActions) {
                beforeAction.execute();
            }
            result = action.execute();
            for (Action afterAction : afterActions) {
                afterAction.execute();
            }
        } catch (Exception e) {
            TaskError error = errorHandler.handleError(e);
            result = TaskResult.of(null, error);
            System.out.println(e.getMessage());
        }
        return result;

    }

    public TaskContext getContext() {
        return context;
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

    public Integer getTaskId() {
        return taskId;
    }


    @Override
    public String toString() {
        return "Task{" + context + "/ Task ID " + taskId + "}";
    }
}
