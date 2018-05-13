package finalfinal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class InvertedIndex {

	public final String PATH = "..\\Projeto_RI\\";

	// HashMap<String term, Integer id, Integer rawFrequency>
	public Map<String, ArrayList<Tuple>> mapIndex = new HashMap<String, ArrayList<Tuple>>();

	// HashMap<Integer Id, String doc>
	public Map<Integer, String> products = new HashMap<>();

	// List<String termsInObject>
	// todos os termos em cada um dos objetos
	public List<String> termsProducts = new ArrayList<>();

	// List<String terms>
	// termos existentes nos objetos, sem repeticao
	public List<String> uniqueTermsProducts = new ArrayList<String>();

	// List<double[] termsTfIdfVector>
	// vetores com o TF/IDF de todos os documentos
	public List<double[]> tfidfDocsVector = new ArrayList<>();

	// Quartiles
	public int firstQuartile;
	public int secondQuartile;
	public int thirdQuartile;
	
	// Main method
	public void run() throws FileNotFoundException, ParseException, IOException {
		PreprocessData l = new PreprocessData(PATH);

		// READ JSON FILE
		JSONArray objects = l.readJSONFile();

		// SAVE THE OBJECTS
		eachOneInItsHouse(objects);

		// GET THE PRINTABLE DOCUMENTS
		products = l.getDocsList();
		firstQuartile = l.getFirstQuartile();
		secondQuartile = l.getSecondQuartile();
		thirdQuartile = l.getThirdQuartile();

		// CALCULATE THE TF-IDF VECTOR
		tfidfDocsVector = tfIdfCalculator(termsProducts, uniqueTermsProducts);
	}

	// Secondary methods
	public void eachOneInItsHouse(JSONArray objects) {
		for (Object object : objects) {
			JSONObject objectInfo = (JSONObject) object;
			String productTerms = "";
			int productId = (int) objectInfo.get("id");

			for (Object key : objectInfo.keySet()) {
				String objectKey = (String) key;

				if (objectInfo.get(objectKey) != null && !objectKey.equals("url") && !objectKey.equals("id")) {
					String value = (String) objectInfo.get(objectKey);
					addToMap(productId, objectKey, value);
					addToUniqueProducts(value);
					productTerms += value + " ";
				}
			}

			addToTermsProducts(productId, productTerms);
		}
	}

	// Auxiliary methods
	public List<double[]> tfIdfCalculator(List<String> termsProducts, List<String> uniqueTerms) {
		TfIdf calc = new TfIdf();
		double tf;
		double idf;
		List<double[]> tfidfVectors = new ArrayList<>();

		for (String termProduct : termsProducts) {
			double[] tfidfVector = new double[uniqueTerms.size()];
			int count = 0;

			for (String uniqueTerm : uniqueTerms) {
				tf = calc.tfCalculator(termProduct, uniqueTerm);
				idf = calc.idfCalculator(termsProducts, uniqueTerm);
				tfidfVector[count++] = tf * idf;
			}

			tfidfVectors.add(tfidfVector);
		}

		return tfidfVectors;
	}

	public void addToMap(int docId, String key, String values) {
		Set<String> words = new HashSet<String>(Arrays.asList(values.split(" ")));

		for (String word : words) {
			if (!word.isEmpty()) {
				ArrayList<Tuple> docList = new ArrayList<>();
				Tuple tuple = new Tuple(docId, wordFrequency(words, word));

				if (mapIndex.containsKey(key + "." + word)) {
					mapIndex.get(key + "." + word).add(tuple);
				} else {
					docList.add(tuple);
					mapIndex.put(key + "." + word, docList);
				}
			}
		}
	}

	public void addToTermsProducts(int docId, String values) {
		termsProducts.add(docId - 1, values);
	}

	private void addToUniqueProducts(String value) {
		Set<String> words = new HashSet<String>(Arrays.asList(value.split(" ")));

		for (String word : words)
			if (!uniqueTermsProducts.contains(word))
				uniqueTermsProducts.add(word);
	}

	public double getCosineSimilarity(double[] vector1, double[] vector2) {
		CosineSimilarity cs = new CosineSimilarity();
		double[] temp;
		int i = 0;

		if (vector1.length > vector2.length) {
			temp = new double[vector1.length];

			for (; i < vector2.length; i++)
				temp[i] = vector2[i];

			for (; i < temp.length; i++)
				temp[i] = 0;

			vector2 = temp;
		} else {
			temp = new double[vector2.length];

			for (; i < vector1.length; i++)
				temp[i] = vector1[i];

			for (; i < temp.length; i++)
				temp[i] = 0;

			vector1 = temp;
		}

		return cs.cosineSimilarity(vector1, vector2);
	}

	private int wordFrequency(Set<String> values, String word) {
		return Collections.frequency(values, word);
	}
}