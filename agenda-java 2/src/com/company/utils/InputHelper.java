package com.company.utils;

import java.util.Scanner;

public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje + ": ");
        String input = scanner.nextLine().trim();

        while (input.isEmpty()) {
            System.out.println("El campo no puede estar vacío. Intente nuevamente.");
            System.out.print(mensaje + ": ");
            input = scanner.nextLine().trim();
        }

        return input;
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje + ": ");
        while (!scanner.hasNextInt()) {
            System.out.println("Debe ingresar un número entero.");
            scanner.next();
            System.out.print(mensaje + ": ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    public static int leerOpcion(String mensaje, int min, int max) {
        int opcion;
        do {
            opcion = leerEntero(mensaje);
            if (opcion < min || opcion > max) {
                System.out.println("Opción fuera de rango. Intente nuevamente.");
            }
        } while (opcion < min || opcion > max);
        return opcion;
    }

    public static String leerCUIT(String mensaje) {
        System.out.print(mensaje + " (formato XX-XXXXXXXX-X): ");
        String input = scanner.nextLine().trim();

        //Regex para validar formato de CUIT valido
        while (!input.matches("^\\d{2}-\\d{8}-\\d{1}$")) {
            System.out.println("CUIT inválido. Debe seguir el formato XX-XXXXXXXX-X");
            System.out.print(mensaje + ": ");
            input = scanner.nextLine().trim();
        }

        return input;
    }
}
