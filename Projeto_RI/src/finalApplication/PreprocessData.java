package finalApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PreprocessData {

	public static String[] stopwords = { "a", "as", "able", "about", "above", "according", "accordingly", "across",
			"actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone",
			"along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any",
			"anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate",
			"appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available",
			"away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before",
			"beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between",
			"beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause",
			"causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning",
			"consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could",
			"couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do",
			"does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight",
			"either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every",
			"everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few",
			"ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth",
			"four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going",
			"gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent",
			"having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit",
			"however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc",
			"indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is",
			"isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows",
			"known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like",
			"liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me",
			"mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my",
			"myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never",
			"nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not",
			"nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on",
			"once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours",
			"ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps",
			"placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv",
			"rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively",
			"respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see",
			"seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious",
			"seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some",
			"somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon",
			"sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken",
			"tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their",
			"theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore",
			"therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third",
			"this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to",
			"together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two",
			"un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used",
			"useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants",
			"was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent",
			"what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas",
			"whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos",
			"whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont",
			"wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your",
			"yours", "yourself", "yourselves", "zero" };
	public static Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
	static Map<Integer, String> docsList = new HashMap<Integer, String>();

	@SuppressWarnings({ "unchecked" })
	public static JSONArray mainPreprocess()
			throws org.json.simple.parser.ParseException, FileNotFoundException, IOException {

		String path = "..\\Projeto_RI\\productInfo.txt";

		JSONParser parser = new JSONParser();
		JSONArray instances = (JSONArray) parser.parse(new FileReader(path));
		JSONArray instancesPreprocess = new JSONArray();
		int docId = 1;

		for (Object instance : instances) {
			JSONObject gameInfo = (JSONObject) instance;
			JSONObject gameInfoPreprocess = new JSONObject();

			String doc = "";
			String title = (String) gameInfo.get("titulo");
			String price = (String) gameInfo.get("preco");
			String language = (String) gameInfo.get("idioma");
			String url = (String) gameInfo.get("url");
			String genre = (String) gameInfo.get("genero");
			String idade = (String) gameInfo.get("idade");

			doc = "Titulo: " + title + "\nPreço: " + price + "\nIdioma: " + language + "\nurl: " + url + "\nGênero: "
					+ genre + "\nIdade: " + idade;

			if (title != null) {
				gameInfoPreprocess.put("titulo", applyAllPreprocess(title));
			}
			if (price != null) {
				gameInfoPreprocess.put("preco", priceToRange(price));
			}
			if (language != null) {
				gameInfoPreprocess.put("idioma", lowerCase(language));
			}
			if (genre != null) {
				gameInfoPreprocess.put("genero", applyAllPreprocess(genre));
			}
			if (idade != null) {
				gameInfoPreprocess.put("idade", applyAgePreprocess(idade));
			}

			gameInfoPreprocess.put("url", url);
			gameInfoPreprocess.put("docId", docId);
			docsList.put(docId++, doc);
			instancesPreprocess.add(gameInfoPreprocess);
		}

		try (FileWriter file = new FileWriter("..\\Projeto_RI\\final_json.json")) {
			file.write(instancesPreprocess.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
		}

		return instancesPreprocess;
	}

	public static String applyAllPreprocess(String value) {
		return removeStopWords(lowerCase(removeSpecialCharacters(value)));
	}

	public static String removeSpecialCharacters(String value) {
		return value.replaceAll("[^a-zA-Z]+", " ");
	}

	public static String lowerCase(String value) {
		return value.toLowerCase();
	}

	public static String removeStopWords(String string) {
		String result = "";
		String[] words = string.split("\\s");
		for (String word : words) {
			if (word.isEmpty())
				continue;
			if (isStopword(word))
				continue;
			result += (word + " ");
		}
		return result;
	}

	public static boolean isStopword(String word) {
		if (word.length() < 2)
			return true;
		if (word.charAt(0) >= '0' && word.charAt(0) <= '9')
			return true;
		if (stopWordSet.contains(word))
			return true;
		else
			return false;
	}

	public static String priceToRange(String price) {
		String value = "";
		Double priceDouble = Double.parseDouble(price.replaceAll("[^0-9+,?0-9]+", "").replace(',', '.'));

		if (priceDouble <= 50) {
			value = "quartil1";
		} else if (priceDouble > 50 && priceDouble <= 80) {
			value = "quartil2";
		} else if (priceDouble > 80 && priceDouble <= 120) {
			value = "quartil3";
		} else {
			value = "quartil4";
		}
		return value;
	}

	public static ArrayList<String> stringToTokens(String field, String words) {
		ArrayList<String> out = new ArrayList<>();
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(words.split(" ")));
		for (String word : list) {
			out.add(field + "." + word);
		}
		return out;
	}

	public static String applyAgePreprocess(String idade) {
		String value = "livre";
		idade = idade.replace("anos", "");

		if (idade.contains("livre")) {
			idade = "0";
		} else {
			idade = idade.replaceAll("[^0-9]+", "");
		}
		int idadeInt = Integer.parseInt(idade);
		if (idadeInt <= 10) {
			value = "livre";
		} else if (idadeInt > 10 && idadeInt <= 18) {
			value = "10To18";
		} else if (idadeInt > 18) {
			value = "maior18";
		}
		return value;
	}
}
