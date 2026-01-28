
# Documentación de Pruebas Unitarias y de Integración: `TestUniIntegra` (Versión Revisada)
=======
# Documentación de Pruebas Unitarias y de Integración: `TestUniIntegra`

Este documento detalla la batería de pruebas implementada en la clase `com.jmmunoz.netfix.tests.TestUniIntegra`. Esta clase funciona como un ejecutor de pruebas independiente (Test Runner) que verifica tanto la lógica de negocio (Pruebas Unitarias) como la conectividad con la base de datos (Pruebas de Integración).

## Resumen General

-   **Clase Principal:** `TestUniIntegra.java`
-   **Ubicación:** `src/main/java/com/jmmunoz/netfix/tests/`
-   **Objetivo:** Validar componentes críticos sin necesidad de levantar toda la interfaz gráfica.
-   **Método de Ejecución:** Ejecutar el método `main` de esta clase.

---

## 1. Pruebas de Unidad (Lógica de Negocio)

Estas pruebas se ejecutan sin conexión a la base de datos y verifican el correcto funcionamiento de métodos utilitarios y modelos de datos.

| ID | Nombre de la Prueba | Descripción | Resultado Esperado |
| :--- | :--- | :--- | :--- |
| **U01** | **Email Valid (.com)** | Verifica que el validador de correos acepte dominios `.com`. | `true` (Válido) |
| **U02** | **Email Valid (.es)** | Verifica que el validador de correos acepte dominios `.es`. | `true` (Válido) |
| **U03** | **Email Invalid (gmail)** | Verifica que se rechacen dominios externos no corporativos (ej. `gmail.com`). | `false` (Inválido) |
| **U04** | **Hashing no vacío** | Prueba que la función de hashing de contraseñas (`hashPass`) genere una cadena no vacía, y distinta al texto original. | `true` |
| **U05** | **Verificación BCrypt** | Valida que una contraseña en texto plano coincida con su hash generado anteriormente usando `BCrypt`. | `true` (Coinciden) |
| **U06** | **Actualización de Contraseña** | Verifica que al solicitar un cambio de contraseña, se genere un nuevo hash diferente al anterior. | `true` (Hash cambia) |
| **U07** | **Pass Update (Vacío)** | Verifica que si se intenta actualizar la contraseña con un campo vacío, se mantenga el hash anterior (no se modifique). | `true` (Mantiene hash) |
| **U08** | **Modelo Usuario Props** | Prueba la creación de un objeto `Usuario`, verificando que el constructor y los *getters* asignen y retornen los valores correctamente. | `true` |
| **U09** | **Simulador Instanciación** | Verifica que la clase `SimuladorDiagnostico` pueda ser instanciada sin errores. | `true` (No nulo) |
| **U10** | **Verificación Booleana** | Prueba de control básica para asegurar que el sistema de aserciones del test runner funciona. | `true` |

---

## 2. Pruebas de Integración (Base de Datos)

Estas pruebas requieren que el servidor de base de datos MySQL esté activo y accesible según la configuración en `AppConfig`.

| ID | Nombre de la Prueba | Descripción | Resultado Esperado |
| :--- | :--- | :--- | :--- |
| **I01** | **Conexión DB (Roles)** | Intenta establecer una conexión real a la base de datos y ejecutar una consulta `SELECT` sobre la tabla de perfiles/roles. | `true` (Conexión exitosa y datos recuperados) |

---

## Interpretación de Resultados

Al ejecutar la clase, la consola mostrará un reporte detallado:

-   **[OK]:** La prueba pasó exitosamente.
-   **[FAIL]:** La prueba falló. Se mostrará el valor esperado vs. el obtenido.

Al final del log se presenta un resumen con el total de pruebas ejecutadas, pasadas y fallidas.
