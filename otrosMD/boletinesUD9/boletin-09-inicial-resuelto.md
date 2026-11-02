---
title: Boletín U09 — Inicial (resuelto)
description: Soluciones del boletín inicial sobre interfaces gráficas y eventos con Swing
---

# ✅ Boletín U09 — Inicial (resuelto)

1. **Diálogo de bienvenida**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import javax.swing.JOptionPane;

   public class Bienvenida {
       public static void main(String[] args) {
           JOptionPane.showMessageDialog(null, "¡Bienvenida, Ada!");
       }
   }
   ```
   </details>

2. **Primera ventana**
   <details>
   <summary>💡 Solución</summary>

   ```java
   import javax.swing.*;

   public class MiVentana extends JFrame {
       public MiVentana() {
           setTitle("Mi primera ventana");
           setSize(400, 300);
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }

       public static void main(String[] args) {
           SwingUtilities.invokeLater(() -> new MiVentana().setVisible(true));
       }
   }
   ```
   </details>

3. **Etiqueta y botón con diálogo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   public MiVentana() {
       setTitle("Botón");
       setSize(400, 300);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(new FlowLayout());

       add(new JLabel("Pulsa el botón:"));
       JButton boton = new JButton("Pulsa aquí");
       boton.addActionListener(e -> JOptionPane.showMessageDialog(this, "¡Botón pulsado!"));
       add(boton);
   }
   ```

   Dentro de la lambda, `this` es la ventana (`MiVentana`), porque las lambdas —a diferencia de las clases anónimas— no crean un `this` propio.
   </details>

4. **Saludar**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JTextField campo = new JTextField(15);
   JButton boton = new JButton("Saludar");
   JLabel resultado = new JLabel(" ");

   boton.addActionListener(e -> resultado.setText("Hola, " + campo.getText()));

   setLayout(new FlowLayout());
   add(campo);
   add(boton);
   add(resultado);
   ```
   </details>

5. **BorderLayout**
   <details>
   <summary>💡 Solución</summary>

   ```java
   setLayout(new BorderLayout());
   add(new JLabel("Título de la ventana", SwingConstants.CENTER), BorderLayout.NORTH);
   add(new JScrollPane(new JTextArea()), BorderLayout.CENTER);
   add(new JButton("Enviar"), BorderLayout.SOUTH);
   ```
   </details>

6. **Botones de colores**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JPanel centro = new JPanel();
   JPanel botones = new JPanel(new GridLayout(1, 3));

   JButton rojo = new JButton("Rojo");
   JButton verde = new JButton("Verde");
   JButton azul = new JButton("Azul");

   rojo.addActionListener(e -> centro.setBackground(Color.RED));
   verde.addActionListener(e -> centro.setBackground(Color.GREEN));
   azul.addActionListener(e -> centro.setBackground(Color.BLUE));

   botones.add(rojo);
   botones.add(verde);
   botones.add(azul);

   setLayout(new BorderLayout());
   add(centro, BorderLayout.CENTER);
   add(botones, BorderLayout.SOUTH);
   ```
   </details>

7. **Hilo de despacho de eventos**
   <details>
   <summary>💡 Solución</summary>

   El *event dispatch thread* (EDT) es el hilo especial en el que Swing procesa todos los eventos (clics, teclas...) y redibuja los componentes. Swing no está diseñado para ser usado desde varios hilos a la vez, así que la regla es crear y modificar la interfaz siempre desde ese hilo. `main` se ejecuta en otro hilo distinto, por lo que `invokeLater` le pide a Swing que ejecute el código de construcción de la ventana en el EDT. Hacerlo directamente desde `main` suele funcionar, pero puede producir fallos intermitentes difíciles de reproducir.
   </details>

8. **Fuente y listener**
   <details>
   <summary>💡 Solución</summary>

   La **fuente** es el componente donde se origina el evento — el `JButton` que el usuario pulsa. El **listener** es un objeto distinto, que ha pedido ser avisado de esos eventos (`boton.addActionListener(listener)`) y que contiene el código que se ejecuta cuando ocurren (`actionPerformed`). Esa separación hace que el botón no necesite saber qué se hace al pulsarlo, y que un mismo listener pueda reutilizarse con varios botones, ya que el evento que recibe indica quién lo originó (`e.getSource()`).
   </details>

9. **Pedido de pizza**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JRadioButton pequena = new JRadioButton("Pequeña", true);
   JRadioButton grande = new JRadioButton("Grande");
   ButtonGroup tamanos = new ButtonGroup();
   tamanos.add(pequena);
   tamanos.add(grande);

   JCheckBox queso = new JCheckBox("Extra de queso");
   JComboBox<String> masa = new JComboBox<>(new String[]{"Fina", "Normal", "Rellena"});
   JButton pedir = new JButton("Pedir");

   pedir.addActionListener(e -> {
       String resumen = "Tamaño: " + (pequena.isSelected() ? "pequeña" : "grande")
               + "\nMasa: " + masa.getSelectedItem()
               + "\nExtra de queso: " + (queso.isSelected() ? "sí" : "no");
       JOptionPane.showMessageDialog(this, resumen, "Resumen del pedido", JOptionPane.INFORMATION_MESSAGE);
   });

   setLayout(new FlowLayout());
   add(pequena); add(grande); add(queso); add(masa); add(pedir);
   ```

   El `ButtonGroup` no se añade a la ventana: es un objeto invisible que solo se encarga de que marcar un radio desmarque el otro.
   </details>
