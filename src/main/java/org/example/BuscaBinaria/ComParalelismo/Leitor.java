package org.example.BuscaBinaria.ComParalelismo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor implements Runnable {
    private File arquivo;
    private String nome;
    private AtomicBoolean encontrado;
    private long tempoInicial;

    public Leitor(File arquivo, String nome, AtomicBoolean encontrado, long tempoinicial) {
        this.arquivo = arquivo;
        this.nome = nome;
        this.encontrado = encontrado;
        this.tempoInicial = tempoinicial;
    }

    @Override
    public void run() {
        List<String> nomesOrdenados = lerNomesEOrdenar(arquivo.getPath());
        if (nomesOrdenados != null) {
            int inicio = 0;
            int fim = nomesOrdenados.size() - 1;
            while (inicio <= fim && !encontrado.get()) {
                int meio = (inicio + fim) / 2;
                int comparacao = nome.compareTo(nomesOrdenados.get(meio));

                if (comparacao == 0) {
                    long tempoFinal = System.currentTimeMillis();
                    int linha = pegarLinha(nome, arquivo.getPath());
                    encontrado.set(true);
                    JOptionPane.showMessageDialog(null,
                            "Arquivo: " + arquivo.getName() + "\nLinha: " + linha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                            "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                    break;
                } else if (comparacao < 0) {
                    fim = meio - 1;
                } else {
                    inicio = meio + 1;
                }
            }
        } else {
            System.out.println("Erro ao ler os nomes do arquivo.");
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


