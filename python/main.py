import os
import subprocess


def read_input():
    """
    Lee la entrada del usuario desde la consola.

    :return: La entrada del usuario
    """
    try:
        return input()
    except KeyboardInterrupt:
        return "quit"


def execute_command(command):
    """
    Ejecuta el comando proporcionado por el usaurio

    :param command: El comando a ejecutar.
    :return: None
    """
    try:
        if os.name == "nt":
            # En Windows, usa cmd.exe para ejecutar comandos internos
            os.system(command)
        else:
            # En otros sistemas, divide el comando por tuberías (|)
            process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
            stdout, stderr = process.communicate()

            # Imprime la salida estándar en verde
            for line in stdout.decode().splitlines():
                print("\x1b[32m" + line)  # Verde

            # Imprime la salida de error en rojo
            for line in stderr.decode().splitlines():
                print("\x1b[31m" + line)  # Rojo

            # Imprime el código de salida
            print("\x1b[0m Código de salida:", process.returncode)

    except Exception as e:
        # Imprime una excepcion
        print(e)


def main():
    """
    Función principal que inicia el programa..
    :return: None
    """

    # Muestra un mensaje de bienvenida
    print("Escribe 'quit' para salir.")
    input_text = ""

    # Continuar hasta que se ingrese "quit" o se cierre el programa
    while input_text.lower() != "quit":
        try:
            # Espera la entrada del usuario
            input_text = input("# MyShell2> ")
            # Ejecuta el comando proporcionado por el usuario
            if input_text.lower() != "quit":
                execute_command(input_text)
        except KeyboardInterrupt:
            # Muestra un mensaje de cierre
            print("\nMyShell2 se ha cerrado.")
            break

# Inicia la ejecución del programa
if __name__ == "__main__":
    main()
