package io.core9.editor;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBlockHandler {

	private static final String pathPrefix = "data/editor/";

	@Test
	public void testCreateWorkingDirectory(){
		BlockHandler blockHandler = new BlockHandlerImpl(pathPrefix);
		blockHandler.createWorkingDirectory();
		assertTrue(blockHandler.checkWorkingDirectory());
	}

	@Test
	public void testDeleteWorkingDirectory(){
		BlockHandler blockHandler = new BlockHandlerImpl(pathPrefix);
		blockHandler.createWorkingDirectory();
		assertTrue(blockHandler.checkWorkingDirectory());
		blockHandler.deleteWorkingDirectory();
		assertFalse(blockHandler.checkWorkingDirectory());
	}


}
