package org.example;

import java.util.Scanner;

public class Menu {
    public static void showMenu(String name) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("A continuación este es el menú de nuestro programa:");
        System.out.println("");
        System.out.println("Menú: \n" +
                "1. Codificar un texto \n" +
                "2. Decodificar un texto \n" +
                "3. Salir");

        System.out.println("");
        System.out.println("Cada número representa la función a ejecutar dentro del programa.\n" +
                "Por favor escribe el número en la terminal en base a la acción que desees que el programa realice.");

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

                    case 3: {
                        System.out.println("Eso fue todo por hoy, gracias por visitarnos, hasta pronto " + name + "!");
                        System.exit(0);
                        break;
                    }
                    default: {
                        System.out.println("Opción no válida. Por favor, ingrese un número del 1 al 3.");
                        break;
                    }
                }
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número del 1 al 3.");
                scanner.next();
            }
        }
    }
}
