package wrapper.Wrapper1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.compress.utils.Charsets;

import wrapper.Wrapper10.Wrapper;

public class Test {
	public static void main(String[] args) {
		Long x = System.currentTimeMillis();
		Wrapper w = new Comum();
		String[] sites = { "americanas", "fastgames", "fnac", "livrariacultura", "magazineluiza", "nagem", "saraiva",
				"steampowered", "submarino", "walmart" };

		try {
			for (int i = 0; i < sites.length; i++) {
				String path = Wrapper.ARTIFACT_PATH + sites[i] + "\\positivo\\";
				File inputFile = new File(path + "visitedLinks.txt");
				File outputFile = new File(Wrapper.ARTIFACT_PATH + "\\uniqueWrapper\\" + "productInfo.txt");
				BufferedReader input;
				input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), Charsets.UTF_8));

				if (!outputFile.exists()) {
					outputFile.mkdirs();
				}

				PrintWriter pw;
				String entrada;
				pw = new PrintWriter(outputFile);

				while ((entrada = input.readLine()) != null) {
					try {
						w.pegarDados(pw, path, entrada);
					} catch (IOException e) {
						e.printStackTrace();
					}
					pw.println();
				}
				System.out.println("Terminei!!!");
				System.out.println(System.currentTimeMillis() - x);
				input.close();
				pw.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
