package dz.wta.web.ooredoo.frontend.model;

public class GenericResponse {
	private int code;
	private String message;

	public GenericResponse() {
		super();
	}

	public GenericResponse(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
