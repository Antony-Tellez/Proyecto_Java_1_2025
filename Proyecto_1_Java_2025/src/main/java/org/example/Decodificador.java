package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Decodificador {
    public static void iniciarDecodificacion (String name){
        Scanner scanner = new Scanner(System.in);
        int clave = 0;

        System.out.println("\nMuy bien, es hora de decodificar textos.");
        System.out.println("\n¿Desea utilizar una llave específica para decodificar el texto?\n" +
                "1. Sí\n" +
                "2. No\n" +
                "Por favor, escriba el número de su respuesta: ");

        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.print("Entrada no válida. Por favor, ingresa 1 o 2: ");
                scanner.nextLine();
                continue;
            }

            int opcionClave = scanner.nextInt();
            scanner.nextLine();

            if (opcionClave == 1) {
                while (true) {
                    System.out.println("\n¿Cuál es la clave que deseas utilizar? ");
                    if (scanner.hasNextInt()) {
                        clave = scanner.nextInt();
                        scanner.nextLine();

                        if (clave < 0) {
                            System.out.println("\nNota: La clave es negativa. El texto será decodificado con un desplazamiento hacia la izquierda.");
                        } else if (clave == 0) {
                            System.out.println("\nAdvertencia: La clave es 0. El texto no se modificará.");
                        }

                        System.out.println("\nMuy bien, la clave a utilizar es: " + clave);
                        break;
                    } else {
                        System.out.println("Entrada no válida. Debes ingresar un número entero para la clave.");
                        scanner.nextLine();
                    }
                }
                break;
            } else if (opcionClave == 2) {
                Analizador.iniciarAnalisis(name);
                return;
            } else {
                System.out.print("Opción no válida. Escriba 1 o 2: ");
            }
        }

        System.out.println("\n¿Cómo prefiere subir el texto al programa?");
        System.out.println("1. Escrito en la terminal (Se recomienda para textos de un párrafo)\n" +
                "2. Subirlo con un archivo .txt (Se recomienda si el texto incluye más de un párrafo)\n" +
                "Escriba el número de su elección: ");

        String textoOriginal = "";

        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.print("Entrada no válida. Por favor, escriba 1 o 2: ");
                scanner.nextLine();
                continue;
            }

            int opcionEntrada = scanner.nextInt();
            scanner.nextLine();

            if (opcionEntrada == 1) {
                System.out.println("\nMuy bien, por favor escriba el texto a decodificar:");
                textoOriginal = scanner.nextLine();

                while (textoOriginal.trim().isEmpty()) {
                    System.out.println("El texto ingresado está vacío. Por favor, ingrésalo nuevamente:");
                    textoOriginal = scanner.nextLine();
                }

                break;

            } else if (opcionEntrada == 2) {
                while (true) {
                    System.out.print("Por favor escriba la URI de la ubicación del archivo: ");
                    String rutaArchivo = scanner.nextLine();

                    try {
                        textoOriginal = new String(Files.readAllBytes(Paths.get(rutaArchivo)));

                        if (textoOriginal.trim().isEmpty()) {
                            while (true) {
                                System.out.println("El archivo ingresado está vacío. ¿Desea intentarlo nuevamente? (Sí/No): ");
                                String respuesta = scanner.nextLine().trim();

                                if (respuesta.equalsIgnoreCase("Sí") || respuesta.equalsIgnoreCase("Si")) {
                                    break;
                                } else if (respuesta.equalsIgnoreCase("No")) {

                                    System.out.println("\n¿Desea seguir usando el programa?");
                                    System.out.println("1. Sí");
                                    System.out.println("2. No" +
                                            "\nPor favor escriba 1 o 2 en base a su respuesta");

                                    while (true) {

                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Entrada no válida. Por favor, escriba 1 o 2.");
                                            scanner.nextLine();
                                            continue;
                                        }

                                        int continuar = scanner.nextInt();
                                        scanner.nextLine();

                                        if (continuar == 1) {
                                            Menu_Final.showMenu(name);
                                            return;
                                        } else if (continuar == 2) {
                                            System.out.println("Eso fue todo por hoy, gracias por visitarnos, hasta pronto " + name + "!");
                                            System.exit(0);
                                        } else {
                                            System.out.println("Opción no válida. Escriba 1 o 2.");
                                        }
                                    }
                                } else {
                                    System.out.println("Respuesta no válida. Por favor, escribe Sí o No.");
                                }
                            }
                            continue;
                        }
                        System.out.println("Documento encontrado correctamente, procediendo con la decodificación...");
                        break;
                    } catch (IOException e) {
                        System.out.println("Documento no encontrado. Verifique la URI e intente de nuevo.\n" +
                                "(ej: C:/Users/Nombre/archivo.txt).");
                    }
                }
                break;
            } else {
                System.out.print("\nOpción no válida. Por favor, elija 1 o 2: ");
            }
        }

        String textoDecodificado = decodificarTexto(textoOriginal, clave);

        System.out.print("\n¿Con qué nombre desea guardar el archivo decodificado? (sin la extensión .txt): ");
        String nombrePersonalizado = scanner.nextLine().trim();

        while (nombrePersonalizado.isEmpty() || nombrePersonalizado.matches(".*[\\\\/:*?\"<>|].*")) {
            System.out.print("Nombre no válido. Por favor, ingrese un nombre válido sin caracteres especiales: ");
            nombrePersonalizado = scanner.nextLine().trim();
        }

        String nombreArchivoSalida = nombrePersonalizado + ".txt";
        File archivoSalida = new File(nombreArchivoSalida);

        while (archivoSalida.exists()) {
            System.out.println("\nEl archivo \"" + nombreArchivoSalida + "\" ya existe.");
            System.out.print("¿Desea reemplazarlo? (Sí/No): ");
            String respuesta = scanner.nextLine().trim();

            if (respuesta.equalsIgnoreCase("Sí") || respuesta.equalsIgnoreCase("Si")) {
                break;
            } else if (respuesta.equalsIgnoreCase("No")) {
                System.out.print("Por favor, ingrese un nuevo nombre para el archivo: ");
                nombrePersonalizado = scanner.nextLine().trim();
                while (nombrePersonalizado.isEmpty() || nombrePersonalizado.matches(".*[\\\\/:*?\"<>|].*")) {
                    System.out.print("Nombre no válido. Por favor, ingrese un nombre válido sin caracteres especiales: ");
                    nombrePersonalizado = scanner.nextLine().trim();
                }
                nombreArchivoSalida = nombrePersonalizado + ".txt";
                archivoSalida = new File(nombreArchivoSalida);
            } else {
                System.out.println("Respuesta no válida. Por favor, escriba Sí o No.");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida))) {
            writer.write(textoDecodificado);
            System.out.println("\nTexto decodificado correctamente.");
            System.out.println("Archivo guardado en: " + archivoSalida.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo decodificado.");
            System.out.println("Detalle técnico: " + e.getMessage());
            System.out.println("\n¿Deseas intentar guardar el archivo en otra ubicación? (Sí/No)");

            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("Sí") || respuesta.equalsIgnoreCase("Si")) {
                while (true) {
                    System.out.print("Por favor, ingresa la nueva ruta de guardado: ");
                    String nuevaRuta = scanner.nextLine();


                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nuevaRuta))) {
                        writer.write(textoDecodificado);
                        System.out.println("\nTexto decodificado correctamente y guardado en: " + nuevaRuta);
                        break;
                    } catch (IOException ex) {
                        System.out.println("\nError al guardar el archivo en la nueva ubicación.");
                        System.out.println("¿Deseas intentar guardar el archivo en otra ubicación? (Sí/No)");

                        String intentoRespuesta = scanner.nextLine();
                        if (!intentoRespuesta.equalsIgnoreCase("Sí") && !intentoRespuesta.equalsIgnoreCase("Si")) {
                            break;
                        }
                    }
                }
            } else if (respuesta.equalsIgnoreCase("No")) {
                System.out.println("Archivo no guardado.");

                while (true) {
                    System.out.println("\n¿Desea continuar usando el programa?");
                    System.out.println("1. Sí");
                    System.out.println("2. No" +
                            "\nPor favor escriba 1 o 2 en base a su respuesta");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada no válida. Por favor, escriba 1 o 2.");
                        scanner.next();
                        continue;
                    }

                    int continuar = scanner.nextInt();
                    scanner.nextLine();

                    if (continuar == 1) {
                        Menu_Final.showMenu(name);
                        break;
                    } else if (continuar == 2) {
                        System.out.println("Eso fue todo por hoy, gracias por visitarnos, hasta pronto " + name + "!");
                        System.exit(0);
                        break;
                    } else {
                        System.out.println("Opción no válida. Escriba 1 o 2.");
                    }
                }
            } else {
                System.out.println("Opción no válida. Por favor, responde Sí o No.");
            }
        }
        System.out.println("\n¿Desea continuar usando el programa?\n" +
                "1. Sí\n" +
                "2. No\n" +
                "\nPor favor escriba 1 o 2 en base a su respuesta");
        while (true) {

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada no válida. Por favor, escriba 1 o 2.");
                scanner.nextLine();
                continue;
            }

            int continuar = scanner.nextInt();
            scanner.nextLine();

            if (continuar == 1) {
                Menu_Final.showMenu(name);
                break;
            } else if (continuar == 2) {
                System.out.println("Eso fue todo por hoy, gracias por visitarnos, hasta pronto " + name + "!");
                System.exit(0);
            } else {
                System.out.println("Opción no válida. Escriba 1 o 2.");
            }
        }
    }

    private static String decodificarTexto(String texto, int clave) {
        StringBuilder resultado = new StringBuilder();
        clave = clave % 26;

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char decodificado = (char) ((c - base + clave + 26) % 26 + base);
                resultado.append(decodificado);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
}
