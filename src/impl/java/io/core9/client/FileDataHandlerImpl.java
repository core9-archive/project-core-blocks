package io.core9.client;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.ClientRepositoryImpl;
import io.core9.editor.FileDataHandler;
import io.core9.editor.RequestImpl;
import io.core9.plugin.admin.plugins.AdminConfigRepository;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;
import io.undertow.util.Headers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class FileDataHandlerImpl implements FileDataHandler<FileDataHandlerConfig> {

	private static final String pathPrefix = "data/editor";

	@InjectPlugin
	private AdminConfigRepository configRepository;

	@Override
	public String getName() {
		return "Menu";
	}

	@Override
	public Class<? extends DataHandlerFactoryConfig> getConfigClass() {
		return FileDataHandlerConfig.class;
	}

	@Override
	public DataHandler<FileDataHandlerConfig> createDataHandler(final DataHandlerFactoryConfig options) {
		return new DataHandler<FileDataHandlerConfig>() {

			private ClientRepositoryImpl clientRepository;

			@Override
			public Map<String, Object> handle(Request req) {

				AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
				clientRepository = new ClientRepositoryImpl();
				clientRepository.addDomain("www.easydrain.nl", "easydrain");
				clientRepository.addDomain("localhost", "easydrain");
				RequestImpl request = new RequestImpl();
				request.setClientRepository(clientRepository);
				String absoluteUrl = "http://" + req.getHostname() + req.getPath();
				request.setAbsoluteUrl(absoluteUrl);
				assetsManager.setRequest(request);

				Map<String, Object> result = new HashMap<String, Object>();
				Map<String, Object> menu = configRepository.readConfig(req.getVirtualHost(), ((FileDataHandlerConfig) options).getClientId(req));
				if (menu == null) {
					menu = new HashMap<String, Object>();
				}

				String path = req.getPath();

				String file = assetsManager.getStaticFilePath(path);

				// byte[] data = fileToBinary(file);
				String data = readFileFromPath(file);

				req.getResponse().putHeader("Content-Type", "text/css");
				//req.getResponse().end(data);

				result.put("file", data);

				return result;
			}

			public String readFile(String path, Charset encoding) throws IOException {
				byte[] encoded = Files.readAllBytes(Paths.get(path));
				return new String(encoded, encoding);
			}

			private String readFileFromPath(String file) {
				try {
					return readFile(file, StandardCharsets.UTF_8);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@SuppressWarnings("unused")
			private byte[] fileToBinary(String filename) {
				File file = new File(filename);
				byte[] fileData = new byte[(int) file.length()];
				return fileData;
			}

			@Override
			public FileDataHandlerConfig getOptions() {
				return (FileDataHandlerConfig) options;
			}
		};
	}
}
