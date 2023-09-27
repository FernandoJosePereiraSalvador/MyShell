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

public class MyShell {
    public static void main(String[] args) {
        System.out.println("Escribe 'quit' para salir.");
        String input;

        do {
            System.out.print("# MyShell> "); // Verde
            input = readInput();

            if (!input.equalsIgnoreCase("quit")) {
                executeCommand(input);
            }

            System.out.print("\u001B[0m"); // Restaurar color original (gris)
        } while (!input.equalsIgnoreCase("quit"));

        System.out.println("MyShell se ha cerrado.");
    }

    private static String readInput() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void executeCommand(String command) {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String finalCommand;

            if (osName.contains("win")) {
                // En Windows, usa cmd /c antes del comando
                finalCommand = "cmd /c " + command;
            } else {
                // En otros sistemas, usa el comando directamente
                finalCommand = command;
            }

            ProcessBuilder processBuilder = new ProcessBuilder(finalCommand.split("\\s+"));
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = stdoutReader.readLine()) != null) {
                System.out.println("\u001B[32m" + line); // Verde
            }

            while ((line = stderrReader.readLine()) != null) {
                System.out.println("\u001B[31m" + line); // Rojo
            }

            System.out.println("\u001B[0mCódigo de salida: " + exitCode); // Restaurar color original (gris)
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
