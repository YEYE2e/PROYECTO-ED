import java.time.format.*;
import java.util.*;
import java.time.*;
import java.io.*;

public class Main {
    public static GestorListas gestor = new GestorListas();
    public static Scanner sc = new Scanner(System.in);
    private static final String guardar = "todo.dat";

    public static void main(String[] args) {
        cargarDatos();
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número.");
                continue;
            }
            switch (opcion) {
                case 1:
                    crearLista();
                    break;
                case 2:
                    seleccionarLista();
                    break;
                case 3:
                    verTodasLasCompletadas();
                    break;
                case 4:
                    buscarGlobalPorPrioridad();
                    break;
                case 5:
                    verTareasVencidas();
                    break;
                case 6:
                    verTareasParaHoy();
                    break;
                case 0:
                    guardarDatos();
                    System.out.println("Se guardaron los datos, Gracias.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n-------------------------------------");
        System.out.println("   PANEL DE CONTROL");
        System.out.println("-------------------------------------");
        int totalListas = gestor.getListas().size();
        int totalPendientes = gestor.contarTodasLasPendientes();
        int totalVencidas = gestor.recuperarPendientesVencidas().size();
        int totalParaHoy = gestor.recuperarPendientesParaHoy().size();
        System.out.println(" Listas Totales: " + totalListas);
        System.out.println(" Tareas Pendientes (Global): " + totalPendientes);
        System.out.println(" Tareas VENCIDAS: " + totalVencidas);
        System.out.println(" Tareas para HOY: " + totalParaHoy);
        System.out.println("-------------------------------------");
        System.out.println("   GESTOR DE LISTAS ToDos");
        System.out.println("-------------------------------------");
        System.out.println("1. Crear nueva lista");
        System.out.println("2. Seleccionar una lista existente");
        System.out.println("3. Ver TODAS las tareas completadas (Global)");
        System.out.println("4. Buscar PENDIENTES por prioridad (Global)");
        System.out.println("5. Ver tareas PENDIENTES VENCIDAS (Global)");
        System.out.println("6. Ver tareas PENDIENTES PARA HOY (Global)");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void crearLista() {
        System.out.print("Ingresa el nombre de la nueva lista: ");
        String nombre = sc.nextLine();
        gestor.crearLista(nombre);

    }

    private static void verTodasLasCompletadas() {
        System.out.println("\n--- Historial Global de Tareas Completadas ---");
        List<Tarea> todas = gestor.recuperarTodasLasCompletadas();
        if (todas.isEmpty()) {
            System.out.println("No hay ninguna tarea completada en el sistema.");
            return;
        }
        for (Tarea tarea : todas) {
            System.out.println(tarea);
        }
    }

    private static void buscarGlobalPorPrioridad() {
        System.out.println("\n--- Búsqueda Global por Prioridad ---");
        System.out.print("Ingresa la prioridad a buscar (ALTA, MEDIA, BAJA): ");
        try {
            Prioridad prio = Prioridad.valueOf(sc.nextLine().toUpperCase());
            List<Tarea> tareas = gestor.recuperarPendientesPorPrioridad(prio);

            if (tareas.isEmpty()) {
                System.out.println("No se encontraron tareas pendientes con prioridad " + prio);
                return;
            }
            System.out.println("--- Tareas Pendientes encontradas con prioridad " + prio + " ---");
            for (Tarea tarea : tareas) {
                ToDos listaOrigen = gestor.buscarListaQueContiene(tarea);
                if (listaOrigen != null) {
                    System.out.println(tarea + " (En lista: " + listaOrigen.getNombre() + ")");
                } else {
                    System.out.println(tarea);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Prioridad no válida.");
        }
    }

    private static void seleccionarLista() {
        System.out.println("\n--- Listas Existentes ---");
        List<ToDos> listas = gestor.getListas();

        if (listas.isEmpty()) {
            System.out.println("No hay listas creadas. Por favor, crea una primero.");
            return;
        }

        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).toString());
        }

        try {
            System.out.print("\nIngresa el número de la lista que quieres gestionar: ");
            int num = Integer.parseInt(sc.nextLine());

            if (num > 0 && num <= listas.size()) {
                ToDos listaSeleccionada = listas.get(num - 1);
                menuListaSeleccionada(listaSeleccionada);
            } else {
                System.out.println("Número fuera de rango.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
        }
    }

    private static void menuListaSeleccionada(ToDos lista) {
        while (true) {
            System.out.println("\n--- Gestionando Lista: [" + lista.getNombre() + "] ---");
            System.out.println("1. Agregar nueva tarea");
            System.out.println("2. Ver tareas pendientes (por prioridad)");
            System.out.println("3. Buscar/Filtrar tareas pendientes");
            System.out.println("4. Marcar tarea como completada");
            System.out.println("5. Ver historial de tareas completadas");
            System.out.println("6. Marcar TODAS las tareas como completadas");
            System.out.println("7. Regresar tarea completada a pendiente");
            System.out.println("8. Editar una tarea pendiente");
            System.out.println("9. Editar nombre de la lista");
            System.out.println("10. Remover una tarea pendiente");
            System.out.println("11. Eliminar esta lista");
            System.out.println("12. Mover tarea a otra lista");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número.");
                continue;
            }

            switch (opcion) {
                case 1: agregarTareaALista(lista); break;
                case 3: buscarTareasPendientes(lista); break;
                case 4: marcarTareaCompletada(lista); break;
                case 5: verHistorialFiltrado(lista); break;
                case 6: marcarTodasEnLista(lista); break;
                case 7: regresarTarea(lista);break;
                case 8: editarTarea(lista);break;
                case 9: editarNombreLista(lista);break;
                case 10: removerTarea(lista);break;
                case 11:
                    System.out.println("Estás a punto de eliminar la lista: '" + lista.getNombre() + "'");
                    System.out.println("Se borrarán " + lista.getTareasPendientes().size() + " tareas pendientes y " + lista.getTareasCompletadas().size() + " completadas.");
                    System.out.print("¿Estás seguro?(S/N): ");
                    String confirmacion = sc.nextLine().toUpperCase();
                    if (confirmacion.equals("S")) {
                        boolean listaEliminada = gestor.eliminarLista(lista);
                        if (listaEliminada) {
                            return;
                        }
                    } else {
                        System.out.println("cancelado");
                    }
                    break;
                case 12: moverTareaAOtraLista(lista); break;
                case 0: return;

                case 2:
                    verTareasPendientesOrdenadas(lista);
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void marcarTodasEnLista(ToDos lista) {
        System.out.println("Estás a punto de marcar " +
                lista.getTareasPendientes().size() + " tareas como completadas.");
        System.out.print("¿Estás seguro? (S/N): ");
        String confirmacion = sc.nextLine().toUpperCase();
        if (confirmacion.equals("S")) {
            lista.marcarTodasCompletadas();
            System.out.println("Todas las tareas han sido completadas");
        } else {
            System.out.println("Cancelado");
        }
    }

    private static void agregarTareaALista(ToDos lista) {
        try {
            System.out.print("Descripción de la tarea: ");
            String desc = sc.nextLine();

            System.out.print("Fecha máxima (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(sc.nextLine());

            System.out.print("Prioridad (ALTA, MEDIA, BAJA): ");
            Prioridad prio = Prioridad.valueOf(sc.nextLine().toUpperCase());
            Tarea nuevaTarea = new Tarea(desc, fecha, prio);

            lista.agregarTarea(nuevaTarea);
            System.out.println("Tarea '" + desc + "' agregada.");

        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Prioridad no válida. Debe ser ALTA, MEDIA, o BAJA.");
        }
    }
    private static void verTareasPendientesOrdenadas(ToDos lista) {
        List<Tarea> tareasOrdenadas = lista.getTareasPendientesPorPrioridad();
        if (tareasOrdenadas.isEmpty()) {
            System.out.println("No hay tareas pendientes en esta lista");
            return;
        }
        for (Tarea tarea : tareasOrdenadas) {
            System.out.println(tarea);
        }
    }

    private static void marcarTareaCompletada(ToDos lista) {
        List<Tarea> pendientes = lista.getTareasPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes para marcar.");
            return;
        }
        System.out.println("\n--- ¿Qué tarea quieres completar? ---");
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i).getDescripcion());
        }
        try {
            System.out.print("Ingresa el número de la tarea: ");
            int num = Integer.parseInt(sc.nextLine());

            if (num > 0 && num <= pendientes.size()) {
                Tarea tareaACompletar = pendientes.get(num - 1);
                lista.marcarComoCompletada(tareaACompletar);
            } else {
                System.out.println("Número fuera de rango.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
        }
    }

    private static void verHistorialFiltrado(ToDos lista) {
        System.out.println("\n--- Ver Historial de Tareas Completadas ---");
        System.out.println("Elige un rango:");
        System.out.println("1. Última semana");
        System.out.println("2. Últimos 15 días");
        System.out.println("3. Último mes");
        System.out.println("4. Ver todo el historial");
        System.out.print("Elige una opción: ");

        int opcion = -1;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida.");
            return;
        }
        LocalDate fechaInicio = null;
        List<Tarea> historial = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        switch (opcion) {
            case 1:
                fechaInicio = hoy.minusWeeks(1);
                System.out.println("--- Tareas completadas en la última semana ---");
                break;
            case 2:
                fechaInicio = hoy.minusDays(15);
                System.out.println("--- Tareas completadas en los últimos 15 días ---");
                break;
            case 3:
                fechaInicio = hoy.minusMonths(1);
                System.out.println("--- Tareas completadas en el último mes ---");
                break;
            case 4:
                System.out.println("--- Todo el historial de completadas ---");
                historial.addAll(lista.getTareasCompletadas());
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }
        if (fechaInicio != null) {
            historial = lista.getCompletadasDespuesDe(fechaInicio);
        }
        if (historial.isEmpty()) {
            System.out.println("No se encontraron tareas completadas en este rango.");
        } else {
            for (Tarea tarea : historial) {
                System.out.println(tarea);
            }
        }
    }

    private static void buscarTareasPendientes(ToDos lista) {
        System.out.println("\n--- Buscar/Filtrar Tareas Pendientes ---");
        System.out.print("Filtrar por prioridad (ALTA, MEDIA, BAJA) o presiona Enter para omitir: ");
        String prioInput = sc.nextLine().toUpperCase();

        Prioridad prioridadFiltro = null;
        if (!prioInput.isEmpty()) {
            try {
                prioridadFiltro = Prioridad.valueOf(prioInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridad no válida. Omitiendo filtro de prioridad.");
            }
        }

        System.out.print("Filtrar por texto en descripción o presiona Enter para omitir: ");
        String textoFiltro = sc.nextLine();

        List<Tarea> resultados = lista.filtrarTareasPendientes(prioridadFiltro, textoFiltro);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron tareas que coincidan con los filtros.");
        } else {
            System.out.println("--- Resultados de la Búsqueda ---");
            for (Tarea tarea : resultados) {
                System.out.println(tarea);
            }
        }
    }

    private static void guardarDatos() {
        try (FileOutputStream fos = new FileOutputStream(guardar);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gestor);

        } catch (IOException e) {
            System.out.println("No se pudieron guardar los datos.");
        }
    }

    private static void cargarDatos() {
        try (FileInputStream fis = new FileInputStream(guardar);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gestor = (GestorListas) ois.readObject();
            System.out.println("Datos cargados exitosamente.");

        } catch (IOException e) {
            System.out.println("No se encontró archivo de datos. Empezando de cero.");
        } catch (ClassNotFoundException e) {
            System.out.println("Las clases no coinciden. Empezando de cero.");
        }
    }
    private static void regresarTarea(ToDos lista) {
        List<Tarea> completadas = new ArrayList<>(lista.getTareasCompletadas());

        if (completadas.isEmpty()) {
            System.out.println("No hay tareas en el historial para regresar.");
            return;
        }
        System.out.println("\n--- ¿Qué tarea quieres regresar a 'pendiente'? ---");
        for (int i = 0; i < completadas.size(); i++) {
            System.out.println((i + 1) + ". " + completadas.get(i).getDescripcion());
        }

        try {
            System.out.print("Ingresa el número de la tarea: ");
            int num = Integer.parseInt(sc.nextLine());
            if (num > 0 && num <= completadas.size()) {
                Tarea tareaARegresar = completadas.get(num - 1);
                lista.regresarTareaAPendiente(tareaARegresar);
            } else {
                System.out.println("Número fuera de rango.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
        }
    }
    private static void editarTarea(ToDos lista) {
        List<Tarea> pendientes = lista.getTareasPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes para editar.");
            return;
        }
        System.out.println("\n--- ¿Qué tarea quieres editar? ---");
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i).getDescripcion());
        }
        Tarea tareaAEditar;
        try {
            System.out.print("Ingresa el número de la tarea: ");
            int num = Integer.parseInt(sc.nextLine());
            if (num > 0 && num <= pendientes.size()) {
                tareaAEditar = pendientes.get(num - 1);
            } else {
                System.out.println("Número fuera de rango.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
            return;
        }
        System.out.println("--- Editando: " + tareaAEditar.getDescripcion() + " ---");
        System.out.println("1. Editar Descripción");
        System.out.println("2. Editar Fecha Máxima");
        System.out.println("3. Editar Prioridad");
        System.out.println("0. Cancelar");
        System.out.print("Elige una opción: ");
        int opcionEditar;
        try {
            opcionEditar = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida.");
            return;
        }
        switch (opcionEditar) {
            case 1:
                System.out.print("Ingresa la nueva descripción: ");
                String nuevaDesc = sc.nextLine();
                tareaAEditar.setDescripcion(nuevaDesc);
                System.out.println("Descripción actualizada");
                break;
            case 2:
                try {
                    System.out.print("Ingresa la nueva fecha máxima (YYYY-MM-DD): ");
                    LocalDate nuevaFecha = LocalDate.parse(sc.nextLine());
                    tareaAEditar.setFechaMaxima(nuevaFecha);
                    System.out.println("Fecha actualizada");
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto.");
                }
                break;
            case 3:
                try {
                    System.out.print("Ingresa la nueva prioridad (ALTA, MEDIA, BAJA): ");
                    Prioridad nuevaPrio = Prioridad.valueOf(sc.nextLine().toUpperCase());
                    tareaAEditar.setPrioridad(nuevaPrio);
                    System.out.println("Prioridad actualizada");
                } catch (IllegalArgumentException e) {
                    System.out.println("Prioridad no válida.");
                }
                break;
            case 0:
                System.out.println("Edición cancelada.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
    private static void removerTarea(ToDos lista) {
        List<Tarea> pendientes = lista.getTareasPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes para remover.");
            return;
        }
        System.out.println("\n--- ¿Qué tarea quieres remover permanentemente? ---");
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i).getDescripcion());
        }
        Tarea tareaARemover;
        try {
            System.out.print("Ingresa el número de la tarea: ");
            int num = Integer.parseInt(sc.nextLine());
            if (num > 0 && num <= pendientes.size()) {
                tareaARemover = pendientes.get(num - 1);
            } else {
                System.out.println("Número fuera de rango.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
            return;
        }
        System.out.println("-> " + tareaARemover.getDescripcion()+" Se va a borrar permanentemente");
        System.out.print("¿Estás seguro? (S/N): ");

        String confirmacion = sc.nextLine().toUpperCase();

        if (confirmacion.equals("S")) {
            lista.removerTareaPendiente(tareaARemover);
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private static void editarNombreLista(ToDos lista) {
        System.out.println("\n--- Editando Nombre de la Lista ---");
        System.out.println("Nombre actual: " + lista.getNombre());
        System.out.print("Ingresa el nuevo nombre: ");
        String nuevoNombre = sc.nextLine();
        if (nuevoNombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        gestor.editarNombreLista(lista, nuevoNombre);
    }
    private static void moverTareaAOtraLista(ToDos listaOrigen) {
        List<Tarea> pendientes = listaOrigen.getTareasPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes en esta lista para mover.");
            return;
        }
        System.out.println("\n--- ¿Qué tarea quieres mover? ---");
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i).getDescripcion());
        }
        Tarea tareaAMover;
        try {
            System.out.print("Ingresa el número de la tarea: ");
            int numTarea = Integer.parseInt(sc.nextLine());
            if (numTarea > 0 && numTarea <= pendientes.size()) {
                tareaAMover = pendientes.get(numTarea - 1);
            } else {
                System.out.println("Número fuera de rango.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
            return;
        }
        List<ToDos> listasDestinoPosibles = new ArrayList<>();
        for (ToDos lista : gestor.getListas()) {
            if (lista != listaOrigen) { // Excluimos la lista actual
                listasDestinoPosibles.add(lista);
            }
        }
        if (listasDestinoPosibles.isEmpty()) {
            System.out.println("No hay otras listas a las que mover la tarea. Debes crear otra lista primero.");
            return;
        }
        System.out.println("\n--- ¿A qué lista quieres mover '" + tareaAMover.getDescripcion() + "'? ---");
        for (int i = 0; i < listasDestinoPosibles.size(); i++) {
            System.out.println((i + 1) + ". " + listasDestinoPosibles.get(i).getNombre());
        }
        ToDos listaDestino;
        try {
            System.out.print("Ingresa el número de la lista destino: ");
            int numLista = Integer.parseInt(sc.nextLine());
            if (numLista > 0 && numLista <= listasDestinoPosibles.size()) {
                listaDestino = listasDestinoPosibles.get(numLista - 1);
            } else {
                System.out.println("Número fuera de rango.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un número.");
            return;
        }
        gestor.moverTarea(tareaAMover, listaOrigen, listaDestino);
    }
    private static void verTareasVencidas() {
        System.out.println("\n--- Tareas Pendientes VENCIDAS ---");
        List<Tarea> vencidas = gestor.recuperarPendientesVencidas();
        if (vencidas.isEmpty()) {
            System.out.println("No hay tareas vencidas.");
            return;
        }
        for (Tarea tarea : vencidas) {
            ToDos listaOrigen = gestor.buscarListaQueContiene(tarea);
            System.out.println(tarea + " (En lista: " + listaOrigen.getNombre() + ")");
        }
    }

    private static void verTareasParaHoy() {
        System.out.println("\n--- Tareas Pendientes PARA HOY ---");
        List<Tarea> paraHoy = gestor.recuperarPendientesParaHoy();

        if (paraHoy.isEmpty()) {
            System.out.println("No hay tareas programadas para hoy.");
            return;
        }

        for (Tarea tarea : paraHoy) {
            ToDos listaOrigen = gestor.buscarListaQueContiene(tarea);
            System.out.println(tarea + " (En lista: " + listaOrigen.getNombre() + ")");
        }
    }
}