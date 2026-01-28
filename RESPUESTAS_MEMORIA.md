# Respuestas de Gestión y Planificación del Proyecto

Este documento recoge las respuestas a las cuestiones planteadas sobre la ejecución, legalidad y valoración económica del proyecto NETFIX.

## 1. ¿Ha podido cumplir la planificación? ¿Qué problemas ha tenido?

**Sí, se ha cumplido la planificación general, aunque con desviaciones puntuales.**

Se han completado todos los requisitos funcionales obligatorios (Login, Gestión de Usuarios, Panel de Administración, etc.) dentro del plazo. Sin embargo, la fase de desarrollo de la interfaz de usuario consumió aproximadamente un 20% más de tiempo del estimado inicialmente.

**Principales problemas y ajustes:**
*   **Curva de aprendizaje de FlatLaf:** Implementar un diseño moderno sobre Swing requirió más investigación de la prevista para personalizar componentes (bordes, colores, sombras).
*   **Gestión del Repositorio:** Hubo dificultades técnicas con Git (conflictos de ramas y problemas de codificación de caracteres) que obligaron a detener el desarrollo de nuevas "features" (como el modo oscuro) para dedicar tiempo a estabilizar y limpiar el código base.
*   **Adaptación de Herramientas:** La migración parcial a VSCode para editar la interfaz gráfica fue una decisión no planificada que, aunque beneficiosa a largo plazo, supuso un freno temporal en la velocidad de desarrollo durante los primeros días.

## 2. ¿Su proyecto necesita algún tipo de permiso o autorización administrativa?

**No.**

Al tratarse de un proyecto académico que simula un entorno empresarial con datos ficticios, no requiere licencias de actividad ni autorizaciones gubernamentales.

*   **Protección de Datos (RGPD):** Si el proyecto se desplegase en un entorno real con datos de clientes reales, sería obligatorio inscribir los ficheros ante la AEPD y cumplir con el RGPD. En este estado, al usar datos generados (Faker/bancos de pruebas), está exento.
*   **Propiedad Intelectual:** El código hace uso de librerías de código abierto (FlatLaf, JFreeChart, MySQL Connector) bajo licencias compatibles (Apache 2.0, LGPL, GPL), por lo que legalmente es viable su distribución cumpliendo con la atribución correspondiente.

## 3. ¿Ha establecido algún documento de prevención de riesgos laborales?

**Sí, se han considerado medidas básicas de prevención asociadas al trabajo en pantalla (PVD).**

Dado que el desarrollo es una actividad de oficina/sedentaria, no existe un documento formal de PRL visado por un técnico, pero sí se han aplicado las siguientes pautas durante la ejecución:

*   **Ergonomía Visual:** Uso de monitores con protección de luz azul y configuración de la interfaz de desarrollo (IDE) en modo oscuro para reducir la fatiga visual tras jornadas largas.
*   **Higiene Postural:** Se han programado pausas activas cada 2 horas para estiramientos y descanso visual, utilizando una silla ergonómica con soporte lumbar adecuado.
*   **Entorno:** Ubicación del puesto de trabajo en zona con iluminación natural lateral para evitar reflejos en la pantalla.

## 4. Valoración económica respecto a su ejecución

Se estima un coste total de desarrollo de **3.150,00 €** si el proyecto se hubiese realizado en un entorno profesional junior.

**Desglose de costes:**

| Concepto | Cantidad | Coste Unitario | Total |
| :--- | :---: | :---: | :---: |
| **Horas de Desarrollo** | 120 horas | 25,00 €/h | 3.000,00 € |
| **Licencias Software*** | 1 | 0,00 € | 0,00 € |
| **Amortización Equipo** | 1 | 150,00 € (parte prop.) | 150,00 € |
| **TOTAL** | | | **3.150,00 €** |

*\*Nota: Se han utilizado herramientas Open Source (NetBeans, VSCode, Git, MySQL Community), por lo que el coste de licencias es cero.*
