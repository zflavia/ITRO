package ro.uvt.fmi.itro.traducator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

@WebServlet(name = "oneTraducatorView", description = "View traducator specific", urlPatterns = "/traducator")
public class TraducatorServlet extends HttpServlet {
	
	@EJB
	TraducatorRemote traducatorEJB;
	private static final Logger logger = Logger.getLogger(TraducatorServlet.class.toString());

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
	        
        Map <String, String[]> m= request.getParameterMap();
        for (String key:m.keySet()){
        	System.out.println("............  " + key + "   " + Arrays.toString( m.get(key)));
        }
        
        long id = Long.parseLong(request.getParameter("id"));
		Traducator traducaror = traducatorEJB.getById(id);
		System.out.println("__ Traducator slectat: "  + new Gson().toJson(traducaror));
		 response.setCharacterEncoding("UTF8");
         response.setContentType("application/json");
         PrintWriter writer = response.getWriter();
         Gson gson = new GsonBuilder().create();
         gson.toJson(traducaror, writer);
	}
	
}

