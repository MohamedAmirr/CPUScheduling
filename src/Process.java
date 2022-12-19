public class Process {
    private String processName;
    private int remainingBurstTime;
    private int arrivalTime;
    private int waitingTime;
    private int turnaroundTime;
    private int priority;
    private int remainingQuantumTime;
    private int quantumTime;
    private int burstTime;
    private int quarterOfQuantumTime;
    private int halfOfQuantumTime;
    private int completeTime;
    public boolean finished = false;

    Process(String processName, int arrivalTime, int burstTime, int priorityLevel, int remainingQuantumTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.remainingBurstTime = burstTime;
        this.burstTime = burstTime;
        this.priority = priorityLevel;
        this.remainingQuantumTime = remainingQuantumTime;
        this.quantumTime = remainingQuantumTime;
    }

    Process(String processName, int arrivalTime, int burstTime) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime=burstTime;
    }


    Process(String processName, int arrivalTime, int burstTime, int priority) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public String getProcessName() {
        return this.processName;
    }

    public int getTurnaroundTime() {
        return this.turnaroundTime;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getCompleteTime() {
        return this.completeTime;
    }

    public int getRemainingBurstTime() {
        return this.remainingBurstTime;
    }

    public int getRemainingQuantumTime() {
        return this.remainingQuantumTime;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public int getQuantumTime() {
        return this.quantumTime;
    }

    public int getHalfOfTimeQuantum() {
        return halfOfQuantumTime;
    }

    public int getQuarterOfTimeQuantum() {
        return quarterOfQuantumTime;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getWaitingTime() {
        return this.waitingTime;
    }

    public int calcQuarterOfQuantumTime() {
        return quarterOfQuantumTime = ((this.quantumTime + 3) / 4);
    }

    public int calcHalfOfQuantumTime() {
        return halfOfQuantumTime = ((this.quantumTime + 1) / 2);
    }

    public void setRemainingBurstTime(int Bt) {
        this.remainingBurstTime = Bt;
    }

    public void setRemainingQuantumTime(int q1) {
        this.remainingQuantumTime = q1;
    }

    public void setBurstTime(int Bt) {
        this.burstTime = Bt;
    }

    public void setTurnaroundTime(int Bt) {
        this.turnaroundTime = Bt;
    }

    public void setCompleteTime(int Bt) {
        this.completeTime = Bt;
    }

    public void setWaitingTime(int Bt) {
        this.waitingTime = Bt;
    }

    public void setPriority(int Bt) {
        this.priority = Bt;
    }

    public void setArrivalTime(int Bt) {
        this.arrivalTime = Bt;
    }

    public void setQuantumTime(int q1) {
        this.quantumTime = q1;
    }

    public void setProcessName(String q1) {
        this.processName = q1;
    }

}