import controlador.Juego;
import modelo.CargadorPreguntas;
import modelo.Pregunta;
import presentacion.Vista;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Pregunta> preguntas = CargadorPreguntas.cargar("recursos/Pregunta.json");
        Vista vista = new Vista();
        Juego juego = new Juego(preguntas, vista);
        juego.IniciarJuego();
    }
}