package br.com.fiap.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.fiap.model.Vaga;
import br.com.fiap.dao.EmpresaDao;
import br.com.fiap.dao.VagaDao;
import br.com.fiap.exceptions.BadInfoException;
import br.com.fiap.exceptions.IdNotFoundException;
import br.com.fiap.factory.ConnectionFactory;

public class VagaService {
	
	private VagaDao vagaDao;
	private EmpresaDao empresaDao;
	
	public VagaService() throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionFactory.getConnection();
		vagaDao = new VagaDao(conn);
		empresaDao = new EmpresaDao(conn);
	}
	
	public void cadastrar(Vaga vaga) throws BadInfoException, SQLException {
		validar(vaga);
		vagaDao.cadastrar(vaga);
	}
	
	public void atualizar(Vaga vaga) throws BadInfoException, SQLException, IdNotFoundException {
		validar(vaga);
		vagaDao.atualizar(vaga);
	}
	
	public void remover(int id) throws SQLException, IdNotFoundException {
		vagaDao.remover(id);
	}
	
	public List<Vaga> listar() throws SQLException{
		return vagaDao.listar();
	}
	
	public List<Vaga> pesquisarPorNome(String nome) throws SQLException, IdNotFoundException{
		return vagaDao.pesquisarPorNome(nome);
	}
	
	public Vaga buscarPorId(int id) throws SQLException, IdNotFoundException {
		return vagaDao.buscarPorId(id);
	}
	
	public void validar(Vaga vaga) throws BadInfoException {
		if (vaga.getDsTitulo() == null || vaga.getDsTitulo().length() > 50) {
			throw new BadInfoException("Nome inválido, nulo ou maior que 30 caracteres!");
		}
		if(vaga.getDsVaga() == null || vaga.getDsVaga().length() > 255) {
			throw new BadInfoException("Nome inválido, nulo ou maior que 30 caracteres!");
		}
		if(vaga.getSalario() < 0) {
			throw new BadInfoException("Salário precisa ser positivo!");
		}
		if(vaga.getEmpresa().getId() < 0) {
			throw new BadInfoException("A vaga deve possuir uma empresa!");
		}
	}
}	
