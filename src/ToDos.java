import java.time.*;
import java.util.*;
import java.io.*;
public class ToDos implements Serializable {
    private static final long serialVersionUID = 2L;
    private String nombre;
    private transient LocalDate fechaCreacion;
    private LinkedList<Tarea> tareasPendientes;
    Deque<Tarea> tareasCompletadas;

    public ToDos(String nombre) {
        this.nombre = nombre;
        this.fechaCreacion = LocalDate.now();
        this.tareasPendientes = new LinkedList<>();
        this.tareasCompletadas = new ArrayDeque<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void agregarTarea(Tarea tarea) {
        this.tareasPendientes.add(tarea);
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LinkedList<Tarea> getTareasPendientes() {
        return tareasPendientes;
    }
    public Deque<Tarea> getTareasCompletadas() {
        return tareasCompletadas;
    }
    public void marcarTodasCompletadas() {
        System.out.println("Marcando todas las tareas de '" + this.nombre + "' como completadas...");
        for (Tarea tarea : this.tareasPendientes) {
            tarea.marcarCompletada();
        }
        this.tareasCompletadas.addAll(this.tareasPendientes);
        this.tareasPendientes.clear();
    }
    public void marcarComoCompletada(Tarea tarea) {
        boolean encontrada = this.tareasPendientes.remove(tarea);
        if (encontrada) {
            tarea.marcarCompletada();
            tareasCompletadas.push(tarea);
            System.out.println("Tarea: '" + tarea.getDescripcion() + "' marcada como completada.");
        }else {
            System.out.println("Tarea: '" + tarea.getDescripcion() + "' no encontrada en pendientes.");
        }
    }
    public LinkedList<Tarea> getTareasPendientesPorPrioridad() {
        System.out.println("Tareas pendientes en '" + nombre + "':");
        LinkedList<Tarea> tareasPendientesPorPrioridad = new LinkedList<>(this.tareasPendientes);
        tareasPendientesPorPrioridad.sort(new ComparadorPrioridad());
        return tareasPendientesPorPrioridad;
    }
    public List<Tarea> filtrarTareasPendientes(Prioridad prioridadFiltro, String textoFiltro) {
        LinkedList<Tarea> resultados = new LinkedList<>();
        String textoBusqueda = null;
        if (textoFiltro != null && !textoFiltro.trim().isEmpty()) {
            textoBusqueda = textoFiltro.toLowerCase().trim();
        }
        for (Tarea tarea : this.tareasPendientes) {
            boolean pasaPrioridad = (prioridadFiltro == null) || (tarea.getPrioridad() == prioridadFiltro);
            boolean pasaTexto = (textoBusqueda == null) || (tarea.getDescripcion().toLowerCase().contains(textoBusqueda));
            if (pasaPrioridad && pasaTexto) {
                resultados.add(tarea);
            }
        }
        resultados.sort(new ComparadorPrioridad());
        return resultados;
    }
    public List<Tarea> getCompletadasDespuesDe(LocalDate fechaInicio) {
        LinkedList<Tarea> resultados = new LinkedList<>();
        for (Tarea tarea : this.tareasCompletadas) {
            LocalDate fechaComp = tarea.getFechaCompletado();
            if (fechaComp != null && fechaComp.isAfter(fechaInicio)) {
                resultados.add(tarea);
            }
        }
            resultados.sort(new ComparadorPrioridad());
        return resultados;
    }
    public void regresarTareaAPendiente(Tarea tarea) {
        boolean encontrada = this.tareasCompletadas.remove(tarea);
        if (encontrada) {
            tarea.desmarcarCompletada();
            this.tareasPendientes.add(tarea);
            System.out.println("Tarea regresada a pendientes: " + tarea.getDescripcion());
        } else {
            System.out.println("Tarea no encontrada en el historial");
        }
    }
    public boolean removerTareaPendiente(Tarea tarea) {
        boolean exito = this.tareasPendientes.remove(tarea);
        if (exito) {
            System.out.println("Tarea removida: " + tarea.getDescripcion());
        } else {
            System.out.println("Tarea no encontrada en pendientes");
        }
        return exito;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(fechaCreacion.toString());
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.fechaCreacion = LocalDate.parse((String) ois.readObject());
    }
    @Override
    public String toString() {
        return "Lista: '" + this.nombre + "'" +
                " (Creada: " + this.fechaCreacion + ")" +
                " - [Pendientes: " + this.tareasPendientes.size() +
                ", Completadas: " + this.tareasCompletadas.size() + "]";
    }
}
