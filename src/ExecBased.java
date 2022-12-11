import java.util.Comparator;

public class ExecBased implements Comparator<Process> {
    @Override
    public int compare(Process process, Process t1) {
        if(process.getRemainingBurstTime()>t1.getRemainingBurstTime()){
            return 1;
        }
        else if(t1.getRemainingBurstTime()>process.getRemainingBurstTime()){
            return -1;
        }
        else return 0;
    }
}
