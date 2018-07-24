package ro.uvt.fmi.itro.ejb.traducator;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ro.uvt.fmi.persistenta.bibbliografie.BibliografieB;
import ro.uvt.fmi.persistenta.bibbliografie.BibliografieBPAT;
import ro.uvt.fmi.persistenta.referinte.VizibilitateBDD;
import ro.uvt.fmi.persistenta.traduceri.*;
import ro.uvt.fmi.persistenta.pseudonim.*;
@Local
@Path("/traducator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TraducatorDetailsREST {
	
	//VizibilitateBDD
	@GET
	@Path("/bddEntry/{id}")
	public VizibilitateBDD getByIdBDD(@PathParam("id") Long id);
	
	@PUT
	@Path("/bddEntry/")
	public Response insertBDD(VizibilitateBDD entry);

	@POST
	@Path("/bddEntry/")
	public Response updateBDD(VizibilitateBDD entry);

	@DELETE
	@Path("/bddEntry/{id}")
	public Response deleteBDD(@PathParam("id") Long id);


	//BibliografieBPAT
	@GET
	@Path("/biblioBPATEntry/{id}")
	public BibliografieBPAT getByIdBiblioBPAT(@PathParam("id") Long id);
	
	@PUT
	@Path("/biblioBPATEntry/")
	public Response insertBiblioBPAT(BibliografieBPAT entry);

	@POST
	@Path("/biblioBPATEntry/")
	public Response updateBiblioBPAT(BibliografieBPAT entry);

	@DELETE
	@Path("/biblioBPATEntry/{id}")
	public Response deleteBiblioBPAT(@PathParam("id") Long id);
	
	//BibliografieB
	@GET
	@Path("/biblioBEntry/{id}")
	public BibliografieB getByIdBiblioB(@PathParam("id") Long id);
	
	@PUT
	@Path("/biblioBEntry/")
	public Response insertBiblioB(BibliografieB entry);

	@POST
	@Path("/biblioBEntry/")
	public Response updateBiblioB(BibliografieB entry);

	@DELETE
	@Path("/biblioBEntry/{id}")
	public Response deleteBiblioB(@PathParam("id") Long id);
	
	//TraducereModerna
	@GET
	@Path("/tradManuscript/{id}")
	public Manuscris getByIdTradManuscript(@PathParam("id") Long id);
	
	@PUT
	@Path("/tradManuscript/")
	public Response insertManuscript(Manuscris entry);
	
	@POST
	@Path("/tradManuscript/")
	public Response updateManuscript(Manuscris entry);
	
	@DELETE
	@Path("/tradManuscript/{id}")
	public Response deleteManuscript(@PathParam("id") Long id);
	
	//Editii Vechi
	@GET
	@Path("/edVeche/{id}")
	public EditiiVechi getByIdEditiiVechi(@PathParam("id") Long id);
	
	@PUT
	@Path("/edVeche/")
	public Response insertEditiiVechi(EditiiVechi entry);
	
	@POST
	@Path("/edVeche/")
	public Response updateEditiiVechi(EditiiVechi entry);
	
	@DELETE
	@Path("/edVeche/{id}")
	public Response deleteEditiiVechi(@PathParam("id") Long id);
	
	//TraducereFaraIndicatii
	@GET
	@Path("/tradFaraIndicatii/{id}")
	public TraduceriFaraIndicatii getByIdTradFaraIndicatii(@PathParam("id") Long id);
	
	@PUT
	@Path("/tradFaraIndicatii/")
	public Response insertTradFaraIndicatii(TraduceriFaraIndicatii entry);
	
	@POST
	@Path("/tradFaraIndicatii/")
	public Response updateTradFaraIndicatii(TraduceriFaraIndicatii entry);
	
	@DELETE
	@Path("/tradFaraIndicatii/{id}")
	public Response deleteTradFaraIndicatii(@PathParam("id") Long id);
	
	//EditiiModerne
	@GET
	@Path("/tradModerna/{id}")
	public EditiiModerne getByIdEditiiModerne(@PathParam("id") Long id);
	
	@PUT
	@Path("/tradModerna/")
	public Response insertEditiiModerne(EditiiModerne entry);
	
	@POST
	@Path("/tradModerna/")
	public Response updateEditiiModerne(EditiiModerne entry);
	
	@DELETE
	@Path("/tradModerna/{id}")
	public Response deleteEditiiModerne(@PathParam("id") Long id);
	
	//Manual
	@GET
	@Path("/manual/{id}")
	public Manual getByIdManual(@PathParam("id") Long id);
	
	@PUT
	@Path("/manual/")
	public Response insertManual(Manual entry);
	
	@POST
	@Path("/manual/")
	public Response updateManual(Manual entry);
	
	@DELETE
	@Path("/manual/{id}")
	public Response deleteManual(@PathParam("id") Long id);
	
	//Dictionar
	@GET
	@Path("/dictionar/{id}")
	public Dictionar getByIdDictionar(@PathParam("id") Long id);
	
	@PUT
	@Path("/dictionar/")
	public Response insertDictionar(Dictionar entry);
	
	@POST
	@Path("/dictionar/")
	public Response updateDictionar(Dictionar entry);
	
	@DELETE
	@Path("/dictionar/{id}")
	public Response deleteDictionar(@PathParam("id") Long id);
	
}
