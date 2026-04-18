package ex_4_1_4;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class BarriersAndLatchesImpl {

    static Queue<Integer> queue = new ConcurrentLinkedQueue<>();
    //static ReentrantLock lock = new ReentrantLock();

    static class Producer extends Thread {

        int n;
        private final CyclicBarrier barrier;
        private final CountDownLatch latch;


        public Producer(int nbToProduce, CyclicBarrier barrier, CountDownLatch latch) {
            this.n = nbToProduce;

            this.barrier = barrier;
            this.latch = latch;
        }

        @Override
        public void run() {

                try {
                    barrier.await(); // Wait for all producers to be ready
                    for (int i = 0; i < n; i++) {
                        queue.add(i);
                        //System.out.println("Produced: " + i);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    latch.countDown(); // Signal that this producer has finished
            }
        }
    }

    static class Consumer extends Thread {

        int n;
        private final CountDownLatch latch;

        public Consumer(int nbToConsume, CountDownLatch latch) {
            this.latch = latch;
            this.n = nbToConsume;
        }

        @Override
        public void run() {
                try {
                    latch.await(); // Wait for all producers to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            while (true) {
                if (queue.isEmpty()) {
                    break;
                }
                int value = queue.poll();
                //System.out.println("Consumed: " + value);
            }
        }
    }

    public static void main(String[] args) {
        int t = 2;
        int n = 1000000;

        CyclicBarrier barrier = new CyclicBarrier(t);
        CountDownLatch latch = new CountDownLatch(t);

        Producer[] producers = new Producer[t];
        for (int i = 0; i < t; i++) {
            producers[i] = new Producer(n, barrier, latch);
        }

        Consumer[] consumers = new Consumer[t];
        for (int i = 0; i < t; i++) {
            consumers[i] = new Consumer(n, latch);
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
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }

}
