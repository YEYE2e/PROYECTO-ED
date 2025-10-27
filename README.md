# Gestor de Listas de Tareas (ToDo Manager)

## Descripción

Sistema de gestión de tareas desarrollado en Java que permite crear y administrar múltiples listas de tareas con prioridades, fechas límite y seguimiento de completado.

## Características

- **Gestión de múltiples listas**: Crea y administra varias listas de tareas independientes
- **Sistema de prioridades**: Asigna prioridades (ALTA, MEDIA, BAJA) a cada tarea
- **Control de fechas límite**: Establece fechas máximas para completar tareas
- **Seguimiento de tareas completadas**: Marca y visualiza tareas finalizadas
- **Búsqueda y filtrado**: 
  - Ver todas las tareas completadas
  - Buscar tareas por prioridad
  - Ver tareas vencidas
  - Ver tareas programadas para hoy
- **Persistencia de datos**: Guarda y carga automáticamente las listas en el archivo `todo.dat`

## Estructura del Proyecto

```
ProyectoED2/
│
├── src/
│   ├── Main.java                  # Clase principal con menú de usuario
│   ├── GestorListas.java          # Gestiona múltiples listas de tareas
│   ├── ToDos.java                 # Representa una lista de tareas
│   ├── Tarea.java                 # Representa una tarea individual
│   ├── Prioridad.java             # Enumeración de prioridades
│   └── ComparadorPrioridad.java   # Comparador para ordenar por prioridad
│
└── todo.dat                       # Archivo de persistencia de datos
```

## Uso

### Compilar y ejecutar

```bash
javac src/*.java
java -cp src Main
```

### Menú Principal

1. **Crear nueva lista**: Crea una nueva lista de tareas
2. **Seleccionar lista**: Accede a una lista existente para administrar sus tareas
3. **Ver todas las tareas completadas**: Muestra todas las tareas completadas de todas las listas
4. **Buscar por prioridad**: Busca tareas por nivel de prioridad
5. **Ver tareas vencidas**: Muestra tareas que superaron su fecha límite
6. **Ver tareas para hoy**: Muestra tareas con fecha límite para el día actual
0. **Salir**: Guarda los datos y cierra la aplicación

### Gestión de Tareas

Dentro de cada lista puedes:
- Añadir nuevas tareas
- Ver tareas pendientes
- Ver tareas completadas
- Marcar tareas como completadas
- Editar tareas existentes
- Eliminar tareas
- Ordenar tareas por prioridad

## Tecnologías

- Java SE
- Estructuras de datos: LinkedList, ArrayDeque
- Serialización de objetos para persistencia
- API de tiempo de Java (LocalDate)

## Autor

Proyecto desarrollado como parte del curso de Estructuras de Datos

## Curso

Estructuras de Datos 2 (ED2)

