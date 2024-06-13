package modelos;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conversor {
    public tipoMoeda converterMoeda(String valor1, String valor2, double moeda){
        String minhaKey = "MINHA_CHAVE";
        URI endereco = URI.create("https://v6.exchangerate-api.com/v6/"+
                minhaKey+"/pair/"+valor1+"/"+valor2+"?amount="+moeda);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(endereco)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Não consegui converter os valores :/");
        }

        return new Gson().fromJson(response.body(),tipoMoeda.class);

    }
}
