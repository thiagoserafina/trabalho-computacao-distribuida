package util.Threads;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor implements Runnable {
    private final File arquivo;
    private final String nome;
    private final AtomicBoolean encontrado;
    private final long tempoInicial;

    public Leitor(File arquivo, String nome, AtomicBoolean encontrado, long tempoInicial) {
        this.arquivo = arquivo;
        this.nome = nome;
        this.encontrado = encontrado;
        this.tempoInicial = tempoInicial;
    }

    @Override
    public void run() {
        try (Scanner scan = new Scanner(arquivo)) {
            int linha = 0;

            while (scan.hasNextLine() && !encontrado.get()) {
                linha++;
                String linhaAtual = scan.nextLine();

                if (nome.equals(linhaAtual)) {
                    encontrado.set(true);
                    long tempoFinal = System.currentTimeMillis();
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(null,
                            "Arquivo: " + arquivo.getName() + "\nLinha: " + linha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                            "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (FileNotFoundException | RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
        }
    }
}

