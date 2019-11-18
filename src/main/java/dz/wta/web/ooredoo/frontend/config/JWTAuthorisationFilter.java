package dz.wta.web.ooredoo.frontend.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
 
class JWTAuthorisationFilter extends OncePerRequestFilter {
 
                public JWTAuthorisationFilter() {
                }
 
                @Override
                protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                                               throws ServletException, IOException {
                               System.out.println("request.getRequestURI():302 -->" + response.getStatus() + ", " + request.getMethod() + ", "
                                                               + request.getRequestURL());
 
                               response.setHeader("Access-Control-Allow-Origin", "*");
                               response.setHeader("Access-Control-Allow-Credentials", "true");
                               response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                               response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
 
                               String path = request.getContextPath();
                               String url = request.getRequestURI();
                               path = url.replace(path, "");
 
                               if (request.getMethod().equals("OPTIONS")) {
                                               response.setStatus(HttpServletResponse.SC_OK);
                               } else if (path.equals("/login") || path.equals("/add") || path.equals("/") || path.equals("/save-user")
                                                               || path.equals("/home") || path.equals("/list-user") || path.equals("/list-role")) {
                                               chain.doFilter(request, response);
                                               return;
                               } else {
 
                                               String jwtToken = request.getHeader(SecurityParams.HEADER_NAME);
                                               System.err.println("jwtToken--> " + jwtToken);
                                               if (jwtToken == null || !jwtToken.startsWith(SecurityParams.HEADER_PREFEX)) {
                                                               chain.doFilter(request, response);
                                                               return;
                                               }
                                               Claims claims = Jwts.parser().setSigningKey(SecurityParams.SECRET)
                                                                              .parseClaimsJws(jwtToken.replace(SecurityParams.HEADER_PREFEX, "")).getBody();
                                               String username = claims.getSubject();
                                               ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
                                               Collection<GrantedAuthority> authorities = new ArrayList<>();
                                               roles.forEach(r -> {
                                                               authorities.add(new SimpleGrantedAuthority(r.get("authority")));
                                               });
                                               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                                                                              null, authorities);
                                               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                               chain.doFilter(request, response);
                               }
                }
 
}