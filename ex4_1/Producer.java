package ex4_1;

import java.util.Queue;

public class Producer extends Thread {
    private Queue<Integer> buffer;
    int n;

    public Producer(Queue<Integer> buffer, int n) {
        this.n = n;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            buffer.add(i);
            System.out.println("Produced: " + i);
        }
    }

}
