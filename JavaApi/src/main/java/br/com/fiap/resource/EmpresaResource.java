package br.com.fiap.resource;

import java.sql.SQLException;
import java.util.List;

import br.com.fiap.exceptions.BadInfoException;
import br.com.fiap.exceptions.IdNotFoundException;
import br.com.fiap.model.Empresa;
import br.com.fiap.service.EmpresaService;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;

@Path("/empresas")
public class EmpresaResource {
	
	private EmpresaService empresaService;
	
	public EmpresaResource() throws ClassNotFoundException, SQLException {
		empresaService = new EmpresaService();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Empresa empresa, @Context UriInfo uri) throws SQLException {
		try {
			empresaService.cadastrar(empresa);
			
			UriBuilder uriBuilder = uri.getAbsolutePathBuilder();
			
			uriBuilder.path(String.valueOf(empresa.getId()));
			
			return Response.created(uriBuilder.build()).build();
		} catch (BadInfoException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@PathParam("id") int id) throws SQLException {
		try {
			return Response.ok(empresaService.buscarPorId(id)).build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Empresa> listar() throws SQLException {
		return empresaService.listar();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Empresa empresa, @PathParam("id") int id) throws ClassNotFoundException, SQLException {
		try {
			empresa.setId(id);
			empresaService.atualizar(empresa);
			return Response.ok().build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (BadInfoException e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response remover(@PathParam("id") int id) throws SQLException, IdNotFoundException {
		try {
			empresaService.remover(id);
			return Response.noContent().build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
