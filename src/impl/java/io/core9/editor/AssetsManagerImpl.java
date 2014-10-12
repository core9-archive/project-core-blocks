package io.core9.editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

public class AssetsManagerImpl implements AssetsManager {

	private String pathPrefix;
	private String healthFile = "health.txt";
	private String blockDir = "blocks";
	private Request request;
	private String blockRepositoryDirectory;
	private String siteRepositoryDirectory;
	private String siteDir = "site";
	private String siteConfigFile;

	public AssetsManagerImpl(String pathPrefix) {
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

	private void createDirectory(String directory) {
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
	public String getClientId() {
		return getShaId(request.getClient());
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
			crypt.update(hashCode.getBytes(StandardCharsets.UTF_8));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
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
	public void createClientDirectory() {
		createDirectory(pathPrefix + File.separator + getClientId());
	}

	@Override
	public boolean checkClientDirectory() {
		return new File(pathPrefix + File.separator + getClientId()).exists();
	}

	@Override
	public void deleteClientDirectory() {
		deleteDirectory(new File(pathPrefix + File.separator + getClientId()));
	}

	@Override
	public boolean checkSiteDirectory() {
		return new File(pathPrefix + File.separator + getClientId() + File.separator + "site").exists();
	}

	@Override
	public void createSiteDirectory() {
		createDirectory(pathPrefix + File.separator + getClientId() + File.separator + "site");
	}

	@Override
	public void deleteSiteDirectory() {
		deleteDirectory(new File(pathPrefix + File.separator + getClientId() + File.separator + "site"));
	}

	@Override
	public void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException {
		createBlockDirectory();
		if (checkBlockDirectoryIfExists()) {
			String fileName = httpsRepositoryUrl.substring(httpsRepositoryUrl.lastIndexOf('/') + 1, httpsRepositoryUrl.length());
			String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
			blockRepositoryDirectory = "../.." + File.separator + pathPrefix + File.separator + getClientId() + File.separator + blockDir + File.separator + fileNameWithoutExtn;
			clonePublicGitRepository(httpsRepositoryUrl, blockRepositoryDirectory);
		} else {
			throw new FileNotFoundException(pathPrefix + File.separator + getClientId() + File.separator + blockDir);
		}

	}

	private void clonePublicGitRepository(String httpsRepositoryUrl, String repositoryDirectory) {
		GitHandler git = new GitHandlerImpl(httpsRepositoryUrl, repositoryDirectory);
		git.init();
	}

	private void createBlockDirectory() {
		createDirectory(pathPrefix + File.separator + getClientId() + File.separator + blockDir);
	}

	private boolean checkBlockDirectoryIfExists() {
		return new File(pathPrefix + File.separator + getClientId() + File.separator + blockDir).exists();
	}

	@Override
	public String getRepositoryDirectory() {
		return blockRepositoryDirectory;
	}

	@Override
	public boolean checkIfRepositoryDirectoryExists() {
		return new File("data/git/" + blockRepositoryDirectory).exists();
	}

	@Override
	public JSONObject getSiteConfig() {
		String config = readFile(siteConfigFile, StandardCharsets.UTF_8);
		JSONObject obj = new JSONObject();
		try {
			obj = (JSONObject) JSONValue.parseStrict(config);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public boolean checkSite() {
		return new File(siteConfigFile).exists();
	}

	@SuppressWarnings("unused")
	private void writeToFile(String fileName, String content) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
			writer.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
	}

	private String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, encoding);
	}

	@Override
	public void clonePublicSiteFromGit(String httpsRepositoryUrl) {
		siteRepositoryDirectory = "../.." + File.separator + pathPrefix + File.separator + getClientId() + File.separator + siteDir;
		siteConfigFile = "data/git" + File.separator + siteRepositoryDirectory + File.separator + "site.json";
		clonePublicGitRepository(httpsRepositoryUrl, siteRepositoryDirectory);
	}

	@Override
	public String getSiteRepositoryDirectory() {
		return siteRepositoryDirectory;
	}

}
