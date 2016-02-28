package me.MiniDigger.Foundation.handler.config;

public abstract class Config {
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}
}
