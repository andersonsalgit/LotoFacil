package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import BO.LotoBO;

public class ManipulaArquivo {

	public void verificaArquivo() {

		File dir = new File("C:\\workspace-sts-3.9.2.RELEASE\\LotoFacil");
		File arq = new File(dir, "D_LOTFAC.HTM");
		
		List<String> lineDrawn = new ArrayList<String>();
		StringBuilder builder = null;
		
		//Verifica última linha inserida
		int lastID = LotoBO.selectID();
		
		boolean controlLine = false;
		
		try {
			Document doc = Jsoup.parse(arq, "UTF-8");
			
			Elements elementsTR = doc.getElementsByTag("tr");
			
			for (int i = 1; i < elementsTR.size(); i++) {
				
				Elements elementsTD = elementsTR.get(i).getElementsByTag("td");
					
				if (elementsTD.get(0).text().equals(String.valueOf(lastID+1)) || controlLine) {

					controlLine = true;

					if (elementsTD.size() > 20) {

						if (null != builder) {// Adiciona toda a linha com os estados

							lineDrawn.add(builder.toString());

							builder = new StringBuilder();

						} else {// instância para receber uma nova linha
							builder = new StringBuilder();
						}

						for (int j = 0; j < elementsTD.size(); j++) {

							if ((j <= 16) || (j == 18) || (j == 20) || (j == 25) || (j == 31)) {
								builder.append(elementsTD.get(j).text() + ":");

							}
						}
					} else {// Para pegar os estados no final do html
						for (int k = 0; k < elementsTD.size(); k++) {
							if (elementsTD.get(k).text().length() == 2) {
								builder.append(elementsTD.get(k).text() + "-");
							}
						}
					}
				}
			}
			
			//Adiciona a última linha na lista
			lineDrawn.add(builder.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Salva todos os números sorteados
		LotoBO.inserirSorteados(lineDrawn);
		
	}
}
