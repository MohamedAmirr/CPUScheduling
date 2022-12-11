import java.util.*;

public class SJF {
    public void SJF(int n, int[] k, Memory memory) {
        Scanner in = new Scanner(System.in);
        int start=0;
        int total = 0;
        while (true) {
            int min = 99, c = n;
            if (total == n)
                break;

            for (int i = 0; i < n; i++) {
                if ((memory.processes.get(i).getArrivalTime() <= start) &&
                        (!memory.processes.get(i).finished) && (memory.processes.get(i).getBurstTime() < min)) {
                    min = memory.processes.get(i).getBurstTime();
                    c = i;
                }
            }

            if (c == n)
                start++;
            else {
                memory.processes.get(c).setBurstTime(memory.processes.get(c).getBurstTime() - 1);
                start++;
                if (memory.processes.get(c).getBurstTime() == 0) {
                    memory.processes.get(c).setCompleteTime(start);
                    memory.processes.get(c).finished = true;
                    total++;
                }
            }
        }

        float avgwt = 0, avgta = 0;

        for (int i = 0; i < n; i++) {
            memory.processes.get(i).setTurnaroundTime(memory.processes.get(i).getCompleteTime() - memory.processes.get(i).getArrivalTime());
            memory.processes.get(i).setWaitingTime(memory.processes.get(i).getTurnaroundTime() - k[i]);
            avgwt += memory.processes.get(i).getWaitingTime();
            avgta += memory.processes.get(i).getTurnaroundTime();
        }

        System.out.print("process Number \t Arrival Time \t Burst Time \t CompleteTime \tTurnAround Tim \tWaiting Time " + "\n");


        for (int i = 0; i < n; i++) {

            System.out.print(i + 1 + " \t  \t  \t  \t    " + memory.processes.get(i).getArrivalTime()
                    + " \t  \t  \t  \t " + k[i] + " \t  \t  \t  " + memory.processes.get(i).getCompleteTime()
                    + " \t  \t  \t  " + memory.processes.get(i).getTurnaroundTime()
                    + " \t  \t  \t " + memory.processes.get(i).getWaitingTime() + "\n");
        }
        System.out.println("\nAverage Turn Around Time is " + (avgta / n));
        System.out.println("Average Waiting Time is " + (avgwt / n));

    }

}