package org.example.BuscaBinaria.SemParalelismo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SemParalelismo {

    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

        if (nome == null || nome.isEmpty()) {
            System.exit(0);
        }

        File[] arquivos;
        File diretorio = new File("arquivoTXT");
        arquivos = diretorio.listFiles();

        long tempoInicial = System.currentTimeMillis();
        long tempoFinal;

        for(int i = 0; i < arquivos.length; i++){
            List<String> nomesOrdenados = lerNomesEOrdenar(arquivos[i].getPath());
            System.out.println(nomesOrdenados.get(4999));
            // Verifica se a lista foi ordenada com sucesso
            if (nomesOrdenados != null) {
                // Realiza a busca binária
                int indice = buscaBinaria(nomesOrdenados, nome);
                // Verifica se o nome foi encontrado
                if (indice != -1) {
                    tempoFinal = System.currentTimeMillis();
                    int linha = pegarLinha(nome ,arquivos[i].getPath());
                    JOptionPane.showMessageDialog(null,
                            "Arquivo: " + arquivos[i].getName() + "\nLinha: " + linha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                            "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            } else {
                System.out.println("Erro ao ler os nomes do arquivo.");
            }
        }
    }

    private static List<String> lerNomesEOrdenar(String nomeArquivo) {
        List<String> nomes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                nomes.add(linha);
            }
            // Ordena os nomes
            Collections.sort(nomes);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return null;
        }
        return nomes;
    }

    private static int buscaBinaria(List<String> nomes, String nomeBuscado) {
        int inicio = 0;
        int fim = nomes.size() - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = nomeBuscado.compareTo(nomes.get(meio));

            if (comparacao == 0) {
                return meio; // Nome encontrado
            } else if (comparacao < 0) {
                fim = meio - 1; // Procurar na metade inferior
            } else {
                inicio = meio + 1; // Procurar na metade superior
            }
        }
        return -1; // Nome não encontrado
    }

    private static int pegarLinha(String nome, String nomeArquivo) {
        int linha = 0;
        try (Scanner sc = new Scanner(new File(nomeArquivo))) {
            while (sc.hasNextLine()) {
                linha++;
                if (sc.nextLine().equals(nome)) {
                    return linha;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return -1;
    }
}