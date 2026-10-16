---
draft: true
prev: false
next: false
title: "🖼️ Unidad 9. Aplicaciones controladas por eventos"
description: "Construcción de interfaces gráficas de escritorio con Java Swing, gestión del hilo despachador EDT, layouts, modelo de delegación de eventos, componentes MVC y gráficos 2D."
---

:::note[Información Curricular y Metadatos]
* **Módulo Profesional:** Programación (0485) — Ciclo Formativo de Grado Superior DAM / DAW.
* **Resultado de Aprendizaje Principal:** **RA5** — Realiza operaciones de entrada y salida de información, utilizando procedimientos específicos del lenguaje y librerías de clases (22 horas lectivas).
  * **Criterio 5.f:** Se han utilizado las herramientas del entorno de desarrollo para crear interfaces gráficos de usuario simples.
  * **Criterio 5.g:** Se han programado controladores de eventos.
  * **Criterio 5.h:** Se han escrito programas que utilicen interfaces gráficos para la entrada y salida de información.
* **Entorno Oficial:** IntelliJ IDEA (Java LTS).
:::

---

## 1. Introducción: De la Consola a la Interfaz Gráfica

Hasta este punto del curso, todas nuestras aplicaciones se ejecutaban de forma **secuencial o dirigida por la máquina**: el programa arrancaba en el método `main`, solicitaba datos por consola mediante un `Scanner`, realizaba un cálculo y mostraba el resultado por `System.out.print`. Si el usuario no introducía el dato que el programa esperaba exactamente en ese instante, la aplicación se quedaba bloqueada.

En las aplicaciones con **Interfaz Gráfica de Usuario (GUI - Graphical User Interface)**, la filosofía cambia por completo:

```
[Paradigma Secuencial / Consola]
Inicio → Pedir Dato A → Pedir Dato B → Procesar → Mostrar → Fin

[Paradigma Controlado por Eventos / GUI]
Inicio → Pintar Ventana → [BUCLE DE EVENTOS ESPERANDO EN REPOSO]
                           ↓                 ↓               ↓
                    Click en Botón    Tecla Pulsada    Arrastrar Ratón
                           ↓                 ↓               ↓
                     Manejador A       Manejador B     Manejador C
                           ↳ (Actualiza vista y vuelve a esperar)
```

En una aplicación gráfica, el usuario tiene el control: puede hacer clic en un botón, escribir en un campo de texto, seleccionar una fila de una tabla o cerrar la ventana en cualquier orden arbitrario. El programa se encuentra en un estado pasivo escuchando acontecimientos del sistema operativo; cuando ocurre una acción, se dispara un **evento** y el sistema invoca al **controlador (listener)** asociado.

---

## 2. Ecosistema Gráfico en Java: AWT, Swing y el EDT

Java proporciona dos bibliotecas clásicas nativas en el JDK:

1. **AWT (Abstract Window Toolkit):** Es la librería original de Java 1.0 (`java.awt.*`). Sus componentes son *pesados (heavyweight)*, ya que delegan directamente en los widgets del sistema operativo subyacente (Windows, macOS o Linux).
2. **Swing:** Introducida en Java 2 (`javax.swing.*`), construida sobre la infraestructura de eventos y gráficos de AWT. Sus componentes son *ligeros (lightweight)*: están pintados directamente en Java mediante primitivas gráficas sobre la ventana. Esto permite que la aplicación tenga una apariencia homogénea (*Look and Feel* personalizable) en cualquier plataforma y ofrezca una colección de widgets mucho más rica (`JTable`, `JTree`, pestañas, etc.).

### 2.1. El Hilo Despachador de Eventos (Event Dispatch Thread - EDT)

Swing no es seguro para hilos (*not thread-safe*). Toda creación, modificación y repintado de componentes gráficos, así como la ejecución de los escuchadores de eventos, debe ocurrir obligatoriamente en un hilo dedicado administrado por la JVM denominado **EDT (Event Dispatch Thread)**.

Si intentamos instanciar o modificar una ventana directamente en el hilo principal `main`, podemos generar carreras de condiciones (*race conditions*) y bloqueos gráficos intermitentes.

Por ello, el punto de entrada estándar en Java moderno siempre utiliza `SwingUtilities.invokeLater`:

```java
package com.dam.gui;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Encolamos la construcción de la ventana en el hilo despachador de eventos (EDT)
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
```

:::warning[Regla de Oro del Rendimiento Gráfico]
**Nunca ejecutes operaciones lentas en el EDT.** Si en el manejador de un botón realizas una consulta SQL pesada, descargas un fichero de red o ejecutas un cálculo de varios segundos, el EDT quedará congelado: los botones no responderán, la ventana se pondrá en blanco y el sistema operativo indicará que la aplicación "No responde". Las tareas pesadas deben derivarse a hilos secundarios o mediante la clase utilitaria `SwingWorker`.
:::

---

## 3. Ventanas y Contenedores: `JFrame` y `JPanel`

En Swing existen dos categorías primordiales de elementos gráficos:
* **Contenedores de alto nivel:** Ventanas que dialogan con el gestor de ventanas del sistema operativo (`JFrame`, `JDialog`).
* **Contenedores intermedios:** Paneles donde se agrupan y organizan otros componentes (`JPanel`, `JScrollPane`, `JTabbedPane`).
* **Componentes atómicos:** Controles interactivos (`JButton`, `JLabel`, `JTextField`, etc.).

### 3.1. Estructura de una Ventana Moderna con `JFrame`

La forma profesional de diseñar una ventana es extender de `JFrame` o componer una instancia dentro de una clase especializada:

```java
package com.dam.gui;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        super("Gestión DAM — Módulo Programación");
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        // Dimensiones iniciales (ancho, alto en píxeles)
        this.setSize(600, 400);

        // Centrar la ventana en la pantalla
        this.setLocationRelativeTo(null);

        // Comportamiento al pulsar la 'X' de la barra de título:
        // EXIT_ON_CLOSE finaliza la JVM por completo
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Permitir o bloquear el redimensionamiento manual por el usuario
        this.setResizable(true);
    }

    private void inicializarComponentes() {
        // Obtenemos el panel de contenido principal de la ventana
        JPanel panelRaiz = new JPanel();
        panelRaiz.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Bienvenido al Entorno Gráfico Swing", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelRaiz.add(lblTitulo, BorderLayout.NORTH);

        JButton btnSaludar = new JButton("Pulsar para interactuar");
        panelRaiz.add(btnSaludar, BorderLayout.CENTER);

        // Asignamos el panel al contenedor de la ventana
        this.setContentPane(panelRaiz);
    }
}
```

---

## 4. Gestores de Diseño (Layout Managers)

En el desarrollo de interfaces de usuario para escritorio, las pantallas varían en resolución, densidad de píxeles y tamaño de fuentes. Por ello, **nunca se debe intentar posicionar componentes usando coordenadas fijas en píxeles** de forma predeterminada.

Swing delega la geometría de los componentes a los **Layout Managers** (gestores de diseño), que calculan dinámicamente el tamaño y la ubicación de cada botón o etiqueta según las restricciones del panel.

### 4.1. `BorderLayout`

Divide el contenedor en cinco regiones geográficas: `NORTH`, `SOUTH`, `EAST`, `WEST` y `CENTER`.
* `NORTH` y `SOUTH` respetan la altura preferida del componente y se extienden a lo ancho.
* `EAST` y `WEST` respetan la anchura preferida y se extienden a lo alto.
* `CENTER` absorbe todo el espacio restante disponible en el centro.

```
+---------------------------------------------------+
|                     NORTH                         |
+-------------+-----------------------+-------------+
|    WEST     |        CENTER         |    EAST     |
+-------------+-----------------------+-------------+
|                     SOUTH                         |
+---------------------------------------------------+
```

```java
JPanel panel = new JPanel(new BorderLayout(10, 10)); // Espaciado horizontal y vertical de 10px
panel.add(new JButton("Arriba"), BorderLayout.NORTH);
panel.add(new JButton("Contenido Principal"), BorderLayout.CENTER);
panel.add(new JButton("Pie"), BorderLayout.SOUTH);
```

### 4.2. `FlowLayout`

Coloca los componentes uno tras otro en una fila continua respetando su tamaño preferido. Cuando la fila se llena, continúa en la siguiente (similar al texto en un procesador de documentos). Es el layout por defecto de los `JPanel`.

```java
JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
panelBotones.add(new JButton("Aceptar"));
panelBotones.add(new JButton("Cancelar"));
```

### 4.3. `GridLayout`

Distribuye el espacio en una cuadrícula uniforme de filas y columnas. **Todas las celdas tienen exactamente el mismo tamaño**, forzando a los componentes a ajustarse a la celda. Ideal para teclados numéricos o tableros de juegos (tres en raya, ajedrez):

```java
// Rejilla de 4 filas y 4 columnas con 5px de separación
JPanel panelCalculadora = new JPanel(new GridLayout(4, 4, 5, 5));
for (int i = 1; i <= 9; i++) {
    panelCalculadora.add(new JButton(String.valueOf(i)));
}
```

### 4.4. `BoxLayout` y Paneles Combinados

`BoxLayout` alinea componentes en un único eje (vertical con `BoxLayout.Y_AXIS` u horizontal con `BoxLayout.X_AXIS`). Al anidar paneles con distintos layouts (`BorderLayout` con paneles interiores en `FlowLayout` o `GridLayout`), se pueden construir interfaces de nivel profesional con absoluta limpieza y adaptabilidad.

```java
JPanel panelVertical = new JPanel();
panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));

panelVertical.add(new JLabel("Nombre:"));
panelVertical.add(new JTextField(20));
panelVertical.add(Box.createVerticalStrut(10)); // Espacio rígido de 10px
panelVertical.add(new JLabel("Apellidos:"));
panelVertical.add(new JTextField(20));
```

### 4.5. Bordes con `BorderFactory`

Los bordes permiten estructurar visualmente los paneles y agrupar campos relacionados:

```java
panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Alumno"));
panelDetalle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Margen interior (padding)
```

:::warning[¿Por qué evitar el Layout Nulo (`setLayout(null)`)?]
Al usar `panel.setLayout(null)` y posicionar con `componente.setBounds(x, y, w, h)`, la interfaz parece funcionar en tu ordenador personal. Sin embargo, al ejecutar la aplicación en un monitor con escalado de alta densidad (pantallas 4K, 125% o 150% en Windows) o en otro sistema operativo con fuentes distintas, los textos se cortarán, los botones se montarán unos encima de otros y la ventana quedará visualmente rota e inusable. Usa siempre Layout Managers.
:::

---

## 5. El Modelo de Delegación de Eventos

Java utiliza el **Delegation Event Model**. Su arquitectura se basa en tres pilares:

```
+---------------+                      +---------------+                      +-------------------+
|  Event Source |                      |  Event Object |                      |   Event Listener  |
|  (Ej: JButton)| === (Genera evento) ⇒| (Ej: ActionEvent) =================⇒ | (Ej: ActionListener)|
+---------------+                      +---------------+                      +-------------------+
   Registra con:                                                                 Ejecuta:
   btn.addActionListener(listener)                                              actionPerformed(e)
```

1. **Fuente del evento (*Event Source*):** El componente visual que interactúa con el usuario (ej. un `JButton`, un `JTextField`, la ventana `JFrame`).
2. **Objeto evento (*Event Object*):** Objeto que encapsula los detalles de lo sucedido (instante de tiempo, coordenadas del cursor, tecla pulsada, modificadores como Shift o Ctrl). Desciende de `java.util.EventObject`.
3. **Escuchador / Controlador (*Event Listener*):** Interfaz que define los métodos de respuesta. El componente origen delega en este objeto la ejecución del código cuando el evento se produce.

### 5.1. Evolución del Código de Controladores en Java

A lo largo de la historia de Java, la forma de conectar listeners a componentes ha evolucionado enormemente:

#### Enfoque 1: Clase Interna Anónima (Java 1.1 - Java 7)
```java
btnSaludar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Botón pulsado a la antigua.");
    }
});
```

#### Enfoque 2: Expresiones Lambda Modernas (Java 8 LTS en adelante)
Dado que `ActionListener` es una **interfaz funcional** (posee un único método abstracto: `actionPerformed`), podemos implementar el listener directamente con una expresión Lambda limpia y legible:

```java
btnSaludar.addActionListener(e -> {
    String nombre = txtNombre.getText().trim();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe introducir un nombre.", "Aviso", JOptionPane.WARNING_MESSAGE);
    } else {
        lblResultado.setText("¡Hola, " + nombre + "!");
    }
});
```

---

## 6. Catálogo de Eventos Principales

### 6.1. Eventos de Acción (`ActionEvent` / `ActionListener`)
Se dispara cuando ocurre la acción por defecto del componente: clic en un `JButton`, pulsar la tecla Enter dentro de un `JTextField`, seleccionar una opción en un `JComboBox` o pulsar un elemento de menú `JMenuItem`.

```java
txtEntrada.addActionListener(e -> {
    // Se ejecuta al pulsar ENTER en el campo de texto
    String texto = txtEntrada.getText();
    areaLogs.append(texto + "\n");
    txtEntrada.setText("");
});
```

### 6.2. Eventos de Ratón (`MouseEvent`, `MouseListener`, `MouseMotionListener`)
Permiten interceptar clics, entradas, salidas y desplazamientos del cursor:

* `MouseListener`: `mouseClicked`, `mousePressed`, `mouseReleased`, `mouseEntered`, `mouseExited`.
* `MouseMotionListener`: `mouseMoved`, `mouseDragged`.

#### Clases Adaptadoras (`Adapter Classes`)
La interfaz `MouseListener` tiene 5 métodos abstractos. Si solo nos interesa responder a `mousePressed`, implementar la interfaz directamente nos obligaría a escribir 4 métodos vacíos inútiles.

Java resuelve esto mediante las **clases adaptadoras** (`MouseAdapter`, `KeyAdapter`, `WindowAdapter`), que proporcionan implementaciones por defecto con cuerpos vacíos:

```java
panelLienzo.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        // Obtenemos coordenadas exactas del clic relativo al panel
        int x = e.getX();
        int y = e.getY();
        
        // Comprobar si se pulsó botón derecho
        if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("Clic secundario en: (" + x + ", " + y + ")");
        } else {
            System.out.println("Clic primario en: (" + x + ", " + y + ")");
        }
    }
});
```

### 6.3. Eventos de Teclado (`KeyEvent` / `KeyListener` / `KeyAdapter`)
Interceptan la pulsación de teclas:
* `keyPressed(KeyEvent e)`: Tecla física presionada.
* `keyReleased(KeyEvent e)`: Tecla soltada.
* `keyTyped(KeyEvent e)`: Carácter imprimible generado.

```java
txtCodigoPostal.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        // Solo permitir caracteres numéricos y bloquear otros
        if (!Character.isDigit(c) || txtCodigoPostal.getText().length() >= 5) {
            e.consume(); // Cancela la propagación del carácter al campo
        }
    }
});
```

### 6.4. Eventos de Ventana (`WindowEvent` / `WindowListener` / `WindowAdapter`)
Permiten capturar cuándo la ventana se minimiza, se restaura o el usuario intenta cerrarla:

```java
this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
this.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
            VentanaPrincipal.this,
            "¿Desea guardar los cambios antes de salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            guardarDatos();
            System.exit(0);
        } else if (confirmacion == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        // Si cancela, no hacemos nada y la ventana permanece abierta
    }
});
```

### 6.5. Temporizadores con `javax.swing.Timer`
A diferencia de los hilos tradicionales, `javax.swing.Timer` dispara un `ActionEvent` periódico **directamente en el EDT**, lo que permite actualizar componentes visuales (como barras de progreso o relojes) sin peligro de concurrencia:

```java
Timer reloj = new Timer(1000, e -> {
    lblHora.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
});
reloj.start();
```

---

## 7. Componentes de Entrada y Visualización

### 7.1. Casillas de Verificación y Botones de Opción

* **`JCheckBox`:** Selección booleana independiente (múltiples opciones activas simultáneamente).
* **`JRadioButton` con `ButtonGroup`:** Selección excluyente (solo una opción activa del grupo).

```java
// Botones de opción excluyentes
JRadioButton rbMasculino = new JRadioButton("Masculino");
JRadioButton rbFemenino = new JRadioButton("Femenino");
JRadioButton rbOtro = new JRadioButton("Otro", true);

ButtonGroup grupoGenero = new ButtonGroup();
grupoGenero.add(rbMasculino);
grupoGenero.add(rbFemenino);
grupoGenero.add(rbOtro);
```

### 7.2. Menús y Barras de Herramientas

Las aplicaciones de escritorio estructuran sus comandos en barras de menús jerárquicas:

```java
JMenuBar barraMenu = new JMenuBar();

JMenu menuArchivo = new JMenu("Archivo");
menuArchivo.setMnemonic('A'); // Atajo Alt + A

JMenuItem itemAbrir = new JMenuItem("Abrir archivo...");
itemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
itemAbrir.addActionListener(e -> abrirArchivo());

JMenuItem itemSalir = new JMenuItem("Salir");
itemSalir.addActionListener(e -> System.exit(0));

menuArchivo.add(itemAbrir);
menuArchivo.addSeparator();
menuArchivo.add(itemSalir);

barraMenu.add(menuArchivo);
this.setJMenuBar(barraMenu);
```

---

## 8. Componentes Basados en el Patrón MVC

Muchos componentes avanzados de Swing (`JComboBox`, `JList`, `JTable`) implementan internamente el patrón **Modelo-Vista-Controlador (MVC)**:

* **Vista:** El componente visual (`JList`, `JTable`) que se dibuja en pantalla.
* **Modelo:** La estructura de datos en memoria que almacena los registros (`ListModel`, `TableModel`).
* **Controlador:** Los listeners que escuchan eventos de usuario y notifican al modelo.

### 8.1. Listas y Desplegables con Modelos Dinámicos

```java
// Modelo dinámico para JComboBox
DefaultComboBoxModel<String> modeloProvincias = new DefaultComboBoxModel<>();
modeloProvincias.addElement("Valencia");
modeloProvincias.addElement("Alicante");
modeloProvincias.addElement("Castellón");

JComboBox<String> comboProvincias = new JComboBox<>(modeloProvincias);

// Para añadir elementos dinámicamente en tiempo de ejecución:
modeloProvincias.addElement("Teruel");

// Recuperar el elemento seleccionado tipado
String seleccion = (String) comboProvincias.getSelectedItem();
```

### 8.2. Tablas Avanzadas con `JTable` y `DefaultTableModel`

Una tabla en Swing requiere envolverla en un `JScrollPane` para contar con cabeceras de columna y barras de desplazamiento vertical y horizontal:

```java
// Definición de columnas y modelo
String[] columnas = {"ID", "Nombre", "Ciclo", "Nota Media"};
DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        // Hacer que las celdas sean de solo lectura (no editables con doble clic)
        return false;
    }
};

JTable tablaAlumnos = new JTable(modeloTabla);
tablaAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Añadir registros como filas
modeloTabla.addRow(new Object[]{1, "Ana Gómez", "DAM", 9.4});
modeloTabla.addRow(new Object[]{2, "Carlos Ruiz", "DAW", 7.8});

// ¡IMPRESCINDIBLE!: Colocar la JTable dentro de un JScrollPane
JScrollPane scrollTabla = new JScrollPane(tablaAlumnos);
panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
```

Para detectar cuándo el usuario pincha sobre una fila:

```java
tablaAlumnos.getSelectionModel().addListSelectionListener(e -> {
    // getValueIsAdjusting() es true mientras el usuario arrastra la selección
    if (!e.getValueIsAdjusting()) {
        int filaSeleccionada = tablaAlumnos.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            lblDetalle.setText("Seleccionado: " + nombre);
        }
    }
});
```

---

## 9. Diálogos Nativos: `JOptionPane` y `JFileChooser`

### 9.1. Ventanas Modales de Notificación con `JOptionPane`

`JOptionPane` bloquea la interacción con la ventana padre mientras se muestra el cuadro de diálogo:

```java
// 1. Mensaje informativo
JOptionPane.showMessageDialog(this, "Datos guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

// 2. Diálogo de confirmación (Sí/No)
int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el registro?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
if (opcion == JOptionPane.YES_OPTION) {
    // Proceder con el borrado
}

// 3. Diálogo de entrada de texto
String respuesta = JOptionPane.showInputDialog(this, "Introduce tu código de empleado:", "Entrada de datos", JOptionPane.QUESTION_MESSAGE);
```

### 9.2. Selector de Archivos con `JFileChooser`

Permite al usuario examinar el sistema de archivos del sistema operativo para abrir o guardar ficheros:

```java
JFileChooser selector = new JFileChooser();
selector.setDialogTitle("Selecciona un fichero de texto");
selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
selector.setFileFilter(new FileNameExtensionFilter("Documentos de texto (*.txt, *.csv)", "txt", "csv"));

int resultado = selector.showOpenDialog(this);
if (resultado == JFileChooser.APPROVE_OPTION) {
    File ficheroSeleccionado = selector.getSelectedFile();
    System.out.println("Ruta absoluta seleccionada: " + ficheroSeleccionado.getAbsolutePath());
}
```

---

## 10. Gráficos 2D y Dibujo Sobre Componentes

En Swing, cualquier componente puede personalizar su representación gráfica sobreescribiendo el método `paintComponent(Graphics g)`:

```java
public class PanelDibujo extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        // 1. SIEMPRE invocar al método super para que Swing limpie el fondo del panel
        super.paintComponent(g);

        // 2. Castear a Graphics2D para acceder al motor de renderizado avanzado
        Graphics2D g2 = (Graphics2D) g;

        // Activar suavizado de bordes (antialiasing) para dibujos nítidos
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar un rectángulo relleno
        g2.setColor(new Color(45, 138, 78)); // Verde calma
        g2.fillRect(30, 30, 200, 100);

        // Dibujar borde con grosor
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawRect(30, 30, 200, 100);

        // Dibujar círculos / elipses
        g2.setColor(new Color(220, 53, 69));
        g2.fillOval(260, 30, 100, 100);

        // Renderizar cadenas de texto con fuentes personalizadas
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.drawString("Gráficos Java 2D", 50, 85);
    }
}
```

Para forzar al panel a repintarse cuando los datos cambian (por ejemplo, en un juego o gráfico interactivo), se invoca al método `panel.repaint()`.

---

## 11. Arquitectura de una Aplicación Swing en IntelliJ IDEA

En un proyecto profesional de DAM, la interfaz gráfica debe desacoplarse de la lógica de negocio aplicando el patrón **MVC**:

```
src/
└── com/dam/
    ├── App.java                   (Punto de entrada con SwingUtilities.invokeLater)
    ├── model/
    │   ├── Alumno.java            (POJO de datos puros)
    │   └── GestorAlumnos.java     (Lógica de negocio, colecciones)
    ├── view/
    │   ├── VentanaPrincipal.java  (JFrame y configuración de componentes)
    │   └── PanelFormulario.java   (Paneles auxiliares)
    └── controller/
        └── AlumnoController.java  (Listeners y coordinación entre Modelo y Vista)
```

En **IntelliJ IDEA**, puedes diseñar interfaces directamente mediante código Java puro (la técnica recomendada y exigida en este curso por su total control, portabilidad y limpieza) o utilizar el diseñador visual integrado (*GUI Designer* generando ficheros `.form`). 

---

## 12. ⭐ Be the Code: El Ciclo de Vida de un Clic

Imaginemos que tenemos este fragmento de código:

```java
JButton btnCalcular = new JButton("Procesar");
btnCalcular.addActionListener(e -> {
    lblResultado.setText("Calculando...");
    hacerPausaLenta(); // Bloqueo de 5 segundos
    lblResultado.setText("Terminado");
});
```

* **Paso 1 (Hardware/OS):** El usuario pulsa con el dedo el botón izquierdo del ratón sobre la coordenada (120, 85) de la pantalla. El driver del ratón manda una interrupción al sistema operativo.
* **Paso 2 (JVM y AWT Toolkit):** El sistema operativo entrega el mensaje a la ventana de Java. El Toolkit de AWT traduce el mensaje nativo a un objeto `MouseEvent` y lo encola en la cola de eventos de la JVM (`EventQueue`).
* **Paso 3 (EDT despacha el evento):** El hilo EDT toma el evento de la cola, determina qué componente visual abarca esas coordenadas (`btnCalcular`) y dispara su evento semántico `ActionEvent`.
* **Paso 4 (Ejecución del Listener):** El EDT entra a ejecutar la Lambda:
  * Cambia el texto del label a `"Calculando..."` y solicita un repintado (`repaint()`). **¡Pero el repintado se encola para después!**
  * Entra en `hacerPausaLenta()`. Como la pausa se está ejecutando sobre el mismísimo EDT, el hilo se congela durante 5 segundos.
  * Durante esos 5 segundos, la pantalla **no se actualiza**, el texto `"Calculando..."` jamás llega a verse, y la ventana deja de responder a cualquier otro clic o arrastre.
  * Termina la pausa y pone el texto `"Terminado"`.
  * Finalmente, el EDT queda libre y procesa el repintado: el usuario solo ve `"Terminado"`.
* **Conclusión:** Cualquier tarea que tarde más de 50 milisegundos debe lanzarse en un hilo independiente (`Thread` o `SwingWorker`).

---

## 13. ¡No Hay Preguntas Tontas!

### ¿Por qué seguimos aprendiendo Swing si existen tecnologías web o JavaFX?
1. **Fundamentos sólidos de arquitectura:** Swing es el estándar canónico en Ciencias de la Computación para aprender el ciclo de vida guiado por eventos, el patrón MVC, el modelo de delegación y el despachador de hilos de interfaz de usuario.
2. **Presente en la industria:** Grandes entornos de desarrollo que utilizas a diario (como el propio **IntelliJ IDEA** o Android Studio) están desarrollados sobre el motor gráfico de Java y Swing con extensiones propias.
3. **Autonomía total:** No requiere servidores, navegadores web ni dependencias externas complejas; se ejecuta de forma nativa e inmediata en cualquier equipo con el JRE instalado.

### ¿Por qué mi aplicación no termina de ejecutarse en consola al cerrar la ventana?
Si en tu `JFrame` no incluyes la instrucción `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`, por defecto Swing aplica `HIDE_ON_CLOSE`. La ventana desaparece visualmente de la pantalla, pero el hilo EDT continúa ejecutándose en segundo plano en la memoria RAM hasta que el proceso es destruido manualmente.

### ¿Qué diferencia hay entre `paint()` y `paintComponent()`?
En AWT clásico se sobreescribía `paint()`. En Swing, `paint()` orquesta el dibujado de las capas internas: llama a `paintComponent()` (el fondo y el cuerpo), luego a `paintBorder()` (el borde) y finalmente a `paintChildren()` (los componentes contenidos). **En Swing siempre se debe sobreescribir `paintComponent(Graphics g)`** y llamar en la primera línea a `super.paintComponent(g)` para asegurar la correcta limpieza del fondo antes de dibujar.

### ¿Es necesario escribir `serialVersionUID` en las clases que extienden de `JFrame`?
`JFrame` y `JComponent` implementan la interfaz `java.io.Serializable`. El compilador de Java emite una advertencia si no defines `private static final long serialVersionUID = 1L;`. Aunque rara vez se serializa una ventana completa por red, es una buena práctica declarar la constante para eliminar el aviso del compilador.
