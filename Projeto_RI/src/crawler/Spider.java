package crawler;

import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

// Implementar exclusão de anúncios
public class Spider {
	private static final int MAX_QTD_OF_PAGES = 1000;
	private static final String path = "..\\links.txt";
	private Set<String> visitedPages;
	private List<String> pagesToVisit;

	/***
	 * Constructor for Spider
	 */
	public Spider() {
		visitedPages = new HashSet<String>();
		pagesToVisit = new LinkedList<String>();
	}

	/***
	 * Method to return the next URL in the pagesToVisit list. Does not return
	 * an URL that has already been visited.
	 * 
	 * @return the nextUrl to be analyzed
	 */

	private String nextUrl() {
		String nextUrl = this.pagesToVisit.remove(0);

		while (this.visitedPages.contains(nextUrl)) {
			nextUrl = this.pagesToVisit.remove(0);

			if (nextUrl == null) {
				return null;
			}
		}

		this.visitedPages.add(nextUrl);

		return nextUrl;
	}

	/***
	 * Standart method from Spider. Starting from a root url, until visit the
	 * maximum quantity of pages or the number of pages to visit is zero.
	 * 
	 * @param url
	 * @param searchWord
	 */

	public void search(String url) {
		pagesToVisit.add(url);

		String currentUrl = nextUrl();

		while (currentUrl != null && this.visitedPages.size() < MAX_QTD_OF_PAGES) {
			SpiderLeg sl = new SpiderLeg();
			sl.crawl(currentUrl);
			this.pagesToVisit.addAll(sl.getLinks());
			currentUrl = nextUrl();
		}
	}

	public void saveFile() throws IOException {
		FileWriter fw = new FileWriter(path);

		for (String link : visitedPages) {
			fw.write(link);
			fw.write("\n");
		}

		fw.close();
	}
}
