package finalfinal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.javatuples.Pair;

public class Search {

	private static ArrayList<String> searchKeys;
	private List<double[]> tfidfVector;
	private InvertedIndex ii;
	private String titulo;
	private String preco;
	private String genero;
	private String desenvolvedor;
	private String plataforma;
	private String idioma;
	private String idade;
	private boolean frequencyType;

	public Search(InvertedIndex invertedIndex, String t, String pr, String g, String d, String pl, String idi,
			String ida, boolean frequenceType) {
		searchKeys = new ArrayList<String>();
		this.ii = invertedIndex;
		this.titulo = t;
		this.preco = pr;
		this.genero = g;
		this.desenvolvedor = d;
		this.plataforma = pl;
		this.idioma = idi;
		this.idade = ida;
		this.frequencyType = frequenceType;
	}

	public void PreprocessEntry() {
		PreprocessData p = new PreprocessData();

		if (this.titulo != null && !this.titulo.isEmpty()) {
			this.titulo = p.standardPreprocess(this.titulo);
			searchKeys.addAll(p.stringToTokens("titulo", this.titulo));
		}

		if (this.preco != null && !this.preco.isEmpty()) {
			searchKeys.addAll(p.stringToTokens("preco", this.preco));
		}

		if (this.genero != null && !this.genero.isEmpty()) {
			this.genero = p.standardPreprocess(this.genero);
			searchKeys.addAll(p.stringToTokens("genero", this.genero));
		}

		if (this.desenvolvedor != null && !this.desenvolvedor.isEmpty()) {
			this.desenvolvedor = p.standardPreprocess(this.desenvolvedor);
			searchKeys.addAll(p.stringToTokens("desenvolvedor", this.desenvolvedor));
		}

		if (this.plataforma != null && !this.plataforma.isEmpty()) {
			this.plataforma = p.standardPreprocess(this.plataforma);
			searchKeys.addAll(p.stringToTokens("plataforma", this.plataforma));
		}

		if (this.idioma != null && !this.idioma.isEmpty()) {
			this.idioma = p.standardPreprocess(this.idioma);
			searchKeys.addAll(p.stringToTokens("idioma", this.idioma));
		}

		if (this.idade != null && !this.idade.isEmpty()) {
			this.idade = p.agePreprocess(this.idade);
			searchKeys.addAll(p.stringToTokens("idade", this.idade));
		}

		List<String> terms = new ArrayList<>();

		for (int i = 0; i < searchKeys.size(); i++) {
			String s = searchKeys.get(i);
			terms.add(s.substring(s.indexOf('.') + 1));
		}

		tfidfVector = this.ii.tfIdfCalculator(this.ii.termsProducts, terms);
	}

	public DefaultListModel<String> searchForAny() {
		DefaultListModel<String> docsId = new DefaultListModel<String>();
		List<Tuple> temp = new LinkedList<>();

		for (String item : searchKeys) {
			int index = 0;

			if (this.ii.mapIndex.containsKey(item)) {
				for (Tuple term : this.ii.mapIndex.get(item)) {
					boolean exists = false;

					if (frequencyType) {
						term.setTermFrequency(this.ii.getCosineSimilarity(
								this.ii.tfidfDocsVector.get(term.getDocId() - 1), tfidfVector.get(index++)));
					}

					for (int i = 0; i < temp.size(); i++) {
						Tuple t = temp.get(i);

						if (t.getDocId() == term.getDocId()) {
							t.setTermFrequency(t.getTermFrequency() + term.getTermFrequency());
							temp.set(i, t);
							exists = true;
						}
					}

					if (!exists)
						temp.add(term);
				}
			}

			index++;
		}

		Collections.sort(temp, new Comparator<Tuple>() {
			public int compare(Tuple a, Tuple b) {
				if (a.getTermFrequency() > b.getTermFrequency())
					return -1;
				else if (a.getTermFrequency() < b.getTermFrequency())
					return 1;
				else
					return 0;
			}
		});

		for (Tuple t : temp)
			docsId.addElement(Integer.toString(t.getDocId()));

		return docsId;
	}

	public DefaultListModel<String> searchForAll() {
		DefaultListModel<String> docsId = new DefaultListModel<String>();
		List<Pair<Tuple, Boolean>> temp = new LinkedList<>();
		List<Tuple> temp2 = new LinkedList<>();

		for (String item : searchKeys) {
			boolean primeiro = true;
			int index = 0;

			if (this.ii.mapIndex.containsKey(item)) {
				if (temp.isEmpty() && primeiro) {
					for (Tuple term : this.ii.mapIndex.get(item))
						temp.add(new Pair<Tuple, Boolean>(term, true));
					primeiro = false;
				} else {
					for (Tuple term : this.ii.mapIndex.get(item)) {
						if (frequencyType) {
							term.setTermFrequency(this.ii.getCosineSimilarity(
									this.ii.tfidfDocsVector.get(term.getDocId() - 1), tfidfVector.get(index++)));
						}

						for (int i = 0; i < temp.size(); i++) {
							Pair<Tuple, Boolean> p = temp.get(i);
							Tuple t = p.getValue0();
							System.out.println(t.getDocId() + " " + term.getDocId() + ": " +(t.getDocId().equals(term.getDocId())));
							if (t.getDocId().equals(term.getDocId())) {
								t.setTermFrequency(t.getTermFrequency() + term.getTermFrequency());
								p = p.setAt0(t);
								p = p.setAt1(true);
								temp.set(i, p);
							}
						}
					}
				}

				int i;

				for (i = temp.size() - 1; i >= 0; i--) {
					if (temp.get(i).getValue1() == false)
						temp.remove(i);
				}

				// Outro for pois o tamanho da lista pode ser alterado.
				for (i = 0; i < temp.size(); i++)
					temp.set(i, temp.get(i).setAt1(false));
			}

			index++;
		}

		for (int i = 0; i < temp.size(); i++) {
			temp2.add(temp.get(i).getValue0());
		}

		Collections.sort(temp2, new Comparator<Tuple>() {
			public int compare(Tuple a, Tuple b) {
				if (a.getTermFrequency() > b.getTermFrequency())
					return -1;
				else if (a.getTermFrequency() < b.getTermFrequency())
					return 1;
				else
					return 0;
			}
		});

		for (Tuple t : temp2)
			docsId.addElement(Integer.toString(t.getDocId()));

		return docsId;
	}
}
