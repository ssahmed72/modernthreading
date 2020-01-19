package com.modernjava.threading.examples;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DinningPhilosophers {
    static Lock fork1 = new ReentrantLock();
    static Lock fork2 = new ReentrantLock();
    static Lock fork3 = new ReentrantLock();
    static Lock fork4 = new ReentrantLock();
    static Lock fork5 = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Philosopher 1 is thinking");
            fork2.lock();
            System.out.println("Philosopher 1 picked up left fork");
            fork1.lock();
            System.out.println("Philosopher 1 is now eating");
            fork1.unlock();
            fork2.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("Philosopher 2 is thinking");
            fork3.lock();
            System.out.println("Philosopher 2 picked up left fork");
            fork2.lock();
            System.out.println("Philosopher 2 is now eating");
            fork2.unlock();
            fork3.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("Philosopher 3 is thinking");
            fork4.lock();
            System.out.println("Philosopher 3 picked up left fork");
            fork3.lock();
            System.out.println("Philosopher 3 is now eating");
            fork3.unlock();
            fork4.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("Philosopher 4 is thinking");
            fork5.lock();
            System.out.println("Philosopher 4 picked up left fork");
            fork4.lock();
            System.out.println("Philosopher 4 is now eating");
            fork4.unlock();
            fork5.unlock();
        }).start();

        new Thread(() -> {
            System.out.println("Philosopher 5 is thinking");
            fork1.lock();
            System.out.println("Philosopher 5 picked up left fork");
            fork5.lock();
            System.out.println("Philosopher 5 is now eating");
            fork5.unlock();
            fork1.unlock();
        }).start();



    }
}
