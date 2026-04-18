package ex4_1_2;

import java.util.Queue;

public class Consumer extends Thread {
    private Queue<Integer> buffer;
    int n;

    public Consumer(Queue<Integer> buffer, int n) {
        this.n = n;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            if (!buffer.isEmpty()) {
                int value = buffer.poll();
                System.out.println("Consumed: " + value);
            }
        }
    }

}
