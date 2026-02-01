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

        /**
         * Crea un nuevo panel de gestión de usuario.
         * 
         * @param usuario El usuario autenticado cuyos datos se mostrarán.
         */
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
                // LAYOUT OVERRIDE (Arregla espaciado)
                // ---------------------------------------------------------
                // Override para eliminar "centering" o espacios
                this.setLayout(new java.awt.BorderLayout());
                this.add(usersPanel, java.awt.BorderLayout.CENTER);

                // ---------------------------------------------------------
                // CONFIGURACIÓN DE ETIQUETAS Y CAMPOS
                // ---------------------------------------------------------
                // Acortar etiquetas
                idLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.id.short"));
                nameLabel1.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.name"));
                emaiLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.email"));
                rolLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("user.label.role"));
                contratoLabel1.setText(com.jmmunoz.netfix.config.AppConfig.getInstance()
                                .getMessage("user.label.password.short"));

                // Evitar que los campos se colapsen cuando el llenado del padre es NONE
                nameText.setColumns(30);
                mailText.setColumns(30);
                passText.setColumns(30);
                rolCombo.setPreferredSize(new java.awt.Dimension(200, 32));

                // ---------------------------------------------------------
                // LAYOUT DEL PANEL PRINCIPAL (Formulario)
                // ---------------------------------------------------------
                mainPanel1.setLayout(new java.awt.GridBagLayout());
                mainPanel1.removeAll();

                java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
                gbc.insets = new java.awt.Insets(10, 10, 10, 10);
                gbc.anchor = java.awt.GridBagConstraints.WEST;
                gbc.fill = java.awt.GridBagConstraints.NONE; // POR DEFECTO NONE para campos

                // Fila 0: ID
                gbc.gridy = 0;
                gbc.gridx = 0;
                gbc.weightx = 0.0;
                mainPanel1.add(idLabel, gbc);

                gbc.gridx = 1;
                gbc.gridwidth = 3;
                gbc.fill = java.awt.GridBagConstraints.HORIZONTAL; // Permitir que el texto del ID llene sus columnas
                                                                   // pero no más
                gbc.fill = java.awt.GridBagConstraints.NONE;
                mainPanel1.add(idText, gbc);
                gbc.gridwidth = 1;

                // Fila 1: Nombre (Etiqueta + Campo) | Rol (Etiqueta + Campo)
                gbc.gridy = 1;

                // Etiqueta Nombre
                gbc.gridx = 0;
                mainPanel1.add(nameLabel1, gbc);

                // Campo Nombre
                gbc.gridx = 1;
                gbc.fill = java.awt.GridBagConstraints.NONE; // Tamaño fijo
                mainPanel1.add(nameText, gbc);

                // Etiqueta Rol
                gbc.gridx = 2;
                mainPanel1.add(rolLabel, gbc);

                // Combo Rol
                gbc.gridx = 3;
                gbc.fill = java.awt.GridBagConstraints.NONE;
                mainPanel1.add(rolCombo, gbc);

                // ESPACIADOR para Fila 1 (empuja todo a la izquierda)
                java.awt.GridBagConstraints gbcSpacer = new java.awt.GridBagConstraints();
                gbcSpacer.gridx = 4;
                gbcSpacer.gridy = 1;
                gbcSpacer.weightx = 1.0; // COMER TODO EL ESPACIO
                gbcSpacer.fill = java.awt.GridBagConstraints.HORIZONTAL;
                mainPanel1.add(javax.swing.Box.createHorizontalGlue(), gbcSpacer);

                // Fila 2: Email
                gbc.gridy = 2;
                gbc.gridx = 0;
                mainPanel1.add(emaiLabel, gbc);

                gbc.gridx = 1;
                gbc.gridwidth = 3;
                gbc.fill = java.awt.GridBagConstraints.NONE;
                mainPanel1.add(mailText, gbc);
                gbc.gridwidth = 1;

                // Fila 3: Contraseña
                gbc.gridy = 3;
                gbc.gridx = 0;
                mainPanel1.add(contratoLabel1, gbc);

                gbc.gridx = 1;
                gbc.gridwidth = 3;
                gbc.fill = java.awt.GridBagConstraints.NONE;
                mainPanel1.add(passText, gbc);
                gbc.gridwidth = 1;

                // Fila 4: Botón
                gbc.gridy = 4;
                gbc.gridx = 0;
                gbc.gridwidth = 4;
                gbc.fill = java.awt.GridBagConstraints.NONE;
                gbc.anchor = java.awt.GridBagConstraints.WEST; // Mantener botón a la izquierda
                mainPanel1.add(modButton, gbc);

                // ---------------------------------------------------------
                // REFACTORIZACIÓN DE DISEÑO - PANEL RAÍZ
                // ---------------------------------------------------------
                usersPanel.removeAll();
                usersPanel.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints rootGbc = new java.awt.GridBagConstraints();
                rootGbc.insets = new java.awt.Insets(20, 20, 20, 20);
                rootGbc.gridx = 0;
                rootGbc.gridy = 0;

                // Usar HORIZONTAL para estirar el contenedor, pero controlaremos los campos
                // internos
                rootGbc.weightx = 1.0;
                rootGbc.anchor = java.awt.GridBagConstraints.NORTHWEST; // Anclar arriba-izquierda
                rootGbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

                // 1) Título
                usersPanel.add(ususTitle, rootGbc);

                // 2) Panel del Formulario
                rootGbc.gridy++;
                rootGbc.weighty = 0.0;
                usersPanel.add(mainPanel1, rootGbc);

                // Empujar arriba
                rootGbc.gridy++;
                rootGbc.weighty = 1.0;
                rootGbc.fill = java.awt.GridBagConstraints.BOTH;
                usersPanel.add(javax.swing.Box.createVerticalGlue(), rootGbc);

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
                        ut.logAction("ERROR", "UsuariosPanel", "Error cargando roles: " + ex.getMessage());
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
                setPreferredSize(new java.awt.Dimension(1592, 946));

                usersPanel.setBackground(new java.awt.Color(255, 255, 255));
                usersPanel.setPreferredSize(new java.awt.Dimension(1592, 946));

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
                                                                                                                .addComponent(contratoLabel1)
                                                                                                                .addComponent(nameLabel1)
                                                                                                                .addComponent(emaiLabel))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(mainPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                false)
                                                                                                                .addComponent(mailText,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                437,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(passText,
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
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
                                                                                                                .addComponent(rolLabel))
                                                                                                .addGap(144, 144,
                                                                                                                144))))
                                                .addGroup(mainPanel1Layout.createSequentialGroup()
                                                                .addGap(702, 702, 702)
                                                                .addComponent(modButton)
                                                                .addGap(0, 736, Short.MAX_VALUE)));
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
                                                                .addGroup(mainPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(nameLabel1)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(emaiLabel))
                                                                                .addGroup(mainPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(nameText,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                32,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGroup(mainPanel1Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addComponent(rolCombo,
                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                32,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addComponent(rolLabel,
                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(mailText,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                32,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                                mainPanel1Layout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(contratoLabel1)
                                                                                                .addComponent(passText,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                32,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(122, 122, 122)
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

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(usersPanel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                1599,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(usersPanel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)));
        }// </editor-fold>//GEN-END:initComponents

        /**
         * Maneja la acción de modificar los datos del usuario.
         * Valida los campos y, si todo es correcto, actualiza la información en la base
         * de datos.
         * 
         * @param evt Evento de acción.
         */
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
                        ut.logAction("WARNING", "UsuariosPanel", "Intento de actualización con nombre vacío.");
                        return;
                }

                if (rolObj == null) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.role"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        ut.logAction("WARNING", "UsuariosPanel", "Intento de actualización con rol vacío.");
                        return;
                }

                if (email.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.email"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        ut.logAction("WARNING", "UsuariosPanel", "Intento de actualización con email vacío.");
                        return;
                }

                if (inputPass.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.empty.password"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        ut.logAction("WARNING", "UsuariosPanel", "Intento de actualización con contraseña vacía.");
                        return;
                }

                if (id.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.invalid.id"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        ut.logAction("WARNING", "UsuariosPanel", "Intento de actualización con ID inválido.");
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
                        ut.logAction("OK", "UsuariosPanel", "Perfil actualizado correctamente: " + id);
                        cargarDatos();
                } else {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.update"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        ut.logAction("ERROR", "UsuariosPanel", "Error al actualizar perfil: " + id);
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
