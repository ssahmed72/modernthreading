package com.modernjava.threading;

public class FastFoodRestaurantSynchronizedMethod {
    private String lastClientName;
    private int numberOfBurgersSold;

    public synchronized void buyBurger(String clientName){
        alongRunningProcess();
        this.lastClientName=clientName;
        numberOfBurgersSold++;
        System.out.println(clientName + "bought a burger");
    }

    public void alongRunningProcess(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLastClientName() {
        return lastClientName;
    }

    public void setLastClientName(String lastClientName) {
        this.lastClientName = lastClientName;
    }

    public int getNumberOfBurgersSold() {
        return numberOfBurgersSold;
    }

    public void setNumberOfBurgersSold(int numberOfBurgersSold) {
        this.numberOfBurgersSold = numberOfBurgersSold;
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        FastFoodRestaurantSynchronizedMethod fastFoodRestaurant = new FastFoodRestaurantSynchronizedMethod();

        Thread t1 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("Mike");
        });

        Thread t2 = new Thread (() -> {
            fastFoodRestaurant.buyBurger("Syed");
        });

        Thread t3 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("Jean");
        });

        Thread t4 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("Amy");
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Total numbers of burgers sold: " + fastFoodRestaurant.getNumberOfBurgersSold());
        System.out.println("The last name of client is: " + fastFoodRestaurant.getLastClientName());
        System.out.println("Total execution time: " + (System.currentTimeMillis() - startTime) + " in msec");







    }
}
