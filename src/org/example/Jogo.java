package org.example;

import java.util.Scanner;

public class Jogo {
    private Aquario aquario;
    private int RA, RB, MA, MB;
    private int iteracoes;

    public Jogo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("********** Bem-vindo ao Jogo do Aquário! **********");


        int linhas = solicitarInputPositivo(scanner, "Digite o número de linhas do aquário (M): ");
        int colunas = solicitarInputPositivo(scanner, "Digite o número de colunas do aquário (N): ");

        // Inicializar aquário
        this.aquario = new Aquario(linhas, colunas);

        int maxPeixes = linhas * colunas;
        int numPeixesA = solicitarInputPositivo(scanner, "Digite a quantidade de peixes do tipo A (X): ", maxPeixes-1);
        int numPeixesB = solicitarInputPositivo(scanner, "Digite a quantidade de peixes do tipo B (Y): ", maxPeixes-numPeixesA);

        // Adicionar peixes
        aquario.adicionarPeixesAleatoriamente(numPeixesA, numPeixesB, linhas, colunas);

        // Solicitar RA, RB, MA, MB
        RA = solicitarInputPositivo(scanner, "Digite a quantidade de movimentações para reprodução do peixe A (RA): ");
        RB = solicitarInputPositivo(scanner, "Digite a quantidade de peixes A que peixe B deve comer para reprodução do peixe B (RB): ");
        MA = solicitarInputPositivo(scanner, "Digite a quantidade de vezes que o peixe A deve ficar sem movimentar para morrer (MA): ");
        MB = solicitarInputPositivo(scanner, "Digite a quantidade de vezes que o peixe B deve ficar sem comer peixe A para morrer (MB): ");

        this.iteracoes = 0;

        aquario.imprimirEstado(iteracoes);
    }

    public int solicitarInputPositivo(Scanner scanner, String mensagem) {
        int valor;
        do {
            System.out.print(mensagem);
            valor = scanner.nextInt();
        } while (valor <= 0);
        return valor;
    }

    public int solicitarInputPositivo(Scanner scanner, String mensagem, int max) {
        int valor;
        do {
            System.out.print(mensagem);
            valor = scanner.nextInt();
        } while (valor <= 0 || valor > max);
        return valor;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            String finalizarJogo = deveTerminar();
            if (!finalizarJogo.isEmpty()) {
                System.out.println();
                System.out.println(finalizarJogo);
                break;
            }
            System.out.println("***************************************************");
            System.out.println("1 - Executar a próxima iteração\n2 - Encerrar o jogo");

            System.out.print("Opção:");
            int escolha = scanner.nextInt();

            while(escolha != 1 && escolha != 2){
                System.out.print("Opção (1 ou 2):");
                escolha = scanner.nextInt();
                System.out.println("***************************************************");
            }
            if (escolha == 1) {
                iteracoes++;
                System.out.println("***************************************************");
                System.out.println("Iteração ("+ iteracoes + "):");
                System.out.println();
                executarIteracao();
                aquario.imprimirEstado(iteracoes);
            } else if(escolha == 2){
                jogoAtivo = false;
            }
        }
        scanner.close();
        System.out.println("***************************************************");
        System.out.println("Jogo encerrado. Pontuação total (iterações): " + iteracoes);
        System.out.println("***************************************************");
    }

    private String deveTerminar() {
        if(aquario.contarPeixesTipoA() == 0){
            return "Os peixes do tipo A foram extintos";
        }
        else if(aquario.contarPeixesTipoB() == 0){
            return "Os peixes do tipo B foram extintos";
        }
        else{
            return "";
        }
    }


    private void executarIteracao() {
        // Lógica para executar uma iteração do jogo
        aquario.executarIteracao(RA, RB, MA, MB);
    }

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciar();
    }
}
