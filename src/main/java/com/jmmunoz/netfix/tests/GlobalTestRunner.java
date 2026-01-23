/*
 * GlobalTestRunner.java
 * Clase independiente para ejecución de batería de pruebas (Unidad e Integración).
 */
package com.jmmunoz.netfix.tests;

import com.jmmunoz.netfix.controlador.Utilities;
import com.jmmunoz.netfix.modelo.Usuario;
import com.jmmunoz.netfix.modelo.SimuladorDiagnostico;
import com.jmmunoz.netfix.modelo.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GlobalTestRunner {

    // Contadores
    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   INICIANDO BATERÍA DE PRUEBAS NETFIX    ");
        System.out.println("==========================================");

        // --- PRUEBAS DE UNIDAD ---
        System.out.println("\n--- [1] PRUEBAS DE UNIDAD (LOGICA) ---");

        // U01 - U03: Email
        Utilities ut = new Utilities();
        test("U01", "Email Valid (.com)",
                "Verificando que un email con dominio .com sea aceptado.",
                "\"test@netfix.com\"",
                ut.checkEmail("test@netfix.com"), true);

        test("U02", "Email Valid (.es)",
                "Verificando que un email con dominio .es sea aceptado.",
                "\"test@netfix.es\"",
                ut.checkEmail("test@netfix.es"), true);

        test("U03", "Email Invalid (gmail)",
                "Verificando que un email con dominio externo (gmail) sea rechazado.",
                "\"test@gmail.com\"",
                ut.checkEmail("test@gmail.com"), false);

        // U04: Hash Generation
        String hash = ut.hashPass("secret");
        test("U04", "Hashing no vacío",
                "Verificando que hashPass genere una cadena no vacía y diferente al input.",
                "\"secret\"",
                hash != null && !hash.isEmpty() && !hash.equals("secret"), true);

        // U05: BCrypt Check
        boolean isMatch = Utilities.PasswordUtils.checkPassword("secret", hash);
        test("U05", "BCrypt Verification",
                "Verificando que BCrypt valide correctamente el password original contra su hash.",
                "\"secret\", \"" + hash + "\"",
                isMatch, true);

        // U06 - U07: Password Update Logic
        String newHash = ut.procesarPasswordUpdate("newPass", "oldHash");
        test("U06", "Pass Update (Cambio)",
                "Verificando que al cambiar password se genere un nuevo hash diferente al anterior.",
                "\"newPass\", \"oldHash\"",
                !newHash.equals("oldHash") && !newHash.equals("newPass"), true);

        String keptHash = ut.procesarPasswordUpdate("", "oldHash");
        test("U07", "Pass Update (Vacío)",
                "Verificando que al dejar password vacío se mantenga el hash anterior.",
                "\"\", \"oldHash\"",
                keptHash.equals("oldHash"), true);

        // U08: Modelo Usuario
        Usuario user = new Usuario("1", "Pepe", "Admin", "pepe@netfix.com", "passhash");
        boolean userOk = user.getIdUsuario().equals("1") && user.getNombre().equals("Pepe");
        test("U08", "Modelo Usuario Props",
                "Verificando constructor y getters de la clase Usuario.",
                "new Usuario(\"1\", \"Pepe\", \"Admin\", \"pepe@netfix.com\", \"passhash\")",
                userOk, true);

        // U09 - U10: Simulador
        SimuladorDiagnostico sim = new SimuladorDiagnostico();
        test("U09", "Simulador Instanciación",
                "Verificando que la clase SimuladorDiagnostico se instancie correctamente.",
                "new SimuladorDiagnostico()",
                sim != null, true);

        // Test extra de lógica simple
        test("U10", "Verificación Booleana Simple",
                "Prueba de control para verificar el runner.",
                "true",
                true, true);

        // --- PRUEBAS DE INTEGRACIÓN ---
        System.out.println("\n--- [2] PRUEBAS DE INTEGRACIÓN (DB) ---");

        // I01: Conexión DB
        boolean dbConnected = false;
        try {
            ResultSet rs = Utilities.ejecutarConsulta(Utilities.TipoConsulta.ROLES, 0);
            if (rs != null)
                dbConnected = true;
        } catch (Exception e) {
            System.out.println("EXCEPTION en I01: " + e.getMessage());
        }
        test("I01", "Conexión DB (Consulta Roles)",
                "Intentando conectar a DB y ejecutar consulta SELECT de roles.",
                "TipoConsulta.ROLES, 0",
                dbConnected, true);

        // Resumen
        System.out.println("\n==========================================");
        System.out.println(" RESUMEN DE EJECUCIÓN");
        System.out.println("==========================================");
        System.out.println(" TOTAL : " + testsRun);
        System.out.println(" PASSED: " + testsPassed);
        System.out.println(" FAILED: " + testsFailed);
        System.out.println("==========================================");
    }

    private static void test(String id, String name, String description, String inputs, Object actual,
            Object expected) {
        testsRun++;
        boolean passed = (actual == null && expected == null) || (actual != null && actual.equals(expected));

        System.out.println(String.format("[%s] %s", id, name));
        System.out.println("      Detalle: " + description);
        System.out.println("      Entradas: " + inputs);

        if (passed) {
            testsPassed++;
            System.out.println("      Resultado: [OK]");
        } else {
            testsFailed++;
            System.out.println("      Resultado: [FAIL]");
            System.out.println(String.format("      Esperado: %s", expected));
            System.out.println(String.format("      Obtenido: %s", actual));
        }
        System.out.println("------------------------------------------");
    }
}
