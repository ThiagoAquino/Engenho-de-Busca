package wrapper.Wrapper10;

import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Saraiva extends Wrapper {

	@Override
	public String getTitulo(Document doc) {
		String titulo = doc.title();

		if (!titulo.equals("")) {
			return titulo;
		} else {
			return "Sem título";

		}
	}

	@Override
	public String getPreco(Document doc) {
		Elements elem = doc.getElementsByAttributeValue("class", "special-price");
		if (elem != null && elem.size() > 0) {
			String valor = elem.toString().substring(elem.toString().indexOf("R$"),
					elem.toString().indexOf("</strong>"));
			return valor;
		} else {

			return "Sem preço";
		}
	}

	@Override
	public String getOutrosDados(Document doc) {

		Elements o = doc.getElementsByAttributeValue("itemprop", "title");
		String genero = o.text();
		StringBuffer sb = new StringBuffer("");
		if(genero.length() > 0){
			sb.append("Gênero: " + genero.substring(genero.indexOf("Jogos")) + "\r\n");
		}
		ArrayList<Object> desc = new ArrayList<Object>();
		ArrayList<Object> carc = new ArrayList<Object>();
		ArrayList<Box> fim = new ArrayList<Box>();

		// Pegar os elementos da tabela TH
		for (Element th : doc.getElementsByAttributeValue("class", "label")) {
			desc.add(th.text());
		}

		// Pegar os elementos da tabela TD
		for (Element td : doc.getElementsByAttributeValue("class", "data")) {
			carc.add(td.text());
		}

		if (desc.size() > 0) {
			int i = 0;
			while (i < desc.size() && i < carc.size()) {
				Box box = new Box(desc.get(i), carc.get(i));
				i++;
				fim.add(box);
			}
			i = 0;
			while (i < fim.size()) {
				if (fim.get(i).desc.toString().equalsIgnoreCase("Marca")
						|| fim.get(i).desc.toString().equalsIgnoreCase("Classificação Indicativa")
						|| fim.get(i).desc.toString().equalsIgnoreCase("Idioma do Áudio")) {
					sb.append((fim.get(i).desc.toString() + ": " + fim.get(i).carac.toString()) + "\r\n");
				}
				i++;
			}

			return sb.toString();
		} else {
			return "";
		}
	}

}


class Box {
	Object desc;
	Object carac;

	public Box(Object desc, Object carac) {
		this.desc = desc;
		this.carac = carac;
	}
}
