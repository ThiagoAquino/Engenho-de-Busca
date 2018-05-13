package wrapper.Wrapper10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.compress.utils.Charsets;

/*http://stummjr.github.io/jsoup/#_conhecendo_o_jsoup*/

public class StartWrapper {

	public void Start(String [] dominios, String crawlerMethod){
		Wrapper w = null;
		AlphanumComparator ac = new AlphanumComparator();

		try {
			for (int i = 0; i < dominios.length; i++) {
				String path = Wrapper.ARTIFACT_PATH + "\\" +  crawlerMethod + "\\"+ dominios[i] + "\\positives\\";
				File inputFile = new File(path + "posLinks.txt");

				String productInfoPath = Wrapper.ARTIFACT_PATH + "\\" + "output_wrapper\\" + dominios[i] + "\\";

				if(!new File(productInfoPath).exists()){
					new File(productInfoPath).mkdirs();
				}

				ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(new File(path).list()));

				fileNames.sort(ac);	
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), Charsets.UTF_8));

				String entrada;
				for (String fileName : fileNames) {
					entrada = input.readLine();
					if(entrada != null){
						if (entrada.contains("www.saraiva.com.br")) {
							w = new Saraiva();
						} else if (entrada.contains("www.walmart.com.br")) {
							w = new Walmart();
						} else if (entrada.contains("www.magazineluiza.com.br")) {
							w = new Magazineluiza();
						} else if (entrada.contains("www.americanas.com.br")) {
							w = new Americanas();
						} else if (entrada.contains("www.fastgames.com.br")) {
							w = new Fast();
						} else if (entrada.contains("www.livrariacultura.com.br")) {
							w = new Cultura();
						} else if (entrada.contains("www.nagem.com.br")) {
							w = new Nagem();
						} else if (entrada.contains("store.steampowered.com")) {
							w = new Steam();
						} else if (entrada.contains("www.fnac.com.br")) {
							w = new Fnac();
						} else if (entrada.contains("www.submarino.com.br")) {
							w = new Submarino();
						}

						File file = new File(path + fileName);
						String s = input.readLine();

						File outputFile = new File(productInfoPath + "\\" + fileName +".txt");
						if (!outputFile.exists()) {
							outputFile.createNewFile();
							outputFile.mkdirs();
						}
						PrintWriter pw = new PrintWriter(outputFile);

						if(s != null){
//							System.out.println(fileName);
							w.pegarDados(pw, file.getPath(), entrada);
							pw.println();
						} 
						pw.close();

					}
				}
				System.out.println("Wrapper para " + dominios[i] + " terminado.");
				input.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
