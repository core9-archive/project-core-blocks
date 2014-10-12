package io.core9.editor;

import java.io.FileNotFoundException;

public interface AssetsManager {


	boolean checkWorkingDirectory();

	void createWorkingDirectory();

	void deleteWorkingDirectory();

	void setRequest(Request request);

	String getUrlId();

	String getHostId();

	void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException;

	void createHostDirectory();

	boolean checkHostDirectory();

	void deleteHostDirectory();

	boolean checkSiteDirectory();

	void createSiteDirectory();

	void deleteSiteDirectory();

	String getRepositoryDirectory();

	boolean checkIfRepositoryDirectoryExists();

	boolean checkPage();

	String getPage();

	void cloneSiteFromGit(String httpsRepositoryUrl);

	void getSiteRepositoryDirectory();

}
