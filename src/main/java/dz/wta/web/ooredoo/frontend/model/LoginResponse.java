package dz.wta.web.ooredoo.frontend.model;

public class LoginResponse extends GenericResponse {
	
	private String token;

	public LoginResponse(String message,int code,String token) {
		super(message,code);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
