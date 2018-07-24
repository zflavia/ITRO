package ro.uvt.fmi.itro.traducator;

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

import ro.uvt.fmi.itro.ejb.traducator.TraducatorRemote;
import ro.uvt.fmi.persistenta.traducator.Traducator;
import ro.uvt.fmi.persistenta.traducator.TraducatorToShort;

@WebServlet(name = "traducatorView", description = "View traducator", urlPatterns = "/traducatoriView")
public class TraducatoriServlet extends HttpServlet {
	
	@EJB
	TraducatorRemote traducatorEJB;
	private static final Logger logger = Logger.getLogger(TraducatoriServlet.class.toString());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {         
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processGET(request, response);
	}

	void processGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//List<Traducator> traducarorList = traducatorEJB.getAll();
		List<TraducatorToShort> traducarorList = traducatorEJB.getAllToShort();
		
		
		System.out.println("traducarorList: "  + traducarorList);
		
		 String json = new Gson().toJson(traducarorList);
		 
		 response.setCharacterEncoding("UTF8");
         response.setContentType("application/json");
         //response.getWriter().write(json);
         PrintWriter writer = response.getWriter();

         Gson gson = new GsonBuilder().create();
         gson.toJson(traducarorList, writer);
	}
	
}
