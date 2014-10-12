package io.core9.editor;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import io.core9.editor.JsonSoyUtils;

public class TestSoyJsonTemplates {

	private String blockClassName = ".block";
	private String blockContainer = "#main-section";
	private PageParser parser;
	private String emptyHtmlTestPage = "/editor/client/site/pages/empty-test-page.html";

	public void setupBlocksFromPage() {
		URL url = this.getClass().getResource(emptyHtmlTestPage );
		File testPage = new File(url.getFile());
		assertTrue(testPage.exists());
		parser = new PageParserImpl(testPage, blockContainer, blockClassName);
		List<Block> blocks = parser.getBlocks();
		assertTrue(blocks.size() > 1);
	}


	@Test
	public void test() {
		setupBlocksFromPage();
		fail("Not yet implemented");
	}

}
