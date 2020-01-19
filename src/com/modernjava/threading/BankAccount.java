package com.modernjava.threading;

public class BankAccount {
    private double balance;
    private int id;

    public BankAccount(double balance, int id){
        this.balance=balance;
        this.id=id;
    }

    public void withdraw(double amount) throws InterruptedException {
        Thread.sleep(1000);
        this.balance -= amount;
    }

    public void deposit (double amount) throws InterruptedException {
        Thread.sleep(1000);
        this.balance += balance;
    }

    public static void transfer (BankAccount from, BankAccount to, double amount) throws InterruptedException {
        synchronized (from){
            from.withdraw(amount);
            System.out.println("Withdrawing: " + amount + " from : " + from.id);
            synchronized (to){
                to.deposit(amount);
                System.out.println("Depositing: " + amount + " to: " + to.id);
            }
        }
    }

    public static void main(String[] args) {
       BankAccount studentBankAccount = new BankAccount(30000, 1);
       BankAccount universityBankAccount = new BankAccount(100000, 2);

       //creating two threads
        //first thread
        new Thread(() -> {
            try {
                BankAccount.transfer(studentBankAccount,universityBankAccount,3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //second thread
        new Thread(() -> {
            try {
                BankAccount.transfer(universityBankAccount,studentBankAccount, 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }





}
