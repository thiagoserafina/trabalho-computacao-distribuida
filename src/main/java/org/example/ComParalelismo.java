package org.example;

import javax.swing.*;
import java.io.File;

public class ComParalelismo {
    public static void main(String[] args) {
        String nome = JOptionPane.showInputDialog("Informe um Nome para a Busca");

        File arquivos[];
        File diretorio = new File("arquivoTXT");
        arquivos = diretorio.listFiles();

    }
}
