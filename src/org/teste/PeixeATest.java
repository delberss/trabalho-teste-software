package org.teste;


import org.junit.Test;


import org.example.Aquario;

import org.example.PeixeA;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;


public class PeixeATest {
    private Aquario aquario;
    private PeixeA peixeA;
    private final int linhas = 5;
    private final int colunas = 5;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        aquario = new Aquario(linhas, colunas);
        peixeA = new PeixeA(2, 2);
        aquario.adicionarPeixe(peixeA);
        System.setOut(new PrintStream(outContent)); // Para capturar saídas do sistema
    }

    @Test
    public void testVerificarReproducaoComCondicaoSatisfeita() {
        // Configurar peixeA para atender a condição de reprodução
    	peixeA.setMovimentosSeguidos(3); // Supondo que RA é 3
        int quantidadePeixesAntes = aquario.contarPeixesTipoA();

        peixeA.verificarReproducao(aquario, 3, 0); // Chamar o método a ser testado

        int quantidadePeixesDepois = aquario.contarPeixesTipoA();
        assertEquals("Deve haver um novo peixe A após a reprodução", quantidadePeixesAntes + 1, quantidadePeixesDepois);
    }
    

    @Test
    public void testVerificarReproducaoSemCondicaoSatisfeita() {
        // Configurar peixeA para não atender a condição de reprodução
        peixeA.setMovimentosSeguidos(2); // Supondo que RA é 3
        int quantidadePeixesAntes = aquario.contarPeixesTipoA();

        peixeA.verificarReproducao(aquario, 3, 0); // Chamar o método a ser testado

        int quantidadePeixesDepois = aquario.contarPeixesTipoA();
        assertEquals("Não deve haver um novo peixe A sem atender a condição de reprodução", quantidadePeixesAntes, quantidadePeixesDepois);
    }

    @Test
    public void testVerificarMorteComCondicaoSatisfeita() {
        // Configurar peixeA para atender a condição de morte
        peixeA.setNaoMovimentouSeguidasVezes(3); // Supondo que MA é 3

        peixeA.verificarMorte(aquario, 3, 0); // Chamar o método a ser testado

        assertNull("Peixe A deve ser removido do aquário após a morte", aquario.getPeixe(peixeA.getX(), peixeA.getY()));
    }

    @Test
    public void testVerificarMorteSemCondicaoSatisfeita() {
        // Configurar peixeA para não atender a condição de morte
        peixeA.setNaoMovimentouSeguidasVezes(2); // Supondo que MA é 3

        peixeA.verificarMorte(aquario, 3, 0); // Chamar o método a ser testado

        assertNotNull("Peixe A não deve ser removido do aquário sem atender a condição de morte", aquario.getPeixe(peixeA.getX(), peixeA.getY()));
    }
    
    @Test
    public void deveRetornarMovimentosSeguidos() {
        peixeA.setMovimentosSeguidos(2); 
        assertEquals(2, peixeA.getMovimentosSeguidos());
    }
    
    @Test
    public void deveNaoMovimentouSeguidasVeze() {
        peixeA.setNaoMovimentouSeguidasVezes(2); 
        assertEquals(2, peixeA.getNaoMovimentouSeguidasVezes());
    }
    
    @Test
    public void testMoverJaMoveu() {
        peixeA.setJaMoveu(true);
        peixeA.mover(aquario);
        assertTrue("Peixe não deve se mover novamente", peixeA.getJaMoveu());
        assertEquals("Posição X deve permanecer a mesma", 2, peixeA.getX());
        assertEquals("Posição Y deve permanecer a mesma", 2, peixeA.getY());
    }
    
    @Test
    public void testMoverSemCelulasLivres() {
        // Preencha todas as células ao redor do peixe com outros peixes
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Ignora a própria célula
                aquario.adicionarPeixe(new PeixeA(2 + dx, 2 + dy));
            }
        }
        peixeA.mover(aquario);
        assertEquals("Movimentos seguidos devem ser resetados", 0, peixeA.getMovimentosSeguidos());
        assertEquals("Contagem de não movimentos deve incrementar", 1, peixeA.getNaoMovimentouSeguidasVezes());
    }
    
    @Test
    public void testMoverParaCelulaLivreDentroDoAquario() {
        Aquario aquario = new Aquario(3, 3);
        PeixeA peixeA = new PeixeA(1, 1); // Posicionado no centro do aquário
        aquario.adicionarPeixe(peixeA);

        // Adicionar peixes para bloquear algumas células ao redor do PeixeA
        aquario.adicionarPeixe(new PeixeA(0, 0));
        aquario.adicionarPeixe(new PeixeA(0, 1));
        aquario.adicionarPeixe(new PeixeA(0, 2));
        // Continuar adicionando peixes para outras células, se necessário

        int xInicial = peixeA.getX();
        int yInicial = peixeA.getY();

        peixeA.mover(aquario);

        int xFinal = peixeA.getX();
        int yFinal = peixeA.getY();

        // Verifica se o peixe se moveu para uma célula livre e dentro dos limites do aquário
        boolean seMoveuCorretamente = (xFinal != xInicial || yFinal != yInicial) && 
                                      xFinal >= 0 && xFinal < aquario.getLinhas() && 
                                      yFinal >= 0 && yFinal < aquario.getColunas() &&
                                      aquario.getPeixe(xFinal, yFinal) == peixeA;

        assertTrue(seMoveuCorretamente);
    }
    
    @Test
    public void testReproducaoSemCelulaLivre() {
        Aquario aquario = new Aquario(3, 3);
        PeixeA peixeA = new PeixeA(1, 1); // Posicionado no centro do aquário
        aquario.adicionarPeixe(peixeA);

        // Preencher todas as células ao redor com outros peixes
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    aquario.adicionarPeixe(new PeixeA(1 + dx, 1 + dy));
                }
            }
        }

        peixeA.setMovimentosSeguidos(5); // Configura movimentos suficientes para reprodução

        // Configura o stream de saída para capturar a saída do console
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        peixeA.verificarReproducao(aquario, 4, 0); // RA = 4, RB não é relevante aqui

        // Verifica se a mensagem apropriada foi exibida
        String expectedOutput = "O peixe A na posição [1, 1] não tem célula livre para se reproduzir";
        assertTrue(outContent.toString().contains(expectedOutput));

        // Restaura o stream de saída padrão
        System.setOut(System.out);
    }


}
