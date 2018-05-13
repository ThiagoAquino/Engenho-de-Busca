package finalApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.javatuples.Triplet;
import org.json.simple.parser.ParseException;

public class Search {

	private ArrayList<String> searchKeys;
	private String titulo;
	private String preco;
	private String genero;
	private String desenvolvedor;
	private String plataforma;
	private String idioma;
	private String idade;

	public Search(String titulo, String preco, String idioma, String genero, String idade) {
		this.titulo = titulo;
		this.idade = idade;
		this.idioma = idioma;
		this.preco = preco;
		this.genero = genero;
	}

	public Search(String t, String pr, String g, String d, String pl, String idi, String ida) {
		this.titulo = t;
		this.preco = pr;
		this.genero = g;
		this.desenvolvedor = d;
		this.plataforma = pl;
		this.idioma = idi;
		this.idade = ida;
	}

	public void PreprocessEntry() {
		this.searchKeys = new ArrayList<String>();

		if (this.titulo != null && !this.titulo.isEmpty()) {
			this.titulo = PreprocessData.applyAllPreprocess(this.titulo);
			this.searchKeys.addAll(PreprocessData.stringToTokens("titulo", this.titulo));
		}

		if (this.preco != null && !this.preco.isEmpty()) {
			//this.preco = PreprocessData.priceToRange(this.preco);
			this.searchKeys.addAll(PreprocessData.stringToTokens("preco", this.preco));
		}

		if (this.genero != null && !this.genero.isEmpty()) {
			this.genero = PreprocessData.applyAllPreprocess(this.genero);
			this.searchKeys.addAll(PreprocessData.stringToTokens("genero", this.genero));
		}

		if (this.desenvolvedor != null && !this.desenvolvedor.isEmpty()) {
			this.desenvolvedor = PreprocessData.applyAllPreprocess(this.desenvolvedor);
			this.searchKeys.addAll(PreprocessData.stringToTokens("desenvolvedor", this.desenvolvedor));
		}

		if (this.plataforma != null && !this.plataforma.isEmpty()) {
			this.plataforma = PreprocessData.applyAllPreprocess(this.plataforma);
			this.searchKeys.addAll(PreprocessData.stringToTokens("plataforma", this.plataforma));
		}

		if (this.idioma != null && !this.idioma.isEmpty()) {
			this.idioma = PreprocessData.applyAllPreprocess(this.idioma);
			this.searchKeys.addAll(PreprocessData.stringToTokens("idioma", this.idioma));
		}

		if (this.idade != null && !this.idade.isEmpty()) {
			this.idade = PreprocessData.applyAgePreprocess(this.idade);
			this.searchKeys.addAll(PreprocessData.stringToTokens("idade", this.idade));
		}
	}

	public DefaultListModel<String> getListDoc() {
		DefaultListModel<String> docsId = new DefaultListModel<String>();
		List<Triplet<Integer, Integer, Double>> temp = new LinkedList<>();

		for (String item : searchKeys) {
			if (InvertedIndexBuild.mapIndex.containsKey(item)) {
				if (temp.isEmpty()) {
					for (Triplet<Integer, Integer, Double> term : InvertedIndexBuild.mapIndex.get(item))
						temp.add(term.setAt2(
								Math.log(InvertedIndexBuild.qtdDoc / InvertedIndexBuild.mapIndex.get(item).size())));
				} else {
					for (Triplet<Integer, Integer, Double> term : InvertedIndexBuild.mapIndex.get(item)) {
						boolean exists = false;

						for (int i = 0; i < temp.size(); i++) {
							Triplet<Integer, Integer, Double> a = temp.get(i);

							if (a.getValue0() == term.getValue0()) {
								a = a.setAt1(a.getValue1() + term.getValue1()).setAt2(a.getValue2() * Math
										.log(InvertedIndexBuild.qtdDoc / InvertedIndexBuild.mapIndex.get(item).size()));
								temp.set(i, a);
								exists = true;
								continue;
							}
						}

						if (!exists)
							temp.add(term);
					}
				}
			}
		}

		Collections.sort(temp, new Comparator<Triplet<Integer, Integer, Double>>() {
			public int compare(Triplet<Integer, Integer, Double> a, Triplet<Integer, Integer, Double> b) {
				if (a.getValue1() > b.getValue1())
					return -1;
				if (a.getValue1() < b.getValue1())
					return 1;
				else
					return 0;
			}
		});

		for (Triplet<Integer, Integer, Double> t : temp)
			docsId.addElement(t.getValue0().toString());

		return docsId;
	}

	public String getDocInfo(String docId) throws FileNotFoundException, IOException, ParseException {
		return PreprocessData.docsList.get(Integer.parseInt(docId));
	}

}