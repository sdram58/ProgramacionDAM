---
title: Boletín U09 — Intermedio (resuelto)
description: Soluciones del boletín intermedio sobre interfaces gráficas, MVC y dibujo con Swing
---

# ✅ Boletín U09 — Intermedio (resuelto)

1. **Calculadora con control de errores**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JTextField campoA = new JTextField(8);
   JTextField campoB = new JTextField(8);
   JLabel resultado = new JLabel("Resultado: ");

   ActionListener operar = e -> {
       try {
           double a = Double.parseDouble(campoA.getText());
           double b = Double.parseDouble(campoB.getText());
           String op = ((JButton) e.getSource()).getText();
           double r = switch (op) {
               case "+" -> a + b;
               case "-" -> a - b;
               case "x" -> a * b;
               default -> {
                   if (b == 0) throw new ArithmeticException("No se puede dividir entre cero");
                   yield a / b;
               }
           };
           resultado.setText("Resultado: " + r);
       } catch (NumberFormatException ex) {
           JOptionPane.showMessageDialog(this, "Introduce dos números válidos");
       } catch (ArithmeticException ex) {
           JOptionPane.showMessageDialog(this, ex.getMessage());
       }
   };

   for (String simbolo : new String[]{"+", "-", "x", "/"}) {
       JButton b = new JButton(simbolo);
       b.addActionListener(operar);   // el MISMO listener para los cuatro botones
       panelBotones.add(b);
   }
   ```

   Fíjate en que un único listener atiende a los cuatro botones, y averigua cuál fue pulsado preguntándole al propio evento (`e.getSource()`).
   </details>

2. **Calculadora con MVC**
   <details>
   <summary>💡 Solución</summary>

   ```java
   // MODELO — sin ninguna referencia a Swing
   public class CalculadoraModelo {
       public double operar(double a, double b, String op) {
           return switch (op) {
               case "+" -> a + b;
               case "-" -> a - b;
               case "x" -> a * b;
               case "/" -> {
                   if (b == 0) throw new ArithmeticException("No se puede dividir entre cero");
                   yield a / b;
               }
               default -> throw new IllegalArgumentException("Operación desconocida: " + op);
           };
       }
   }

   // VISTA — solo componentes, sin lógica
   public class CalculadoraVista extends JFrame {
       public final JTextField campoA = new JTextField(8);
       public final JTextField campoB = new JTextField(8);
       public final JLabel resultado = new JLabel("Resultado: ");
       public final JButton[] botones = {
           new JButton("+"), new JButton("-"), new JButton("x"), new JButton("/")
       };
       // constructor: colocar los componentes con los layouts
   }

   // CONTROLADOR — une las dos capas
   public class CalculadoraControlador {
       public CalculadoraControlador(CalculadoraVista vista, CalculadoraModelo modelo) {
           for (JButton boton : vista.botones) {
               boton.addActionListener(e -> {
                   try {
                       double a = Double.parseDouble(vista.campoA.getText());
                       double b = Double.parseDouble(vista.campoB.getText());
                       double r = modelo.operar(a, b, boton.getText());
                       vista.resultado.setText("Resultado: " + r);
                   } catch (NumberFormatException ex) {
                       JOptionPane.showMessageDialog(vista, "Introduce dos números válidos");
                   } catch (ArithmeticException ex) {
                       JOptionPane.showMessageDialog(vista, ex.getMessage());
                   }
               });
           }
       }
   }
   ```

   Ventaja: puedes probar `CalculadoraModelo` sin abrir ninguna ventana, y cambiar la vista sin tocar la lógica.
   </details>

3. **Formulario de registro**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JTextField nombre = new JTextField(15);
   JTextField email = new JTextField(15);
   JCheckBox acepto = new JCheckBox("Acepto las condiciones");
   JButton registrar = new JButton("Registrar");

   registrar.addActionListener(e -> {
       if (nombre.getText().isBlank() || email.getText().isBlank()) {
           JOptionPane.showMessageDialog(this, "Rellena todos los campos");
       } else if (!email.getText().matches("\\w+@\\w+\\.\\w+")) {
           JOptionPane.showMessageDialog(this, "El email no es válido");
       } else if (!acepto.isSelected()) {
           JOptionPane.showMessageDialog(this, "Debes aceptar las condiciones");
       } else {
           JOptionPane.showMessageDialog(this, "Registro completado");
       }
   });
   ```
   </details>

4. **Casita con paintComponent**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Casita extends JPanel {
       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           g.setColor(new Color(230, 200, 150));
           g.fillRect(100, 150, 200, 150);                       // paredes
           g.setColor(Color.RED);
           g.fillPolygon(new int[]{80, 200, 320}, new int[]{150, 60, 150}, 3);   // tejado
           g.setColor(new Color(120, 70, 20));
           g.fillRect(180, 220, 40, 80);                          // puerta
       }
   }
   ```
   </details>

5. **Círculo donde se hace clic**
   <details>
   <summary>💡 Solución</summary>

   ```java
   class Lienzo extends JPanel {
       private Point centro;

       Lienzo() {
           addMouseListener(new MouseAdapter() {
               @Override
               public void mousePressed(MouseEvent e) {
                   centro = e.getPoint();
                   repaint();                  // pide a Swing que vuelva a dibujar
               }
           });
       }

       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           if (centro != null) {
               g.setColor(Color.BLUE);
               g.fillOval(centro.x - 20, centro.y - 20, 40, 40);
           }
       }
   }
   ```
   </details>

6. **Cronómetro con Timer**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JLabel etiqueta = new JLabel("0 s");
   JButton boton = new JButton("Iniciar");
   int[] segundos = {0};                                   // array para poder modificarlo dentro de la lambda
   Timer timer = new Timer(1000, e -> etiqueta.setText(++segundos[0] + " s"));

   boton.addActionListener(e -> {
       if (timer.isRunning()) {
           timer.stop();
           boton.setText("Iniciar");
       } else {
           timer.start();
           boton.setText("Parar");
       }
   });
   ```

   Una lambda no puede modificar una variable local `int` normal (debe ser *efectivamente final*); un array de un elemento es un truco habitual para guardar un valor que sí cambia.
   </details>

7. **Editor de texto mínimo**
   <details>
   <summary>💡 Solución</summary>

   ```java
   JTextArea area = new JTextArea();
   add(new JScrollPane(area), BorderLayout.CENTER);

   JMenuBar barra = new JMenuBar();
   JMenu archivo = new JMenu("Archivo");
   JMenuItem abrir = new JMenuItem("Abrir");
   JMenuItem guardar = new JMenuItem("Guardar");

   abrir.addActionListener(e -> {
       JFileChooser selector = new JFileChooser();
       if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           try {
               area.setText(Files.readString(selector.getSelectedFile().toPath()));
           } catch (IOException ex) {
               JOptionPane.showMessageDialog(this, "No se pudo abrir el fichero");
           }
       }
   });

   guardar.addActionListener(e -> {
       JFileChooser selector = new JFileChooser();
       if (selector.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
           try {
               Files.writeString(selector.getSelectedFile().toPath(), area.getText());
           } catch (IOException ex) {
               JOptionPane.showMessageDialog(this, "No se pudo guardar el fichero");
           }
       }
   });

   archivo.add(abrir);
   archivo.add(guardar);
   barra.add(archivo);
   setJMenuBar(barra);
   ```
   </details>

8. **Por qué se congela la ventana**
   <details>
   <summary>💡 Solución</summary>

   El `actionPerformed` se ejecuta **en el hilo de despacho de eventos**, el mismo que atiende los clics y redibuja la ventana. Si dentro del listener haces `Thread.sleep(10000)` (o cualquier cálculo o lectura de 10 segundos), ese hilo queda ocupado y no puede hacer nada más hasta terminar. El usuario verá que la ventana no responde a clics, no se redibuja (si la tapa otra ventana, queda "en blanco") y el sistema operativo puede incluso mostrarla como "no responde". Las tareas largas deben ejecutarse en otro hilo, dejando libre el de eventos.
   </details>
