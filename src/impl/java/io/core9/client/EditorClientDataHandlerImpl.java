package io.core9.client;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.ClientRepository;
import io.core9.editor.ClientRepositoryImpl;
import io.core9.editor.PageParser;
import io.core9.editor.PageParserImpl;
import io.core9.editor.RequestImpl;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.template.closure.ClosureTemplateEngine;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@PluginImplementation
public class EditorClientDataHandlerImpl implements EditorClientDataHandler<EditorClientDataHandlerConfig> {

	private static final String pathPrefix = "data/editor";

	@InjectPlugin
	private EditorClientPlugin editorClientPlugin;

	@InjectPlugin
	private ClosureTemplateEngine engine;

	private String blockClassName = ".block";
	private String blockContainer = "#main-section";
	@SuppressWarnings("unused")
	private PageParser parser;

	private AssetsManager assetsManager;
	private RequestImpl request;
	private String httpsSiteRepositoryUrl = "https://github.com/jessec/site-core9.git";
	private String httpsBlockRepositoryUrl = "https://github.com/jessec/block-video.git";
	private ClientRepository clientRepository;

	@Override
	public String getName() {
		return "Editor client";
	}

	@Override
	public Class<? extends DataHandlerFactoryConfig> getConfigClass() {
		return EditorClientDataHandlerConfig.class;
	}

	@Override
	public DataHandler<EditorClientDataHandlerConfig> createDataHandler(DataHandlerFactoryConfig options) {
		final EditorClientDataHandlerConfig config = (EditorClientDataHandlerConfig) options;
		return new DataHandler<EditorClientDataHandlerConfig>() {

			private Document doc;

			@Override
			public Map<String, Object> handle(Request req) {

				Map<String, Object> result = new HashMap<String, Object>();

				assetsManager = new AssetsManagerImpl(pathPrefix);
				//assetsManager.deleteWorkingDirectory();
				if (!assetsManager.checkWorkingDirectory()) {
					assetsManager.createWorkingDirectory();
				}

				clientRepository = new ClientRepositoryImpl();
				clientRepository.addDomain("www.easydrain.nl", "easydrain");
				clientRepository.addDomain("localhost", "easydrain");
				request = new RequestImpl();
				request.setClientRepository(clientRepository);

				String absoluteUrl = "http://" + req.getHostname() + req.getPath();

				request.setAbsoluteUrl(absoluteUrl);



				assetsManager.setRequest(request);

				String url = "http://localhost/module-page-editor/src/impl/resources/editor/clients/easydrain/pages/frontpage.html";

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

				}else{


				}

				System.out.println(assetsManager.getPageTemplate());

				if(assetsManager.checkIfPageTemplateExists()){
					String pageTemplate = assetsManager.getPageTemplate();
					System.out.println("");


					File testPage = new File(pageTemplate);
					if(testPage.exists()){
						parser = new PageParserImpl(testPage, blockContainer, blockClassName);

						String htmlTemplate = parser.getOriginalFile();
						System.out.println(htmlTemplate);

						Document document = Jsoup.parse(htmlTemplate);
						result.put("head", document.head().toString());
						result.put("body", document.body().toString());
					}


				}


				//result = getBackupUrl(result, url);

				return result;
			}

			@SuppressWarnings("unused")
			private Map<String, Object> getBackupUrl(Map<String, Object> result, String url) {
				String urlFileName = url.replace("/", "_");

				createFileInDataDir(urlFileName);

				try {
					doc = Jsoup.connect(url).get();
				} catch (IOException e) {
					e.printStackTrace();
				}

				result.put("head", doc.head().toString());
				result.put("body", doc.body().toString());
				return result;
			}

			private void createFileInDataDir(String fileName) {
				new File(pathPrefix).mkdirs();
				File yourFile = new File(pathPrefix + "/" + fileName);
				if (!yourFile.exists()) {
					try {
						yourFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public EditorClientDataHandlerConfig getOptions() {
				return config;
			}

		};
	}
	/*
	 * switch(req.getMethod()) { case POST: sendMail(config, req);
	 * result.put("sent", true); default: if(config.getCustomVariables() !=
	 * null) { for(CustomVariable var : config.getCustomVariables()) {
	 * if(var.isManual()) { req.getResponse().addGlobal(var.getKey(),
	 * var.getValue()); } else { req.getResponse().addGlobal(var.getKey(),
	 * req.getParams().get(var.getValue())); } } } }
	 */

}
