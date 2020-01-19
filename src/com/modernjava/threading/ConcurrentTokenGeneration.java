package com.modernjava.threading;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ConcurrentTokenGeneration {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> concurrentMap = new ConcurrentHashMap<Integer, Integer>();

        new Thread (() -> {
            Random numGenerator = new Random();
            for (int i=0;i<10000; i++){
                concurrentMap.put(i, numGenerator.nextInt(10000));
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()  -> {
            Random numGenerator = new Random();
            for (int i=10000; i<20000; i++){
                concurrentMap.put(i, numGenerator.nextInt(20000));
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true){
                Random numGenerator = new Random();
                int key = numGenerator.nextInt(20000);
                Integer value = concurrentMap.get(key);
                if (value !=null){
                    System.out.println("key : " + key + " value is: " + value);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
