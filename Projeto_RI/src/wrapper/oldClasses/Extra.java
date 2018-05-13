package wrapper.oldClasses;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Extra {

	public static void main(String[] args) {
		try {
			String u = "http://www.extra.com.br/Games/Playstation4/JogosPlaystation4/Jogo-Tom-Clancy-s-The-Division-Limited-Edition-PS4-7223752.html?recsource=wcateg&rectype=w18";
			Document doc = Jsoup.connect(u).get();
			System.out.println("Site acessado: " + u);

			// Nome do Jogo
			Elements nome = doc.getElementsByAttributeValue("class","fn name");
			String title = nome.text().substring(5, nome.text().indexOf("(Cód"));
			System.out.println("Título: " + title);

			// Preço do jogo
			Elements preco = doc.getElementsByAttributeValue("class","sale price");
			String [] valor = preco.text().split(" "); 
			System.out.println("Preço: R$ " +  valor[0]);


			//Gênero
			Elements genero = doc.getElementsByAttributeValue("class", "Genero");
			System.out.println(genero.text());

			//Faixa Etária
			Elements idade = doc.getElementsByAttributeValue("class", "Classificacao-indicativa even");
			System.out.println(idade.text());

			//Idioma
			Elements idioma = doc.getElementsByAttributeValue("class", "Idioma-do-jogo even");
			System.out.println(idioma.text());


		} catch (IOException e) {
			e.printStackTrace();		}
	}

}
