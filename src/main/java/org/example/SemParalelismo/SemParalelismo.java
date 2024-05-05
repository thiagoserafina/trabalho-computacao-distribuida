package org.example.SemParalelismo;

import javax.swing.*;
import java.io.*;

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
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivos[i]))) {
                String linha;
                int numeroLinha = 1;

                while ((linha = reader.readLine()) != null) {
                    if (linha.equals(nome)) {
                        tempoFinal = System.currentTimeMillis();
                        JOptionPane.showMessageDialog(null,
                                "Arquivo: " + arquivos[i].getName() + "\nLinha: " + numeroLinha + "\nTempo de execução: " + (tempoFinal - tempoInicial) + "ms",
                                "Nome encontrado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    numeroLinha++;
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
            }
        }

        JOptionPane.showMessageDialog(null, "Nome não encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}