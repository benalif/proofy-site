package dz.wta.web.ooredoo.frontend.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
 
                private AuthenticationManager authenticationManager;
 
 
                public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
                               super();
                               this.authenticationManager = authenticationManager;                             
                }
 
                @Override
                public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                                               throws AuthenticationException {                                                        
                                               System.out.println("AttemptAuthentication V62 --------");
                                               String username = obtainUsername(request);
                                               String password = obtainPassword(request);
                                               username = (username == null) ? "" : username;
                                               password = (password == null) ? "" : password;
                                               System.out.println("username:" + username + ", password:" + password);     
                                               return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));                       
                }
 
                @Override
                protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                               FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
                               // une fois loginé
                               System.out.println("------SuccessfulAuthentication-------");
                               User springUser = (User) authResult.getPrincipal();
                               String jwtToken = Jwts.builder().setSubject(springUser.getUsername())
                                                               .setExpiration(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION))
                                                               .signWith(SignatureAlgorithm.HS512, SecurityParams.SECRET).claim("roles", springUser.getAuthorities())
                                                               .compact();
                               System.err.println("jwtToken-------------: " + jwtToken);
                               response.addHeader(SecurityParams.HEADER_NAME, SecurityParams.HEADER_PREFEX + jwtToken);
                }
 
                @Override
                protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                               AuthenticationException failed) throws IOException, ServletException {
                              
                               //SecurityContextHolder.clearContext();
                               System.err.println("Erreur de l'authentication..V2....");
                               response.addHeader("erreur", "Authentication failed");                                          
                               response.setStatus(401);                          
                               response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: Username/Pwd Not valid ");                                    
                }
 
}