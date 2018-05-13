package wrapper.Wrapper10;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fast extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("itemprop", "name");
		if (elem != null) {
			if(elem.text().contains("-")){
				return elem.text().substring(0, elem.text().indexOf("-"));				
			} else {
				return elem.text();
			}
		} else {
			return "Sem título";
		}
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "preco-avista precoAvista");
		if (elem != null && !elem.toString().equals("")) {
			if(elem.text().length() < 10) {
				return elem.text();
			} else {
				return elem.text().substring(0, elem.text().indexOf(" R$ "));
			}
		} else
			return "Sem preço";
	}

	@Override
	public String getOutrosDados(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("id", "descricao");
		StringBuffer sb = new StringBuffer("");
		if (elem != null) {
			for (Element e : elem) {
				String[] text = e.toString().split("<li>");
				String[] text2 = null;
				String[] text3;

				for (int i = 0; i < text.length; i++) {
					text2 = text[i].split("<strong>");
					for (int j = 0; j < text2.length; j++) {
						text3 = text2[j].split("size=");
						if (text2[j].contains("<b>")) {
							for (int k = 0; k < text3.length; k++) {
								if (k == 1 && i != 8) {
									if(text3[k].contains("<b>") && text3[k].contains("</b>")){
										sb.append(text3[k].substring(text3[k].indexOf("<b>") + 3, text3[k].indexOf("</b>"))
												+ "\r\n");
									}
								}
							}

						} else if (text3.length > 1 && text3[1].contains("A partir de ") && text3 != null) {
							if (text3[1].contains("&")) {
								sb.append(text3[1].substring(text3[1].indexOf("A partir de "), (text3[1].indexOf("&")))
										+ " Anos\n");
							} else {
								sb.append(
										text3[1].substring(text3[1].indexOf("A partir de "), (text3[1].indexOf("anos")))
										+ "Anos\n");
							}

						} else {
							if (text2[j].contains("Desenvolvedor")) {
								sb.append(text2[j].substring(0, text2[j].indexOf("</li"))+ "\r\n");
							} else if (text2[j].contains("Faixa etária")) {
								sb.append(text2[j].substring(0, text2[j].indexOf("</li")) + "\r\n");
							} else if (text2[j].contains("Gênero")) {
								if (text2[j].contains("</li")){
									sb.append(text2[j].substring(0, text2[j].indexOf("</li")) + "\r\n");
								} else if (text2[j].contains("</strong")) {
									sb.append(text2[j].substring(0, text2[j].indexOf("</strong")) + "\r\n");
								}
							}
						}
					}
				}
			}

			return sb.toString();
		} else
			return "";
	}
}
