package ex_4_2;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeConsensusAlgo {
    // I am using an Integer, but you could also go it with generic type T.

    // one shared devision variable
    AtomicReference<Integer> decision;
    public LockFreeConsensusAlgo() {
        decision = new AtomicReference<>();
    }

    public int propose(int value) {
        // try to set the decision to the proposed value if it is not already set
        if (decision.compareAndSet(null, value)) {
            System.out.println("this thread's proposal was accepted");
            return value;
        } else {
            return decision.get(); // another thread's proposal was accepted
        }
    }

    public int getDecision() {
        return decision.get();
    }
}
