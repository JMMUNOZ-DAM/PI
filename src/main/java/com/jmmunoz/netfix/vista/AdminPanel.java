/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.jmmunoz.netfix.vista;

import com.jmmunoz.netfix.controlador.Utilities;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Juanma Muñoz
 */
/**
 * Panel de Administración del Sistema (Dashboard Principal).
 * <p>
 * Centraliza la gestión de contratos y dispositivos. Permite a los
 * administradores:
 * <ul>
 * <li>Visualizar todos los contratos activos.</li>
 * <li>Asignar y liberar aparatos a contratos.</li>
 * <li>Gestionar números de teléfono asociados a dispositivos 5G.</li>
 * <li>Filtrar contratos por término de búsqueda.</li>
 * </ul>
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class AdminPanel extends javax.swing.JPanel {

    private TableRowSorter<DefaultTableModel> sorter;
    private JTable contratosTable;
    private DefaultTableModel tableModel;

    // Componentes del Panel de Gestión
    private JPanel managementPanel;
    private JLabel selectedContractLabel;
    private JLabel deviceLabel;
    private JButton releaseButton;
    private JButton assignButton;
    private JButton viewLogsButton;

    // Panel de Números 5G
    private JPanel numberPanel;
    private JList<String> numberList;
    private DefaultListModel<String> numberListModel;
    private JButton addNumberButton;
    private JButton delNumberButton;

    // Datos Seleccionados
    private int selectedContratoId = -1;
    private int selectedAparatoId = -1;
    private String selectedTipoAparato = "";
    private JScrollPane scrollPane;

    /**
     * Creates new form incidenciasPanel
     */
    public AdminPanel() {
        initComponents();
        setupCustomUI();
        com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance().applyAdminPanelTheme(this);
        // Asegurar que la BD soporta id_contrato NULL
        Utilities ut = new Utilities();
        ut.ejecutarUpdate(Utilities.TipoConsulta.FIX_SCHEMA);
        ut.logAction("OK", "AdminPanel", "Esquema de base de datos verificado/corregido.");
        setupScrollListener();
        cargarContratos();
    }

    private void setupCustomUI() {
        // Inicializar componentes
        contratosTable = new JTable();
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.table.id"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.table.dni"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.table.device.id"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.table.serial"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.table.model"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.table.type")
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        contratosTable.setModel(tableModel);
        contratosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contratosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onContractSelected();
            }
        });

        // Ordenador
        sorter = new TableRowSorter<>(tableModel);
        contratosTable.setRowSorter(sorter);

        // Estilo
        scrollPane = new JScrollPane(contratosTable);
        // Estilo de tabla manejado ahora por ThemeManager

        // --- Panel de Gestión ---
        managementPanel = new JPanel(new GridBagLayout());
        managementPanel.setBorder(BorderFactory.createTitledBorder(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.management")));

        selectedContractLabel = new JLabel(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.label.select.contract"));
        deviceLabel = new JLabel(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.label.device.none"));

        assignButton = new JButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.assign"));
        releaseButton = new JButton(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.release"));
        assignButton.addActionListener(e -> asignarAparato());
        releaseButton.addActionListener(e -> liberarAparato());

        // Sección 5G
        numberPanel = new JPanel(new BorderLayout());
        numberPanel.setOpaque(false);
        numberPanel.setBorder(BorderFactory
                .createTitledBorder(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.5g")));
        numberListModel = new DefaultListModel<>();
        numberList = new JList<>(numberListModel);
        numberPanel.add(new JScrollPane(numberList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        addNumberButton = new JButton(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.add.number"));
        delNumberButton = new JButton(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.del.number"));
        addNumberButton.addActionListener(e -> addNumber());
        delNumberButton.addActionListener(e -> delNumber());
        btnPanel.add(addNumberButton);
        btnPanel.add(delNumberButton);
        numberPanel.add(btnPanel, BorderLayout.SOUTH);

        // --- DISEÑO ---
        this.setLayout(new BorderLayout());
        this.add(adminPanel, BorderLayout.CENTER);

        adminPanel.removeAll();
        adminPanel.setLayout(new GridBagLayout());
        // Fondo manejado por ThemeManager
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        adminPanel.add(sisTitle, gbc);

        // Restablecer relleno para componentes siguientes
        gbc.fill = GridBagConstraints.BOTH;

        // Búsqueda
        JPanel searchWrapper = new JPanel();
        searchWrapper.setOpaque(false);

        // Corrección: Hacer barra de búsqueda más ancha
        seaField.setPreferredSize(new java.awt.Dimension(300, 35));
        seaField.setFont(new java.awt.Font("Segoe UI", 0, 16));
        // Corrección: Añadir soporte tecla Enter
        seaField.addActionListener(e -> filterTable());

        searchWrapper.add(seaField);
        searchWrapper.add(searchButtSis);

        viewLogsButton = new JButton(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.view.logs"));
        viewLogsButton.setFont(new java.awt.Font("Segoe UI", 0, 18));
        viewLogsButton.addActionListener(
                e -> new LogViewerDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this))
                        .setVisible(true));
        searchWrapper.add(Box.createHorizontalStrut(20)); // Espaciador
        searchWrapper.add(viewLogsButton);

        searchButtSis.addActionListener(e -> filterTable());

        gbc.gridy++;
        adminPanel.add(searchWrapper, gbc);

        // Tabla
        gbc.gridy++;
        gbc.weighty = 1.0;
        adminPanel.add(scrollPane, gbc);

        // Gestión
        gbc.gridy++;
        gbc.weighty = 0.0;
        setupManagementLayout();
        adminPanel.add(managementPanel, gbc);
    }

    private void setupManagementLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        managementPanel.add(selectedContractLabel, gbc);

        gbc.gridy++;
        managementPanel.add(deviceLabel, gbc);

        gbc.gridy++;
        JPanel actPanel = new JPanel();
        actPanel.setOpaque(false);
        actPanel.add(assignButton);
        actPanel.add(releaseButton);
        managementPanel.add(actPanel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        // numberPanel visible solo cuando es 5G
        managementPanel.add(numberPanel, gbc);
        numberPanel.setVisible(false);
    }

    /**
     * Filtra la tabla de contratos según el texto introducido en el buscador.
     * Soporta expresiones regulares y no distingue mayúsculas/minúsculas.
     */
    private void filterTable() {
        String text = seaField.getText().trim();
        if (text.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + text));
        }
    }

    // applyTelecomStyle eliminado - lógica movida a ThemeManager

    /**
     * Carga la lista completa de contratos desde la base de datos.
     * Utiliza la consulta {@code ALL_CONTRATOS_APARATOS} para obtener detalles
     * extendidos (Dispositivo asignado, modelo, etc.).
     */
    private void cargarContratos() {
        try {
            ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.ALL_CONTRATOS_APARATOS);
            tableModel.setRowCount(0);

            while (rs != null && rs.next()) {
                tableModel.addRow(new Object[] {
                        rs.getInt(1), // Contrato
                        rs.getString(2), // DNI
                        rs.getObject(3), // ID Aparato
                        rs.getObject(4), // Nº Serie
                        rs.getObject(5), // Modelo
                        rs.getObject(6) // Tipo
                });
            }
            new Utilities().logAction("OK", "AdminPanel",
                    "Contratos cargados correctamente (" + tableModel.getRowCount() + " registros)");

        } catch (SQLException ex) {
            new Utilities().logAction(
                    "ERROR",
                    "AdminPanel",
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.error.load.contracts")
                            + ex.getMessage());
        }
        resizeColumnWidths(contratosTable);
    }

    /**
     * Ajusta el ancho de las columnas de la tabla al contenido y a la cabecera.
     */
    private void setupScrollListener() {
        scrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                resizeColumnWidths(contratosTable);
            }
        });
    }

    /**
     * Ajusta el ancho de las columnas.
     * Estrategia "Responsive":
     * 1. Calcula el ancho ideal de cada columna (Header vs Contenido).
     * 2. Si el ancho total ideal < ancho del viewport: ESTIRA las columnas para
     * llenar el espacio (Armónico).
     * 3. Si el ancho total ideal > ancho del viewport: Mantiene el ancho ideal y
     * permite SCROLL (Legible).
     */
    private void resizeColumnWidths(JTable table) {
        if (table.getRowCount() == 0)
            return;

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        final javax.swing.table.TableColumnModel columnModel = table.getColumnModel();
        int totalIdealWidth = 0;
        int[] idealWidths = new int[table.getColumnCount()];

        // 1. Calcular anchos ideales
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 60; // Ancho mínimo base

            // Cabecera
            java.awt.Component header = table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, columnModel.getColumn(column).getHeaderValue(), false, false,
                            -1, column);
            width = Math.max(header.getPreferredSize().width + 20, width);

            // Contenido (Optimización: Muestrear primeras 50 filas)
            int limit = Math.min(table.getRowCount(), 50);
            for (int row = 0; row < limit; row++) {
                java.awt.Component renderer = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                width = Math.max(renderer.getPreferredSize().width + 10, width);
            }

            idealWidths[column] = width;
            totalIdealWidth += width;
        }

        // 2. Obtener ancho disponible
        int viewportWidth = scrollPane.getViewport().getWidth();
        // Si no está visible aún, usar un fallback o el tamaño del padre
        if (viewportWidth == 0)
            viewportWidth = table.getParent().getWidth();

        // 3. Aplicar factor de escala si sobra espacio
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
     * Maneja el evento de selección de un contrato en la tabla.
     * Actualiza el panel de gestión lateral con la información del contrato
     * seleccionado y habilita/deshabilita botones según el estado del dispositivo.
     */
    private void onContractSelected() {
        int row = contratosTable.getSelectedRow();
        if (row == -1) {
            selectedContratoId = -1;
            selectedAparatoId = -1;
            selectedTipoAparato = "";
            selectedContractLabel.setText(
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.label.select.contract"));
            deviceLabel
                    .setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.label.device.none"));
            releaseButton.setEnabled(false);
            numberPanel.setVisible(false);
            return;
        }

        row = contratosTable.convertRowIndexToModel(row);
        selectedContratoId = (int) tableModel.getValueAt(row, 0);
        Object apId = tableModel.getValueAt(row, 2);

        selectedContractLabel.setText("Contrato ID: " + selectedContratoId);

        if (apId != null) {
            selectedAparatoId = (int) apId;
            String serie = (String) tableModel.getValueAt(row, 3);
            String modelo = (String) tableModel.getValueAt(row, 4);
            selectedTipoAparato = (String) tableModel.getValueAt(row, 5);

            deviceLabel.setText("Dispositivo: " + modelo + " (" + serie + ") [" + selectedTipoAparato + "]");
            releaseButton.setEnabled(true);

            if ("5G".equalsIgnoreCase(selectedTipoAparato)) {
                numberPanel.setVisible(true);
                loadNumbers();
            } else {
                numberPanel.setVisible(false);
            }
        } else {
            selectedAparatoId = -1;
            selectedTipoAparato = "";
            deviceLabel
                    .setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.label.device.none"));
            releaseButton.setEnabled(false);
            numberPanel.setVisible(false);
        }
        managementPanel.revalidate();
        managementPanel.repaint();
    }

    /**
     * Libera (desasigna) el dispositivo actual del contrato seleccionado.
     * Solicita confirmación al usuario antes de proceder.
     */
    private void liberarAparato() {
        if (selectedAparatoId == -1) {
            com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                    this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.select.required"),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.release.select"),
                    com.jmmunoz.netfix.vista.tema.CustomNotification.Type.WARNING);
            new Utilities().logAction("WARNING", "AdminPanel", "Intento de liberar dispositivo sin selección válida.");
            return;
        }

        int confirm = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showConfirmDialog(this,
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.release.confirm"),
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.release"),
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.OK_OPTION) {
            Utilities ut = new Utilities();
            if (ut.ejecutarUpdate(Utilities.TipoConsulta.LIBERAR_APARATO, selectedAparatoId) > 0) {
                com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                        this,
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.release.success"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.release.success")
                                + selectedContratoId,
                        com.jmmunoz.netfix.vista.tema.CustomNotification.Type.SUCCESS);
                ut.logAction("OK", "AdminPanel", "Dispositivo liberado del contrato " + selectedContratoId);
            } else {
                com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                        this,
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.release.error"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.release.error")
                                + selectedAparatoId,
                        com.jmmunoz.netfix.vista.tema.CustomNotification.Type.ERROR);
                ut.logAction("ERROR", "AdminPanel", "Error liberando dispositivo " + selectedAparatoId);
            }
            cargarContratos();
            onContractSelected(); // Refrescamos para mostrar que el contrato está vacío
        }
    }

    /**
     * Inicia el flujo de asignación de dispositivo.
     * <ol>
     * <li>Carga lista de dispositivos libres.</li>
     * <li>Muestra diálogo de selección.</li>
     * <li>Valida reglas de negocio (ej. FTTH solo en instalación limpia).</li>
     * <li>Si es 5G, solicita número obligatorio.</li>
     * <li>Ejecuta la asignación en BD.</li>
     * </ol>
     */
    private void asignarAparato() {
        if (selectedContratoId == -1) {
            com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                    this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.select.required"),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.select.contract"),
                    com.jmmunoz.netfix.vista.tema.CustomNotification.Type.WARNING);
            new Utilities().logAction("WARNING", "AdminPanel",
                    "Intento de asignar dispositivo sin contrato seleccionado.");
            return;
        }

        // 1. Cargar dispositivos libres
        Vector<DeviceItem> freeDevs = new Vector<>();
        try {
            ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.FREE_APARATOS);
            while (rs != null && rs.next()) {
                freeDevs.add(new DeviceItem(
                        rs.getInt(1), // ID Aparato
                        rs.getString(2), // Nº Serie
                        rs.getString(3), // Modelo
                        rs.getString(4))); // Tipo
            }
        } catch (SQLException ex) {
            com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                    this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.db.error"),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.error.load.devices")
                            + ex.getMessage(),
                    com.jmmunoz.netfix.vista.tema.CustomNotification.Type.ERROR);
            new Utilities().logAction("ERROR", "AdminPanel", "Error cargando equipos libres: " + ex.getMessage());
        }

        if (freeDevs.isEmpty()) {
            com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                    this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.info"),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.no.devices"),
                    com.jmmunoz.netfix.vista.tema.CustomNotification.Type.INFO);
            return;
        }

        // 2. Diálogo de selección
        DeviceItem selected = (DeviceItem) com.jmmunoz.netfix.vista.dialogos.ModernDialog.showInputDialog(
                this,
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.select.device"),
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.assign"),
                JOptionPane.PLAIN_MESSAGE,
                null,
                freeDevs.toArray(),
                freeDevs.get(0));

        if (selected == null) {
            return;
        }

        // 3. VALIDACIONES
        // Regla: FTTH solo si está vacío
        if ("FTTH".equalsIgnoreCase(selected.tipo) && selectedAparatoId != -1) {
            com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                    this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.restriction"),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.ftth.restriction"),
                    com.jmmunoz.netfix.vista.tema.CustomNotification.Type.WARNING);
            return;
        }

        // Manejar Reemplazo (si existe)
        if (selectedAparatoId != -1) {
            int confirm = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showConfirmDialog(this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.replace.confirm",
                            selected.modelo),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.replace"),
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.OK_OPTION) {
                return;
            }

            // Liberar antiguo primero
            new Utilities().ejecutarUpdate(Utilities.TipoConsulta.LIBERAR_APARATO, selectedAparatoId);
        }

        // Regla: Número obligatorio para 5G
        if ("5G".equalsIgnoreCase(selected.tipo)) {
            String num = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showInputDialog(this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.5g.number"));

            if (num == null || num.trim().isEmpty()) {
                com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                        this,
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.cancel"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.5g.cancel"),
                        com.jmmunoz.netfix.vista.tema.CustomNotification.Type.WARNING);
                // Si cancelamos, recargamos para mostrar que el contrato está vacío
                cargarContratos();
                onContractSelected();
                return;
            }

            // Ejecutar Asignación + Añadir Número
            Utilities ut = new Utilities();
            boolean assignOk = ut.ejecutarUpdate(Utilities.TipoConsulta.ASIGNAR_APARATO, selectedContratoId,
                    selected.id) > 0;

            if (assignOk) {
                ut.logAction("OK", "AdminPanel",
                        "Asignado dispositivo " + selected.id + " a contrato " + selectedContratoId);

                if (ut.ejecutarUpdate(Utilities.TipoConsulta.ADD_NUMERO, selected.id, num.trim(),
                        selectedContratoId) > 0) {
                    com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                            this,
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.success"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.5g.success"),
                            com.jmmunoz.netfix.vista.tema.CustomNotification.Type.SUCCESS);
                    ut.logAction("OK", "AdminPanel", "Añadido número " + num + " a dispositivo " + selected.id);
                } else {
                    com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                            this,
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.partial.error"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance()
                                    .getMessage("admin.msg.assign.partial.error"),
                            com.jmmunoz.netfix.vista.tema.CustomNotification.Type.ERROR);
                    ut.logAction("ERROR", "AdminPanel",
                            "Error añadiendo número " + num + " a dispositivo " + selected.id);
                }
            }

        } else {
            // Asignación Estándar
            Utilities ut = new Utilities();
            if (ut.ejecutarUpdate(Utilities.TipoConsulta.ASIGNAR_APARATO, selectedContratoId, selected.id) > 0) {
                com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                        this,
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.success"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.assign.success"),
                        com.jmmunoz.netfix.vista.tema.CustomNotification.Type.SUCCESS);
                ut.logAction("OK", "AdminPanel",
                        "Asignado dispositivo " + selected.id + " a contrato " + selectedContratoId);
            } else {
                com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                        this,
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.error"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.assign.error"),
                        com.jmmunoz.netfix.vista.tema.CustomNotification.Type.ERROR);
                ut.logAction("ERROR", "AdminPanel",
                        "Error asignando dispositivo " + selected.id + " a contrato " + selectedContratoId);
            }
        }

        cargarContratos();
        onContractSelected();
    }

    /**
     * Carga y muestra los números de teléfono asociados al dispositivo 5G
     * seleccionado.
     */
    private void loadNumbers() {
        numberListModel.clear();
        try {
            ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.NUMEROS_APARATO, selectedAparatoId);
            while (rs != null && rs.next()) {
                numberListModel.addElement(rs.getString("numero"));
            }
        } catch (SQLException ex) {
            com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                    this,
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.error"),
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.error.load.numbers")
                            + ex.getMessage(),
                    com.jmmunoz.netfix.vista.tema.CustomNotification.Type.ERROR);
            new Utilities().logAction("ERROR", "AdminPanel", "Error cargando números: " + ex.getMessage());
        }
    }

    /**
     * Solicita y añade un nuevo número de teléfono al dispositivo 5G actual.
     */
    private void addNumber() {
        String num = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showInputDialog(this,
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.add.number"));
        if (num != null && !num.trim().isEmpty()) {
            Utilities ut = new Utilities();
            if (ut.ejecutarUpdate(Utilities.TipoConsulta.ADD_NUMERO, selectedAparatoId, num.trim(),
                    selectedContratoId) > 0) {
                ut.logAction("OK", "AdminPanel",
                        "Añadido número 5G manual: " + num + " al aparato " + selectedAparatoId);
                loadNumbers();
            }
        }
    }

    /**
     * Elimina el número de teléfono seleccionado de la lista.
     */
    private void delNumber() {
        String num = numberList.getSelectedValue();
        if (num == null)
            return;

        int c = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showConfirmDialog(this,
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.msg.del.number.confirm", num));
        if (c == JOptionPane.OK_OPTION) {
            Utilities ut = new Utilities();
            if (ut.ejecutarUpdate(Utilities.TipoConsulta.DEL_NUMERO, num) > 0) {
                ut.logAction("OK", "AdminPanel", "Eliminado número 5G: " + num);
                loadNumbers();
            }
        }
    }

    // Clase auxiliar para ComboBox
    private static class DeviceItem {
        int id;
        String serie, modelo, tipo;

        public DeviceItem(int id, String serie, String modelo, String tipo) {
            this.id = id;
            this.serie = serie;
            this.modelo = modelo;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return tipo + " - " + modelo + " (" + serie + ")";
        }
    }

    /**
     * Diálogo para visualizar los logs del sistema.
     */
    private class LogViewerDialog extends javax.swing.JDialog {
        private javax.swing.JTable logTable;
        private javax.swing.table.DefaultTableModel logModel;
        private javax.swing.JComboBox<String> filterCombo;

        public LogViewerDialog(java.awt.Frame parent) {
            super(parent, true);
            setupUI();
            loadLogs();
            setLocationRelativeTo(parent);
        }

        private void setupUI() {
            setSize(900, 600);
            setLayout(new java.awt.BorderLayout(10, 10));
            com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance().applyLogViewerTheme(this);

            // Barra de herramientas
            javax.swing.JPanel toolbar = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
            toolbar.setOpaque(false);
            toolbar.add(new javax.swing.JLabel(
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.label.filter.type")));

            filterCombo = new javax.swing.JComboBox<>(new String[] {
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.log.filter.all"),
                    "OK", "WARNING", "ERROR"
            });
            filterCombo.addActionListener(e -> loadLogs());
            toolbar.add(filterCombo);

            javax.swing.JButton refreshBtn = new javax.swing.JButton(
                    com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.refresh"));
            refreshBtn.addActionListener(e -> loadLogs());
            toolbar.add(refreshBtn);

            add(toolbar, java.awt.BorderLayout.NORTH);

            // Tabla
            logModel = new javax.swing.table.DefaultTableModel(
                    new Object[][] {},
                    new String[] {
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.log.table.state"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.log.table.panel"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.log.table.desc"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.log.table.date")
                    }) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            logTable = new javax.swing.JTable(logModel);
            com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance().configureLogTableColumns(logTable);

            add(new javax.swing.JScrollPane(logTable), java.awt.BorderLayout.CENTER);
        }

        private void loadLogs() {
            String filter = (String) filterCombo.getSelectedItem();
            if (com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.log.filter.all").equals(filter))
                filter = null;

            logModel.setRowCount(0);

            try {
                java.sql.ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.GET_LOGS, filter, filter);
                while (rs != null && rs.next()) {
                    logModel.addRow(new Object[] {
                            rs.getString("estado"),
                            rs.getString("panel"),
                            rs.getString("descripcion"),
                            rs.getString("fecha_hora")
                    });
                }
            } catch (java.sql.SQLException ex) {
                new Utilities().logAction("ERROR", "AdminPanel", "Error cargando logs: " + ex.getMessage());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminPanel = new javax.swing.JPanel();
        sisTitle = new javax.swing.JLabel();
        seaField = new javax.swing.JTextField();
        searchButtSis = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        // setPreferredSize(new java.awt.Dimension(1592, 946)); // Removed fixed
        // preference

        adminPanel.setBackground(new java.awt.Color(255, 255, 255));
        // adminPanel.setPreferredSize(new java.awt.Dimension(1592, 946)); // Removed
        // fixed preference

        sisTitle.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        sisTitle.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.title.system"));

        searchButtSis.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        searchButtSis.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("admin.button.search"));

        javax.swing.GroupLayout adminPanelLayout = new javax.swing.GroupLayout(adminPanel);
        adminPanel.setLayout(adminPanelLayout);
        adminPanelLayout.setHorizontalGroup(
                adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adminPanelLayout.createSequentialGroup()
                                .addGroup(adminPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(adminPanelLayout.createSequentialGroup()
                                                .addGap(638, 638, 638)
                                                .addComponent(sisTitle))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, adminPanelLayout
                                                .createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(seaField, javax.swing.GroupLayout.PREFERRED_SIZE, 417,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(searchButtSis, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 544, Short.MAX_VALUE)));
        adminPanelLayout.setVerticalGroup(
                adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adminPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sisTitle)
                                .addGap(31, 31, 31)
                                .addGroup(
                                        adminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(seaField, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(searchButtSis, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 813, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(adminPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(adminPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adminPanel;
    private javax.swing.JTextField seaField;
    private javax.swing.JButton searchButtSis;
    private javax.swing.JLabel sisTitle;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JPanel getAdminPanel() {
        return adminPanel;
    }

    public javax.swing.JPanel getManagementPanel() {
        return managementPanel;
    }

    public javax.swing.JLabel getSisTitle() {
        return sisTitle;
    }

    public javax.swing.JLabel getSelectedContractLabel() {
        return selectedContractLabel;
    }

    public javax.swing.JLabel getDeviceLabel() {
        return deviceLabel;
    }

    public javax.swing.JTextField getSeaField() {
        return seaField;
    }

    public javax.swing.JButton getSearchButtSis() {
        return searchButtSis;
    }

    public javax.swing.JTable getContratosTable() {
        return contratosTable;
    }

    public javax.swing.JScrollPane getScrollPane() {
        return scrollPane;
    }

    public javax.swing.JButton getAssignButton() {
        return assignButton;
    }

    public javax.swing.JButton getReleaseButton() {
        return releaseButton;
    }

    public javax.swing.JButton getAddNumberButton() {
        return addNumberButton;
    }

    public javax.swing.JButton getDelNumberButton() {
        return delNumberButton;
    }
}
