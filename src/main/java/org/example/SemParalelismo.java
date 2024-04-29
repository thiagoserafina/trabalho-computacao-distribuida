package org.example;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SemParalelismo {
    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

        File arquivos[];
        File diretorio = new File("arquivoTXT");
        arquivos = diretorio.listFiles();

        long tempoInicial = System.currentTimeMillis();
        long tempoFinal = 0;

        for (int i = 0; i < arquivos.length; i++) {
            try {
                Scanner scan = new Scanner(arquivos[i]);
                int linha = 0;
                while (scan.hasNextLine()) {
                    linha++;
                    if (nome.equals(scan.nextLine())) {
                        JOptionPane.showMessageDialog(null,
                                "Nome Encontrado" + "\nArquivo: " + arquivos[i].getName() + "\nLinha: " + linha);
                        tempoFinal = System.currentTimeMillis();
                        break;
                    }
                }
                scan.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showConfirmDialog(null, "Arquivo não encontrado");
            }
        }

        System.out.println("O método foi executado em " + (tempoFinal - tempoInicial)+"ms");
    }
}