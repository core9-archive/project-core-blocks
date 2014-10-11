package io.core9.client;



import io.core9.plugin.server.request.Request;
import io.core9.plugin.template.closure.ClosureTemplateEngine;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class EditorClientDataHandlerImpl implements EditorClientDataHandler<EditorClientDataHandlerConfig> {

	@InjectPlugin
	private EditorClientPlugin editorClientPlugin;

	@InjectPlugin
	private ClosureTemplateEngine engine;

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

			@Override
			public Map<String, Object> handle(Request req) {

				Map<String, Object> result = new HashMap<String, Object>();
/*				switch(req.getMethod()) {
				case POST:
					sendMail(config, req);
					result.put("sent", true);
				default:
					if(config.getCustomVariables() != null) {
						for(CustomVariable var : config.getCustomVariables()) {
							if(var.isManual()) {
								req.getResponse().addGlobal(var.getKey(), var.getValue());
							} else {
								req.getResponse().addGlobal(var.getKey(), req.getParams().get(var.getValue()));
							}
						}
					}
				}*/
				result.put("data", "test");

				return result;
			}

			@Override
			public EditorClientDataHandlerConfig getOptions() {
				return config;
			}

		};
	}

	/**
	 * Setup and set the email message
	 * @param config
	 * @param req
	 */
	@SuppressWarnings("unused")
	protected void sendMail(final EditorClientDataHandlerConfig config, final Request req) {

		Map<String,Object> body = req.getBodyAsMap().toBlocking().last();
		try {
/*			message.setSubject((String) body.getOrDefault("subject", config.getSubject()));
			try {
				message.setText(engine.render(req.getVirtualHost(), config.getTemplate(), body), "utf-8", "html");
			} catch (Exception e) {
				message.setText(body.toString());
			}
			message.setFrom(new InternetAddress((String) body.getOrDefault("from", config.getFrom())));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.getTo()));
			mailer.send(profile, message);*/
		} catch (Exception e) {
			e.printStackTrace();
			req.getResponse().setStatusCode(500);
			req.getResponse().end("Something went wrong: " + e.getMessage());
		}
	}
}
