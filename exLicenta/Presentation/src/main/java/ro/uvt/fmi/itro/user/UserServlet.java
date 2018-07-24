package ro.uvt.fmi.itro.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ro.uvt.fmi.itro.Utils;
import ro.uvt.fmi.itro.ejb.user.UserRemote;
import ro.uvt.fmi.persistenta.user.User;
import ro.uvt.fmi.persistenta.user.UserToShort;

@WebServlet(name = "userManagment", description = "User Managment insert/update/delete", urlPatterns = "/userMng")
public class UserServlet extends HttpServlet {

	@EJB
	UserRemote userEJB;



	private static final Logger logger = Logger.getLogger(UserServlet.class.toString());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("POST");
		String type = null;
		long id;

		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);

			System.out.println("........... post user servlet: " + sb.toString());
		}
		JSONObject jObj = new JSONObject(sb.toString());

		type = jObj.getString("type");

		if (Utils.ACTION_EDIT.equals(type)) {
			id = jObj.getLong("id");
			User u = userEJB.getUserById(id);
			String json = new Gson().toJson(u);
			System.out.println("UPDATE: " + json);
			response.setCharacterEncoding("UTF8");
			response.setContentType("application/json");
			PrintWriter writer = response.getWriter();
			Gson gson = new GsonBuilder().create();
			gson.toJson(u, writer);

		} else if (Utils.ACTION_DELETE.equals(type)) {
			id = jObj.getLong("id");
			User u = userEJB.getUserById(id);
			u.setStatus(User.USER_STATUS.INACTIVE.getLabel());
			userEJB.updateUser(u);

		} else if (Utils.ACTION_INSERT.equals(type)) {
			String firstName = jObj.getString("firstName");
			String lastName = jObj.getString("lastName");
			String un = jObj.getString("userName");
			String alias = jObj.getString("alias");
			String password = jObj.getString("password");

			User u = new User();
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setUserName(un);
			u.setAlias(alias);
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			byte[] digest = md.digest(password.getBytes());

			u.setPassword(Base64.getEncoder().encodeToString(digest));
			u.setStatus(User.USER_STATUS.ACTIVE.getLabel());

			System.out.println(u);

			userEJB.insertUser(u, "face insert");
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGET(request, response);
	}

	void processGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserToShort> userList = userEJB.getAll();

		String json = new Gson().toJson(userList);

		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();

		Gson gson = new GsonBuilder().create();
		gson.toJson(userList, writer);
	}

	
}
