package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;

public class Donwload {

	public boolean donwload() {

		String caminho = null;	
		try {
			caminho = new File("dwAntigo").getCanonicalPath();
			caminho.replaceAll("\\\\", "/");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			// cria URL
			URL url1 = new URL("http://www1.caixa.gov.br/loterias/_arquivos/loterias/D_lotfac.zip");
			
			// Define um gerenciamento padrão dos cookies, neste momento para evitar redirecionamento da servlet (loop)
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			
			// abre uma conexao na url criada àcima
			URLConnection con = url1.openConnection();
			
			// pega conexão
			con.connect();

			// arquivo de saida
			FileOutputStream fileOut = new FileOutputStream("C:\\workspace-sts-3.9.2.RELEASE\\LotoFacil\\dwNovo\\D_lotfac.zip");

			int c = 0;

			do {
				// le o byte
				c = con.getInputStream().read();

				// escreve o byte no arquivo saida
				fileOut.write(c);

			} while (c != -1);

			// fecha o arquivo de saida
			fileOut.close();

			System.out.println("Arquivo baixado com sucesso");
			return Boolean.TRUE;
		} catch (IOException e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}

	}

}
