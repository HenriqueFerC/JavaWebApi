package br.com.fiap.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.fiap.dao.EmpresaDao;
import br.com.fiap.exceptions.BadInfoException;
import br.com.fiap.exceptions.IdNotFoundException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Empresa;

public class EmpresaService {
	
	private EmpresaDao empresaDao;
	
	public EmpresaService() throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionFactory.getConnection();
		empresaDao = new EmpresaDao(conn);
	}
	
	public void cadastrar(Empresa empresa) throws SQLException, BadInfoException {
		validar(empresa);
		empresaDao.cadastrar(empresa);
	}
	
	public void atualizar(Empresa empresa) throws BadInfoException, SQLException, IdNotFoundException {
		validar(empresa);
		empresaDao.atualizar(empresa);
	}
	
	public void remover(int id) throws SQLException, IdNotFoundException {
		empresaDao.remover(id);
	}
	
	public List<Empresa> listar() throws SQLException{
		return empresaDao.listar();
	}
	
	public Empresa buscarPorId(int id) throws SQLException, IdNotFoundException {
		return empresaDao.buscarPorId(id);
	}
	
	private void validar(Empresa empresa) throws BadInfoException {
		if(empresa.getNome() == null ||  empresa.getNome().length() > 100) {
			throw new BadInfoException("O nome da empresa está nulo ou maior que 100 caracteres!");
		}
		if(empresa.getId() <= 0) {
			throw new BadInfoException("O ID da empresa precisa ser positivo!");
		}
	}
}
