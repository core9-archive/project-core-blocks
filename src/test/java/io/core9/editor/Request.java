package io.core9.editor;

public interface Request {

	void setAbsoluteUrl(String absoluteUrl);

	String getPath();

	String getHost();

}
