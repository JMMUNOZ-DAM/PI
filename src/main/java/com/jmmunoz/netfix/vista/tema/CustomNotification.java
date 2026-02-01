package com.jmmunoz.netfix.vista.tema;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * Proporciona una interfaz simplificada para las notificaciones de usuario.
 * Centraliza el uso de JOptionPane para asegurar la uniformidad en los mensajes
 * informativos, de éxito, advertencia y error.
 * 
 * @author Juanma Muñoz
 */
public class CustomNotification {

    public enum Type {
        INFO(null, com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("notification.info"),
                JOptionPane.INFORMATION_MESSAGE),
        SUCCESS(null, com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("notification.success"),
                JOptionPane.INFORMATION_MESSAGE), // JOptionPane no tiene SUCCESS nativo, usamos
        // INFO/PLAIN
        WARNING(null, com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("notification.warning"),
                JOptionPane.WARNING_MESSAGE),
        ERROR(null, com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("notification.error"),
                JOptionPane.ERROR_MESSAGE);

        final Color color; // Mantenido por compatibilidad de API aunque no se use en JOptionPane nativo
        final String defaultTitle;
        final int messageType;

        Type(Color color, String defaultTitle, int messageType) {
            this.color = color;
            this.defaultTitle = defaultTitle;
            this.messageType = messageType;
        }
    }

    // Constructor privado para evitar instanciación
    private CustomNotification() {
    }

    /**
     * Muestra una notificación modal usando JOptionPane estándar.
     */
    public static void show(Component parent, String message, Type type) {
        show(parent, null, message, type);
    }

    /**
     * Muestra una notificación modal con título y tipo específicos.
     * 
     * @param parent  Componente padre sobre el que se centra el diálogo.
     * @param title   Título personalizado de la notificación (puede ser null para
     *                usar el por defecto).
     * @param message Cuerpo del mensaje.
     * @param type    Tipo de notificación (INFO, SUCCESS, WARNING, ERROR).
     */
    public static void show(Component parent, String title, String message, Type type) {
        String finalTitle = (title != null) ? title : type.defaultTitle;

        // Mapeo simple a ModernDialog
        com.jmmunoz.netfix.vista.dialogos.ModernDialog.showMessageDialog(
                parent,
                message,
                finalTitle,
                type.messageType);
    }
}
