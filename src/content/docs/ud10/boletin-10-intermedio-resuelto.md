---
draft: true
prev: false
next: false
title: "📝 Unidad 10 — Boletín Intermedio (Resuelto)"
description: "Soluciones completas y detalladas de los ejercicios intermedios de JDBC: patrón DAO, consultas JOIN, transacciones atómicas, metadatos y Swing."
---

:::note[Compatibilidad y Arquitectura]
Las soluciones aplican principios de diseño limpio: separación en capas (Modelo, DAO, Conexión), control de transacciones seguras con rollback, y compatibilidad probada con SQLite y MySQL.
:::

---

### Solución Ejercicio 1: Arquitectura DAO Completa para Empleados

#### Clase Modelo `Empleado`
```java
package com.dam.boletin10.intermedio.model;

import java.math.BigDecimal;

public class Empleado {
    private int id;
    private String nif;
    private String nombre;
    private String puesto;
    private BigDecimal salario;
    private int departamentoId;

    public Empleado() {}

    public Empleado(int id, String nif, String nombre, String puesto, BigDecimal salario, int departamentoId) {
        this.id = id;
        this.nif = nif;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.departamentoId = departamentoId;
    }

    public Empleado(String nif, String nombre, String puesto, BigDecimal salario, int departamentoId) {
        this(0, nif, nombre, puesto, salario, departamentoId);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
    public int getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(int departamentoId) { this.departamentoId = departamentoId; }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + nif + ") - " + puesto + " - " + salario + " €";
    }
}
```

#### Interfaz `EmpleadoDAO`
```java
package com.dam.boletin10.intermedio.dao;

import com.dam.boletin10.intermedio.model.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoDAO {
    boolean insertar(Empleado e);
    boolean actualizar(Empleado e);
    boolean eliminar(int id);
    Optional<Empleado> buscarPorId(int id);
    List<Empleado> listarTodos();
}
```

#### Implementación `EmpleadoDAOImpl`
```java
package com.dam.boletin10.intermedio.dao;

import com.dam.boletin10.inicial.ConexionBD;
import com.dam.boletin10.intermedio.model.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public boolean insertar(Empleado e) {
        String sql = "INSERT INTO empleados (nif, nombre, puesto, salario, departamento_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNif());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getPuesto());
            ps.setBigDecimal(4, e.getSalario());
            ps.setInt(5, e.getDepartamentoId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        e.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error al insertar empleado: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Empleado e) {
        String sql = "UPDATE empleados SET nif = ?, nombre = ?, puesto = ?, salario = ?, departamento_id = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNif());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getPuesto());
            ps.setBigDecimal(4, e.getSalario());
            ps.setInt(5, e.getDepartamentoId());
            ps.setInt(6, e.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar empleado: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar empleado: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Empleado> buscarPorId(int id) {
        String sql = "SELECT id, nif, nombre, puesto, salario, departamento_id FROM empleados WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearEmpleado(rs));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar por ID: " + ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT id, nif, nombre, puesto, salario, departamento_id FROM empleados ORDER BY id ASC";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar empleados: " + ex.getMessage());
        }
        return lista;
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
            rs.getInt("id"),
            rs.getString("nif"),
            rs.getString("nombre"),
            rs.getString("puesto"),
            rs.getBigDecimal("salario"),
            rs.getInt("departamento_id")
        );
    }
}
```

---

### Solución Ejercicio 2: Consulta Relacional con `INNER JOIN` y Objeto DTO

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.inicial.ConexionBD;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio02ConsultaRelacional {

    public record EmpleadoDetalleDTO(int id, String nombre, String puesto, BigDecimal salario, String departamento) {}

    public static List<EmpleadoDetalleDTO> listarConDepartamento() {
        List<EmpleadoDetalleDTO> resultado = new ArrayList<>();
        String sql = """
            SELECT e.id, e.nombre, e.puesto, e.salario, d.nombre AS nombre_depto
            FROM empleados e
            INNER JOIN departamentos d ON e.departamento_id = d.id
            ORDER BY d.nombre ASC, e.nombre ASC;
            """;

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.add(new EmpleadoDetalleDTO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("puesto"),
                    rs.getBigDecimal("salario"),
                    rs.getString("nombre_depto")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en consulta JOIN: " + e.getMessage());
        }

        return resultado;
    }

    public static void main(String[] args) {
        List<EmpleadoDetalleDTO> lista = listarConDepartamento();
        System.out.println("--- EMPLEADOS CON DEPARTAMENTO ASIGNADO ---");
        for (EmpleadoDetalleDTO dto : lista) {
            System.out.println(String.format("• %-20s | %-18s | %-20s | %8.2f €",
                dto.nombre(), dto.puesto(), dto.departamento(), dto.salario()));
        }
    }
}
```

---

### Solución Ejercicio 3: Detección y Tratamiento de Valores `NULL`

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.inicial.ConexionBD;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ejercicio03TratamientoNull {

    public static void mostrarComisiones() {
        String sql = "SELECT id, nombre, salario, comision FROM empleados";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                BigDecimal comision = rs.getBigDecimal("comision");

                // Comprobación con wasNull()
                if (rs.wasNull() || comision == null) {
                    System.out.println("Empleado: " + nombre + " -> Comisión: [Sin comisión asignada]");
                } else {
                    System.out.println("Empleado: " + nombre + " -> Comisión: " + comision + " €");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar comisiones: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        mostrarComisiones();
    }
}
```

---

### Solución Ejercicio 4: Inspector Genérico con `ResultSetMetaData`

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.inicial.ConexionBD;
import java.sql.*;

public class Ejercicio04InspectorTablas {

    public static void inspeccionarTabla(String nombreTabla) {
        String sql = "SELECT * FROM " + nombreTabla + " WHERE 1=0";

        System.out.println("==========================================================");
        System.out.println("ESTRUCTURA DE LA TABLA: " + nombreTabla.toUpperCase());
        System.out.println("==========================================================");

        try (Connection con = ConexionBD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int numColumnas = meta.getColumnCount();
            System.out.println("Número total de columnas: " + numColumnas);
            System.out.println(String.format("%-4s | %-20s | %-15s | %-10s | %-10s",
                    "Nº", "NOMBRE", "TIPO SQL", "TAMAÑO", "ADMITE NULL"));
            System.out.println("----------------------------------------------------------");

            for (int i = 1; i <= numColumnas; i++) {
                String nombre = meta.getColumnLabel(i);
                String tipo = meta.getColumnTypeName(i);
                int tamanio = meta.getColumnDisplaySize(i);
                String admiteNull = (meta.isNullable(i) == ResultSetMetaData.columnNullable) ? "SÍ" : "NO";

                System.out.println(String.format("%-4d | %-20s | %-15s | %-10d | %-10s",
                        i, nombre, tipo, tamanio, admiteNull));
            }

        } catch (SQLException e) {
            System.err.println("Error al inspeccionar la tabla: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        inspeccionarTabla("departamentos");
    }
}
```

---

### Solución Ejercicio 5: Transacción Bancaria Atómica (ACID)

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.inicial.ConexionBD;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio05TransaccionBancaria {

    public static boolean transferir(int cuentaOrigen, int cuentaDestino, BigDecimal importe) {
        String sqlRetirar = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ? AND saldo >= ?";
        String sqlIngresar = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?";

        Connection con = null;
        try {
            con = ConexionBD.conectar();
            // 1. INICIAR TRANSACCIÓN ATÓMICA
            con.setAutoCommit(false);

            // Retirada
            try (PreparedStatement psRetiro = con.prepareStatement(sqlRetirar)) {
                psRetiro.setBigDecimal(1, importe);
                psRetiro.setInt(2, cuentaOrigen);
                psRetiro.setBigDecimal(3, importe);

                int filas = psRetiro.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("Fondos insuficientes o cuenta de origen no válida: " + cuentaOrigen);
                }
            }

            // Ingreso
            try (PreparedStatement psIngreso = con.prepareStatement(sqlIngresar)) {
                psIngreso.setBigDecimal(1, importe);
                psIngreso.setInt(2, cuentaDestino);

                int filas = psIngreso.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("La cuenta de destino no existe: " + cuentaDestino);
                }
            }

            // 2. CONFIRMAR TODO SI NO HUBO ERRORES
            con.commit();
            System.out.println("Transferencia de " + importe + " € completada exitosamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error durante la transacción. Ejecutando ROLLBACK: " + e.getMessage());
            if (con != null) {
                try {
                    // 3. REVERTIR CUALQUIER CAMBIO PARCIAL
                    con.rollback();
                    System.out.println("Rollback ejecutado con éxito. Estado original restaurado.");
                } catch (SQLException rollbackEx) {
                    System.err.println("Error grave al revertir: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    // Cierre
                }
            }
        }
    }

    public static void main(String[] args) {
        transferir(1, 2, new BigDecimal("250.00"));
    }
}
```

---

### Solución Ejercicio 6: Invocación de Procedimientos Almacenados (`CallableStatement`)

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.inicial.ConexionBD;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Ejercicio06ProcedimientoAlmacenado {

    public static void consultarResumenDepartamento(int departamentoId) {
        String llamada = "{call sp_resumen_departamento(?, ?, ?)}";

        try (Connection con = ConexionBD.conectar();
             CallableStatement cs = con.prepareCall(llamada)) {

            // Parámetro 1: IN
            cs.setInt(1, departamentoId);

            // Parámetros 2 y 3: OUT
            cs.registerOutParameter(2, Types.INTEGER);
            cs.registerOutParameter(3, Types.DECIMAL);

            cs.execute();

            int totalEmpleados = cs.getInt(2);
            BigDecimal masaSalarial = cs.getBigDecimal(3);

            System.out.println("=== RESUMEN DEPARTAMENTO " + departamentoId + " ===");
            System.out.println("• Total de Empleados: " + totalEmpleados);
            System.out.println("• Masa Salarial Total: " + masaSalarial + " €");

        } catch (SQLException e) {
            System.err.println("Nota: Procedimiento no disponible en bases embebidas sin soporte: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        consultarResumenDepartamento(1);
    }
}
```

---

### Solución Ejercicio 7: Inserción por Lotes (*Batch Processing*)

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.inicial.ConexionBD;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio07ProcesamientoPorLotes {

    private static final int TOTAL_REGISTROS = 1000;

    public static void main(String[] args) {
        String sql = "INSERT INTO metricas (clave, valor) VALUES (?, ?)";

        // Test con Batch Processing
        long inicio = System.currentTimeMillis();
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false); // Gran mejora en inserciones masivas

            for (int i = 1; i <= TOTAL_REGISTROS; i++) {
                ps.setString(1, "KEY_" + i);
                ps.setBigDecimal(2, new BigDecimal(i * 1.5));
                ps.addBatch();

                if (i % 200 == 0) {
                    ps.executeBatch(); // Envía 200 registros juntos a la red
                }
            }
            ps.executeBatch(); // Resto final
            con.commit();

            long fin = System.currentTimeMillis();
            System.out.println("Tiempo con Batch Processing para " + TOTAL_REGISTROS + " registros: " + (fin - inicio) + " ms");

        } catch (SQLException e) {
            System.err.println("Error en inserción masiva: " + e.getMessage());
        }
    }
}
```

---

### Solución Ejercicio 8: Mantenimiento Visual Swing Conectado a Base de Datos

```java
package com.dam.boletin10.intermedio;

import com.dam.boletin10.intermedio.dao.EmpleadoDAO;
import com.dam.boletin10.intermedio.dao.EmpleadoDAOImpl;
import com.dam.boletin10.intermedio.model.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class Ejercicio08MantenimientoSwing extends JFrame {

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JTextField txtNif, txtNombre, txtPuesto, txtSalario;

    public Ejercicio08MantenimientoSwing() {
        super("Mantenimiento de Empleados - JDBC & Swing");
        this.setSize(750, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 10, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Alta de Empleado"));

        txtNif = new JTextField();
        txtNombre = new JTextField();
        txtPuesto = new JTextField();
        txtSalario = new JTextField();

        panelForm.add(new JLabel("NIF:"));
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(new JLabel("Puesto:"));
        panelForm.add(new JLabel("Salario (€):"));

        panelForm.add(txtNif);
        panelForm.add(txtNombre);
        panelForm.add(txtPuesto);
        panelForm.add(txtSalario);

        this.add(panelForm, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "NIF", "Nombre", "Puesto", "Salario", "Depto ID"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCargar = new JButton("Recargar Tabla");
        JButton btnGuardar = new JButton("Guardar Empleado");
        JButton btnEliminar = new JButton("Eliminar Seleccionado");

        btnCargar.addActionListener(e -> cargarDatos());
        btnGuardar.addActionListener(e -> registrarEmpleado());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        panelBotones.add(btnCargar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        this.add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Empleado> empleados = empleadoDAO.listarTodos();
        for (Empleado emp : empleados) {
            modeloTabla.addRow(new Object[]{
                emp.getId(), emp.getNif(), emp.getNombre(), emp.getPuesto(), emp.getSalario() + " €", emp.getDepartamentoId()
            });
        }
    }

    private void registrarEmpleado() {
        String nif = txtNif.getText().trim();
        String nom = txtNombre.getText().trim();
        String puesto = txtPuesto.getText().trim();
        String salStr = txtSalario.getText().trim();

        if (nif.isEmpty() || nom.isEmpty() || puesto.isEmpty() || salStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal sal = new BigDecimal(salStr.replace(',', '.'));
            Empleado nuevo = new Empleado(nif, nom, puesto, sal, 1);
            if (empleadoDAO.insertar(nuevo)) {
                JOptionPane.showMessageDialog(this, "Empleado guardado con ID: " + nuevo.getId(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                txtNif.setText("");
                txtNombre.setText("");
                txtPuesto.setText("");
                txtSalario.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        int resp = JOptionPane.showConfirmDialog(this, "¿Eliminar empleado con ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            if (empleadoDAO.eliminar(id)) {
                cargarDatos();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ejercicio08MantenimientoSwing().setVisible(true));
    }
}
```
