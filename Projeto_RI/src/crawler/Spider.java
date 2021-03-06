/*
 * Universidade Federal de Pernambuco
 * Centro de Inform�tica (CIn)
 * Recupera��o de Informa��o
 * 
 * Ana Caroline Ferreira de Fran�a (acff)
 * Thiago Aquino Santos (tas4)
 * Victor Sin Yu Chen (vsyc)
 */

package crawler;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.URL;
import java.net.MalformedURLException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import crawler.crawlercommons.BaseRobotRules;
import crawler.crawlercommons.BaseRobotsParser;
import crawler.crawlercommons.SimpleRobotRules;
import crawler.crawlercommons.SimpleRobotRulesParser;

/***
 * Here are the methods of the Crawlers. The method crawl() is abstract here and
 * will be implemented in another class.
 * 
 * @author Victor Chen
 *
 */
public abstract class Spider implements Runnable {

	// Constants
	private static final String USER_AGENT = "Mozilla/5.0 AppleWebKit/537.36 Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48";
	private static final int MAXIMUM_QUANTITY_PAGES = 1000;

	protected static final String NAGEM_CONSTANT = "/home/index.php";

	// Variables
	protected String domain;
	protected String host;
	protected List<String> linksToVisit;
	protected List<String> trashToVisit;
	protected Set<String> visitedLinks;
	protected Map<String, String> cookies;

	protected BaseRobotsParser srrp;
	protected BaseRobotRules srr;

	private PrintWriter pw;
	private StringBuffer filePath;
	private int fileIndex;

	// Constructor
	public Spider(String domain, PrintWriter pw) {
		this.domain = domain;
		this.host = discoverHost(domain);
		this.linksToVisit = new LinkedList<String>();
		this.trashToVisit = new LinkedList<String>();
		this.visitedLinks = new LinkedHashSet<String>();
		this.cookies = new HashMap<String, String>();

		this.srrp = new SimpleRobotRulesParser();
		this.srr = new SimpleRobotRules();

		this.pw = pw;
		this.filePath = discoverFilePath(domain);
		this.fileIndex = 0;
	}

	/***
	 * This method returns the domain's host.
	 * 
	 * @param domain
	 *            a domain
	 * @return (String) the domain's host
	 */
	private String discoverHost(String domain) {
		StringBuffer url = new StringBuffer();

		if (domain == "steampowered")
			url.append("store.steampowered.com");
		else {
			url.append("www.");
			url.append(domain);
			url.append(".com.br");
		}

		return url.toString();
	}

	/***
	 * This method returns a path to save the files retrieved.
	 * 
	 * @param domain
	 *            the folder's name
	 * @return (StringBuffer) the path in which the files will be saved
	 */
	private StringBuffer discoverFilePath(String domain) {
		StringBuffer path = new StringBuffer(SpiderFactory.ARTIFACT_PATH).append(domain);
		File f = new File(path.toString());

		if (!f.exists())
			f.mkdirs();

		return path;
	}

	/***
	 * 
	 * This method will run the Crawler.
	 * 
	 */
	@Override
	public void run() {
		String http = (this.domain == "fnac" || this.domain == "steampowered" || this.domain == "walmart") ? "https://"
				: "http://";
		String url = http + this.host;

		try {
			// Request the robots.txt
			byte[] robotsTxt = requestRobotsTxt(url);

			// Parse the robots.txt
			this.srr = this.srrp.parseContent(url, robotsTxt, "text/html", USER_AGENT);

			// Search the domain for pages
			if (this.domain == "nagem")
				url += NAGEM_CONSTANT;

			searchPages(url, 3000, 5000);

			// Save the links of the visited pages
			saveVisitedLinks();
		} catch (Exception e) {
			String message = new StringBuffer("Crawler for <<").append(this.domain)
					.append(">> could not run successfully").toString();
			System.out.println(message);
			SpiderFactory.error = true;

			synchronized (this.pw) {
				this.pw.println(new StringBuffer(new Date().toString()).append(": ").append(message).append("."));
			}
		}
	}

	/***
	 * This method will get the robots.txt file and convert to an array of
	 * bytes.
	 * 
	 * @param url
	 *            the URL from which the file will be acquired
	 * @return (byte[]) the robots.txt file
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private byte[] requestRobotsTxt(String url) throws MalformedURLException, IOException {
		String path = "..\\Projeto_RI\\artefatos\\robots\\";
		File f = new File(path);

		if (!f.exists())
			f.mkdir();

		InputStream is;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		f = new File(new StringBuffer(path).append(this.domain).append(".txt").toString());
		boolean fileExists = false;

		if (f.isFile()) {
			is = new FileInputStream(f);
			fileExists = true;
		} else
			is = new URL(url + "/robots.txt").openStream();

		byte[] buffer = new byte[1024];
		int length;

		while ((length = is.read(buffer)) != -1)
			baos.write(buffer, 0, length);

		if (!fileExists)
			baos.writeTo(new FileOutputStream(f));

		return baos.toByteArray();
	}

	/***
	 * This method will search the pages to get HTML and the next links to be
	 * searched.
	 * 
	 * @param url
	 *            the URL to start the search
	 * @param timeOut
	 *            the time in milliseconds to timeout a connection
	 * @param timeSleep
	 *            the time in milliseconds to sleep the thread if the site is
	 *            overloaded
	 * @throws InterruptedException
	 */
	private void searchPages(String url, int incTimeOut, int incTimeSleep) throws InterruptedException {
		this.linksToVisit.add(url);
		String nextUrl = nextUrl();

		// flux control variables
		boolean pause = false;
		int timeOut = 8000, maxTimeOut = 50000;
		int timeSleep = incTimeSleep << 1, maxTimeSleep = 600000;
		int count = 0;

		while (this.visitedLinks.size() < MAXIMUM_QUANTITY_PAGES && nextUrl != null) {
			if (pause) {
				Thread.sleep(timeSleep);
				pause = false;
			}

			try {
				crawl(nextUrl, timeOut);
				this.visitedLinks.add(nextUrl);
				System.out.println(nextUrl + " VISITED. tO: " + timeOut + " tS: " + timeSleep);
			} catch (IOException e) {
				System.out.println(e.getMessage() + " for the URL " + nextUrl);

				if (count++ % 5 != 4)
					continue;
			} catch (Exception e) {
				System.out.println(e.getMessage() + " for the URL " + nextUrl);
				pause = true;

				if (timeOut < maxTimeOut)
					timeOut += incTimeOut;

				if (timeSleep < maxTimeSleep)
					timeSleep += incTimeSleep;

				if (count++ % 5 != 4)
					continue;
			} finally {
				nextUrl = nextUrl();
			}
		}
	}

	/***
	 * This method will get the next URL to be visited.
	 * 
	 * @return (String) the next URL to be visited
	 */
	private String nextUrl() {
		String next;

		do {
			if (this.linksToVisit.isEmpty()) {
				if (this.getClass() == HeuristicSpider.class && !this.trashToVisit.isEmpty())
					next = this.trashToVisit.remove(0);
				else
					return null;
			} else {
				next = this.linksToVisit.remove(0);
			}
		} while (this.visitedLinks.contains(next));

		return next;
	}

	/***
	 * Abstract method.
	 * 
	 * @param url
	 *            the URL of the page to be visited
	 * @param timeout
	 *            the maximum time to wait for the response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	abstract void crawl(String url, int timeout) throws IOException, InterruptedException;

	/***
	 * This method will connect with the host.
	 * 
	 * @param url
	 *            the URL to connect
	 * @param timeout
	 *            the maximum time to wait for the response
	 * @return (Connection.Response) the response of the connection
	 * @throws IOException
	 */
	protected Response connect(String url, int timeout) throws IOException {
		Connection c = Jsoup.connect(url);
		c.userAgent(USER_AGENT);
		c.header("Accept-Language", "pt-br");
		c.referrer("http://www.google.com");
		c.cookies(this.cookies);
		c.timeout(timeout);
		c.ignoreHttpErrors(true);

		return c.execute();
	}

	/***
	 * This method will save the pages retrieved.
	 * 
	 * @param doc
	 *            the page retrieved
	 * @throws IOException
	 */
	protected void saveHtml(String doc) throws IOException {
		StringBuffer path = new StringBuffer(this.filePath.toString());
		path.append("\\doc");
		path.append(this.fileIndex++);
		path.append(".html");

		FileWriter fwDoc = new FileWriter(path.toString());
		fwDoc.write(doc);
		fwDoc.close();
	}

	/***
	 * This method will save the visited links in a .TXT file.
	 * 
	 * @throws IOException
	 */
	private void saveVisitedLinks() throws IOException {
		StringBuffer path = new StringBuffer(this.filePath.toString());
		path.append("\\visitedLinks.txt");

		FileWriter fwLinks = new FileWriter(path.toString());
		Iterator<String> itr = this.visitedLinks.iterator();

		while (itr.hasNext()) {
			fwLinks.write(itr.next());
			fwLinks.write("\r\n");
		}

		fwLinks.close();
	}
}
