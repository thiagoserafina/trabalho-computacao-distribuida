package org.example.ExtendsThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Leitor extends Thread {
    private File arquivo;
    private String nome;
    private AtomicBoolean encontrado;

    public Leitor(File arquivo, String nome, AtomicBoolean encontrado) {
        this.arquivo = arquivo;
        this.nome = nome;
        this.encontrado = encontrado;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int numeroLinha = 1;

            while ((linha = reader.readLine()) != null) {
                if (linha.equals(nome)) {
                    encontrado.set(true);
                    System.out.println("Nome encontrado no arquivo: " + arquivo.getName() + ", linha: " + numeroLinha);
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

