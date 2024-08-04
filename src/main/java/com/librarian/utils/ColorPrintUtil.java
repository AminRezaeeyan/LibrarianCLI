package com.librarian.utils;

import java.util.HashMap;
import java.util.Map;

public class ColorPrintUtil {
    private static final String RESET = "\u001B[0m";

    public enum Color {
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static void setColor(Color color) {
        System.out.print(color.getCode());
    }

    public static void resetColor() {
        System.out.print(Color.WHITE.code);
    }
}
