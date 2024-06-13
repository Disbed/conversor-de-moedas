package modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeradorDeArquivo {
    public void salvarJson(double moeda, String valor_origem, String valor_destino) throws IOException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataAtual = LocalDateTime.now();
//        System.out.println(dataAtual.format(fmt));
        Dados dados = new Dados(dataAtual.format(fmt), moeda, valor_origem, valor_destino);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String diretorio = "historico";
        File dir = new File(diretorio);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String caminhoArquivo = diretorio + "/" + moeda + ".json";

        try (FileWriter escrita = new FileWriter(caminhoArquivo)) {
            gson.toJson(dados, escrita);
            System.out.println("Salvo em histórico! Será atualizado assim que finalizar o programa.");
        }
    }

    public class Dados {
        private String data;
        private double moeda;
        private String valor_origem;
        private String valor_destino;

        public String getData() {
            return data;
        }

        public double getMoeda() {
            return moeda;
        }

        public String getValor_origem() {
            return valor_origem;
        }


        public String getValor_destino() {
            return valor_destino;
        }

        public Dados(String data, double moeda, String valor_origem, String valor_destino) {
            this.data = data;
            this.moeda = moeda;
            this.valor_origem = valor_origem;
            this.valor_destino = valor_destino;
        }
    }
}
