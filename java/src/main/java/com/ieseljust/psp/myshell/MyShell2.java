/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.ieseljust.psp.myshell;

/**
 *
 * @author Fernando
 */

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * La clase MyShell2 es una variante de la shell interactiva que permite al usuario usar tuberías
 * 
 * @author Fernando
 */
public class MyShell2 {
    /**
     * El método principal
     * @param args Los argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        // Muestra un mensaje de bienvenida
        System.out.println("Escribe 'quit' para salir.");
        String input;
        
        // Inicia el bucle
        do {
            // Muestra el prompt
            System.out.print("# MyShell2> "); // Cambiamos el nombre de la shell
            input = readInput();
            
            // Si la entrada no es "quit", ejecuta el comando
            if (!input.equalsIgnoreCase("quit")) {
                executeCommand(input);
            }
            
            // Si la entrada no es "quit", ejecuta el comando
            System.out.print("\u001B[0m"); // Gris
        } while (!input.equalsIgnoreCase("quit"));
        
        // Si la entrada no es "quit", ejecuta el comando
        System.out.println("MyShell2 se ha cerrado.");
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
            System.out.println(e);;
            return "";
        }
    }
    
    /**
     * Ejecuta un comando ingresado por el usuario en la shell.
     * 
     * @param command El comando a ejecutar
     */
     private static void executeCommand(String command) {
        try {
            // Determina el nombre del sistema operativo actual
            String osName = System.getProperty("os.name").toLowerCase();
            
            // Dividir el comando en comandos individuales usando el carácter de tubería "|"
            String[] commands = command.split("\\|");

            // Crear una lista de ProcessBuilder para cada comando
            List<ProcessBuilder> builders = new ArrayList<>();
            for (String cmd : commands) {
                // Eliminar espacios en blanco alrededor del comando
                cmd = cmd.trim();
                
                if (osName.contains("win")) {
                    // En Windows, usa cmd /c antes del comando
                    builders.add(new ProcessBuilder("cmd", "/c", cmd));
                } else {
                    builders.add(new ProcessBuilder(cmd.split("\\s+")));
                }
            }

            // Crear una tubería de procesos
            List<Process> processes = ProcessBuilder.startPipeline(builders);

            // Obtener el último proceso en la tubería
            Process lastProcess = processes.get(processes.size() - 1);

            // Obtener la salida estándar y de error del último proceso
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(lastProcess.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(lastProcess.getErrorStream()));

            // Imprimir la salida estándar en verde
            String line;
            while ((line = stdoutReader.readLine()) != null) {
                System.out.println("\u001B[32m" + line); // Verde
            }

            // Imprimir la salida de error en rojo
            while ((line = stderrReader.readLine()) != null) {
                System.out.println("\u001B[31m" + line); // Rojo
            }

            // Esperar a que el último proceso termine y obtener su código de salida
            int exitCode = lastProcess.waitFor();

            // Mostrar el código de salida del proceso (0 significa que no hubo errores)
            System.out.println("\u001B[0m Código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
