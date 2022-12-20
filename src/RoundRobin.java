//Public class for Round Robin algorithm
public class RoundRobin {
    private final Memory memory;

    RoundRobin(Memory _memory) {
        memory = _memory;
    }

    void processFinished(Process process) {
        process.setTurnaroundTime(memory.currTime + memory.currContext - process.getArrivalTime());
        process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
        memory.increaseTurnaroundTime(process.getTurnaroundTime());
        memory.increaseWaitingTime(process.getWaitingTime());
    }

    void queueEmpty() {
        memory.currTime = memory.processes.firstElement().getArrivalTime();
        memory.processQueue.add(memory.processes.firstElement());
        memory.processes.remove(memory.processes.firstElement());
    }

    void checkForNewProcess() {
        while (memory.processes.size() > 0 && memory.processes.firstElement().getArrivalTime() <= memory.currTime) {
            memory.processQueue.add(memory.processes.firstElement());
            memory.processes.remove(memory.processes.firstElement());
        }
    }

    void printAvgs(){
        System.out.println("Average wait time : " + (memory.getTotalWaitingTime() / memory.getNumOfProcesses()) +
                "\nAverage Turn Around Time : " + (memory.getTotalTurnaroundTime() / memory.getNumOfProcesses()));
    }

    //function round robin
    public void round_robin() {
        while (memory.processes.size() > 0 || memory.processQueue.size() > 0) {
            if (memory.processQueue.isEmpty())
                queueEmpty();
            Process peek = memory.processQueue.remove();
            memory.order.add(peek.getProcessName());
            int mn = Math.min(peek.getRemainingBurstTime(), peek.getQuantumTime());
            peek.setRemainingBurstTime(peek.getRemainingBurstTime() - mn);
            memory.currTime += mn;
            checkForNewProcess();
            if (peek.getRemainingBurstTime() == 0) {
                processFinished(peek);
            } else {
                memory.processQueue.add(peek);
            }
            memory.currContext += memory.context;
        }
    }

    //Function to find the order of processes in which execution occurs
    public void orderProcesses() {
        for (int i = 1; i <= memory.order.size(); i++) {
            System.out.print(memory.order.get(i-1));
            if ((i % 6 == 0) || i == memory.order.size() ) {
                System.out.println();
                continue;
            }
            if (i != memory.order.size()) {
                System.out.print(" --> ");
            }
        }
    }
}