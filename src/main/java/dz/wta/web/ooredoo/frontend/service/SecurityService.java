package dz.wta.web.ooredoo.frontend.service;

public interface SecurityService {
	
	String findLoggedInUsername();

    void autologin(String username, String password);

}
