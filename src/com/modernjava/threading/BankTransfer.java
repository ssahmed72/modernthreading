package com.modernjava.threading;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankTransfer {
    private double balance;
    private int id;
    private String accountName;
    final Lock lock = new ReentrantLock();

    public BankTransfer (int id, double balance, String accountName){
        this.id=id;
        this.balance=balance;
        this.accountName=accountName;
    }

    public boolean withdraw (double amount) throws InterruptedException {
        if (this.lock.tryLock()){
            Thread.sleep(100);
            balance -= amount;
            this.lock.unlock();
            return true;
        }
        return false;
    }

    public boolean deposit (double amount) throws InterruptedException {
        if(this.lock.tryLock()){
            Thread.sleep(100);
            balance += amount;
            this.lock.unlock();
            return true;
        }
        return false;
    }

    public boolean transfer (BankTransfer to, double amount) throws InterruptedException {
        if (withdraw(amount)){
            System.out.println("Withdrawing amount: " + amount + " from: " + accountName);
            if(to.deposit(amount)){
                System.out.println("Depositing amount: " + amount + " to: " + to.accountName);
                return true;
            }
            else {
                System.out.println("Failed to acquire both locks: refunding " + amount + " to:  " + accountName);
                while (!deposit(amount))
                    continue;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        BankTransfer studentBankAccount = new BankTransfer(1, 50000, "StudentA");
        BankTransfer universityBankAccount = new BankTransfer(1, 100000, "University");

        System.out.println("Starting balance of account are: University: " + universityBankAccount.balance
         + " student: " + studentBankAccount.balance);

        ExecutorService service = Executors.newFixedThreadPool(10);

        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " says :: Executing Transfer");
            try {
                while (!studentBankAccount.transfer(universityBankAccount, 1000)) {
                    Thread.sleep(100);
                    continue;
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " says transfer is successful");
        });

        for (int i=0;i<20; i++){
            service.submit(t);
        }

        service.shutdown();
        try {
            while (!service.awaitTermination(24L, TimeUnit.HOURS)) {
                System.out.println("Not Yet. still waiting for termination");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Ending balance of student account: " + studentBankAccount.balance +
                " University account: " + universityBankAccount.balance);

    }















}
