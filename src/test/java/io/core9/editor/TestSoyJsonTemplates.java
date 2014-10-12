package io.core9.editor;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import io.core9.editor.JsonSoyUtils;

public class TestSoyJsonTemplates {

	private String blockClassName;
	private String blockContainer;
	private PageParser parser;


	public void setupBlocksFromPage() {
		blockClassName = ".block";
		blockContainer = "#main-section";
		URL url = this.getClass().getResource("/editor/client/pages/full-test-page.html");
		File testPage = new File(url.getFile());
		assertTrue(testPage.exists());
		parser = new PageParserImpl(testPage, blockContainer, blockClassName);
		List<Block> blocks = parser.getBlocks();
		assertTrue(blocks.size() > 1);
	}


	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
