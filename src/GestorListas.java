import java.util.*;
import java.io.*;
import java.time.*;
public class GestorListas implements Serializable {
    private static final long serialVersionUID = 3L;
    private List<ToDos> listas;
    public GestorListas() {
        this.listas = new ArrayList<>();
    }
    public void crearLista(String nombre) {
        if(this.buscarLista(nombre) != null) {
            System.out.println("Ya existe una lista con el nombre '" + nombre);
            return;
        }
        ToDos nuevaLista = new ToDos(nombre);
        this.listas.add(nuevaLista);
        System.out.println("Lista '" + nombre + "' creada.");
    }
    public ToDos buscarLista(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        String nombreBuscadoNormalizado = nombre.replaceAll("\\s+", "").toLowerCase();
        for (ToDos listaActual : this.listas) {
            String nombreListaActualNormalizado = listaActual.getNombre().replaceAll("\\s+", "").toLowerCase();
            if (nombreListaActualNormalizado.equals(nombreBuscadoNormalizado)) {
                return listaActual;
            }
        }
        return null;
    }
    public void agregarLista(ToDos lista) {
        this.listas.add(lista);
    }

    public List<ToDos> getListas() {
        return listas;
    }
    public List<Tarea> recuperarTodasLasCompletadas() {
        LinkedList<Tarea> todasCompletadas = new LinkedList<>();
        for (ToDos lista : this.listas) {
            todasCompletadas.addAll(lista.getTareasCompletadas());
        }
        return todasCompletadas;
    }

    public List<Tarea> recuperarPendientesPorPrioridad(Prioridad prioridad) {
        LinkedList<Tarea> tareasFiltradas = new LinkedList<>();
        for (ToDos lista : this.listas) {
            for (Tarea tarea : lista.getTareasPendientes()) {
                if (tarea.getPrioridad() == prioridad) {
                    tareasFiltradas.add(tarea);
                }
            }
        }
        return tareasFiltradas;
    }
    public ToDos buscarListaQueContiene(Tarea tarea) {
        for (ToDos lista : this.listas) {
            if (lista.getTareasPendientes().contains(tarea)) {
                return lista;
            }
        }
        return null;
    }
    public boolean editarNombreLista(ToDos listaAEditar, String nuevoNombre) {
        ToDos listaExistente = this.buscarLista(nuevoNombre);
        if (listaExistente != null && listaExistente != listaAEditar) {
            System.out.println("Error: Ya existe otra lista con el nombre '" + nuevoNombre + "'.");
            return false;
        }
        listaAEditar.setNombre(nuevoNombre);
        System.out.println("Nombre de la lista actualizado a: " + nuevoNombre);
        return true;
    }
    public boolean eliminarLista(ToDos listaAEliminar) {
        if (listaAEliminar == null) {
            System.out.println("No se puede eliminar una lista nula.");
            return false;
        }
        boolean exito = this.listas.remove(listaAEliminar);
        if (exito) {
            System.out.println("Lista '" + listaAEliminar.getNombre() + "' y todas sus tareas han sido eliminadas.");
        } else {
            System.out.println("Error: La lista '" + listaAEliminar.getNombre() + "' no se pudo encontrar para eliminar.");
        }
        return exito;
    }
    public void moverTarea(Tarea tareaAMover, ToDos listaOrigen, ToDos listaDestino) {
        if (tareaAMover == null || listaOrigen == null || listaDestino == null) {
            System.out.println("Información incompleta para mover la tarea.");
            return;
        }
        boolean exitoRemover = listaOrigen.removerTareaPendiente(tareaAMover);

        if (exitoRemover) {
            listaDestino.agregarTarea(tareaAMover);
            System.out.println("Tarea '" + tareaAMover.getDescripcion() + "' movida de [" + listaOrigen.getNombre() + "] a [" + listaDestino.getNombre() + "].");
        } else {
            System.out.println("No se pudo encontrar la tarea en la lista de origen.");
        }
    }
    public List<Tarea> recuperarPendientesVencidas() {
        List<Tarea> vencidas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (ToDos lista : this.listas) {
            for (Tarea tarea : lista.getTareasPendientes()) {
                if (tarea.getFechaMaxima().isBefore(hoy)) {
                    vencidas.add(tarea);
                }
            }
        }
        return vencidas;
    }
    public List<Tarea> recuperarPendientesParaHoy() {
        List<Tarea> paraHoy = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (ToDos lista : this.listas) {
            for (Tarea tarea : lista.getTareasPendientes()) {
                if (tarea.getFechaMaxima().isEqual(hoy)) {
                    paraHoy.add(tarea);
                }
            }
        }
        return paraHoy;
    }
    public int contarTodasLasPendientes() {
        int contador = 0;
        for (ToDos lista : this.listas) {
            contador += lista.getTareasPendientes().size();
        }
        return contador;
    }
}
