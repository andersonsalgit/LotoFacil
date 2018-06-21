package Main;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import BO.LotoBO;
import util.Descompactador;
import util.Donwload;
import util.ManipulaArquivo;

public class Main {

	static int indexList;
	
	public static void main(String[] args) {
		
		//Tempo inicial da execução
		Instant startTime = Instant.now();
		
		/*Baixa e tratamento do arquivo*/
		Donwload dw = new Donwload();
		
		//baixa o arquivo da internet
		boolean baixou = dw.donwload();
		
		
		//Se baixou extrai o arquivo D_LOTFAC.HTM
		if (baixou) {
			Descompactador dc = new Descompactador();

			// Leitura do arquivo HMT
			if (dc.extrairArquivo()) {
	
				ManipulaArquivo ma = new ManipulaArquivo();
				ma.verificaArquivo();
			}
		}
		
		
		//Util.alterTableToCopyNewTable();
		
		//Gerador de todas as combinações de sorteios da loto
		/*Random gerador = new Random();
		 
		int conjunto = 25;
		int agrupamento = 15;
		int combinacoes = 3268760;
		int count_combi = 0;
		
        List<Integer> combinados = new ArrayList<Integer>();
        List<List<Integer>> ListCombinados = new ArrayList<List<Integer>>();
        
        getValue1(gerador, combinados, ListCombinados, conjunto, agrupamento, combinacoes, count_combi);
        
		// faz o trabalho a ser medido tempo final
        Instant endTime = Instant.now();
        Duration totalTime = Duration.between(startTime, endTime);
        
        System.out.println("--------------Tempo Total--------------");
        System.out.println(totalTime.getSeconds()+"s");*/
        
		System.exit(0);
	}

	private static void getValue1(Random gerador, List<Integer> combinados, List<List<Integer>> ListCombinados, int conjunto, int agrupamento, int combinacoes, int count_combi) {
		
		Instant startTimeLista = Instant.now();
		
		int corte = 1;
		int corteMax = 32000;
		
		while (count_combi != combinacoes) {
		
        	int numeroSorteado = gerador.nextInt(conjunto) + 1;
        	        	
        	//Verifica se alista já atingiu o tamanho máximo de combinações
        	if(combinados.size() < agrupamento){
        	
        		//Se alista não contém o número
        		if(!combinados.contains(numeroSorteado)){
            		combinados.add(numeroSorteado);       		
            	}
        		
        	} else {
        		
        		Collections.sort(combinados); // = 50s
                
                //boolean res = lotoBO.verificaCombinacao(combinados);
        		
        		//Só adiciona se alista ainda não contém a combinação
        		//if(!res){
        		if(!ListCombinados.contains(combinados)){
        		
        			ListCombinados.add(combinados);
        			
        			if(corte == corteMax || ListCombinados.size() == combinacoes){
        				
        				// faz o trabalho a ser medido tempo final
        		        Instant endTimeLista = Instant.now();
        		        Duration totalTime = Duration.between(startTimeLista, endTimeLista);
        		        
        		        System.out.println("Montagem da lista Em: "+totalTime.getSeconds()+"s");
        				
        				Thread threadSalvar = new Thread(new LotoBO(ListCombinados, combinacoes, corte));
        				threadSalvar.start();
        			
        				corte = 0;
        				
        				//Tempo inicial da execução
        				startTimeLista = Instant.now();
        			}
        			
        			//Salva conforme o corte
        			corte++;
        			
        			//Contador de combinações
            		count_combi++;
        		}
        		
        		//lima a lista de combinados
    			combinados = new ArrayList<Integer>();
        	}
		}
	}
}
