/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.vista.tema;

import java.awt.Color;

/**
 * Paleta de colores corporativa para la aplicación Netfix.
 * Define constantes estáticas para colores de fondo, texto, acentos y estados.
 * 
 * @author Juanma Muñoz
 */
public final class TelecomTheme {

    private TelecomTheme() {
    }

    // Marca / Acento (cyan telecom)
    public static final Color ACCENT = new Color(43, 107, 180, 236); // #2b6bb4ec
    public static final Color ACCENT_DARK = new Color(10, 80, 160); // #0A50A0

    // Neutros claros
    public static final Color APP_BG = Color.WHITE; // #FFFFFF fondo app
    public static final Color SURFACE = new Color(255, 255, 255); // #FFFFFF tarjetas/paneles
    public static final Color BORDER = new Color(220, 226, 235); // #DCE2EB

    // Texto
    public static final Color TEXT = new Color(17, 30, 59, 255); // #1a305eff
    public static final Color TEXT_MUTED = new Color(102, 112, 133);// #667085

    // Barra Lateral
    public static final Color NAV_BG = new Color(17, 22, 43); // #111e3bff
    public static final Color NAV_BG_ACTIVE = new Color(10, 80, 160); // #0A50A0
    public static final Color NAV_BG_HOVER = new Color(10, 80, 160); // #0A50A0
    public static final Color TEXT_ON_DARK = new Color(230, 235, 245); // #E6EBF5

    // Estados (útiles para incidencias)
    public static final Color OK = new Color(0, 180, 120); // #00B478
    public static final Color WARN = new Color(255, 170, 0); // #FFAA00
    public static final Color ERROR = new Color(220, 70, 70); // #DC4646

    // Colores Gráficas y Visualización
    public static final Color CHART_PRIMARY = new Color(43, 107, 180, 236); // #2b6bb4ec
    public static final Color GRID_LINE = new Color(240, 240, 240); // #F0F0F0

    // Bordes y Fondos Adicionales
    public static final Color BORDER_LIGHT = new Color(230, 230, 230); // #E6E6E6
    public static final Color BORDER_DARK = new Color(200, 200, 200); // #C8C8C8
    public static final Color BG_SUBTLE = new Color(245, 247, 250); // #F5F7FA
    public static final Color BG_INFO = new Color(230, 248, 255); // #E6F8FF

    // Básicos
    public static final Color WHITE = Color.WHITE; // #FFFFFF
    public static final Color BLACK = Color.BLACK; // #000000
    public static final Color GRAY = Color.GRAY; // #808080

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0); // Transparente
    public static final Color BG_ERROR = new Color(255, 236, 236); // #FFECEC
    public static final Color TEXT_ERROR = new Color(153, 0, 0, 185); // #990000b9
    public static final Color TEXT_DARK = new Color(50, 50, 50); // #323232
}
