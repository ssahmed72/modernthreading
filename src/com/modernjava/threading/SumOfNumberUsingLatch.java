package com.modernjava.threading;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SumOfNumberUsingLatch {
    public static int[] array = IntStream.rangeClosed(0,5000).toArray();
    final static int total = IntStream.rangeClosed(0,5000).sum();
    final static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable callable1 = () -> {
            int sum =0;
            for (int i=0;i<array.length/2; i++){
                sum = sum + array[i];
            }
            countDownLatch.countDown();
            return sum;
        };

        Callable callable2 = () -> {
            int sum =0;
            for (int i=array.length/2; i<array.length; i++){
                sum = sum + array[i];
            }
            countDownLatch.countDown();
            return sum;
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> sum1 = executorService.submit(callable1);
        Future<Integer> sum2 = executorService.submit(callable2);

        System.out.println("Count Down latch count before calling the await: " + countDownLatch.getCount());
        countDownLatch.await();
        System.out.println("Count Down Latch count after await: " + countDownLatch.getCount());

        int sum= sum1.get() + sum2.get();

        System.out.println("Sum from the thread is: " + sum );
        System.out.println("Correct sum is: " + total);

        executorService.shutdown();




    }
}
