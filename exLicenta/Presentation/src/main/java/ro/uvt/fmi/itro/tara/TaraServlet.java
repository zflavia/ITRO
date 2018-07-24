package ro.uvt.fmi.itro.tara;

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

import ro.uvt.fmi.itro.ejb.tara.TaraRemote;
import ro.uvt.fmi.persistenta.tara.Tara;

@WebServlet(name = "taraView", description = "Tara Managment insert/update/delete", urlPatterns = "/taraView")
public class TaraServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	TaraRemote taraEJB;

	private static final Logger logger = Logger.getLogger(TaraServlet.class.toString());

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
		List<Tara> taraList = taraEJB.getAll();

		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");

		//logger.log(level, msg, thrown);
		PrintWriter writer = response.getWriter();
		Gson gson = new GsonBuilder().create();
		gson.toJson(taraList, writer);
	}

}
