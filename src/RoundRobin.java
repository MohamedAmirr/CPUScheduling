
//Public class for Round Robin algorithm
@SuppressWarnings("ReassignedVariable")
public class RoundRobin {
    //The Scanner is used to get input from the user

    //function round robin
    public void round_robin(Memory memory,int NumOfProcess, int timer, int timeQuantum, int[] TempBurstTime) {
        int NumOfIndex = 0;
        float AverageWaiting = 0.0f, AverageTurnAroundTime = 0.0f;
        //To get Time Quantum from user and save it in int variable
        int [] Queue = new int[NumOfProcess];

        //Initializing the queue
        for(int i = 0; i < NumOfProcess; i++)
        {
            Queue[i] = 0;
        }
        Queue[0] = 1;

        while (true) {
            boolean flag = true;
            for (int i = 0; i < NumOfProcess; i++) {
                if (TempBurstTime[i] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                break;
            }
            for (int i = 0; (i < NumOfProcess) && (Queue[i] != 0); i++) {
                int Count = 0;
                while ((Count < timeQuantum) && (TempBurstTime[Queue[0] - 1] > 0)) {
                    TempBurstTime[Queue[0] - 1] -= 1;
                    timer += 1;
                    Count++;

                    //Updating the ready queue until all the processes arrive
                    checkNewArrival(timer, memory, NumOfProcess, NumOfIndex, Queue);
                }
                if ((TempBurstTime[Queue[0] - 1] == 0) && (!memory.processes.get(Queue[0]-1).finished)) {
                    //turn currently stores exit times
                    memory.processes.get(Queue[0]-1).setTurnaroundTime(timer);
                    memory.processes.get(Queue[0]-1).finished=true;
                }
                //checks weather or not CPU is idle(idle mean the CPU has completed all tasks it is idle)
                boolean idle = true;
                if (Queue[NumOfProcess - 1] == 0) {
                    for (int k = 0; k < NumOfProcess && Queue[k] != 0; k++) {
                        if (!memory.processes.get(Queue[k]-1).finished) {
                            idle = false;
                            break;
                        }
                    }
                } else {
                    idle = false;
                }

                if (idle) {
                    timer++;
                    checkNewArrival(timer, memory, NumOfProcess, NumOfIndex, Queue);
                }

                //Maintaining the entries of processes after each process in the ready Queue
                QueueMaintain(Queue, NumOfProcess);
            }
        }
        //For loop to calculate Turn Around Time and Waiting Time
        for (int i = 0; i < NumOfProcess; i++) {
            memory.processes.get(i).setTurnaroundTime(memory.processes.get(i).getTurnaroundTime()-memory.processes.get(i).getArrivalTime());
            memory.processes.get(i).setWaitingTime(memory.processes.get(i).getTurnaroundTime()-memory.processes.get(i).getBurstTime());
        }
        //To print all result
        //(\t)To give space between the columns
        System.out.print("process Number \t Arrival Time \t Burst Time \t Waiting Time \t TurnAround Time" + "\n");
        for (int i = 0; i < NumOfProcess; i++) {
            System.out.print(i + 1 + " \t  \t  \t  \t    " + memory.processes.get(i).getArrivalTime() + " \t  \t  \t  \t  " + memory.processes.get(i).getBurstTime() + " \t  \t  \t  \t " + memory.processes.get(i).getWaitingTime() + "  \t  \t  \t " + memory.processes.get(i).getTurnaroundTime() + "\n");
        }
        //For loop to calculate  Average Waiting and Average Turn Around Time
        for (int i = 0; i < NumOfProcess; i++) {
            AverageWaiting += memory.processes.get(i).getWaitingTime();
            AverageTurnAroundTime += memory.processes.get(i).getTurnaroundTime();
        }
        //To print result of Average Waiting and Average Turn Around Time
        System.out.println("Average wait time : " + (AverageWaiting / NumOfProcess) + "\nAverage Turn Around Time : " + (AverageTurnAroundTime / NumOfProcess));
        //Call function to print index of Processes execution order
        orderProcesses(memory, NumOfProcess, timeQuantum);
    }

    //Function to update processes in Queue when we know arrival time to each process
    public static void QueueUpdate(int[] queue, int n, int NumOfIndex) {
        int FirstIndex = -1;
        for (int i = 0; i < n; i++) {
            if (queue[i] == 0) {
                FirstIndex = i;
                break;
            }
        }
        if (FirstIndex == -1) {
            return;
        }
        queue[FirstIndex] = NumOfIndex + 1;
    }

    //Function to check the Arrival time and new arrival time of processes
    public static void checkNewArrival(int timer,Memory memory, int n, int NumOfIndex, int[] queue) {
        if (timer <= memory.processes.get(n-1).getArrivalTime()) {
            boolean NewArrival = false;
            for (int j = (NumOfIndex + 1); j < n; j++) {
                if (memory.processes.get(j).getArrivalTime() <= timer) {
                    if (NumOfIndex < j) {
                        NumOfIndex = j;
                        NewArrival = true;
                    }
                }
            }
            //adds the index of the arriving process(if any new index)
            if (NewArrival) {
                //Call function to update queue if we add new arrival time
                QueueUpdate(queue, n, NumOfIndex);
            }
        }
    }

    //Function to swap between element in the Queue
    public static void QueueMaintain(int[] queue, int n) {

        for (int i = 0; (i < n - 1) && (queue[i + 1] != 0); i++) {
            int temp = queue[i];
            queue[i] = queue[i + 1];
            queue[i + 1] = temp;
        }
    }

    //Function to sort the array Queue[] on the basis of the array freq[]
    public static void merge(int[] Queue, int[] freq, int i, int mid, int j) {
        int[] TempQueue = new int[j - i + 1];
        int TemP1 = mid + 1, index = -1;

        while (i <= mid && TemP1 <= j) {

            // If Queue[i]th is less than Queue[Temp1]th process
            if (freq[Queue[i]] <= freq[Queue[TemP1]]) {
                TempQueue[++index] = Queue[i++];
            }

            // Otherwise
            else {
                TempQueue[++index] = Queue[TemP1++];
            }
        }

        // Add the left half to TempQueue[]
        while (i <= mid) {
            TempQueue[++index] = Queue[i++];
        }

        // Add right half to TempQueue[]
        while (TemP1 <= j) {
            TempQueue[++index] = Queue[TemP1++];
        }

        // Copy the TempQueue[] array to Queue[] array
        int ind = index;
        for (index = ind; index >= 0; index--) {
            Queue[j--] = TempQueue[index];
        }
    }

    //Utility function to sort the array Queue[] on the basis of freq[]
    public static void divide(int[] Queue, int[] freq, int i, int j) {
        // Base Case
        if (i >= j)
            return;

        // Divide array into 2 parts
        int mid = i / 2 + j / 2;

        // Sort the left array
        divide(Queue, freq, i, mid);

        // Sort the right array
        divide(Queue, freq, mid + 1, j);

        // Merge the sorted arrays
        merge(Queue, freq, i, mid, j);
    }

    //Function to find the order of processes in which execution occurs
    public static void orderProcesses(Memory memory, int N, int q) {
        // Store the frequency
        int[] freq = new int[N];

        // Find elements in array freq[]
        for (int i = 0; i < N; i++) {
            freq[i] = (memory.processes.get(i).getBurstTime() / q);
            if (memory.processes.get(i).getBurstTime() % q > 0)
                freq[i] += 1;
        }

        // Store the order of completion of processes
        int[] order = new int[N];

        // Initialize order[i] as i
        for (int i = 0; i < N; i++) {
            order[i] = i;
        }

        // Function Call to find the order of execution
        divide(order, freq, 0, N - 1);

        // Print order of completion of processes
        System.out.println("Process execution order :");
        for (int i = 0; i < N; i++) {
            System.out.print(order[i] + "\n");
        }
    }
}
