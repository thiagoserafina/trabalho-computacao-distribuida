package org.example.ComParalelismo;

import javax.swing.*;
import java.io.*;
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
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int numeroLinha = 1;

            while ((linha = reader.readLine()) != null && !encontrado.get()) {
                if (linha.equals(nome)) {
                    encontrado.set(true);
                    long tempoFinal = System.currentTimeMillis();
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(null,
                            "Arquivo: " + arquivo.getName() + "\nLinha: " + numeroLinha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                            "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                numeroLinha++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + arquivo.getName());
            e.printStackTrace();
        }
    }
}

