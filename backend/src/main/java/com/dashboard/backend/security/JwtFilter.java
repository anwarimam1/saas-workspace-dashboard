package com.dashboard.backend.security;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dashboard.backend.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

import org.springframework.security.core.userdetails.User;


public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserRepository userRepository;
	
	public JwtFilter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                               HttpServletResponse response,
	                               FilterChain filterChain)
	        throws ServletException, IOException {

	    String path = request.getServletPath();
	    

	    // ✅ Allow auth endpoints only
	    if (path.startsWith("/api/auth")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String header = request.getHeader("Authorization");

	    // ❌ BLOCK if no token
	    if (header == null || !header.startsWith("Bearer ")) {
	    	filterChain.doFilter(request, response);
	        return;
	    }

	    String token = header.substring(7);

	    if (!JwtUtil.validateToken(token)) {
	    	 filterChain.doFilter(request, response);
	        return;
	    }
	    
	    String email = JwtUtil.extractEmail(token);

	    com.dashboard.backend.entity.User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // 🔥 ALWAYS TRUST DB ROLE
	    String role = "ROLE_" + user.getRole().name();

	    List<SimpleGrantedAuthority> authorities =
	            List.of(new SimpleGrantedAuthority(role));

	    UserDetails userDetails = new User(
	            user.getEmail(),
	            user.getPassword(),
	            authorities
	    );

	    UsernamePasswordAuthenticationToken auth =
	            new UsernamePasswordAuthenticationToken(
	                    userDetails,
	                    null,
	                    userDetails.getAuthorities()
	            );

	    SecurityContextHolder.getContext().setAuthentication(auth);

	    filterChain.doFilter(request, response);
	}
}