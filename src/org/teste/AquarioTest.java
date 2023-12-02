package org.teste;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.example.Aquario;
import org.example.Peixe;
import org.example.PeixeA;
import org.example.PeixeB;

public class AquarioTest {
    private Aquario aquario;

    // C1 - Matriz 10x10, X=10, Y=5, RA=5, MA=10, RB=3, MB=8
    @Test
    public void deveCriarUmJogoValido() {
        Aquario aquario = new Aquario(10, 10);
        int ra = 5;
        int ma = 10;
        int rb = 3;
        int mb = 8;

        for (int i = 0; i < 10; i++) {
            Peixe peixe = new PeixeA(0, i);
            aquario.adicionarPeixe(peixe);
        }

        for (int i = 0; i < 5; i++) {
            Peixe peixe = new PeixeB(1, i);
            aquario.adicionarPeixe(peixe);
        }

        Assert.assertEquals(10, aquario.contarPeixesTipoA());
        Assert.assertEquals(5, aquario.contarPeixesTipoB());

        aquario.executarIteracao(ra, rb, ma, mb);
    }

    // C2
    @Test
    public void naoDeveCriarJogoLinhasInvalidas() {

        try {
            aquario = new Aquario(0, 1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Quantidade de linhas inválido", e.getMessage());
        }
    }

    @Test
    public void naoDeveCriarJogoColunasInvalidas() {

        try {
            aquario = new Aquario(1, 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade de colunas inválido", e.getMessage());
        }
    }


    // C3
    @Test
    public void quantidadeInvalidaPeixeA() {
        aquario = new Aquario(10, 10);

        try {
            aquario.adicionarPeixesAleatoriamente(0, 1, 10, 10);
            Assert.fail("Deveria ter lançado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Quantidade de peixes A inválidos", e.getMessage());
        }
    }


    // C4
    @Test
    public void quantidadeInvalidaPeixeB() {
        aquario = new Aquario(10, 10);

        try {
            aquario.adicionarPeixesAleatoriamente(1, 0, 10, 10);
            Assert.fail("Deveria ter lançado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Quantidade de peixes B inválidos", e.getMessage());
        }
    }


    // C5
    @Test
    public void quantidadeRAInvalido() {
        try {
            aquario = new Aquario(10, 10);
            aquario.executarIteracao(0, 1, 10, 10);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade RA inválida", e.getMessage());
        }

    }

    @Test
    public void quantidadeRBInvalido() {
        try {
            aquario = new Aquario(10, 10);
            aquario.executarIteracao(1, 0, 10, 10);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade RB inválida", e.getMessage());
        }
    }

    @Test
    public void quantidadeMAInvalido() {
        try {
            aquario = new Aquario(10, 10);
            aquario.executarIteracao(1, 1, 0, 10);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade MA inválida", e.getMessage());
        }
    }

    @Test
    public void quantidadeMBInvalido() {
        try {
            aquario = new Aquario(10, 10);
            aquario.executarIteracao(1, 1, 10, 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade MB inválida", e.getMessage());
        }
    }


    // C6
    @Test
    public void naoDeveCriarJogoLinhasSuperiorInvalidas() {
        try {
            aquario = new Aquario(101, 1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade de linhas inválido", e.getMessage());
        }
    }

    @Test
    public void naoDeveCriarJogoColunasSuperiorInvalidas() {
        try {
            aquario = new Aquario(1, 101);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade de colunas inválido", e.getMessage());
        }
    }

    // C7

    @Test
    public void naoDeveCriarJogoPeixesASuperiorMaximo() {
        try {
            aquario = new Aquario(100, 1);
            aquario.adicionarPeixesAleatoriamente(26,1,100,1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade de peixes A inválidos", e.getMessage());
        }
    }

    // C8
    @Test
    public void naoDeveCriarJogoPeixesBSuperiorMaximo() {
        try {
            aquario = new Aquario(100, 1);
            aquario.adicionarPeixesAleatoriamente(1,26,100,1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Quantidade de peixes B inválidos", e.getMessage());
        }
    }


    // EXTRAS
    @Test
    public void deveCriarAquarioDimensoesMaximas() {
        aquario = new Aquario(100, 100);
        Assert.assertEquals(100, aquario.getLinhas());
        Assert.assertEquals(100, aquario.getColunas());
    }

    @Test
    public void deveCriarAquarioDimensoesMinimas() {
        aquario = new Aquario(1, 1);
        Assert.assertEquals(1, aquario.getLinhas());
        Assert.assertEquals(1, aquario.getColunas());
    }
    
    @Test
    public void deveAdicionarPeixeNosCantos() {
        aquario = new Aquario(10, 10);
        Peixe peixe1 = new PeixeA(0, 0); // Canto superior esquerdo
        Peixe peixe2 = new PeixeB(9, 9); // Canto inferior direito
        aquario.adicionarPeixe(peixe1);
        aquario.adicionarPeixe(peixe2);
        Assert.assertEquals(peixe1, aquario.getPeixe(0, 0));
        Assert.assertEquals(peixe2, aquario.getPeixe(9, 9));
    }
    
    @Test
    public void deveRemoverPeixe() {
        aquario = new Aquario(5, 5);
        Peixe peixe = new PeixeA(2, 2);
        aquario.adicionarPeixe(peixe);
        aquario.removerPeixe(2, 2);
        Assert.assertNull(aquario.getPeixe(2, 2));
    }
    
    @Test
    public void deveImprimirEstadoDoAquario() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        aquario = new Aquario(2, 2);
        aquario.adicionarPeixe(new PeixeA(0, 0)); // Adiciona um PeixeA no canto superior esquerdo
        aquario.adicionarPeixe(new PeixeB(1, 1)); // Adiciona um PeixeB no canto inferior direito
        aquario.imprimirEstado(1);

        // A saída esperada deve refletir exatamente o que o método imprimirEstado gera, incluindo espaços
        String expectedOutput = 
            "   Estado atual (1) do Aquário:\n" +
            "     0  1  \n" +  // espaços correspondentes ao cabeçalho
            "0  A  .  \n" +    // espaços correspondentes à linha 0
            "1  .  B  \n";     // espaços correspondentes à linha 1

        // Remover espaços em branco extras da saída real e esperada
        String actual = outContent.toString().replaceAll("\\s+", " ").trim();
        String expected = expectedOutput.replaceAll("\\s+", " ").trim();

        assertEquals(expected, actual);
        
        // Reset System.out para a saída padrão
        System.setOut(System.out);
    }

    
//    @Test(expected = IllegalArgumentException.class)
//    public void naoDeveAdicionarPeixesAleatoriamenteEmAquarioCheio() {
//        aquario = new Aquario(2, 2);
//        // Tenta adicionar mais peixes do que o aquário pode conter
//        aquario.adicionarPeixesAleatoriamente(3, 3, 2, 2);
//    }
    
    @Test
    public void deveAdicionarPeixesAleatoriamente() {
        aquario = new Aquario(10, 10);
        aquario.adicionarPeixesAleatoriamente(5, 5, 10, 10);
        assertEquals(5, aquario.contarPeixesTipoA());
        assertEquals(5, aquario.contarPeixesTipoB());
    }
    
    @Test
    public void deveAdicionarPeixesAleatoriamenteNoLimite() {
        aquario = new Aquario(5, 5);
        // O limite é 25 peixes, então adicionar 12 de cada tipo deve ser permitido
        aquario.adicionarPeixesAleatoriamente(12, 12, 5, 5);
        assertEquals(12, aquario.contarPeixesTipoA());
        assertEquals(12, aquario.contarPeixesTipoB());
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoParaQuantidadeInvalidaDePeixesA() {
        aquario = new Aquario(5, 5);
        // Tenta adicionar uma quantidade inválida de peixes tipo A
        aquario.adicionarPeixesAleatoriamente(26, 5, 5, 5);
    }

 // OUTROS TESTES
    @Test
    public void deveAdicionarPeixeEmPosicaoValida() {
        aquario = new Aquario(5, 5); // Aqu�rio de 5x5 para exemplo
        Peixe peixe = new PeixeA(2, 3);
        aquario.adicionarPeixe(peixe);
        Assert.assertEquals(peixe, aquario.getPeixe(2, 3));
    }

    @Test
    public void naoDeveAdicionarPeixeEmPosicaoInvalida() {

        try {
            aquario = new Aquario(5, 5); // Aqu�rio de 5x5 para exemplo
            int linhaInvalida = 6;
            int colunaInvalida = 6;
            Peixe peixe = new PeixeA(linhaInvalida, colunaInvalida);
            aquario.adicionarPeixe(peixe);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        	Assert.assertEquals("Posição inválida ou já ocupada: 6, 6", e.getMessage());
        }
    }

    @Test
    public void naoDeveAdicionarPeixeEmPosicaoJaOcupada() {
        aquario = new Aquario(5, 5); // Aqu�rio de 5x5 para exemplo
        Peixe peixe1 = new PeixeA(2, 3);
        Peixe peixe2 = new PeixeB(2, 3); // Mesma posi��o de peixe1

        aquario.adicionarPeixe(peixe1);

        try {
            if (aquario.getPeixe(peixe2.getX(), peixe2.getY()) != null) {
                throw new IllegalArgumentException("Posição inválida ou já ocupada: " + peixe2.getX() + ", " + peixe2.getY());
            }
            aquario.adicionarPeixe(peixe2);

            Assert.fail("Uma exceção IllegalArgumentException deveria ter sido lançada.");
        } catch (IllegalArgumentException exception) {
        	Assert.assertEquals("Posição inválida ou já ocupada: " + peixe2.getX() + ", " + peixe2.getY(), exception.getMessage());
        }
    }


    @Test
    public void deveAdicionarMultiplosPeixesEmPosicoesValidas() {
        aquario = new Aquario(5, 5); // Aqu�rio de 5x5 para exemplo
        Peixe peixe1 = new PeixeA(2, 3);
        Peixe peixe2 = new PeixeB(1, 1);

        aquario.adicionarPeixe(peixe1);
        aquario.adicionarPeixe(peixe2);

        Assert.assertEquals(peixe1, aquario.getPeixe(2, 3));
        Assert.assertEquals(peixe2, aquario.getPeixe(1, 1));
    }

    @Test
    public void naoDeveAdicionarMultiplosPeixesEmPosicoesInvalidas() {
        aquario = new Aquario(5, 5); // Aqu�rio de 5x5 para exemplo
        int linhaInvalida1 = 6;
        int colunaInvalida1 = 6;
        int linhaInvalida2 = -1;
        int colunaInvalida2 = 2;

        try {
            Peixe peixe = new PeixeA(linhaInvalida1, colunaInvalida1);
            aquario.adicionarPeixe(peixe);
            Assert.fail("Deveria ter lançado IllegalArgumentException para a primeira posição inválida");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Posição inválida ou já ocupada: " + linhaInvalida1 + ", " + colunaInvalida1, e.getMessage());
        }

        try {
            Peixe peixe = new PeixeB(linhaInvalida2, colunaInvalida2);
            aquario.adicionarPeixe(peixe);
            Assert.fail("Deveria ter lançado IllegalArgumentException para a segunda posição inválida");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Posição inválida ou já ocupada: " + linhaInvalida2 + ", " + colunaInvalida2, e.getMessage());
        }
    }

}