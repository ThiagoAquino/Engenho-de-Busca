package wrapper.Wrapper10;

import javax.lang.model.element.Element;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Walmart extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		/*
		String title = doc.title();
		//System.out.println(title);
		if (!title.equals("") && title.contains("Game")) {
			return title.substring(0, doc.title().indexOf("Games"));
		}else if (!title.equals("") && title.contains("-")) {
			return title.substring(0, title.indexOf("-"));
		}else {
			return "Sem título";
		}
		 */
		Elements elem = doc.getElementsByAttributeValue("class", "product-title");
		//if(!(elem.text().contains("PS4"))) {
			//if(elem.text().contains("para")){
				//System.out.println(elem.text().substring(0, elem.text().indexOf("para")));
				//return elem.text().substring(0, elem.text().indexOf("para"));			
			//} else {
				//System.out.println(elem.text());
			//	return elem.text();
			//}
		//} else {
		//	System.out.println(elem.text());
			return elem.text();
		//}
	}



	@Override
	public String getPreco(Document doc) {
		Elements o = doc.getElementsByAttributeValue("class", "int");
		Elements p = doc.getElementsByAttributeValue("class", "dec");

		if (o != null) {
			if(o.text().contains(" ")){
				String inte = o.text().substring(0, o.text().indexOf(' '));
				String dec = p.text().substring(0, p.text().indexOf(' '));
				return (inte + dec);
			} else {
				return (o.text() + p.text());
			}
		} else {
			return "Sem preço";
		}
	}

	@Override
	public String getOutrosDados(Document doc) {
		StringBuffer sb = new StringBuffer("");
		Elements genero = doc.getElementsByAttributeValue("class", "value-field Genero");
		Elements marca = doc.getElementsByAttributeValue("itemprop", "brand");
		Elements faixa = doc.getElementsByAttributeValue("class", "value-field Faixa-Etaria");
		Elements idioma = doc.getElementsByAttributeValue("class", "value-field Idiomas-Audio");

		if (genero != null && ! genero.toString().equals("")) {
			sb.append("Gênero: " + genero.text().substring(0, genero.text().length()) + "\r\n");
		}

		if (marca != null && !marca.toString().equals("")) {
			sb.append("Marca: " + marca.text().substring(0, marca.text().length()) + "\r\n");
		}

		if (faixa != null && !faixa.toString().equals("")) {
			sb.append("Faixa: " + faixa.text().substring(0, faixa.text().length()) + "\r\n");
		}

		if (idioma != null && !idioma.toString().equals("")) {
			sb.append("Idioma: " + idioma.text().substring(0, idioma.text().length()) + "\r\n");
		}

		return sb.toString();
	}
}
