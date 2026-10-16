---
draft: true
prev: false
next: false
title: "📝 Unidad 9 — Boletín Intermedio (Resuelto)"
description: "Soluciones completas y detalladas de los ejercicios intermedios de Java Swing: tablas, listas, componentes avanzados, gráficos y temporizadores."
---

:::note[Buenas Prácticas de Código]
En todos los ejercicios se aplica el encapsulamiento de componentes, inicialización dentro del EDT (`SwingUtilities.invokeLater`), captura de excepciones y configuración segura de modelos de datos.
:::

---

### Solución Ejercicio 1: Calculadora de Cuatro Operaciones

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;

public class Ejercicio01Calculadora extends JFrame {

    private JTextField txtVisor;
    private double primerOperando = 0.0;
    private String operadorPendiente = "";
    private boolean inicioNuevoNumero = true;

    public Ejercicio01Calculadora() {
        super("Calculadora DAM");
        this.setSize(320, 420);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));

        txtVisor = new JTextField("0");
        txtVisor.setEditable(false);
        txtVisor.setHorizontalAlignment(JTextField.RIGHT);
        txtVisor.setFont(new Font("Segoe UI", Font.BOLD, 26));
        txtVisor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(txtVisor, BorderLayout.NORTH);

        JPanel panelTeclas = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] botones = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String texto : botones) {
            JButton btn = new JButton(texto);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.addActionListener(e -> procesarTecla(texto));
            panelTeclas.add(btn);
        }

        this.add(panelTeclas, BorderLayout.CENTER);
    }

    private void procesarTecla(String tecla) {
        if ("0123456789".contains(tecla)) {
            if (inicioNuevoNumero || txtVisor.getText().equals("0")) {
                txtVisor.setText(tecla);
                inicioNuevoNumero = false;
            } else {
                txtVisor.setText(txtVisor.getText() + tecla);
            }
        } else if (tecla.equals("C")) {
            txtVisor.setText("0");
            primerOperando = 0.0;
            operadorPendiente = "";
            inicioNuevoNumero = true;
        } else if ("+-*/".contains(tecla)) {
            primerOperando = Double.parseDouble(txtVisor.getText());
            operadorPendiente = tecla;
            inicioNuevoNumero = true;
        } else if (tecla.equals("=")) {
            if (!operadorPendiente.isEmpty()) {
                double segundoOperando = Double.parseDouble(txtVisor.getText());
                double resultado = 0.0;
                boolean error = false;

                switch (operadorPendiente) {
                    case "+" -> resultado = primerOperando + segundoOperando;
                    case "-" -> resultado = primerOperando - segundoOperando;
                    case "*" -> resultado = primerOperando * segundoOperando;
                    case "/" -> {
                        if (segundoOperando == 0) {
                            error = true;
                        } else {
                            resultado = primerOperando / segundoOperando;
                        }
                    }
                }

                if (error) {
                    txtVisor.setText("Error: Div 0");
                } else {
                    txtVisor.setText(resultado % 1 == 0 ? String.valueOf((long) resultado) : String.valueOf(resultado));
                }
                operadorPendiente = "";
                inicioNuevoNumero = true;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio01Calculadora().setVisible(true));
    }
}
```

---

### Solución Ejercicio 2: Gestor de Tareas Pendientes con `JList` y `DefaultListModel`

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;

public class Ejercicio02GestorTareas extends JFrame {

    private DefaultListModel<String> modeloTareas;
    private JList<String> listaTareas;
    private JTextField txtNuevaTarea;

    public Ejercicio02GestorTareas() {
        super("Gestor de Tareas Pendientes");
        this.setSize(450, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        txtNuevaTarea = new JTextField();
        JButton btnAgregar = new JButton("Añadir Tarea");
        panelSuperior.add(txtNuevaTarea, BorderLayout.CENTER);
        panelSuperior.add(btnAgregar, BorderLayout.EAST);
        this.add(panelSuperior, BorderLayout.NORTH);

        modeloTareas = new DefaultListModel<>();
        modeloTareas.addElement("Repasar conceptos de Java Swing");
        modeloTareas.addElement("Terminar proyecto de Base de Datos");
        listaTareas = new JList<>(modeloTareas);
        listaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.add(new JScrollPane(listaTareas), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnEliminar = new JButton("Eliminar Seleccionada");
        JButton btnSubir = new JButton("Mover Arriba");
        JButton btnLimpiar = new JButton("Limpiar Todo");
        panelInferior.add(btnEliminar);
        panelInferior.add(btnSubir);
        panelInferior.add(btnLimpiar);
        this.add(panelInferior, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {
            String texto = txtNuevaTarea.getText().trim();
            if (!texto.isEmpty()) {
                modeloTareas.addElement(texto);
                txtNuevaTarea.setText("");
                txtNuevaTarea.requestFocus();
            }
        });

        btnEliminar.addActionListener(e -> {
            int indice = listaTareas.getSelectedIndex();
            if (indice != -1) {
                modeloTareas.remove(indice);
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una tarea de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnSubir.addActionListener(e -> {
            int indice = listaTareas.getSelectedIndex();
            if (indice > 0) {
                String elemento = modeloTareas.remove(indice);
                modeloTareas.add(indice - 1, elemento);
                listaTareas.setSelectedIndex(indice - 1);
            }
        });

        btnLimpiar.addActionListener(e -> {
            if (!modeloTareas.isEmpty()) {
                int resp = JOptionPane.showConfirmDialog(this, "¿Vaciar todas las tareas?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    modeloTareas.clear();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio02GestorTareas().setVisible(true));
    }
}
```

---

### Solución Ejercicio 3: Desplegables Dependientes con `JComboBox`

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Ejercicio03CombosDependientes extends JFrame {

    private JComboBox<String> comboPais;
    private JComboBox<String> comboCiudad;
    private JLabel lblResultado;
    private Map<String, String[]> datosCiudades;

    public Ejercicio03CombosDependientes() {
        super("Selector de Destino de Viaje");
        this.setSize(450, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3, 1, 10, 10));

        datosCiudades = new HashMap<>();
        datosCiudades.put("España", new String[]{"Madrid", "Barcelona", "Valencia", "Sevilla"});
        datosCiudades.put("Francia", new String[]{"París", "Lyon", "Marsella"});
        datosCiudades.put("Italia", new String[]{"Roma", "Milán", "Nápoles"});

        JPanel panelCombos = new JPanel(new FlowLayout());
        comboPais = new JComboBox<>(new String[]{"España", "Francia", "Italia"});
        comboCiudad = new JComboBox<>();
        panelCombos.add(new JLabel("País:"));
        panelCombos.add(comboPais);
        panelCombos.add(new JLabel("Ciudad:"));
        panelCombos.add(comboCiudad);
        this.add(panelCombos);

        lblResultado = new JLabel("Seleccione un destino", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 15));
        this.add(lblResultado);

        comboPais.addActionListener(e -> actualizarCiudades());
        comboCiudad.addActionListener(e -> actualizarEtiqueta());

        actualizarCiudades();
    }

    private void actualizarCiudades() {
        String paisSeleccionado = (String) comboPais.getSelectedItem();
        comboCiudad.removeAllItems();
        if (paisSeleccionado != null && datosCiudades.containsKey(paisSeleccionado)) {
            for (String ciudad : datosCiudades.get(paisSeleccionado)) {
                comboCiudad.addItem(ciudad);
            }
        }
    }

    private void actualizarEtiqueta() {
        String pais = (String) comboPais.getSelectedItem();
        String ciudad = (String) comboCiudad.getSelectedItem();
        if (pais != null && ciudad != null) {
            lblResultado.setText("Destino elegido: " + ciudad + " (" + pais + ")");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio03CombosDependientes().setVisible(true));
    }
}
```

---

### Solución Ejercicio 4: Bloc de Notas con Menú y `JFileChooser`

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Ejercicio04EditorTexto extends JFrame {

    private JTextArea areaTexto;
    private File ficheroActual = null;

    public Ejercicio04EditorTexto() {
        super("Editor de Texto Sencillo");
        this.setSize(600, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaTexto.setLineWrap(true);
        this.add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        crearBarraMenu();
    }

    private void crearBarraMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemNuevo = new JMenuItem("Nuevo");
        JMenuItem itemAbrir = new JMenuItem("Abrir...");
        JMenuItem itemGuardar = new JMenuItem("Guardar Como...");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemNuevo.addActionListener(e -> {
            areaTexto.setText("");
            ficheroActual = null;
            this.setTitle("Editor de Texto Sencillo");
        });

        itemAbrir.addActionListener(e -> abrirFichero());
        itemGuardar.addActionListener(e -> guardarComo());
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        barra.add(menuArchivo);
        this.setJMenuBar(barra);
    }

    private void abrirFichero() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ficheroActual = chooser.getSelectedFile();
            try {
                String contenido = Files.readString(ficheroActual.toPath());
                areaTexto.setText(contenido);
                this.setTitle("Editor - " + ficheroActual.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer fichero: " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarComo() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            ficheroActual = chooser.getSelectedFile();
            try {
                Files.writeString(ficheroActual.toPath(), areaTexto.getText());
                this.setTitle("Editor - " + ficheroActual.getName());
                JOptionPane.showMessageDialog(this, "Fichero guardado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar fichero: " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio04EditorTexto().setVisible(true));
    }
}
```

---

### Solución Ejercicio 5: Mantenimiento de Alumnos con `JTable`

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Ejercicio05MantenimientoAlumnos extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tablaAlumnos;
    private JTextField txtDni, txtNombre, txtNota;
    private JComboBox<String> comboCiclo;

    public Ejercicio05MantenimientoAlumnos() {
        super("Gestión Escolar - Alumnos");
        this.setSize(650, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(2, 4, 10, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Nuevo Alumno"));

        txtDni = new JTextField();
        txtNombre = new JTextField();
        txtNota = new JTextField();
        comboCiclo = new JComboBox<>(new String[]{"DAM", "DAW", "ASIR"});

        panelForm.add(new JLabel("DNI:"));
        panelForm.add(new JLabel("Nombre Completo:"));
        panelForm.add(new JLabel("Ciclo:"));
        panelForm.add(new JLabel("Nota Media:"));

        panelForm.add(txtDni);
        panelForm.add(txtNombre);
        panelForm.add(comboCiclo);
        panelForm.add(txtNota);

        this.add(panelForm, BorderLayout.NORTH);

        String[] columnas = {"DNI", "Nombre", "Ciclo", "Nota Media"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaAlumnos = new JTable(modeloTabla);
        this.add(new JScrollPane(tablaAlumnos), BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnInsertar = new JButton("Insertar Alumno");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");

        btnInsertar.addActionListener(e -> insertarAlumno());
        btnEliminar.addActionListener(e -> {
            int fila = tablaAlumnos.getSelectedRow();
            if (fila != -1) {
                modeloTabla.removeRow(fila);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        panelAcciones.add(btnInsertar);
        panelAcciones.add(btnEliminar);
        this.add(panelAcciones, BorderLayout.SOUTH);
    }

    private void insertarAlumno() {
        String dni = txtDni.getText().trim();
        String nom = txtNombre.getText().trim();
        String ciclo = (String) comboCiclo.getSelectedItem();
        String notaStr = txtNota.getText().trim();

        if (dni.isEmpty() || nom.isEmpty() || notaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double nota = Double.parseDouble(notaStr.replace(',', '.'));
            if (nota < 0.0 || nota > 10.0) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 10.", "Nota Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            modeloTabla.addRow(new Object[]{dni, nom, ciclo, String.format("%.2f", nota)});
            txtDni.setText("");
            txtNombre.setText("");
            txtNota.setText("");
            txtDni.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La nota debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio05MantenimientoAlumnos().setVisible(true));
    }
}
```

---

### Solución Ejercicio 6: Panel Multitarea con `JTabbedPane`, `JSlider` y `JSpinner`

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ejercicio06PestaniasAvanzadas extends JFrame {

    private JSlider sliderVolumen;
    private JCheckBox chkMute;
    private JSpinner spinnerPersonas;
    private JSpinner spinnerFecha;
    private JTextArea areaResumen;

    public Ejercicio06PestaniasAvanzadas() {
        super("Configuración y Reservas");
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pestanias = new JTabbedPane();

        // Pestaña 1: Sonido
        JPanel panelSonido = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        sliderVolumen = new JSlider(0, 100, 50);
        sliderVolumen.setMajorTickSpacing(25);
        sliderVolumen.setMinorTickSpacing(5);
        sliderVolumen.setPaintTicks(true);
        sliderVolumen.setPaintLabels(true);

        chkMute = new JCheckBox("Silencio (Mute)");
        chkMute.addActionListener(e -> sliderVolumen.setEnabled(!chkMute.isSelected()));

        panelSonido.add(new JLabel("Volumen:"));
        panelSonido.add(sliderVolumen);
        panelSonido.add(chkMute);
        pestanias.addTab("Ajustes de Sonido", panelSonido);

        // Pestaña 2: Reserva
        JPanel panelReserva = new JPanel(new GridLayout(2, 2, 10, 20));
        panelReserva.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        spinnerPersonas = new JSpinner(new SpinnerNumberModel(2, 1, 12, 1));
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy HH:mm");
        spinnerFecha.setEditor(editorFecha);

        panelReserva.add(new JLabel("Número de comensales:"));
        panelReserva.add(spinnerPersonas);
        panelReserva.add(new JLabel("Fecha y Hora:"));
        panelReserva.add(spinnerFecha);
        pestanias.addTab("Configuración de Reserva", panelReserva);

        // Pestaña 3: Resumen
        JPanel panelResumen = new JPanel(new BorderLayout(10, 10));
        areaResumen = new JTextArea();
        areaResumen.setEditable(false);
        JButton btnGenerar = new JButton("Generar Resumen");
        btnGenerar.addActionListener(e -> actualizarResumen());

        panelResumen.add(new JScrollPane(areaResumen), BorderLayout.CENTER);
        panelResumen.add(btnGenerar, BorderLayout.SOUTH);
        pestanias.addTab("Resumen", panelResumen);

        this.add(pestanias);
    }

    private void actualizarResumen() {
        int vol = chkMute.isSelected() ? 0 : sliderVolumen.getValue();
        int personas = (int) spinnerPersonas.getValue();
        Date fecha = (Date) spinnerFecha.getValue();
        String fechaFmt = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fecha);

        String texto = "=== RESUMEN DE CONFIGURACIÓN ===\n"
                     + "• Nivel de Audio: " + (chkMute.isSelected() ? "SILENCIADO (0%)" : vol + "%") + "\n"
                     + "• Comensales reservados: " + personas + "\n"
                     + "• Fecha y Hora: " + fechaFmt + "\n";
        areaResumen.setText(texto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio06PestaniasAvanzadas().setVisible(true));
    }
}
```

---

### Solución Ejercicio 7: Lienzo de Dibujo Interactivo con Ratón

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio07LienzoInteractivo extends JFrame {

    private static class PanelLienzo extends JPanel {
        private final List<Point> puntos = new ArrayList<>();

        public PanelLienzo() {
            this.setBackground(Color.WHITE);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    puntos.add(e.getPoint());
                    repaint();
                }
            });
        }

        public void limpiar() {
            puntos.clear();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(45, 138, 78)); // Verde calma
            g2.setStroke(new BasicStroke(2.0f));

            for (int i = 0; i < puntos.size(); i++) {
                Point p = puntos.get(i);
                // Dibujar círculo centrado
                g2.fillOval(p.x - 6, p.y - 6, 12, 12);

                // Trazar línea al punto anterior
                if (i > 0) {
                    Point prev = puntos.get(i - 1);
                    g2.drawLine(prev.x, prev.y, p.x, p.y);
                }
            }
        }
    }

    public Ejercicio07LienzoInteractivo() {
        super("Lienzo de Trazos con Ratón");
        this.setSize(550, 420);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        PanelLienzo lienzo = new PanelLienzo();
        this.add(lienzo, BorderLayout.CENTER);

        JButton btnLimpiar = new JButton("Limpiar Lienzo");
        btnLimpiar.addActionListener(e -> lienzo.limpiar());
        JPanel panelSur = new JPanel(new FlowLayout());
        panelSur.add(btnLimpiar);
        this.add(panelSur, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio07LienzoInteractivo().setVisible(true));
    }
}
```

---

### Solución Ejercicio 8: Cronómetro Digital con `javax.swing.Timer`

```java
package com.dam.boletin09.intermedio;

import javax.swing.*;
import java.awt.*;

public class Ejercicio08Cronometro extends JFrame {

    private JLabel lblTiempo;
    private JButton btnIniciar, btnPausar, btnReiniciar;
    private Timer timer;
    private int decimasTranscurridas = 0;

    public Ejercicio08Cronometro() {
        super("Cronómetro Digital");
        this.setSize(380, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        lblTiempo = new JLabel("00:00.0", SwingConstants.CENTER);
        lblTiempo.setFont(new Font("Consolas", Font.BOLD, 42));
        lblTiempo.setForeground(new Color(33, 37, 41));
        this.add(lblTiempo, BorderLayout.CENTER);

        // Temporizador disparado cada 100 milisegundos (1 décima)
        timer = new Timer(100, e -> {
            decimasTranscurridas++;
            actualizarVisor();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnReiniciar = new JButton("Reiniciar");

        btnIniciar.addActionListener(e -> {
            timer.start();
            btnIniciar.setEnabled(false);
            btnPausar.setEnabled(true);
        });

        btnPausar.addActionListener(e -> {
            timer.stop();
            btnIniciar.setEnabled(true);
            btnPausar.setEnabled(false);
        });

        btnReiniciar.addActionListener(e -> {
            timer.stop();
            decimasTranscurridas = 0;
            actualizarVisor();
            btnIniciar.setEnabled(true);
            btnPausar.setEnabled(false);
        });

        btnPausar.setEnabled(false);
        panelBotones.add(btnIniciar);
        panelBotones.add(btnPausar);
        panelBotones.add(btnReiniciar);

        this.add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarVisor() {
        int decimas = decimasTranscurridas % 10;
        int totalSegundos = decimasTranscurridas / 10;
        int segundos = totalSegundos % 60;
        int minutos = totalSegundos / 60;

        lblTiempo.setText(String.format("%02d:%02d.%d", minutos, segundos, decimas));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio08Cronometro().setVisible(true));
    }
}
```
