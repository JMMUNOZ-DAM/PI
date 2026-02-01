/*
 * EstadisPanel.java
 */
package com.jmmunoz.netfix.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.jmmunoz.netfix.controlador.Utilities;
import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import com.jmmunoz.netfix.vista.tema.ThemeManager;

/**
 * Panel de Estadísticas (Dashboard)
 * 
 * @author Juanma Muñoz
 */
public class EstadisPanel extends javax.swing.JPanel {

        // Componentes UI
        private JLabel lblPendientesVal;
        private JLabel lblResueltasVal;
        private JLabel lblSincoVal;

        private JTable inciTabla;
        private JPanel chartDisplay; // Donde se pinta el gráfico JFreeChart

        // Filtros
        private JComboBox<String> monthCombo;
        private JComboBox<Integer> yearCombo;

        public EstadisPanel() {
                // Ignoramos initComponents() generado
                // initComponents();

                // Configuración base
                setLayout(new BorderLayout());
                setBackground(TelecomTheme.APP_BG);

                buildDashboardLayout();
                // Carga inicial de datos
                cargarDatos();
        }

        /**
         * Construye y organiza el layout del dashboard.
         * Incluye la cabecera con KPIs, la tabla de datos y el gráfico.
         */
        private void buildDashboardLayout() {
                removeAll();

                // --- 1. CABECERA (Título + KPIs) ---
                JPanel headerPanel = new JPanel(new BorderLayout(0, 20));
                headerPanel.setOpaque(false);
                headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

                JLabel title = new JLabel(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.title"));
                title.setFont(new Font("Segoe UI", Font.BOLD, 28));
                title.setForeground(TelecomTheme.ACCENT_DARK); // Azul oscuro
                headerPanel.add(title, BorderLayout.NORTH);

                // Fila de KPIs
                JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 3 columnas, espacio 20
                kpiPanel.setOpaque(false);

                lblPendientesVal = new JLabel("0");
                lblResueltasVal = new JLabel("0");
                lblSincoVal = new JLabel("0");

                kpiPanel.add(ThemeManager.getInstance().createKPICard(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.kpi.pending"),
                                lblPendientesVal, TelecomTheme.WARN, "🕒"));

                kpiPanel.add(ThemeManager.getInstance().createKPICard(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.kpi.resolved"),
                                lblResueltasVal, TelecomTheme.OK, "✅"));

                kpiPanel.add(ThemeManager.getInstance().createKPICard(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage(
                                                "stats.kpi.uncommunicated"),
                                lblSincoVal, TelecomTheme.ERROR, "❗"));

                headerPanel.add(kpiPanel, BorderLayout.CENTER);

                add(headerPanel, BorderLayout.NORTH);

                // --- 2. CONTENIDO PRINCIPAL (División: Tabla | Gráfico) ---
                // Usa JSplitPane para flexibilidad.

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPane.setOpaque(false);
                splitPane.setBorder(new EmptyBorder(0, 20, 20, 20));
                splitPane.setDividerSize(10);
                splitPane.setResizeWeight(0.45); // 45% tabla, 55% gráfico

                // -- LADO IZQUIERDO: TABLA --
                JPanel tableWrapper = new JPanel(new BorderLayout(0, 10));
                ThemeManager.getInstance().cardify(tableWrapper); // Estilo tarjeta base

                JLabel tableTitle = new JLabel(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.table.title"));
                tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
                tableTitle.setForeground(TelecomTheme.TEXT);
                tableWrapper.add(tableTitle, BorderLayout.NORTH);

                inciTabla = new JTable();
                JScrollPane scrollTable = new JScrollPane(inciTabla);
                scrollTable.setBorder(BorderFactory.createEmptyBorder()); // Sin borde extra
                scrollTable.getViewport().setBackground(TelecomTheme.WHITE);
                tableWrapper.add(scrollTable, BorderLayout.CENTER);

                // Listener para doble clic -> Ver detalles
                inciTabla.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                if (e.getClickCount() == 2 && inciTabla.getSelectedRow() != -1) {
                                        int viewRow = inciTabla.getSelectedRow();
                                        int modelRow = inciTabla.convertRowIndexToModel(viewRow);

                                        // Obtener ID (asumiendo que está en la columna 0 "Incidencia")
                                        Object val = inciTabla.getModel().getValueAt(modelRow, 0);
                                        int idInci = 0;
                                        if (val instanceof Number num) {
                                                idInci = num.intValue();
                                        } else {
                                                idInci = Integer.parseInt(val.toString());
                                        }

                                        Window parentWindow = SwingUtilities.getWindowAncestor(EstadisPanel.this);
                                        IncidenciaDetalleDialog dialog;
                                        if (parentWindow instanceof Frame frame) {
                                                dialog = new IncidenciaDetalleDialog(frame, idInci);
                                        } else {
                                                // Alternativa para cuando estemos dentro de otro diálogo
                                                dialog = new IncidenciaDetalleDialog((Frame) null, idInci);
                                        }
                                        dialog.setVisible(true);
                                }
                        }
                });

                splitPane.setLeftComponent(tableWrapper);

                // -- LADO DERECHO: GRÁFICO --
                JPanel chartWrapper = new JPanel(new BorderLayout(0, 10));
                ThemeManager.getInstance().cardify(chartWrapper);

                // Barra de herramientas de filtros (Mes/Año) en la parte superior del
                // chartWrapper
                JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
                toolbar.setOpaque(false);

                // Combos
                String[] meses = new String[12];
                for (int i = 0; i < 12; i++) {
                        meses[i] = com.jmmunoz.netfix.config.AppConfig.getInstance()
                                        .getMessage("stats.month." + (i + 1));
                }
                monthCombo = new JComboBox<>(meses);

                yearCombo = new JComboBox<>();
                int currentYear = LocalDate.now().getYear();
                for (int i = currentYear - 5; i <= currentYear + 5; i++) {
                        yearCombo.addItem(i);
                }
                yearCombo.setSelectedItem(currentYear);
                monthCombo.setSelectedIndex(LocalDate.now().getMonthValue() - 1); // Mes actual

                // Listeners
                java.awt.event.ActionListener reloadListener = e -> cargarDatos(); // O updateChart solo
                monthCombo.addActionListener(reloadListener);
                yearCombo.addActionListener(reloadListener);

                toolbar.add(new JLabel(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.label.month")));
                toolbar.add(monthCombo);
                toolbar.add(new JLabel(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.label.year")));
                toolbar.add(yearCombo);

                chartWrapper.add(toolbar, BorderLayout.NORTH);

                // Visualización del Gráfico
                chartDisplay = new JPanel();
                chartDisplay.setOpaque(false);
                chartDisplay.setLayout(new BorderLayout());
                chartWrapper.add(chartDisplay, BorderLayout.CENTER);

                splitPane.setRightComponent(chartWrapper);

                setupScrollListener(scrollTable);
                add(splitPane, BorderLayout.CENTER);
        }

        /**
         * Configura el listener para redimensionar columnas al cambiar el tamaño del
         * scroll pane.
         * 
         * @param scrollPane El JScrollPane que contiene la tabla.
         */
        private void setupScrollListener(JScrollPane scrollPane) {
                scrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
                        @Override
                        public void componentResized(java.awt.event.ComponentEvent e) {
                                resizeColumnWidths(inciTabla, scrollPane);
                        }
                });
        }

        /**
         * Ajusta el ancho de las columnas (Lógica adaptativa "armónica").
         * Copiado de AdminPanel para consistencia.
         */
        private void resizeColumnWidths(JTable table, JScrollPane scrollPane) {
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
                                        .getTableCellRendererComponent(table,
                                                        columnModel.getColumn(column).getHeaderValue(), false, false,
                                                        -1, column);
                        width = Math.max(header.getPreferredSize().width + 20, width);

                        // Contenido (Mostrar 50 filas)
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
         * Carga/Refresca todos los datos.
         */
        /**
         * Carga y refresca todos los datos del panel.
         * Actualiza la tabla de incidencias, el gráfico estadístico y los contadores
         * KPI.
         * Se ejecuta en el hilo de despacho de eventos de Swing.
         */
        public void cargarDatos() {
                SwingUtilities.invokeLater(() -> {
                        Utilities ut = new Utilities();
                        try {
                                // 1. Cargar Tabla (Incidencias globales o filtradas)
                                ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.INCIDENCIAS, 0);
                                ut.cargarTabla(inciTabla, rs);

                                // Configurar columnas y ORDENADOR (Preservar ID ordenación: 887)
                                javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter = new javax.swing.table.TableRowSorter<>(
                                                inciTabla.getModel());
                                inciTabla.setRowSorter(sorter);

                                ThemeManager.getInstance().configureTableColumns(inciTabla);

                                // FORZAR REDIMENSIONADO
                                JScrollPane sp = (JScrollPane) inciTabla.getParent().getParent(); // Viewport ->
                                                                                                  // ScrollPane
                                resizeColumnWidths(inciTabla, sp);

                                // 2. Cargar Gráfico (Con filtros)
                                int mes = monthCombo.getSelectedIndex() + 1;
                                int anio = (Integer) yearCombo.getSelectedItem();
                                ut.cargarGrafico(chartDisplay, mes, anio);

                                // 3. Actualizar KPIs - Pendientes/Resueltas etc.
                                int[] contadores = ut.obtenerContadorIncidencias();
                                lblPendientesVal.setText(String.valueOf(contadores[0]));
                                lblResueltasVal.setText(String.valueOf(contadores[1]));
                                lblSincoVal.setText(String.valueOf(contadores[2]));

                                ut.logAction("OK", "EstadisPanel", "Dashboard Actualizado.");
                        } catch (SQLException ex) {
                                ut.logAction("ERROR", "EstadisPanel", "Error cargando datos: " + ex.getMessage());
                        }
                });
        }

        // Getters para ThemeManager
        public JTable getInciTabla() {
                return inciTabla;
        }

        public JPanel getGrafiPanel() {
                return chartDisplay;
        }

        public JLabel getLblPendientesVal() {
                return lblPendientesVal;
        }

        public JLabel getLblResueltasVal() {
                return lblResueltasVal;
        }

        public JLabel getLblSincoVal() {
                return lblSincoVal;
        }

        @Override
        public void setBackground(Color bg) {
                super.setBackground(bg);
                if (chartDisplay != null)
                        chartDisplay.setBackground(bg);
        }
}
