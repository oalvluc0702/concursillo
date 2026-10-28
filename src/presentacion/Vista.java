package presentacion;

import modelo.Dificultad;
import modelo.Pregunta;
import modelo.Respuesta;

import java.util.Scanner;

public class Vista {
    //ATRIBUTOS
    private Scanner s = new Scanner(System.in);
    //FUNCIONES GENÉRICAS DE VISTA
    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }
    //TE MUESTRA LA PREGUNTA Y SU LISTA DE RESPUESTAS
    public void mostrarPregunta(Pregunta pregunta){
        System.out.println("\n"+ pregunta.getEnunciado());
        int contador = 1;
        for (Respuesta respuesta: pregunta.getRespuestas()){
            System.out.println(contador+"-| "+respuesta.getTexto());
            contador++;
        }
    }
    //PIDE RESPUESTA POR TECLADO
    public int pedirRespuesta(){
        System.out.println("Elige el número de tu respuesta (20 comodin reserva/99 comodin 50/50): ");
        return s.nextInt() - 1;
    }
    //PIDE DIFICULTAD Y DEVUELVE UN OBJETO DEL TIPO DIFICULTAD
    public Dificultad pedirDificultad(){
        System.out.printf("+-----------------------------+%n");
        System.out.printf("|    SELECCIONA DIFICULTAD   |%n");
        System.out.printf("+-----------------------------+%n");
        System.out.printf("| 1) Facil                    |%n");
        System.out.printf("| 2) Medio                    |%n");
        System.out.printf("| 3) Dificil                  |%n");
        System.out.printf("+-----------------------------+%n");
        System.out.printf("Elige una opcion (1-3): ");
        String dificultadSeleccionada = s.nextLine();
        //AQUI SELECCIONA LA DIFICULTAD EN EL ENUM
        switch (dificultadSeleccionada){
            case "1": return Dificultad.FACIL;
            case "2": return Dificultad.MEDIO;
            case "3": return Dificultad.DIFICIL;
            default:
                System.out.println("Opción Inválida, Dificultad Seleccionada por defecto: Facil");
                return Dificultad.FACIL;
        }
    }
    public String pedirTema(){
        System.out.println("Dime el tema que quieres abordar");
        return s.nextLine().toLowerCase();
    }
}
