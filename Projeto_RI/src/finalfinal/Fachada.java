package finalfinal;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.DefaultListModel;

import org.json.simple.parser.ParseException;

public class Fachada {
	private InvertedIndex ii;
	private int firstQ;
	private int secondQ;
	private int thirdQ;

	public Fachada() throws FileNotFoundException, ParseException, IOException {
		this.ii = new InvertedIndex();
		ii.run();
		firstQ = ii.firstQuartile;
		secondQ = ii.secondQuartile;
		thirdQ = ii.thirdQuartile;
	}

	public DefaultListModel<String> searchDocs(String title, String price, String genre, String developer,
			String platform, String idiom, String age, boolean frequenceType, boolean searchMethod) {
		Search search = new Search(this.ii, title, price, genre, developer, platform, idiom, age,
				frequenceType);
		search.PreprocessEntry();

		if (searchMethod == false)
			return search.searchForAny();
		else
			return search.searchForAll();
	}

	public int getFirstQuartile() {
		return this.firstQ;
	}

	public int getSecondQuartile() {
		return this.secondQ;
	}

	public int getThirdQuartile() {
		return this.thirdQ;
	}

	public String getDocInfo(String selectedItem) {
		return this.ii.products.get(Integer.parseInt(selectedItem));
	}
}
