package com.jmmunoz.netfix.vista;

import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import com.jmmunoz.netfix.vista.tema.ThemeManager;
import com.jmmunoz.netfix.controlador.Utilities;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo moderno para mostrar los detalles completos de una incidencia.
 * 
 * @author Juanma Muñoz
 */
public class IncidenciaDetalleDialog extends JDialog {

        private final int idIncidencia;

        // Paneles de secciones
        private JPanel mainContent;
        // Paneles de secciones (outer)
        private JPanel sectClient, sectContract, sectInci, sectComen, sectDevices;
        // Áreas de contenido (inner)
        private JPanel contClient, contContract, contInci, contComen, contDevices;
        private Utilities ut = new Utilities();

        public IncidenciaDetalleDialog(Frame owner, int idIncidencia) {
                super(owner, com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("incid.detail.title")
                                + idIncidencia,
                                true);
                this.idIncidencia = idIncidencia;

                ut.logAction("INFO", "IncidenciaDetalleDialog", "Iniciando vista detalle ID=" + idIncidencia);

                initComponents();
                cargarDatos();
                ThemeManager.getInstance().applyIncidenciaDetalleTheme(this);

                setSize(850, 750);
                setLocationRelativeTo(owner);
        }

        private void initComponents() {
                setLayout(new BorderLayout());
                getContentPane().setBackground(TelecomTheme.APP_BG);

                mainContent = new JPanel();
                mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
                mainContent.setOpaque(false);
                mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));

                JScrollPane scrollPane = new JScrollPane(mainContent);
                scrollPane.setBorder(null);
                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                add(scrollPane, BorderLayout.CENTER);

                // Secciones
                contClient = new JPanel();
                sectClient = createSection(
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.detail.client.title"),
                                contClient);

                contContract = new JPanel();
                sectContract = createSection(
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.detail.contract.title"),
                                contContract);

                contInci = new JPanel();
                sectInci = createSection(
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.detail.incid.title"),
                                contInci);

                contComen = new JPanel();
                sectComen = createSection(
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.detail.comments.title"),
                                contComen);

                contDevices = new JPanel();
                sectDevices = createSection(
                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                .getMessage("incid.detail.devices.title"),
                                contDevices);

                mainContent.add(sectClient);
                mainContent.add(Box.createVerticalStrut(15));
                mainContent.add(sectContract);
                mainContent.add(Box.createVerticalStrut(15));
                mainContent.add(sectInci);
                mainContent.add(Box.createVerticalStrut(15));
                mainContent.add(sectComen);
                mainContent.add(Box.createVerticalStrut(15));
                mainContent.add(sectDevices);
        }

        private JPanel createSection(String title, JPanel contentArea) {
                JPanel panel = new JPanel(new BorderLayout(0, 10));
                ThemeManager.getInstance().cardify(panel);

                JLabel lblTitle = new JLabel(title);
                lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
                lblTitle.setForeground(TelecomTheme.ACCENT_DARK);
                panel.add(lblTitle, BorderLayout.NORTH);

                contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
                contentArea.setOpaque(false);
                panel.add(contentArea, BorderLayout.CENTER);

                return panel;
        }

        private void cargarDatos() {
                try {
                        ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.INCI_DETALLE, idIncidencia);

                        if (rs != null && rs.next()) {
                                int idContrato = rs.getInt("id_contrato");

                                // Llenar Cliente
                                addDetailRow(contClient,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.name"),
                                                rs.getString("nombre_cliente"));
                                addDetailRow(contClient,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.dni"),
                                                rs.getString("dni_cliente"));

                                // Llenar Contrato
                                addDetailRow(contContract,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.contract.id"),
                                                String.valueOf(idContrato));
                                addDetailRow(contContract,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.plan"),
                                                rs.getString("tipo_servicio"));

                                // Llenar Incidencia
                                addDetailRow(contInci,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.incid.id"),
                                                String.valueOf(rs.getInt("id_incidencia")));
                                String estado = rs.getString("estado");
                                addDetailRow(contInci,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.status"),
                                                (estado != null ? estado.toUpperCase() : "-"));
                                addDetailRow(contInci,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.report.date"),
                                                rs.getString("fecha_reporte"));
                                addDetailRow(contInci,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.description"),
                                                rs.getString("descripcion"));

                                String tecnico = rs.getString("tecnico");
                                addDetailRow(contInci,
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.label.tech"),
                                                (tecnico != null ? tecnico
                                                                : com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                                .getMessage("incid.detail.label.tech.none")));

                                String solucion = rs.getString("solucion");
                                if (solucion != null && !solucion.isEmpty()) {
                                        addDetailRow(contInci,
                                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                        .getMessage("incid.detail.label.solution"),
                                                        solucion);
                                }

                                // 2. Comentarios
                                cargarComentarios();

                                // 3. Aparatos
                                cargarAparatos(idContrato);

                        } else {
                                ut.logAction("WARNING", "IncidenciaDetalleDialog",
                                                "ResultSet vacío para ID: " + idIncidencia);
                                contInci.add(new JLabel(
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.msg.no.details")));
                        }
                } catch (SQLException ex) {
                        ut.logAction("ERROR", "IncidenciaDetalleDialog",
                                        "Error DB al cargar detalles: " + ex.getMessage());
                        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(this,
                                        com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                        .getMessage("incid.detail.error.db")
                                                        + ex.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                } finally {
                        mainContent.revalidate();
                        mainContent.repaint();
                }
        }

        private void addDetailRow(JPanel contentPanel, String label, String value) {
                JPanel row = new JPanel(new BorderLayout(10, 0));
                row.setOpaque(false);
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

                JLabel lbl = new JLabel(label);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setForeground(TelecomTheme.TEXT_MUTED);
                lbl.setPreferredSize(new Dimension(150, 25));

                JLabel val = new JLabel(value != null && !value.isEmpty() ? value : "-");
                val.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                val.setForeground(TelecomTheme.TEXT);

                row.add(lbl, BorderLayout.WEST);
                row.add(val, BorderLayout.CENTER);

                contentPanel.add(row);
                contentPanel.add(Box.createVerticalStrut(2));
        }

        private void cargarComentarios() {
                try {
                        ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.COMENTARIOS, idIncidencia);
                        boolean hasComen = false;
                        while (rs != null && rs.next()) {
                                hasComen = true;
                                String info = String.format("[%s] %s: %s", rs.getString("Fecha"),
                                                rs.getString("Agente"),
                                                rs.getString("Comentario"));
                                JLabel lbl = new JLabel(info);
                                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                                contComen.add(lbl);
                        }
                        if (!hasComen) {
                                contComen.add(new JLabel(
                                                com.jmmunoz.netfix.config.AppConfig.getInstance()
                                                                .getMessage("incid.detail.msg.no.comments")));
                        }
                } catch (SQLException ex) {
                        ut.logAction("ERROR", "IncidenciaDetalleDialog",
                                        "Error cargando comentarios: " + ex.getMessage());
                }
        }

        private void cargarAparatos(int idContrato) {
                try {
                        // FTTH
                        ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.APARATOSFTTH, idContrato);
                        while (rs != null && rs.next()) {
                                String desc = com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage(
                                                "incid.detail.device.format",
                                                rs.getString("modelo"),
                                                rs.getString("numero_serie"), rs.getString("mac"));
                                contDevices.add(new JLabel("• " + desc));
                        }

                        // 5G
                        ResultSet rs2 = Utilities.ejecutarConsulta(Utilities.TipoConsulta.APARATOS5G, idContrato);
                        while (rs2 != null && rs2.next()) {
                                contDevices.add(new JLabel("• " + rs2.getString("datos_5g")));
                        }
                } catch (SQLException ex) {
                        ut.logAction("ERROR", "IncidenciaDetalleDialog", "Error cargando aparatos: " + ex.getMessage());
                }
        }
}
