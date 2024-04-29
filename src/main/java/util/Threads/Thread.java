package util.Threads;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Thread implements Runnable {

    File arquivo;
    String nome;

    public Thread(File arquivo, String nome) {
        this.arquivo = arquivo;
        this.nome = nome;
    }

    public void run() {
        long tempoInicial = System.currentTimeMillis();
        long tempoFinal = 0;
        try {
            Scanner scan = new Scanner(arquivo);
            int linha = 0;
            while (scan.hasNextLine()) {
                linha++;
                if (nome.equals(scan.nextLine())) {
                    JOptionPane.showMessageDialog(null,
                            "Nome Encontrado" + "\nArquivo: " + arquivo.getName() + "\nLinha: " + linha);
                    tempoFinal = System.currentTimeMillis();
                }
            }
            scan.close();
            System.out.println("O método foi executado em " + (tempoFinal - tempoInicial) + "ms");
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Arquivo não encontrado");
        }
    }
}
