package wrapper.oldClasses;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Casasbahia {

	public static void main(String[] args) {

		try {
			String u = "http://www.casasbahia.com.br/Games/Playstation4/JogosPlaystation4/Jogo-Call-of-Duty-Black-Ops-III-PS4-6304886.html?recsource=busca-int&rectype=busca-2518";
			Document doc = Jsoup.connect(u).get();
			System.out.println("Site acessado: " + u);
			
			// Nome do Jogo
			Elements nome = doc.getElementsByAttributeValue("class","fn name");
			String title = nome.text().substring(5, nome.text().indexOf("(C�d"));
			System.out.println("T�tulo: " + title);

			// Pre�o do jogo
			Elements preco = doc.getElementsByAttributeValue("class","sale price");
			String [] valor = preco.text().split(" "); 
			System.out.println("Pre�o: R$ " +  valor[0]);


			//G�nero
			Elements genero = doc.getElementsByAttributeValue("class", "Genero even");
			System.out.println(genero.text());

			//Faixa Et�ria
			Elements idade = doc.getElementsByAttributeValue("class", "Classificacao-indicativa");
			System.out.println(idade.text());

			//Idioma
			Elements idioma = doc.getElementsByAttributeValue("class", "Idioma-do-jogo");
			System.out.println(idioma.text());

		} catch (IOException e) {
			e.printStackTrace();		}
	}
}
