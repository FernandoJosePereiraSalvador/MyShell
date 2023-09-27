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

public class MyShell2 {
    public static void main(String[] args) {
        System.out.println("Escribe 'quit' para salir.");
        String input;

        do {
            System.out.print("# MyShell2> "); // Cambiamos el nombre de la shell
            input = readInput();

            if (!input.equalsIgnoreCase("quit")) {
                executeCommand(input);
            }

            System.out.print("\u001B[0m"); // Restaurar color original (gris)
        } while (!input.equalsIgnoreCase("quit"));

        System.out.println("MyShell2 se ha cerrado.");
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
            String[] commands;

            if (osName.contains("win")) {
                // En Windows, usa cmd /c antes del comando
                commands = new String[]{"cmd", "/c", command};
            } else {
                // En otros sistemas, divide el comando por tuberías (|)
                commands = command.split("\\|");
            }

            ProcessBuilder processBuilder = new ProcessBuilder(commands);
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

            System.out.println("\u001B[0m Código de salida: " + exitCode); 
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
