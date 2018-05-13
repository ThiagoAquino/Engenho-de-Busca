package wrapper.Wrapper10;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Magazineluiza extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "title-out-of-stock-product");

		if (elem != null)
			return elem.text();
		else
			return "Sem t�tulo";
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "right-price");

		if (elem != null)
			return elem.text();
		else
			return "Sem pre�o";
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "fs-row");
		StringBuffer sb = new StringBuffer("");

		if (elem != null) {
			for (Element e : elem) {
				if (e.text().contains("Informa��es"))
					sb.append(e.text().substring(e.text().indexOf("Marca"), e.text().indexOf("Refer�ncia")) + "\r\n");

				if (e.text().contains("G�nero") || e.text().contains("Idioma")
						|| e.text().contains("Idade recomendada"))
					sb.append(e.text() + "\r\n");
			}

			return sb.toString();
		} else
			return "";
	}
}
