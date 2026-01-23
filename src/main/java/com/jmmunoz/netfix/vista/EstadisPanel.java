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
 * Panel de Estadísticas (Dashboard) - Rediseño Total
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
                // Ignoramos initComponents() generado o lo limpiamos
                // initComponents();

                // Configuración base
                setLayout(new BorderLayout());
                setBackground(TelecomTheme.APP_BG);

                buildDashboardLayout();
                cargarDatos();
        }

        private void buildDashboardLayout() {
                removeAll();

                // --- 1. HEADER (Título + KPIs) ---
                JPanel headerPanel = new JPanel(new BorderLayout(0, 20));
                headerPanel.setOpaque(false);
                headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

                // Título Principal
                JLabel title = new JLabel(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.title"));
                title.setFont(new Font("Segoe UI", Font.BOLD, 28));
                title.setForeground(TelecomTheme.ACCENT_DARK); // Azul oscuro
                headerPanel.add(title, BorderLayout.NORTH);

                // KPIs Row
                JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 3 columnas, gap 20
                kpiPanel.setOpaque(false);

                lblPendientesVal = new JLabel("0");
                lblResueltasVal = new JLabel("0");
                lblSincoVal = new JLabel("0");

                // Tarjeta Pendientes (Amarillo/Naranja)
                kpiPanel.add(ThemeManager.getInstance().createKPICard(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.kpi.pending"),
                                lblPendientesVal, new Color(255, 193, 7), "🕒"));

                // Tarjeta Resueltas (Verde)
                kpiPanel.add(ThemeManager.getInstance().createKPICard(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("stats.kpi.resolved"),
                                lblResueltasVal, new Color(40, 167, 69), "✅"));

                // Tarjeta Sin Comunicar (Rojo)
                kpiPanel.add(ThemeManager.getInstance().createKPICard(
                                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage(
                                                "stats.kpi.uncommunicated"),
                                lblSincoVal, new Color(220, 53, 69), "❗"));

                headerPanel.add(kpiPanel, BorderLayout.CENTER);

                add(headerPanel, BorderLayout.NORTH);

                // --- 2. MAIN CONTENT (Split: Table | Chart) ---
                // Usamos un JSplitPane o un GridLayout. El mockup muestra split.
                // Haremos un Panel principal con GridBag o GridLayout para simular el split
                // 50/50 o 40/60.
                // Usaremos JSplitPane para flexibilidad.

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
                scrollTable.getViewport().setBackground(Color.WHITE);
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
                                        System.out.println("DEBUG: Doble clic en fila. ID extraído: " + idInci);

                                        Window parentWindow = SwingUtilities.getWindowAncestor(EstadisPanel.this);
                                        IncidenciaDetalleDialog dialog;
                                        if (parentWindow instanceof Frame frame) {
                                                dialog = new IncidenciaDetalleDialog(frame, idInci);
                                        } else {
                                                // Fallback para cuando estemos dentro de otro diálogo
                                                dialog = new IncidenciaDetalleDialog((Frame) null, idInci);
                                        }
                                        dialog.setVisible(true);
                                }
                        }
                });

                splitPane.setLeftComponent(tableWrapper);

                // -- LADO DERECHO: CHART --
                JPanel chartWrapper = new JPanel(new BorderLayout(0, 10));
                ThemeManager.getInstance().cardify(chartWrapper);

                // Toolbar de filtros (Mes/Año) en el top del chartWrapper
                JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
                toolbar.setOpaque(false);

                // Combos
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

                // Display del Chart
                chartDisplay = new JPanel();
                chartDisplay.setOpaque(false);
                chartDisplay.setLayout(new BorderLayout());
                chartWrapper.add(chartDisplay, BorderLayout.CENTER);

                splitPane.setRightComponent(chartWrapper);

                add(splitPane, BorderLayout.CENTER);
        }

        /**
         * Carga/Refresca todos los datos.
         */
        public void cargarDatos() {
                SwingUtilities.invokeLater(() -> {
                        Utilities ut = new Utilities();
                        try {
                                // 1. Cargar Tabla (Incidencias globales o filtradas?)
                                // El usuario pidió "filtro solo para el gráfico" en el paso anterior,
                                // pero "Incidencias recientes" en la tabla.
                                // Mantendremos la tabla mostrando TODAS (o las recientes) independiente del
                                // filtro de gráfico.
                                ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.INCIDENCIAS, 0);
                                ut.cargarTabla(inciTabla, rs);

                                // Configurar columnas y SORTER (Preservar sorting ID: 887)
                                javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter = new javax.swing.table.TableRowSorter<>(
                                                inciTabla.getModel());
                                inciTabla.setRowSorter(sorter);

                                ThemeManager.getInstance().configureTableColumns(inciTabla);

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
