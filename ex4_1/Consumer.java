package ex4_1;

import java.util.Queue;

public class Consumer extends Thread {
    private Queue<Integer> buffer;
    private int n;

    public Consumer(Queue<Integer> buffer, int n) {
        this.n = n;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int consumedCount = 0;
        while (consumedCount < n) {

                Integer value = buffer.poll();
                if (value != null) {
                    consumedCount++;
                    System.out.println("Consumed: " + value);
                }

            }

        }
    }


