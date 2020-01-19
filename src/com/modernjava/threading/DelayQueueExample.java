package com.modernjava.threading;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

public class DelayQueueExample {
    static int taskCount;
    public static void main(String[] args) {
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

        Runnable producer = () -> {
            while (true){
                long delayTime = new Random().nextInt(10000);
                //
                Date expirationTime = new Date(System.currentTimeMillis() + delayTime);
                String taskName = "Task " + taskCount++;
                delayQueue.put(new DelayTask(taskName, delayTime));
                System.out.println("Producing task: " + taskName + " with expiration time of: " + expirationTime);
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            while (true){
                DelayTask poll;
                try {
                    poll = delayQueue.take();
                    System.out.println("Consumed task: " + poll.getName() + " with expiration of: " +
                            new Date(poll.getDelayTime()));
                    TimeUnit.MILLISECONDS.sleep(2000);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(producer);
        executorService.submit(consumer);
        executorService.shutdown();


    }
}

class DelayTask implements Delayed {
    private String name;
    private long delayTime;

    public DelayTask (String name, long delayTime){
        this.name=name;
        this.delayTime= System.currentTimeMillis() + delayTime;
    }

    public String getName() {
        return name;
    }

    public long getDelayTime() {
        return delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long difference = delayTime -System.currentTimeMillis();
        return unit.convert(difference, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.delayTime < ((DelayTask)o).delayTime)
            return -1;
        if (this.delayTime > ((DelayTask)o).delayTime)
            return 1;
        return 0;
    }
}
