package ex4_1_2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ex_4_2 {

    static Queue<Integer> queue = new LinkedList<>();
    static ReentrantLock lock = new ReentrantLock();

    static class Producer extends Thread {
        int n;

        public Producer(int nbToProduce) {
            this.n = nbToProduce;

        }
        @Override
        public void run() {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    queue.add(i);
                    System.out.println("Produced: " + i);
                } finally {
                    lock.unlock();
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
                lock.lock();
                // Critival section
                try {
                    if (!queue.isEmpty()) {
                        Integer value = queue.poll();
                        if (value != null) {
                            consumedCount++;
                        }
                        System.out.println("Consumed: " + value);
                    }
                } finally {
                    lock.unlock();
                }
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
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        System.out.println("Producer and Consumer threads have finished." + " Queue size: " + queue.size());
    }

}
