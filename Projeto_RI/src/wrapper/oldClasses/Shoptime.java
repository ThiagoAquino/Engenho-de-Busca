package wrapper.oldClasses;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/** NÃO SERVE POR CAUSA DO CRAWLER **/

public class Shoptime {
	public static void main(String[] args) {
		try {
			String u = "http://www.shoptime.com.br/produto/124615509/game-call-of-duty-black-ops-3-ps4";
			Document doc = Jsoup.connect(u).get();

			System.out.println("Link da página acessada: " + u);
			System.out.println();

			// Nome do Jogo
			Object nome = doc.getElementsByTag ("title");
			String title = nome.toString().substring(nome.toString().indexOf("Game "), nome.toString().indexOf("- Shoptime")); 
			System.out.println("Título: " + title);


			// Preço do jogo
			Object preco = doc.getElementsByAttributeValueEnding("class","p-price");
			String p = preco.toString();
			String valor = p.substring(p.indexOf("R$"));
			int t = valor.indexOf("</span>");
			valor = valor.substring(0, t);
			System.out.println("Preço: " +  valor);


			ArrayList<String []> desc = new ArrayList<String []>();


			// Extraindo dados da tabela
			Object a = null;
			for(Element th1 : doc.getElementsByAttributeValueStarting("class","table-info-tec")){
				a = th1;
			}
			String texto [] = a.toString().split("</tr>");

			//Formatação da saída dos dados

			for (int i = 0; i < texto.length-1; i++) {
				desc.add(texto[i].split("</th>"));
				desc.get(i)[0] = desc.get(i)[0].replace("<tbody>", "");
				desc.get(i)[0] = desc.get(i)[0].replace("<tr>", "");
				desc.get(i)[0] = desc.get(i)[0].replace("<th>", "");
				desc.get(i)[0] = desc.get(i)[0].replaceAll("\n", "");
				desc.get(i)[0] = desc.get(i)[0].replaceAll(" ", "");
				desc.get(i)[1] = desc.get(i)[1].replaceAll(" ", "");
				desc.get(i)[1] = desc.get(i)[1].replaceAll("\n", "");
				desc.get(i)[1] = desc.get(i)[1].replace("<td>", "");
				desc.get(i)[1] = desc.get(i)[1].replace("</td>", "");

				if((desc.get(i)[0].equalsIgnoreCase("Gênero")) || (desc.get(i)[0].equalsIgnoreCase("Classificação indicativa")) ||(desc.get(i)[0].equalsIgnoreCase("Desenvolvedor")) || (desc.get(i)[0].equalsIgnoreCase("Áudio")) 
						|| (desc.get(i)[0].equalsIgnoreCase("Idiomas")) || (desc.get(i)[0].equalsIgnoreCase("Faixa Etária"))){

					System.out.println(desc.get(i)[0] + ": " + desc.get(i)[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
