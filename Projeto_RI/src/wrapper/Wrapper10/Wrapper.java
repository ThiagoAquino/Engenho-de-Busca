package wrapper.Wrapper10;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class Wrapper {
	public static String ARTIFACT_PATH = "..\\Projeto_RI\\artefatos";

	public void pegarDados(PrintWriter pw, String path, String site) throws IOException {
		File file = new File(path);
		Document doc = Jsoup.parse(file, "UTF-8");

		String titulo = getTitulo(doc);
		if(!(titulo.equalsIgnoreCase("Sem título") || titulo.equalsIgnoreCase(""))){
			String preco = getPreco(doc);
			String outrosDados = getOutrosDados(doc);
			synchronized (pw) {
				//pw.println("URL: " + site);
				pw.println("Titulo: " + titulo);
				pw.println("Preco: " + preco);
				pw.println(outrosDados);
				pw.println();
				pw.flush();
			}
		}
		synchronized (pw) {
			pw.println();
			pw.flush();
		}
	}

	public abstract String getTitulo(Document doc);

	public abstract String getPreco(Document doc);

	public abstract String getOutrosDados(Document doc);
}

