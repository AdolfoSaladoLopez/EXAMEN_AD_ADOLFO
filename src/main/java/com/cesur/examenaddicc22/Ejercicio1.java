package com.cesur.examenaddicc22;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class Ejercicio1 {

    /**
     * Enunciado:
     * <p>
     * Completar el método estadísticasDeArchivo de manera que lea el archivo
     * de texto que se le pasa como parámetro, lo analice y muestre por consola
     * el número de caracteres, palabras y líneas total.
     * <p>
     * Modificar solo el código del método.
     */

    static void solucion() {

        estadísticasDeArchivo("pom.xml");
    }

    private static void estadísticasDeArchivo(String archivo) {

        /* añadir código */

        Integer lineas = 0;
        Integer palabras = 0;
        Integer caracteres = 0;

        /* Cuenta los caracteres del fichero */
        try {
            FileReader lectorCaracteres = new FileReader(archivo);
            while (lectorCaracteres.read() != -1) {
                caracteres += 1;

                lectorCaracteres.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* Cuenta las líneas del fichero y las palabras por cada línea */
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(archivo));
            Scanner sc;

            while (bfr.readLine() != null) {
                sc = new Scanner(bfr.readLine());

                StringTokenizer st = new StringTokenizer(sc.nextLine(), " ");

                palabras += st.countTokens();

                lineas += 1;

                bfr.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (lineas != 0) {
            System.out.println("Estadísticas de POM: ");
            System.out.println(lineas + " lineas.");
            System.out.println(caracteres + " caracteres.");
            System.out.println(palabras + " palabras.");
        } else {
            System.out.println("Ejercicio no resuelto");

        }
    }

}
