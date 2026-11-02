---
title: Boletín U09 — Intermedio
description: Ejercicios propuestos de nivel intermedio sobre interfaces gráficas, MVC y dibujo con Swing
---

# 📝 Boletín U09 — Intermedio

1. Diseña una calculadora básica con dos campos de texto (operandos), cuatro botones (+, −, ×, ÷) y una etiqueta de resultado. Controla con `try-catch` los errores de formato (texto que no es un número) y la división entre cero, mostrando un diálogo de error.

2. Reorganiza el ejercicio anterior siguiendo el patrón **MVC**: una clase `CalculadoraModelo` solo con la lógica, una clase `CalculadoraVista` solo con los componentes, y una clase `CalculadoraControlador` que una ambas con los listeners.

3. Crea un formulario de registro con campos para nombre y email, una casilla "Acepto las condiciones" y un botón "Registrar" que solo funcione si la casilla está marcada y los campos no están vacíos (si no, muestra un diálogo con el problema). Valida el email con una expresión regular de la unidad 3.

4. Crea un componente propio (heredando de `JPanel`) que dibuje con `paintComponent` una casita sencilla (un cuadrado, un triángulo como tejado y una puerta) usando `fillRect`, `fillPolygon` y `setColor`.

5. Haz que el componente del ejercicio anterior dibuje un círculo en la posición donde el usuario haga clic con el ratón (usa un `MouseAdapter` y `repaint()`).

6. Crea un contador con un botón "Iniciar/Parar" y una etiqueta que, usando `javax.swing.Timer`, muestre cada segundo cuántos segundos han pasado desde que se pulsó "Iniciar".

7. Crea un editor de texto mínimo: un `JTextArea` dentro de un `JScrollPane`, y una barra de menú con "Archivo → Abrir" y "Archivo → Guardar" que usen `JFileChooser` y lo aprendido de ficheros en la unidad 8.

8. Explica con un ejemplo concreto por qué ejecutar una tarea de 10 segundos directamente dentro de un `ActionListener` congela la ventana, y qué síntoma verá el usuario.
