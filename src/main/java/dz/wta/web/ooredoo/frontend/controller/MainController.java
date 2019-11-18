package dz.wta.web.ooredoo.frontend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import dz.wta.web.ooredoo.frontend.model.GenericResponse;
import dz.wta.web.ooredoo.frontend.model.LoginRequest;
import dz.wta.web.ooredoo.frontend.model.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class MainController {

	@GetMapping("/api/")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> main(@RequestHeader(required = true) String authorization) {

		return new ResponseEntity<>(new GenericResponse("Back end Service", 0), HttpStatus.OK);
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> login(@RequestBody(required = true) LoginRequest request) {

		if (request.getUsername().equals("farid") && request.getPassword().equals("farid")) {

			Claims body = Jwts.claims().setSubject(request.getUsername());

			return new ResponseEntity<>(new LoginResponse("success", 0, Jwts.builder().setClaims(body).signWith(SignatureAlgorithm.HS256, "MySrecet").compact()), HttpStatus.OK);
		}

		return new ResponseEntity<>(new GenericResponse("bad credentials", 0), HttpStatus.UNAUTHORIZED);
	}

}
