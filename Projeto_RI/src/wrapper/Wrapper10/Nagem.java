package wrapper.Wrapper10;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Nagem extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class","tituloProduto produtoDescricao");

		if (elem != null)
			return elem.text();
		else
			return "Sem título";
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValueMatching("class", "precoDetalhe");

		if (elem != null)
			return elem.text();
		else
			return "Sem preço";
	}

	@Override
	public String getOutrosDados(Document doc) {
		StringBuffer sb = new StringBuffer("");
		 Elements elem = doc.getElementsByAttributeValue("class", "area-especificacao");

		if (elem != null) {
			for (Element e : elem) {
				String[] text = e.toString().split("<tr>");

				for (int i = 0; i < text.length; i++) {
					String[] text2 = text[i].split("</td>");
					for (int j = 0; j < text2.length; j++) {
						text2[j] = text2[j].substring(text2[j].indexOf(">"), text2[j].length());
						text2[j] = text2[j].substring(1, text2[j].length());
					}
					for (int k = 0; k < text2.length; k++) {
						if (text2[k].contains("nero") || text2[k].contains("Idioma")
								|| text2[k].contains("Censura")) {
							text2[k] = text2[k].replace(" <strong>", "");
							text2[k] = text2[k].replace("</strong> ", "");
							if(text2[k].contains("<br>")){
								text2[k] = text2[k].replace("<br>", ""); 
							}
							if(text2[k].length() > 50){
								text2[k] = "";
								text2[k+1] = "";
							}
							sb.append(text2[k] + ": " + text2[k + 1] + "\r\n");
						}
					}
				}
			}

			return sb.toString();
		} else
			return "";
		 
		
	}

}