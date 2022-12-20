import java.util.*;

public class Memory {
    public Queue<Process> processQueue = new LinkedList<>();
    public PriorityQueue<Process> Priority;
    public PriorityQueue<Process> Exec;
    public int time = 0;
    public Vector<Process> processes = new Vector<>();
    public Map<String, Integer> history = new HashMap<>();
    Map.Entry<String, Integer> lastEntry = null;
    Vector<String> order = new Vector<>();
    public int currContext = 0;
    public int context = 0;
    private int numOfProcesses;
    private float totalWaitingTime = 0;
    private float totalTurnaroundTime = 0;

    Memory(int _numOfProcesses) {
        Priority = new PriorityQueue<>(new PriorityBased());
        Exec = new PriorityQueue<>(new ExecBased());
        numOfProcesses = _numOfProcesses;
    }

    int getNumOfProcesses() {
        return numOfProcesses;
    }

    float getTotalWaitingTime() {
        return totalWaitingTime;
    }

    float getTotalTurnaroundTime() {
        return totalTurnaroundTime;
    }


    void increaseWaitingTime(int waitingTime) {
        totalWaitingTime += waitingTime;
    }

    void increaseTurnaroundTime(int turnaroundTime) {
        totalTurnaroundTime += turnaroundTime;
    }

}
