package io.core9.client;

import io.core9.plugin.template.closure.ClosureTemplateEngine;
import io.core9.plugin.widgets.datahandler.DataHandler;

import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class EditorClientFileWidgetImpl implements EditorClientFileWidget {

	private static final String pathPrefix = "data/editor";

	@InjectPlugin
	private EditorClientPlugin editorClientPlugin;

	@InjectPlugin
	private ClosureTemplateEngine engine;

	@Override
	public String getName() {
		return "Editor client static files";
	}

	@Override
	public String getTemplateName() {

		return null;
	}

	@Override
	public DataHandler<?> getDataHandler() {

		return null;
	}

	@Override
	public String getTemplate() {

		return null;
	}

	@Override
	public String getId() {

		return null;
	}

	@Override
	public void setId(String id) {

	}

	@Override
	public Map<String, Object> retrieveDefaultQuery() {

		return null;
	}

}
