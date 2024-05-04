package org.example.ComParalelismo;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor implements Runnable {
    private File arquivo;
    private String nome;
    private AtomicBoolean encontrado;
    private long tempoInicial;

    public Leitor(File arquivo, String nome, AtomicBoolean encontrado, long tempoInicial) {
        this.arquivo = arquivo;
        this.nome = nome;
        this.encontrado = encontrado;
        this.tempoInicial = tempoInicial;
    }

    @Override
    public void run() {
        try {
            if (!encontrado.get()) {
                Scanner scan = new Scanner(arquivo);
                int linha = 0;
                while (scan.hasNextLine()) {
                    linha++;
                    String linhaAtual = scan.nextLine();
                    if (nome.equals(linhaAtual)) {
                        encontrado.set(true);
                        long tempoFinal = System.currentTimeMillis();
                        System.out.println("O método foi executado em " + (tempoFinal - tempoInicial) + "ms");
                        JOptionPane.showMessageDialog(null,
                                "Nome Encontrado" + "\nArquivo: " + arquivo.getName() + "\nLinha: " + linha);

                        break;
                    }
                }
                scan.close();
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Arquivo não encontrado");
        }
    }
}

