# Jogo do Aquário - Trabalho de Teste de Software

## Membros
- Delber Silveira Soares
- Marcus Vinícius Rodrigues da Silva
- Pedro Henrique Pascoalino Marques

## Telas do Funcionamento do Jogo

<table>
  <tr>
    <td><img src="https://github.com/delberss/jogoAquario/assets/71342302/1c37a5b0-bae1-4b71-aec4-17cd63718631" alt="Tela 1" width="100%"></td>
    <td><img src="https://github.com/delberss/jogoAquario/assets/71342302/c42ecc37-c4a8-49b6-8585-7a468e3d59b3" alt="Tela 2" width="100%"></td>
    <td><img src="https://github.com/delberss/jogoAquario/assets/71342302/04786114-1323-462d-bc78-4f20cb401387" alt="Tela 3" width="100%"></td>
  </tr>
</table>




## Especificações do Jogo
O jogo do aquário é representado por uma matriz bidimensional de tamanho MxN.

### Tipos de Peixes
Existem dois tipos de peixes no jogo: A e B.

### Regras dos Peixes Tipo A
1. Movimentação:
   - Se houver uma célula livre ao redor, os peixes do tipo A movem-se para essa célula.
2. Reprodução:
   - Após se movimentarem RA vezes consecutivas e encontrarem uma célula livre ao redor, os peixes tipo A se reproduzem, permanecendo na mesma célula, e o filhote ocupa a célula livre.
3. Fome:
   - Se os peixes tipo A não se movimentarem durante MA vezes consecutivas, eles morrem de fome.

### Regras dos Peixes Tipo B
1. Movimentação e Alimentação:
   - Se houver algum peixe do tipo A ao redor, os peixes tipo B movem-se para lá e os consomem. Caso contrário, movem-se para uma célula livre.
2. Reprodução:
   - Quando tiverem comido RB peixes do tipo A e não houver nenhum peixe do seu tipo ao redor, mas existir uma célula livre, os peixes tipo B se reproduzem, permanecendo na mesma célula, e o filhote ocupa a célula livre.
3. Fome:
   - Se os peixes tipo B não comerem nenhum peixe do tipo A durante MB vezes, eles morrem de fome.
