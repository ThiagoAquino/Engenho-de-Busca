package finalfinal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.compress.utils.Charsets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PreprocessData {

	private String path;

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
	private Set<String> stopWordSet;
	private Map<Integer, String> docsList;

	// Quartis
	private List<Double> precos;
	private int firstQuartile;
	private int secondQuartile;
	private int thirdQuartile;

	// Constructors
	public PreprocessData(String path) {
		this.path = path;
		this.stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
		this.docsList = new HashMap<Integer, String>();
		this.precos = new ArrayList<Double>();
	}

	public PreprocessData() {
		this.stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
	}

	// Main methods
	@SuppressWarnings("unchecked")
	public JSONArray readJSONFile() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray instancesBefore = (JSONArray) parser
				.parse(new InputStreamReader(new FileInputStream(this.path + "productInfo.txt\\"), Charsets.UTF_8));
		JSONArray instancesAfter = new JSONArray();
		int productId = 1;

		for (Object instance : instancesBefore)
			instancesAfter.add(preprocess(productId++, (JSONObject) instance));

		calcQuartiles();
		instancesAfter = attQuartiles(instancesAfter);

		try (FileWriter file = new FileWriter(this.path + "final_json.json\\")) {
			file.write(instancesAfter.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
		}

		return instancesAfter;
	}

	// Secondary methods
	@SuppressWarnings({ "unchecked" })
	private Object preprocess(int productId, JSONObject gameInfoBefore) {
		JSONObject gameInfoAfter = new JSONObject();
		StringBuffer product = new StringBuffer();

		String titulo = (String) gameInfoBefore.get("titulo");
		String preco = (String) gameInfoBefore.get("preco");
		String desenvolvedor = (String) gameInfoBefore.get("desenvolvedor");
		String plataforma = (String) gameInfoBefore.get("plataforma");
		String genero = (String) gameInfoBefore.get("genero");
		String idioma = (String) gameInfoBefore.get("idioma");
		Object temp = gameInfoBefore.get("idade");
		String url = (String) gameInfoBefore.get("url");
		String idade = temp + "";

//		if (temp == null)
//			idade = "0";
//		else if (temp.getClass().isInstance(int.class))
//			idade = Long.toString((long) temp);
//		else if (temp.getClass().isInstance(int.class))
//			idade = Integer.toString((int) temp);
//		else
//			idade = (String) temp;

		gameInfoAfter.put("id", productId);

		gameInfoAfter.put("titulo", standardPreprocess(titulo));
		product.append("Titulo: ");

		if (titulo != null) {
			product.append(titulo);
		} else {
			product.append("sem titulo");
		}

		product.append("\n");

		product.append("Preco: ");

		if (preco != null) {
			gameInfoAfter.put("preco", pricePreprocess(preco));
			product.append(preco);
		} else {
			gameInfoAfter.put("preco", "sem preco");
			product.append("sem preco");
		}

		product.append("\n");

		if (desenvolvedor != null) {
			gameInfoAfter.put("desenvolvedor", standardPreprocess(desenvolvedor));
			product.append("Desenvolvedor: ");
			product.append(desenvolvedor);
			product.append("\n");
		}

		if (plataforma != null) {
			gameInfoAfter.put("plataforma", standardPreprocess(plataforma));
			product.append("Plataforma: ");
			product.append(plataforma);
			product.append("\n");
		}

		if (genero != null) {
			gameInfoAfter.put("genero", standardPreprocess(genero));
			product.append("Genero: ");
			product.append(genero);
			product.append("\n");
		}

		if (idioma != null) {
			gameInfoAfter.put("idioma", standardPreprocess(idioma));
			product.append("Idioma: ");
			product.append(idioma);
			product.append("\n");
		}

		if (idade != null) {
			gameInfoAfter.put("idade", agePreprocess(idade));
			product.append("Idade: ");
			product.append(idade);
			product.append("\n");
		}

		if (url != null) {
			gameInfoAfter.put("url", lowerCase(url));
			product.append("Url: ");
			product.append(url);
			product.append("\n");
		}

		this.getDocsList().put(productId, product.toString());
		return gameInfoAfter;
	}

	private void calcQuartiles() {
		int l = this.precos.size();
		Double[] arr = new Double[l];
		Arrays.sort(this.precos.toArray(arr));

		this.firstQuartile = (int) Math.floor(arr[l / 4] + 1);
		this.secondQuartile = (int) Math.floor(arr[l / 2] + 1);
		this.thirdQuartile = (int) Math.floor(arr[(3 * l) / 4] + 1);
	}

	@SuppressWarnings("unchecked")
	private JSONArray attQuartiles(JSONArray instances) {
		String value;
		double priceDouble;

		for (int i = 0; i < instances.size(); i++) {
			JSONObject obj = (JSONObject) instances.get(i);

			if (obj.get("preco").equals("sem preco"))
				continue;

			priceDouble = Double.parseDouble((String) obj.get("preco"));

			if (priceDouble <= firstQuartile)
				value = "quartil1";
			else if (priceDouble <= secondQuartile)
				value = "quartil2";
			else if (priceDouble <= thirdQuartile)
				value = "quartil3";
			else
				value = "quartil4";

			obj.replace("preco", value);
			instances.set(i, obj);
		}

		return instances;
	}

	// Auxiliary methods
	public String standardPreprocess(String value) {
		if (value == null)
			return "";
		return removeStopWords(lowerCase(removeSpecialCharacters(value)));
	}

	public String removeSpecialCharacters(String value) {
		return value.replaceAll("[^\\w\\s]+", "?").replaceAll("[^a-zA-Z\\s]", "");
	}

	public String lowerCase(String value) {
		return value.toLowerCase();
	}

	public String removeStopWords(String string) {
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

	public boolean isStopword(String word) {
		if (word.length() < 2)
			return true;
		if (word.charAt(0) >= '0' && word.charAt(0) <= '9')
			return true;
		if (this.stopWordSet.contains(word))
			return true;
		else
			return false;
	}

	public String pricePreprocess(String price) {
		if (price.equals("") || price == null)
			return "0.0";

		Double priceDouble = Double.parseDouble(price.replaceAll("[^0-9+,?0-9]+", "").replace(',', '.'));
		this.precos.add(priceDouble);
		return priceDouble.toString();
	}

	public String agePreprocess(String idade) {
		idade = idade.replaceAll("[^0-9]+", "");
		if (idade.equals("") || idade == null)
			return "livre";

		int value = Integer.parseInt(idade);

		if (value <= 10)
			return "livre";
		else if (value < 18)
			return "10To18";
		else
			return "maior18";
	}

	public ArrayList<String> stringToTokens(String field, String words) {
		ArrayList<String> out = new ArrayList<>();
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(words.split(" ")));

		for (String word : list)
			out.add(field + "." + word);

		return out;
	}

	// Getters
	public Map<Integer, String> getDocsList() {
		return docsList;
	}

	public int getFirstQuartile() {
		return firstQuartile;
	}

	public int getSecondQuartile() {
		return secondQuartile;
	}

	public int getThirdQuartile() {
		return thirdQuartile;
	}
}