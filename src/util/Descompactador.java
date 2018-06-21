package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Descompactador {

	public boolean extrairArquivo() {

		byte[] buffer = new byte[1024];

		try {

			// Cria o input do arquivo ZIP
			ZipInputStream zinstream = new ZipInputStream(new FileInputStream("C:\\workspace-sts-3.9.2.RELEASE\\LotoFacil\\dwNovo\\D_lotfac.zip"));

			// Pega a proxima entrada do arquivo
			ZipEntry zentry = zinstream.getNextEntry();
			
			while (zentry != null) {
				// Pega o nome da entrada
				String entryName = zentry.getName();

				// Cria o output do arquivo , Sera extraido onde esta rodando a
				// classe
				FileOutputStream outstream = new FileOutputStream(entryName);
				int n;

				// Escreve no arquivo
				while ((n = zinstream.read(buffer)) > -1) {
					outstream.write(buffer, 0, n);

				}

				// Fecha arquivo
				outstream.close();

				// Fecha entrada e tenta pegar a proxima
				zinstream.closeEntry();
				zentry = zinstream.getNextEntry();
				if(entryName.equals("D_LOTFAC.HTM")){
					break;
				}
			}

			// Fecha o zip como um todo
			zinstream.close();

			System.out.println("Arquivo extraído");
			return Boolean.TRUE;
		} catch (IOException ex) {
			ex.printStackTrace();
			return Boolean.FALSE;
		}

	}
}
