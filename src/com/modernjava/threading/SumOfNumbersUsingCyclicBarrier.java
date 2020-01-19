package com.modernjava.threading;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SumOfNumbersUsingCyclicBarrier {
    private static int[] array1 = IntStream.rangeClosed(0,5000).toArray();
    private static int[] array2 = IntStream.rangeClosed(5001,10000).toArray();
    private static int[] array3 = IntStream.rangeClosed(10001, 15000).toArray();
    private static int total = IntStream.rangeClosed(0,15000).sum();
    final static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException, ExecutionException {
        Callable callable1 = () -> {
            int sum=0;
            sum = sum + calculateSum(0,array1.length/2, array1);
            cyclicBarrier.await();
            sum = sum + calculateSum(0, array2.length/2, array2);
            cyclicBarrier.await();
            sum = sum + calculateSum(0, array3.length/2, array3);
            cyclicBarrier.await();
            return sum;
        };

        Callable callable2 = () -> {
            int sum =0;
            sum = sum + calculateSum(array1.length/2, array1.length,array1);
            cyclicBarrier.await();
            sum = sum + calculateSum(array2.length/2, array2.length,array2);
            cyclicBarrier.await();
            sum = sum + calculateSum(array2.length/2, array3.length, array3);
            cyclicBarrier.await();
            return sum;
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> sum1 = executorService.submit(callable1);
        Future<Integer> sum2 = executorService.submit(callable2);

        System.out.println("Calculating first sum ");
        cyclicBarrier.await();
        System.out.println("First sum is calculated");

        System.out.println("Calculating second sum ");
        cyclicBarrier.await();
        System.out.println("Second sum is calculated");

        System.out.println("Calculating third sum ");
        cyclicBarrier.await();
        System.out.println("Third sum is calculated");

        int sum = sum1.get() + sum2.get();

        System.out.println("Sum of three arrays is: " + sum );
        System.out.println("Correct sum is: "  + total);
        executorService.shutdown();
    }

    private static int calculateSum(int start, int end, int[] array) {
        int sum1=0;
        for (int i=start; i<end; i++){
            sum1 = sum1 + array[i];
        }
        return sum1;
    }

}
