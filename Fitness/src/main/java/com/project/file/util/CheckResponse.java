package com.project.file.util;

public class CheckResponse {
	private boolean isDuplicate;

	public CheckResponse(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
}


