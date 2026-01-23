/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.controlador;

import com.jmmunoz.netfix.modelo.DatabaseManager;
import com.jmmunoz.netfix.modelo.querys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
 * Clase de utilidad que centraliza la lógica de negocio, acceso a datos y
 * operaciones UI comunes.
 * 
 * @author Juanma Muñoz
 */
public class Utilities {

    public Utilities() {
    }

    /**
     * Verifica las credenciales de inicio de sesión de un usuario.
     * 
     * @param mail                Correo electrónico del usuario.
     * @param passwordIntroducida Contraseña en texto plano.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean loggin(String mail, String passwordIntroducida) {

        try {
            DatabaseManager db = DatabaseManager.getInstance();

            // 1️⃣ Obtener hash desde BD
            String hashBD = db.executePreparedString(
                    querys.login,
                    mail);

            // Usuario no existe
            if (hashBD == null) {
                return false;
            }

            // 2️⃣ Comparar hash
            return PasswordUtils.checkPassword(
                    passwordIntroducida,
                    hashBD);

        } catch (SQLException ex) {
        }

        return false;
    }

    /**
     * Genera un hash seguro (BCrypt) para una contraseña.
     */
    public String hashPass(String pass) {
        return PasswordUtils.hashPassword(pass);
    }

    /**
     * Enumeración que define los tipos de consultas SQL disponibles en el sistema.
     * Se utiliza para enrutar la ejecución de sentencias en DatabaseManager.
     */
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
        USUARIOS_STRICT,
        ROLES,
        UPDATEUSER,
        ALTA,
        USUARIO,
        ALL_CONTRATOS_APARATOS,
        FREE_APARATOS,
        ASIGNAR_APARATO,
        LIBERAR_APARATO,
        NUMEROS_APARATO,
        ADD_NUMERO,
        DEL_NUMERO,
        FIX_SCHEMA,
        CREATE_LOGS_TABLE,
        INSERT_LOG,
        GET_LOGS,
        INCI_MES_ANIO,
        INCI_DETALLE,
        INSERT_INCIDENCIA_FULL
    }

    /**
     * Ejecuta una consulta SQL de tipo SELECT basada en el tipo especificado.
     * 
     * @param tipo   El tipo de consulta {@link TipoConsulta} a ejecutar.
     * @param params Parámetros variables para la consulta preparada.
     * @return {@link ResultSet} con los resultados o null si hay error.
     */
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
                            params);
                }
                case INCI_CONTRATO -> {
                    return db.executePreparedQuery(
                            querys.inciContra,
                            params);
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
                case USUARIOS_STRICT -> {
                    return db.executePreparedQuery(querys.usuariosStrict);
                }

                case ROLES -> {
                    return db.executePreparedQuery(querys.roles);
                }

                case USUARIO -> {
                    return db.executePreparedQuery(querys.usuario, params);
                }
                case ALL_CONTRATOS_APARATOS -> {
                    return db.executePreparedQuery(querys.allContratosAparatos);
                }
                case FREE_APARATOS -> {
                    return db.executePreparedQuery(querys.freeAparatos);
                }
                case NUMEROS_APARATO -> {
                    return db.executePreparedQuery(querys.numerosPorAparato, params);
                }
                case GET_LOGS -> {
                    return db.executePreparedQuery(querys.getLogs, params);
                }
                case INCI_MES_ANIO -> {
                    return db.executePreparedQuery(querys.inciXmesAnio, params);
                }
                case INCI_DETALLE -> {
                    return db.executePreparedQuery(querys.detalleIncidenciaFull, params);
                }

                // Caso por defecto para tipos de consulta que no retornan ResultSet (ej.
                // UPDATEs)
                default -> {
                    new Utilities().logAction("ERROR", "utilities.ejecutarConsulta",
                            "Tipo consulta no válido (esperaba ResultSet): " + tipo);
                    return null;
                }
            }

        } catch (SQLException ex) {
            new Utilities().logAction("ERROR", "utilities.ejecutarConsulta",
                    "Error ejecutando consulta: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Ejecuta una sentencia SQL de actualización (INSERT, UPDATE, DELETE).
     * 
     * @param tipo   El tipo de consulta {@link TipoConsulta} a ejecutar.
     * @param params Parámetros variables para la sentencia.
     * @return El número de filas afectadas o 0 si hay error.
     */
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
                case ASIGNAR_APARATO ->
                    db.executeUpdate(querys.updateAparatoContrato, params);
                case LIBERAR_APARATO ->
                    db.executeUpdate(querys.liberarAparato, params);
                case ADD_NUMERO ->
                    db.executeUpdate(querys.insertNumero, params);
                case DEL_NUMERO ->
                    db.executeUpdate(querys.deleteNumero, params);
                case FIX_SCHEMA -> {
                    String[] queries = querys.fixSchema.split(";");
                    int totalUpdated = 0;
                    for (String q : queries) {
                        if (!q.trim().isEmpty()) {
                            totalUpdated += db.executeUpdate(q.trim());
                        }
                    }
                    yield totalUpdated;
                }
                case CREATE_LOGS_TABLE ->
                    db.executeUpdate(querys.createTableLogs);
                case INSERT_LOG ->
                    db.executeUpdate(querys.insertLog, params);
                case INSERT_INCIDENCIA_FULL ->
                    db.executeUpdate(querys.insertIncidenciaFull, params);
                default ->
                    0;
            };

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Registra una acción en la base de datos.
     * 
     * @param estado      Estado de la acción ("OK" o "ERROR").
     * @param panel       Nombre del panel o clase donde ocurre la acción (ej.
     *                    "Login", "IncidPanel").
     * @param descripcion Descripción del evento o mensaje de error.
     */
    public void logAction(String estado, String panel, String descripcion) {
        // Asegurar que la tabla existe (podría optimizarse para no llamar siempre)
        ejecutarUpdate(TipoConsulta.CREATE_LOGS_TABLE);
        ejecutarUpdate(TipoConsulta.INSERT_LOG, estado, panel, descripcion);
    }

    /**
     * Utilidades criptográficas para el manejo de contraseñas.
     * Utiliza BCrypt para hashing seguro.
     */
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

    /**
     * Procesa la contraseña para actualización.
     * Si la contraseña introducida coincide con el hash original, se mantiene.
     * Si es diferente, se considera un cambio y se hashea.
     * 
     * @param inputPassword Contraseña introducida en el formulario.
     * @param originalHash  Hash almacenado en base de datos.
     * @return El hash a guardar (original o nuevo).
     */
    public String procesarPasswordUpdate(String inputPassword, String originalHash) {
        if (inputPassword.equals(originalHash)) {
            return originalHash;
        } else {
            return hashPass(inputPassword);
        }
    }

    /**
     * Carga los datos de un ResultSet en un JTable.
     * <p>
     * Crea dinámicamente las columnas basándose en los metadatos de la consulta.
     * Aplica renderizadores para colorear filas según el estado de la incidencia
     * (columna 6).
     * </p>
     * 
     * @param tabla La tabla destino.
     * @param rs    El conjunto de resultados de la BD.
     * @throws SQLException Si falla la lectura de datos.
     */
    public void cargarTabla(JTable tabla, ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnas = meta.getColumnCount();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Nombres de columnas
        for (int i = 1; i <= columnas; i++) {
            modelo.addColumn(meta.getColumnLabel(i));
        }

        // Datos
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        while (rs.next()) {
            Object[] fila = new Object[columnas];
            for (int i = 0; i < columnas; i++) {
                int colIndex = i + 1;
                int tipo = meta.getColumnType(colIndex);

                // Detectar tipos de fecha/hora de forma robusta
                if (tipo == java.sql.Types.DATE || tipo == java.sql.Types.TIMESTAMP
                        || tipo == java.sql.Types.TIMESTAMP_WITH_TIMEZONE) {
                    try {
                        java.sql.Timestamp ts = rs.getTimestamp(colIndex);
                        fila[i] = (ts != null) ? sdf.format(ts) : "";
                    } catch (Exception e) {
                        fila[i] = rs.getObject(colIndex);
                    }
                } else {
                    fila[i] = rs.getObject(colIndex);
                }
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
                        table, value, isSelected, hasFocus, row, column);

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
                                c.setBackground(new Color(255, 236, 236));
                                c.setForeground(new Color(153, 0, 0));
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

    /**
     * Genera y muestra un gráfico de barras con las incidencias por día del mes
     * seleccionado.
     * 
     * @param panelDestino El panel donde se renderizará el gráfico.
     * @param mes          Mes (1-12). Si es -1 usa el actual.
     * @param anio         Año (YYYY). Si es -1 usa el actual.
     */
    public void cargarGrafico(JPanel panelDestino, int mes, int anio) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Si no se pasan argumentos válidos, usar fecha actual
        if (mes == -1 || anio == -1) {
            java.time.LocalDate now = java.time.LocalDate.now();
            mes = now.getMonthValue();
            anio = now.getYear();
        }

        ResultSet rs = ejecutarConsulta(TipoConsulta.INCI_MES_ANIO, mes, anio);

        try {
            while (rs != null && rs.next()) {
                String dia = String.valueOf(rs.getInt("dia"));
                int total = rs.getInt("total");
                dataset.addValue(total, "Incidencias", dia);
            }
        } catch (SQLException ex) {
            logAction("ERROR", "utilities.cargarGrafico", "Error cargando gráfico: " + ex.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("chart.title.incidents") + mes + "/" + anio,
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("chart.axis.day", mes),
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("chart.axis.quantity"),
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false);

        applyTelecomChartTheme(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(null);
        chartPanel.setMinimumSize(new Dimension(0, 0));
        chartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Insertar en el panel destino
        panelDestino.removeAll();
        panelDestino.setLayout(new BorderLayout());
        panelDestino.add(chartPanel, BorderLayout.CENTER);
        panelDestino.revalidate();
        panelDestino.repaint();
    }

    /**
     * @deprecated Usar {@link #cargarGrafico(JPanel, int, int)}
     */
    public void cargarGrafico(JPanel panelDestino) {
        cargarGrafico(panelDestino, -1, -1);
    }

    /**
     * Aplica el tema visual corporativo (TelecomTheme) a un gráfico JFreeChart.
     * Delega en ThemeManager.
     */
    private void applyTelecomChartTheme(org.jfree.chart.JFreeChart chart) {
        com.jmmunoz.netfix.vista.tema.ThemeManager.getInstance().applyTelecomChartTheme(chart);
    }

    /**
     * Obtiene contadores de incidencias agrupadas por estado.
     * 
     * @return Array de enteros: [0]=pendientes, [1]=resueltas, [2]=sin_comunicar.
     * @throws SQLException Si falla la consulta.
     */
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

    /**
     * Busca información de un aparato FTTH asociado a un contrato.
     * 
     * @param contrato ID del contrato.
     * @return Array de Strings: [0]=serie, [1]=mac, [2]=modelo.
     * @throws SQLException Si falla la consulta.
     */
    public String[] obtenerApaFTTH(int contrato) throws SQLException {
        String[] aparatos = { " ", " ", " " }; // Inicializamos con valores por defecto
        ResultSet rs = ejecutarConsulta(TipoConsulta.APARATOSFTTH, contrato);

        if (rs != null && rs.next()) { // Tomamos la primera fila si existe
            aparatos[0] = rs.getString("numero_Serie");
            aparatos[1] = rs.getString("mac");
            aparatos[2] = rs.getString("modelo");
        }

        return aparatos;
    }

    /**
     * Obtiene la lista de aparatos 5G y sus números asociados para un contrato.
     * Rellena un JList con la información formateada.
     * 
     * @param contrato ID del contrato.
     * @param lista    Componente JList a rellenar.
     * @throws SQLException Si falla la consulta.
     */
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
                model.addElement(aparato); // Añadimos al JList
            }
        }
    }

    /**
     * Obtiene el nombre del titular de un contrato.
     * 
     * @param contrato ID del contrato.
     * @return Nombre del titular o cadena vacía si no se encuentra.
     * @throws SQLException Si falla la consulta.
     */
    public String obtenerTitular(int contrato) throws SQLException {
        String titular = new String();
        ResultSet rs = ejecutarConsulta(TipoConsulta.TITULAR, contrato);
        if (rs.next()) { // Tomamos la primera fila
            titular = rs.getString("titular");
        }
        return titular;
    }

    /**
     * Busca aparatos para diagnóstico por texto libre (MAC, Serie o ID).
     * 
     * @param searchText Texto de búsqueda.
     * @return Lista de arrays de objetos con los resultados.
     * @throws SQLException Si falla la consulta.
     */
    public static List<Object[]> buscarDiagnosticoAparato(String searchText) throws SQLException {

        int sAparato = 0;
        if (searchText.matches("\\d+")) {
            try {
                sAparato = Integer.parseInt(searchText);
            } catch (NumberFormatException e) {
                sAparato = 0;
            }
        }

        ResultSet rs = ejecutarConsulta(
                TipoConsulta.DIAGNOSTICO_APARATO,
                searchText,
                searchText,
                sAparato,
                searchText);

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

    // ==========================================
    // GESTIÓN DE TÉCNICOS Y CITAS
    // ==========================================

    /**
     * Inserta técnicos de prueba si no existen.
     */
    public void seedTestTechnicians() {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            // Verificar si existen
            ResultSet rs = db.executeQuery(
                    "SELECT count(*) FROM netfix.usuarios WHERE email IN ('tecnico1@netfix.com', 'tecnico2@netfix.com')");
            if (rs.next() && rs.getInt(1) == 0) {
                // Insertar
                String p1 = PasswordUtils.hashPassword("1234");
                String p2 = PasswordUtils.hashPassword("1234");

                db.executeUpdate(querys.altaUser, "Técnico Test 1", "tecnico", "tecnico1@netfix.com", p1);
                db.executeUpdate(querys.altaUser, "Técnico Test 2", "tecnico", "tecnico2@netfix.com", p2);
                System.out.println("Seeded test technicians.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Inicializa el esquema de base de datos para técnicos si no existe.
     * Crea tablas: tecnicos, horarios, agenda.
     * Inserta datos iniciales de sincronización.
     */
    public void initTechnicianSchema() {
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            db.executeUpdate(querys.createTableTecnicos);
            db.executeUpdate(querys.createTableHorarios);
            db.executeUpdate(querys.createTableAgenda);

            // Sync inicial
            db.executeUpdate(querys.syncTecnicos);
            db.executeUpdate(querys.initHorariosDefecto);

        } catch (SQLException ex) {
            System.err.println("Error initTechnicianSchema: " + ex.getMessage());
        }
    }

    /**
     * Obtiene la lista de técnicos disponibles (id, nombre).
     * 
     * @return Lista de arrays [id_tecnico, nombre]
     */
    public List<Object[]> getListaTecnicos() {
        List<Object[]> lista = new ArrayList<>();
        try {
            DatabaseManager db = DatabaseManager.getInstance();
            ResultSet rs = db.executeQuery(querys.getTecnicos);
            while (rs.next()) {
                lista.add(new Object[] { rs.getInt("id_tecnico"), rs.getString("nombre") });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Calcula los huecos libres para un técnico en una fecha dada.
     * Resta las citas de la agenda al horario laboral del técnico.
     * 
     * @param idTecnico ID del técnico.
     * @param fecha     Fecha a consultar (java.util.Date).
     * @return Lista de horas disponibles (String "HH:mm").
     */
    public List<String> getHuecosLibres(int idTecnico, java.util.Date fecha) {
        List<String> huecos = new ArrayList<>();
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());

        // Determinar fecha "ahora" para validaciones (no mostrar horas pasadas)
        java.util.Date now = new java.util.Date();
        java.util.Calendar calNow = java.util.Calendar.getInstance();
        calNow.setTime(now);
        calNow.add(java.util.Calendar.HOUR_OF_DAY, 1); // Margen de 1 hora
        java.util.Date oneHourLater = calNow.getTime();

        // 1. Determinar día de la semana (1=Domingo... 7=Sábado en Java Calendar es
        // igual)
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fecha);
        int diaSemana = cal.get(java.util.Calendar.DAY_OF_WEEK); // 1=Sun, 2=Mon...

        try {
            DatabaseManager db = DatabaseManager.getInstance();

            // 2. Obtener TODAS las franjas horarias (mañana y tarde)
            ResultSet rsHorario = db.executePreparedQuery(querys.getHorarioTecnico, idTecnico, diaSemana);

            // 3. Obtener citas ocupadas
            List<String> ocupadas = new ArrayList<>();
            ResultSet rsCitas = db.executePreparedQuery(querys.getCitasTecnico, idTecnico, sqlDate);
            while (rsCitas.next()) {
                java.sql.Timestamp ts = rsCitas.getTimestamp("fecha_cita");
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
                ocupadas.add(sdf.format(ts));
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");

            // Iterar sobre cada rango horario (ej: 08-13 y 16-19)
            while (rsHorario.next()) {
                java.sql.Time entrada = rsHorario.getTime("hora_entrada");
                java.sql.Time salida = rsHorario.getTime("hora_salida");

                long slotMillis = 30 * 60 * 1000;
                long current = entrada.getTime();
                long end = salida.getTime();

                while (current < end) {
                    java.util.Date dateCurrent = new java.util.Date(current);
                    String horaStr = sdf.format(dateCurrent);

                    // Validar si es una fecha futura
                    // Construir Timestamp completo para comparar tiempos reales
                    java.util.Calendar slotCal = (java.util.Calendar) cal.clone();
                    java.util.Calendar timePart = java.util.Calendar.getInstance();
                    timePart.setTime(dateCurrent);
                    slotCal.set(java.util.Calendar.HOUR_OF_DAY, timePart.get(java.util.Calendar.HOUR_OF_DAY));
                    slotCal.set(java.util.Calendar.MINUTE, timePart.get(java.util.Calendar.MINUTE));
                    java.util.Date fullSlotDate = slotCal.getTime();

                    // Si el día es HOY, no mostrar horas pasadas ni margen de 1h
                    if (isSameDay(fecha, now)) {
                        if (fullSlotDate.before(oneHourLater)) {
                            // Es pasado o muy pronto
                            current += slotMillis;
                            continue;
                        }
                    } else if (fullSlotDate.before(now)) {
                        // Es un día pasado (no debería poder seleccionarse, pero por seguridad)
                        current += slotMillis;
                        continue;
                    }

                    if (!ocupadas.contains(horaStr)) {
                        huecos.add(horaStr);
                    }
                    current += slotMillis;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Ordenar huecos cronológicamente
        java.util.Collections.sort(huecos);
        return huecos;
    }

    private boolean isSameDay(java.util.Date d1, java.util.Date d2) {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        c1.setTime(d1);
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        c2.setTime(d2);
        return c1.get(java.util.Calendar.YEAR) == c2.get(java.util.Calendar.YEAR) &&
                c1.get(java.util.Calendar.DAY_OF_YEAR) == c2.get(java.util.Calendar.DAY_OF_YEAR);
    }

    /**
     * Agenda una cita y actualiza el estado de la incidencia.
     * 
     * @param idTecnico    Técnico asignado.
     * @param idIncidencia Incidencia a resolver.
     * @param fechaDia     Fecha del día.
     * @param horaStr      Hora seleccionada ("HH:mm").
     * @return true si éxito.
     */
    public boolean agendarCita(int idTecnico, int idIncidencia, java.util.Date fechaDia, String horaStr) {
        try {
            // Combinar fecha y hora
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fechaStr = sdf.format(fechaDia);
            String fullDateStr = fechaStr + " " + horaStr + ":00";
            java.sql.Timestamp ts = java.sql.Timestamp.valueOf(fullDateStr);

            DatabaseManager db = DatabaseManager.getInstance();

            // Transacción
            db.beginTransaction();

            // 1. Insertar Cita
            db.executePreparedUpdate(querys.insertCita, idTecnico, idIncidencia, ts);

            // 2. Actualizar Incidencia (Asignada y con solución temporal indicando la cita)
            String msg = com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("util.msg.scheduled") + fullDateStr;
            db.executePreparedUpdate(querys.asignarIncidencia, idTecnico, msg, idIncidencia);

            db.commit();
            return true;

        } catch (Exception ex) {
            DatabaseManager.getInstance().rollback();
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Valida si un email pertenece al dominio corporativo (@netfix.com
     * o @netfix.es).
     * 
     * @param email Email a validar.
     * @return true si es válido, false en caso contrario.
     */
    public boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@netfix\\.(com|es)$";
        return email.matches(regex);
    }

}
