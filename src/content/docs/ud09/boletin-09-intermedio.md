---
draft: true
prev: false
next: false
title: "📝 Unidad 9 — Boletín Intermedio (Propuesto)"
description: "Ejercicios de nivel intermedio en Java Swing: modelos dinámicos (DefaultListModel, DefaultTableModel), diálogos JFileChooser, componentes de pestañas y dibujo 2D."
---

:::tip[Objetivos Pedagógicos]
* Dominar la manipulación dinámica de modelos de datos (`DefaultListModel`, `DefaultTableModel`).
* Integrar sistemas de archivos con la interfaz gráfica mediante `JFileChooser` y flujos de E/S.
* Implementar aplicaciones modulares respetando la separación entre vista y lógica.
:::

---

### Ejercicio 1: Calculadora de Cuatro Operaciones
Implementa una interfaz gráfica para una calculadora de escritorio clásica:
* Un campo de texto `JTextField` superior en modo solo lectura (`setEditable(false)`), alineado a la derecha y con fuente de tamaño 22.
* Un panel central con un `GridLayout(4, 4, 5, 5)` que contenga los dígitos del 0 al 9, las cuatro operaciones básicas (`+`, `-`, `*`, `/`), el botón decimal (`.`), el igual (`=`) y el borrado (`C`).
* Al pulsar dígitos, se van concatenando en el visor. Al pulsar un operador, se almacena el primer operando y la operación seleccionada. Al pulsar `=`, se ejecuta el cálculo y se muestra el resultado en pantalla.
* Controla adecuadamente la división por cero mostrando un mensaje descriptivo en el visor.

---

### Ejercicio 2: Gestor de Tareas Pendientes con `JList` y `DefaultListModel`
Construye una aplicación para gestionar una lista de tareas:
* Una caja de texto para escribir una nueva tarea y un botón `"Añadir Tarea"`.
* Un componente `JList<String>` embebido dentro de un `JScrollPane`, conectado a un `DefaultListModel<String>`.
* En la parte inferior, tres botones:
  1. `"Eliminar Tarea Seleccionada"`: elimina el elemento actualmente marcado en la lista. Si no hay selección, avisa con un diálogo.
  2. `"Mover Arriba"`: intercambia la posición del elemento seleccionado con el inmediatamente superior.
  3. `"Limpiar Todo"`: vacía por completo el modelo de la lista tras pedir confirmación al usuario.

---

### Ejercicio 3: Desplegables Dependientes con `JComboBox`
Diseña una ventana con dos listas desplegables dependientes:
* Desplegable 1 (País): contiene inicialmente `"España"`, `"Francia"` e `"Italia"`.
* Desplegable 2 (Ciudad/Provincia): su contenido debe cambiar dinámicamente según el país seleccionado en el primer combo.
  * España: `"Madrid"`, `"Barcelona"`, `"Valencia"`, `"Sevilla"`.
  * Francia: `"París"`, `"Lyon"`, `"Marsella"`.
  * Italia: `"Roma"`, `"Milán"`, `"Nápoles"`.
* Una etiqueta inferior que actualice en tiempo real la selección completa: `"Destino elegido: Valencia (España)"`.

---

### Ejercicio 4: Bloc de Notas con Menú y `JFileChooser`
Desarrolla un editor de notas de texto enriquecido con menús:
* Un área de texto `JTextArea` con saltos de línea automáticos (`setLineWrap(true)`) colocada dentro de un `JScrollPane`.
* Una barra de menú `JMenuBar` con el menú `"Archivo"` y las opciones:
  1. `"Nuevo"`: limpia el área de texto tras comprobar si hay contenido no guardado.
  2. `"Abrir..."`: muestra un `JFileChooser`, lee el fichero de texto seleccionado mediante NIO.2 o `BufferedReader` y vuelca el contenido en el área de texto.
  3. `"Guardar Como..."`: muestra un `JFileChooser` para seleccionar la ruta destino y escribe el texto en el disco.
  4. `"Salir"`: finaliza la aplicación.

---

### Ejercicio 5: Mantenimiento de Alumnos con `JTable`
Crea una ventana para registrar calificaciones escolares:
* Un formulario superior con campos: DNI, Nombre Completo, Ciclo (`JComboBox` con DAM, DAW, ASIR) y Nota Media.
* Un botón `"Insertar Alumno"` y un botón `"Eliminar Seleccionado"`.
* Una tabla `JTable` configurada con un `DefaultTableModel` con las cuatro columnas indicadas y celdas no editables directamente por doble clic.
* Al pulsar `"Insertar Alumno"`, valida los datos (comprobando que la nota sea un número entre 0 y 10) y agrega la fila a la tabla.
* Al pulsar `"Eliminar Seleccionado"`, retira la fila seleccionada del `DefaultTableModel`.

---

### Ejercicio 6: Panel Multitarea con `JTabbedPane`, `JSlider` y `JSpinner`
Construye una interfaz organizada en tres pestañas:
* **Pestaña 1 ("Ajustes de Sonido"):** Contiene un `JSlider` de volumen (rango de 0 a 100, con marcas de graduación mayores y menores visibles y etiquetas numéricas) y una casilla de verificación `"Silencio (Mute)"` que deshabilite el slider.
* **Pestaña 2 ("Configuración de Reserva"):** Contiene un `JSpinner` numérico para el número de comensales (mínimo 1, máximo 12) y un `JSpinner` con un `SpinnerDateModel` para seleccionar fecha y hora.
* **Pestaña 3 ("Resumen"):** Un botón `"Generar Resumen"` que recopile los valores de los controles de las otras pestañas y los muestre formateados en un cuadro de texto.

---

### Ejercicio 7: Lienzo de Dibujo Interactivo con Ratón
Crea un panel personalizado que extienda de `JPanel` y sobreescriba `paintComponent(Graphics g)`:
* Mantén una lista interna de puntos `List<Point>` donde el usuario haya hecho clic con el ratón.
* Asocia un `MouseAdapter` al panel; cada vez que el usuario haga clic primario en cualquier lugar del lienzo, añade la coordenada a la lista y llama a `repaint()`.
* En `paintComponent`, dibuja un círculo de radio 10 píxeles relleno de color verde calma en cada una de las coordenadas guardadas, y traza una línea recta que conecte los puntos secuencialmente.
* Añade un botón `"Limpiar Lienzo"` que vacíe la lista de puntos y repinte el panel en blanco.

---

### Ejercicio 8: Cronómetro Digital con `javax.swing.Timer`
Implementa un cronómetro con precisión de décimas de segundo:
* Una etiqueta central con fuente grande (ej. tamaño 36 en negrita): `"00:00.0"`.
* Tres botones: `"Iniciar"`, `"Pausar"` y `"Reiniciar"`.
* Utiliza un temporizador `javax.swing.Timer` configurado con un intervalo de 100 milisegundos que actualice la variable de tiempo y recalcule los minutos, segundos y décimas en el EDT.
* Asegura que el botón `"Iniciar"` quede deshabilitado mientras el cronómetro esté corriendo para evitar múltiples temporizadores solapados.
