---
draft: true
prev: false
next: false
title: "📝 Unidad 9 — Boletín Inicial (Resuelto)"
description: "Soluciones completas, comentadas y explicadas paso a paso para el boletín inicial de Java Swing y programación orientada a eventos."
---

:::note[Entorno de Ejecución]
Todas las soluciones están implementadas en Java LTS moderno, empleando expresiones Lambda para los listeners y garantizando la invocación dentro del hilo despachador de eventos (EDT) mediante `SwingUtilities.invokeLater`.
:::

---

### Solución Ejercicio 1: Hola Mundo Gráfico

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio01HolaMundo extends JFrame {

    public Ejercicio01HolaMundo() {
        super("Primer Programa Gráfico - DAM");
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        this.setSize(400, 200);
        this.setLocationRelativeTo(null); // Centra en la pantalla
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        this.setLayout(new BorderLayout());

        JLabel lblMensaje = new JLabel("¡Bienvenido a Java Swing!", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 18));
        this.add(lblMensaje, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar Ventana");
        btnCerrar.addActionListener(e -> System.exit(0));
        this.add(btnCerrar, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ejercicio01HolaMundo().setVisible(true);
        });
    }
}
```

---

### Solución Ejercicio 2: Contador de Clics

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio02Contador extends JFrame {

    private int contador = 0;
    private JLabel lblContador;

    public Ejercicio02Contador() {
        super("Contador Interactivo");
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        this.setSize(350, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        this.setLayout(new BorderLayout());

        lblContador = new JLabel("Clics: 0", SwingConstants.CENTER);
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(lblContador, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnIncrementar = new JButton("Incrementar (+1)");
        JButton btnDecrementar = new JButton("Decrementar (-1)");
        JButton btnReiniciar = new JButton("Reiniciar");

        btnIncrementar.addActionListener(e -> {
            contador++;
            actualizarEtiqueta();
        });

        btnDecrementar.addActionListener(e -> {
            if (contador > 0) {
                contador--;
                actualizarEtiqueta();
            }
        });

        btnReiniciar.addActionListener(e -> {
            contador = 0;
            actualizarEtiqueta();
        });

        panelBotones.add(btnIncrementar);
        panelBotones.add(btnDecrementar);
        panelBotones.add(btnReiniciar);

        this.add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarEtiqueta() {
        lblContador.setText("Clics: " + contador);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio02Contador().setVisible(true));
    }
}
```

---

### Solución Ejercicio 3: Conversor de Divisas Simple

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio03Conversor extends JFrame {

    private static final double TASA_EUR_A_USD = 1.08;

    public Ejercicio03Conversor() {
        super("Conversor EUR a USD");
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        this.setSize(400, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        JLabel lblEtiqueta = new JLabel("Importe en Euros (€):");
        JTextField txtEuros = new JTextField(10);
        panelEntrada.add(lblEtiqueta);
        panelEntrada.add(txtEuros);
        this.add(panelEntrada, BorderLayout.NORTH);

        JLabel lblResultado = new JLabel("Resultado: 0.00 USD", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 18));
        this.add(lblResultado, BorderLayout.CENTER);

        JButton btnConvertir = new JButton("Convertir a USD");
        btnConvertir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConvertir.addActionListener(e -> {
            String texto = txtEuros.getText().trim();
            try {
                double euros = Double.parseDouble(texto.replace(',', '.'));
                if (euros < 0) {
                    JOptionPane.showMessageDialog(this, "El importe no puede ser negativo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double dolares = euros * TASA_EUR_A_USD;
                lblResultado.setText(String.format("Resultado: %.2f USD", dolares));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe introducir un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBoton.add(btnConvertir);
        this.add(panelBoton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio03Conversor().setVisible(true));
    }
}
```

---

### Solución Ejercicio 4: Cambiador de Color de Fondo

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio04ColorDeFondo extends JFrame {

    private JPanel panelLienzo;
    private final Color COLOR_DEFECTO = new Color(240, 240, 240);

    public Ejercicio04ColorDeFondo() {
        super("Selector de Color de Fondo");
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        panelLienzo = new JPanel();
        panelLienzo.setBackground(COLOR_DEFECTO);
        this.add(panelLienzo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRojo = new JButton("Rojo");
        JButton btnVerde = new JButton("Verde");
        JButton btnAzul = new JButton("Azul");
        JButton btnRestablecer = new JButton("Restablecer");

        btnRojo.addActionListener(e -> panelLienzo.setBackground(new Color(220, 53, 69)));
        btnVerde.addActionListener(e -> panelLienzo.setBackground(new Color(40, 167, 69)));
        btnAzul.addActionListener(e -> panelLienzo.setBackground(new Color(0, 123, 255)));
        btnRestablecer.addActionListener(e -> panelLienzo.setBackground(COLOR_DEFECTO));

        panelBotones.add(btnRojo);
        panelBotones.add(btnVerde);
        panelBotones.add(btnAzul);
        panelBotones.add(btnRestablecer);

        this.add(panelBotones, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio04ColorDeFondo().setVisible(true));
    }
}
```

---

### Solución Ejercicio 5: Reflejo en Tiempo Real (Espejo de Texto)

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Ejercicio05EspejoTexto extends JFrame {

    public Ejercicio05EspejoTexto() {
        super("Espejo de Texto en Tiempo Real");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel panelSuperior = new JPanel(new FlowLayout());
        JTextField txtEntrada = new JTextField(20);
        panelSuperior.add(new JLabel("Escribe aquí:"));
        panelSuperior.add(txtEntrada);

        JLabel lblEspejo = new JLabel("Escribe algo arriba...", SwingConstants.CENTER);
        lblEspejo.setFont(new Font("Segoe UI", Font.ITALIC, 16));

        // DocumentListener escucha inserciones, borrados y cambios de atributos con total fiabilidad
        txtEntrada.getDocument().addDocumentListener(new DocumentListener() {
            private void actualizar() {
                String t = txtEntrada.getText().trim();
                if (t.isEmpty()) {
                    lblEspejo.setText("Escribe algo arriba...");
                    lblEspejo.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                } else {
                    lblEspejo.setText("Has escrito: " + t);
                    lblEspejo.setFont(new Font("Segoe UI", Font.BOLD, 16));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) { actualizar(); }

            @Override
            public void removeUpdate(DocumentEvent e) { actualizar(); }

            @Override
            public void changedUpdate(DocumentEvent e) { actualizar(); }
        });

        this.add(panelSuperior);
        this.add(lblEspejo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio05EspejoTexto().setVisible(true));
    }
}
```

---

### Solución Ejercicio 6: Conmutador de Visibilidad con CheckBox

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio06PasswordVisibility extends JFrame {

    public Ejercicio06PasswordVisibility() {
        super("Visibilidad de Password");
        this.setSize(380, 180);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        this.add(new JLabel("Contraseña:"));
        JPasswordField txtPass = new JPasswordField(15);
        final char ecoPorDefecto = txtPass.getEchoChar();
        this.add(txtPass);

        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.addActionListener(e -> {
            if (chkMostrar.isSelected()) {
                // (char) 0 desactiva el ocultamiento de caracteres
                txtPass.setEchoChar((char) 0);
            } else {
                txtPass.setEchoChar(ecoPorDefecto);
            }
        });
        this.add(chkMostrar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio06PasswordVisibility().setVisible(true));
    }
}
```

---

### Solución Ejercicio 7: Selector de Tipografía con Radio Buttons

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio07TamanioFuente extends JFrame {

    private JLabel lblMuestra;

    public Ejercicio07TamanioFuente() {
        super("Selector de Tamaño de Tipografía");
        this.setSize(450, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        lblMuestra = new JLabel("Java Swing en IntelliJ IDEA", SwingConstants.CENTER);
        lblMuestra.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        this.add(lblMuestra, BorderLayout.CENTER);

        JPanel panelRadios = new JPanel(new FlowLayout());
        JRadioButton rbPequenio = new JRadioButton("Pequeño (12 pt)");
        JRadioButton rbMediano = new JRadioButton("Mediano (16 pt)", true);
        JRadioButton rbGrande = new JRadioButton("Grande (22 pt)");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbPequenio);
        grupo.add(rbMediano);
        grupo.add(rbGrande);

        rbPequenio.addActionListener(e -> cambiarTamanio(12));
        rbMediano.addActionListener(e -> cambiarTamanio(16));
        rbGrande.addActionListener(e -> cambiarTamanio(22));

        panelRadios.add(rbPequenio);
        panelRadios.add(rbMediano);
        panelRadios.add(rbGrande);

        this.add(panelRadios, BorderLayout.SOUTH);
    }

    private void cambiarTamanio(int tamanio) {
        lblMuestra.setFont(new Font("Segoe UI", Font.PLAIN, tamanio));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio07TamanioFuente().setVisible(true));
    }
}
```

---

### Solución Ejercicio 8: Formulario de Contacto con Validación y Diálogos

```java
package com.dam.boletin09.inicial;

import javax.swing.*;
import java.awt.*;

public class Ejercicio08FormularioContacto extends JFrame {

    private JTextField txtNombre;
    private JTextField txtEmail;

    public Ejercicio08FormularioContacto() {
        super("Formulario de Contacto");
        this.setSize(420, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 15));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelCampos.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Correo Electrónico:"));
        txtEmail = new JTextField();
        panelCampos.add(txtEmail);

        this.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnEnviar = new JButton("Enviar");

        btnLimpiar.addActionListener(e -> {
            txtNombre.setText("");
            txtEmail.setText("");
            txtNombre.requestFocus();
        });

        btnEnviar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();

            if (nombre.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "El correo electrónico no tiene un formato válido.", "Email incorrecto", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Formulario enviado con éxito para: " + nombre, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEnviar);
        this.add(panelBotones, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio08FormularioContacto().setVisible(true));
    }
}
```
