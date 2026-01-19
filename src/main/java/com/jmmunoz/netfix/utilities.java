/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

import com.jmmunoz.netfix.SimuladorDiagnostico.Aparato;
import com.jmmunoz.netfix.SimuladorDiagnostico.Diagnostico;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author juanm
 */
public class utilities {

    public utilities() {
    }

    public boolean loggin(String mail, String passwordIntroducida) {

        try {
            DatabaseManager db = DatabaseManager.getInstance();

            // 1️⃣ Obtener hash desde BD
            String hashBD = db.executePreparedString(
                    querys.login,
                    mail
            );

            // Usuario no existe
            if (hashBD == null) {
                return false;
            }

            // 2️⃣ Comparar hash
            return PasswordUtils.checkPassword(
                    passwordIntroducida,
                    hashBD
            );

        } catch (SQLException ex) {
        }

        return false;
    }

    public String hashPass(String pass) {
        return PasswordUtils.hashPassword(pass);
    }

    public enum TipoConsulta {
        INCIDENCIAS,
        INCI_CONTRATO,
        INCIDENCIAS_RESUELTAS_TECNICO,
        DIAGNOSTICO_APARATO,
        CONTADOR_APARATOS,
        INCI_PORDIA,
        INCI_CONTADOR,
        APARATOSFTTH,
        APARATOS5G,
        TITULAR,
        COMENTARIOS,
        COMUNICAR,
        INSERT_COMENTARIO,
        SOLUCIONAR,
        DERIVAR,
        USUARIOS,
        ROLES,
        UPDATEUSER,
        ALTA,
        USUARIO
    }

    public static ResultSet ejecutarConsulta(TipoConsulta tipo, Object... params) {
        try {
            DatabaseManager db = DatabaseManager.getInstance();

            switch (tipo) {
                case INCIDENCIAS -> {
                    return db.executeQuery(querys.incidencias);
                }

                case INCIDENCIAS_RESUELTAS_TECNICO -> {
                    return db.executeQuery(querys.incidenciasResueltasPorTecnico);
                }

                case CONTADOR_APARATOS -> {
                    return db.executeQuery(querys.contadorAparatos);
                }
                case DIAGNOSTICO_APARATO -> {
                    return db.executePreparedQuery(
                            querys.diagnosticoAparato,
                            params
                    );
                }
                case INCI_CONTRATO -> {
                    return db.executePreparedQuery(
                            querys.inciContra,
                            params
                    );
                }
                case INCI_PORDIA -> {
                    return db.executePreparedQuery(querys.inciXdia);
                }

                case INCI_CONTADOR -> {
                    return db.executePreparedQuery(querys.contadorInci);
                }
                case APARATOSFTTH -> {
                    return db.executePreparedQuery(querys.aparatosFTTH, params);
                }

                case APARATOS5G -> {
                    return db.executePreparedQuery(querys.aparatos5G, params);
                }

                case TITULAR -> {
                    return db.executePreparedQuery(querys.titular, params);
                }
                case COMENTARIOS -> {
                    return db.executePreparedQuery(querys.comentarios, params);
                }
                case USUARIOS -> {
                    return db.executePreparedQuery(querys.usuarios);
                }

                case ROLES -> {
                    return db.executePreparedQuery(querys.roles);
                }

                case USUARIO -> {
                    return db.executePreparedQuery(querys.usuario, params);
                }
            }

        } catch (SQLException ex) {

        }
        return null;
    }

    public int ejecutarUpdate(TipoConsulta tipo, Object... params) {
        try {
            DatabaseManager db = DatabaseManager.getInstance();

            return switch (tipo) {
                case COMUNICAR ->
                    db.executeUpdate(querys.comunicar, params);
                case INSERT_COMENTARIO ->
                    db.executeUpdate(querys.insertComentario, params);
                case SOLUCIONAR ->
                    db.executeUpdate(querys.solucionar, params);
                case DERIVAR ->
                    db.executeUpdate(querys.derivar, params);
                case UPDATEUSER ->
                    db.executeUpdate(querys.updateUser, params);
                case ALTA ->
                    db.executeUpdate(querys.altaUser, params);
                default ->
                    0;
            };

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public class PasswordUtils {

        // Crear hash al registrar usuario
        public static String hashPassword(String plainPassword) {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
        }

        // Comparar password con hash
        public static boolean checkPassword(String plainPassword, String hashedPassword) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }
    }

    public void cargarTabla(JTable tabla, ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnas = meta.getColumnCount();

        DefaultTableModel modelo = new DefaultTableModel();

        // Nombres de columnas
        for (int i = 1; i <= columnas; i++) {
            modelo.addColumn(meta.getColumnName(i));
        }

        // Datos
        while (rs.next()) {
            Object[] fila = new Object[columnas];
            for (int i = 0; i < columnas; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            modelo.addRow(fila);
        }

        tabla.setModel(modelo);

        // --- APLICAR RENDERER PARA COLOREAR FILAS ---
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                int modelRow = table.convertRowIndexToModel(row);
                TableModel model = table.getModel();

                // 🔐 Comprobación de columnas
                if (model.getColumnCount() > 5) {

                    Object estadoValor = model.getValueAt(modelRow, 5);
                    if (estadoValor != null) {
                        String estado = estadoValor.toString();

                        switch (estado) {
                            case "Pendiente" -> {
                                c.setBackground(Color.YELLOW);
                                c.setForeground(Color.BLACK);
                            }
                            case "Resuelto" -> {
                                c.setBackground(Color.GREEN);
                                c.setForeground(Color.BLACK);
                            }
                            case "sin_comunicar" -> {
                                c.setBackground(Color.RED);
                                c.setForeground(Color.WHITE);
                            }
                            default -> {
                                c.setBackground(Color.WHITE);
                                c.setForeground(Color.BLACK);
                            }
                        }
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }

                } else {
                    // Si no hay columna de estado
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                // Mantener el color de selección
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }

                return c;
            }
        });
    }

    public void cargarGrafico(JPanel panelDestino) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ResultSet rs = ejecutarConsulta(TipoConsulta.INCI_PORDIA, 0);

        try {
            while (rs.next()) {
                String dia = rs.getString("dia");
                int total = rs.getInt("total");

                dataset.addValue(total, "Incidencias", dia);
            }
        } catch (SQLException ex) {
            System.getLogger(utilities.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        // Crear gráfico horizontal
        JFreeChart chart = ChartFactory.createBarChart(
                "Incidencias por día",
                "Día",
                "Cantidad",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false
        );

        // Panel del gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(
                panelDestino.getWidth(),
                panelDestino.getHeight()
        ));

        // Insertar en el panel destino
        panelDestino.removeAll();
        panelDestino.setLayout(new BorderLayout());
        panelDestino.add(chartPanel, BorderLayout.CENTER);
        panelDestino.revalidate();
        panelDestino.repaint();
    }

    public int[] obtenerContadorIncidencias() throws SQLException {

        int[] contadores = new int[3];

        ResultSet rs = ejecutarConsulta(TipoConsulta.INCI_CONTADOR);

        if (rs.next()) {
            contadores[0] = rs.getInt("pendientes");
            contadores[1] = rs.getInt("resueltas");
            contadores[2] = rs.getInt("sin_comunicar");
        }

        return contadores;
    }

    public String[] obtenerApaFTTH(int contrato) throws SQLException {
        String[] aparatos = {" ", " ", " "}; // Inicializamos con valores por defecto
        ResultSet rs = ejecutarConsulta(TipoConsulta.APARATOSFTTH, contrato);

        if (rs != null && rs.next()) { // Tomamos la primera fila si existe
            aparatos[0] = rs.getString("numero_Serie");
            aparatos[1] = rs.getString("mac");
            aparatos[2] = rs.getString("modelo");
        }

        return aparatos;
    }

    public void obtenerApa5G(int contrato, JList<String> lista) throws SQLException {
        // Ejecutamos la consulta que devuelve la columna "datos_5g" con GROUP_CONCAT
        ResultSet rs = ejecutarConsulta(TipoConsulta.APARATOS5G, contrato);

        // Creamos el modelo para el JList y lo asociamos
        DefaultListModel<String> model = new DefaultListModel<>();
        lista.setModel(model);

        // Recorremos todas las filas del ResultSet
        while (rs != null && rs.next()) {
            String aparato = rs.getString("datos_5g"); // Columna con toda la info concatenada
            if (aparato != null && !aparato.isEmpty()) {
                model.addElement(aparato);   // Añadimos al JList
            }
        }
    }

    public String obtenerTitular(int contrato) throws SQLException {
        String titular = new String();
        ResultSet rs = ejecutarConsulta(TipoConsulta.TITULAR, contrato);
        if (rs.next()) { // Tomamos la primera fila
            titular = rs.getString("titular");
        }
        return titular;
    }

    public static List<Object[]> buscarDiagnosticoAparato(String searchText) throws SQLException {

        int sAparato = searchText.matches("\\d+")
                ? Integer.parseInt(searchText)
                : 0;

        ResultSet rs = ejecutarConsulta(
                TipoConsulta.DIAGNOSTICO_APARATO,
                searchText,
                searchText,
                sAparato
        );

        ResultSetMetaData meta = rs.getMetaData();
        int columnas = meta.getColumnCount();

        List<Object[]> resultados = new ArrayList<>();

        while (rs.next()) {
            Object[] fila = new Object[columnas];

            for (int i = 1; i <= columnas; i++) {
                fila[i - 1] = rs.getObject(i);
            }

            resultados.add(fila);
        }

        return resultados;
    }

    public boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@netfix\\.(com|es)$";
        return email.matches(regex);
    }

}
