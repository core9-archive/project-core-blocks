package io.core9.client;

import java.util.HashMap;
import java.util.Map;

import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;

public class EditorClientFileDataHandler implements DataHandler<EditorClientFileDataHandlerConfig> {

	@Override
	public Map<String, Object> handle(Request req) {

		Map<String, Object> result = new HashMap<String, Object>();
		return result ;
	}

	@Override
	public EditorClientFileDataHandlerConfig getOptions() {

		return null;
	}

}
