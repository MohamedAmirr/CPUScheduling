public class SJF {
    public void SJF(Memory memory) {
        int total = 0;
        String processRunning = null;
        while (true) {
            int min = Integer.MAX_VALUE, c = memory.getNumOfProcesses();
            if (total == memory.getNumOfProcesses())
                break;
            Process process = null;
            for (int i = 0; i < memory.getNumOfProcesses(); i++) {
                if ((memory.processes.get(i).getArrivalTime() <= memory.time)
                        && (!memory.processes.get(i).finished)
                        && (memory.processes.get(i).getRemainingBurstTime() < min)) {
                    min = memory.processes.get(i).getRemainingBurstTime();
                    c = i;
                    process = memory.processes.get(i);
                }
            }
            if (process != null && processRunning != null && processRunning != process.getProcessName()) {
                memory.currContext += memory.context;
            }
            if(c==memory.getNumOfProcesses()){
                memory.time++;
            }
            else{
                memory.processes.get(c).setRemainingBurstTime(memory.processes.get(c).getRemainingBurstTime() - 1);
                processRunning = memory.processes.get(c).getProcessName();
                memory.time++;
                if (memory.processes.get(c).getRemainingBurstTime() == 0) {
                    memory.processes.get(c).setCompleteTime(memory.time + memory.currContext + memory.context);
                    memory.processes.get(c).finished = true;
                    total++;
                }
            }
        }
        float avgwt = 0, avgta = 0;

        for (int i = 0; i < memory.getNumOfProcesses(); i++) {
            memory.processes.get(i).setTurnaroundTime(memory.processes.get(i).getCompleteTime() - memory.processes.get(i).getArrivalTime());
            memory.processes.get(i).setWaitingTime(memory.processes.get(i).getTurnaroundTime() - memory.processes.get(i).getBurstTime());
            avgwt += memory.processes.get(i).getWaitingTime();
            avgta += memory.processes.get(i).getTurnaroundTime();
        }

        System.out.print("process Number \t Arrival Time \t Burst Time \t CompleteTime \tTurnAround Time \tWaiting Time " + "\n");

        for (int i = 0; i < memory.getNumOfProcesses(); i++) {

            System.out.print(i + 1 + " \t  \t  \t  \t    " + memory.processes.get(i).getArrivalTime()
                    + " \t  \t  \t  \t " + memory.processes.get(i).getBurstTime() + " \t  \t  \t  " + memory.processes.get(i).getCompleteTime()
                    + " \t  \t  \t  " + memory.processes.get(i).getTurnaroundTime()
                    + " \t  \t  \t " + memory.processes.get(i).getWaitingTime() + "\n");
        }

        System.out.println("\nAverage Turn Around Time is " + (avgta / memory.getNumOfProcesses()));
        System.out.println("Average Waiting Time is " + (avgwt / memory.getNumOfProcesses()));
        System.out.print("Context Switching: ");
        System.out.println(memory.currContext + memory.context);
    }
}