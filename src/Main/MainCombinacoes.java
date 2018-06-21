package Main;

import java.util.ArrayList;
import java.util.List;

public class MainCombinacoes {

	public static void main(String[] args) {

		/*
		 * http://www.matematicadidatica.com.br/CalculadoraArranjoCombinacaoSimples.aspx
		 * 
		 * FÓRMULA SIMPLES DE COMBINAÇÕES
		 * 
		 * C n,p = n! / p! (n - p)!
		 * 
		 */

		int elemento = 25;// O conjunto de números que podem ser sorteados
		List<Integer> produtoElemento = new ArrayList<>();
		long resElemento = 0;

		int combinacoes = 17;// Conjunto de números que podem ser agrupados
		List<Integer> produtoComb = new ArrayList<>();
		long resComb = 0;
		
		int subtracao = elemento - combinacoes;
		
		//NUMERADOR
		// Para decrementar o elemento
		produtoElemento = getProdutos(elemento, subtracao);
		//Cálculo do produto do elemento
		resElemento = calculaProduto(produtoElemento, resElemento);
		
		
		//DENOMINADOR
		produtoComb = getProdutos(subtracao, subtracao);
		
		resComb = calculaProduto(produtoComb, resComb);
		
		System.out.println(resElemento / resComb);
		
		//Resposta = 3268760
	}

	public static List<Integer> getProdutos(int elemento, int subtracao) {
		
		List<Integer> nuns = new ArrayList<>();
		
		// Para decrementar o elemento
		for (int i = 0; i < subtracao; i++) {
			
			if (i == 0) {
				nuns.add(elemento);
			} else {
				elemento = elemento - 1;
				nuns.add(elemento);
			}
		}
		return nuns;
	}
	
	public static long calculaProduto(List<Integer> produtoElemento, long resElemento) {
		//Cálculo do produto do elemento
		for (int i = 0; i < produtoElemento.size(); i++) {
			
			if(i == 0){
				resElemento = produtoElemento.get(i);
			} else {
				resElemento = resElemento * produtoElemento.get(i);
			}
		}
		return resElemento;
	}
}
