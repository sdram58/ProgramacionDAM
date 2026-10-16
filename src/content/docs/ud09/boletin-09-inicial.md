---
draft: true
prev: false
next: false
title: "📝 Unidad 9 — Boletín Inicial (Propuesto)"
description: "Ejercicios guiados de iniciación a Java Swing: ventanas JFrame, componentes básicos, layouts simples y manejo de eventos con ActionEvent."
---

:::tip[Objetivos Pedagógicos]
* Dominar la creación de ventanas de escritorio seguras con el hilo despachador de eventos (EDT).
* Comprender la jerarquía visual de contenedores (`JFrame`, `JPanel`) y componentes atómicos.
* Implementar controladores de eventos mediante expresiones Lambda limpias.
:::

---

### Ejercicio 1: Hola Mundo Gráfico
Crea una aplicación con una ventana `JFrame` de 400x200 píxeles, titulada `"Primer Programa Gráfico - DAM"`. 
* La ventana debe aparecer centrada en la pantalla.
* Debe cerrarse por completo al pulsar la cruz de cierre (`EXIT_ON_CLOSE`).
* Debe contener una etiqueta `JLabel` en el centro con el texto `"¡Bienvenido a Java Swing!"`, alineada al centro y con tipografía `"Segoe UI"` en negrita y tamaño 18.
* Añade un botón `JButton` en la parte inferior (`BorderLayout.SOUTH`) con el texto `"Cerrar Ventana"` que finalice la aplicación al pulsarse.

---

### Ejercicio 2: Contador de Clics
Desarrolla una pequeña interfaz con un contador interactivo:
* Contiene una etiqueta `JLabel` grande en el centro que muestra el texto `"Clics: 0"`.
* En la parte inferior, mediante un `FlowLayout`, sitúa tres botones:
  1. `"Incrementar (+1)"`: suma 1 al contador.
  2. `"Decrementar (-1)"`: resta 1 al contador (sin permitir valores negativos).
  3. `"Reiniciar"`: restablece el contador a 0.
* El valor numérico debe guardarse en una variable entera y actualizar el texto de la etiqueta en cada pulsación.

---

### Ejercicio 3: Conversor de Divisas Simple
Diseña un conversor de Euros a Dólares estadounidenses:
* Un panel superior con una etiqueta `"Importe en Euros (€):"` y un campo `JTextField` de 10 columnas.
* Un botón central `"Convertir a USD"`.
* Una etiqueta inferior que muestre `"Resultado: 0.00 USD"`.
* Al pulsar el botón, el programa debe leer el valor del campo de texto, multiplicarlo por la tasa de cambio fija (ej. 1.08) y mostrar el resultado formateado a dos decimales.
* Si el usuario escribe texto no numérico o deja el campo vacío, debe capturarse la excepción `NumberFormatException` y mostrar un mensaje de aviso en rojo o mediante un diálogo `JOptionPane.showWarningDialog`.

---

### Ejercicio 4: Cambiador de Color de Fondo
Crea una ventana con un panel central grande.
* En la barra superior, incluye cuatro botones: `"Rojo"`, `"Verde"`, `"Azul"` y `"Restablecer"`.
* Al hacer clic en cada botón, el fondo del panel central debe cambiar al color correspondiente (`Color.RED`, `new Color(45, 138, 78)`, `Color.BLUE`, o el gris original del sistema).
* Observa cómo el método `panel.setBackground(Color c)` altera la visualización de forma inmediata.

---

### Ejercicio 5: Reflejo en Tiempo Real (Espejo de Texto)
Construye una ventana que contenga un campo `JTextField` de entrada y una etiqueta `JLabel` debajo.
* Asigna un controlador de teclado (`KeyAdapter` sobreescribiendo `keyReleased`) o un `DocumentListener` al campo de texto.
* A medida que el usuario escribe cada letra en el campo, la etiqueta inferior debe reflejar en vivo: `"Has escrito: " + texto`.
* Si el campo queda vacío, la etiqueta debe mostrar `"Escribe algo arriba..."`.

---

### Ejercicio 6: Conmutador de Visibilidad con CheckBox
Crea una pequeña pantalla de autenticación simplificada:
* Incluye una etiqueta `"Introduce tu contraseña:"` y un campo `JPasswordField`.
* Debajo, añade un `JCheckBox` con el texto `"Mostrar contraseña"`.
* Por defecto, el `JPasswordField` oculta los caracteres con el carácter de eco `*` o `•`.
* Al marcar o desmarcar la casilla de verificación (`ItemListener` o `ActionListener`), la contraseña debe alternar entre ser legible (`setEchoChar((char) 0)`) o volver a quedar oculta (`setEchoChar('•')`).

---

### Ejercicio 7: Selector de Tipografía con Radio Buttons
Diseña un visualizador de texto que permita al usuario personalizar el estilo:
* Un campo de texto central con una frase de muestra fija: `"Java Swing en IntelliJ IDEA"`.
* En un panel lateral o inferior, añade un grupo de tres botones de radio (`JRadioButton` dentro de un `ButtonGroup`):
  1. `"Pequeño (12 pt)"`
  2. `"Mediano (16 pt)"` (marcado por defecto)
  3. `"Grande (22 pt)"`
* Al seleccionar cualquiera de las opciones, la fuente de la frase de muestra debe cambiar al tamaño seleccionado de forma inmediata.

---

### Ejercicio 8: Formulario de Contacto con Validación y Diálogos
Desarrolla un formulario con los siguientes campos organizados con un gestor de diseño adecuado (`GridLayout` o `BoxLayout` con márgenes):
* Nombre (campo de texto).
* Correo Electrónico (campo de texto).
* Dos botones en la parte inferior: `"Enviar"` y `"Limpiar"`.
* Al pulsar `"Limpiar"`, todos los campos de texto deben vaciarse y el cursor debe colocarse automáticamente en el campo Nombre (`txtNombre.requestFocus()`).
* Al pulsar `"Enviar"`, valida que ninguno de los dos campos esté vacío y que el correo contenga al menos un carácter `@`. Si todo es correcto, muestra un `JOptionPane.showMessageDialog` informativo indicando `"Formulario enviado con éxito para: [Nombre]"`. Si hay errores, muestra un diálogo de advertencia.
