package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeixeA extends Peixe {
    int movimentosSeguidos;
    int naoMovimentouSeguidasVezes;
    public PeixeA(int x, int y) {
        super(x, y);
        this.movimentosSeguidos = 0;
        this.naoMovimentouSeguidasVezes = 0;
    }

    @Override
    public void mover(Aquario aquario) {
        if (this.getJaMoveu()) {
            return;
        }

        List<int[]> celulasLivres = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Ignorar a própria célula

                int novoX = x + dx;
                int novoY = y + dy;

                if (novoX >= 0 && novoX < aquario.getLinhas() && novoY >= 0 && novoY < aquario.getColunas() && aquario.getPeixe(novoX, novoY) == null) {
                    celulasLivres.add(new int[]{novoX, novoY});
                }
            }
        }

        if (!celulasLivres.isEmpty()) {
            Random random = new Random();
            int[] escolha = celulasLivres.get(random.nextInt(celulasLivres.size()));
            System.out.println("O peixe A moveu-se de [" + x + ", " + y + "] para [" + escolha[0] + ", " + escolha[1] + "]");
            aquario.moverPeixe(x, y, escolha[0], escolha[1]);
            this.movimentosSeguidos++;
            this.setX(escolha[0]); // Atualiza a posição X do peixe
            this.setY(escolha[1]); // Atualiza a posição Y do peixe
        } else {
            this.movimentosSeguidos = 0;
            this.naoMovimentouSeguidasVezes++;
            System.out.println("O peixe A na posição [" + x + ", " + y + "] não conseguiu se mover");
        }

        this.setJaMoveu(true);
    }

    @Override
    public void verificarReproducao(Aquario aquario, int RA, int RB) {
        if (this.movimentosSeguidos >= RA) {
            List<int[]> celulasLivres = new ArrayList<>();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    int novoX = x + dx;
                    int novoY = y + dy;

                    if (novoX >= 0 && novoX < aquario.getLinhas() && novoY >= 0 && novoY < aquario.getColunas() && aquario.getPeixe(novoX, novoY) == null) {
                        celulasLivres.add(new int[]{novoX, novoY});
                    }
                }
            }

            if (!celulasLivres.isEmpty()) {
                Random random = new Random();
                int[] escolha = celulasLivres.get(random.nextInt(celulasLivres.size()));
                aquario.adicionarPeixe(new PeixeA(escolha[0], escolha[1]));
                this.movimentosSeguidos = 0;
                System.out.println("O peixe A na posição [" + x + ", " + y + "] reproduziu um novo peixe A na posição [" + escolha[0] + ", " + escolha[1] + "]");
            }
            else{
                System.out.println("O peixe A na posição [" + x + ", " + y + "] não tem célula livre para se reproduzir");
            }
        }
    }


    @Override
    public void verificarMorte(Aquario aquario, int MA, int MB) {
        if (this.naoMovimentouSeguidasVezes >= MA) {
            aquario.removerPeixe(this.x, this.y);
            System.out.println("O peixe A na posição [" + x + ", " + y + "] morreu por falta de movimento");
        }
    }
    
    public int getMovimentosSeguidos() {
        return this.movimentosSeguidos;
    }

    public void setMovimentosSeguidos(int movimentosSeguidos) {
        this.movimentosSeguidos = movimentosSeguidos;
    }
    
    public int getNaoMovimentouSeguidasVezes() {
        return this.naoMovimentouSeguidasVezes;
    }

    public void setNaoMovimentouSeguidasVezes(int naoMovimentouSeguidasVezes) {
        this.naoMovimentouSeguidasVezes = naoMovimentouSeguidasVezes;
    }

}
