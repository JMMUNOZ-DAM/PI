/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.jmmunoz.netfix.vista;

import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import com.jmmunoz.netfix.vista.tema.CustomNotification;
import com.jmmunoz.netfix.vista.tema.ThemeManager;
import com.jmmunoz.netfix.controlador.Utilities;
import com.jmmunoz.netfix.modelo.Usuario;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Panel de Gestión de Incidencias.
 * <p>
 * Permite listar, filtrar y gestionar detalladamente las incidencias.
 * Incluye funcionalidades para ver detalles completos, historial de
 * comentarios,
 * asignar técnicos y marcar incidencias como resueltas.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class IncidPanel extends javax.swing.JPanel {

        private TableRowSorter<DefaultTableModel> sorter;
        ResultSet rs;
        ResultSet crs;
        private Utilities ut = new Utilities();
        private Usuario currentUser;

        /**
         * Creates new form incidenciasPanel
         */
        public IncidPanel(com.jmmunoz.netfix.modelo.Usuario user) {
                this.currentUser = user;
                initComponents();
                initCustomComponents();
                configurarBuscador();
                cargarDatos();
                setupSelectionListener();
        }

        /**
         * Carga y refresca la lista de incidencias desde la base de datos.
         * <p>
         * Configura también los listeners de selección de la tabla para rellenar
         * automáticamente el panel de detalles cuando el usuario selecciona una fila.
         * Obtiene datos relacionados (titular, aparatos, comentarios) en tiempo real.
         * </p>
         */
        public void cargarDatos() {
                SwingUtilities.invokeLater(() -> {
                        try {
                                // Guardar ID seleccionado actual antes de recargar
                                int currentId = getSelectedIncidenciaId();

                                rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.INCIDENCIAS);
                                ut.cargarTabla(inciTabla, rs);
                                com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance()
                                                .configureIncidenciasTableColumns(inciTabla);

                                // FORZAR REDIMENSIONADO
                                setupScrollListener(incidenciasTable);
                                resizeColumnWidths(inciTabla, incidenciasTable);

                                ut.logAction("OK", "IncidPanel", "Datos de incidencias cargados correctamente ("
                                                + inciTabla.getRowCount() + " registros)");

                                // Actualizamos el sorter con el nuevo modelo en caso de recarga
                                sorter.setModel((DefaultTableModel) inciTabla.getModel());

                                // Restaurar selección
                                if (currentId != -1) {
                                        restoreSelection(currentId);
                                }

                        } catch (SQLException ex) {
                                ut.logAction("ERROR", "IncidPanel", "Error cargando datos: " + ex.getMessage());
                        }
                });

        }

        /**
         * Configura el motor de búsqueda y filtrado de la tabla.
         * Utiliza un {@link TableRowSorter} para filtrar coincidencias por regex
         * en cualquier columna visible.
         */
        private void configurarBuscador() {
                // Inicializamos el sorter con el modelo actual de la tabla
                DefaultTableModel model = (DefaultTableModel) inciTabla.getModel();
                sorter = new TableRowSorter<>(model);
                inciTabla.setRowSorter(sorter);

                // Configuramos el botón de búsqueda
                searchButton.addActionListener(e -> {
                        String texto = searchField.getText().trim();

                        if (texto.isEmpty()) {
                                sorter.setRowFilter(null);
                        } else {
                                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                        }
                });

                // Soporte para tecla Enter
                searchField.addActionListener(e -> searchButton.doClick());
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
                        resizeColumnWidths(inciTabla, sp);
                }
        }

        /**
         * Ajusta el ancho de las columnas (Lógica adaptativa "armónica").
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
         * Ajusta el ancho de las columnas de la tabla de incidencias.
         * Prioriza la descripción y campos de texto largos.
         */

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */

        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                inciPanel = new javax.swing.JPanel();
                listadoText = new javax.swing.JLabel();
                searchField = new javax.swing.JTextField();
                searchButton = new javax.swing.JButton();
                incidenciasTable = new javax.swing.JScrollPane();
                inciTabla = new javax.swing.JTable();
                inciGTitle = new javax.swing.JLabel();
                datosPanel = new javax.swing.JPanel();
                gestiPanel = new javax.swing.JLabel();
                sepaDatos = new javax.swing.JSeparator();
                jScrollPane1 = new javax.swing.JScrollPane();
                comenTable = new javax.swing.JTable();
                comenLabel = new javax.swing.JLabel();
                numLabel = new javax.swing.JLabel();
                contatoLabel = new javax.swing.JLabel();
                problemaLabel = new javax.swing.JLabel();
                sepaAparatos = new javax.swing.JSeparator();
                tituLabel = new javax.swing.JLabel();
                ftthLabel = new javax.swing.JLabel();
                jScrollPane2 = new javax.swing.JScrollPane();
                lista5G = new javax.swing.JList<>();
                movilLabel = new javax.swing.JLabel();
                soluButton = new javax.swing.JButton();
                actuButton = new javax.swing.JButton();
                enviButton = new javax.swing.JButton();
                txtContrato = new javax.swing.JLabel();
                txtNombre = new javax.swing.JLabel();
                txtID = new javax.swing.JLabel();
                txtDescripcion = new javax.swing.JLabel();
                txtAparato = new javax.swing.JLabel();
                txtMoFT = new javax.swing.JLabel();
                txtMAC = new javax.swing.JLabel();

                setBackground(new java.awt.Color(204, 204, 204));

                inciPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                listadoText.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
                listadoText.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.list.title"));

                searchButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                searchButton.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.search"));
                searchButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                searchButtonActionPerformed(evt);
                        }
                });

                inciTabla.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null }
                                },
                                new String[] {
                                                "Title 1", "Title 2", "Title 3", "Title 4"
                                }) {

                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                });
                incidenciasTable.setViewportView(inciTabla);

                javax.swing.GroupLayout inciPanelLayout = new javax.swing.GroupLayout(
                                inciPanel);
                inciPanel.setLayout(inciPanelLayout);
                inciPanelLayout.setHorizontalGroup(inciPanelLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(inciPanelLayout.createSequentialGroup().addContainerGap()
                                                .addGroup(inciPanelLayout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(inciPanelLayout.createSequentialGroup()
                                                                                .addComponent(listadoText)
                                                                                .addContainerGap(
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addGroup(inciPanelLayout.createSequentialGroup()
                                                                                .addComponent(searchField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                417,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(searchButton,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                138,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(31, 31, 31))))
                                .addComponent(incidenciasTable));
                inciPanelLayout.setVerticalGroup(inciPanelLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inciPanelLayout
                                                .createSequentialGroup().addContainerGap()
                                                .addGroup(inciPanelLayout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(searchField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                32,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(searchButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                32,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18).addComponent(listadoText).addGap(18, 18, 18)
                                                .addComponent(incidenciasTable, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                666, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)));

                inciGTitle.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
                inciGTitle.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.manage.title"));

                datosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                gestiPanel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                gestiPanel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.manage.subtitle"));

                comenTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {
                                { null, null, null },
                                { null, null, null },
                                { null, null, null },
                                { null, null, null }
                }, new String[] {
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.table.agent"),
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.table.comment"),
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.table.date")
                }) {

                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                });
                jScrollPane1.setViewportView(comenTable);

                comenLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                comenLabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.comments.title"));

                numLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                numLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.id"));

                contatoLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                contatoLabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.contract"));

                problemaLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                problemaLabel.setText(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.problem"));

                tituLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                tituLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.holder"));
                tituLabel.setToolTipText("");

                ftthLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                ftthLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.ftth"));

                lista5G.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                jScrollPane2.setViewportView(lista5G);

                movilLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                movilLabel.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.mobile"));

                soluButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                soluButton.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.solve"));
                soluButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                soluButtonActionPerformed(evt);
                        }
                });

                actuButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                actuButton.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.update"));
                actuButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                actuButtonActionPerformed(evt);
                        }
                });

                enviButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                enviButton.setText(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.send.tech"));
                enviButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                enviButtonActionPerformed(evt);
                        }
                });

                txtContrato.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                txtID.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                txtAparato.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                txtMoFT.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                txtMAC.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

                javax.swing.GroupLayout datosPanelLayout = new javax.swing.GroupLayout(datosPanel);
                datosPanel.setLayout(datosPanelLayout);
                datosPanelLayout.setHorizontalGroup(
                                datosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(sepaDatos)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(datosPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(datosPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(sepaAparatos,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(datosPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(datosPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(gestiPanel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(numLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(txtID,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                172,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(jScrollPane1,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(soluButton)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(actuButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                142,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(163, 163,
                                                                                                                                                163)
                                                                                                                                .addComponent(enviButton))
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(comenLabel)
                                                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(contatoLabel)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                                .addComponent(txtContrato,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                89,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(tituLabel)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                                .addComponent(txtNombre,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                522,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(movilLabel)
                                                                                                                                                                .addGap(22, 22, 22)
                                                                                                                                                                .addComponent(txtMAC,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                333,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(problemaLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(txtDescripcion,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(ftthLabel)
                                                                                                                                .addGap(27, 27, 27)
                                                                                                                                .addComponent(txtMoFT,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                319,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(txtAparato,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                319,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(60, 60, 60)))
                                                                                                .addContainerGap()))));
                datosPanelLayout.setVerticalGroup(
                                datosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(datosPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(datosPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(datosPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(datosPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(gestiPanel)
                                                                                                                                .addComponent(numLabel))
                                                                                                                .addComponent(txtID,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                32,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(sepaDatos,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                10,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(datosPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(datosPanelLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(contatoLabel)
                                                                                                                                .addComponent(tituLabel)
                                                                                                                                .addComponent(txtContrato,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                32,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(txtNombre,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                32,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(datosPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(problemaLabel,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(txtDescripcion,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(sepaAparatos,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                10,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(5, 5, 5)
                                                                                                .addGroup(datosPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(txtAparato,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                32,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(ftthLabel)))
                                                                                .addGroup(datosPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                .addComponent(txtMoFT,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                28,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(datosPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(datosPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(31, 31, 31)
                                                                                                .addComponent(movilLabel))
                                                                                .addGroup(datosPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(txtMAC,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                26,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                98,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(comenLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                285,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(
                                                                                datosPanelLayout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(soluButton)
                                                                                                .addComponent(actuButton)
                                                                                                .addComponent(enviButton))
                                                                .addGap(40, 40, 40)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(537, 537, 537)
                                                                .addComponent(inciGTitle)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(datosPanel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(inciPanel,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(inciGTitle)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(datosPanel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(inciPanel,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addGap(0, 0, Short.MAX_VALUE)));
        }// </editor-fold>//GEN-END:initComponents

        private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchButtonActionPerformed
                String text = searchField.getText();
                if (text.trim().isEmpty()) {
                        sorter.setRowFilter(null);
                        ut.logAction("OK", "IncidPanel", "Filtro de búsqueda limpiado.");
                } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                        ut.logAction("OK", "IncidPanel", "Búsqueda realizada con filtro: " + text);
                }
        }// GEN-LAST:event_searchButtonActionPerformed

        private void actuButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_actuButtonActionPerformed
                try {
                        int idIncidencia = getSelectedIncidenciaId();
                        if (idIncidencia == -1) {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.select.req"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.select.row"),
                                                CustomNotification.Type.WARNING);
                                return;
                        }

                        String accion = actuButton.getText();

                        if (com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.communicate")
                                        .equalsIgnoreCase(accion)) {

                                ut.ejecutarUpdate(
                                                Utilities.TipoConsulta.COMUNICAR,
                                                currentUser.getIdUsuario(),
                                                idIncidencia);

                                ut.logAction("OK", "IncidPanel", "Incidencia " + idIncidencia + " comunicada.");
                                cargarDatos();
                        } else if (com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.update")
                                        .equalsIgnoreCase(accion)) {

                                String comentario = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showInputDialog(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.input.comment"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.title.new.comment"),
                                                JOptionPane.PLAIN_MESSAGE);

                                // Si cancela o deja vacío
                                if (comentario == null || comentario.trim().isEmpty()) {
                                        return;
                                }

                                ut.ejecutarUpdate(
                                                Utilities.TipoConsulta.INSERT_COMENTARIO,
                                                idIncidencia,
                                                currentUser.getNombre(),
                                                comentario);
                                ut.logAction("OK", "IncidPanel", "Comentario añadido a incidencia " + idIncidencia);
                        }
                        crs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.COMENTARIOS, idIncidencia);
                        ut.cargarTabla(comenTable, crs);
                        ThemeManager.getInstance()
                                        .configureComentariosTableColumns(comenTable);
                } catch (SQLException ex) {
                        ut.logAction("ERROR", "IncidPanel", "Error actualizando incidencia: " + ex.getMessage());

                }
        }// GEN-LAST:event_actuButtonActionPerformed

        private void soluButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_soluButtonActionPerformed
                int idIncidencia = getSelectedIncidenciaId();
                if (idIncidencia == -1) {
                        CustomNotification.show(
                                        this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.msg.select.req"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.msg.select.solve"),
                                        CustomNotification.Type.WARNING);
                        ut.logAction("ERROR", "IncidPanel", "Intento de solucionar sin seleccionar fila.");
                        return;
                }
                String solucion = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showInputDialog(
                                this,
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.msg.input.solution"),
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.title.new.comment"),
                                JOptionPane.PLAIN_MESSAGE);
                // Si cancela o deja vacío
                if (solucion == null || solucion.trim().isEmpty()) {
                        return;
                }
                ut.ejecutarUpdate(
                                Utilities.TipoConsulta.SOLUCIONAR,
                                currentUser.getIdUsuario(),
                                solucion,
                                idIncidencia);
                ut.logAction("OK", "IncidPanel", "Incidencia " + idIncidencia + " marcada como solucionada.");
                limpiarCampos();
                cargarDatos();

        }// GEN-LAST:event_soluButtonActionPerformed

        private void enviButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_enviButtonActionPerformed
                int idIncidencia = getSelectedIncidenciaId();
                if (idIncidencia == -1) {
                        CustomNotification.show(
                                        this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.msg.select.req"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.msg.select.row"),
                                        CustomNotification.Type.WARNING);
                        return;
                }

                if (idIncidencia == 0) {
                        CustomNotification.show(
                                        this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.title.invalid"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.msg.select.valid"),
                                        CustomNotification.Type.WARNING);
                        return;
                }

                // 1. Obtener lista de técnicos reales
                java.util.List<Object[]> tecnicos = ut.getListaTecnicos();
                if (tecnicos.isEmpty()) {
                        CustomNotification.show(
                                        this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.title.data.error"),
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.error.no.techs"),
                                        CustomNotification.Type.ERROR);
                        return;
                }

                // Componentes del Diálogo
                JComboBox<String> comboTecnicos = new JComboBox<>();
                tecnicos.forEach(t -> comboTecnicos.addItem((String) t[1])); // Nombre
                comboTecnicos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

                JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
                JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
                dateSpinner.setEditor(dateEditor);
                dateSpinner.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

                JComboBox<String> comboHuecos = new JComboBox<>();
                comboHuecos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

                // Panel
                JPanel panel = new JPanel(new GridLayout(3, 2, 10, 20));
                panel.setOpaque(false);

                JLabel lblTech = new JLabel(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.tech"));
                lblTech.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

                JLabel lblDate = new JLabel(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.date"));
                lblDate.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

                JLabel lblTime = new JLabel(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.label.time"));
                lblTime.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

                panel.add(lblTech);
                panel.add(comboTecnicos);
                panel.add(lblDate);
                panel.add(dateSpinner);
                panel.add(lblTime);
                panel.add(comboHuecos);

                // Listener para actualizar huecos
                Runnable updateHuecos = () -> {
                        comboHuecos.removeAllItems();
                        int selectedIndex = comboTecnicos.getSelectedIndex();
                        if (selectedIndex < 0)
                                return;

                        int idTecnico = (int) tecnicos.get(selectedIndex)[0];
                        Date fecha = (Date) dateSpinner.getValue();

                        java.util.List<String> huecos = ut.getHuecosLibres(idTecnico, fecha);
                        if (huecos.isEmpty()) {
                                comboHuecos.addItem(com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.msg.no.availability"));
                        } else {
                                huecos.forEach(comboHuecos::addItem);
                        }
                };

                comboTecnicos.addActionListener(e -> updateHuecos.run());
                dateSpinner.addChangeListener(e -> updateHuecos.run());

                // Carga inicial
                updateHuecos.run();

                int res = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showConfirmDialog(
                                this,
                                panel,
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.title.schedule"),
                                JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                        String hora = (String) comboHuecos.getSelectedItem();
                        if (hora == null || hora.equals(com.jmmunoz.netfix.config.AppConfig.getInstance()
                                        .getMessage("incid.msg.no.availability"))) {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.title.invalid.time"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.invalid.time"),
                                                CustomNotification.Type.WARNING);
                                return;
                        }

                        int idTecnico = (int) tecnicos.get(comboTecnicos.getSelectedIndex())[0];
                        Date fecha = (Date) dateSpinner.getValue();

                        if (ut.agendarCita(idTecnico, idIncidencia, fecha, hora)) {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.title.scheduled"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.scheduled"),
                                                CustomNotification.Type.SUCCESS);
                                ut.logAction("OK", "IncidPanel", "Cita agendada para incidencia " + idIncidencia);
                                cargarDatos(); // Refrescar tabla incidencias
                        } else {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.title.schedule.error"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.schedule.error"),
                                                CustomNotification.Type.ERROR);
                                ut.logAction("ERROR", "IncidPanel",
                                                "Fallo al agendar cita para incidencia " + idIncidencia);
                        }
                }
        }// GEN-LAST:event_enviButtonActionPerformed

        /**
         * Aplica el diseño visual corporativo.
         * Estiliza paneles, tablas y textos según {@link TelecomTheme}.
         * Reestructura el layout para un diseño responsivo de dos columnas (Detalle |
         * Lista).
         */
        private void initCustomComponents() {
                btnDiagFTTH = new javax.swing.JButton(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.diagnose"));
                btnDiagMovil = new javax.swing.JButton(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.btn.diagnose"));

                btnDiagFTTH.addActionListener(e -> {
                        if (getSelectedIncidenciaId() == -1) {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.select.req"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.select.first"),
                                                CustomNotification.Type.WARNING);
                                return;
                        }
                        String mac = txtMAC.getText();
                        if (mac != null && !mac.trim().isEmpty()) {
                                MainFrame mf = (MainFrame) javax.swing.SwingUtilities
                                                .getWindowAncestor(this);
                                if (mf != null) {
                                        mf.navegarAparatos(mac);
                                }
                        } else {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.title.missing.mac"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.missing.mac"),
                                                CustomNotification.Type.WARNING);
                        }
                });

                btnDiagMovil.addActionListener(e -> {
                        if (getSelectedIncidenciaId() == -1) {
                                CustomNotification.show(
                                                this,
                                                "Selección requerida",
                                                "Selecciona una incidencia primero.",
                                                CustomNotification.Type.WARNING);
                                return;
                        }
                        String sel = lista5G.getSelectedValue();
                        if (sel != null && !sel.trim().isEmpty()) {
                                String sn = sel.substring(sel.indexOf(":") + 1, sel.indexOf("|")).trim();
                                MainFrame mf = (MainFrame) javax.swing.SwingUtilities
                                                .getWindowAncestor(this);
                                if (mf != null) {
                                        mf.navegarAparatos(sn);
                                }
                        } else {
                                CustomNotification.show(
                                                this,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.select.req"),
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.msg.select.5g"),
                                                CustomNotification.Type.WARNING);
                        }
                });

                ThemeManager.getInstance().applyIncidPanelTheme(this);
        }

        /**
         * Limpia los campos de texto del panel de detalles.
         * Se llama después de resolver una incidencia o realizar una acción que
         * requiera reset.
         */
        private void limpiarCampos() {
                txtContrato.setText("");
                txtNombre.setText("");
                txtDescripcion.setText("");
                txtMoFT.setText("");
                lista5G.setModel(new DefaultListModel<>());
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton actuButton;
        private javax.swing.JLabel comenLabel;
        private javax.swing.JTable comenTable;
        private javax.swing.JLabel contatoLabel;
        private javax.swing.JPanel datosPanel;
        private javax.swing.JButton enviButton;
        private javax.swing.JLabel ftthLabel;
        private javax.swing.JLabel gestiPanel;
        private javax.swing.JLabel inciGTitle;
        private javax.swing.JPanel inciPanel;
        private javax.swing.JTable inciTabla;
        private javax.swing.JScrollPane incidenciasTable;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JList<String> lista5G;
        private javax.swing.JLabel listadoText;
        private javax.swing.JLabel movilLabel;
        private javax.swing.JLabel numLabel;
        private javax.swing.JLabel problemaLabel;
        private javax.swing.JButton searchButton;
        private javax.swing.JTextField searchField;
        private javax.swing.JSeparator sepaAparatos;
        private javax.swing.JSeparator sepaDatos;
        private javax.swing.JButton soluButton;
        private javax.swing.JLabel tituLabel;
        private javax.swing.JLabel txtAparato;
        private javax.swing.JLabel txtContrato;
        private javax.swing.JLabel txtDescripcion;
        private javax.swing.JLabel txtID;
        private javax.swing.JLabel txtMAC;
        private javax.swing.JLabel txtMoFT;
        private javax.swing.JLabel txtNombre;
        private javax.swing.JButton btnDiagFTTH;
        private javax.swing.JButton btnDiagMovil;
        // End of variables declaration//GEN-END:variables

        public javax.swing.JButton getActuButton() {
                return actuButton;
        }

        public javax.swing.JButton getBtnDiagFTTH() {
                return btnDiagFTTH;
        }

        public javax.swing.JButton getBtnDiagMovil() {
                return btnDiagMovil;
        }

        public javax.swing.JLabel getComenLabel() {
                return comenLabel;
        }

        public javax.swing.JTable getComenTable() {
                return comenTable;
        }

        public javax.swing.JLabel getContatoLabel() {
                return contatoLabel;
        }

        public javax.swing.JPanel getDatosPanel() {
                return datosPanel;
        }

        public javax.swing.JButton getEnviButton() {
                return enviButton;
        }

        public javax.swing.JLabel getFtthLabel() {
                return ftthLabel;
        }

        public javax.swing.JLabel getGestiPanel() {
                return gestiPanel;
        }

        public javax.swing.JLabel getInciGTitle() {
                return inciGTitle;
        }

        public javax.swing.JPanel getInciPanel() {
                return inciPanel;
        }

        public javax.swing.JTable getInciTabla() {
                return inciTabla;
        }

        public javax.swing.JScrollPane getIncidenciasTable() {
                return incidenciasTable;
        }

        public javax.swing.JScrollPane getJScrollPane1() {
                return jScrollPane1;
        }

        public javax.swing.JScrollPane getJScrollPane2() {
                return jScrollPane2;
        }

        public javax.swing.JList<String> getLista5G() {
                return lista5G;
        }

        public javax.swing.JLabel getListadoText() {
                return listadoText;
        }

        public javax.swing.JLabel getMovilLabel() {
                return movilLabel;
        }

        public javax.swing.JLabel getNumLabel() {
                return numLabel;
        }

        public javax.swing.JLabel getProblemaLabel() {
                return problemaLabel;
        }

        public javax.swing.JButton getSearchButton() {
                return searchButton;
        }

        public javax.swing.JTextField getSearchField() {
                return searchField;
        }

        public javax.swing.JSeparator getSepaAparatos() {
                return sepaAparatos;
        }

        public javax.swing.JSeparator getSepaDatos() {
                return sepaDatos;
        }

        public javax.swing.JButton getSoluButton() {
                return soluButton;
        }

        public javax.swing.JLabel getTituLabel() {
                return tituLabel;
        }

        public javax.swing.JLabel getTxtAparato() {
                return txtAparato;
        }

        public javax.swing.JLabel getTxtContrato() {
                return txtContrato;
        }

        public javax.swing.JLabel getTxtDescripcion() {
                return txtDescripcion;
        }

        public javax.swing.JLabel getTxtID() {
                return txtID;
        }

        public javax.swing.JLabel getTxtMAC() {
                return txtMAC;
        }

        public javax.swing.JLabel getTxtMoFT() {
                return txtMoFT;
        }

        public javax.swing.JLabel getTxtNombre() {
                return txtNombre;
        }

        /**
         * Obtiene el ID de la incidencia seleccionada.
         * Intenta obtenerlo primero de la fila seleccionada en la tabla.
         * Si no hay fila seleccionada, intenta leerlo del campo txtID (incidencia en
         * gestión).
         * 
         * @return ID de incidencia o -1 si no se encuentra.
         */
        private int getSelectedIncidenciaId() {
                int fila = inciTabla.getSelectedRow();
                if (fila != -1) {
                        try {
                                return Integer.parseInt(inciTabla.getValueAt(fila, 0).toString());
                        } catch (NumberFormatException e) {
                                return -1;
                        }
                }
                try {
                        String text = txtID.getText();
                        if (text != null && !text.trim().isEmpty()) {
                                return Integer.parseInt(text.trim());
                        }
                } catch (NumberFormatException e) {
                        // Ignora el error
                }
                return -1;
        }

        private void setupSelectionListener() {
                inciTabla.getSelectionModel().addListSelectionListener(e -> {
                        if (!e.getValueIsAdjusting()) {
                                int fila = inciTabla.getSelectedRow();
                                if (fila != -1) {
                                        try {
                                                txtID.setText(inciTabla.getValueAt(fila, 0).toString());
                                                txtContrato.setText(inciTabla.getValueAt(fila, 1)
                                                                .toString());
                                                txtDescripcion.setText(inciTabla.getValueAt(fila, 2)
                                                                .toString());

                                                txtNombre.setText(
                                                                ut.obtenerTitular(Integer.parseInt(
                                                                                inciTabla.getValueAt(
                                                                                                fila, 1)
                                                                                                .toString())));

                                                String[] aparatos = ut.obtenerApaFTTH(Integer
                                                                .parseInt(txtContrato.getText()));
                                                ut.obtenerApa5G(Integer.parseInt(inciTabla
                                                                .getValueAt(fila, 1).toString()),
                                                                lista5G);
                                                txtAparato.setText(aparatos[0]);
                                                txtMoFT.setText(aparatos[2]);
                                                txtMAC.setText(aparatos[1]);
                                                crs = Utilities.ejecutarConsulta(
                                                                Utilities.TipoConsulta.COMENTARIOS,
                                                                Integer.valueOf(inciTabla
                                                                                .getValueAt(fila, 0)
                                                                                .toString()));
                                                ut.cargarTabla(comenTable, crs);
                                                com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance()
                                                                .configureComentariosTableColumns(
                                                                                comenTable);

                                                if ((inciTabla.getValueAt(fila,
                                                                5).toString())
                                                                .equals("sin_comunicar")) {
                                                        actuButton.setText(
                                                                        com.jmmunoz.netfix.config.AppConfig
                                                                                        .getInstance()
                                                                                        .getMessage("incid.btn.communicate"));
                                                } else {
                                                        actuButton.setText(
                                                                        com.jmmunoz.netfix.config.AppConfig
                                                                                        .getInstance()
                                                                                        .getMessage("incid.btn.update"));
                                                }
                                        } catch (SQLException ex) {
                                                ut.logAction("ERROR", "IncidPanel",
                                                                "Error cargando detalles incidencia: "
                                                                                + ex.getMessage());
                                        }

                                }
                        }
                });
        }

        private void restoreSelection(int idIncidencia) {
                try {
                        for (int i = 0; i < inciTabla.getRowCount(); i++) {
                                Object val = inciTabla.getValueAt(i, 0); // asume que el ID es la primera columna
                                if (val != null) {
                                        int id = Integer.parseInt(val.toString());
                                        if (id == idIncidencia) {
                                                inciTabla.setRowSelectionInterval(i, i);
                                                // Asegura que la vista del viewport se desplace a la selección
                                                java.awt.Rectangle rect = inciTabla.getCellRect(i, 0, true);
                                                inciTabla.scrollRectToVisible(rect);
                                                return;
                                        }
                                }
                        }
                } catch (Exception e) {
                        ut.logAction("WARNING", "IncidPanel", "Error restaurando selección: " + e.getMessage());
                }
        }
}
