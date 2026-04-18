package ex4_1;

import java.util.LinkedList;
import java.util.Queue;

public class ex_4_1 {

    public static void main(String[] args) {
        int t = 2;
        int n = 1000000; // my computer cannot handle 10million.

        Queue<Integer> q = new LinkedList<Integer>();

        Producer[] producers = new Producer[t];
        for (int i = 0; i < t; i++) {
            producers[i] = new Producer(q,n);
        }

        Consumer[] consumers = new Consumer[t];
        for (int i = 0; i < t; i++) {
            consumers[i] = new Consumer(q,n);
        }

        System.out.println("ex4_1.Producer and ex4_1.Consumer threads have been started.");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < t; i++) {
            consumers[i].start();
        }

        for (int i = 0; i < t; i++) {
            producers[i].start();
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
        System.out.println("ex4_1.Producer and ex4_1.Consumer threads have finished." + " Queue size: " + q.size());


    }

}
