package com.github.joaoh4547.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskWorker {


    private final Integer workerId;
    private final TaskContext context;

    private final BlockingQueue<SubWorker<?>> fila = new LinkedBlockingQueue<>();
    private ExecutorService executor;
    private final Lock lock = new ReentrantLock();
    private final Condition novaTarefaAdicionada = lock.newCondition();
    private boolean executando = true;


    TaskWorker(Integer workerId, TaskContext context) {
        this.workerId = workerId;
        this.context = context;
        configExecutor();
        configProcess();
    }

    private void configExecutor() {

        executor = Executors.newSingleThreadExecutor(new NamedThreadFactory(getThreadName()));
    }

    private String getThreadName() {
        return "TaskWorker-" + context.getProcessName() + "-" + workerId;
    }

    private void configProcess() {
        executor.submit(() -> {
            while (true) {
                try {
                    SubWorker<?> tarefa = fila.poll(1, TimeUnit.SECONDS); // Aguarda por uma tarefa por até 1 segundo
                    if (tarefa != null) {
                        tarefa.doWork();
                    } else {
                        // Se não há tarefas, pausamos a execução
                        pausarExecucao();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }


    private SubWorker<?> getSubWorker() throws InterruptedException {
        return fila.poll(1, TimeUnit.SECONDS);
    }


    public static TaskWorker create(Integer workerId, TaskContext context) {
        return new TaskWorker(workerId, context);
    }

    @SafeVarargs
    public final <T> void addTask(Collection<Task<T>> tasks, TaskExecutionListener<T>... listeners) {
        lock.lock();
        TaskRegistry<T> registry = createRegistry(tasks, listeners);
        SubWorker<T> worker = new SubWorker<>(registry);

        try {
            fila.offer(worker);
//            TaskNotificator.getInstance().addNotification();
            if (!executando) {
                novaTarefaAdicionada.signal();
            }
        } finally {
            lock.unlock();
        }

    }

//    private TaskNotification createNotification(){
//
//    }

    private void pausarExecucao() {
        lock.lock();
        try {
            executando = false;
            while (fila.isEmpty()) {
                novaTarefaAdicionada.await(); // Pausa até que uma nova tarefa seja adicionada
            }
            executando = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }


    private static <T> TaskRegistry<T> createRegistry(Collection<Task<T>> tasks, TaskExecutionListener<T>[] listeners) {
        TaskRegistry<T> registry = TaskRegistry.create();
        Arrays.asList(listeners).forEach(registry::addEndListener);
        tasks.forEach(registry::addTask);
        return registry;
    }


    public <T> void addTask(Task<T> task) {
        TaskExecutionListener[] listeners = new TaskExecutionListener[0];
        addTask(task, listeners);
    }


    @SafeVarargs
    public final <T> void addTask(Task<T> task, TaskExecutionListener<T>... listeners) {
        addTask(Collections.singletonList(task), listeners);
    }


    public <T> void addTask(Collection<Task<T>> tasks) {
        TaskExecutionListener<T>[] listeners = new TaskExecutionListener[0];
        addTask(tasks, listeners);
    }

    @SafeVarargs
    public final <T> void addTask(Task<T>... tasks) {
        addTask(Arrays.asList(tasks));
    }

    @SafeVarargs
    public final <T> void addTask(TaskExecutionListener<T> listener, Task<T>... tasks) {
        addTask(Arrays.asList(tasks), listener);
    }


    public int getTaskCount() {
        return fila.size();
    }

}
