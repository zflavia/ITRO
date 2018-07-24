package ro.uvt.fmi.itro.traducator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ro.uvt.fmi.itro.ejb.traducator.TraducatorRemote;
import ro.uvt.fmi.persistenta.traducator.Traducator;
import ro.uvt.fmi.persistenta.traducator.TraducatorToShort;
import ro.uvt.fmi.persistenta.user.User;

@WebServlet(name = "oneTraducatorAdmin", description = "insert/update/delete traducator specific", urlPatterns = "/traducatorMng")
public class TraducatorMngServlet extends HttpServlet {
	
	private static final String ACTION_EDIT = "edit";
	private static final String ACTION_DELETE = "delete";
	private static final String ACTION_INSERT = "insert";
	
	@EJB
	TraducatorRemote traducatorEJB;
	private static final Logger logger = Logger.getLogger(TraducatoriServlet.class.toString());

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

		if (ACTION_EDIT.equals(type)) {
			id = jObj.getLong("id");

			String nume = jObj.getString("nume");
			String prenume = jObj.getString("prenume");
			String aspecteBibliografice = jObj.getString("aspecteBibliografice");
			String atributiiContributii = jObj.getString("atributiiContributiiEditoriale");
			String activitateTraducator = jObj.getString("activitateTraducator");
			
			Traducator u = traducatorEJB.getById(id);
			u.setNume(nume);
			u.setPrenume(prenume);
			u.setAspecteBibliografice(aspecteBibliografice);
			u.setAtributiiContributiiEditoriale(atributiiContributii);
			u.setActivitateTraducator(activitateTraducator);
			
			traducatorEJB.updateTraducator(u);
			
			

		} else if (ACTION_DELETE.equals(type)) {
			id = jObj.getLong("id");

			
			
//			User u = userEJB.getUserById(id);
//			u.setStatus(User.USER_STATUS.INACTIVE.getLabel());
//			// userEJB.deleteUser(id);
//			userEJB.updateUser(u);

		} else if (ACTION_INSERT.equals(type)) {
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

		
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	

}
