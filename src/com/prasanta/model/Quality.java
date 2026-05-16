package com.prasanta.model;

public class Quality {

	private String formatId;
	private String extension;
	private String resolution;
	private String resolutionValue;
	private String fileSize;
	private String note;
	
	public Quality() {
		super();
	}

	public Quality(String formatId, String extension, String resolution, String resolutionValue, String fileSize,
			String note) {

		this.formatId = formatId;
		this.extension = extension;
		this.resolution = resolution;
		this.resolutionValue = resolutionValue;
		this.fileSize = fileSize;
		this.note = note;
	}

	public String getFormatId() {
		return formatId;
	}

	public String getExtension() {
		return extension;
	}

	public String getResolution() {
		return resolution;
	}

	public String getResolutionValue() {
		return resolutionValue;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getNote() {
		return note;
	}

	@Override
	public String toString() {
		return "Quality [formatId=" + formatId + ", extension=" + extension + ", resolution=" + resolution
				+ ", fileSize=" + fileSize + ", note=" + note + "]";
	}

}