package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.model.Empresa;
import br.com.fiap.exceptions.IdNotFoundException;

public class EmpresaDao {
	
	public Connection conn;

	public EmpresaDao(Connection conn) {
		this.conn = conn;
	}

	public void cadastrar(Empresa empresa) throws SQLException{
		PreparedStatement stm = conn.prepareStatement("INSERT INTO TDSS_TB_EMPRESA (cd_empresa, nm_empresa, nr_funcionario) values (?, ?, ?)");
		stm.setInt(1, empresa.getId());
		stm.setString(2, empresa.getNome());
		stm.setInt(3, empresa.getNumeroFuncionario());
		
		stm.executeUpdate();
	}
	
	public Empresa buscarPorId(int id) throws SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("SELECT * FROM TDSS_TB_EMPRESA WHERE CD_EMPRESA = ?");
		stm.setInt(1, id);
		ResultSet resultado = stm.executeQuery();
		
		if(!resultado.next()) {
			throw new IdNotFoundException("Código da empresa não encontrado!");
		}
		
		Empresa empresa = parse(resultado);
		
		return empresa;
	}
	
	public List<Empresa> listar() throws SQLException{
		PreparedStatement stm = conn.prepareStatement("SELECT * FROM TDSS_TB_EMPRESA");
		ResultSet resultado = stm.executeQuery();
		
		List<Empresa> lista = new ArrayList<>();
		
		while(resultado.next()){
			Empresa empresa = parse(resultado);
			lista.add(empresa);
		}
		return lista;
	}
	
	public void atualizar(Empresa empresa) throws SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("UPDATE TDSS_TB_EMPRESA SET NM_EMPRESA = ?, NR_FUNCIONARIO = ? WHERE CD_EMPRESA = ?");
		stm.setString(1, empresa.getNome());
		stm.setInt(2, empresa.getNumeroFuncionario());
		stm.setInt(3, empresa.getId());
		
		int linha = stm.executeUpdate();
		
		if(linha == 0) {
			throw new IdNotFoundException("Empresa não encontrada!");
		}
	}
	
	public void remover(int id) throws SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM TDSS_TB_EMPRESA WHERE CD_EMPRESA = ?");
		stm.setInt(1, id);
		
		int linha = stm.executeUpdate();
		
		if(linha == 0) {
			throw new IdNotFoundException("ID de empresa não encontrado!");
		}
	}
	
	
	
	private Empresa parse(ResultSet resultado) throws SQLException {
		int codigo = resultado.getInt("cd_empresa");
		String nome = resultado.getString("nm_empresa");
		int numeroFuncionario = resultado.getInt("nr_funcionario");
		
		Empresa empresa = new Empresa(codigo, nome, numeroFuncionario);
		return empresa;
	}
}
