package com.github.joaoh4547.taskmanager;

import com.github.joaoh4547.taskmanager.core.Application;
import com.github.joaoh4547.taskmanager.db.JpaManager;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {


//        makeTasks();

        var app = new Application() {

            @Override
            public void run() {
                System.out.println("Hello World!");
                JpaManager.getInstance();
            }

        };

        app.start();
    }


//    private static void makeTasks() {
//        Task<Object> task = new Task<>(Main::runAction);
//        Task<Object> task2 = new Task<>(Main::runAction);
//        TaskManager.addTask(task);
////        Thread.sleep(5000);
////        TaskManager.addTask(task2, x -> System.out.println("Task " + task2 + " finished"));
//
//
//        Task<Object> task3 = new Task<>(Main::runAction);
//        Task<Object> task4 = new Task<>(Main::runAction);
//        Task<Object> task5 = new Task<>(() -> null);
//        Task<Object> task6 = new Task<>(Main::runAction);
//
//
//        TaskBuilder<Integer> builder = TaskBuilder.builder();
//
//        builder.build();
//
//
////        Thread.sleep(30000);
////        TaskManager.addTask(TaskContext.IMPORTING_DATA, task3, task4, task5, task6);
//    }
//
//    private static synchronized TaskResult<Object> runAction() {
//        Random random = new Random();
//        long val = random.nextLong((90000000 - 900000) + 1) + 900000;
//        int taskid = counter.incrementAndGet();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        for (long i = 0; i <= val; i++) {
////            System.out.println(i + " of task " + taskid);
//        }
//        System.out.println("Runing task " + taskid);
//        return TaskResult.of(null);
//    }
}