/*
 * Universidade Federal de Pernambuco
 * Centro de Informática (CIn)
 * Recuperação de Informação
 * 
 * Ana Caroline Ferreira de França (acff)
 * Thiago Aquino Santos (tas4)
 * Victor Sin Yu Chen (vsyc)
 */

package crawler;

import java.util.Date;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Here we create all Crawlers.
 * 
 * @author Victor Chen
 *
 */
public class SpiderFactory {

	// Constants
	private static final String domain[] = { "americanas", "fastgames", "fnac", "livrariacultura", "magazineluiza",
			"nagem", "saraiva", "steampowered", "submarino", "walmart" };
	public static String ARTIFACT_PATH = "..\\Projeto_RI\\artefatos\\";

	// Variables
	private Thread threads[];
	public static boolean error = false;

	// Constructor
	public SpiderFactory() {
		this.threads = new Thread[10];
	}

	/***
	 * This method starts the Crawlers.
	 * 
	 * @param type
	 *            search method to be used by the Crawler
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void startCrawlers(String type) throws IOException, InterruptedException {
		File f = new File(ARTIFACT_PATH);

		if (!f.exists())
			f.mkdirs();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ARTIFACT_PATH + "\\log.txt", true)));

		if (type == "bfs")
			startBfsCrawlers(pw);
		else
			startHeuristicCrawlers(pw);

		for (Thread t : this.threads)
			t.join();

		if (SpiderFactory.error) {
			pw.print(new Date().toString());
			pw.println(": All Crawlers finished.");
		} else {
			pw.print(new Date().toString());
			pw.println(": All Crawlers successfully finished.");
		}

		pw.close();
	}

	/***
	 * This method runs the BFS Crawlers.
	 * 
	 * @throws RuntimeException
	 */
	private void startBfsCrawlers(PrintWriter pw) throws RuntimeException {
		ARTIFACT_PATH += "bfs\\";
		for (int i = 0; i < 10; i++)
			(this.threads[i] = new Thread(new BfsSpider(SpiderFactory.domain[i], pw))).start();
	}

	/***
	 * This method runs the Heuristic Crawlers.
	 * 
	 * @throws RuntimeException
	 */
	private void startHeuristicCrawlers(PrintWriter pw) throws RuntimeException {
		ARTIFACT_PATH += "heuristic\\";
		for (int i = 0; i < 10; i++)
			(this.threads[i] = new Thread(new HeuristicSpider(SpiderFactory.domain[i], pw))).start();
	}
}
