package org.example.ComParalismo2Thread;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor2 implements Runnable {
    private File arquivo[];
    private String nome;
    private AtomicBoolean encontrado;
    private long tempoInicial;

    public Leitor2(File[] arquivo, String nome, AtomicBoolean encontrado, long tempoInicial) {
        this.arquivo = arquivo;
        this.nome = nome;
        this.encontrado = encontrado;
        this.tempoInicial = tempoInicial;
    }

    @Override
    public void run() {
        try {
            if (!encontrado.get()) {
                for (int i = 0; i < arquivo.length; i++) {
                    Scanner scan = new Scanner(arquivo[i]);
                    int linha = 0;
                    while (scan.hasNextLine()) {
                        linha++;
                        String linhaAtual = scan.nextLine();
                        if (nome.equals(linhaAtual)) {
                            encontrado.set(true);
                            long tempoFinal = System.currentTimeMillis();
                            System.out.println("O método foi executado em " + (tempoFinal - tempoInicial) + "ms");
                            JOptionPane.showMessageDialog(null,
                                    "Nome Encontrado" + "\nArquivo: " + arquivo[i].getName() + "\nLinha: " + linha);

                            break;
                        }
                    }
                    scan.close();
                    }
                }
            } catch(FileNotFoundException e){
                JOptionPane.showConfirmDialog(null, "Arquivo não encontrado");
            }
    }
}

