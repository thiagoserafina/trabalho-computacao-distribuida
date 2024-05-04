package org.example.SemParalelismo;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SemParalelismo {
    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

        if (nome == null || nome.isEmpty()) {
            System.exit(0);
        }

        File[] arquivos;
        File diretorio = new File("arquivoTXT");
        arquivos = diretorio.listFiles();

        long tempoInicial = System.currentTimeMillis();
        long tempoFinal;

        for (int i = 0; i < arquivos.length; i++) {
            try (Scanner scan = new Scanner(arquivos[i])) {
                int linha = 0;

                while (scan.hasNextLine()) {
                    linha++;
                    if (nome.equals(scan.nextLine())) {
                        tempoFinal = System.currentTimeMillis();
                        JOptionPane.showMessageDialog(null,
                                "Arquivo: " + arquivos[i].getName() + "\nLinha: " + linha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms", "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
            }
        }

        JOptionPane.showMessageDialog(null, "Nome não encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}