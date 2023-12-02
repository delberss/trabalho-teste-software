package org.example;

import java.util.Random;

public class Aquario {
    private Peixe[][] matriz;
    private int linhas, colunas;

    public Aquario(int linhas, int colunas) {
        if(linhas < 1 || linhas > 100){
            throw new IllegalArgumentException("Quantidade de linhas inválido");
        }
        if(colunas < 1 || colunas > 100){
            throw new IllegalArgumentException("Quantidade de colunas inválido");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        matriz = new Peixe[linhas][colunas];
    }

    public void adicionarPeixesAleatoriamente(int numPeixesA, int numPeixesB, int linhas, int colunas) {
        Random random = new Random();
        if(numPeixesA > 0 && numPeixesA <= 25){
            for (int i = 0; i < numPeixesA; i++) {
                int x = random.nextInt(linhas);
                int y = random.nextInt(colunas);
                // Verifica se a posição já está ocupada antes de adicionar
                if (this.getPeixe(x, y) == null) {
                    this.adicionarPeixe(new PeixeA(x, y));
                } else {
                    i--; // Decrementa o contador para tentar novamente
                }
            }
        } else{
            throw new IllegalArgumentException("Quantidade de peixes A inválidos");
        }

        if(numPeixesB > 0 && numPeixesB <= 25){
            for (int i = 0; i < numPeixesB; i++) {
                int x = random.nextInt(linhas);
                int y = random.nextInt(colunas);
                // Verifica se a posição já está ocupada antes de adicionar
                if (this.getPeixe(x, y) == null) {
                    this.adicionarPeixe(new PeixeB(x, y));
                } else {
                    i--; // Decrementa o contador para tentar novamente
                }
            }
        }else{
            throw new IllegalArgumentException("Quantidade de peixes B inválidos");
        }
    }
    public void adicionarPeixe(Peixe peixe) {
        if (peixe.getX() >= 0 && peixe.getX() < linhas && peixe.getY() >= 0 && peixe.getY() < colunas) {
            matriz[peixe.getX()][peixe.getY()] = peixe;
        } else {
            throw new IllegalArgumentException("Posição inválida ou já ocupada: " + peixe.getX() + ", " + peixe.getY());
        }
    }

    public Peixe getPeixe(int x, int y) {
        return matriz[x][y];
    }

    public void removerPeixe(int x, int y) {
        matriz[x][y] = null;
    }

    public void moverPeixe(int origemX, int origemY, int destinoX, int destinoY) {
        Peixe peixe = matriz[origemX][origemY];
        if (peixe != null && (origemX != destinoX || origemY != destinoY)) {
            matriz[destinoX][destinoY] = peixe;
            matriz[origemX][origemY] = null;
        }
    }


    public void imprimirEstado(int iteracao) {
        System.out.println();
        System.out.println("Estado atual (" + iteracao + ") do Aquário:");

        // Cabeçalho para as colunas com espaçamento correto
        System.out.print("   "); // Espaço inicial para alinhamento
        for (int j = 0; j < colunas; j++) {
            System.out.print(String.format("%-3d", j)); // Formatação com 3 espaços de largura
        }
        System.out.println();

        // Corpo da matriz com identificadores de linha
        for (int i = 0; i < linhas; i++) {
            System.out.print(String.format("%-3d", i)); // Identificador da linha com 3 espaços de largura
            for (int j = 0; j < colunas; j++) {
                Peixe peixe = matriz[i][j];
                if (peixe instanceof PeixeA) {
                    System.out.print("A  "); // Representação do peixe A com espaçamento
                } else if (peixe instanceof PeixeB) {
                    System.out.print("B  "); // Representação do peixe B com espaçamento
                } else {
                    System.out.print(".  "); // Representação de célula vazia com espaçamento
                }
            }
            System.out.println();
        }
    }


    public void executarIteracao(int RA, int RB, int MA, int MB) {
        if(RA == 0){
            throw new IllegalArgumentException("Quantidade RA inválida");
        }
        if(RB == 0){
            throw new IllegalArgumentException("Quantidade RB inválida");
        }
        if(MA == 0){
            throw new IllegalArgumentException("Quantidade MA inválida");
        }
        if(MB == 0){
            throw new IllegalArgumentException("Quantidade MB inválida");
        }
        // Resetar o estado de movimento de todos os peixes
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peixe peixe = matriz[i][j];
                if (peixe != null) {
                    peixe.setJaMoveu(false);
                }
            }
        }

        // Primeiro, movimentar todos os peixes
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peixe peixe = matriz[i][j];
                if (peixe != null && !peixe.getJaMoveu()) {
                    peixe.mover(this);
                }
            }
        }

        // Verificar reprodução e morte
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peixe peixe = matriz[i][j];
                if (peixe != null) {
                    peixe.verificarReproducao(this, RA, RB);
                    peixe.verificarMorte(this, MA, MB);
                }
            }
        }
    }
    public int contarPeixesTipoA() {
        return contarPeixesPorTipo(PeixeA.class);
    }

    public int contarPeixesTipoB() {
        return contarPeixesPorTipo(PeixeB.class);
    }

    private int contarPeixesPorTipo(Class<?> tipoPeixe) {
        int contador = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (tipoPeixe.isInstance(matriz[i][j])) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }
}
