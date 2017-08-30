package crawler;

import java.util.List;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SpiderLeg {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> urls;

	/***
	 * Constructor for Spider Leg
	 */
	public SpiderLeg() {
		urls = new LinkedList<String>();
	}

	/***
	 * This method makes the communication between the crawler and the site and
	 * gathers the links from the page in the list.
	 * 
	 * @param url
	 */
	public void crawl(String url) {
		try {
			Connection c = Jsoup.connect(url).userAgent(USER_AGENT);
			Document document = c.get();

			if (c.response().statusCode() != 200) {
				return;
			}
			// if(!c.response().contentType().contains("text/html")) {return
			// false;} //change return type
			Elements links = document.select("a[href]");

			for (Element link : links) {
				// compare root url with link here to not search in others sites
				this.urls.add(link.absUrl("href"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * This method returns the list of links inside the page.
	 * 
	 * @return the list of links inside the page
	 */
	public List<String> getLinks() {
		return urls;
	}
}
