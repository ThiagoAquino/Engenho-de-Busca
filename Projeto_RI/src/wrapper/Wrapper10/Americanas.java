package wrapper.Wrapper10;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Americanas extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = (doc.getElementsByAttributeValue("class", "mp-tit-name prodTitle"));
		if(!elem.toString().equals("")){
			if (elem != null && !elem.toString().equals("")){
				//String saida = elem.text().substring(5, elem.text().indexOf("- "));
				String saida = elem.text().substring(5);
				return saida;
			} else
				return "Sem t�tulo";
		} else {
			elem = doc.getElementsByAttributeValue("itemprop", "name");
			if(elem != null && ! elem.toString().equals("")){
				return elem.text().substring(5, elem.text().indexOf("- "));
			} else{
				return "Sem t�tulo";
			}
		}

	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("itemprop", "price/salesPrice");

		if (elem != null)
			return elem.text();
		else
			return "Sem pre�o";
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "ficha-tecnica");
		ArrayList<String[]> desc = new ArrayList<String[]>();
		StringBuffer sb = new StringBuffer("");

		if (elem != null) {
			for (Element e : elem) {
				String texto[] = e.toString().toLowerCase().split("</tr>");

				// Formata��o da sa�da dos dados
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

					if ((desc.get(i)[0].equals("g�nero")) || (desc.get(i)[0].equals("classifica��o indicativa"))
							|| (desc.get(i)[0].equals("desenvolvedor")) || (desc.get(i)[0].equals("�udio"))
							|| (desc.get(i)[0].equals("idiomas"))) {

						sb.append(desc.get(i)[0] + ": " + desc.get(i)[1] + " \r\n");
					} else if (desc.get(i)[0].equalsIgnoreCase("FaixaEt�ria")) {
						sb.append("Faixa Et�ria: " + desc.get(i)[1] + "\r\n");
					}
				}
			}

			return sb.toString();
		} else {
			return "";
		}
	}
}