package io.core9.editor;

import java.io.File;
import java.io.IOException;

public class BlockHandlerImpl implements BlockHandler {

	private String pathPrefix;
	private String healthFile = "health.txt";

	public BlockHandlerImpl(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	@Override
	public void createWorkingDirectory() {
		new File(pathPrefix).mkdirs();
		File yourFile = new File(pathPrefix + File.separator + healthFile);
		if (!yourFile.exists()) {
			try {
				yourFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean checkWorkingDirectory() {
		return new File(pathPrefix + File.separator + healthFile).exists();
	}

	@Override
	public void deleteWorkingDirectory() {
		deleteDirectory(new File(pathPrefix));
	}

	private static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

}
