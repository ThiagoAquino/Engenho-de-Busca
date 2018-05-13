package wrapper.oldClasses;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/** NÃO SERVE POR CAUSA DO CRAWLER **/

public class Pontofrio {

	public static void main(String[] args) {
		try {
			String u = "http://www.pontofrio.com.br/Games/Playstation4/JogosPlaystation4/Jogo-Call-of-Duty-Black-Ops-III-PS4-6304886.html?recsource=busca-int&rectype=busca-2518";
//			String v = "http://www.pontofrio.com.br/Games/Playstation4/JogosPlaystation4/Jogo-Terra-Media-Sombras-de-Mordor-Goty-PS4-6616597.html?recsource=wproddisp&rectype=w8";
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
			Elements idade = doc.getElementsByAttributeValue("class", "Classificacao-indicativa");
			System.out.println(idade.text());
			
			//Idioma
			Elements idioma = doc.getElementsByAttributeValue("class", "Idioma-do-jogo");
			System.out.println(idioma.text());
			
			


		} catch (IOException e) {
			e.printStackTrace();		}
	}

}
