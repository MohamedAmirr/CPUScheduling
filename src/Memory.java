import java.util.*;

public class Memory {
    public Queue<Process> Fcfs = new LinkedList<>();
    public PriorityQueue<Process> Priority;
    public PriorityQueue<Process> Exec;
    public int time = 0;
    public Vector<Process> processes = new Vector<>();
    public Map<String,Integer>history = new HashMap<>() ;
    Map.Entry<String ,Integer>lastEntry=null;
    Vector<String>order=new Vector<>();

    Memory() {
        Priority = new PriorityQueue<>(new PriorityBased());
        Exec = new PriorityQueue<>(new ExecBased());
    }

}
