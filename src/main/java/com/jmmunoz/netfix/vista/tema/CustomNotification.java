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
        INFO(null, "Información", JOptionPane.INFORMATION_MESSAGE),
        SUCCESS(null, "Éxito", JOptionPane.INFORMATION_MESSAGE), // JOptionPane no tiene SUCCESS nativo, usamos
                                                                 // INFO/PLAIN
        WARNING(null, "Advertencia", JOptionPane.WARNING_MESSAGE),
        ERROR(null, "Error", JOptionPane.ERROR_MESSAGE);

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
