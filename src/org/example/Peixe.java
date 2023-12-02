package org.example;

public abstract class Peixe {
    protected int x, y;

    private boolean jaMoveu;


    public Peixe(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void mover(Aquario aquario);
    public abstract void verificarReproducao(Aquario aquario, int RA, int RB);
    public abstract void verificarMorte(Aquario aquario, int MA, int MB);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getJaMoveu() {
        return jaMoveu;
    }

    public void setJaMoveu(boolean jaMoveu) {
        this.jaMoveu = jaMoveu;
    }

}