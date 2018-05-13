package wrapper.oldClasses;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/** N�O SERVE POR CAUSA DO CRAWLER **/

public class Shoptime {
	public static void main(String[] args) {
		try {
			String u = "http://www.shoptime.com.br/produto/124615509/game-call-of-duty-black-ops-3-ps4";
			Document doc = Jsoup.connect(u).get();

			System.out.println("Link da p�gina acessada: " + u);
			System.out.println();

			// Nome do Jogo
			Object nome = doc.getElementsByTag ("title");
			String title = nome.toString().substring(nome.toString().indexOf("Game "), nome.toString().indexOf("- Shoptime")); 
			System.out.println("T�tulo: " + title);


			// Pre�o do jogo
			Object preco = doc.getElementsByAttributeValueEnding("class","p-price");
			String p = preco.toString();
			String valor = p.substring(p.indexOf("R$"));
			int t = valor.indexOf("</span>");
			valor = valor.substring(0, t);
			System.out.println("Pre�o: " +  valor);


			ArrayList<String []> desc = new ArrayList<String []>();


			// Extraindo dados da tabela
			Object a = null;
			for(Element th1 : doc.getElementsByAttributeValueStarting("class","table-info-tec")){
				a = th1;
			}
			String texto [] = a.toString().split("</tr>");

			//Formata��o da sa�da dos dados

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

				if((desc.get(i)[0].equalsIgnoreCase("G�nero")) || (desc.get(i)[0].equalsIgnoreCase("Classifica��o indicativa")) ||(desc.get(i)[0].equalsIgnoreCase("Desenvolvedor")) || (desc.get(i)[0].equalsIgnoreCase("�udio")) 
						|| (desc.get(i)[0].equalsIgnoreCase("Idiomas")) || (desc.get(i)[0].equalsIgnoreCase("Faixa Et�ria"))){

					System.out.println(desc.get(i)[0] + ": " + desc.get(i)[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
