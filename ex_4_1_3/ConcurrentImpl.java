package ex_4_1_3;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentImpl {

    static Queue<Integer> queue = new ConcurrentLinkedQueue<>();
    //static ReentrantLock lock = new ReentrantLock();

    static class Producer extends Thread {

        int n;

        public Producer(int nbToProduce) {
            this.n = nbToProduce;

        }

        @Override
        public void run() {
            for (int i = 0; i < n; i++) {
                try {
                    queue.add(i);
                    System.out.println("Produced: " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {

        int n;

        public Consumer(int nbToConsume) {
            this.n = nbToConsume;
        }

        @Override
        public void run() {
            int consumedCount = 0;
            while (consumedCount < n) {

                Integer value = queue.poll();
                if (value != null) {
                    consumedCount++;; // Queue is empty, try again
                }

                System.out.println("Consumed: " + value);
            }
        }
    }

    public static void main(String[] args) {
        int t = 2;
        int n = 1000000;

        Producer[] producers = new Producer[t];
        for (int i = 0; i < t; i++) {
            producers[i] = new Producer(n);
        }

        Consumer[] consumers = new Consumer[t];
        for (int i = 0; i < t; i++) {
            consumers[i] = new Consumer(n);
        }

        System.out.println("Producer and Consumer threads have been started.");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < t; i++) {
            producers[i].start();
        }

        for (int i = 0; i < t; i++) {
            consumers[i].start();
        }

        for (int i = 0; i < t; i++) {
            try {
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < t; i++) {
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Producer and Consumer threads have finished." + " Queue size: " + queue.size());
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

}
