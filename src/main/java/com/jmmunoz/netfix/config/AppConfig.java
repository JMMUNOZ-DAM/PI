/*
 * AppConfig.java
 * Clase centralizada para gestionar la configuración y textos de la aplicación.
 */
package com.jmmunoz.netfix.config;

import java.util.ResourceBundle;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * Gestiona la carga de recursos de texto desde messages.properties.
 * Proporciona una instancia única compartida.
 */
public class AppConfig {

    private static AppConfig instance;
    private ResourceBundle messages;

    private AppConfig() {
        try {
            // Carga el fichero messages.properties del classpath
            messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        } catch (Exception e) {
            System.getLogger(AppConfig.class.getName()).log(System.Logger.Level.ERROR,
                    "Error cargando messages.properties: " + e.getMessage());
        }
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    /**
     * Obtiene un mensaje por su clave.
     * 
     * @param key Clave del mensaje en properties.
     * @return El mensaje o la clave entre ! si no existe.
     */
    public String getMessage(String key) {
        try {
            if (messages != null && messages.containsKey(key)) {
                return messages.getString(key);
            }
        } catch (Exception e) {
            System.getLogger(AppConfig.class.getName()).log(System.Logger.Level.ERROR,
                    "Error recuperando clave " + key + ": " + e.getMessage());
        }
        return "!" + key + "!";
    }

    /**
     * Obtiene un mensaje parametrizado.
     * 
     * @param key  Clave del mensaje.
     * @param args Argumentos para sustituir {0}, {1}, etc.
     * @return Mensaje formateado.
     */
    public String getMessage(String key, Object... args) {
        String msg = getMessage(key);
        return MessageFormat.format(msg, args);
    }
}
