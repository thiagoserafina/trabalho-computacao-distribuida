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

            int indice = buscaBinaria(nomesOrdenados, nome);
            if (indice != -1) {
                tempoFinal = System.currentTimeMillis();
                int linha = pegarLinha(nome ,arquivos[i].getPath());
                JOptionPane.showMessageDialog(null,
                        "Arquivo: " + arquivos[i].getName() + "\nLinha: " + linha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                        "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                break;
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
                return meio;
            } else if (comparacao < 0) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }
        }
        return -1;
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