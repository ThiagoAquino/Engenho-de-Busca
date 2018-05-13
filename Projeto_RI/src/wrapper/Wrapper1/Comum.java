package wrapper.Wrapper1;

import org.jsoup.nodes.Document;
import wrapper.Wrapper10.Wrapper;

public class Comum extends Wrapper {

	// public void pegarDados(String site, Arquivo arquivo) throws IOException {
	// Document doc = Jsoup.connect(site).timeout(15000).get();
	// Arquivo arq = arquivo;
	// arq.println("Link da página acessada: " + site);
	//
	//
	// /*Dados das tabelas Americanas, Submarino e Cultura*/
	//
	// if(site.contains("www.americanas.com.br") ||
	// site.contains("www.submarino.com.br") ||
	// site.contains("www.livrariacultura.com.br")){
	// ArrayList<String []> desc = new ArrayList<String []>();
	// Element b = null;
	//
	// if(site.contains("www.livrariacultura.com.br")){
	// for(Element li : doc.getElementsByAttributeValue("id",
	// "product-list-detail")){
	// b = li;
	// }
	// String texto [] = b.toString().split("</li>");
	// for (int i = 0; i < texto.length-1; i++) {
	// desc.add(texto[i].split("</b>"));
	// if(desc.get(i)[0].length() > 8){
	// desc.get(i)[0] = desc.get(i)[0].substring(desc.get(i)[0].indexOf("<b>"),
	// desc.get(i)[0].indexOf(":"));
	// desc.get(i)[0] = desc.get(i)[0].substring(3, desc.get(i)[0].length());
	// if((desc.get(i)[0].equalsIgnoreCase("Gênero")) ||
	// (desc.get(i)[0].contains("Classificação Indicativa")) ||
	// (desc.get(i)[0].equalsIgnoreCase("Áudio")) ||
	// (desc.get(i)[0].equalsIgnoreCase("Idiomas"))){
	//
	// arq.println(desc.get(i)[0] + ": " + desc.get(i)[1]);
	// }
	// }
	// }
	// } else {
	// for(Element th1 : doc.getElementsByAttributeValue("class",
	// "ficha-tecnica")){
	// b = th1;
	// }
	// String texto [] = b.toString().split("</tr>");
	// for (int i = 0; i < texto.length-1; i++) {
	// desc.add(texto[i].split("</th>"));
	// desc.get(i)[0] = desc.get(i)[0].replace("<tbody>", "");
	// desc.get(i)[0] = desc.get(i)[0].replace("<tr>", "");
	// desc.get(i)[0] = desc.get(i)[0].replace("<th>", "");
	// desc.get(i)[0] = desc.get(i)[0].replaceAll("\n", "");
	// desc.get(i)[0] = desc.get(i)[0].replaceAll(" ", "");
	// desc.get(i)[1] = desc.get(i)[1].replaceAll(" ", "");
	// desc.get(i)[1] = desc.get(i)[1].replaceAll("\n", "");
	// desc.get(i)[1] = desc.get(i)[1].replace("<td>", "");
	// desc.get(i)[1] = desc.get(i)[1].replace("</td>", "");
	//
	// if((desc.get(i)[0].equalsIgnoreCase("Gênero")) ||
	// (desc.get(i)[0].equalsIgnoreCase("Classificação indicativa"))
	// ||(desc.get(i)[0].equalsIgnoreCase("Desenvolvedor")) ||
	// (desc.get(i)[0].equalsIgnoreCase("Áudio"))||
	// (desc.get(i)[0].equalsIgnoreCase("Idiomas"))){
	// arq.println(desc.get(i)[0] + ": " + desc.get(i)[1]);
	// } else if(desc.get(i)[0].equalsIgnoreCase("FaixaEtária")){
	// arq.println("Faixa Etária: " + desc.get(i)[1]);
	// }
	// }
	// }
	// } else if(site.contains("www.magazineluiza.com.br")){
	// for(Element th1 : doc.getElementsByAttributeValue("class", "fs-row")){
	// if(th1.text().contains("Informações")){
	// arq.println(th1.text().substring(th1.text().indexOf("Marca"),
	// th1.text().indexOf("Referência")));
	//
	// }
	// if(th1.text().contains("Gênero") || th1.text().contains("Idioma") ||
	// th1.text().contains("Idade recomendada")){
	// arq.println(th1.text());
	// }
	// }
	// } else if(site.contains("www.nagem.com.br")){
	// for (Element th1 : doc.getElementsByAttributeValue("class",
	// "tabelaDescricao")) {
	// String [] text = th1.toString().split("<tr>");
	// for (int i = 0; i < text.length; i++) {
	// String [] text2 = text[i].split("</td>");
	// for (int j = 0; j < text2.length; j++) {
	// text2[j] = text2[j].substring(text2[j].indexOf(">"), text2[j].length());
	// text2[j] = text2[j].substring(1, text2[j].length());
	// }
	// for (int k = 0; k < text2.length; k++) {
	// if(text2[k].contains("Gênero") || text2[k].contains("Idioma") ||
	// text2[k].contains("Censura")){
	// arq.println(text2[k] + ": " + text2[k+1]);
	// }
	// }
	// }
	// }
	//
	// } else if(site.contains("www.fnac.com.br")){
	// for(Element th1 : doc.getElementsByAttributeValue("id",
	// "conteudoEspecificacao")){
	// String entrada = th1.toString().substring(th1.toString().indexOf("<ul>"),
	// th1.toString().indexOf("</ul>"));
	//
	// String [] text = entrada.split("</li>");
	//
	// for (int i = 0; i < text.length -1; i++) {
	// text[i] = text[i].substring(text[i].indexOf("<li>"), text[i].length());
	// text[i] = text[i].substring(4, text[i].length());
	//
	// if(text[i].contains("Gênero")){
	// arq.println(text[i]);
	// } else if( text[i].contains("Faixa etária")){
	// arq.println(text[i] + "anos");
	// }
	// }
	// }
	// }
	// }

	@Override
	public String getTitulo(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreco(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutrosDados(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

}
