package wrapper.Wrapper10;

import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Steam extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("itemprop", "name");

		if (elem != null)
			return elem.text();
		else
			return "Sem título";
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "game_purchase_price price");
		if(elem == null || elem.text().equals("")){
			elem = doc.getElementsByAttributeValue("class", "discount_final_price");
		}

		if (elem != null){
			if(elem.text().length() >=10) {
				return elem.text().substring(0, elem.text().indexOf("R$"));
			} else {
				return elem.text();
			}
		} else {
			return "Sem preço";
		}
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "details_block");
		StringBuffer sb = new StringBuffer("");

		if (elem != null) {
			for (Element e : elem) {
				String[] show = e.text().toLowerCase().split(": ");
				for (int i = 0; i < show.length; i++) {
					if (show[i].contains("nero")) {
						String saida = "Gênero: " +show[i + 1];
						if(saida.contains("desenvolvedor")){
							saida = saida.replace("desenvolvedor", "");
							sb.append(saida + "\r\n");
						}else{
							sb.append(saida + "\r\n");
						}
					} else if (show[i].contains("desenvolvedor")) {
						String saida = "Desenvolvedor: " + show[i + 1];
						if(saida.contains("distribuidora")){
							saida = saida.replace("distribuidora", "");
							sb.append(saida + "\r\n");
						}else{
							sb.append(saida + "\r\n");
						}
					}
				}
			}

			return sb.toString();
		} else
			return "";
	}
}