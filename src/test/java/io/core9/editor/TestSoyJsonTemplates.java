package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

import net.minidev.json.JSONObject;

import org.junit.AfterClass;
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
	private String httpsSiteRepositoryUrl = "https://github.com/jessec/site-core9.git";
	private String httpsBlockRepositoryUrl = "https://github.com/jessec/block-video.git";
	private ClientRepository clientRepository;

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

	private void setUpRequest() {
		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		request = new RequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/easydrain");
	}

	//@AfterClass
	public static void cleanup() {
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}


	@Test
	public void testCloneSiteAndBlocksFromGit() {
		setupWorkingDirectory();
		setupBlocksFromPage();
		setUpRequest();

		assetsManager.setRequest(request);
		assetsManager.createClientDirectory();

		assetsManager.clonePublicSiteFromGit(httpsSiteRepositoryUrl);
		JSONObject config = assetsManager.getSiteConfig();
		System.out.println(config);

		try {
			assetsManager.cloneBlocksFromGit(httpsBlockRepositoryUrl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}



}
