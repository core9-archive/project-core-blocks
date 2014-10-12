package io.core9.editor;

import java.io.FileNotFoundException;

import net.minidev.json.JSONObject;

public interface AssetsManager {


	boolean checkWorkingDirectory();

	void createWorkingDirectory();

	void deleteWorkingDirectory();

	void setRequest(Request request);

	String getUrlId();

	String getClientId();

	void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException;

	void createClientDirectory();

	boolean checkClientDirectory();

	void deleteClientDirectory();

	boolean checkSiteDirectory();

	void createSiteDirectory();

	void deleteSiteDirectory();

	String getRepositoryDirectory();

	boolean checkIfRepositoryDirectoryExists();

	boolean checkSite();

	JSONObject getSiteConfig();

	void clonePublicSiteFromGit(String httpsRepositoryUrl);

	String getSiteRepositoryDirectory();

	String getSiteConfigFile();

	String getPageTemplate();

	boolean checkIfPageTemplateExists();

}
