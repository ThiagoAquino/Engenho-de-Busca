package finalfinal;

import java.util.List;

/**
 * Class to calculate TfIdf of term.
 * 
 * @author Mubin Shrestha
 */
public class TfIdf {

	/**
	 * Calculates the tf of term termToCheck
	 * 
	 * @param docTermsArray
	 *            : Array of all the words under processing document
	 * @param termToCheck
	 *            : term of which tf is to be calculated.
	 * @return tf(term frequency) of term termToCheck
	 */
	public double tfCalculator(String docTermsArray, String termToCheck) {
		String[] array = docTermsArray.split(" ");
		double count = 0;

		for (String s : array)
			if (s.contains(termToCheck))
				count++;

		return count / array.length;
	}

	/**
	 * Calculates idf of term termToCheck
	 * 
	 * @param termsDocsArray
	 *            : all the terms of all the documents
	 * @param termToCheck
	 * @return idf(inverse document frequency) score
	 */
	public double idfCalculator(List<String> termsDocsArray, String termToCheck) {
		double count = 0;

		for (String ss : termsDocsArray)
			if (ss.contains(termToCheck)) {
				count++;
			}

		return 1 + Math.log(termsDocsArray.size() / count);
	}
}