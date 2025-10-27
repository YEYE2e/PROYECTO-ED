import java.util.*;

public class ComparadorPrioridad implements Comparator<Tarea> {
    @Override
    public int compare(Tarea t1, Tarea t2) {
        return Integer.compare(t1.getPrioridad().getValor(), t2.getPrioridad().getValor());
    }
}
