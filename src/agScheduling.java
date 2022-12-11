public class agScheduling {
    Process curr = null;
    Memory memory;

    agScheduling(Memory memory) {
        this.memory = memory;
    }

    void changeCurr(int state) {// move pointer "curr" to another process
        if (state == 0) {
            curr = memory.Fcfs.remove();
            memory.Priority.remove(curr);
            memory.Exec.remove(curr);
        } else if (state == 1) {
            curr = memory.Priority.remove();
            memory.Fcfs.remove(curr);
            memory.Exec.remove(curr);
        } else {
            curr = memory.Exec.remove();
            memory.Priority.remove(curr);
            memory.Fcfs.remove(curr);
        }
    }

    void whenCurrNull() {// move pointer "curr" to top of ready queue
        if (memory.Fcfs.isEmpty()) {
            memory.Fcfs.add(memory.processes.firstElement());
            memory.Priority.add(memory.processes.firstElement());
            memory.Exec.add(memory.processes.firstElement());
            if (memory.time < memory.processes.firstElement().getArrivalTime()) {
                memory.time = memory.processes.firstElement().getArrivalTime();
            }
            memory.processes.remove(0);
        }
        changeCurr(0);
    }

    boolean anyThingNew() {// if any new process has been arrived
        boolean ch = false;
        while (memory.processes.size() > 0 && memory.time >= memory.processes.firstElement().getArrivalTime()) {
            memory.Fcfs.add(memory.processes.firstElement());
            memory.Priority.add(memory.processes.firstElement());
            memory.Exec.add(memory.processes.firstElement());
            memory.processes.remove(0);
            ch = true;
        }
        return ch;
    }

    void returnCurr() {// return curr to the end of ready queue
        memory.Fcfs.add(curr);
        memory.Priority.add(curr);
        memory.Exec.add(curr);
    }

    void print() {// print output
        System.out.print("Quantum(");
        for (var entry : memory.history.entrySet()) {
            System.out.printf(String.valueOf(entry.getValue()));
            if (entry != memory.lastEntry) {
                System.out.print(", ");
            } else System.out.print(")");
        }
        System.out.print("\n");
    }

    void changingHistory() {// if history of quantum time changes
        if (curr.getRemainingQuantumTime() != memory.history.get(curr.getProcessName())) {
            memory.history.put(curr.getProcessName(), curr.getQuantumTime());
            print();
        }
    }

    void firstScenario() {
        returnCurr();
        curr.setQuantumTime(curr.getQuantumTime() + 2);
    }

    void secondScenario() {
        returnCurr();
        curr.setQuantumTime(curr.getQuantumTime() + ((curr.getRemainingQuantumTime() + 1) / 2));
    }

    void thirdScenario() {
        returnCurr();
        curr.setQuantumTime(curr.getQuantumTime() + curr.getRemainingQuantumTime());
    }

    void forthScenario() {
        curr.setQuantumTime(0);
    }

    private void firstStage() {
        int cost = Math.min(curr.calcQuarterOfQuantumTime(), Math.min(curr.getRemainingQuantumTime(), curr.getRemainingBurstTime()));
        curr.calcHalfOfQuantumTime();
        curr.setRemainingQuantumTime(curr.getRemainingQuantumTime() - cost);
        curr.setRemainingBurstTime(curr.getRemainingBurstTime() - cost);
        memory.time += cost;
        if (curr.getRemainingBurstTime() > 0 && curr.getRemainingQuantumTime() == 0) {
            firstScenario();
            curr = null;
        }
    }

    private void secondStage() {
        int cost = Math.min(curr.getHalfOfTimeQuantum()-curr.getQuarterOfTimeQuantum(), Math.min(curr.getRemainingQuantumTime(), curr.getRemainingBurstTime()));
        curr.setRemainingQuantumTime(curr.getRemainingQuantumTime() - cost);
        curr.setRemainingBurstTime(curr.getRemainingBurstTime() - cost);
        memory.time += cost;
        if (curr.getRemainingBurstTime() > 0 && curr.getRemainingQuantumTime() == 0) {
            firstScenario();
            curr = null;
        }
    }

    private void thirdStage() {
        boolean check = false;
        while (curr.getRemainingBurstTime() > 0 && curr.getRemainingQuantumTime() > 0) {
            curr.setRemainingQuantumTime(curr.getRemainingQuantumTime() - 1);
            curr.setRemainingBurstTime(curr.getRemainingBurstTime() - 1);
            memory.time++;

            if (anyThingNew()) {
                if (memory.Exec.size() > 0 && memory.Exec.peek().getRemainingBurstTime() < curr.getRemainingBurstTime()) {
                    changeCurr(2);
                    check = true;
                    break;
                }
            }
        }
        if (!check) {
            if (curr.getRemainingBurstTime() > 0 && curr.getRemainingQuantumTime() == 0) {
                firstScenario();
                changingHistory();
                curr = null;
            } else if (curr.getRemainingBurstTime() == 0 && curr.getRemainingQuantumTime() > 0) {
                forthScenario();
                changingHistory();
                curr = null;
            }
        }
    }


    void go() {
        while (memory.processes.size() > 0 || (memory.Fcfs.size() > 0)) {
            if (curr == null)
                whenCurrNull();
            curr.setRemainingQuantumTime(curr.getQuantumTime());
            firstStage();
            anyThingNew();
            if (curr == null) continue;
            if (curr.getRemainingBurstTime() == 0) {
                forthScenario();
                changingHistory();
                curr = null;
                continue;
            }
            if (memory.Priority.size() > 0 && memory.Priority.peek().getPriority() < curr.getPriority()) {// hn3ml move mn curr 34an fy priority a7sn
                secondScenario();
                changingHistory();
                changeCurr(1);
            } else {
                secondStage();
                anyThingNew();
                if (curr == null) continue;
                if (curr.getRemainingBurstTime() == 0) {
                    forthScenario();
                    curr = null;
                    continue;
                }
                if (memory.Exec.size() > 0 && memory.Exec.peek().getRemainingBurstTime() < curr.getRemainingBurstTime()) {
//                    changingHistory();
                    thirdScenario();// second scenario
                    changingHistory();
                    changeCurr(2);
                } else {
                    thirdStage();
                    anyThingNew();
                    if (curr == null) continue;
                    if (curr.getRemainingBurstTime() == 0) {
                        forthScenario();
                        curr = null;
                    }
                }
            }
        }
    }
}
