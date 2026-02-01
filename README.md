# Proyecto NETFIX 

**NETFIX** es una aplicación de gestión empresarial avanzada para compañías de telecomunicaciones (ISP/Carriers).

Su objetivo principal es optimizar el flujo de trabajo entre los diferentes roles de la organización (**Sistemas, Supervisores y Técnicos**), proporcionando herramientas específicas para la toma de decisiones y la operación diaria.

---

## Funcionalidades Principales

*   **Gestión de Incidencias:** Ciclo de vida completo desde el reporte hasta la solución, incluyendo asignación de técnicos y comentarios.
*   **Inventario y Provisión:** Control de dispositivos, asignación a contratos y gestión de números asociados (5G).
*   **Diagnóstico Remoto:** Simulador de pruebas de red (velocidad, latencia, niveles ópticos) para depuración técnica sin hardware real.
*   **Administración Interactiva:** Paneles avanzados para la gestión de usuarios y supervisión de equipos de trabajo.
*   **Interfaz Moderna:** Sistema de diseño personalizado (temas claro/oscuro) y diálogos estilizados que reemplazan los componentes nativos estándar.

---

## Estructura del Proyecto

El proyecto sigue una arquitectura **MVC (Modelo-Vista-Controlador)** adaptada, separando la lógica de negocio, la persistencia de datos y la interfaz de usuario.

### 📦 Paquete: `config`
Gestión de la configuración global y recursos.

#### `AppConfig`
Gestor central de la configuración de la aplicación.
- **Funcionalidad:**
  - Garantiza una única instancia de configuración compartida.
  - Maneja la internacionalización (i18n) cargando textos desde archivos `.properties`.
  - Provee métodos `getMessage()` para recuperar cadenas localizadas y formateadas dinámicamente.

---

### 📦 Paquete: `modelo`
Representación de datos y acceso a la base de datos.

#### `DatabaseManager`
Administrador de conexiones a la base de datos.
- **Funcionalidad:**
  - Gestiona el pool de conexiones JDBC/MySQL.
  - Controla el ciclo de vida de las transacciones (inicio, commit, rollback).
  - Provee métodos de bajo nivel para ejecutar sentencias SQL.

#### `Querys`
Repositorio centralizado de sentencias SQL.
- **Funcionalidad:**
  - Almacena constantes estáticas con todas las consultas del sistema.
  - Facilita el mantenimiento y modificación de la lógica de acceso a datos sin tocar el código de los controladores.

#### `Usuario` / `Aparato` / `Incidencia`
Clases POJO (Plain Old Java Objects) que modelan las entidades del negocio. Capturan la estructura de las tablas de la base de datos para su uso en la aplicación Java.

#### `SimuladorDiagnostico`
Motor de simulación técnica.
- **Funcionalidad:**
  - Genera valores aleatorios realistas para pruebas de red (Ping, Jitter, BA/B, Potencia Óptica, Cobertura 4G/5G).
  - Simula latencia de red mediante `SwingWorker` para no congelar la interfaz de usuario durante el diagnóstico.
  - Actualiza en tiempo real los componentes visuales con los resultados obtenidos.

---

### 📦 Paquete: `controlador`
Lógica de negocio y utilidades transversales.

#### `Utilities`
Clase principal de utilidades y orquestación.
- **Autenticación:** Métodos `loggin()` y `hashPass()` (usando BCrypt) para validar credenciales de forma segura.
- **Ejecución de Datos:** Métodos `ejecutarConsulta` y `ejecutarUpdate` que actúan como fachada simplificada para interactuar con la base de datos, manejando excepciones y cierres de recursos automáticamente.
- **Manejo de Tablas:** Lógica para poblar `JTable` desde `ResultSet`, incluyendo reglas de negocio visuales (ej. coloreado de filas según estado de la incidencia).
- **Auditoría (Logging):** Método `logAction` que registra todas las operaciones críticas de los usuarios en el historial del sistema.

---

### 📦 Paquete: `vista`
Interfaz Gráfica de Usuario (GUI).

#### `login`
Ventana de acceso.
- Valida credenciales.
- Muestra feedback visual (cargando) y redirige al Dashboard principal.

#### `MainFrame`
Contenedor principal (Dashboard).
- Implementa navegación lateral persistente.
- Utiliza `CardLayout` para la transición fluida entre paneles.
- **Control de Acceso:** Oculta o deshabilita secciones de navegación basándose dinámicamente en el rol del usuario logueado.

#### `IncidPanel`
Centro de operaciones de incidencias.
- **Funciones:** Filtrado de tickets, asignación rápida, resolución de incidencias y chat de seguimiento.
- Permite abrir el detalle extendido de cada ticket.

#### `IncidenciaDetalleDialog`
Visor detallado modal.
- Presenta toda la información contextual de una incidencia: datos del cliente, contrato asociado, historial de comentarios y dispositivos vinculados.

#### `SupervisorPanel`
Herramienta para coordinadores de equipo.
- Permite visualizar la carga de trabajo de los técnicos.
- Facilita la reasignación de tareas y supervisión del estado global del servicio.

#### `AparatosPanel`
Gestión de parque de dispositivos.
- Búsqueda avanzada por Número de Serie, MAC o Contrato.
- Integra el **Simulador de Diagnóstico** en la interfaz, permitiendo ejecutar pruebas y visualizar resultados técnicos en el momento.

#### `AdminPanel`
Panel de administración de sistemas.
- Gestión de contratos y vinculación de equipos (Routers, ONTs).
- Asignación de líneas 5G.
- Visor de logs del sistema para auditoría y depuración.

#### `UsuariosPanel`
Gestión de perfil propio.
- Permite a los usuarios actualizar sus credenciales, nombre y visualizar su información de rol.

#### `EstadisPanel`
Panel de Business Intelligence.
- Gráficos estadísticos generados dinámicamente (JFreeChart) para analizar el volumen de incidencias y rendimiento.

---

### 📦 Paquete: `vista.dialogos`
Componentes de interfaz personalizados.

#### `ModernDialog`
Sistema de diálogos propio que reemplaza a `JOptionPane`.
- **Características:**
  - Estética unificada con el tema de la aplicación (bordes, fuentes, colores).
  - Soporte para mensajes de confirmación, entrada de texto y alertas.
  - Elimina la dependencia de los diálogos nativos del sistema operativo para una experiencia de usuario consistente.

---

### 📦 Paquete: `vista.tema`
Gestión de la identidad visual.

#### `ThemeManager`
Gestor de apariencia y estilos.
- Centraliza la configuración de **FlatLaf**.
- Aplica personalizaciones específicas a componentes (tablas, botones, campos de texto) para asegurar coherencia visual en toda la app.
- Define paletas de colores semánticos (Éxito, Error, Acento).

#### `CustomNotification`
Sistema de notificaciones no intrusivas.
- Muestra mensajes flotantes o diálogos estilizados para informar al usuario del resultado de sus acciones sin interrumpir flujos críticos innecesariamente.
#### `TelecomTheme`
Definición de constantes de diseño (Colores hexadecimales, tipografías base).

---

## Requisitos del Sistema

*   **Java Development Kit (JDK):** Versión 17 o superior.
*   **Base de Datos:** MySQL o MariaDB.
*   **Dependencias:**
    *   FlatLaf (Look and Feel).
    *   MySQL Connector/J.
    *   BCrypt (Seguridad).
    *   JFreeChart (Gráficos).

---

## Instalación y Uso

1.  **Base de Datos:** Importar el script SQL incluido (`netfix.sql`) en el servidor MySQL.
2.  **Configuración:** Verificar las credenciales de base de datos en `DatabaseManager`.
3.  **Ejecución:** Iniciar la aplicación desde `login.java`.
4.  **Roles de Prueba:**
    *   **Admin:** Acceso total (Sistemas).
    *   **Supervisor:** Gestión de técnicos e incidencias.
    *   **Técnico:** Resolución de incidencias y diagnóstico.

<!-- Última actualización: 28/01/2026 -->
