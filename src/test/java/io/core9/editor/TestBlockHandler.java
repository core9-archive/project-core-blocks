package io.core9.editor;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBlockHandler {

	private static final String pathPrefix = "data/editor/";
	private BlockHandler blockHandler;
	private RequestImpl request;
	private String hostId = "334389048b872a533002b34d73f8c29fd09efc50";
	private String urlId = "a8132caf2be3cf0114a96482d8f8799d192d9583";

	private void setupWorkingDirectory() {
		blockHandler = new BlockHandlerImpl(pathPrefix);
		blockHandler.deleteWorkingDirectory();
		assertFalse(blockHandler.checkWorkingDirectory());
		blockHandler.createWorkingDirectory();
		assertTrue(blockHandler.checkWorkingDirectory());
	}
	private void setUpRequest(){
		request = new RequestImpl();
		request.setAbsoluteUrl("http://localhost/module-page-editor/src/impl/resources/editor/clients/easydrain/pages/frontpage.html");
	}

	@Test
	public void testCreateIdFromUrl() {
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		assertTrue(urlId.equals(blockHandler.getUrlId()));
	}

	@Test
	public void testCreateIdFromHost() {
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		assertTrue(hostId.equals(blockHandler.getHostId()));
	}

	@Test
	public void testCreateHostDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		assertFalse(blockHandler.checkHostDirectory());
		blockHandler.createHostDirectory();
		assertTrue(blockHandler.checkHostDirectory());
	}

	@Test
	public void testDeleteHostDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		blockHandler.createHostDirectory();
		assertTrue(blockHandler.checkHostDirectory());
		blockHandler.deleteHostDirectory();
		assertFalse(blockHandler.checkHostDirectory());
	}

	@Test
	public void testCreatePageDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		assertFalse(blockHandler.checkPageDirectory());
		blockHandler.deleteHostDirectory();
		blockHandler.createPageDirectory();
		assertTrue(blockHandler.checkPageDirectory());
	}

	@Test
	public void testDeletePageDirectory(){
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		blockHandler.createPageDirectory();
		assertTrue(blockHandler.checkPageDirectory());
		blockHandler.deletePageDirectory();
		assertFalse(blockHandler.checkPageDirectory());
	}


	//@Test
	public void testDownloadBlockFromGit(){
		setupWorkingDirectory();
		setUpRequest();
		blockHandler.setRequest(request);
		blockHandler.downloadBlockFromGit();
		fail("TODO");
	}

	@Test
	public void testCreateWorkingDirectory() {
		BlockHandler blockHandler = new BlockHandlerImpl(pathPrefix);
		blockHandler.createWorkingDirectory();
		assertTrue(blockHandler.checkWorkingDirectory());
	}

	@Test
	public void testDeleteWorkingDirectory() {
		BlockHandler blockHandler = new BlockHandlerImpl(pathPrefix);
		blockHandler.createWorkingDirectory();
		assertTrue(blockHandler.checkWorkingDirectory());
		blockHandler.deleteWorkingDirectory();
		assertFalse(blockHandler.checkWorkingDirectory());
	}

}
