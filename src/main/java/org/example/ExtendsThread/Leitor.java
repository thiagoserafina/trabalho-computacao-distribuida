package org.example.ExtendsThread;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor extends Thread {
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

            while ((linha = reader.readLine()) != null) {
                if (linha.equals(nome)) {
                    encontrado.set(true);
                    long tempoFinal = System.currentTimeMillis();
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(null,
                            "Arquivo: " + arquivo.getName() + "\nLinha: " + linha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
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

