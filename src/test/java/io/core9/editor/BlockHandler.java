package io.core9.editor;

public interface BlockHandler {


	boolean checkWorkingDirectory();

	void createWorkingDirectory();

	void deleteWorkingDirectory();

	void setRequest(Request request);

	String getUrlId();

	String getHostId();

	void downloadBlockFromGit(String httpsRepositoryUrl);

	void createHostDirectory();

	boolean checkHostDirectory();

	void deleteHostDirectory();

	boolean checkPageDirectory();

	void createPageDirectory();

	void deletePageDirectory();

}
