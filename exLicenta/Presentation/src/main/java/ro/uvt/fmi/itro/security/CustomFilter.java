package ro.uvt.fmi.itro.security;

import java.io.IOException;
import java.security.Key;
import java.util.StringTokenizer;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

public class CustomFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(CustomFilter.class);
	private String realm = "Protected";
	private KeyGenerator keyGenerator = new SimpleKeyGenerator();
	private FilterConfig filter;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filter  = filterConfig;
		
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		  String encryptedToken = "";
		  HttpServletRequest request = (HttpServletRequest) servletRequest;
		  HttpServletResponse response = (HttpServletResponse) servletResponse;
		  Cookie[] c =  request.getCookies();
		  if(c==null)
		  {
			  
			  String s = request.getContextPath()+"/loginITRO";
			  response.sendRedirect(s);
			  
	          return;
		  }
		  for(int i = 0 ; i < c.length; i++) {
			  if(c[i].getName().toLowerCase().equals("nume".toLowerCase())) {
				  encryptedToken = c[i].getValue();
				  System.out.println("cookie" + c[i]);
			  }
			  
		  }
		  System.out.println("token==" + encryptedToken);
	      try {

	            // Validate the token
	            Key key = keyGenerator.generateKey();
	            Jwts.parser().setSigningKey(key).parseClaimsJws(encryptedToken);
	            System.out.println("#### valid token : " + encryptedToken);
	            filterChain.doFilter(servletRequest, servletResponse);

	        } catch (Exception e) {
	        	System.out.println("#### invalid token : " + encryptedToken);
	        	
	     
	        	RequestDispatcher rd = filter.getServletContext().getRequestDispatcher(request.getContextPath()+"/loginITRO");
	        	rd.forward(request, response);
	        	
	        	return;
	        }
	      
	}

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
