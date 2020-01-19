package com.modernjava.threading;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class ExecutorRunnable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = () ->{
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Finished executing at: " + LocalDateTime.now());
        };

        ExecutorService executor = Executors.newFixedThreadPool(10);
        System.out.println("First Example - executing task with execute() method ");
        executor.execute(runnable);

        System.out.println("Second Example - executing task with submit() method");
        Future<String> result = executor.submit(runnable, "COMPLETED");

        while (!result.isDone()){
            System.out.println("The method return value: " + result.get());
        }
        executor.shutdown();


    }
}
