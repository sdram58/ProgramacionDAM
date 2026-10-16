---
draft: true
prev: false
next: false
title: "⚡ Unidad 9 — Retos, Proyectos y Extras"
description: "Proyectos y retos avanzados de interfaces gráficas con Java Swing: juegos de reflejos, TPV comercial interactivo y visualizador algorítmico concurrente."
---

:::tip[Retos de Nivel Avanzado]
Estos retos combinan múltiples aspectos del módulo: arquitectura Modelo-Vista-Controlador (MVC), gestión concurrente fuera del EDT, diseño visual profesional y algoritmos interactivos.
:::

---

## 🎮 Proyecto 1: Minijuego "Caza al Botón Fugitivo"

### Descripción del Reto
Diseña un juego de reflejos y agilidad con el ratón:
* Una ventana fija de 600x400 píxeles.
* Un panel de juego que utiliza un layout nulo (`setLayout(null)`) exclusivamente en este contenedor para permitir movimiento libre de un botón mediante coordenadas `(x, y)`.
* Un botón `JButton` que representa el "Objetivo".
* Cada vez que el cursor del ratón entra en el área del botón (`mouseEntered`), o si transcurren 2 segundos sin que el usuario logre atraparlo, el botón salta instantáneamente a una coordenada aleatoria válida dentro de los márgenes visibles de la ventana.
* Si el usuario consigue hacer clic con éxito sobre el botón:
  * Suma 10 puntos al marcador superior.
  * Emite un pitido del sistema (`Toolkit.getDefaultToolkit().beep()`).
  * Incrementa la velocidad de evasión.
* Dispone de un temporizador regresivo de 30 segundos. Al llegar a 0, se detiene el juego y se muestra la puntuación final mediante un diálogo `JOptionPane`.

### Pistas de Implementación
1. Calcula las coordenadas aleatorias garantizando que el botón nunca quede cortado fuera de la ventana:
   `int maxX = panel.getWidth() - boton.getWidth();`
   `int maxY = panel.getHeight() - boton.getHeight();`
2. Utiliza dos instancias de `javax.swing.Timer`: una para la cuenta regresiva global de 30 segundos y otra para el desplazamiento automático del botón.

---

## 🍔 Proyecto 2: Simulador de Terminal Punto de Venta (TPV Fast-Food)

### Descripción del Reto
Crea una interfaz de cobro comercial para un restaurante de comida rápida inspirada en el material de aula:

```
+-------------------------------------------------------------------------------+
|                        SISTEMA TPV FAST-FOOD DAM                             |
+---------------------------------------+---------------------------------------+
|  CATÁLOGO DE PRODUCTOS (GridLayout)   |  TICKET ACTUAL (JTable + Totales)     |
|                                       |                                       |
|  [🍔 Hamburguesa Deluxe - 6.50 €]     |  Producto       Cant.  Precio Subtot  |
|  [🍟 Patatas Rústicas   - 2.80 €]     |  -----------------------------------  |
|  [🥤 Refresco Grande    - 2.20 €]     |  Hamburguesa     2     6.50   13.00 € |
|  [🍗 Alitas Crujientes  - 4.90 €]     |  Refresco        1     2.20    2.20 € |
|  [🍦 Helado Sundae      - 2.50 €]     |                                       |
|  [☕ Café Expreso       - 1.50 €]     |  Subtotal: 15.20 € | IVA (10%): 1.52 €|
|                                       |  TOTAL A PAGAR: 16.72 €               |
|                                       |  [❌ Quitar] [🧹 Cancelar] [💳 Cobrar] |
+---------------------------------------+---------------------------------------+
```

### Requisitos Técnicos
1. **Modelo de Datos:** Clase `Producto(String nombre, double precio)` y clase `LineaPedido(Producto producto, int cantidad)`.
2. **Componentes Visuales:**
   * Panel izquierdo con un `GridLayout(3, 2, 10, 10)` conteniendo botones estilizados para cada producto.
   * Panel derecho con una `JTable` gobernada por un `DefaultTableModel` con las columnas `Producto`, `Cantidad`, `Precio Unitario` y `Subtotal`.
3. **Lógica de Añadido:** Al pulsar un botón de producto, si el producto ya existe en el ticket actual, simplemente se incrementa su cantidad y se recalcula el subtotal de esa fila; si no existe, se inserta una nueva fila.
4. **Cálculo de Totales:** En cada modificación del ticket se recalculan las etiquetas de Subtotal, IVA (10%) y Total final.
5. **Acción de Cobro:** Al pulsar `"Cobrar"`, se pide confirmación con el importe exacto. Tras aceptar, se genera un fichero de texto `ticket_TIMESTAMP.txt` con el desglose y se vacía el carrito para el siguiente cliente.

---

## ⚡ Parte 2: Reto Algorítmico Concurrente — Visualizador de Algoritmo de Ordenación

### Enunciado del Reto
Diseña una ventana que permita visualizar en tiempo real cómo el algoritmo de la burbuja (*Bubble Sort*) ordena un vector de números enteros:
* Un panel superior con un botón `"Generar Datos Aleatorios"` y un botón `"Iniciar Ordenación"`.
* Un panel central que extienda de `JPanel` y sobreescriba `paintComponent(Graphics g)`. En él, el vector de enteros (ej. 50 elementos con valores entre 10 y 300) se dibuja como un diagrama de barras verticales.
* Durante la ordenación:
  * Las barras que se están comparando en ese instante deben pintarse de color rojo.
  * Las barras ya ordenadas deben pintarse de color verde calma.
  * Las barras pendientes deben pintarse de color azul o gris.
* **Aspecto Crítico de Concurrencia:** La ordenación y las pausas de animación (`Thread.sleep(30)`) **no deben ejecutarse en el EDT**, o la ventana se congelaría. Debe ejecutarse en un hilo secundario (`new Thread(...)`), invocando `panel.repaint()` en cada paso para refrescar la visualización.

### Código de Referencia Completo del Visualizador

```java
package com.dam.boletin09.extras;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class VisualizadorOrdenacion extends JFrame {

    private final int NUM_BARRAS = 40;
    private final int[] datos = new int[NUM_BARRAS];
    private int indiceComparandoA = -1;
    private int indiceComparandoB = -1;
    private volatile boolean ordenando = false;

    private final PanelBarras panelBarras;
    private final JButton btnGenerar;
    private final JButton btnOrdenar;

    public VisualizadorOrdenacion() {
        super("Visualizador de Bubble Sort Concurrente");
        this.setSize(700, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        panelBarras = new PanelBarras();
        this.add(panelBarras, BorderLayout.CENTER);

        JPanel panelControles = new JPanel(new FlowLayout());
        btnGenerar = new JButton("Generar Vector Aleatorio");
        btnOrdenar = new JButton("Iniciar Bubble Sort");

        btnGenerar.addActionListener(e -> generarDatosAleatorios());
        btnOrdenar.addActionListener(e -> iniciarOrdenacionEnSegundoPlano());

        panelControles.add(btnGenerar);
        panelControles.add(btnOrdenar);
        this.add(panelControles, BorderLayout.SOUTH);

        generarDatosAleatorios();
    }

    private void generarDatosAleatorios() {
        if (ordenando) return;
        Random rnd = new Random();
        for (int i = 0; i < NUM_BARRAS; i++) {
            datos[i] = rnd.nextInt(280) + 20; // Alturas entre 20 y 300 px
        }
        indiceComparandoA = -1;
        indiceComparandoB = -1;
        panelBarras.repaint();
    }

    private void iniciarOrdenacionEnSegundoPlano() {
        if (ordenando) return;
        ordenando = true;
        btnGenerar.setEnabled(false);
        btnOrdenar.setEnabled(false);

        // LANZAR EN HILO SECUNDARIO PARA NO BLOQUEAR EL EDT
        new Thread(() -> {
            try {
                int n = datos.length;
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        indiceComparandoA = j;
                        indiceComparandoB = j + 1;

                        if (datos[j] > datos[j + 1]) {
                            int aux = datos[j];
                            datos[j] = datos[j + 1];
                            datos[j + 1] = aux;
                        }

                        // Refrescar la pantalla
                        panelBarras.repaint();
                        // Pausa para que el ojo humano perciba el movimiento
                        Thread.sleep(25);
                    }
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } finally {
                indiceComparandoA = -1;
                indiceComparandoB = -1;
                ordenando = false;
                panelBarras.repaint();
                SwingUtilities.invokeLater(() -> {
                    btnGenerar.setEnabled(true);
                    btnOrdenar.setEnabled(true);
                });
            }
        }).start();
    }

    private class PanelBarras extends JPanel {
        public PanelBarras() {
            this.setBackground(new Color(245, 245, 245));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int anchoPanel = getWidth();
            int altoPanel = getHeight();
            int anchoBarra = Math.max(2, anchoPanel / NUM_BARRAS - 2);

            for (int i = 0; i < NUM_BARRAS; i++) {
                int x = i * (anchoPanel / NUM_BARRAS) + 2;
                int altura = datos[i];
                int y = altoPanel - altura - 10;

                if (i == indiceComparandoA || i == indiceComparandoB) {
                    g2.setColor(new Color(220, 53, 69)); // Rojo: comparando
                } else {
                    g2.setColor(new Color(45, 138, 78)); // Verde calma
                }

                g2.fillRect(x, y, anchoBarra, altura);
                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(x, y, anchoBarra, altura);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualizadorOrdenacion().setVisible(true));
    }
}
```
