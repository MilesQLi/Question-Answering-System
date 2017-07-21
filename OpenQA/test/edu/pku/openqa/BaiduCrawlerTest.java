package edu.pku.openqa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

/**
 * 
 * @author Liang Wang
 *
 */
public class BaiduCrawlerTest {

	@Test
	public void ResponseNotEmpty() {
		BaiduCrawler crawler = new BaiduCrawler();
		ArrayList<String> res = crawler.getSearchResult("�й����׶����������У�");
		assertTrue(res != null);
	}

} // end class BaiduCrawlerTest
