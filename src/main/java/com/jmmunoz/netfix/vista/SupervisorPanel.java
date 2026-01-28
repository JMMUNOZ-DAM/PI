/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.jmmunoz.netfix.vista;

import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import com.jmmunoz.netfix.vista.tema.ThemeManager;
import com.jmmunoz.netfix.controlador.Utilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

/**
 *
 * @author Juanma Muñoz
 */
/**
 * Panel de Supervisión y Gestión de Usuarios.
 * <p>
 * Permite a los supervisores visualizar, buscar, modificar y dar de alta
 * nuevos usuarios en el sistema. Incluye gestión de roles y contraseñas.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class SupervisorPanel extends javax.swing.JPanel {

        /**
         * Creates new form incidenciasPanel
         */
        private TableRowSorter<DefaultTableModel> sorter;
        ResultSet rs;
        ResultSet crs;
        Utilities ut = new Utilities();
        com.jmmunoz.netfix.modelo.Usuario currentUser;

        public SupervisorPanel(com.jmmunoz.netfix.modelo.Usuario user) {
                this.currentUser = user;
                initComponents();
                // anula tamaños fijos del diseñador
                setPreferredSize(null);
                setMinimumSize(new java.awt.Dimension(0, 0));
                setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

                usersPanel.setPreferredSize(null);
                usersPanel.setMinimumSize(new java.awt.Dimension(0, 0));
                usersPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                usersPanel.setMinimumSize(new java.awt.Dimension(0, 0));
                usersPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                applyTelecomStyle();
                configurarBuscador();
                configurarBuscador();
                cargarDatos();
                setupRoleListeners();
        }

        private void setupRoleListeners() {
                rolAlta.addItemListener(e -> {
                        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                                String role = (String) e.getItem();
                                boolean isTecnico = "Tecnico".equalsIgnoreCase(role);
                                especialidadLabelAlta.setVisible(isTecnico);
                                especialidadAlta.setVisible(isTecnico);
                        }
                });

                rolCombo.addItemListener(e -> {
                        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                                Object item = e.getItem();
                                if (item != null) {
                                        String role = item.toString();
                                        boolean isTecnico = "Tecnico".equalsIgnoreCase(role);
                                        especialidadLabelMod.setVisible(isTecnico);
                                        especialidadCombo.setVisible(isTecnico);
                                }
                        }
                });
        }

        private boolean canAssignRole(String role) {
                if ("Sistemas".equalsIgnoreCase(currentUser.getRol())) {
                        return true;
                }
                if ("Supervisor".equalsIgnoreCase(currentUser.getRol())) {
                        return "Tecnico".equalsIgnoreCase(role) || "Supervisor".equalsIgnoreCase(role);
                }
                return false;
        }

        /**
         * Carga los roles disponibles en el combobox y rellena la tabla de usuarios.
         * <p>
         * Obtiene los datos mediante consultas {@code ROLES} y {@code USUARIOS}
         * a la base de datos. Configura también el listener de selección de la tabla
         * para rellenar el formulario de edición.
         * </p>
         */
        public void cargarDatos() {
                crs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.ROLES, 0);
                rolAlta.removeAllItems(); // Clear before adding
                try {
                        while (crs.next()) {
                                String r = crs.getString("descripcion");
                                if (canAssignRole(r)) {
                                        rolAlta.addItem(r);
                                }
                        }
                } catch (SQLException ex) {
                        System.getLogger(SupervisorPanel.class.getName()).log(System.Logger.Level.ERROR, (String) null,
                                        ex);
                }

                SwingUtilities.invokeLater(() -> {
                        try {
                                if ("Supervisor".equalsIgnoreCase(currentUser.getRol())) {
                                        // Los supervisores ven una lista restringida (sin "Sistemas")
                                        rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.USUARIOS_STRICT, 0);
                                } else {
                                        // Sistemas/Admin ven todo
                                        rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.USUARIOS, 0);
                                }

                                ut.cargarTabla(usersTable, rs);

                                // ACTUALIZAR EL SORTER INMEDIATAMENTE DESPUÉS DE ACTUALIZAR EL MODELO para
                                // evitar
                                // IndexOutOfBoundsException
                                sorter.setModel((DefaultTableModel) usersTable.getModel());

                                // Forzar redimensionado tras la carga
                                resizeColumnWidths(usersTable, jScrollPane1);

                                usersTable.getSelectionModel().addListSelectionListener(e -> {
                                        if (!e.getValueIsAdjusting()) {
                                                int fila = usersTable.getSelectedRow();
                                                if (fila != -1) {
                                                        idText.setText(usersTable.getValueAt(fila, 0).toString());
                                                        nameText.setText(usersTable.getValueAt(fila, 1).toString());

                                                        // Recargar roles para el combo de edición con filtrado
                                                        ResultSet rsRoles = Utilities.ejecutarConsulta(
                                                                        Utilities.TipoConsulta.ROLES, 0);
                                                        rolCombo.removeAllItems();
                                                        try {
                                                                while (rsRoles.next()) {
                                                                        String r = rsRoles.getString("descripcion");
                                                                        if (canAssignRole(r)) {
                                                                                rolCombo.addItem(r);
                                                                        }
                                                                }
                                                        } catch (SQLException ex) {
                                                                System.getLogger(SupervisorPanel.class.getName()).log(
                                                                                System.Logger.Level.ERROR,
                                                                                (String) null, ex);
                                                        }

                                                        String currentRole = usersTable.getValueAt(fila, 2).toString();
                                                        // If current role is not in the allowed list (e.g. Supervisor
                                                        // viewing Sistemas),
                                                        // simply add it temporarily so it shows up, or handle it?
                                                        // Requirement says "only can GIVE role", doesn't say can't
                                                        // view.
                                                        // But if they edit, they can't save as Sistemas.
                                                        // Let's ensure selected item is set if available.
                                                        rolCombo.setSelectedItem(currentRole);

                                                        mailText.setText(usersTable.getValueAt(fila, 3).toString());
                                                        passText.setText(usersTable.getValueAt(fila, 4).toString());

                                                        // Load specialty if technician
                                                        if ("Tecnico".equalsIgnoreCase(currentRole)) {
                                                                String idStr = usersTable.getValueAt(fila, 0)
                                                                                .toString();
                                                                try {
                                                                        ResultSet specRes = Utilities.ejecutarConsulta(
                                                                                        Utilities.TipoConsulta.GET_TECNICO_SPEC,
                                                                                        idStr);
                                                                        if (specRes != null && specRes.next()) {
                                                                                String spec = specRes.getString(
                                                                                                "especialidad");
                                                                                especialidadCombo.setSelectedItem(spec);
                                                                        }
                                                                        if (specRes != null)
                                                                                specRes.close();
                                                                } catch (SQLException ex) {
                                                                        System.out.println("Error loading specialty: "
                                                                                        + ex.getMessage());
                                                                }
                                                        }
                                                }
                                        }
                                });
                                // Actualizamos el sorter con el nuevo modelo en caso de recarga
                                // sorter.setModel((DefaultTableModel) usersTable.getModel());

                        } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                        }
                });

        }

        private void setupScrollListener(javax.swing.JScrollPane scrollPane) {
                // Evitar duplicar listeners si se llama varias veces
                for (java.awt.event.ComponentListener cl : scrollPane.getComponentListeners()) {
                        if (cl instanceof ResizerListener)
                                return;
                }
                scrollPane.addComponentListener(new ResizerListener(scrollPane));
        }

        private class ResizerListener extends java.awt.event.ComponentAdapter {
                private final javax.swing.JScrollPane sp;

                public ResizerListener(javax.swing.JScrollPane sp) {
                        this.sp = sp;
                }

                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                        resizeColumnWidths(usersTable, sp);
                }
        }

        /**
         * Ajusta el ancho de las columnas (Lógica Responsive "Harmonic").
         */
        private void resizeColumnWidths(javax.swing.JTable table, javax.swing.JScrollPane scrollPane) {
                if (table.getRowCount() == 0)
                        return;

                table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                final javax.swing.table.TableColumnModel columnModel = table.getColumnModel();
                int totalIdealWidth = 0;
                int[] idealWidths = new int[table.getColumnCount()];

                // 1. Calcular anchos ideales
                for (int column = 0; column < table.getColumnCount(); column++) {
                        int width = 60; // Ancho mínimo base

                        // Cabecera
                        java.awt.Component header = table.getTableHeader().getDefaultRenderer()
                                        .getTableCellRendererComponent(table,
                                                        columnModel.getColumn(column).getHeaderValue(), false, false,
                                                        -1, column);
                        width = Math.max(header.getPreferredSize().width + 20, width);

                        // Contenido (Muestrear 50 filas)
                        int limit = Math.min(table.getRowCount(), 50);
                        for (int row = 0; row < limit; row++) {
                                java.awt.Component renderer = table.prepareRenderer(table.getCellRenderer(row, column),
                                                row, column);
                                width = Math.max(renderer.getPreferredSize().width + 10, width);
                        }

                        idealWidths[column] = width;
                        totalIdealWidth += width;
                }

                // 2. Obtener ancho disponible
                int viewportWidth = scrollPane.getViewport().getWidth();
                if (viewportWidth == 0)
                        viewportWidth = table.getParent() != null ? table.getParent().getWidth() : 0;

                // 3. Aplicar escala si sobra espacio
                double scaleFactor = 1.0;
                if (viewportWidth > totalIdealWidth && totalIdealWidth > 0) {
                        scaleFactor = (double) viewportWidth / totalIdealWidth;
                }

                for (int column = 0; column < table.getColumnCount(); column++) {
                        int finalWidth = (int) (idealWidths[column] * scaleFactor);
                        columnModel.getColumn(column).setPreferredWidth(finalWidth);
                }
        }

        /**
         * Configura el buscador de usuarios.
         * Establece un {@link TableRowSorter} en la tabla para filtrar por nombre,
         * email o rol mediante expresiones regulares.
         */
        private void configurarBuscador() {
                // Inicializamos el sorter con el modelo actual de la tabla
                DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
                sorter = new TableRowSorter<>(model);
                usersTable.setRowSorter(sorter);

                // Configuramos el botón de búsqueda
                searchButton1.addActionListener(e -> {
                        String texto = searchField1.getText().trim();

                        if (texto.isEmpty()) {
                                sorter.setRowFilter(null);
                        } else {
                                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                        }
                });
        }

        /**
         * Aplica el tema visual corporativo.
         * Rediseña el layout principal, ajusta fuentes, colores y estilos de tabla.
         */
        private void applyTelecomStyle() {

                // 1) Fondo general del panel
                setBackground(TelecomTheme.APP_BG);
                setOpaque(true);
                usersPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 0));
                usersPanel.setBackground(TelecomTheme.APP_BG);
                usersPanel.setOpaque(true);

                // 2) Ajusta tipografías (dashboard)
                ususTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
                ususTitle.setForeground(TelecomTheme.ACCENT_DARK);

                altasTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                altasTitle.setForeground(TelecomTheme.TEXT);

                userLlabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                userLlabel.setForeground(TelecomTheme.TEXT);

                // Labels de formulario más pequeños
                Font lbl = new Font("Segoe UI", Font.PLAIN, 14);
                Color muted = TelecomTheme.TEXT_MUTED;

                idLabel.setFont(lbl);
                idLabel.setForeground(muted);
                nameLabel1.setFont(lbl);
                nameLabel1.setForeground(muted);
                emaiLabel.setFont(lbl);
                emaiLabel.setForeground(muted);
                rolLabel.setFont(lbl);
                rolLabel.setForeground(muted);
                contratoLabel1.setFont(lbl);
                contratoLabel1.setForeground(muted);

                altaLabel.setFont(lbl);
                altaLabel.setForeground(muted);
                rLabel.setFont(lbl);
                rLabel.setForeground(muted);
                maiLabel.setFont(lbl);
                maiLabel.setForeground(muted);
                passLabel.setFont(lbl);
                passLabel.setForeground(muted);
                confiLabel.setFont(lbl);
                confiLabel.setForeground(muted);

                idText.setFont(new Font("Segoe UI", Font.BOLD, 14));
                idText.setForeground(TelecomTheme.TEXT);

                // Inputs
                Font inp = new Font("Segoe UI", Font.PLAIN, 14);
                searchField1.setFont(inp);
                searchField1.setPreferredSize(new java.awt.Dimension(400, 32));
                searchField1.addActionListener(e -> searchButton1.doClick());

                nameText.setFont(inp);
                nameText.addActionListener(e -> modButton.doClick());
                mailText.setFont(inp);
                mailText.addActionListener(e -> modButton.doClick());
                passText.setFont(inp);
                passText.addActionListener(e -> modButton.doClick());
                rolCombo.setFont(inp);

                nameAlta.setFont(inp);
                nameAlta.addActionListener(e -> altaButton.doClick());
                mailAlta.setFont(inp);
                mailAlta.addActionListener(e -> altaButton.doClick());
                passAlta.setFont(inp);
                passAlta.addActionListener(e -> altaButton.doClick());
                confAlta.setFont(inp);
                confAlta.addActionListener(e -> altaButton.doClick());
                rolAlta.setFont(inp);
                // Especialidades
                especialidadLabelAlta.setFont(lbl);
                especialidadLabelAlta.setForeground(muted);
                especialidadAlta.setFont(inp);
                especialidadCombo.setFont(inp);
                especialidadLabelMod.setFont(lbl);
                especialidadLabelMod.setForeground(muted);

                // Botones
                Font btn = new Font("Segoe UI", Font.BOLD, 14);
                searchButton1.setFont(btn);
                modButton.setFont(btn);
                bajaButton.setFont(btn);
                altaButton.setFont(btn);

                // 3) Quita los bordes antiguos y pon cards
                mainPanel.setBorder(BorderFactory.createEmptyBorder());
                mainPanel.setOpaque(false); // lo vamos a envolver

                altaPanel.setBorder(BorderFactory.createEmptyBorder()); // fuera etched
                altaPanel.setOpaque(false);

                // 4) Tabla estilo telecom
                ThemeManager.getInstance().styleTable(usersTable, jScrollPane1);

                // Remove fixed sizes on table scroll if present
                jScrollPane1.setMinimumSize(new Dimension(200, 200));
                jScrollPane1.setPreferredSize(null);
                usersTable.setFillsViewportHeight(true);
                jScrollPane1.setBorder(BorderFactory.createEmptyBorder());

                setupScrollListener(jScrollPane1);

                ThemeManager.getInstance().cardify(mainPanel);
                // TelecomUI.cardify(altaPanel); // No cardify altaPanel to avoid double-card
                // look if implied

                // ---------------------------------------------------------
                // LAYOUT REFACTOR - ROOT ONLY
                // (We preserve mainPanel's internal GroupLayout to match AparatosPanel
                // behavior)
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

                // 1) Title
                usersPanel.add(ususTitle, gbc);

                // 2) Buscador
                javax.swing.JPanel searchWrapper = new javax.swing.JPanel(
                                new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
                searchWrapper.setOpaque(false);
                searchWrapper.add(searchField1);
                searchWrapper.add(searchButton1);

                gbc.gridy++;
                usersPanel.add(searchWrapper, gbc);

                // 3) Main Panel (Content)
                gbc.gridy++;
                gbc.weighty = 0.0;
                gbc.fill = java.awt.GridBagConstraints.HORIZONTAL; // Use available width
                usersPanel.add(mainPanel, gbc);

                // Push top
                gbc.gridy++;
                gbc.weighty = 1.0;
                gbc.fill = java.awt.GridBagConstraints.BOTH;
                usersPanel.add(javax.swing.Box.createVerticalGlue(), gbc);

                revalidate();
                repaint();

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

                especialidadAlta = new javax.swing.JComboBox<>();
                especialidadCombo = new javax.swing.JComboBox<>();
                especialidadLabelAlta = new javax.swing.JLabel();
                especialidadLabelMod = new javax.swing.JLabel();

                especialidadAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                especialidadAlta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Calle", "Agente" }));
                especialidadAlta.setVisible(false);

                especialidadCombo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                especialidadCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Calle", "Agente" }));
                especialidadCombo.setVisible(false);

                especialidadLabelAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                especialidadLabelAlta.setText("Especialidad:");
                especialidadLabelAlta.setVisible(false);

                especialidadLabelMod.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                especialidadLabelMod.setText("Especialidad:");
                especialidadLabelMod.setVisible(false);

                usersPanel = new javax.swing.JPanel();
                ususTitle = new javax.swing.JLabel();
                searchField1 = new javax.swing.JTextField();
                searchButton1 = new javax.swing.JButton();
                mainPanel = new javax.swing.JPanel();
                nameLabel1 = new javax.swing.JLabel();
                emaiLabel = new javax.swing.JLabel();
                rolLabel = new javax.swing.JLabel();
                contratoLabel1 = new javax.swing.JLabel();
                altasTitle = new javax.swing.JLabel();
                userLlabel = new javax.swing.JLabel();
                modButton = new javax.swing.JButton();
                bajaButton = new javax.swing.JButton();
                altaPanel = new javax.swing.JPanel();
                altaLabel = new javax.swing.JLabel();
                nameAlta = new javax.swing.JTextField();
                rLabel = new javax.swing.JLabel();
                rolAlta = new javax.swing.JComboBox<>();
                maiLabel = new javax.swing.JLabel();
                mailAlta = new javax.swing.JTextField();
                passLabel = new javax.swing.JLabel();
                passAlta = new javax.swing.JPasswordField();
                confiLabel = new javax.swing.JLabel();
                confAlta = new javax.swing.JPasswordField();
                altaButton = new javax.swing.JButton();
                jScrollPane1 = new javax.swing.JScrollPane();
                usersTable = new javax.swing.JTable();
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
                ususTitle.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.title"));

                searchButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                searchButton1.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.btn.search"));

                mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                nameLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                nameLabel1.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.name"));

                emaiLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                emaiLabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.email"));

                rolLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                rolLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.role"));

                contratoLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                contratoLabel1.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.pass"));

                altasTitle.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                altasTitle.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.altas.title"));
                altasTitle.setToolTipText("");

                userLlabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                userLlabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.users.label"));

                modButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                modButton.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.btn.modify"));
                modButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                modButtonActionPerformed(evt);
                        }
                });

                bajaButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                bajaButton.setText("Dar de Baja");
                bajaButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bajaButtonActionPerformed(evt);
                        }
                });

                altaPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                altaLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                altaLabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.name"));

                nameAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                rLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                rLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.role"));

                rolAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                maiLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.email")
                                .toUpperCase());

                mailAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                passLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                passLabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.pass"));

                passAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                confiLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                confiLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance()
                                .getMessage("supervisor.label.confirm"));

                confAlta.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                altaButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                altaButton.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.btn.add"));
                altaButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                altaButtonActionPerformed(evt);
                        }
                });

                // Eliminamos configuraciones de fuente manuales aquí para usar
                // applyTelecomStyle
                // especialidadLabelAlta.setFont(new java.awt.Font("Segoe UI", 0, 18));
                // especialidadAlta.setFont(new java.awt.Font("Segoe UI", 0, 24));
                // especialidadLabelMod.setFont(new java.awt.Font("Segoe UI", 0, 18));
                // especialidadCombo.setFont(new java.awt.Font("Segoe UI", 0, 24));

                javax.swing.GroupLayout altaPanelLayout = new javax.swing.GroupLayout(altaPanel);
                altaPanel.setLayout(altaPanelLayout);
                altaPanelLayout.setHorizontalGroup(
                                altaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(altaPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(altaPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addGroup(altaPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(altaLabel)
                                                                                                                .addComponent(maiLabel))
                                                                                                .addGap(46, 46, 46)
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(mailAlta,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                377,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(nameAlta,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                377,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(altaPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(passLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(passAlta)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(altaPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(altaPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(0, 102, Short.MAX_VALUE)
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(altaPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(rLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                .addComponent(rolAlta,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                244,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(altaPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(especialidadLabelAlta)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(especialidadAlta,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                244,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                .addGroup(altaPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(confiLabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(confAlta)))
                                                                .addContainerGap())
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, altaPanelLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(altaButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                166,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(389, 389, 389)));
                altaPanelLayout.setVerticalGroup(
                                altaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(altaPanelLayout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addGroup(altaPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(altaPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(altaLabel)
                                                                                                                .addComponent(nameAlta,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(45, 45, 45)
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(maiLabel)
                                                                                                                .addComponent(mailAlta,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(altaPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(rLabel)
                                                                                                                .addComponent(rolAlta,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(altaPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(especialidadLabelAlta)
                                                                                                                .addComponent(especialidadAlta,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                .addGap(66, 66, 66)
                                                                .addGroup(altaPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(passLabel)
                                                                                .addComponent(passAlta,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(confiLabel)
                                                                                .addComponent(confAlta,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(54, 54, 54)
                                                                .addComponent(altaButton)
                                                                .addContainerGap(136, Short.MAX_VALUE)));

                usersTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null }
                                },
                                new String[] {
                                                "Title 1", "Title 2", "Title 3", "Title 4"
                                }));
                jScrollPane1.setViewportView(usersTable);

                nameText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                mailText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                rolCombo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                passText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                idLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                idLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.label.id"));

                idText.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(mainPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                false)
                                                                                                                                .addComponent(altaPanel,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addComponent(modButton)
                                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                                .addComponent(bajaButton))
                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(nameLabel1)
                                                                                                                                                                .addComponent(emaiLabel))
                                                                                                                                                .addGap(31, 31, 31)
                                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                mainPanelLayout.createSequentialGroup()
                                                                                                                                                                                                .addComponent(nameText))
                                                                                                                                                                .addComponent(mailText))
                                                                                                                                                .addGap(382, 382,
                                                                                                                                                                382)))
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(idLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(idText,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                168,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(mainPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(altasTitle)
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(contratoLabel1)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(passText,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                443,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(mainPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(rolLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(rolCombo,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                220,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(especialidadLabelMod)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(especialidadCombo,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                220,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addGap(10, 10, 10)))
                                                                .addGroup(mainPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                640,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(userLlabel))
                                                                .addContainerGap()));
                mainPanelLayout.setVerticalGroup(
                                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(mainPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(mainPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(mainPanelLayout
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
                                                                                                .addGroup(mainPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                                .addComponent(rolCombo,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                32,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(rolLabel)))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(especialidadLabelMod)
                                                                                                                                                .addComponent(especialidadCombo,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                32,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                .addGroup(mainPanelLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(nameLabel1)
                                                                                                                                .addComponent(nameText,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                37,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(mainPanelLayout
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
                                                                                                .addGroup(mainPanelLayout
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
                                                                                                .addGap(18, 18, 18)
                                                                                                .addGroup(mainPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(modButton)
                                                                                                                .addComponent(bajaButton))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(altasTitle)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(altaPanel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(mainPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(6, 6, 6)
                                                                                                .addComponent(userLlabel)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jScrollPane1)))
                                                                .addContainerGap()));

                javax.swing.GroupLayout usersPanelLayout = new javax.swing.GroupLayout(usersPanel);
                usersPanel.setLayout(usersPanelLayout);
                usersPanelLayout.setHorizontalGroup(
                                usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(usersPanelLayout.createSequentialGroup()
                                                                .addGroup(usersPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(usersPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(mainPanel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(usersPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(448, 448, 448)
                                                                                                .addGroup(usersPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(ususTitle)
                                                                                                                .addGroup(usersPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(searchField1,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                417,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(45, 45, 45)
                                                                                                                                .addComponent(searchButton1,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                138,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                .addContainerGap()));
                usersPanelLayout.setVerticalGroup(
                                usersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usersPanelLayout
                                                                .createSequentialGroup()
                                                                .addComponent(ususTitle)
                                                                .addGap(37, 37, 37)
                                                                .addGroup(usersPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(searchField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                32,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(searchButton1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                32,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(25, 25, 25)
                                                                .addComponent(mainPanel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 16, Short.MAX_VALUE)));

                // --- LAYOUT ---
                this.setLayout(new java.awt.BorderLayout());
                this.add(usersPanel, java.awt.BorderLayout.CENTER);
        }// </editor-fold>//GEN-END:initComponents

        /**
         * Acción del botón "Modificar".
         * Valida los campos del formulario de edición y actualiza los datos del usuario
         * seleccionado en la base de datos.
         * 
         * @param evt Evento del botón.
         */
        private void modButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_modButtonActionPerformed
                // Obtener valores
                String nombre = nameText.getText().trim();
                String email = mailText.getText().trim();
                String inputPass = new String(passText.getPassword()).trim();
                String id = idText.getText().trim();
                Object rolObj = rolCombo.getSelectedItem();

                // Retrieve original hash from table to compare
                int selectedRow = usersTable.getSelectedRow();
                String originalHash = "";
                if (selectedRow != -1) {
                        originalHash = usersTable.getValueAt(selectedRow, 4).toString();
                }

                // Use utility to decide whether to use original hash or new hash
                String passwordToSend = ut.procesarPasswordUpdate(inputPass, originalHash);

                // Validaciones
                if (nombre.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.name"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (rolObj == null) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.role"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (email.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.email"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (!ut.checkEmail(email)) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        "El email debe pertenecer al dominio corporativo (@netfix.com o @netfix.es)",
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (inputPass.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.pass"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (id.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.invalid.id"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Ejecutar UPDATE
                ut.ejecutarUpdate(Utilities.TipoConsulta.UPDATEUSER, nombre, rolObj.toString(), email, passwordToSend,
                                id, especialidadCombo.getSelectedItem());

                com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("supervisor.success.update"),
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("supervisor.title.success"),
                                JOptionPane.INFORMATION_MESSAGE);
                ut.logAction("OK", "SupervisorPanel", "Usuario actualizado: " + email);
                cargarDatos();
        }// GEN-LAST:event_modButtonActionPerformed

        /**
         * Acción del botón "ALTA" (Registrar Usuario).
         * Valida los campos del formulario de alta (nombre, rol, email, passwords)
         * e inserta el nuevo usuario en la base de datos si todo es correcto.
         * 
         * @param evt Evento del botón.
         */
        private void altaButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_altaButtonActionPerformed
                // Obtener valores
                String nombre = nameAlta.getText().trim();
                String email = mailAlta.getText().trim();

                String password = new String(passAlta.getPassword()).trim();

                // Si confiAlta es JPasswordField, usa esto:
                String confiPass = new String(confAlta.getPassword()).trim();
                // Si confiAlta es JTextField, entonces sería:
                // String confiPass = confiAlta.getText().trim();

                Object rolObj = rolAlta.getSelectedItem();

                // Validaciones
                if (nombre.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.name"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        nameAlta.requestFocus();
                        return;
                }

                if (rolObj == null) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.role"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (email.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.email"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        mailAlta.requestFocus();
                        return;
                }

                if (!ut.checkEmail(email)) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        "El email debe pertenecer al dominio corporativo (@netfix.com o @netfix.es)",
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        mailAlta.requestFocus();
                        return;
                }

                if (password.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.empty.pass"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        passAlta.requestFocus();
                        return;
                }

                if (confiPass.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.confirm.pass"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        confAlta.requestFocus();
                        return;
                }

                if (!password.equals(confiPass)) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.error.mismatch.pass"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("supervisor.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        passAlta.requestFocus();
                        return;
                }

                ut.ejecutarUpdate(
                                Utilities.TipoConsulta.ALTA,
                                nombre,
                                rolObj.toString(),
                                email,
                                ut.hashPass(password),
                                especialidadAlta.getSelectedItem());

                com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("supervisor.success.add"),
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("supervisor.title.success"),
                                JOptionPane.INFORMATION_MESSAGE);
                ut.logAction("OK", "SupervisorPanel", "Usuario creado: " + email);
                limpiarCamposAlta();
                cargarDatos();
        }// GEN-LAST:event_altaButtonActionPerformed

        private void limpiarCamposAlta() {
                nameAlta.setText("");
                mailAlta.setText("");
                passAlta.setText("");
                confAlta.setText("");
                rolAlta.setSelectedIndex(-1);
                especialidadAlta.setVisible(false);
                especialidadLabelAlta.setVisible(false);
        }

        /**
         * Limpia los campos del formulario de edición (no el de alta).
         */
        private void limpiarCamposMod() {
                nameText.setText("");
                mailText.setText("");
                passText.setText("");
                idText.setText("");
                rolCombo.setSelectedIndex(-1); // nada seleccionado
        }

        private void bajaButtonActionPerformed(java.awt.event.ActionEvent evt) {
                // Obtener ID
                String id = idText.getText().trim();

                // Validaciones
                if (id.isEmpty()) {
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.error.invalid.id"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("user.title.error"),
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Confirmación
                int confirm = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showConfirmDialog(this,
                                "¿Estás seguro de que quieres dar de baja a este usuario? Esta acción no se puede deshacer.",
                                "Confirmar Baja",
                                javax.swing.JOptionPane.YES_NO_OPTION);

                if (confirm == javax.swing.JOptionPane.OK_OPTION) {
                        // Ejecutar DELETE
                        int result = ut.ejecutarUpdate(
                                        Utilities.TipoConsulta.DELETEUSER,
                                        id);

                        if (result > 0) {
                                com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                                "Usuario dado de baja correctamente.",
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("user.title.success"),
                                                JOptionPane.INFORMATION_MESSAGE);
                                ut.logAction("OK", "SupervisorPanel", "Usuario eliminado: " + id);
                                limpiarCamposMod();
                                cargarDatos();
                        } else {
                                com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                                "Error al dar de baja el usuario.",
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("user.title.error"),
                                                JOptionPane.ERROR_MESSAGE);
                                ut.logAction("ERROR", "SupervisorPanel", "Error al eliminar usuario: " + id);
                        }
                }
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton bajaButton; // Added manually
        private javax.swing.JButton altaButton;
        private javax.swing.JLabel altaLabel;
        private javax.swing.JPanel altaPanel;
        private javax.swing.JLabel altasTitle;
        private javax.swing.JPasswordField confAlta;
        private javax.swing.JLabel confiLabel;
        private javax.swing.JLabel contratoLabel1;
        private javax.swing.JLabel emaiLabel;
        private javax.swing.JLabel idLabel;
        private javax.swing.JLabel idText;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JLabel maiLabel;
        private javax.swing.JTextField mailAlta;
        private javax.swing.JTextField mailText;
        private javax.swing.JPanel mainPanel;
        private javax.swing.JButton modButton;
        private javax.swing.JTextField nameAlta;
        private javax.swing.JLabel nameLabel1;
        private javax.swing.JTextField nameText;
        private javax.swing.JPasswordField passAlta;
        private javax.swing.JLabel passLabel;
        private javax.swing.JPasswordField passText;
        private javax.swing.JLabel rLabel;
        private javax.swing.JComboBox<String> rolAlta;
        private javax.swing.JComboBox<String> rolCombo;
        private javax.swing.JLabel rolLabel;
        private javax.swing.JButton searchButton1;
        private javax.swing.JTextField searchField1;
        private javax.swing.JLabel userLlabel;
        private javax.swing.JPanel usersPanel;
        private javax.swing.JTable usersTable;
        private javax.swing.JLabel ususTitle;
        // Declaración de variables - no modificar
        private javax.swing.JComboBox<String> especialidadAlta;
        private javax.swing.JComboBox<String> especialidadCombo;
        private javax.swing.JLabel especialidadLabelAlta;
        private javax.swing.JLabel especialidadLabelMod;
        // Fin de declaración de variables
}
