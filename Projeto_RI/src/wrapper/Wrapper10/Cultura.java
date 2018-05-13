package wrapper.Wrapper10;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Cultura extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("itemprop", "name");
		if (elem != null) {
			return elem.text();
		} else {
			return "Sem título";
		}
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "price");
		if (elem != null) {
			if (elem.text().length() > 40) {
				String valor = elem.text().substring(elem.text().indexOf("R$"), elem.text().indexOf("pr"));
				return valor;
			} else if (elem.text().length() >= 25 && elem.text().length() <= 40 || elem.text().contains(" R$ ")) {
				String valor = elem.text().substring(elem.text().indexOf("R$"), elem.text().indexOf(" R$ "));
				return valor;
			} else if(elem.text().length() >= 11 && elem.text().length() <= 24) {
				String valor = elem.text().substring(elem.text().indexOf("R$"), elem.text().indexOf("pr"));
				return valor;
			} else {
				return elem.text();
			}
		} else
			return "Sem preço";
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("id", "product-list-detail");
		ArrayList<String[]> desc = new ArrayList<String[]>();
		StringBuffer sb = new StringBuffer("");

		if (elem != null) {
			for (Element e : elem) {
				String texto[] = e.toString().toLowerCase().split("</li>");

				// Formatação da saída dos dados
				for (int i = 0; i < texto.length - 1; i++) {
					desc.add(texto[i].split("</b>"));

					if (desc.get(i)[0].length() > 8) {
						desc.get(i)[0] = desc.get(i)[0].substring(desc.get(i)[0].indexOf("<b>"),
								desc.get(i)[0].indexOf(":"));
						desc.get(i)[0] = desc.get(i)[0].substring(3, desc.get(i)[0].length());

						if ((desc.get(i)[0].equals("gênero")) || (desc.get(i)[0].contains("classificação indicativa"))
								|| (desc.get(i)[0].equals("áudio")) || (desc.get(i)[0].equals("idiomas"))) {

							sb.append(desc.get(i)[0] + ": " + desc.get(i)[1] + "\r\n");
						}
					}
				}
			}

			return sb.toString();
		} else
			return "";
	}
}