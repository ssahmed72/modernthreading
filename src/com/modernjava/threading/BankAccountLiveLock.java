package com.modernjava.threading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountLiveLock {
    private double balance;
    private int id;
    final Lock lock = new ReentrantLock();

    public BankAccountLiveLock (int id, double balance){
        this.id = id;
        this.balance  = balance;
    }

    public boolean withdraw (double amount ) throws InterruptedException {
        if (this.lock.tryLock()){
            Thread.sleep(1000);
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean deposit (double amount) throws InterruptedException {
        if (this.lock.tryLock()){
            Thread.sleep(1000);
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean transfer (BankAccountLiveLock from, BankAccountLiveLock to, double amount) throws InterruptedException {
        if (from.withdraw(amount)){
            System.out.println("Withdrawing " + amount + " from" + id);
            if (to.deposit(amount)){
                System.out.println("Depositing " + amount + " to " + id);
                return true;
            }else{
                from.deposit(amount);
                System.out.println("Refunding amount: " + amount + " to account: " + from.id);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        BankAccountLiveLock studentBankAccount = new BankAccountLiveLock(1, 50000);
        BankAccountLiveLock universityBankAccount = new BankAccountLiveLock(2, 100000);

        new Thread(() -> {
            while (true) {
                try {
                    if (!!studentBankAccount.transfer(studentBankAccount, universityBankAccount, 3000)) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    if (!!universityBankAccount.transfer(universityBankAccount,studentBankAccount,1000)) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

        }).start();


    }

}
