package com.modernjava.threading.examples;

public class DinningPhilosopherSynchronized {
    public static void main(String[] args) {
        Philospher[] philosphers = new Philospher[5];
        Object[] forks = new Object [5];
        for (int i=0;i<forks.length; i++){
            forks[i] = new Object();
        }

        for (int i=0; i<philosphers.length; i++){
            Object leftFork = forks[i];
            Object rightFork = forks[(i+1)%forks.length];
            philosphers[i] = new Philospher(leftFork, rightFork);
            Thread t = new Thread(philosphers[i], "Philosopher " + (i+1));
            t.start();
        }


    }
}

class Philospher implements Runnable{
    private Object leftFork;
    private Object rightFork;

    public Philospher(Object leftFork, Object rightFork){
        this.leftFork=leftFork;
        this.rightFork=rightFork;
    }

    @Override
    public void run() {
        while (true){
            doWork (System.currentTimeMillis() + " : Thinking");
            synchronized (leftFork) {
                doWork(System.currentTimeMillis() + ": Picked up left Fork");
                synchronized (rightFork) {
                    doWork(System.currentTimeMillis() + ": Pickup right Fork and eating");
                }
                doWork(System.currentTimeMillis() + ": Put down right Fork");
            }
            doWork (System.currentTimeMillis() + ": Put down left Fork and back to thinking");
        }


    }

    private void doWork(String s) {
        System.out.println(Thread.currentThread().getName() + " " + s);
        try {
            Thread.sleep(((int) (Math.random()*100)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
