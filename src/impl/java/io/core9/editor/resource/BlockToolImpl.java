package io.core9.editor.resource;

import java.io.File;
import java.io.FileNotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.Block;
import io.core9.editor.ClientRepository;
import io.core9.editor.ClientRepositoryImpl;
import io.core9.editor.PageParser;
import io.core9.editor.PageParserImpl;
import io.core9.editor.RequestImpl;
import net.minidev.json.JSONObject;

public class BlockToolImpl implements BlockTool {

	private String blockClassName = ".block";
	private String blockContainer = "#main-section";
	private PageParser parser;
	private JSONObject data;
	private static final String pathPrefix = "data/editor";
	private AssetsManager assetsManager;
	private RequestImpl request;
	private String httpsSiteRepositoryUrl = "https://github.com/jessec/site-core9.git";
	private String httpsBlockRepositoryUrl = "https://github.com/jessec/block-video.git";
	private ClientRepository clientRepository;

	@Override
	public void setData(JSONObject data) {
		this.data = data;
		processData();
	}

	private void processData() {
		assetsManager = new AssetsManagerImpl(pathPrefix);
		// assetsManager.deleteWorkingDirectory();
		if (!assetsManager.checkWorkingDirectory()) {
			assetsManager.createWorkingDirectory();
		}

		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		request = new RequestImpl();
		request.setClientRepository(clientRepository);

		JSONObject meta = (JSONObject) data.get("meta");

		JSONObject editorData = (JSONObject) data.get("editor");

		String absoluteUrl = meta.getAsString("absolute-url");

		request.setAbsoluteUrl(absoluteUrl);

		assetsManager.setRequest(request);

		File siteConfig = new File(assetsManager.getSiteConfigFile());
		if (!siteConfig.exists()) {

			assetsManager.createClientDirectory();

			assetsManager.clonePublicSiteFromGit(httpsSiteRepositoryUrl);
			JSONObject config = assetsManager.getSiteConfig();
			System.out.println(config);

			try {
				assetsManager.cloneBlocksFromGit(httpsBlockRepositoryUrl);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {

		}

		System.out.println(assetsManager.getPageTemplate());

		if (assetsManager.checkIfPageTemplateExists()) {
			String pageTemplate = assetsManager.getPageTemplate();
			System.out.println("");

			File testPage = new File(pageTemplate);
			if (testPage.exists()) {
				parser = new PageParserImpl(testPage, blockContainer, blockClassName);

				Block block = parser.getBlock(3);

				parser.insertBlock(3, block);
				parser.insertBlock(3, block);

				parser.insertBlock(3, block);
				parser.insertBlock(3, block);
				parser.insertBlock(3, block);

				parser.deleteBlock(7);

				String htmlTemplate = parser.getPage();
				System.out.println(htmlTemplate);

				Document document = Jsoup.parse(htmlTemplate);

				// save the compiled page as cache.html
			}
		}
	}

	@Override
	public String getResponse() {
		return null;
	}

}
