/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.jmmunoz.netfix.vista;

import com.jmmunoz.netfix.modelo.Usuario;
import com.jmmunoz.netfix.controlador.Utilities;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Juanma Muñoz
 */
/**
 * Panel de Gestión de Usuario (Perfil).
 * <p>
 * Permite al usuario autenticado visualizar y modificar su propia información
 * personal,
 * como nombre, correo electrónico y contraseña.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class UsuariosPanel extends javax.swing.JPanel {

        /**
         * Creates new form incidenciasPanel
         */
        Utilities ut = new Utilities();
        Usuario usuario;

        public UsuariosPanel(Usuario usuario) {
                initComponents();
                this.usuario = usuario;
                applyTelecomStyle();
                cargarDatos();
        }

        /**
         * Aplica el tema visual corporativo.
         * Configura estilos, fuentes y rediseña el layout principal para centrar el
         * formulario.
         */
        private void applyTelecomStyle() {
                // Fondo / Tema
                setBackground(com.jmmunoz.netfix.vista.tema.TelecomTheme.APP_BG);
                setOpaque(true);

                usersPanel.setBackground(com.jmmunoz.netfix.vista.tema.TelecomTheme.APP_BG);
                usersPanel.setOpaque(true);

                // Estilos
                ususTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
                ususTitle.setForeground(com.jmmunoz.netfix.vista.tema.TelecomTheme.ACCENT_DARK);

                // Etiquetas
                java.awt.Color muted = com.jmmunoz.netfix.vista.tema.TelecomTheme.TEXT_MUTED;
                java.awt.Font lblFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);

                idLabel.setFont(lblFont);
                idLabel.setForeground(muted);
                nameLabel1.setFont(lblFont);
                nameLabel1.setForeground(muted);
                emaiLabel.setFont(lblFont);
                emaiLabel.setForeground(muted);
                rolLabel.setFont(lblFont);
                rolLabel.setForeground(muted);
                contratoLabel1.setFont(lblFont);
                contratoLabel1.setForeground(muted);

                // Valores/Entradas
                java.awt.Font valFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
                idText.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
                idText.setForeground(com.jmmunoz.netfix.vista.tema.TelecomTheme.TEXT);

                nameText.setFont(valFont);
                nameText.addActionListener(e -> modButton.doClick());
                mailText.setFont(valFont);
                mailText.addActionListener(e -> modButton.doClick());
                rolCombo.setFont(valFont);
                passText.setFont(valFont);
                passText.addActionListener(e -> modButton.doClick());

                modButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

                com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance().cardify(mainPanel1);

                // ---------------------------------------------------------
                // REFACTORIZACIÓN DE DISEÑO - SOLO RAÍZ
                // (Preservamos el GroupLayout interno de mainPanel1 para coincidir
                // con el comportamiento de AparatosPanel)
                // ---------------------------------------------------------
                usersPanel.removeAll();
                usersPanel.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
                gbc.insets = new java.awt.Insets(20, 20, 10, 20);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1.0;
                gbc.anchor = java.awt.GridBagConstraints.LINE_START;
                gbc.fill = java.awt.GridBagConstraints.NONE;

                // 1) Título
                usersPanel.add(ususTitle, gbc);

                // 2) Panel del Formulario
                gbc.gridy++;
                gbc.weighty = 0.0;
                // Coincidir con AparatosPanel: Rellenar HORIZONTAL para usar el ancho
                // disponible,
                // confiando en GroupLayout para organizar los internos.
                gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

                usersPanel.add(mainPanel1, gbc);

                // Empujar arriba
                gbc.gridy++;
                gbc.weighty = 1.0;
                gbc.fill = java.awt.GridBagConstraints.BOTH;
                usersPanel.add(javax.swing.Box.createVerticalGlue(), gbc);

                revalidate();
                repaint();
        }

        /**
         * Carga los datos del usuario actual en los campos del formulario.
         * Rellena también el combobox de roles disponibles.
         */
        public void cargarDatos() {
                idText.setText(usuario.getIdUsuario());
                nameText.setText(usuario.getNombre());
                mailText.setText(usuario.getEmail());

                ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.ROLES, 0);
                rolCombo.removeAllItems();
                try {
                        while (rs.next()) {
                                rolCombo.addItem(rs.getString("descripcion"));
                        }
                } catch (SQLException ex) {
                        System.getLogger(SupervisorPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null,
                                        ex);
                }
                rolCombo.setSelectedItem(usuario.getRol());
                passText.setText(usuario.getPassword());
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */

        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                usersPanel = new javax.swing.JPanel();
                ususTitle = new javax.swing.JLabel();
                mainPanel1 = new javax.swing.JPanel();
                nameLabel1 = new javax.swing.JLabel();
                emaiLabel = new javax.swing.JLabel();
                rolLabel = new javax.swing.JLabel();
                contratoLabel1 = new javax.swing.JLabel();
                modButton = new javax.swing.JButton();
                nameText = new javax.swing.JTextField();
                mailText = new javax.swing.JTextField();
                rolCombo = new javax.swing.JComboBox<>();
                passText = new javax.swing.JPasswordField();
                idLabel = new javax.swing.JLabel();
                idText = new javax.swing.JLabel();

                setBackground(new java.awt.Color(255, 255, 255));

                usersPanel.setBackground(new java.awt.Color(255, 255, 255));

                ususTitle.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
                ususTitle.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.manage.title"));

                mainPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                nameLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                nameLabel1.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.name"));

                emaiLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                emaiLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.email"));

                rolLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                rolLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.role"));

                contratoLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                contratoLabel1.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.password"));

                modButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                modButton.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.btn.modify"));
                modButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                modButtonActionPerformed(evt);
                        }
                });

                nameText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                mailText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                rolCombo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                rolCombo.setEnabled(false);

                passText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                passText.setToolTipText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.tooltip.password"));

                idLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                idLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.id"));

                idText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
                mainPanel1.setLayout(mainPanel1Layout);
                mainPanel1Layout.setHorizontalGroup(
                                mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(mainPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(contratoLabel1)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(passText,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                743,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGap(689, 689, 689))
                                                                                .addGroup(mainPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(idLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(idText,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                168,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addContainerGap(
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(mainPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(nameLabel1)
                                                                                                                .addComponent(emaiLabel))
                                                                                                .addGap(31, 31, 31)
                                                                                                .addGroup(mainPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(mailText,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                443,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(nameText))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(mainPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(62, 62, 62)
                                                                                                                                .addComponent(rolCombo,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                286,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(mainPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(rolLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                300,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addGap(54, 54, 54))))
                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                .addGap(702, 702, 702)
                                                                .addComponent(modButton)
                                                                .addGap(0, 0, Short.MAX_VALUE)));
                mainPanel1Layout.setVerticalGroup(
                                mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(mainPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(idLabel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(idText,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(
                                                                                mainPanel1Layout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(rolCombo,
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                32,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(rolLabel,
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                .addGroup(mainPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(nameLabel1)
                                                                                                                .addComponent(nameText,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                37,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(mainPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(emaiLabel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(mailText,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                32,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(mainPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(contratoLabel1,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(passText,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                32,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(110, 110, 110)
                                                                .addComponent(modButton)
                                                                .addContainerGap(440, Short.MAX_VALUE)));

                javax.swing.GroupLayout usersPanelLayout = new javax.swing.GroupLayout(usersPanel);
                usersPanel.setLayout(usersPanelLayout);
                usersPanelLayout.setHorizontalGroup(
                                usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usersPanelLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(mainPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(37, 37, 37))
                                                .addGroup(usersPanelLayout.createSequentialGroup()
                                                                .addGap(602, 602, 602)
                                                                .addComponent(ususTitle)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                usersPanelLayout.setVerticalGroup(
                                usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usersPanelLayout
                                                                .createSequentialGroup()
                                                                .addComponent(ususTitle)
                                                                .addGap(20, 20, 20)
                                                                .addComponent(mainPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)));

                // --- DISEÑO ---
                this.setLayout(new java.awt.BorderLayout());
                this.add(usersPanel, java.awt.BorderLayout.CENTER);
        }// </editor-fold>//GEN-END:initComponents

        private void modButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_modButtonActionPerformed
                // Obtener valores
                String nombre = nameText.getText().trim();
                String email = mailText.getText().trim();
                String inputPass = new String(passText.getPassword()).trim();
                String id = idText.getText().trim();
                Object rolObj = rolCombo.getSelectedItem();

                String originalHash = usuario.getPassword();
                String passwordToSend = ut.procesarPasswordUpdate(inputPass, originalHash);

                // Validaciones
                if (nombre.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.name"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (rolObj == null) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.role"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (email.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.email"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (inputPass.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.password"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (id.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.invalid.id"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Ejecutar UPDATE
                int result = ut.ejecutarUpdate(
                                Utilities.TipoConsulta.UPDATEUSER,
                                nombre,
                                rolObj.toString(),
                                email,
                                passwordToSend,
                                id);

                if (result > 0) {
                        // Actualizar objeto local para reflejar cambios inmediatamente
                        usuario.setNombre(nombre);
                        usuario.setEmail(email);
                        usuario.setRol(rolObj.toString());
                        usuario.setPassword(passwordToSend);

                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.success.update"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.success"),
                                        JOptionPane.INFORMATION_MESSAGE);
                        cargarDatos();
                } else {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.update"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                }

        }// GEN-LAST:event_modButtonActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel contratoLabel1;
        private javax.swing.JLabel emaiLabel;
        private javax.swing.JLabel idLabel;
        private javax.swing.JLabel idText;
        private javax.swing.JTextField mailText;
        private javax.swing.JPanel mainPanel1;
        private javax.swing.JButton modButton;
        private javax.swing.JLabel nameLabel1;
        private javax.swing.JTextField nameText;
        private javax.swing.JPasswordField passText;
        private javax.swing.JComboBox<String> rolCombo;
        private javax.swing.JLabel rolLabel;
        private javax.swing.JPanel usersPanel;
        private javax.swing.JLabel ususTitle;
        // End of variables declaration//GEN-END:variables
}
