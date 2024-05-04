package org.example.ComParalelismo;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComParalelismo {
    private static AtomicBoolean encontrado = new AtomicBoolean(false);

    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

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
                e.printStackTrace();
            }
        }
        if (!encontrado.get()) {
            JOptionPane.showMessageDialog(null, "Nome não encontrado");
        }
    }
}
