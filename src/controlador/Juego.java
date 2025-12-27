package controlador;

import modelo.Dificultad;
import modelo.Pregunta;
import modelo.Respuesta;
import presentacion.Vista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego {
    private List<Pregunta> preguntas;
    private Vista vista;

    public Juego(List<Pregunta> preguntas, Vista vista) {
        this.preguntas = preguntas;
        this.vista = vista;
    }
    //AQUI FILTRA LAS PREGUNTAS POR TEMA Y DIFICULTAD, SE LE PASAN 2 PARAMETROS UN OBJETO DIFICULTAD Y UN STRING QUE DIGA EL TEMA, EL TEMA PASADO NO DEBE TENER MAYUSCULAS
    private List<Pregunta> filtrarPorDificultadYTema(Dificultad dificultad, String tema){
        List<Pregunta> filtradas = new ArrayList<>();
        for (Pregunta pregunta: this.preguntas){
            if (pregunta.getDificultad().equals(dificultad) && pregunta.getTema().equals(tema)){
                filtradas.add(pregunta);
            }
        }
        return filtradas;
    }
    private List<Respuesta> cincuentaCincuenta(Pregunta pregunta){
        //Separar incorrectas
        List<Respuesta> incorrectas = pregunta.getRespuestas().stream()
                .filter(r -> !r.isCorrecta())
                .toList();

        //Barajar
        List<Respuesta> barajadas = new ArrayList<>(incorrectas);
        Collections.shuffle(barajadas);

        //Tomar 2 a eliminar (o menos si no hay suficientes)
        int aEliminar = Math.min(2, barajadas.size());
        List<Respuesta> eliminar = barajadas.subList(0, aEliminar);

        //Eliminar del set original
        pregunta.getRespuestas().removeAll(eliminar);

        //Devolver lista restante
        return new ArrayList<>(pregunta.getRespuestas());
    }

    public void IniciarJuego() {

        vista.mostrarMensaje("Concursillo de Gorka");

        Dificultad dificultad = vista.pedirDificultad();
        String tema = vista.pedirTema();

        List<Pregunta> preguntasFiltradas = filtrarPorDificultadYTema(dificultad, tema);

        int puntuacion = 0;
        boolean reservaUsada = false;

        for (Pregunta pregunta : preguntasFiltradas) {

            if (pregunta.getReserva()) continue;

            vista.mostrarPregunta(pregunta);
            int respuestaSeleccionada = vista.pedirRespuesta();

            // COMODÍN RESERVA
            if (respuestaSeleccionada == 19 && !reservaUsada) {

                vista.mostrarMensaje("Has seleccionado el comodín de la pregunta de reserva");

                for (Pregunta preguntaReserva : preguntasFiltradas) {
                    if (preguntaReserva.getReserva()) {

                        reservaUsada = true;
                        preguntaReserva.setReserva(false);

                        vista.mostrarPregunta(preguntaReserva);
                        respuestaSeleccionada = vista.pedirRespuesta();

                        if (respuestaSeleccionada >= 0 && respuestaSeleccionada < preguntaReserva.getRespuestas().size()
                                && preguntaReserva.getRespuestas().get(respuestaSeleccionada).isCorrecta()) {

                            vista.mostrarMensaje("Felicidades es correcta!");
                            puntuacion++;
                        } else {
                            vista.mostrarMensaje("No era correcta, Has perdido...");
                            return;
                        }
                    }
                }
                continue;
            }

            // COMODÍN 50/50
            if (respuestaSeleccionada == 98) {

                pregunta.setRespuestas(cincuentaCincuenta(pregunta));
                vista.mostrarPregunta(pregunta);
                respuestaSeleccionada = vista.pedirRespuesta();
            }

            // RESPUESTA NORMAL O DESPUÉS DE 50/50
            if (respuestaSeleccionada >= 0 && respuestaSeleccionada < pregunta.getRespuestas().size()
                    && pregunta.getRespuestas().get(respuestaSeleccionada).isCorrecta()) {

                vista.mostrarMensaje("Felicidades es correcta!");
                puntuacion++;
            } else {
                vista.mostrarMensaje("No era correcta, Has perdido...");
                return;
            }
        }

        vista.mostrarMensaje("Juego terminado. Puntuación: " + puntuacion);
    }

}
