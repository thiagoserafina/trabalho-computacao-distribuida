package org.example.ParalelismoDivideMetade;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParalelismoDivideMetade {
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
        int totalLines = 10000;
        int midPoint = totalLines / 2;

        assert arquivos != null;
        Thread[] threads = new Thread[arquivos.length * 2];
        int j = 0;

        for (int i = 0; i < arquivos.length; i++) {
            final File arquivo = arquivos[i];
            threads[j] = new Leitor(arquivo, nome, encontrado, tempoInicial, 1, midPoint);
            threads[j+1] = new Leitor(arquivo, nome, encontrado, tempoInicial, midPoint + 1, totalLines);
            threads[j].start();
            threads[j+1].start();
            j += 2;
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