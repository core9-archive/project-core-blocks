package io.core9.editor;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestImpl implements Request {

	private URL urlObject;

	@Override
	public String getHost() {
		return urlObject.getHost();
	}

	@Override
	public String getPath() {
		return urlObject.getPath();
	}

	@Override
	public void setAbsoluteUrl(String absoluteUrl) {
		try {
			this.urlObject = new URL(absoluteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		return urlObject.hashCode();
	}

}
