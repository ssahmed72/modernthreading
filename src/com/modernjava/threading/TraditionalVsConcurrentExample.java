package com.modernjava.threading;

import java.sql.Time;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class TraditionalVsConcurrentExample {
    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        conurrentModificationArrayList();
    }

    public static void conurrentModificationArrayList() throws BrokenBarrierException, InterruptedException {
        List<Integer> list = new ArrayList<Integer>();
        for (int i=0;i<100000; i++ ){
            list.add(i);
        }
        new Thread (() -> {
            try {
                System.out.println("Adding 5000 to the list");
                cyclicBarrier.await();
                list.add(5000);
            }catch (ConcurrentModificationException cc){
                cc.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }).start();

        Iterator<Integer> it = list.iterator();
        boolean flag=false;
        while (it.hasNext()){
            it.next();
            if (!flag){
                cyclicBarrier.await();
                flag=true;
            }
        }

    }

}
