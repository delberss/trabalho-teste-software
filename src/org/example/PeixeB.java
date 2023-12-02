package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeixeB extends Peixe {
    int peixesAcomidos;
    int iteracoesSemComer;
    public PeixeB(int x, int y) {
        super(x, y);
        this.peixesAcomidos = 0;
        this.iteracoesSemComer = 0;
    }

    @Override
    public void mover(Aquario aquario) {
        if (this.getJaMoveu()) {
            return;
        }

        List<int[]> celulasLivres = new ArrayList<>();
        boolean comeuPeixeA = false;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Ignorar a própria célula

                int novoX = x + dx;
                int novoY = y + dy;

                if (novoX >= 0 && novoX < aquario.getLinhas() && novoY >= 0 && novoY < aquario.getColunas()) {
                    Peixe peixe = aquario.getPeixe(novoX, novoY);
                    if (peixe instanceof PeixeA) {
                        this.peixesAcomidos++;
                        this.iteracoesSemComer = 0;
                        System.out.println("O peixe B moveu-se de [" + x + ", " + y + "] para [" + novoX + ", " + novoY + "] e alimentou-se");
                        aquario.removerPeixe(novoX, novoY);
                        aquario.moverPeixe(x, y, novoX, novoY);
                        this.setX(novoX); // Atualiza a posição X do peixe
                        this.setY(novoY); // Atualiza a posição Y do peixe
                        comeuPeixeA = true;
                        break;
                    } else if (peixe == null) {
                        celulasLivres.add(new int[]{novoX, novoY});
                    }
                }
            }

            if (comeuPeixeA) break;
        }

        if (!comeuPeixeA && !celulasLivres.isEmpty()) {
            Random random = new Random();
            int[] escolha = celulasLivres.get(random.nextInt(celulasLivres.size()));
            System.out.println("O peixe B moveu-se de [" + x + ", " + y + "] para [" + escolha[0] + ", " + escolha[1] + "]");
            aquario.moverPeixe(x, y, escolha[0], escolha[1]);
            this.iteracoesSemComer++;
            this.setX(escolha[0]); // Atualiza a posição X do peixe
            this.setY(escolha[1]); // Atualiza a posição Y do peixe
        } else if (!comeuPeixeA) {
            System.out.println("O peixe B na posição [" + x + ", " + y + "] não conseguiu se mover");
            this.iteracoesSemComer++;
        }

        this.setJaMoveu(true);
    }



    @Override
    public void verificarReproducao(Aquario aquario, int RA, int RB) {
        if (this.peixesAcomidos >= RB) {
            boolean outroPeixeBProximo = false;
            List<int[]> celulasLivres = new ArrayList<>();

            // Verificar células ao redor para reprodução
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    int novoX = x + dx;
                    int novoY = y + dy;

                    if (novoX >= 0 && novoX < aquario.getLinhas() && novoY >= 0 && novoY < aquario.getColunas()) {
                        Peixe peixe = aquario.getPeixe(novoX, novoY);
                        if (peixe instanceof PeixeB) {
                            outroPeixeBProximo = true;
                            break;
                        } else if (peixe == null) {
                            celulasLivres.add(new int[]{novoX, novoY});
                        }
                    }
                }

                if (outroPeixeBProximo) break;
            }

            // Reprodução de PeixeB
            if (!outroPeixeBProximo && !celulasLivres.isEmpty()) {
                Random random = new Random();
                int[] escolha = celulasLivres.get(random.nextInt(celulasLivres.size()));
                aquario.adicionarPeixe(new PeixeB(escolha[0], escolha[1]));
                this.peixesAcomidos = 0;
                System.out.println("O peixe B na posição [" + x + ", " + y + "] reproduziu um novo peixe B na posição [" + escolha[0] + ", " + escolha[1] + "]");
            }

        }
    }

    @Override
    public void verificarMorte(Aquario aquario, int MA, int MB) {
        if (this.iteracoesSemComer >= MB) {
            aquario.removerPeixe(this.x, this.y);
            System.out.println("O peixe B na posição [" + x + ", " + y + "] morreu por falta de alimento");
        }
    }
    
    public int getPeixesAcomidos() {
        return this.peixesAcomidos;
    }

    public void setPeixesAcomidos(int peixesAcomidos) {
        this.peixesAcomidos = peixesAcomidos;
    }
    
    public int getIteracoesSemComer() {
        return this.iteracoesSemComer;
    }

    public void setIteracoesSemComer(int iteracoesSemComer) {
        this.iteracoesSemComer = iteracoesSemComer;
    }
}
