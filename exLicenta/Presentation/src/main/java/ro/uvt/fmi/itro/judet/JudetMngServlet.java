package ro.uvt.fmi.itro.judet;

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
import ro.uvt.fmi.itro.ejb.judet.JudetRemote;
import ro.uvt.fmi.itro.ejb.tara.TaraRemote;
import ro.uvt.fmi.itro.tara.TaraServlet;
import ro.uvt.fmi.persistenta.judet.Judet;
import ro.uvt.fmi.persistenta.tara.Tara;

@WebServlet(name = "JudetManagment", description = "Judet Managment insert/update/delete", urlPatterns = "/judetMng")
public class JudetMngServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	JudetRemote judetEJB;
	@EJB
	TaraRemote taraEJB;

	private static final Logger logger = Logger.getLogger(TaraServlet.class.toString());

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

			Judet t = judetEJB.getById(id);
			t.setNume(nume);

			
			try{
				Long idTara = jObj.getLong("idTara");
				Tara tt = taraEJB.getById(idTara);
				t.setTara(tt);
			}catch(Exception e){
				
			}
			
			try {
				judetEJB.update(t);
			} catch (EJBException ex) {
				returnErrorState(response, ex.getMessage());
			}

		} else if (Utils.ACTION_DELETE.equals(type)) {
			id = jObj.getLong("id");
			try {
				judetEJB.delete(id);
			} catch (EJBException ex) {
				returnErrorState(response, ex.getMessage());
			}
		} else if (Utils.ACTION_INSERT.equals(type)) {
			String nume = jObj.getString("nume");

			Judet t = new Judet();
			t.setNume(nume);
			
			try{
				Long idTara = jObj.getLong("idTara");
				Tara tt = taraEJB.getById(idTara);
				t.setTara(tt);
			}catch(Exception e){
				
			}
			
			try {
				t = judetEJB.insert(t);

				response.setCharacterEncoding("UTF8");
				response.setContentType("application/json");
				Gson gson = new GsonBuilder().create();
				gson.toJson(t, response.getWriter());

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

		Judet t = judetEJB.getById(id);
		
		System.out.println("Judet: " + t);

		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");
		Gson gson = new GsonBuilder().create();
		gson.toJson(t, response.getWriter());
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

