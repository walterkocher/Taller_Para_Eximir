package com.example.walter;
import java.util.Scanner;

public class Ev2_wkg {

    // Datos proporcionados (1999 a 2020)
    static String[] meses = {
            "diciembre", "enero", "febrero",
            "diciembre", "enero", "febrero",
            "diciembre", "enero", "febrero"
    };
    static int[] años = { 1999, 2000, 2000, 2009, 2010, 2010, 2019, 2020, 2020 };
    static double[] temperaturaMaxMedia = { 23.3, 24, 22.9, 20.3, 22.7, 22, 23.6, 26.3, 27 };
    static double[] temperaturaMinMedia = { 8, 8.7, 10.1, 8, 7.9, 8.3, 7, 8.7, 7.6 };
    static double[] temperaturaMaxMes = { 28.8, 28.4, 32.2, 24, 28.6, 31.9, 28.9, 32.9, 37.5 };
    static double[] temperaturaMinMes = { 1.6, 4, 4, 3.5, 2.2, 2.2, 1.4, 2.7, 2.9 };
    static double[] precipitacionTotal = { 10.2, 5, 129.2, 61.8, 35.4, 70, 14.8, 16, 23.2 };
    static double[] precipitacionMax = { 4.2, 2.1, 27.2, 19.4, 24.6, 27.6, 7.4, 12.7, 18.5 };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el año (entre 1990 y 2022): ");
        int añoUsuario = scanner.nextInt();

        if (añoUsuario < 1990 || añoUsuario > 2022) {
            System.out.println("El año debe estar entre 1990 y 2022.");
            return;
        }

        System.out.print("Ingrese el mes (en minúsculas, ej. 'enero'): ");
        String mesUsuario = scanner.next();

        scanner.close();

        double temperaturaMaxMediaPredicha;
        double temperaturaMinMediaPredicha;
        double temperaturaMaxMesPredicha;
        double temperaturaMinMesPredicha;
        double precipitacionTotalPredicha;
        double precipitacionMaxPredicha;


        if (añoUsuario >= 1999 && añoUsuario <= 2020) {

            // Realizar interpolación lineal para todas las variables
            temperaturaMaxMediaPredicha = calcularInterpolacion(temperaturaMaxMedia, añoUsuario);
            temperaturaMinMediaPredicha = calcularInterpolacion(temperaturaMinMedia, añoUsuario);
            temperaturaMaxMesPredicha = calcularInterpolacion(temperaturaMaxMes, añoUsuario);
            temperaturaMinMesPredicha = calcularInterpolacion(temperaturaMinMes, añoUsuario);
            precipitacionTotalPredicha = calcularInterpolacion(precipitacionTotal, añoUsuario);
            precipitacionMaxPredicha = calcularInterpolacion(precipitacionMax, añoUsuario);


        } else {
            if (añoUsuario < 1999) {
                // Extrapolación lineal cuando el año es menor a 1999
                int añoInicio = 1990;
                int añoFin = 1999;
                double fraccionAñoAnterior = (double) (añoUsuario - añoInicio) / (añoFin - añoInicio);
                double fraccionAñoSiguiente = 1 - fraccionAñoAnterior;

                temperaturaMaxMediaPredicha = fraccionAñoSiguiente * temperaturaMaxMedia[0] +
                        fraccionAñoAnterior * temperaturaMaxMedia[8];
                temperaturaMinMediaPredicha = fraccionAñoSiguiente * temperaturaMinMedia[0] +
                        fraccionAñoAnterior * temperaturaMinMedia[8];
                temperaturaMaxMesPredicha = fraccionAñoSiguiente * temperaturaMaxMes[0] +
                        fraccionAñoAnterior * temperaturaMaxMes[8];
                temperaturaMinMesPredicha = fraccionAñoSiguiente * temperaturaMinMes[0] +
                        fraccionAñoAnterior * temperaturaMinMes[8];
                precipitacionTotalPredicha = fraccionAñoSiguiente * precipitacionTotal[0] +
                        fraccionAñoAnterior * precipitacionTotal[8];
                precipitacionMaxPredicha = fraccionAñoSiguiente * precipitacionMax[0] +
                        fraccionAñoAnterior * precipitacionMax[8];
            } else if (añoUsuario > 2020) {
                // Extrapolación lineal cuando el año es mayor a 2020
                int añoInicio = 2020;
                int añoFin = 2022;
                double fraccionAñoAnterior = (double) (añoUsuario - añoInicio) / (añoFin - añoInicio);
                double fraccionAñoSiguiente = 1 - fraccionAñoAnterior;

                temperaturaMaxMediaPredicha = fraccionAñoSiguiente * temperaturaMaxMedia[8] +
                        fraccionAñoAnterior * temperaturaMaxMedia[7];
                temperaturaMinMediaPredicha = fraccionAñoSiguiente * temperaturaMinMedia[8] +
                        fraccionAñoAnterior * temperaturaMinMedia[7];
                temperaturaMaxMesPredicha = fraccionAñoSiguiente * temperaturaMaxMes[8] +
                        fraccionAñoAnterior * temperaturaMaxMes[7];
                temperaturaMinMesPredicha = fraccionAñoSiguiente * temperaturaMinMes[8] +
                        fraccionAñoAnterior * temperaturaMinMes[7];
                precipitacionTotalPredicha = fraccionAñoSiguiente * precipitacionTotal[8] +
                        fraccionAñoAnterior * precipitacionTotal[7];
                precipitacionMaxPredicha = fraccionAñoSiguiente * precipitacionMax[8] +
                        fraccionAñoAnterior * precipitacionMax[7];
            } else {
                System.out.println("No se encontraron datos para el año y mes ingresados.");
                return;
            }
        }

        System.out.println("Predicciones para " + mesUsuario + " " + añoUsuario + ":");
        System.out.println("Temperatura media máxima: " + temperaturaMaxMediaPredicha);
        System.out.println("Temperatura media mínima: " + temperaturaMinMediaPredicha);
        System.out.println("Temperatura máxima del mes: " + temperaturaMaxMesPredicha);
        System.out.println("Temperatura mínima del mes: " + temperaturaMinMesPredicha);
        System.out.println("Precipitación total: " + precipitacionTotalPredicha);
        System.out.println("Precipitación máxima: " + precipitacionMaxPredicha);
    }

    // Función para calcular interpolación lineal entre los años 1999 y 2020
    static double calcularInterpolacion(double[] datos, int añoUsuario) {
        int indiceAnterior = -1;
        int indiceSiguiente = -1;

        for (int i = 0; i < años.length; i++) {
            if (años[i] == añoUsuario) {
                return datos[i]; // Devuelve el valor exacto si se encuentra en los datos originales.
            } else if (años[i] < añoUsuario) {
                indiceAnterior = i;
            } else if (años[i] > añoUsuario) {
                indiceSiguiente = i;
                break;
            }
        }

        if (indiceAnterior == -1 || indiceSiguiente == -1) {
            return -1; // No se pueden realizar cálculos de interpolación.
        }

        double fraccionAnterior = (double) (añoUsuario - años[indiceAnterior]) / (años[indiceSiguiente] - años[indiceAnterior]);
        double fraccionSiguiente = 1 - fraccionAnterior;

        return fraccionSiguiente * datos[indiceAnterior] + fraccionAnterior * datos[indiceSiguiente];
    }
}

