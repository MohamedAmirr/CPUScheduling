import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("1. Preemptive Shortest Job First\n" +
                "2. Round Robin\n" +
                "3. Preemptive Priority Scheduling\n" +
                "4. AG Scheduling\n" +
                "5. Exit");
        System.out.print("Enter Choice Number: ");
        int choice = 0;
        while (choice != 5) {
            choice = in.nextInt();
            if (choice == 1) {
                System.out.print("Enter Number of Processes: ");
                int n = in.nextInt();
                int[] k = new int[n];
                Memory memory = new Memory();
                System.out.println("Enter Context Switching: ");
                int contextSwitching = in.nextInt();
                memory.context=contextSwitching;
                for (int i = 0; i < n; i++) {
                    System.out.print("Enter Process Name: ");
                    String name = in.next();
                    System.out.print("Enter Arrival Time: ");
                    int arrTime = in.nextInt();
                    System.out.print("Enter Burst Time: ");
                    int BurstTime = in.nextInt();
                    Process pc = new Process(name, arrTime, BurstTime);
                    memory.processes.add(pc);
                    k[i] = BurstTime;
                }
                SJF sjf = new SJF();
                sjf.SJF(n, k, memory);
                System.out.println("\nEnter (5) to Exit Program or Enter Another Choice Number: ");
            } else if (choice == 2) {
                int timer = 1000000000;
                System.out.print("Enter number of processes : ");
                int n = in.nextInt();
                System.out.print("Enter Time Quantum: ");
                int timeQuantum = in.nextInt();
                Memory memory = new Memory();
                int[] TempBurstTime = new int[n];
                for (int i = 0; i < n; i++) {
                    System.out.print("Enter Process Name: ");
                    String name = in.next();
                    System.out.print("Enter Arrival Time: ");
                    int arrTime = in.nextInt();
                    System.out.print("Enter Burst Time: ");
                    int BurstTime = in.nextInt();
                    Process pc = new Process(name, arrTime, BurstTime);
                    pc.setQuantumTime(timeQuantum);
                    timer = Math.min(timer, arrTime);
                    TempBurstTime[i] = BurstTime;
                    memory.processes.add(pc);
                    memory.history.put(name, timeQuantum);
                }
                Collections.sort(
                        memory.processes, new Comparator<Process>() {
                            @Override
                            public int compare(Process process, Process t1) {
                                if (process.getArrivalTime() > t1.getArrivalTime()) {
                                    return 1;
                                } else if (process.getArrivalTime() < t1.getArrivalTime()) {
                                    return -1;
                                } else return 0;
                            }
                        }
                );
                RoundRobin roundRobin = new RoundRobin();
                roundRobin.round_robin(memory, n, timer, timeQuantum, TempBurstTime);
                System.out.println("\nEnter (5) to Exit Program or Enter Another Choice Number: ");
            } else if (choice == 3) {
                System.out.print("Enter Number Of Processes: ");
                int n = in.nextInt();
                Memory memory = new Memory();
                for (int i = 0; i < n; i++) {
                    System.out.print("Enter Process Name: ");
                    String name = in.next();
                    System.out.print("Enter Arrival Time: ");
                    int arrTime = in.nextInt();
                    System.out.print("Enter Burst Time: ");
                    int BurstTime = in.nextInt();
                    System.out.print("Enter Process Priority: ");
                    int ProcessPri = in.nextInt();
                    Process pc = new Process(name, arrTime, BurstTime, ProcessPri);
                    memory.processes.add(pc);
                }
                Collections.sort(
                        memory.processes, new Comparator<Process>() {
                            @Override
                            public int compare(Process process, Process t1) {
                                if (process.getArrivalTime() > t1.getArrivalTime()) {
                                    return 1;
                                } else if (process.getArrivalTime() < t1.getArrivalTime()) {
                                    return -1;
                                } else return 0;
                            }
                        }
                );
                PreemptivePriorityScheduling preemptivePriorityScheduling = new PreemptivePriorityScheduling(memory, n);
                preemptivePriorityScheduling.preemptivePriority();
                System.out.println("\nEnter (5) to Exit Program or Enter Another Choice Number: ");
            } else if (choice == 4) {
                System.out.print("Enter Number Of Processes: ");
                int n = in.nextInt();
                Memory memory = new Memory();
                Vector<Process> processes = new Vector<>();
                for (int i = 0; i < n; i++) {
                    System.out.print("Enter Process Name: ");
                    String name = in.next();
                    System.out.print("Enter Arrival Time: ");
                    int arrTime = in.nextInt();
                    System.out.print("Enter Burst Time: ");
                    int BurstTime = in.nextInt();
                    System.out.print("Enter Process Priority: ");
                    int ProcessPri = in.nextInt();
                    System.out.print("Enter Time Quantum: ");
                    int timeQuantum = in.nextInt();
                    Process pc = new Process(name, arrTime, BurstTime, ProcessPri, timeQuantum);
                    processes.add(pc);
                    memory.processes.add(pc);
                    memory.history.put(name, timeQuantum);
                }
                Collections.sort(
                        memory.processes, new Comparator<Process>() {
                            @Override
                            public int compare(Process process, Process t1) {
                                if (process.getArrivalTime() > t1.getArrivalTime()) {
                                    return 1;
                                } else if (process.getArrivalTime() < t1.getArrivalTime()) {
                                    return -1;
                                } else return 0;
                            }
                        }
                );
                Collections.sort(
                        processes, new Comparator<Process>() {
                            @Override
                            public int compare(Process process, Process t1) {
                                if (process.getArrivalTime() > t1.getArrivalTime()) {
                                    return 1;
                                } else if (process.getArrivalTime() < t1.getArrivalTime()) {
                                    return -1;
                                } else return 0;
                            }
                        }
                );

                memory.Fcfs.add(memory.processes.firstElement());
                memory.Priority.add(memory.processes.firstElement());
                memory.Exec.add(memory.processes.firstElement());
                if (memory.processes.firstElement().getArrivalTime() > memory.time)// if first arrival time > 0
                    memory.time = memory.processes.firstElement().getArrivalTime();
                memory.processes.remove(0);
                for (var entry : memory.history.entrySet()) {
                    memory.lastEntry = entry;
                }
                System.out.printf("\n");

                agScheduling ag = new agScheduling(memory);
                ag.print();
                ag.go();
                for (var i : memory.order) {
                    System.out.println(i);
                }
                Formatter fmt = new Formatter();
                fmt.format("%15s %15s %15s", "Process Name", "Waiting Time", "TurnAroundTime");
                System.out.println(fmt);
                System.out.print("\n");
                int sumWaitingTime = 0, sumTurnAroundTime = 0;
                for (int i = 0; i < n; i++) {
                    Formatter fmt1 = new Formatter();
                    fmt1.format("%15s %15s %15s", processes.get(i).getProcessName(), processes.get(i).getWaitingTime(), processes.get(i).getTurnaroundTime());
                    System.out.println(fmt1);
                    sumWaitingTime += processes.get(i).getWaitingTime();
                    sumTurnAroundTime += processes.get(i).getTurnaroundTime();
                    System.out.print("\n");
                }
                System.out.println("Avg Waiting Time: " + (double) sumWaitingTime / (double) n);
                System.out.println("Avg Turnaround Time: " + (double) sumTurnAroundTime / (double) n);
                System.out.println("\nEnter (5) to Exit Program or Enter Another Choice Number: ");
            }
        }
    }
}

//6 4 7
//0 1 2