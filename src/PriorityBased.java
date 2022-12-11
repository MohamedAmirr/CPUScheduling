import java.util.Comparator;

public class PriorityBased implements Comparator<Process> {

    @Override
    public int compare(Process process, Process t1) {
        if(process.getPriority()>t1.getPriority()){
            return 1;
        }
        else if(t1.getPriority()>process.getPriority()){
            return -1;
        }
        else return 0;
    }
}
