---
title: Boletín U09 — Extras
description: Retos de programación con interfaces gráficas y eventos
---

# 🔥 Boletín U09 — Extras

> Las plataformas tipo CodeWars o AceptaElReto no sirven para interfaces gráficas, así que los retos de esta unidad son pequeños proyectos. Aquí tienes dos.

## Reto 1: el juego de adivinar el número

Crea una ventana con un campo de texto, un botón "Probar" y una etiqueta de pistas. El programa elige un número secreto entre 1 y 100; cada vez que el usuario pulsa "Probar", la etiqueta debe decir "Más alto", "Más bajo" o "¡Acertaste en N intentos!". Cuando acierte, el botón debe cambiar a "Jugar otra vez" y reiniciar la partida.

:::tip[Pista]
Guarda el número secreto y el contador de intentos como atributos de la ventana (no como variables locales del listener), para que conserven su valor entre un clic y el siguiente.
:::

<details>
<summary>💡 Solución</summary>

```java
public class Adivina extends JFrame {
    private int secreto = nuevoSecreto();
    private int intentos = 0;
    private final JTextField campo = new JTextField(5);
    private final JButton boton = new JButton("Probar");
    private final JLabel pista = new JLabel("Piensa un número del 1 al 100");

    private static int nuevoSecreto() {
        return (int) (Math.random() * 100) + 1;
    }

    public Adivina() {
        setTitle("Adivina el número");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        add(campo);
        add(boton);
        add(pista);
        pack();

        boton.addActionListener(e -> {
            if (boton.getText().equals("Jugar otra vez")) {
                secreto = nuevoSecreto();
                intentos = 0;
                boton.setText("Probar");
                pista.setText("Piensa un número del 1 al 100");
                campo.setText("");
                return;
            }
            try {
                int n = Integer.parseInt(campo.getText().trim());
                intentos++;
                if (n < secreto) pista.setText("Más alto");
                else if (n > secreto) pista.setText("Más bajo");
                else {
                    pista.setText("¡Acertaste en " + intentos + " intentos!");
                    boton.setText("Jugar otra vez");
                }
            } catch (NumberFormatException ex) {
                pista.setText("Escribe un número entero");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Adivina().setVisible(true));
    }
}
```
</details>

## Reto 2: una pelota que rebota

Dibuja en un `JPanel` una pelota que se mueva sola y rebote en los bordes de la ventana, usando un `javax.swing.Timer` cada ~16 ms (unos 60 fotogramas por segundo). Añade un botón para pausar y reanudar la animación.

:::tip[Pista]
En cada tick del `Timer`: suma la velocidad a la posición, invierte el signo de la velocidad si la pelota toca un borde, y llama a `repaint()`. El dibujo en sí va en `paintComponent`.
:::

<details>
<summary>💡 Solución</summary>

```java
class Pelota extends JPanel {
    private int x = 50, y = 50, vx = 3, vy = 2;
    private static final int DIAMETRO = 30;
    private final Timer timer = new Timer(16, e -> {
        x += vx;
        y += vy;
        if (x < 0 || x + DIAMETRO > getWidth()) vx = -vx;
        if (y < 0 || y + DIAMETRO > getHeight()) vy = -vy;
        repaint();
    });

    public void alternar() {
        if (timer.isRunning()) timer.stop(); else timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, DIAMETRO, DIAMETRO);
    }
}

// En la ventana:
Pelota pelota = new Pelota();
JButton pausa = new JButton("Pausar / Reanudar");
pausa.addActionListener(e -> pelota.alternar());
add(pelota, BorderLayout.CENTER);
add(pausa, BorderLayout.SOUTH);
pelota.alternar();   // arranca la animación
```

Todo ocurre en el hilo de eventos: el `Timer` de Swing dispara su `ActionEvent` en ese hilo, así que modificar `x`, `y` y llamar a `repaint()` desde ahí es seguro, sin tener que crear hilos propios.
</details>
