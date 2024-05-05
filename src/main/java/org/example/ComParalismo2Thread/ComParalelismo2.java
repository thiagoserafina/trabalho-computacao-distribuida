package org.example.ComParalismo2Thread;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComParalelismo2 {
    private static AtomicBoolean encontrado = new AtomicBoolean(false);

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
        Thread[] threads = new Thread[4];

        File arquivo1 = arquivos[0];
        File arquivo2 = arquivos[1];
        File arquivo3 = arquivos[2];
        File arquivo4 = arquivos[3];
        File arquivo5 = arquivos[4];
        File arquivo6 = arquivos[5];
        File arquivo7 = arquivos[6];

        threads[0] = new Thread(new Leitor2(new File[]{arquivo1, arquivo2}, nome, encontrado, tempoInicial));
        threads[1] = new Thread(new Leitor2(new File[]{arquivo3, arquivo4}, nome, encontrado, tempoInicial));
        threads[2] = new Thread(new Leitor2(new File[]{arquivo5, arquivo6}, nome, encontrado, tempoInicial));
        threads[3] = new Thread(new Leitor2(new File[]{arquivo7}, nome, encontrado, tempoInicial));

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
