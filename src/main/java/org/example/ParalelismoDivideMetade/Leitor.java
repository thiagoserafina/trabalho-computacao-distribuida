package org.example.ParalelismoDivideMetade;

import javax.swing.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor implements Runnable {
    private final File arquivo;
    private final String nome;
    private final AtomicBoolean encontrado;
    private final long tempoInicial;
    private int startLine;
    private final int endLine;

    public Leitor(File arquivo, String nome, AtomicBoolean encontrado, long tempoInicial, int startLine, int endLine) {
        this.arquivo = arquivo;
        this.nome = nome;
        this.encontrado = encontrado;
        this.tempoInicial = tempoInicial;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            int numeroLinha = startLine - 1;
            String linhaAtual;

            for (int i = 1; i < startLine; i++) {
                linhaAtual = reader.readLine();
                if (linhaAtual == null) {
                    System.out.println("Arquivo não possui " + startLine + " linhas");
                    return;
                }
            }

            while ((linhaAtual = reader.readLine()) != null && !encontrado.get() && startLine <= endLine) {
                numeroLinha++;

                if (nome.equals(linhaAtual)) {
                    encontrado.set(true);
                    long tempoFinal = System.currentTimeMillis();
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(null,
                            "Arquivo: " + arquivo.getName() + "\nLinha: " + numeroLinha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                            "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                startLine++;
            }

        } catch (RuntimeException | IOException e) {
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
        }
    }
}