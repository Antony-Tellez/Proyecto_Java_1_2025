package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Analizador {
    private static final Set<String> PALABRAS_COMUNES = new HashSet<>(Arrays.asList(
            "el", "la", "los", "las", "de", "del", "que", "y", "en", "a", "con", "por", "para", "se", "no", "una", "un",
            "su", "al", "es", "lo", "como", "más", "pero", "sus", "ya", "o", "este", "sí", "porque", "esta", "entre",
            "cuando", "muy", "sin", "sobre", "también", "me", "hasta", "hay", "donde", "quien", "desde", "todo", "nos",
            "durante", "todos", "uno", "les", "ni", "contra", "otros", "ese", "eso", "ante", "ellos", "e", "esto", "mí",
            "antes", "algunos", "qué", "unos", "yo", "otro", "otras", "otra", "él", "tanto", "esa", "estos", "mucho",
            "quienes", "nada", "muchos", "cual", "poco", "ella", "estar", "estas", "al", "ser", "hacer", "tiene", "tenía",
            "entonces", "fue", "era", "va", "vamos", "tengo", "puede", "han", "he", "dice", "dijo", "había", "cada", "vez",
            "ahora", "bien", "así", "aunque", "cómo", "nunca", "todas", "tarde", "día", "años", "tiempo", "mismo",

            "the", "be", "to", "of", "and", "a", "in", "that", "have", "i", "it", "for", "not", "on", "with", "he", "as",
            "you", "do", "at", "this", "but", "his", "by", "from", "they", "we", "say", "her", "she", "or", "an", "will",
            "my", "one", "all", "would", "there", "their", "what", "so", "up", "out", "if", "about", "who", "get", "which",
            "go", "me", "when", "make", "can", "like", "time", "no", "just", "him", "know", "take", "people", "into",
            "year", "your", "good", "some", "could", "them", "see", "other", "than", "then", "now", "look", "only",
            "come", "its", "over", "think", "also", "back", "after", "use", "two", "how", "our", "work", "first", "well",
            "way", "even", "new", "want", "because", "any", "these", "give", "day", "most", "us"
    ));

    public static void iniciarAnalisis(String name) {
        Scanner scanner = new Scanner(System.in);
        String textoOriginal = "";

        System.out.println("Haz elegido decoificar un texto sin una clave específica:\n" +
                "El propio programa se encargará de realizar distintas decodificaciones y devolverá el resultado más pertinente.\n" +
                "¿Cómo prefiere subir el texto al programa?");
        System.out.println("1. Escrito en la terminal (Se recomienda para textos de un párrafo)\n" +
                "2. Subirlo con un archivo .txt (Se recomienda si el texto incluye más de un párrafo)\n" +
                "Escriba el número de su elección: ");

        int opcionEntrada;
        while (true) {
            if (scanner.hasNextInt()) {
                opcionEntrada = scanner.nextInt();
                scanner.nextLine();
                if (opcionEntrada == 1 || opcionEntrada == 2) {
                    break;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("\nOpción no válida. Por favor, ingresa 1 o 2.");
        }

        if (opcionEntrada == 1) {
            System.out.println("\nEscribe el texto a analizar:");
            textoOriginal = scanner.nextLine();

            while (textoOriginal.trim().isEmpty()) {
                System.out.println("El texto ingresado está vacío. Por favor, ingresa el texto nuevamente:");
                textoOriginal = scanner.nextLine();
            }
        } else {
            System.out.println("\nEscribe la ruta del archivo (.txt) que deseas analizar:");
            String rutaArchivo = scanner.nextLine();

            boolean archivoValido = false;
            while (!archivoValido) {
                try {
                    textoOriginal = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
                    if (textoOriginal.trim().isEmpty()) {
                        System.out.println("El archivo está vacío. Por favor, ingresa la ruta de un archivo no vacío:");
                    } else {
                        archivoValido = true; // Archivo válido, salir del bucle
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo leer el archivo para análisis: " + e.getMessage() + "\nPor favor, ingresa una ruta válida.");
                }
            }
        }

        int mejorClave = 0;
        int mayorCoincidencias = -1;
        String mejorTexto = "";

        for (int clave = 0; clave <= 25; clave++) {
            String textoDecodificado = decodificarTexto(textoOriginal, clave);
            int coincidencias = contarPalabrasComunes(textoDecodificado);

            if (coincidencias > mayorCoincidencias) {
                mayorCoincidencias = coincidencias;
                mejorClave = clave;
                mejorTexto = textoDecodificado;
            }
        }

        System.out.println("\nClave más probable encontrada: " + mejorClave);
        guardarResultado(mejorTexto, name);
    }

    private static String decodificarTexto(String texto, int clave) {
        StringBuilder resultado = new StringBuilder();
        clave = clave % 26;

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char decodificado = (char) ((c - base - clave + 26) % 26 + base);
                resultado.append(decodificado);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private static int contarPalabrasComunes(String texto) {
        int contador = 0;
        String[] palabras = texto.toLowerCase().split("[^a-záéíóúñü]+");
        for (String palabra : palabras) {
            if (PALABRAS_COMUNES.contains(palabra)) {
                contador++;
            }
        }
        return contador;
    }

    private static void guardarResultado(String texto, String name) {
        Scanner scanner = new Scanner(System.in);
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
            writer.write(texto);
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
                        writer.write(texto);
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
                    System.out.println("2. No");

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
                        System.out.println("Eso fue todo por hoy, hasta luego " + name + "!");
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
                "2. No\n");
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
                System.out.println("Eso fue todo por hoy, hasta luego " + name + "!");
                System.exit(0);
            } else {
                System.out.println("Opción no válida. Escriba 1 o 2.");
            }
        }
    }
}