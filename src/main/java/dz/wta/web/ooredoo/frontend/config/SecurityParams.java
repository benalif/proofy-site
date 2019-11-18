package dz.wta.web.ooredoo.frontend.config;

public interface SecurityParams {
	 
    public static final String HEADER_NAME = "Authorisation";
    public static final String SECRET = "Wehhab";
    public static final long EXPIRATION = 10 * 24 * 3600;
    public static final String HEADER_PREFEX = "Bearer "; 
  
}