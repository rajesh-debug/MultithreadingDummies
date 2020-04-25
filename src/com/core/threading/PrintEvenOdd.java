package com.core.threading;

public class PrintEvenOdd {
    private int count = 1;
    private boolean odd = true;
    private int MAX = 10;

    public PrintEvenOdd(int max) {
        this.MAX = max;
    }

    public void printOdd() {
        while(count < MAX) {
            synchronized (this) {
                while (!odd) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(count++);
                notify();
            }
        }

    }

    public void printEven() {
        while (count < MAX) {
            synchronized (this) {
                while (odd) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(count++);
                notify();
            }
        }
    }

    public static void main(String[] args) {
        PrintEvenOdd printEvenOdd = new PrintEvenOdd(2000);
        Thread even = new Thread() {
            @Override
            public void run() {
                printEvenOdd.printEven();
            }
        };

        Thread odd = new Thread() {
            @Override
            public void run() {
                printEvenOdd.printOdd();
            }
        };
        even.start();
        odd.start();
    }
}