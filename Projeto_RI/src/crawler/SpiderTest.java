package crawler;

public class SpiderTest {

	public static void main(String[] args) {
		Spider s = new Spider();
		s.search("http://store.steampowered.com");

		try {
			s.saveFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
