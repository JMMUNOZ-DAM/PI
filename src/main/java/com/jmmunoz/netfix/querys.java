/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author juanm
 */
public class querys {

    static String incidencias = """
                        select t1.id_incidencia, t1.id_contrato , t1.descripcion , t1.fecha_reporte, t2.nombre as agente, t1.estado 
                        from netfix.incidencias t1
                        inner join netfix.usuarios t2 on t1.id_usuario = t2.id_usuario  
                        where estado in ('abierta', 'en_proceso', 'sin_comunicar')""";

    static String inciXdia = "SELECT DATE(fecha_reporte) AS dia, COUNT(*) AS total "
            + "FROM incidencias GROUP BY DATE(fecha_reporte)";

    static String comentarios = """
                                SELECT agente, comentario, fecha
                                FROM NETFIX.COMENTARIOS
                                WHERE id_incidencia = ?;
                                """;

    static String contadorInci = """
                                 SELECT
                                     COUNT(CASE WHEN estado IN ('abierta', 'en_proceso') THEN 1 END) AS pendientes,
                                     COUNT(CASE WHEN estado = 'solucionada' THEN 1 END) AS resueltas,
                                     COUNT(CASE WHEN estado = 'sin_comunicar' THEN 1 END) AS sin_comunicar
                                 FROM netfix.incidencias;""";

    static String inciContra = """
                                   select *
                                   from netfix.incidencias as t1
                                   inner join netfix.contratos as t2 on t1.id_contrato = t2.id_contrato
                                   where t1.id_contrato = ?""";

    static String incidenciasResueltasPorTecnico
            = """
              SELECT t1.id_usuario ID, t2.nombre NOMBRE, COUNT(*) total 
              FROM netfix.incidencias t1 
              INNER JOIN netfix.usuarios t2 ON t1.id_usuario = t2.id_usuario 
              WHERE t1.estado = 'resuelta' 
              GROUP BY t1.id_usuario, t2.nombre""";

    static String usuarios = """
                             select *
                             from netfix.usuarios
                             """;

    static String roles = """
                          select descripcion
                          from netfix.roles
                          """;

    static String updateUser = """
    UPDATE netfix.usuarios
    SET nombre = ?,
        rol = ?,
        email = ?,
        password = ?
    WHERE id_usuario = ?;
""";

    static String usuario = """
                            select *
                            from netfix.usuarios
                            where email = ?            
                            """;

    static String altaUser = """
    INSERT INTO netfix.usuarios (nombre, rol, email, password)
    VALUES (?, ?, ?, ?);
""";

    static String diagnosticoAparato
            = """
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
                  COALESCE(t2.id_diagnostico, 0) AS id_diagnostico
              
              FROM netfix.aparatos t1
              LEFT JOIN netfix.diagnostico t2 
                  ON t1.id_aparato = t2.id_aparato
              WHERE (t1.mac = ? OR t1.numero_serie = ? OR t1.id_aparato = ?);""";

    static String contadorAparatos
            = """
              SELECT tipo_aparato, COUNT(*) total 
              FROM netfix.aparatos 
              GROUP BY tipo_aparato""";

    static String login = """
    SELECT password
    FROM netfix.usuarios
    WHERE email = ?
    """;

    static String diagnostico = """
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

    static String aparatosFTTH = """
                               select numero_Serie, mac, modelo
                               from netfix.aparatos a 
                               inner join netfix.contratos c on a.id_contrato  = c.id_contrato 
                               where c.id_contrato = ? and a.tipo_aparato = 'FTTH'
                                """;

    static String aparatos5G = """
                               SELECT 
                                   CONCAT(a.numero_serie, ': ', GROUP_CONCAT(n.numero ORDER BY n.numero SEPARATOR ', ')) AS datos_5g
                               FROM netfix.aparatos a
                               INNER JOIN netfix.contratos c ON a.id_contrato = c.id_contrato
                               INNER JOIN netfix.numeros n ON a.id_aparato = n.id_aparato
                               WHERE c.id_contrato = ?
                                 AND a.tipo_aparato = '5G'
                               GROUP BY a.id_aparato, a.numero_serie;
                                """;

    static String titular = """
                            select b.nombre as titular
                            from netfix.contratos c
                            inner join clientes b on c.dni_cliente = b.dni
                            where c.id_contrato = ?;
                            """;

    static String comunicar = """
                              update netfix.incidencias set estado = 'abierta', id_usuario = ? 
                              where id_incidencia = ?
                              """;

    static String insertComentario = """
                                     insert into netfix.comentarios
                                     (id_incidencia, agente, comentario, fecha)
                                     values (?, ?, ?, now())
                                     """;

    static String solucionar = """
                               update netfix.incidencias set estado = 'solucionada', id_usuario = ?, solucion = ?
                               where id_incidencia = ?;
                               """;

    static String derivar = """
                               update netfix.incidencias set estado = 'derivada', id_usuario = ?, solucion = ?
                               where id_incidencia = ?;
                               """;

}
