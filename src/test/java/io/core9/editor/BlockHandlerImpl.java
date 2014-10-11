package io.core9.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class BlockHandlerImpl implements BlockHandler {

	private String pathPrefix;
	private String healthFile = "health.txt";
	private String blockDir = "blocks";
	private Request request;
	private String repositoryDirectory;



	public BlockHandlerImpl(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	@Override
	public void createWorkingDirectory() {
		createDirectory(pathPrefix);
		File yourFile = new File(pathPrefix + File.separator + healthFile);
		if (!yourFile.exists()) {
			try {
				yourFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createDirectory(String directory){
		new File(directory).mkdirs();
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

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public String getHostId() {
		return getShaId(request.getHost());
	}

	@Override
	public String getUrlId() {
		return getShaId(Integer.toString(request.hashCode()));
	}

	private static String getShaId(String hashCode) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(hashCode.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public void downloadBlockFromGit() {

	}

	@Override
	public void createHostDirectory() {
		createDirectory(pathPrefix + File.separator + getHostId());
	}

	@Override
	public boolean checkHostDirectory() {
		return new File(pathPrefix + File.separator + getHostId()).exists();
	}

	@Override
	public void deleteHostDirectory() {
		deleteDirectory(new File(pathPrefix + File.separator + getHostId()));
	}

	@Override
	public boolean checkPageDirectory() {
		return new File(pathPrefix + File.separator + getHostId() + File.separator + getUrlId()).exists();
	}

	@Override
	public void createPageDirectory() {
		createDirectory(pathPrefix + File.separator + getHostId() + File.separator + getUrlId());
	}

	@Override
	public void deletePageDirectory() {
		deleteDirectory(new File(pathPrefix + File.separator + getHostId() + File.separator + getUrlId()));
	}

	@Override
	public void downloadBlockFromGit(String httpsRepositoryUrl) throws FileNotFoundException {
		createBlockDirectory();
		if(checkBlockDirectoryIfExists()){

			String fileName = httpsRepositoryUrl.substring( httpsRepositoryUrl.lastIndexOf('/')+1, httpsRepositoryUrl.length() );
			String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
			repositoryDirectory = "../.." + File.separator +  pathPrefix + File.separator + getHostId() + File.separator + blockDir + File.separator + fileNameWithoutExtn;
			GitHandler git = new GitHandlerImpl(httpsRepositoryUrl, repositoryDirectory);
			git.init();
			//git.pull();
		}else{
			// should be directory does not exists
			throw new FileNotFoundException(pathPrefix + File.separator + getHostId() + File.separator + blockDir);
		}

	}

	private void createBlockDirectory() {
		createDirectory(pathPrefix + File.separator + getHostId() + File.separator + blockDir);
	}

	private boolean checkBlockDirectoryIfExists() {
		return new File(pathPrefix + File.separator + getHostId() + File.separator + blockDir).exists();
	}

	@Override
	public String getRepositoryDirectory() {
		return repositoryDirectory;
	}

	@Override
	public boolean checkIfRepositoryDirectoryExists() {
		return new File("data/git/" + repositoryDirectory).exists();
	}

	@Override
	public void downloadPage() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkPage() {
		// TODO Auto-generated method stub
		return false;
	}

}
