package top.ruilink.realworld.exception;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("errors")
public class ErrorMessages {
	private int status;
	private String timestamp;
	private String messaage;
	private String description;

	public ErrorMessages(int status, String timestamp, String messaage, String description) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.messaage = messaage;
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the messaage
	 */
	public String getMessaage() {
		return messaage;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
