package org.teste;


import org.junit.Test;


import org.example.Aquario;

import org.example.PeixeB;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;




public class PeixeBTest {
	 private Aquario aquario;
	    private PeixeB peixeB;
	    private final int linhas = 5;
	    private final int colunas = 5;
	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	    @Before
	    public void setUp() {
	        aquario = new Aquario(linhas, colunas);
	        peixeB = new PeixeB(2, 2);
	        aquario.adicionarPeixe(peixeB);
	        System.setOut(new PrintStream(outContent)); // Para capturar saídas do sistema
	    }

	 @Test
	    public void testMoverJaMoveu() {
	        peixeB.setJaMoveu(true);
	        peixeB.mover(aquario);
	        assertTrue("Peixe não deve se mover novamente", peixeB.getJaMoveu());
	        assertEquals("Posição X deve permanecer a mesma", 2, peixeB.getX());
	        assertEquals("Posição Y deve permanecer a mesma", 2, peixeB.getY());
	    }
	 
	 @Test
	    public void testGetSetPeixesAcomidos() {
	        PeixeB peixeB = new PeixeB(0, 0);

	        // Teste do setter
	        peixeB.setPeixesAcomidos(5);
	        assertEquals(5, peixeB.getPeixesAcomidos());

	        // Teste do getter
	        peixeB.setPeixesAcomidos(10);
	        assertEquals(10, peixeB.getPeixesAcomidos());
	    }

	    @Test
	    public void testGetSetIteracoesSemComer() {
	        PeixeB peixeB = new PeixeB(0, 0);

	        // Teste do setter
	        peixeB.setIteracoesSemComer(3);
	        assertEquals(3, peixeB.getIteracoesSemComer());

	        // Teste do getter
	        peixeB.setIteracoesSemComer(6);
	        assertEquals(6, peixeB.getIteracoesSemComer());
	    }
	    
	    @Test
	    public void testReproducaoBemSucedida() {
	        Aquario aquario = new Aquario(3, 3); // Cria um aquário 3x3
	        PeixeB peixeB = new PeixeB(1, 1); // Coloca o PeixeB no centro

	        aquario.adicionarPeixe(peixeB);
	        peixeB.setPeixesAcomidos(3); // Simula que o peixe B comeu o suficiente

	        peixeB.verificarReproducao(aquario, 0, 2); // RB = 2, RA não é relevante aqui

	        // Verifica se um novo peixe B foi adicionado ao aquário
	        int quantidadePeixesB = contarPeixesB(aquario);
	        assertEquals(2, quantidadePeixesB);
	    }

	    @Test
	    public void testReproducaoFalha() {
	        Aquario aquario = new Aquario(1, 1); // Cria um aquário 1x1
	        PeixeB peixeB = new PeixeB(0, 0); // Coloca o PeixeB na única célula disponível

	        aquario.adicionarPeixe(peixeB);
	        peixeB.setPeixesAcomidos(3); // Simula que o peixe B comeu o suficiente

	        peixeB.verificarReproducao(aquario, 0, 2); // RB = 2, RA não é relevante aqui

	        // Verifica se não há um novo peixe B no aquário
	        int quantidadePeixesB = contarPeixesB(aquario);
	        assertEquals(1, quantidadePeixesB);
	    }

	    private int contarPeixesB(Aquario aquario) {
	        int contador = 0;
	        for (int i = 0; i < aquario.getLinhas(); i++) {
	            for (int j = 0; j < aquario.getColunas(); j++) {
	                if (aquario.getPeixe(i, j) instanceof PeixeB) {
	                    contador++;
	                }
	            }
	        }
	        return contador;
	    }
	    
	    @Test
	    public void testMortePorFaltaDeAlimento() {
	        Aquario aquario = new Aquario(3, 3);
	        PeixeB peixeB = new PeixeB(1, 1);

	        aquario.adicionarPeixe(peixeB);
	        peixeB.setIteracoesSemComer(5); // Simula que o peixe B não comeu por 5 iterações

	        peixeB.verificarMorte(aquario, 0, 4); // MB = 4, MA não é relevante aqui

	        // Verifica se o peixe B foi removido do aquário
	        assertNull(aquario.getPeixe(1, 1));
	    }

	    @Test
	    public void testSobrevivencia() {
	        Aquario aquario = new Aquario(3, 3);
	        PeixeB peixeB = new PeixeB(1, 1);

	        aquario.adicionarPeixe(peixeB);
	        peixeB.setIteracoesSemComer(3); // Simula que o peixe B não comeu por 3 iterações

	        peixeB.verificarMorte(aquario, 0, 4); // MB = 4, MA não é relevante aqui

	        // Verifica se o peixe B ainda está no aquário
	        assertNotNull(aquario.getPeixe(1, 1));
	    }

	    @Test
	    public void testMoverSemComerPeixeA() {
	        Aquario aquario = new Aquario(3, 3); // Cria um aquário 3x3
	        PeixeB peixeB = new PeixeB(1, 1); // Coloca o PeixeB no centro

	        aquario.adicionarPeixe(peixeB);

	        int iteracoesIniciaisSemComer = peixeB.getIteracoesSemComer();
	        int xInicial = peixeB.getX();
	        int yInicial = peixeB.getY();

	        peixeB.mover(aquario); // Executa o movimento

	        int xFinal = peixeB.getX();
	        int yFinal = peixeB.getY();

	        // Verifica se o peixe se moveu
	        boolean seMoveu = xInicial != xFinal || yInicial != yFinal;
	        assertTrue(seMoveu);

	        // Verifica se o número de iterações sem comer foi incrementado
	        assertEquals(iteracoesIniciaisSemComer + 1, peixeB.getIteracoesSemComer());
	    }
	    
	    @Test
	    public void testReproducaoComOutroPeixeBProximo() {
	        Aquario aquario = new Aquario(3, 3);
	        PeixeB peixeB1 = new PeixeB(1, 1);
	        PeixeB peixeB2 = new PeixeB(1, 2); // Outro PeixeB próximo

	        aquario.adicionarPeixe(peixeB1);
	        aquario.adicionarPeixe(peixeB2);
	        peixeB1.setPeixesAcomidos(3); // Suficiente para reprodução

	        peixeB1.verificarReproducao(aquario, 0, 2); // RB = 2

	        // Verifica se não houve reprodução
	        int quantidadePeixesB = contarPeixesB(aquario);
	        assertEquals(2, quantidadePeixesB); // Somente os dois peixes originais
	    }

	    @Test
	    public void testReproducaoComCelulaLivreAdjacente() {
	        Aquario aquario = new Aquario(3, 3);
	        PeixeB peixeB = new PeixeB(1, 1); // Sem outro PeixeB próximo

	        aquario.adicionarPeixe(peixeB);
	        peixeB.setPeixesAcomidos(3); // Suficiente para reprodução

	        peixeB.verificarReproducao(aquario, 0, 2); // RB = 2

	        // Verifica se houve reprodução
	        int quantidadePeixesB = contarPeixesB(aquario);
	        assertTrue(quantidadePeixesB > 1); // Mais de um PeixeB indica reprodução
	    }
	    
	    @Test
	    public void testMovimentoDentroDosLimitesDoAquario() {
	        Aquario aquario = new Aquario(3, 3);
	        PeixeB peixeB = new PeixeB(0, 0); // Posicionado no canto superior esquerdo do aquário

	        aquario.adicionarPeixe(peixeB);
	        peixeB.mover(aquario);

	        // Verifica se o peixe permanece dentro dos limites do aquário
	        assertTrue(peixeB.getX() >= 0 && peixeB.getX() < aquario.getLinhas());
	        assertTrue(peixeB.getY() >= 0 && peixeB.getY() < aquario.getColunas());
	    }
	    
	    @Test
	    public void testIncapacidadeDeMover() {
	        Aquario aquario = new Aquario(3, 3);
	        PeixeB peixeB = new PeixeB(1, 1); // Posicionado no centro
	        // Adicionar peixes do tipo B ao redor para bloquear o movimento
	        for (int dx = -1; dx <= 1; dx++) {
	            for (int dy = -1; dy <= 1; dy++) {
	                if (!(dx == 0 && dy == 0)) {
	                    aquario.adicionarPeixe(new PeixeB(1 + dx, 1 + dy));
	                }
	            }
	        }

	        aquario.adicionarPeixe(peixeB);
	        int iteracoesIniciaisSemComer = peixeB.getIteracoesSemComer();
	        peixeB.mover(aquario);

	        // Verifica se o peixe não conseguiu se mover e se iteracoesSemComer foi incrementado
	        assertEquals(1 + iteracoesIniciaisSemComer, peixeB.getIteracoesSemComer());
	    }


	  
}
