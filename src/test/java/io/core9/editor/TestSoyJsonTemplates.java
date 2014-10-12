package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Test;

public class TestSoyJsonTemplates {

	private String blockClassName = ".block";
	private String blockContainer = "#main-section";
	private PageParser parser;
	private String emptyHtmlTestPage = "/editor/client/site/pages/empty-test-page.html";

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private RequestImpl request;
	private String absoluteUrl = "http://localhost:8080/easydrain";

	private void setupWorkingDirectory() {
		assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}

	public void setupBlocksFromPage() {
		URL url = this.getClass().getResource(emptyHtmlTestPage );
		File testPage = new File(url.getFile());
		assertTrue(testPage.exists());
		parser = new PageParserImpl(testPage, blockContainer, blockClassName);
		List<Block> blocks = parser.getBlocks();
		assertTrue(blocks.size() == 0);
	}


	@Test
	public void test() {
		setupWorkingDirectory();
		setupBlocksFromPage();

		request = new RequestImpl();
		request.setAbsoluteUrl(absoluteUrl);
		assetsManager.setRequest(request);
		assetsManager.createHostDirectory();
	}

}
