package wrapper.Wrapper10;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fnac extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		String title = doc.title();
		if (!title.equals("")){
			return title.substring(0, title.indexOf(" -"));
		} else {
			return "Sem título";
		}
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "skuBestPrice");
		if (elem != null)
			return elem.text();
		else
			return "Sem preço";
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("id", "conteudoEspecificacao");
		StringBuffer sb = new StringBuffer("");

		if (elem != null) {
			for (Element e : elem) {
				String entrada = e.toString().substring(e.toString().indexOf("<ul>"), e.toString().indexOf("</ul>"));
				String[] text = entrada.split("</li>");

				for (int i = 0; i < text.length - 1; i++) {
					text[i] = text[i].substring(text[i].indexOf("<li>"), text[i].length());
					text[i] = text[i].substring(4, text[i].length());

					if (text[i].contains("Gênero")) {
						sb.append(text[i] + "\n");
					} else if (text[i].contains("Faixa etária")) {
						sb.append(text[i] + "anos\n");
					}
				}
			}

			return sb.toString();
		} else
			return "";
	}
}