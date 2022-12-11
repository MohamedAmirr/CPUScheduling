import java.util.ArrayList;
import java.util.List;

public abstract class Scheduling {
    private final List<processTime> timeline;

    public Scheduling() {
        timeline = new ArrayList();
    }

//    public double getAverageWaitingTime() {
//        double avg = 0.0;
//        for (Process row : rows) {
//            avg += row.getWaitingTime();
//        }
//        return avg / rows.size();
//    }

//    public double getAverageTurnAroundTime() {
//        double avg = 0.0;
//        for (Process row : rows) {
//            avg += row.getTurnaroundTime();
//        }
//        return avg / rows.size();
//    }

    public processTime getTime(Process row) {
        for (processTime p : timeline) {
            if (row.getProcessName().equals(p.getProcessName())) {
                return p;
            }
        }
        return null;
    }

    public List<processTime> getTime() {
        return timeline;
    }

    public abstract void process();
}
