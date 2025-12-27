package modelo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.List;

public class CargadorPreguntas {

    public static List<Pregunta> cargar(String ruta) throws Exception{
        Gson gson = new Gson();

        return gson.fromJson(
                new FileReader(ruta),
                new TypeToken<List<Pregunta>>(){}.getType()
                );
    }
}
