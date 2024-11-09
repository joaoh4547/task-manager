package com.github.joaoh4547;


import com.github.joaoh4547.task.Task;
import com.github.joaoh4547.task.TaskContext;
import com.github.joaoh4547.task.TaskManager;
import com.github.joaoh4547.task.TaskResult;
import com.github.joaoh4547.task.notification.mail.Mail;
import com.github.joaoh4547.task.notification.mail.MailSender;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        makeTasks();
    }



    private static void makeTasks() {
        Task<Object> task = new Task<>(Main::runAction);
        Task<Object> task2 = new Task<>(Main::runAction);
        TaskManager.addTask(task);
//        Thread.sleep(5000);
//        TaskManager.addTask(task2, x -> System.out.println("Task " + task2 + " finished"));


        Task<Object> task3 = new Task<>(Main::runAction);
        Task<Object> task4 = new Task<>(Main::runAction);
        Task<Object> task5 = new Task<>(() -> null);
        Task<Object> task6 = new Task<>(Main::runAction);

//        Thread.sleep(30000);
        TaskManager.addTask(TaskContext.IMPORTING_DATA, task3, task4, task5, task6);
    }

    private static synchronized TaskResult<Object> runAction() {
        Random random = new Random();
        long val = random.nextLong((90000000 - 900000) + 1) + 900000;
        int taskid = counter.incrementAndGet();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (long i = 0; i <= val; i++) {
//            System.out.println(i + " of task " + taskid);
        }
        System.out.println("Runing task " + taskid);
        return TaskResult.of(null);
    }


}