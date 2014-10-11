package io.core9.editor;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class TestBlockHandler {

	private static final String pathPrefix = "data/editor";
	private AssetsManager assetsManager;
	private RequestImpl request;
	private String hostId = "334389048b872a533002b34d73f8c29fd09efc50";
	private String urlId = "a8132caf2be3cf0114a96482d8f8799d192d9583";

	private void setupWorkingDirectory() {
		assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}
	private void setUpRequest(){
		request = new RequestImpl();
		request.setAbsoluteUrl("http://localhost/module-page-editor/src/impl/resources/editor/clients/easydrain/pages/frontpage.html");
	}

	@Test
	public void testCreateIdFromUrl() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertTrue(urlId.equals(assetsManager.getUrlId()));
	}

	@Test
	public void testCreateIdFromHost() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertTrue(hostId.equals(assetsManager.getHostId()));
	}

	@Test
	public void testCreateHostDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertFalse(assetsManager.checkHostDirectory());
		assetsManager.createHostDirectory();
		assertTrue(assetsManager.checkHostDirectory());
	}

	@Test
	public void testDeleteHostDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.createHostDirectory();
		assertTrue(assetsManager.checkHostDirectory());
		assetsManager.deleteHostDirectory();
		assertFalse(assetsManager.checkHostDirectory());
	}

	@Test
	public void testCreatePageDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertFalse(assetsManager.checkPageDirectory());
		assetsManager.deleteHostDirectory();
		assetsManager.createPageDirectory();
		assertTrue(assetsManager.checkPageDirectory());
	}

	@Test
	public void testDownloadPageInPageDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.downloadPage();
		assertTrue(assetsManager.checkPage());
		System.out.println(assetsManager.getPage());
	}


	@Test
	public void testDeletePageDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.createPageDirectory();
		assertTrue(assetsManager.checkPageDirectory());
		assetsManager.deletePageDirectory();
		assertFalse(assetsManager.checkPageDirectory());
	}


	@Test
	public void testDownloadBlockFromGit() throws FileNotFoundException, InterruptedException{
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.downloadBlockFromGit("https://github.com/jessec/block-video.git");
		assetsManager.getRepositoryDirectory();
		assertTrue(assetsManager.checkIfRepositoryDirectoryExists());
	}

	@Test
	public void testDownloadPagesFromGit() throws FileNotFoundException, InterruptedException{
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.downloadPagesFromGit("https://github.com/jessec/site-core9.git");
		assetsManager.getPagesRepositoryDirectory();
		//assertTrue(blockHandler.checkIfRepositoryDirectoryExists());
	}


	@Test
	public void testCreateWorkingDirectory() {
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}

	@Test
	public void testDeleteWorkingDirectory() {
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}

}
