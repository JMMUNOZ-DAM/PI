# Netfix - Sistema de Gestión de Telecomunicaciones

## Introducción

**Netfix** es una solución integral de gestión diseñada para compañías proveedoras de servicios de internet (ISP) y telecomunicaciones. Esta aplicación de escritorio, construida en Java con Swing, centraliza las operaciones críticas del negocio, facilitando la administración de contratos, parque de dispositivos (FTTH y 5G), gestión de incidencias técnicas y coordinación del personal.

Su objetivo principal es optimizar el flujo de trabajo entre los diferentes roles de la organización (**Administradores, Supervisores y Técnicos**), proporcionando herramientas específicas para:
- **Gestión de Incidencias:** Ciclo de vida completo desde el reporte hasta la solución.
- **Inventario:** Control de dispositivos, asignación a contratos y gestión de números asociados.
- **Diagnóstico Remoto:** Simulador de pruebas de red (velocidad, latencia, niveles ópticos) para depuración sin hardware real.
- **Administración de Usuarios:** Control de acceso basado en roles (RBAC) y auditoría de acciones.

---

## Estructura del Proyecto

El proyecto sigue una arquitectura **MVC (Modelo-Vista-Controlador)** adaptada, separando la lógica de negocio, la persistencia de datos y la interfaz de usuario.

### 📦 Paquete: `config`
Gestión de la configuración global y recursos.

#### `AppConfig`
Clase **Singleton** encargada de la internacionalización (i18n) y gestión de textos.
- **Métodos:**
  - `getInstance()`: Retorna la instancia única de la configuración.
  - `getMessage(String key)`: Recupera una cadena de texto localizada desde `messages.properties`.
  - `getMessage(String key, Object... args)`: Recupera y formatea un mensaje con parámetros dinámicos.

---

### 📦 Paquete: `modelo`
Representación de datos y acceso a la base de datos.

#### `DatabaseManager`
Clase **Singleton** que gestiona la conexión JDBC con la base de datos MySQL `netfix`.
- **Métodos:**
  - `getConnection()`: Establece o recupera la conexión activa.
  - `beginTransaction()`, `commit()`, `rollback()`: Control de transacciones ACID.
  - `executeQuery(String sql)`: Ejecuta consultas de lectura (SELECT).
  - `executeUpdate(String sql)`: Ejecuta consultas de escritura (INSERT, UPDATE, DELETE).

#### `querys`
Diccionario estático que almacena todas las sentencias SQL utilizadas en la aplicación. Centraliza el mantenimiento de las consultas.
- **Contenido:** Strings estáticos con SQL para Login, Incidencias, Reportes, Gestión de Inventario, etc.

#### `Usuario`
POJO (Plain Old Java Object) que modela a un usuario del sistema.
- **Atributos:** ID, Nombre, Rol, Email, Password.

#### `SimuladorDiagnostico`
Motor de simulación para pruebas técnicas de dispositivos.
- **Subclases:**
  - `Aparato`: Modelo del dispositivo a testear.
  - `Diagnostico`: Resultados del test (velocidad, ping, cobertura, etc.).
- **Métodos:**
  - `generarDiagnostico(Aparato)`: Crea datos aleatorios realistas basados en el tipo de equipo (FTTH vs 5G).
  - `generarDiagnosticoConCarga(...)`: Ejecuta el diagnóstico en segundo plano (`SwingWorker`), mostrando una barra de progreso visual en la UI.

---

### 📦 Paquete: `controlador`
Lógica de negocio y utilidades transversales.

#### `Utilities`
Clase "cerebro" que actúa como controlador principal y librería de funciones.
- **Autenticación y Seguridad:**
  - `loggin(mail, pass)`: Valida credenciales contra la BD.
  - `hashPass(pass)`: Genera hashes seguros usando **BCrypt**.
- **Gestión de Datos:**
  - `ejecutarConsulta(TipoConsulta, params)`: Método central para obtener datos. Usa un `Switch` gigante con un Enum `TipoConsulta` para enrutar peticiones.
  - `ejecutarUpdate(TipoConsulta, params)`: Homólogo para actualizaciones de datos.
- **UI Helpers:**
  - `cargarTabla(JTable, ResultSet)`: Rellena tablas Swing dinámicamente y colorea filas según el estado de la incidencia (Verde=Resuelto, Amarillo=Pendiente).
  - `cargarGrafico(JPanel)`: Genera gráficos estadísticos (Barras) usando **JFreeChart**.
- **Lógica de Negocio:**
  - `logAction(estado, panel, desc)`: Registra eventos de auditoría en la tabla de logs.
  - `seedTestTechnicians()`: Inicializa datos de prueba para técnicos.

---

### 📦 Paquete: `vista`
Interfaz Gráfica de Usuario (GUI) construida con Swing y FlatLaf.

#### `login`
Punto de entrada de la aplicación (`main`).
- Muestra el SplashScreen.
- Gestiona el formulario de acceso y valida el usuario inicial.

#### `mainFrame`
Contenedor principal (Dashboard).
- Implementa un sistema de navegación lateral.
- Usa `CardLayout` para alternar entre los diferentes paneles funcionales sin cerrar la ventana.
- Gestiona los permisos: Habilita/Deshabilita botones según el rol del usuario (Admin/Técnico/Supervisor).

#### `IncidPanel`
Panel principal para la gestión diaria de incidencias.
- **Funciones:**
  - Listado filtrable de tickets.
  - Visualización de detalles (Cliente, Contrato, Dispositivo).
  - Chat de comentarios (Historial de actualizaciones).
  - Botones de acción: "Solucionar", "Actualizar", "Enviar Técnico".

#### `IncidenciaDetalleDialog`
Ventana modal de solo lectura.
- Muestra un resumen ejecutivo y detallado de una incidencia específica.
- Usada para consultas rápidas o impresiones de pantalla.

#### `AdminPanel`
Panel avanzado para administradores de sistemas.
- **Gestión de Contratos:** Vinculación de clientes con servicios.
- **Inventario:**
  - Asignación de Routers/ONTS a contratos.
  - Gestión de líneas móviles (SIMs 5G).
- **Logs:** Visor de auditoría del sistema.

#### `EstadisPanel`
Panel de inteligencia de negocio (BI).
- Muestra gráficos de rendimiento.
- KPIs de incidencias resueltas vs pendientes.

#### `AparatosPanel`
Gestión de inventario físico.
- CRUD de dispositivos.
- Búsqueda por MAC o Número de Serie.

#### `UsuariosPanel`
Gestión de recursos humanos.
- Alta, Baja y Modificación de usuarios del sistema (Técnicos, Agentes).

#### `SplashScreen`
Pantalla de carga inicial para mejorar la experiencia de usuario durante la inicialización de recursos.

---

### 📦 Paquete: `vista.tema`
Gestión de la apariencia visual (Look & Feel).

#### `ThemeManager`
Singleton encargado de aplicar estilos consistentes.
- Configura **FlatLaf** como base.
- Define paletas de colores corporativos.
- Estandariza fuentes, bordes y comportamientos de componentes.

#### `CustomNotification`
Sistema de notificaciones "toast" personalizadas.
- Muestra alertas (Éxito, Error, Info) no intrusivas sobre la UI.
