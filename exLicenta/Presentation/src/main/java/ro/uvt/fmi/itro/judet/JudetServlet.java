package ro.uvt.fmi.itro.judet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ro.uvt.fmi.itro.ejb.judet.JudetRemote;
import ro.uvt.fmi.itro.ejb.tara.TaraRemote;
import ro.uvt.fmi.itro.tara.TaraServlet;
import ro.uvt.fmi.persistenta.judet.Judet;
import ro.uvt.fmi.persistenta.tara.Tara;

@WebServlet(name = "judetView", description = "Judet Managment insert/update/delete", urlPatterns = "/judetView")
public class JudetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	JudetRemote judetEJB;

	private static final Logger logger = Logger.getLogger(JudetServlet.class.toString());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGET(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGET(request, response);
	}

	void processGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Judet> judetList = judetEJB.getAll();

		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");

		//logger.log(level, msg, thrown);
		PrintWriter writer = response.getWriter();
		Gson gson = new GsonBuilder().create();
		gson.toJson(judetList, writer);
	}
}
