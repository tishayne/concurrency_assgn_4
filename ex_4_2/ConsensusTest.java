package ex_4_2;

public class ConsensusTest {
    public static void main(String[] args) {
        LockFreeConsensusAlgo consensus = new LockFreeConsensusAlgo();

        Thread proposer1 = new Thread(() -> {
            int result = consensus.propose(1);
            System.out.println("Proposer 1 got decision: " + result);
        });

        Thread proposer2 = new Thread(() -> {
            int result = consensus.propose(2);
            System.out.println("Proposer 2 got decision: " + result);
        });

        Thread proposer3 = new Thread(() -> {
            int result = consensus.propose(3);
            System.out.println("Proposer 3 got decision: " + result);
        });

        proposer3.start();
        proposer1.start();
        proposer2.start();

        try {
            proposer1.join();
            proposer2.join();
            proposer3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Consensus reached: " + consensus.getDecision());
    }

}
