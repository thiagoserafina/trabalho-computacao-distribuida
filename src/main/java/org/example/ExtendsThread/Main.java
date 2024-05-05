package org.example.ExtendsThread;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

        if (nome == null || nome.isEmpty()) {
            System.exit(0);
        }

        File[] arquivos;
        File diretorio = new File("arquivoTXT");
        arquivos = diretorio.listFiles();

        AtomicBoolean encontrado = new AtomicBoolean(false);

        long tempoInicial = System.currentTimeMillis();

        assert arquivos != null;
        Leitor[] threads = new Leitor[arquivos.length];

        for (int i = 0; i < arquivos.length; i++) {
            final File arquivo = arquivos[i];
            threads[i] = new Leitor(arquivo, nome, encontrado);
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
        // Verificar se o nome foi encontrado
        if (!encontrado.get()) {
            System.out.println("Nome não encontrado em nenhum arquivo.");
        }
    }
}

