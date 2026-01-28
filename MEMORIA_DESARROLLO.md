# Memoria de Desarrollo - Proyecto NETFIX

Este documento recoge los puntos clave del desarrollo de la aplicación NETFIX, destacando las decisiones tomadas, los problemas que me he ido encontrando y cómo los he resuelto, así como las ideas que tengo para futuras versiones.

---

## 1. Aspectos Destacados del Desarrollo

Lo que más me ha gustado implementar o lo que considero más interesante del proyecto:

*   **Interfaz Moderna con FlatLaf:**
    No quería que la aplicación tuviera el típico aspecto gris y antiguo de Swing/Java. He dedicado tiempo a integrar la librería **FlatLaf** para darle un toque limpio y moderno. Me ha permitido unificar colores, bordes redondeados y fuentes (Segoe UI) en toda la aplicación de forma "centralizada" usando una clase `ThemeManager`. De esta forma, si quiero cambiar el color principal de la app, solo lo cambio en un sitio.

*   **Arquitectura MVC Modificada:**
    He intentado seguir el patrón Modelo-Vista-Controlador para mantener el código ordenado. Tengo paquetes separados para la lógica (`controlador`), las ventanas (`vista`) y los datos (`modelo`). Aunque al principio cuesta más estructurarlo, luego facilita mucho saber dónde buscar si falla algo en la base de datos o en un botón.

*   **Simulación de Diagnóstico en Tiempo Real:**
    Para la parte de los técnicos, implementé un pequeño simulador (`SimuladorDiagnostico`) que genera datos aleatorios de red (velocidad, latencia, etc.) y usa hilos (`SwingWorker`) para no congelar la pantalla, dando la sensación de que está "midiendo" de verdad.

*   **Seguridad Básica:**
    No guardo las contraseñas en texto plano en la base de datos. Utilicé **BCrypt** para hashearlas. Me pareció un detalle importante de seguridad, aunque sea un proyecto académico.

---

## 2. Problemas Encontrados y Soluciones

Durante el desarrollo me surgieron varios bloqueos que tuve que solucionar:

*   **Problema con los acentos y la codificación (UTF-8):**
    *   *El problema:* Al principio, al guardar datos o mostrar textos en el README y la app, salían caracteres raros () en lugar de tildes o eñes.
    *   *La solución:* Tuve que asegurarme de configurar el proyecto y los archivos en UTF-8 y corregir manualmente los textos dañados. Aprendí que es vital fijar la codificación desde el principio en el IDE y en Git.

*   **Gestión de la navegación entre pantallas:**
    *   *El problema:* Al principio abría muchas ventanas (`New JFrame`) y se llenaba la barra de tareas. Era un lío para el usuario.
    *   *La solución:* Implementé un `MainFrame` principal con un diseño de **CardLayout**. Así, el menú lateral siempre está fijo y solo cambia el panel central. Da una experiencia mucho más fluida, como una web o app moderna.

*   **Conexión a Base de Datos y "Leaks":**
    *   *El problema:* A veces, si hacía muchas consultas, la aplicación fallaba o iba lenta.
    *   *La solución:* Creé una clase `DatabaseManager` (Singleton) para reutilizar la conexión. También revisé el código para asegurarme de cerrar siempre los `ResultSet` y `PreparedStatement` dentro de bloques `try-catch` o `finally`.

*   **Conflictos con Git:**
    *   *El problema:* Al trabajar en diferentes ramas (`desarrollo`, `entrega`) y hacer pruebas, a veces tenía conflictos al hacer merge o push, sobre todo con archivos que cambiaba localmente.
    *   *La solución:* Decidí mantener un flujo de trabajo limpio: trabajar en una rama aparte y solo subir a la principal cuando el código funcionase seguro. En caso de desastre (como cuando intenté meter el tema oscuro y se rompió), aprendí a usar `git reset --hard` para volver a una versión estable conocida.

*   **Rigidez del Diseñador Gráfico de NetBeans (Matisse):**
    *   *El problema:* El asistente gráfico de NetBeans es muy visual, pero genera bloques de código "protegidos" (en gris/azul) que no te deja tocar. Eso me frustraba mucho cuando quería meter diseños dinámicos, bucles para botones o simplemente retocar un layout de forma precisa, ya que el IDE forzaba su propio código `GroupLayout` intocable.
    *   *La solución:* Tomé la decisión de abrir y editar las clases de la vista directamente con **VSCode**. Al hacerlo "a mano", gané libertad total para limpiar código redundante, reorganizar los componentes y aplicar mis propios estilos sin que NetBeans me bloqueara. Fue un poco arriesgado mezclar editores, pero valió la pena para tener el control real del código fuente de la interfaz.

---

## 3. Trabajo Futuro y Mejoras

Hay cosas que se quedaron en el tintero por falta de tiempo o complejidad, pero que me gustaría añadir más adelante:

*   **Modo Oscuro Real:**
    Intenté implementarlo (un botón para cambiar entre claro/oscuro en tiempo real), pero daba problemas de refresco en algunos componentes y afectaba a la estabilidad. Decidí dejarlo fuera de esta entrega para priorizar que la app funcione perfecta, pero es lo primero que añadiría.

*   **Generación de PDFs:**
    Sería muy útil que los técnicos pudieran exportar el "parte de incidencia" o el "resultado del diagnóstico" a un archivo PDF directamente desde la app para enviárselo al cliente.

*   **Versión Web/Móvil:**
    Ahora mismo es una app de escritorio (Java Swing). En el futuro, lo ideal sería migrar el backend a una API REST (Spring Boot, por ejemplo) y hacer un frontend web para que se pueda acceder desde cualquier sitio sin instalar nada.

*   **Chat en tiempo real:**
    El chat de incidencias actual guarda comentarios en BBDD, pero no se actualiza "en vivo" (tienes que recargar). Usar *WebSockets* para que sea instantáneo sería una gran mejora.
