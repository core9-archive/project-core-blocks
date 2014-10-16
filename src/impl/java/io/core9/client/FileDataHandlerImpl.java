package io.core9.client;

import io.core9.editor.FileDataHandler;
import io.core9.plugin.admin.plugins.AdminConfigRepository;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class FileDataHandlerImpl implements FileDataHandler<FileDataHandlerConfig> {

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

			@Override
			public Map<String, Object> handle(Request req) {



				Map<String,Object> result = new HashMap<String,Object>();
				Map<String, Object> menu = configRepository.readConfig(req.getVirtualHost(), ((FileDataHandlerConfig) options).getClientId(req));
				if(menu == null) {
					menu = new HashMap<String,Object>();
				}
				result.put("file", "file");
				return result;
			}

			@Override
			public FileDataHandlerConfig getOptions() {
				return (FileDataHandlerConfig) options;
			}
		};
	}
}
