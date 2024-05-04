package org.example.ComParalelismo;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComParalelismo {
    private static final AtomicBoolean encontrado = new AtomicBoolean(false);

    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

        if (nome == null || nome.isEmpty()) {
            System.exit(0);
        }

        File[] arquivos;
        File diretorio = new File("arquivoTXT");
        arquivos = diretorio.listFiles();

        long tempoInicial = System.currentTimeMillis();

        assert arquivos != null;
        Thread[] threads = new Thread[arquivos.length];

        for (int i = 0; i < arquivos.length; i++) {
            final File arquivo = arquivos[i];
            threads[i] = new Thread(new Leitor(arquivo, nome, encontrado, tempoInicial));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

        for (Thread thread : threads) {
            if (thread.isAlive()) {
                System.out.println("Thread " + thread.getName() + " não parou");
                thread.interrupt();
            }
        }

        if (!encontrado.get()) {
            JOptionPane.showMessageDialog(null, "Nome não encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}