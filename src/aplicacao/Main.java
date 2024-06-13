package aplicacao;

import com.google.gson.Gson;
import modelos.Conversor;
import modelos.GeradorDeArquivo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Conversor conversor = new Conversor();
        GeradorDeArquivo gerador = new GeradorDeArquivo();

        while (true) {
            System.out.println("--------------------------------------------");
            System.out.println("Seja bem-vindo ao Conversor de Moeda");
            System.out.println("1-) Dólar => Peso argentino");
            System.out.println("2-) Peso argentino => Dólar");
            System.out.println("3-) Dólar => Real brasileiro");
            System.out.println("4-) Real brasileiro => Dólar");
            System.out.println("5-) Dólar => Peso colombiano");
            System.out.println("6-) Peso colombiano => Dólar");
            System.out.println("7-) Histórico de buscas");
            System.out.println("8-) Sair");
            System.out.println("Escolha uma opção: ");
            System.out.println("--------------------------------------------");
            int op = sc.nextInt();

            if (op >= 1 && op <= 6) {
                try {
                    System.out.println("Digite um valor que deseja converter: ");
                    double valor = sc.nextDouble();

                    String moedaOrigem = "";
                    String moedaDestino = "";

                    switch (op) {
                        case 1:
                            moedaOrigem = "USD";
                            moedaDestino = "ARS";
                            break;
                        case 2:
                            moedaOrigem = "ARS";
                            moedaDestino = "USD";
                            break;
                        case 3:
                            moedaOrigem = "USD";
                            moedaDestino = "BRL";
                            break;
                        case 4:
                            moedaOrigem = "BRL";
                            moedaDestino = "USD";
                            break;
                        case 5:
                            moedaOrigem = "USD";
                            moedaDestino = "COP";
                            break;
                        case 6:
                            moedaOrigem = "COP";
                            moedaDestino = "USD";
                            break;
                    }

                    double valorConvertido = conversor.converterMoeda(moedaOrigem, moedaDestino, valor).
                            conversion_result();
                    System.out.printf("O valor %.3f %s corresponde ao valor final de %.3f %s%n",
                            valor, moedaOrigem, valorConvertido, moedaDestino);

                    gerador.salvarJson(valorConvertido, moedaOrigem, moedaDestino);
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, insira um valor válido.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (op == 7) {
                try {
                    exibirHistorico();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (op == 8) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida! Saindo...");
                break;
            }
        }
    }

    private static void exibirHistorico() throws IOException {
        File diretorio = new File("historico");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".json"));

        if (arquivos != null && arquivos.length > 0) {
            for (File arquivo : arquivos) {
                try (FileReader reader = new FileReader(arquivo)) {
                    GeradorDeArquivo.Dados dados = new Gson().fromJson(reader, GeradorDeArquivo.Dados.class);
                    System.out.printf("Data: %s, Valor: %.5f %s => %s%n",
                            dados.getData(), dados.getMoeda(), dados.getValor_origem(), dados.getValor_destino());
                }
            }
        } else {
            System.out.println("Não há histórico de buscas.");
        }

    }
}