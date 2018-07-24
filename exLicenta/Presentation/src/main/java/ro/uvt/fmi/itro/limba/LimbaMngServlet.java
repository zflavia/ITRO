package ro.uvt.fmi.itro.limba;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ro.uvt.fmi.itro.Utils;
import ro.uvt.fmi.itro.ejb.limba.LimbaRemote;
import ro.uvt.fmi.persistenta.limba.Limba;

@WebServlet(name = "limbaManagment", description = "Limba Managment insert/update/delete", urlPatterns = "/limbaMng")
public class LimbaMngServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	LimbaRemote limbaEJB;

	private static final Logger logger = Logger.getLogger(LimbaServlet.class.toString());

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
		}
		System.out.println("........... post user servlet: " + sb.toString());
		JSONObject jObj = new JSONObject(sb.toString());

		type = jObj.getString("type");

		if (Utils.ACTION_EDIT.equals(type)) {
			id = jObj.getLong("id");
			String nume = jObj.getString("nume");

			Limba l = limbaEJB.getById(id);
			l.setNume(nume);

			try {
				limbaEJB.update(l);
			} catch (EJBException ex) {
				returnErrorState(response, ex.getMessage());
			}

		} else if (Utils.ACTION_DELETE.equals(type)) {
			id = jObj.getLong("id");
			try {
				limbaEJB.delete(id);
			} catch (EJBException ex) {
				returnErrorState(response, ex.getMessage());
			}
		} else if (Utils.ACTION_INSERT.equals(type)) {
			String nume = jObj.getString("nume");

			Limba l = new Limba();
			l.setNume(nume);
			try {
				l = limbaEJB.insert(l);

				response.setCharacterEncoding("UTF8");
				response.setContentType("application/json");
				Gson gson = new GsonBuilder().create();
				gson.toJson(l, response.getWriter());

			} catch (EJBException ex) {
				returnErrorState(response, ex.getMessage());
			}
		}

		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sId = request.getParameter("id");

		long id = Long.parseLong(sId);

		Limba l = limbaEJB.getById(id);

		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");
		Gson gson = new GsonBuilder().create();
		gson.toJson(l, response.getWriter());
	}

	private void returnErrorState(HttpServletResponse response, String msg) {
		response.setCharacterEncoding("UTF8");
		// response.setContentType("application/json");
		try {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
