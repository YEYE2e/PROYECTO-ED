import java.time.*;
import java.io.*;

public class Tarea implements Serializable {
    private static final long serialVersionUID = 1L;
    private String descripcion;
    private transient LocalDate fechaMaxima;
    private transient LocalDate fechaCompletado;
    private Prioridad prioridad;
    private boolean completada;

    public Tarea(String descripcion, LocalDate fechaMaxima, Prioridad prioridad) {
        this.descripcion = descripcion;
        this.fechaMaxima = fechaMaxima;
        this.prioridad = prioridad;
        this.completada = false;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setFechaMaxima(LocalDate fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }
    public LocalDate getFechaMaxima() {
        return fechaMaxima;
    }
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
    public boolean isCompletada() {
        return completada;
    }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    public Prioridad getPrioridad() {
        return prioridad;
    }
    public void marcarCompletada() {
        this.completada = true;
        this.fechaCompletado = LocalDate.now();
    }
    public void desmarcarCompletada() {
        this.completada = false;
        this.fechaCompletado = null;
    }
    public LocalDate getFechaCompletado() {
        return fechaCompletado;
    }
    public boolean esVencida() {
        return LocalDate.now().isAfter(fechaMaxima);
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(fechaMaxima.toString());
        oos.writeBoolean(fechaCompletado != null);
        if (fechaCompletado != null) {
            oos.writeObject(fechaCompletado.toString());
        }
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.fechaMaxima = LocalDate.parse((String) ois.readObject());
        boolean hayFechaCompletado = ois.readBoolean();
        if (hayFechaCompletado) {
            this.fechaCompletado = LocalDate.parse((String) ois.readObject());
        } else {
            this.fechaCompletado = null;
        }
    }
    @Override
    public String toString() {
        String estado = completada ? "[X]" : "[ ]";
        return estado + " " + descripcion + " - " + prioridad + " (Vence: " + fechaMaxima + ")";
    }
}