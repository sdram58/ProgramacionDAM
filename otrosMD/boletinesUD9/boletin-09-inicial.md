---
title: Boletín U09 — Inicial
description: Ejercicios propuestos de nivel inicial sobre interfaces gráficas y eventos con Swing
---

# 📝 Boletín U09 — Inicial

1. Escribe un programa que muestre, con `JOptionPane.showMessageDialog`, un mensaje de bienvenida con tu nombre.

2. Crea una ventana (`JFrame`) de 400×300 con el título "Mi primera ventana" que termine el programa al cerrarse. Arráncala dentro de `SwingUtilities.invokeLater`.

3. Añade a la ventana anterior una etiqueta (`JLabel`) con un texto y un botón (`JButton`) "Pulsa aquí". Al pulsar el botón, debe aparecer un diálogo con el mensaje "¡Botón pulsado!".

4. Crea una ventana con un campo de texto, un botón "Saludar" y una etiqueta. Al pulsar el botón, la etiqueta debe mostrar "Hola, " seguido del texto escrito en el campo.

5. Usa un `BorderLayout` para colocar un `JLabel` en el norte, un `JTextArea` en el centro y un `JButton` en el sur.

6. Crea una ventana con tres botones ("Rojo", "Verde", "Azul") dentro de un panel con `GridLayout(1, 3)`. Al pulsar cada uno, el color de fondo del panel central de la ventana debe cambiar al color correspondiente.

7. Explica con tus propias palabras qué es el *hilo de despacho de eventos* y por qué se arranca la interfaz con `invokeLater` en vez de directamente desde `main`.

8. Explica la diferencia entre la *fuente* de un evento y su *listener*, usando como ejemplo un botón y un `ActionListener`.

9. Crea un formulario de pedido de pizza: dos `JRadioButton` en un `ButtonGroup` (tamaño pequeña/grande), una `JCheckBox` "Extra de queso", un `JComboBox` con tres masas distintas y un botón "Pedir" que muestre con un `JOptionPane` un resumen de lo elegido.
