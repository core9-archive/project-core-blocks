package io.core9.editor;

import java.io.FileNotFoundException;

public interface BlockHandler {


	boolean checkWorkingDirectory();

	void createWorkingDirectory();

	void deleteWorkingDirectory();

	void setRequest(Request request);

	String getUrlId();

	String getHostId();

	void downloadBlockFromGit(String httpsRepositoryUrl) throws FileNotFoundException;

	void createHostDirectory();

	boolean checkHostDirectory();

	void deleteHostDirectory();

	boolean checkPageDirectory();

	void createPageDirectory();

	void deletePageDirectory();

	String getRepositoryDirectory();

	boolean checkIfRepositoryDirectoryExists();

	void downloadPage();

	boolean checkPage();

	String getPage();

}
