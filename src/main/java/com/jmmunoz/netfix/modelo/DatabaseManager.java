/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.modelo;

/**
 *
 * @author Juanma Muñoz
 */
import java.sql.*;

/**
 * Clase Singleton para gestionar la conexión a la base de datos MySQL.
 * <p>
 * Esta clase centraliza la configuración de conexión (URL, usuario, contraseña)
 * y proporciona métodos para ejecutar consultas SQL, transacciones y gestionar
 * el ciclo
 * de vida de la conexión.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class DatabaseManager {

    // ===============================================
    // CONFIGURACIÓN DE LA CONEXIÓN
    // ===============================================
    private static final String URL = "jdbc:mysql://localhost:3306/netfix";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;
    private static DatabaseManager instance;

    // ===============================================
    // SINGLETON
    // ===============================================
    private DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando el driver JDBC", e);
        }
    }

    /**
     * Obtiene la instancia única de DatabaseManager.
     * <p>
     * Implementación segura para hilos (synchronized) del patrón Singleton.
     * </p>
     * 
     * @return La instancia única de DatabaseManager.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // ===============================================
    // GESTIÓN DE CONEXIÓN
    // ===============================================
    /**
     * Obtiene la conexión actual a la base de datos.
     * <p>
     * Si no existe conexión o está cerrada, intenta establecer una nueva.
     * </p>
     * 
     * @return Un objeto {@link Connection} listo para usar.
     * @throws SQLException Si ocurre un error al intentar conectar con la base de
     *                      datos.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /**
     * Cierra la conexión activa si está abierta.
     * <p>
     * Este método es seguro ante excepciones; si falla el cierre, imprime el error
     * en la salida de error estándar.
     * </p>
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando la conexión: " + e.getMessage());
        }
    }

    // ===============================================
    // TRANSACCIONES
    // ===============================================
    /**
     * Inicia una transacción desactivando el auto-commit.
     * 
     * @throws SQLException Si hay un error de base de datos.
     */
    public void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    /**
     * Confirma los cambios de la transacción actual y restaura el auto-commit.
     * 
     * @throws SQLException Si hay un error al hacer commit.
     */
    public void commit() throws SQLException {
        getConnection().commit();
        getConnection().setAutoCommit(true);
    }

    /**
     * Deshace los cambios de la transacción actual en caso de error.
     * <p>
     * Restaura el auto-commit tras el rollback. Captura errores de SQL
     * internamente.
     * </p>
     */
    public void rollback() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error en rollback: " + e.getMessage());
        }
    }

    // ===============================================
    // CONSULTAS COMUNES
    // ===============================================
    /**
     * Ejecuta una sentencia SQL de actualización (INSERT, UPDATE, DELETE).
     * 
     * @param sql    La consulta SQL preparada (con ? para parámetros).
     * @param params Lista variable de objetos para sustituir los ? en la consulta.
     * @return El número de filas afectadas.
     * @throws SQLException Si ocurre un error SQL.
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = prepareStatement(sql, params)) {
            return stmt.executeUpdate();
        }
    }

    /**
     * Ejecuta una consulta SQL de selección (SELECT).
     * <p>
     * <b>Nota:</b> El {@link ResultSet} retornado mantiene el
     * {@link PreparedStatement} abierto.
     * Es responsabilidad del llamador cerrar el ResultSet.
     * </p>
     * 
     * @param sql    La consulta SQL select.
     * @param params Parámetros para la consulta preparada.
     * @return Un ResultSet con los resultados.
     * @throws SQLException Si ocurre un error SQL.
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeQuery(); // El ResultSet debe cerrarse externamente
    }

    // ===============================================
    // PREPARACIÓN DE SENTENCIAS
    // ===============================================
    private PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt;
    }

    /**
     * Método auxiliar para ejecutar consultas preparadas (alias de executeQuery).
     * 
     * @param sql    Consulta SQL.
     * @param params Parámetros.
     * @return ResultSet con los resultados.
     * @throws SQLException Si falla la ejecución.
     */
    public ResultSet executePreparedQuery(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeQuery();
    }

    boolean executePreparedBoolean(String sql, Object... params) throws SQLException {

        try (PreparedStatement stmt = prepareStatement(sql, params); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        }
    }

    public String executePreparedString(String sql, Object... params) throws SQLException {

        try (PreparedStatement stmt = prepareStatement(sql, params); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        }
    }

    public int executePreparedUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = prepareStatement(sql, params)) {
            return stmt.executeUpdate();
        }
    }

    // ===============================================
    // MÉTODO DE PRUEBA
    // ===============================================
    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();

        try {
            // ============================================
            // Obtener información de columnas dinámicamente
            // ============================================
            try (ResultSet rs = db.executeQuery(
                    "SELECT * FROM netfix.clientes LIMIT 1;")) {
                printResultSet(rs);
            }
            try (ResultSet rs = db.executeQuery(
                    "SELECT * FROM netfix.contratos LIMIT 1;")) {
                printResultSet(rs);
            }
            try (ResultSet rs = db.executeQuery(
                    "SELECT * FROM netfix.usuarios LIMIT 1;")) {
                printResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }

    private static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnas = meta.getColumnCount();
        System.out.println("===== TABLE: " + meta.getTableName(1) + " =====");
        for (int i = 1; i <= columnas; i++) {
            System.out.print(meta.getColumnName(i) + " (" + meta.getColumnTypeName(i) + ")\t");
        }
        System.out.println("\n----------------------------------------");
    }
}
