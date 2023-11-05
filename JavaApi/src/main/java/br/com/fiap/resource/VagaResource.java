package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.model.Vaga;

import br.com.fiap.exceptions.BadInfoException;
import br.com.fiap.exceptions.IdNotFoundException;

import br.com.fiap.service.VagaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriBuilderException;
import jakarta.ws.rs.core.UriInfo;

@Path("/vagas")
public class VagaResource {
	
	private VagaService vagaService;

	public VagaResource() throws ClassNotFoundException, SQLException {
		vagaService = new VagaService();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Vaga vaga, @Context UriInfo uri) throws IllegalArgumentException, UriBuilderException, SQLException {
		try {
			vagaService.cadastrar(vaga);
			
			UriBuilder uriBuilder = uri.getAbsolutePathBuilder();
			
			uriBuilder.path(String.valueOf(vaga.getId()));
			
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
			return Response.ok(vagaService.buscarPorId(id)).build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vaga> listar() throws SQLException {
		return vagaService.listar();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Vaga vaga, @PathParam("id") int id) throws SQLException {
		try {
			vaga.setId(id);
			vagaService.atualizar(vaga);
		return Response.ok().build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (BadInfoException e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}	
	}
	
	@DELETE
	@Path("/{id}")
	public Response remover(@PathParam("id") int id) throws SQLException {
		try {
			vagaService.remover(id);
			return Response.noContent().build();
		} catch (IdNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	//@GET
	//@Path("/query")
	//@Produces(MediaType.APPLICATION_JSON)
	public List<Vaga> pesquisarPorNome(@QueryParam("nome") String nome) throws SQLException, IdNotFoundException{
		return vagaService.pesquisarPorNome(nome);
	}
}
