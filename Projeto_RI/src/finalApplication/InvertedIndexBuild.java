package finalApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.javatuples.Triplet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InvertedIndexBuild {

	static Map<String, ArrayList<Triplet<Integer, Integer, Double>>> mapIndex = new HashMap<String, ArrayList<Triplet<Integer, Integer, Double>>>();
	static Set<Entry<String, ArrayList<Triplet<Integer, Integer, Double>>>> set = mapIndex.entrySet();
	static int qtdDoc;

	public static void buildIndex() throws org.json.simple.parser.ParseException, FileNotFoundException, IOException {
		JSONArray instances = PreprocessData.mainPreprocess();
		qtdDoc = instances.size();

		for (Object instance : instances) {
			JSONObject gameInfo = (JSONObject) instance;
			int docId = (int) gameInfo.get("docId");
			for (Object key : gameInfo.keySet()) {
				String keyObj = (String) key;
				if (key.equals("idade")) {
					System.out.println();
				}
				if (gameInfo.get(keyObj) != null && !keyObj.equalsIgnoreCase("url")
						&& !keyObj.equalsIgnoreCase("docId")) {
					String value = (String) gameInfo.get(keyObj);
					ArrayList<String> values = new ArrayList<String>(Arrays.asList(value.split(" ")));
					if (values.size() > 0) {
						addToMap(values, keyObj, docId);
					}
				}
			}
		}
		// // Delete this code !!!
		// @SuppressWarnings("rawtypes")
		// Iterator it = set.iterator();
		// while (it.hasNext()) {
		// Entry<String, ArrayList<String>> entry = (Entry) it.next();
		// System.out.println(entry.getKey() + " -> " + entry.getValue());
		// }
	}

	private static int wordFrequency(ArrayList<String> values, String word) {
		return Collections.frequency(values, word);
	}

	private static double tf(int frequency) {
		return 1 + Math.log(frequency);
	}

	public static void addToMap(ArrayList<String> values, String key, int docId) {
		Set<String> words = new HashSet<String>(values);
		for (String word : words) {
			if (!word.isEmpty()) {
				ArrayList<Triplet<Integer, Integer, Double>> docList = new ArrayList<>();
				int frequency = wordFrequency(values, word);
				Triplet<Integer, Integer, Double> tuple = new Triplet<Integer, Integer, Double>(docId, frequency,
						tf(frequency));

				if (mapIndex.containsKey(key + "." + word)) {
					mapIndex.get(key + "." + word).add(tuple);
				} else {
					docList.add(tuple);
					mapIndex.put(key + "." + word, docList);
				}
			}
		}
	}
}
