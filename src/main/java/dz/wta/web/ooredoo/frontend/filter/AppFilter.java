package dz.wta.web.ooredoo.frontend.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;

public class AppFilter extends GenericFilterBean {

	private static final String key = "MySrecet";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("**** *** ** * ------ * ** *** ****");
		System.out.println("**** *** ** * Filter * ** *** ****");
		System.out.println("**** *** ** * ------ * ** *** ****");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authorization = httpRequest.getHeader("Authorization");

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (authorization != null) {

			try {
				Jws<Claims> res = Jwts.parser().setSigningKey(key).parseClaimsJws(authorization);
				String username = res.getBody().getSubject();

				httpRequest.setAttribute("username", username);

				chain.doFilter(httpRequest, response);

			} catch (MissingClaimException | ExpiredJwtException | MalformedJwtException e) {
				httpResponse.setContentType("application/json");
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.getOutputStream().write(("{\"code\":\"" + HttpServletResponse.SC_UNAUTHORIZED + "\",").getBytes());
				httpResponse.getOutputStream().write(("\"message\":\"" + e.getMessage() + "\"}").getBytes());
				httpResponse.getOutputStream().flush();
			} catch (JwtException e) {
				httpResponse.setContentType("application/json");
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.getOutputStream().write(("{\"code\":\"" + HttpServletResponse.SC_UNAUTHORIZED + "\",").getBytes());
				httpResponse.getOutputStream().write(("\"message\":\"" + e.getMessage() + "\"}").getBytes());
				httpResponse.getOutputStream().flush();
			}
		} else {
			httpResponse.setContentType("application/json");
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getOutputStream().write(("{\"code\":\"" + HttpServletResponse.SC_UNAUTHORIZED + "\",").getBytes());
			httpResponse.getOutputStream().write("\"message\":\"Token Required\"}".getBytes());
			httpResponse.getOutputStream().flush();
		}

	}
}
