package wrapper.Wrapper10;

import org.jsoup.nodes.Document;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Submarino extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "mp-tit-name prodTitle");
		if (elem != null && elem.text().contains("Game")) {
			return elem.text().substring(elem.text().indexOf("Game"), elem.text().length());
		} else {
			return "Sem título";
		}
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("itemprop", "price/salesPrice");
		if (elem != null) {
			return elem.text();
		} else {
			return "Sem preço";
		}
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "ficha-tecnica");
		ArrayList<String[]> desc = new ArrayList<String[]>();
		StringBuffer sb = new StringBuffer("");

		if (elem != null) {
			for (Element e : elem) {
				String texto[] = e.toString().toLowerCase().split("</tr>");

				// Formatação da saída dos dados
				for (int i = 0; i < texto.length - 1; i++) {
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

					if ((desc.get(i)[0].equalsIgnoreCase("Gênero")) || (desc.get(i)[0].equalsIgnoreCase("Classificação indicativa"))
							|| (desc.get(i)[0].equalsIgnoreCase("Desenvolvedor")) || (desc.get(i)[0].equalsIgnoreCase("Áudio"))
							|| (desc.get(i)[0].equalsIgnoreCase("idiomas"))) {

						sb.append(desc.get(i)[0] + ": " + desc.get(i)[1] + "\r\n");
					} else if (desc.get(i)[0].equalsIgnoreCase("FaixaEtária")) {
						sb.append("Faixa Etária: " + desc.get(i)[1] + "\r\n");
					}
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}
}









