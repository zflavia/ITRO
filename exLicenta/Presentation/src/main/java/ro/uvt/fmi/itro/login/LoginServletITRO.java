package ro.uvt.fmi.itro.login;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ro.uvt.fmi.itro.ejb.user.UserRemote;
import ro.uvt.fmi.itro.security.KeyGenerator;
import ro.uvt.fmi.itro.security.SimpleKeyGenerator;

@WebServlet(name = "loginServlet", description = "AuthenticationServlet", urlPatterns = { "/loginITRO" })
public class LoginServletITRO extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	UserRemote userEJB;
	
	@Context
    private UriInfo uriInfo;

//    @Inject
//    private Logger logger;

    
    private KeyGenerator keyGenerator = new SimpleKeyGenerator();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		processRequest(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			StringBuilder sb = new StringBuilder();
			BufferedReader br = request.getReader();
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);

				System.out.println("..........." + str);
			}
			JSONObject jObj = new JSONObject(sb.toString());
			username = jObj.getString("username");
			password = jObj.getString("password");
			System.out.println("name: " + username + " password: " + password);
			if (request.getUserPrincipal() != null) {
				request.logout();
			}
			System.out.println("--- In login sertvelet (" + username + ":" + password + ")");
			if (username != null && password != null) {
				response.setCharacterEncoding("UTF8");
				response.setContentType("application/json");	
				 request.login(username, password);
			}

			CustomPrincipal principal = (CustomPrincipal) request.getUserPrincipal();
			System.out.println("principal: " + principal);
			String token = issueToken(username);
			response.addHeader(AUTHORIZATION, "Bearer " + token);
	        response.setCharacterEncoding("UTF8");
	        response.setContentType("application/json");
	        Cookie c = new Cookie("nume",token);
	        response.addCookie(c);
			Gson gson = new GsonBuilder().create();
			gson.toJson(token, response.getWriter());

		} catch (Exception e) {
			e.printStackTrace();

			if (e instanceof ServletException) {
				Throwable ex = null;
				do {
					ex = ((ServletException) e).getRootCause();

					if (ex != null)
						ex.printStackTrace();
				} while (ex != null);
			}

			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	
	


private String issueToken(String login) {
    Key key = keyGenerator.generateKey();
    String jwtToken = Jwts.builder()
            .setSubject(login)
            .setIssuedAt(new Date())
            .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
            .signWith(SignatureAlgorithm.HS512, key)
            .compact();
    return jwtToken;
}

private Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
}
}
