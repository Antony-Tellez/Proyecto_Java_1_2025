package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor escriba su nombre aquí abajo en la terminal:");
        String name = scanner.nextLine();

        System.out.println("");
        System.out.println("Bienvenido " + name + ". \n" +
                "En esta aplicación podrás codificar y decodificar tus propios textos haciendo uso del cifrado Cesar. \n" +
                "¡Genial! ¿Verdad?");

        System.out.println("");
        System.out.println("Pero antes de iniciar: ¿Desea conocer una breve explicación sobre que es el cifrado Cesar? \n" +
                "(Escriba “Si” en la terminal si lo desea o “No” si lo que busca es entrar directamente al programa).");

        String answer1 = scanner.nextLine();

        while (true) {
            if (answer1.equalsIgnoreCase("Si")||answer1.equalsIgnoreCase("Sí")) {
                System.out.println("");
                System.out.println("Para que Entiendas:\n" +
                        "El cifrado César es uno de los métodos de cifrado más sencillos y famosos.\n" +
                        "Debe su nombre al emperador Cayo Julio César, que lo utilizaba para mantener correspondencia secreta con sus generales.\n" +
                        "Este cifrado sustituye cada carácter del texto por un carácter que está un número constante de posiciones a la izquierda o a la derecha del mismo en el alfabeto.");
                System.out.println("");
                Menu.showMenu(name);
                break;
            } else if (answer1.equalsIgnoreCase("No")) {
                System.out.println("");
                System.out.println("Ok, entonces sin más dilación vamos al programa.");
                System.out.println("");
                Menu.showMenu(name);
                break;
            } else {
                System.out.println("Respuesta no válida, por favor intentalo de nuevo");
                answer1 = scanner.nextLine();
            }
        }
    }
}