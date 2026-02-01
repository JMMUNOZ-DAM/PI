/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.modelo;

/**
 *
 * @author Juanma Muñoz
 */
/**
 * Almacena todas las consultas SQL y sentencias DML utilizadas en la
 * aplicación.
 * <p>
 * Centraliza el acceso a las cadenas de texto SQL para facilitar el
 * mantenimiento.
 * Incluye consultas de selección, inserción, actualización y borrado.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class Querys {

    /**
     * Consulta para obtener todas las incidencias activas (abiertas, en proceso,
     * sin
     * comunicar).
     * <p>
     * Columnas: Incidencia, Contrato, Descripción, Fecha, Agente, Estado.
     * </p>
     */
    public static String incidencias = """
            select t1.id_incidencia as Incidencia, t1.id_contrato as Contrato, t1.descripcion as Descripción, t1.fecha_reporte as Fecha, t2.nombre as Agente, t1.estado as Estado
            from netfix.incidencias t1
            inner join netfix.usuarios t2 on t1.id_usuario = t2.id_usuario
            where estado in ('abierta', 'en_proceso', 'sin_comunicar')
            order by t1.estado desc""";

    /**
     * Cuenta el número total de incidencias agrupadas por día.
     */
    public static String inciXdia = "SELECT DATE(fecha_reporte) AS dia, COUNT(*) AS total "
            + "FROM incidencias GROUP BY DATE(fecha_reporte)";

    /**
     * Cuenta las incidencias por día para un mes y año específicos.
     * Parámetros: Mes (int), Año (int).
     */
    public static String inciXmesAnio = """
            SELECT DAY(fecha_reporte) AS dia, COUNT(*) AS total
            FROM netfix.incidencias
            WHERE MONTH(fecha_reporte) = ? AND YEAR(fecha_reporte) = ?
            GROUP BY DAY(fecha_reporte)
            ORDER BY dia ASC""";

    /**
     * Obtiene los comentarios asociados a una incidencia.
     * Parámetros: ID Incidencia.
     */
    public static String comentarios = """
            SELECT agente as Agente, comentario as Comentario, fecha as Fecha
            FROM NETFIX.COMENTARIOS
            WHERE id_incidencia = ?;
            """;

    /**
     * Obtiene un resumen del estado de las incidencias (pendientes, resueltas, sin
     * comunicar).
     */
    public static String contadorInci = """
            SELECT
                COUNT(CASE WHEN estado IN ('abierta', 'en_proceso') THEN 1 END) AS pendientes,
                COUNT(CASE WHEN estado = 'solucionada' THEN 1 END) AS resueltas,
                COUNT(CASE WHEN estado = 'sin_comunicar' THEN 1 END) AS sin_comunicar
            FROM netfix.incidencias;""";

    /**
     * Obtiene todas las incidencias asociadas a un contrato específico.
     * Parámetros: ID Contrato.
     */
    public static String inciContra = """
            select *
            from netfix.incidencias as t1
            inner join netfix.contratos as t2 on t1.id_contrato = t2.id_contrato
            where t1.id_contrato = ?""";

    /**
     * Cuenta las incidencias resueltas agrupadas por técnico.
     */
    public static String incidenciasResueltasPorTecnico = """
            SELECT t1.id_usuario ID, t2.nombre NOMBRE, COUNT(*) total
            FROM netfix.incidencias t1
            INNER JOIN netfix.usuarios t2 ON t1.id_usuario = t2.id_usuario
            WHERE t1.estado = 'resuelta'
            GROUP BY t1.id_usuario, t2.nombre""";

    /**
     * Obtiene la lista completa de usuarios del sistema.
     */
    public static String usuarios = """
            select id_usuario as ID, nombre as Nombre, rol as Rol, email as Email, password as Contraseña
            from netfix.usuarios
            """;

    /**
     * Obtiene usuarios excluyendo a los administradores de sistemas.
     */
    public static String usuariosStrict = """
            select id_usuario as ID, nombre as Nombre, rol as Rol, email as Email, password as Contraseña
            from netfix.usuarios
            where rol != 'sistemas'
            """;

    /**
     * Obtiene la lista de roles disponibles.
     */
    public static String roles = """
            select descripcion
            from netfix.roles
            """;

    /**
     * Actualiza los datos de un usuario existente. Parámetros: nombre, rol, email,
     * password, id_usuario.
     */
    public static String updateUser = """
                UPDATE netfix.usuarios
                SET nombre = ?,
                    rol = ?,
                    email = ?,
                    password = ?
                WHERE id_usuario = ?;
            """;

    /**
     * Obtiene los datos de un usuario por su email.
     * Parámetros: Email.
     */
    public static String usuario = """
            select *
            from netfix.usuarios
            where email = ?
            """;

    /**
     * Inserta un nuevo usuario en el sistema.
     * Parámetros: nombre, rol, email, password.
     */
    public static String altaUser = """
            INSERT INTO netfix.usuarios (nombre, rol, email, password)
            VALUES (?, ?, ?, ?);
            """;

    /** Elimina un usuario por su ID. */
    public static String deleteUser = "DELETE FROM netfix.usuarios WHERE id_usuario = ?";

    /**
     * Obtiene el diagnóstico completo de un aparato.
     * Combina datos de la tabla de aparatos y la de diagnósticos mediante LEFT
     * JOIN.
     */
    public static String diagnosticoAparato = """
                SELECT
                t1.id_aparato,
                t1.tipo_aparato,
                t1.modelo,
                t1.numero_serie,
                t1.mac,
                t1.id_contrato,
                COALESCE(t2.fecha_actualizacion, ' ') AS fecha_actualizacion,
                COALESCE(t2.estado_general, 'Desconocido') AS estado_general,
                COALESCE(t2.velocidad_internet, 'aun no comprobada') AS velocidad_internet,
                COALESCE(t2.niveles_opticos, 'aun no comprobados') AS niveles_opticos,
                COALESCE(t2.cobertura, 'aun no comprobada') AS cobertura,
                COALESCE(t2.ping, 'aun no comprobado') AS ping,
                COALESCE(t2.observaciones, 'No existen observaciones') AS observaciones,
                COALESCE(t2.id_diagnostico, 0) AS id_diagnostico,
                GROUP_CONCAT(n.numero SEPARATOR ', ') as telefonos

            FROM netfix.aparatos t1
            LEFT JOIN netfix.diagnostico t2
                ON t1.id_aparato = t2.id_aparato
            LEFT JOIN netfix.numeros n
                ON t1.id_aparato = n.id_aparato
            WHERE (t1.mac = ? OR t1.numero_serie = ? OR t1.id_aparato = ? OR n.numero = ?)
            GROUP BY t1.id_aparato""";

    /**
     * Cuenta el total de aparatos agrupados por tipo.
     */
    public static String contadorAparatos = """
            SELECT tipo_aparato, COUNT(*) total
            FROM netfix.aparatos
            GROUP BY tipo_aparato""";

    /**
     * Obtiene la contraseña de un usuario para validación de login.
     * Parámetros: Email.
     */
    public static String login = """
            SELECT password
            FROM netfix.usuarios
            WHERE email = ?
            """;

    /** Inserta o actualiza (Upsert) el diagnóstico de un aparato. */
    public static String diagnostico = """
            INSERT INTO netfix.diagnostico (
                id_aparato,
                fecha_actualizacion,
                estado_general,
                velocidad_internet,
                niveles_opticos,
                cobertura,
                ping,
                observaciones
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                fecha_actualizacion = VALUES(fecha_actualizacion),
                estado_general = VALUES(estado_general),
                velocidad_internet = VALUES(velocidad_internet),
                niveles_opticos = VALUES(niveles_opticos),
                cobertura = VALUES(cobertura),
                ping = VALUES(ping),
                observaciones = VALUES(observaciones)
            """;

    /**
     * Obtiene aparatos FTTH asociados a un contrato.
     * Parámetros: ID Contrato.
     */
    public static String aparatosFTTH = """
            select numero_Serie, mac, modelo
            from netfix.aparatos a
            inner join netfix.contratos c on a.id_contrato  = c.id_contrato
            where c.id_contrato = ? and a.tipo_aparato = 'FTTH'
             """;

    /**
     * Obtiene aparatos 5G (y sus números asociados) para un contrato.
     * Parámetros: ID Contrato.
     */
    public static String aparatos5G = """
            SELECT
                CONCAT('SIM: ', a.numero_serie, '   |   Nº: ', GROUP_CONCAT(n.numero ORDER BY n.numero SEPARATOR ', ')) AS datos_5g
            FROM netfix.aparatos a
            INNER JOIN netfix.contratos c ON a.id_contrato = c.id_contrato
            INNER JOIN netfix.numeros n ON a.id_aparato = n.id_aparato
            WHERE c.id_contrato = ?
              AND a.tipo_aparato = '5G'
            GROUP BY a.id_aparato, a.numero_serie;
             """;

    /**
     * Obtiene el nombre del titular de un contrato.
     * Parámetros: ID Contrato.
     */
    public static String titular = """
            select b.nombre as titular
            from netfix.contratos c
            inner join clientes b on c.dni_cliente = b.dni
            where c.id_contrato = ?;
            """;

    /**
     * Asigna una incidencia a un usuario (comunicar incidencia).
     * Parámetros: ID Usuario, ID Incidencia.
     */
    public static String comunicar = """
            update netfix.incidencias set estado = 'abierta', id_usuario = ?
            where id_incidencia = ?
            """;

    /**
     * Inserta un nuevo comentario en una incidencia.
     * Parámetros: ID Incidencia, Agente, Comentario.
     */
    public static String insertComentario = """
            insert into netfix.comentarios
            (id_incidencia, agente, comentario, fecha)
            values (?, ?, ?, now())
            """;

    /**
     * Marca una incidencia como solucionada.
     * Parámetros: ID Usuario, Solución, ID Incidencia.
     */
    public static String solucionar = """
            update netfix.incidencias set estado = 'solucionada', id_usuario = ?, solucion = ?
            where id_incidencia = ?;
            """;

    /**
     * Deriva una incidencia a otro usuario/departamento.
     * Parámetros: ID Usuario, Solución (motivo), ID Incidencia.
     */
    public static String derivar = """
            update netfix.incidencias set estado = 'derivada', id_usuario = ?, solucion = ?
            where id_incidencia = ?;
            """;

    /**
     * Obtiene los detalles completos de una incidencia, incluyendo datos del
     * cliente y técnico.
     * Parámetros: ID Incidencia.
     */
    public static String detalleIncidenciaFull = """
            SELECT
                i.id_incidencia, i.id_contrato, i.descripcion, i.fecha_reporte, i.estado, i.solucion,
                c.dni_cliente, cl.nombre AS nombre_cliente, c.tipo_servicio,
                u.nombre AS tecnico
            FROM netfix.incidencias i
            LEFT JOIN netfix.contratos c ON i.id_contrato = c.id_contrato
            LEFT JOIN netfix.clientes cl ON c.dni_cliente = cl.dni
            LEFT JOIN netfix.usuarios u ON i.id_usuario = u.id_usuario
            WHERE i.id_incidencia = ?
            """;

    /**
     * Crea una nueva incidencia con todos sus campos.
     * Parámetros: ID Contrato, Descripción, Fecha, Estado, ID Usuario, Solución.
     */
    public static String insertIncidenciaFull = """
            INSERT INTO netfix.incidencias (id_contrato, descripcion, fecha_reporte, estado, id_usuario, solucion)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    /**
     * Obtiene todos los contratos junto con sus aparatos asociados (si los tienen).
     */
    public static String allContratosAparatos = """
            SELECT c.id_contrato as Contrato, c.dni_cliente as DNI, a.id_aparato as "ID Aparato", a.numero_serie as "Nº Serie", a.modelo as Modelo, a.tipo_aparato as Tipo
            FROM netfix.contratos c
            LEFT JOIN netfix.aparatos a ON c.id_contrato = a.id_contrato;
            """;

    /**
     * Obtiene los aparatos que no están asignados a ningún contrato (libres).
     */
    public static String freeAparatos = """
            SELECT id_aparato as "ID Aparato", numero_serie as "Nº Serie", modelo as Modelo, tipo_aparato as Tipo
            FROM netfix.aparatos
            WHERE id_contrato IS NULL;
            """;

    /** Asigna un contrato a un aparato. Parámetros: ID Contrato, ID Aparato. */
    public static String updateAparatoContrato = "UPDATE netfix.aparatos SET id_contrato = ? WHERE id_aparato = ?";

    /** Libera un aparato de su contrato. Parámetros: ID Aparato. */
    public static String liberarAparato = "UPDATE netfix.aparatos SET id_contrato = NULL WHERE id_aparato = ?";

    /** Sentencia DDL para corregir el esquema (si es necesario). */
    public static String fixSchema = "ALTER TABLE netfix.aparatos MODIFY id_contrato INT NULL; ALTER TABLE netfix.comentarios MODIFY fecha DATETIME DEFAULT CURRENT_TIMESTAMP;";

    /** Obtiene los números telefónicos asociados a un aparato. */
    public static String numerosPorAparato = "SELECT numero FROM netfix.numeros WHERE id_aparato = ?";

    public static String insertNumero = "INSERT INTO netfix.numeros (id_aparato, numero, id_contrato) VALUES (?, ?, ?)";
    public static String deleteNumero = "DELETE FROM netfix.numeros WHERE numero = ?";

    // ==========================================
    // GESTIÓN DE TÉCNICOS Y CITAS
    // ==========================================

    /* 1. Tabla de TÉCNICOS (Vincula usuarios con el rol técnico) */
    public static String createTableTecnicos = """
            CREATE TABLE IF NOT EXISTS netfix.tecnicos (
                id_tecnico INT AUTO_INCREMENT PRIMARY KEY,
                id_usuario INT NOT NULL,
                especialidad VARCHAR(100),
                FOREIGN KEY (id_usuario) REFERENCES netfix.usuarios(id_usuario) ON DELETE CASCADE
            );""";

    /*
     * 2. Tabla de HORARIOS (Define disponibilidad por día de semana:
     * 1=Domingo...7=Sábado o L-V)
     */
    public static String createTableHorarios = """
            CREATE TABLE IF NOT EXISTS netfix.horarios (
                id_horario INT AUTO_INCREMENT PRIMARY KEY,
                id_tecnico INT NOT NULL,
                dia_semana INT NOT NULL, -- 1=Domingo, 2=Lunes, ... 7=Sábado
                hora_entrada TIME NOT NULL,
                hora_salida TIME NOT NULL,
                FOREIGN KEY (id_tecnico) REFERENCES netfix.tecnicos(id_tecnico) ON DELETE CASCADE
            );""";

    /* 3. Tabla de AGENDA (Citas concretas) */
    public static String createTableAgenda = """
            CREATE TABLE IF NOT EXISTS netfix.agenda (
                id_cita INT AUTO_INCREMENT PRIMARY KEY,
                id_tecnico INT NOT NULL,
                id_incidencia INT NOT NULL,
                fecha_cita DATETIME NOT NULL,
                FOREIGN KEY (id_tecnico) REFERENCES netfix.tecnicos(id_tecnico),
                FOREIGN KEY (id_incidencia) REFERENCES netfix.incidencias(id_incidencia)
            );""";

    // Consulta para llenar la tabla técnicos automáticamente con usuarios de rol
    // 'tecnico' que no estén ya
    public static String syncTecnicos = """
            INSERT INTO netfix.tecnicos (id_usuario, especialidad)
            SELECT id_usuario, 'General' FROM netfix.usuarios
            WHERE rol = 'tecnico' AND id_usuario NOT IN (SELECT id_usuario FROM netfix.tecnicos);
            """;

    // Consulta para inicializar horarios por defecto (Lunes a Viernes, 08:00 -
    // 13:00 Y 16:00 - 19:00)
    public static String initHorariosDefecto = """
            INSERT INTO netfix.horarios (id_tecnico, dia_semana, hora_entrada, hora_salida)
            SELECT id_tecnico, dia, '08:00:00', '13:00:00'
            FROM netfix.tecnicos t
            JOIN (SELECT 2 as dia UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) d
            WHERE NOT EXISTS (SELECT 1 FROM netfix.horarios h WHERE h.id_tecnico = t.id_tecnico)
            UNION ALL
            SELECT id_tecnico, dia, '16:00:00', '19:00:00'
            FROM netfix.tecnicos t
            JOIN (SELECT 2 as dia UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) d
            WHERE NOT EXISTS (SELECT 1 FROM netfix.horarios h WHERE h.id_tecnico = t.id_tecnico);
            """;

    // Obtener lista de técnicos (Nombre y ID)
    public static String getTecnicos = """
            SELECT t.id_tecnico, u.nombre
            FROM netfix.tecnicos t
            JOIN netfix.usuarios u ON t.id_usuario = u.id_usuario
            WHERE t.especialidad = 'Calle';
            """;

    // Obtener horario de un técnico para un día de la semana
    public static String getHorarioTecnico = """
            SELECT hora_entrada, hora_salida
            FROM netfix.horarios
            WHERE id_tecnico = ? AND dia_semana = ?;
            """;

    // Obtener citas ocupadas de un técnico en una fecha específica
    public static String getCitasTecnico = """
            SELECT fecha_cita
            FROM netfix.agenda
            WHERE id_tecnico = ? AND DATE(fecha_cita) = DATE(?);
            """;

    // Insertar nueva cita
    public static String insertCita = """
            INSERT INTO netfix.agenda (id_tecnico, id_incidencia, fecha_cita)
            VALUES (?, ?, ?);
            """;

    // Actualizar estado de incidencia a 'derivada'
    public static String asignarIncidencia = """
            UPDATE netfix.incidencias
            SET estado = 'derivada', id_usuario = (SELECT id_usuario FROM netfix.tecnicos WHERE id_tecnico = ?), solucion = ?
            WHERE id_incidencia = ?;
            """;

    // ==========================================
    // SISTEMA DE REGISTRO (LOGGING)
    // ==========================================

    public static String createTableLogs = """
            CREATE TABLE IF NOT EXISTS netfix.logs (
                id_log INT AUTO_INCREMENT PRIMARY KEY,
                estado VARCHAR(10) NOT NULL,
                panel VARCHAR(50) NOT NULL,
                descripcion TEXT,
                fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP
            );""";

    public static String insertLog = "INSERT INTO netfix.logs (estado, panel, descripcion, fecha_hora) VALUES (?, ?, ?, NOW())";

    public static String getLogs = "SELECT estado, panel, descripcion, fecha_hora FROM netfix.logs WHERE (? IS NULL OR estado = ?) ORDER BY fecha_hora DESC";

    // Consultas auxiliares para Cascade Delete (Baja Usuario)
    public static String getTecnicoId = "SELECT id_tecnico FROM netfix.tecnicos WHERE id_usuario = ?";
    public static String deleteAgenda = "DELETE FROM netfix.agenda WHERE id_tecnico = ?";

    // Gestión de Especialidad de Técnicos
    public static String insertTecnico = "INSERT INTO netfix.tecnicos (id_usuario, especialidad) VALUES (?, ?)";
    public static String updateTecnicoSpec = "UPDATE netfix.tecnicos SET especialidad = ? WHERE id_usuario = ?";
    public static String getTecnicoSpec = "SELECT especialidad FROM netfix.tecnicos WHERE id_usuario = ?";
    public static String deleteTecnico = "DELETE FROM netfix.tecnicos WHERE id_usuario = ?";
    public static String checkTecnicoExists = "SELECT id_tecnico FROM netfix.tecnicos WHERE id_usuario = ?";
}
