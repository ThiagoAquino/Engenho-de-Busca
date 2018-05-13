/*
 * Universidade Federal de Pernambuco
 * Centro de Inform�tica (CIn)
 * Recupera��o de Informa��o
 * 
 * Ana Caroline Ferreira de Fran�a (acff)
 * Thiago Aquino Santos (tas4)
 * Victor Sin Yu Chen (vsyc)
 */

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.FileUtils;

import crawler.SpiderFactory;
import wrapper.Wrapper10.Americanas;
import wrapper.Wrapper10.Cultura;
import wrapper.Wrapper10.Fast;
import wrapper.Wrapper10.Fnac;
import wrapper.Wrapper10.Magazineluiza;
import wrapper.Wrapper10.Nagem;
import wrapper.Wrapper10.Saraiva;
import wrapper.Wrapper10.StartWrapper;
import wrapper.Wrapper10.Steam;
import wrapper.Wrapper10.Submarino;
import wrapper.Wrapper10.Walmart;
import wrapper.Wrapper10.Wrapper;
import classify.Classificador;
import classify.Preprocess;

public class Integracao {
	public final static String[] dominios = {"fnac", "nagem","livrariacultura","fastgames","magazineluiza", "submarino","saraiva","americanas", "steampowered", "walmart"};

	public static void main(String[] args) throws IOException, InterruptedException {
		//String crawlerMethod = "bfs";
		String crawlerMethod = "heuristic";

		try {
			// Run Crawler
			//runCrawler(crawlerMethod);

			// Run Classifier
			//runClassifier(crawlerMethod);

			// Run Wrapper
			runWrapper(crawlerMethod);
		} finally {

		}
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	/***
	 * This method will run the Crawler.
	 * 
	 * @param algorithm
	 *            the algorithm the Crawler will use. Can only have two values:
	 *            bfs and heuristic
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void runCrawler(String algorithm) throws IOException, InterruptedException {
		SpiderFactory sf = new SpiderFactory();
		sf.startCrawlers(algorithm);
	}

	/***
	 * This method will run the Classifier.
	 */
	public static void runClassifier(String crawlerMethod) {
		Classificador c = Classificador.getC();
		for (int i = 0; i < dominios.length; i++) {
			String path = "artefatos/" + crawlerMethod + "/" + dominios[i];
			File f = new File(path);
			File linksList = new File(path + "/visitedLinks.txt");
			ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
			ArrayList<Integer> filesIds = new ArrayList<>();

			// Criando dirs para p�ginas negativas e positivas
			new File(f.getPath() + "/Positives").mkdir();
			new File(f.getPath() + "/Negatives").mkdir();

			try {
				int count = 0;
				for (File file : files) {
					if (file.isFile() && !(file.getName().equals("visitedLinks.txt"))) {
						String doc = Preprocess.preProcessFile(file.getPath());
						System.out.println(file.getName()+ " "+ c.classify(doc));
						System.out.println();
						if (c.classify(doc)) {
							File filetest = new File(f.getPath() + "/Positives/" + file.getName());
							writeFile(Preprocess.fileToString(file), filetest);
							filesIds.add(count);
						}
						count++;
					}
					// System.err.println("count >>>>> "+ count);

				}
			} catch (Exception e) {
			}
			getUrls(f.getPath() + "/Positives/posLinks.txt", linksList, filesIds);
		}

		// String doc = c.preProcessFile("Examples/negDoc1");

	}

	/***
	 * This method will run the Wrapper.
	 */
	public static void runWrapper(String crawlerMethod) {
		StartWrapper wrapper = new StartWrapper();
		wrapper.Start(dominios, crawlerMethod);
	
	}

	public static void writeFile(String text, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getUrls(String pathNewFile, File linkFiles, ArrayList<Integer> lines) {
		File urls = new File(pathNewFile);
		try {
			PrintWriter printWriter = new PrintWriter(urls);
			for (Integer line : lines) {
				String lineFile = (String) FileUtils.readLines(linkFiles).get(line);
				printWriter.println(lineFile);
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
