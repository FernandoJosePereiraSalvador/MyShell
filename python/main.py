import os
import subprocess

def read_input():
    try:
        return input()
    except KeyboardInterrupt:
        return "quit"

def execute_command(command):
    try:
        if os.name == "nt":
            # En Windows, usa cmd.exe para ejecutar comandos internos
            os.system(command)
        else:
            # En otros sistemas, divide el comando por tuberías (|)
            process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
            stdout, stderr = process.communicate()

            for line in stdout.decode().splitlines():
                print("\x1b[32m" + line)  # Verde

            for line in stderr.decode().splitlines():
                print("\x1b[31m" + line)  # Rojo

            print("\x1b[0m Código de salida:", process.returncode)

    except Exception as e:
        print(e)

def main():
    print("Escribe 'quit' para salir.")
    input_text = ""

    while input_text.lower() != "quit":
        try:
            input_text = input("# MyShell2> ")
            if input_text.lower() != "quit":
                execute_command(input_text)
        except KeyboardInterrupt:
            print("\nMyShell2 se ha cerrado.")
            break

if __name__ == "__main__":
    main()

