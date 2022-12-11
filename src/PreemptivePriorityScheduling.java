public class PreemptivePriorityScheduling {
    private final int numOfProcesses;
    //    private final String [] process;
//    private final int [] arrivalTime;
//    private final int [] burstTime;
    private int[] mainBurstTime;
    //    private final int [] executionTime;
//    private final int [] priority;
    private int[] mainPriority;
    //    private final int [] turnAroundTime;
//    private final int [] waitingTime;
//    private final int [] flag;
    private int totalExecutionTime = 0;
    private float averageWaiting = 0, averageTAT = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
    private Memory memory;

    PreemptivePriorityScheduling(Memory memory, int numOfProcesses) {
        this.memory = memory;
        this.numOfProcesses = numOfProcesses;
        mainPriority = new int[numOfProcesses];
        mainBurstTime = new int[numOfProcesses];
    }

    public void prepare() {
        for (int i = 0; i < numOfProcesses; i++) {
            mainBurstTime[i] = memory.processes.get(i).getBurstTime();
            mainPriority[i] = memory.processes.get(i).getPriority();
        }
    }

    public void preemptivePriority() {
        prepare();
        int cnt = 0, tot = 0;
        while (true) {
            int min = Integer.MAX_VALUE, idx = numOfProcesses;

            if (tot == numOfProcesses)
                break;

            for (int i = 0; i < numOfProcesses; ++i) {
                if (memory.processes.get(i).getArrivalTime() <= cnt && !memory.processes.get(i).finished && memory.processes.get(i).getPriority() <= min) {
                    min = memory.processes.get(i).getPriority();
                    idx = i;
                }
            }

            if (idx == numOfProcesses)
                cnt++;
            else {
                memory.processes.get(idx).setBurstTime(memory.processes.get(idx).getBurstTime()-1);
                cnt++;
                if (memory.processes.get(idx).getBurstTime() == 0) {
                    memory.processes.get(idx).setCompleteTime(cnt);
                    memory.processes.get(idx).finished=true;
                    tot++;
                }
                totalExecutionTime = memory.processes.get(idx).getCompleteTime();
            }

            if (tot != numOfProcesses && memory.processes.get(idx).getPriority() != 0)
                memory.processes.get(idx).setPriority(memory.processes.get(idx).getPriority()-1);
        }

        for (int i = 0; i < numOfProcesses; ++i) {
            memory.processes.get(i).setTurnaroundTime(memory.processes.get(i).getCompleteTime()-memory.processes.get(i).getArrivalTime());
            memory.processes.get(i).setWaitingTime(memory.processes.get(i).getTurnaroundTime()-mainBurstTime[i]);
            totalTurnaroundTime += memory.processes.get(i).getTurnaroundTime();
            totalWaitingTime += memory.processes.get(i).getWaitingTime();
        }
        averageTAT = totalTurnaroundTime / numOfProcesses;
        averageWaiting = totalWaitingTime / numOfProcesses;
        print();
    }

    public void print() {
        System.out.println("\nPreemptive Priority Scheduling Result:");
        System.out.println("Process" + '\t' + "AT" + '\t' + "BT" + '\t' + "P" + "   WT" + '\t' + "TAT");

        for (int i = 0; i < numOfProcesses; ++i)
            System.out.println(memory.processes.get(i).getProcessName() + '\t' + '\t'
                    + memory.processes.get(i).getArrivalTime() + '\t' +
                    mainBurstTime[i] + '\t' + mainPriority[i] + '\t' + memory.processes.get(i).getWaitingTime()
                    + '\t' + memory.processes.get(i).getTurnaroundTime());

        int temp;
        String tmp;

        for (int i = 0; i < numOfProcesses; ++i) {
            for (int j = i + 1; j < numOfProcesses; ++j) {
                if (memory.processes.get(j).getCompleteTime() < memory.processes.get(i).getCompleteTime()) {

                    temp = memory.processes.get(i).getCompleteTime();
                    memory.processes.get(i).setCompleteTime(memory.processes.get(j).getCompleteTime());
                    memory.processes.get(j).setCompleteTime(temp);

                    temp = memory.processes.get(i).getArrivalTime();
                    memory.processes.get(i).setArrivalTime(memory.processes.get(j).getArrivalTime());
                    memory.processes.get(j).setArrivalTime(temp);


                    tmp = memory.processes.get(i).getProcessName();
                    memory.processes.get(i).setProcessName(memory.processes.get(j).getProcessName());
                    memory.processes.get(j).setProcessName(tmp);
                }
            }
        }

        for (int i = 0; i < numOfProcesses; ++i)
            System.out.print("\n" + memory.processes.get(i).getArrivalTime() + " (" + memory.processes.get(i).getProcessName() + ") " +
                    memory.processes.get(i).getCompleteTime());

        System.out.println("\n\nAverage Waiting Time = " + (averageWaiting) + "\nAverage Turnaround Time = " + (averageTAT) + "\nExecution Time = " + totalExecutionTime);
    }
}