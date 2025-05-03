package org.example;

import java.util.Scanner;

public class Menu_Final {
    public static void showMenu(String name) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Muy bien, " + name + " ¿Qué acción deseas realizar?:");
        System.out.println("");
        System.out.println("1. Codificar un texto \n" +
                "2. Decodificar un texto");
        System.out.println("\nPor favor, ingresa el número correspondiente a la opción deseada:");

        while (true) {

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: {
                        Codificador.iniciarCodificacion(name);
                        return;
                    }
                    case 2: {
                        Decodificador.iniciarDecodificacion(name);
                        return;
                    }

                    default: {
                        System.out.println("Opción no válida. Por favor, elige 1 o 2.");
                        break;
                    }
                }
            } else {
                System.out.println("Entrada no válida. Por favor, elige 1 o 2.\n");
                scanner.next();
            }
        }
    }
}
