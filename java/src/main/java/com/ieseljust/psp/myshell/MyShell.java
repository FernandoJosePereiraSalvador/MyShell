/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.ieseljust.psp.myshell;

/**
 *
 * @author Fernando
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * La clase principal del programa
 * @author Fernando
 */
public class MyShell {
    /**
     * Este programa implementa una sencilla shell interactiva llamada MyShell.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        
        // Muestra un mensaje de bienvenida
        System.out.println("Escribe 'quit' para salir.");
        String input;
        
        // Inicia el bucle
        do {
            // Muestra el prompt
            System.out.print("# MyShell> "); 
            input = readInput();
            
            // Si la entrada no es quit ejecuta el comando
            if (!input.equalsIgnoreCase("quit")) {
                executeCommand(input);
            }
            
            // Volvemos al color original
            System.out.print("\u001B[0m");
        } while (!input.equalsIgnoreCase("quit"));
        
        // Muestra un mensaje de despedida
        System.out.println("MyShell se ha cerrado.");
    }
    
    /**
     * 
     * Lee una línea de entrada del usuario desde la consola.
     * 
     * @return La línea de entrada del usuario como un String.
     */
    private static String readInput() {
        try {
            // Crea un lector de entrada para leer desde la consola
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            // Lee una línea de texto ingresada por el usuario
            return reader.readLine();
        } catch (IOException e) {
            // // En caso de error de entrada/salida, imprime la traza de excepción y devuelve una cadena vacía
            System.out.println(e);
            return "";
        }
    }
    
    /**
     * Ejecuta un comando ingresado por el usuario en la shell.
     * 
     * @param command El comando a ejecutar.
     */
    private static void executeCommand(String command) {
        try {
            
            // Determina el nombre del sistema operativo actual
            String osName = System.getProperty("os.name").toLowerCase();
            String finalCommand;
            
            // Construye el comando final dependiendo del sistema operativo
            if (osName.contains("win")) {
                // En Windows, usa cmd /c antes del comando
                finalCommand = "cmd /c " + command;
            } else {
                // En otros sistemas, usa el comando directamente
                finalCommand = command;
            }
            
            // Crea un proceso para ejecutar el comando
            ProcessBuilder processBuilder = new ProcessBuilder(finalCommand.split("\\s+"));
            Process process = processBuilder.start();
            
            // Espera a que el proceso termine y obtiene su código de salida
            int exitCode = process.waitFor();
            
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            
            // Imprimimos el resultado de la ejecucion
            String line;
            while ((line = stdoutReader.readLine()) != null) {
                System.out.println("\u001B[32m" + line); // Verde
            }
            
            // Imprimimos el resultado si ha habido algun problema en rojo
            while ((line = stderrReader.readLine()) != null) {
                System.out.println("\u001B[31m" + line); // Rojo
            }
            
            // Muestra el código de salida del proceso (0 es que no ha habido ningun error)
            System.out.println("\u001B[0m Código de salida: " + exitCode); // Restaurar color original (gris)
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
