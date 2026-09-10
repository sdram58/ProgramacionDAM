package adicionales;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Ejercicio_3_Adicionales extends javax.swing.JFrame {
    
    List<Persona> usuarios;
    Persona actualizando;

    public Ejercicio_3_Adicionales() {
        initComponents();
        usuarios = new ArrayList<>();
        usuarios.add(new Usuario("12345678A", "Josemi", "Blázquez", "Inf", "24/02/2024", "josemi", "123456", 300));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupTipoUsuario = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListado = new javax.swing.JTable();
        jLabelTituloAPP = new javax.swing.JLabel();
        jPaneFormulario = new javax.swing.JPanel();
        jLabelDNI = new javax.swing.JLabel();
        jTextFieldDNI = new javax.swing.JTextField();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jLabelApellidos = new javax.swing.JLabel();
        jTextFieldApellidos = new javax.swing.JTextField();
        jLabelDpto = new javax.swing.JLabel();
        jTextFieldDpto = new javax.swing.JTextField();
        jLabelFecha = new javax.swing.JLabel();
        jTextFieldFecha = new javax.swing.JTextField();
        jLabelUser = new javax.swing.JLabel();
        jTextFieldUser = new javax.swing.JTextField();
        jLabelPassword = new javax.swing.JLabel();
        jPasswordFieldPass = new javax.swing.JPasswordField();
        jPanelRol = new javax.swing.JPanel();
        jRadioButtonAdministrador = new javax.swing.JRadioButton();
        jRadioButtonUsuario = new javax.swing.JRadioButton();
        jLabelCuota = new javax.swing.JLabel();
        jLabelAccessLevel = new javax.swing.JLabel();
        jComboBoxAccessLevel = new javax.swing.JComboBox<>();
        jTextFieldCuota = new javax.swing.JTextField();
        jPasswordFieldPassRepetir = new javax.swing.JPasswordField();
        jLabelPassword2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonEliminar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonConsulta = new javax.swing.JButton();
        jButtonAlta = new javax.swing.JButton();
        jButtonGuardarCambios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombre de usuario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableListado);

        jLabelTituloAPP.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTituloAPP.setText("Gestión de usuarios");

        jPaneFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        jLabelDNI.setText("DNI:");

        jLabelNombre.setText("Nombre:");

        jLabelApellidos.setText("Apellidos:");

        jLabelDpto.setText("Departamento:");

        jLabelFecha.setText("Fecha incorporación:");

        jLabelUser.setText("Usuario:");

        jLabelPassword.setText("Contraseña:");

        jPanelRol.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de rol"));

        btnGroupTipoUsuario.add(jRadioButtonAdministrador);
        jRadioButtonAdministrador.setText("Administrador");
        jRadioButtonAdministrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAdministradorActionPerformed(evt);
            }
        });

        btnGroupTipoUsuario.add(jRadioButtonUsuario);
        jRadioButtonUsuario.setSelected(true);
        jRadioButtonUsuario.setText("Usuario");
        jRadioButtonUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRolLayout = new javax.swing.GroupLayout(jPanelRol);
        jPanelRol.setLayout(jPanelRolLayout);
        jPanelRolLayout.setHorizontalGroup(
            jPanelRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRolLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jRadioButtonUsuario)
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonAdministrador)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRolLayout.setVerticalGroup(
            jPanelRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRolLayout.createSequentialGroup()
                .addGroup(jPanelRolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonUsuario)
                    .addComponent(jRadioButtonAdministrador))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabelCuota.setText("Cuota de disco (MB):");

        jLabelAccessLevel.setText("Nivel de acceso:");

        jComboBoxAccessLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Parcial", "Total" }));
        jComboBoxAccessLevel.setEnabled(false);

        jLabelPassword2.setText("Repetir contraseña:");

        javax.swing.GroupLayout jPaneFormularioLayout = new javax.swing.GroupLayout(jPaneFormulario);
        jPaneFormulario.setLayout(jPaneFormularioLayout);
        jPaneFormularioLayout.setHorizontalGroup(
            jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPaneFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelUser)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelFecha)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelDpto)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldDpto, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelNombre)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelDNI)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelApellidos)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelPassword)
                                .addGap(18, 18, 18)
                                .addComponent(jPasswordFieldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                .addComponent(jLabelPassword2)
                                .addGap(18, 18, 18)
                                .addComponent(jPasswordFieldPassRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPaneFormularioLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPaneFormularioLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanelRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                    .addComponent(jLabelAccessLevel)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBoxAccessLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(52, 52, 52))
                                .addGroup(jPaneFormularioLayout.createSequentialGroup()
                                    .addComponent(jLabelCuota)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldCuota, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPaneFormularioLayout.setVerticalGroup(
            jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneFormularioLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDNI)
                    .addComponent(jTextFieldDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombre)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelApellidos)
                    .addComponent(jTextFieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDpto)
                    .addComponent(jTextFieldDpto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFecha)
                    .addComponent(jTextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUser)
                    .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldPassRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPassword2))
                .addGap(18, 18, 18)
                .addComponent(jPanelRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAccessLevel)
                    .addComponent(jComboBoxAccessLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPaneFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCuota)
                    .addComponent(jTextFieldCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonModificar.setText("Actualizar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonConsulta.setText("Consulta");
        jButtonConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultaActionPerformed(evt);
            }
        });

        jButtonAlta.setText("Alta");
        jButtonAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAltaActionPerformed(evt);
            }
        });

        jButtonGuardarCambios.setText("Guardar cambios");
        jButtonGuardarCambios.setEnabled(false);
        jButtonGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButtonAlta)
                        .addGap(12, 12, 12)
                        .addComponent(jButtonConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonGuardarCambios)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonGuardarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPaneFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(jLabelTituloAPP)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTituloAPP)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPaneFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAltaActionPerformed
        String dni = jTextFieldDNI.getText();
        String nombre = jTextFieldNombre.getText();
        String apellidos = jTextFieldApellidos.getText();
        String dpto = jTextFieldDpto.getText();
        String fecha = jTextFieldFecha.getText();
        String user = jTextFieldUser.getText();
        char[] password = jPasswordFieldPass.getPassword();
        char[] password2 = jPasswordFieldPassRepetir.getPassword();
        String rol = jRadioButtonAdministrador.isSelected() ? "Administrador" : "Usuario";
        
        boolean vacios = dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() ||
                               dpto.isEmpty() || fecha.isEmpty() || user.isEmpty() || password.length == 0;
        
        if (vacios) {
            JOptionPane.showMessageDialog(this, "Hay algún campo sin rellenar.", "ERROR", JOptionPane.WARNING_MESSAGE);
        } else if (!Arrays.equals(password, password2)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "ERROR", JOptionPane.WARNING_MESSAGE);
        } else {

            if (rol.equals("Administrador")) {
                String accessLevel = (String) jComboBoxAccessLevel.getSelectedItem();
                Administrador admin1 = new Administrador(dni, nombre, apellidos, dpto, fecha, user, String.valueOf(password), accessLevel);
                usuarios.add(admin1);
                JOptionPane.showMessageDialog(this, "Creación de usuarios", "Admin creado satisfactoriamente!", JOptionPane.INFORMATION_MESSAGE);

            } else {
                String cuota = jTextFieldCuota.getText();
                try{
                    Usuario user1 = new Usuario(dni, nombre, apellidos, dpto, fecha, user, String.valueOf(password), Integer.parseInt(cuota));
                    usuarios.add(user1);
                    JOptionPane.showMessageDialog(this, "Creación de usuarios", "Usuario creado satisfactoriamente!", JOptionPane.INFORMATION_MESSAGE);
                }catch(NumberFormatException nf){
                    JOptionPane.showMessageDialog(this, "La cuota debe ser un número entero!.", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        }      
    }//GEN-LAST:event_jButtonAltaActionPerformed

    private void jButtonConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultaActionPerformed
        String[] tipos = {"Usuario","Administrador"};
        String tipoUser = (String)JOptionPane.showInputDialog(this, "Escribe tipo:", "Consulta de usuarios", JOptionPane.QUESTION_MESSAGE, null,tipos,tipos[0]);
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("DNI");
        modelo.addColumn("Username");
        
        if(tipoUser.equals("Usuario")){
            for(int i=0; i < usuarios.size(); i++){
                Persona paux = usuarios.get(i);
                if(paux instanceof Usuario)
                    modelo.addRow(new String[]{paux.getDNI(), paux.getUsername()});
            }
            
        }else{
            for(int i=0; i < usuarios.size(); i++){
                Persona paux = usuarios.get(i);
                if(paux instanceof Administrador)
                    modelo.addRow(new String[]{paux.getDNI(), paux.getUsername()});
            }  
        }
        
        jTableListado.setModel(modelo);
    }//GEN-LAST:event_jButtonConsultaActionPerformed

    private void jRadioButtonAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAdministradorActionPerformed
        jComboBoxAccessLevel.setEnabled(true);
        jTextFieldCuota.setEnabled(false);
    }//GEN-LAST:event_jRadioButtonAdministradorActionPerformed

    private void jRadioButtonUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonUsuarioActionPerformed
        jComboBoxAccessLevel.setEnabled(false);
        jTextFieldCuota.setEnabled(true);
    }//GEN-LAST:event_jRadioButtonUsuarioActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        String nombreUsuario = JOptionPane.showInputDialog(this, "Buscar usuario", "Introduzca el nombre a buscar:", JOptionPane.QUESTION_MESSAGE);

        for (int i = 0; i < usuarios.size(); i++) {
            Persona paux = usuarios.get(i);
            actualizando = paux;
            if(paux.getUsername().equals(nombreUsuario)){
                // Cargar datos en formulario
                JOptionPane.showMessageDialog(this, "Usuario encontrado! Modifica el campo deseado y acepta cambios.", "Actualizar datos", JOptionPane.INFORMATION_MESSAGE);
                jTextFieldDNI.setText(paux.getDNI());
                jTextFieldNombre.setText(paux.getNombre());
                jTextFieldApellidos.setText(paux.getApellidos());
                jTextFieldDpto.setText(paux.getDepartamento());
                jTextFieldFecha.setText(paux.getFechaIncorporacion());
                jTextFieldUser.setText(paux.getUsername());
                jPasswordFieldPass.setText(paux.getPassword());
                
                if(paux instanceof Usuario){
                    jRadioButtonUsuario.setEnabled(true);
                    jRadioButtonAdministrador.setEnabled(false);
                    jTextFieldCuota.setText(((Usuario)paux).getCuotaDisco() + "");
                } else{
                    jRadioButtonUsuario.setEnabled(false);
                    jRadioButtonAdministrador.setEnabled(true);
                    jComboBoxAccessLevel.setSelectedItem(((Administrador)paux).getAccessLevel());
                }
                
                jButtonGuardarCambios.setEnabled(true);
                jPasswordFieldPass.setEnabled(false);
                jPasswordFieldPassRepetir.setEnabled(false);
                
            } else{
                JOptionPane.showMessageDialog(this, "Usuario no encontrado!", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        String nombreUsuario = JOptionPane.showInputDialog(this, "Escribe nombre de usuario a eliminar:", "Buscar y eliminar", JOptionPane.QUESTION_MESSAGE);
        
        for (int i = 0; i < usuarios.size(); i++) {
            Persona paux = usuarios.get(i);
            if(paux.getUsername().equals(nombreUsuario)){
                int opcion = JOptionPane.showConfirmDialog(this, "Usuario encontrado, ¿está seguro de que desea eliminarlo?", "Eliminación de usuarios", JOptionPane.YES_NO_CANCEL_OPTION);
                if(opcion == 0){
                    usuarios.remove(i);
                }           
            }              
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarCambiosActionPerformed
        jButtonGuardarCambios.setEnabled(false);
        
        actualizando.setDni(jTextFieldDNI.getText());
        actualizando.setNombre(jTextFieldNombre.getText());
        actualizando.setApellidos(jTextFieldApellidos.getText());
        actualizando.setDepartamento(jTextFieldDpto.getText());
        actualizando.setFechaIncorporacion(jTextFieldFecha.getText());
        actualizando.setNombreUsuario(jTextFieldUser.getText());
        
        // Omitimos las comprobaciones por facilitar la resolución. Quedan como propuestas de ampliación
        
        if (actualizando instanceof Administrador) {
            ((Administrador)actualizando).setAccessLevel((String)jComboBoxAccessLevel.getSelectedItem());
            JOptionPane.showMessageDialog(this, "Actualización de usuarios", "Administrador actualizado satisfactoriamente!", JOptionPane.INFORMATION_MESSAGE);

        } else {
            ((Usuario)actualizando).setCuotaDisco(Integer.parseInt(jTextFieldCuota.getText()));
            JOptionPane.showMessageDialog(this, "Actualización de usuarios", "Usuario actualizado satisfactoriamente!", JOptionPane.INFORMATION_MESSAGE);
        }
        // Limpiamos los campos
        jPasswordFieldPass.setEnabled(true);
        jPasswordFieldPassRepetir.setEnabled(true);
        jTextFieldDNI.setText("");
        jTextFieldNombre.setText("");
        jTextFieldApellidos.setText("");
        jTextFieldDpto.setText("");
        jTextFieldFecha.setText("");
        jTextFieldUser.setText("");
        jPasswordFieldPass.setText("");
        jTextFieldCuota.setText("");
        

  
    }//GEN-LAST:event_jButtonGuardarCambiosActionPerformed


    public static void main(String args[]) {
        
        try{
            Ejercicio_3_Adicionales.setDefaultLookAndFeelDecorated(true);
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");        
        }catch (Exception e){
            e.printStackTrace();
        }
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ejercicio_3_Adicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ejercicio_3_Adicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ejercicio_3_Adicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ejercicio_3_Adicionales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ejercicio_3_Adicionales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupTipoUsuario;
    private javax.swing.JButton jButtonAlta;
    private javax.swing.JButton jButtonConsulta;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonGuardarCambios;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JComboBox<String> jComboBoxAccessLevel;
    private javax.swing.JLabel jLabelAccessLevel;
    private javax.swing.JLabel jLabelApellidos;
    private javax.swing.JLabel jLabelCuota;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelDpto;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPassword2;
    private javax.swing.JLabel jLabelTituloAPP;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JPanel jPaneFormulario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelRol;
    private javax.swing.JPasswordField jPasswordFieldPass;
    private javax.swing.JPasswordField jPasswordFieldPassRepetir;
    private javax.swing.JRadioButton jRadioButtonAdministrador;
    private javax.swing.JRadioButton jRadioButtonUsuario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListado;
    private javax.swing.JTextField jTextFieldApellidos;
    private javax.swing.JTextField jTextFieldCuota;
    private javax.swing.JTextField jTextFieldDNI;
    private javax.swing.JTextField jTextFieldDpto;
    private javax.swing.JTextField jTextFieldFecha;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldUser;
    // End of variables declaration//GEN-END:variables
}
