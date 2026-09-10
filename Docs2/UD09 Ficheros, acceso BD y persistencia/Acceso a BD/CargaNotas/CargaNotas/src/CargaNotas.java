import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class CargaNotas extends javax.swing.JFrame {
    DefaultTableModel modelo = null; //El modelo debe ser variable de clase
    boolean precarga = false; //Así sabremos si ya hemos precargado la tabla del TXT o no
    int registros = 0; //Para contar cuántos registros se han precargado y comprobar la inserción

    public CargaNotas() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jButtonCargaFichero = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNotas = new javax.swing.JTable();
        jButtonAlmacenarBD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonCargaFichero.setText("Cargar datos de fichero");
        jButtonCargaFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargaFicheroActionPerformed(evt);
            }
        });

        jTableNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellidos", "Nota1", "Nota2", "Nota3", "Nota4", "Nota5", "Nota6", "Nota7", "Nota8", "Nota9", "Nota10"
            }
        ));
        jScrollPane1.setViewportView(jTableNotas);

        jButtonAlmacenarBD.setText("Almacenar en base de datos");
        jButtonAlmacenarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlmacenarBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jButtonCargaFichero)
                        .addGap(87, 87, 87)
                        .addComponent(jButtonAlmacenarBD, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCargaFichero, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAlmacenarBD, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCargaFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargaFicheroActionPerformed
        // # SELECCIÓN DEL FICHERO Y DECLARACIÓN DE VARIABLES (MODELO) -------
        int seleccion = jFileChooser1.showOpenDialog(this);
        File fichero = jFileChooser1.getSelectedFile();
        
        if(seleccion != JFileChooser.APPROVE_OPTION){ //Si el usuario elige un fichero diferente
            JOptionPane.showMessageDialog(this, "Se ha cancelado la selección del fichero.", "Error", JOptionPane.WARNING_MESSAGE);
        }else if(!fichero.getName().equals("alumnos_notas.txt")){ //Si el usuario NO acepta la selección
            JOptionPane.showMessageDialog(this, "¡El fichero seleccionado no es correcto!", "Error", JOptionPane.ERROR_MESSAGE);            
        }else{ //Si no pasa lo anterior, entonces podemos procesar
              
            String linea = null;
            modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            int x = 1;
            while(x<=10){
               modelo.addColumn("Nota"+x); 
               x++;
            }

            Object[] vector = new Object[12];
            // # CARGA DE DATOS DEL FICHERO -----------------------------
            try {
                FileReader fileReader = new FileReader(fichero);
                BufferedReader bR = new BufferedReader(fileReader);

                while((linea = bR.readLine()) != null) {
                    String[] row = linea.split(" ");

                    for(int i=0;i<row.length;i++){
                        vector[i] = row[i];
                    }

                    if(row.length<10){ //Rellenar con null si fila es menor a 12 elementos
                        for(int j=row.length;j<12;j++){
                            vector[j] = null;
                        }
                    }
                    
                    modelo.addRow(vector);
                    registros++;
                }

                jTableNotas.setModel(modelo);
                precarga = true;
                bR.close();
                JOptionPane.showMessageDialog(this, "Se han precargado: " + registros + " registros.", "Resultado", JOptionPane.WARNING_MESSAGE);
                
            }
            catch(IOException ex) {
                JOptionPane.showMessageDialog(this, "¡Error al leer el fichero seleccionado!!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonCargaFicheroActionPerformed

    private void jButtonAlmacenarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlmacenarBDActionPerformed
        Connection conBD = null;
        Statement stm = null;
        String servidor = "jdbc:mysql://localhost:3306/";
        String basedatos = "usuarios";
        String DBuser = "root";
        String DBpass = "123456";
        
        if(!precarga){ //Si el usuario no ha precargado en el model no permitimos la inserción
            JOptionPane.showMessageDialog(this, "No se han precargado los datos. Por favor, proceda primero...", "Atención", JOptionPane.WARNING_MESSAGE);
        }else{
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en la carga del driver MySQL.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            try {
                conBD = DriverManager.getConnection(servidor + basedatos, DBuser, DBpass);
            
                int filas = modelo.getRowCount();
                int columnas = modelo.getColumnCount();
                int registrosTotal = 0;
                int id = 1;

                String query = "INSERT INTO notas VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conBD.prepareStatement(query);

                for (int i = 0; i < filas; i++) {
                    statement.setString(1, String.valueOf(id++));                  
                    
                    for (int j = 2; j < columnas; j++) {
                        String valor = (String) modelo.getValueAt(i, j);
                        statement.setString(j + 1, valor);
                    }
                    int cantidad = statement.executeUpdate();
                    registrosTotal += cantidad;
                }
                
                JOptionPane.showMessageDialog(this, "Se han insertado: " + registrosTotal + " registros.", "Resultado", JOptionPane.WARNING_MESSAGE);
            
            } catch (SQLException error) {
                System.out.println("Error al conectar a BD: " + error.getMessage());
            }     
        }
    }//GEN-LAST:event_jButtonAlmacenarBDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CargaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargaNotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargaNotas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlmacenarBD;
    private javax.swing.JButton jButtonCargaFichero;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableNotas;
    // End of variables declaration//GEN-END:variables
}
