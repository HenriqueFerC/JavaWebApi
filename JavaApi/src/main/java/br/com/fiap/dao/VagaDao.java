package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.com.fiap.model.Empresa;
import br.com.fiap.model.Vaga;
import br.com.fiap.exceptions.IdNotFoundException;

public class VagaDao {
	
	private Connection conn;

	public VagaDao(Connection conn) {
		this.conn = conn;
	}
	
	public void cadastrar(Vaga vaga) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("INSERT INTO TDSS_TB_VAGA (cd_vaga, ds_titulo, ds_vaga, vl_salario, dt_publicacao, cd_empresa) values(?, ?, ?, ?, ?, ?)");
		stm.setInt(1, vaga.getId());
		stm.setString(2, vaga.getDsTitulo());
		stm.setString(3, vaga.getDsVaga());
		stm.setDouble(4, vaga.getSalario());
		vaga.setDataPublicacao(LocalDate.now());
		stm.setDate(5, Date.valueOf(vaga.getDataPublicacao()));
		stm.setInt(6, vaga.getEmpresa().getId());
		
		stm.executeUpdate();
	}
	
	public Vaga buscarPorId(int id) throws SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("SELECT * FROM TDSS_TB_VAGA, TDSS_TB_EMPRESA WHERE CD_VAGA = ?");
		stm.setInt(1, id);
		ResultSet resultado = stm.executeQuery();

		
		if(!resultado.next()) {
			throw new IdNotFoundException("ID da vaga não encontrado!");
		}
		Vaga vaga = parse(resultado);
		
		return vaga;
	}
	
	public List<Vaga> pesquisarPorNome(String nome) throws SQLException, IdNotFoundException, IllegalArgumentException {
		PreparedStatement stm = conn.prepareStatement("SELECT V.* FROM TDSS_TB_VAGA V JOIN TDSS_TB_EMPRESA E ON V.cd_empresa = E.cd_empresa WHERE UPPER(E.NM_EMPRESA) LIKE ?");
		stm.setString(1, "%"+nome.toUpperCase()+"%");
		ResultSet resultado = stm.executeQuery();
		
		if(!resultado.next()) {
			throw new IdNotFoundException("Empresa não encontrada!");
		}
		
		List<Vaga> lista = new ArrayList<>();
		while (resultado.next()) {
			Vaga vaga = parse(resultado);
			lista.add(vaga);
		}
		return lista;
	}
	
	public List<Vaga> listar() throws SQLException{
		PreparedStatement stm = conn.prepareStatement("SELECT * FROM TDSS_TB_VAGA, TDSS_TB_EMPRESA");
		ResultSet resultado = stm.executeQuery();
		
		List<Vaga> lista = new ArrayList<>();
		
		while (resultado.next()) {
			Vaga vaga = parse(resultado);
			lista.add(vaga);
		}
		
		return lista;
	}
	
	public void atualizar(Vaga vaga) throws SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("UPDATE TDSS_TB_VAGA SET ds_titulo = ?, ds_vaga = ?, vl_salario = ?, dt_publicacao = ? WHERE CD_VAGA = ?");
		stm.setString(1, vaga.getDsTitulo());
		stm.setString(2, vaga.getDsVaga());
		stm.setDouble(3, vaga.getSalario());
		vaga.setDataPublicacao(LocalDate.now());
		stm.setDate(4, Date.valueOf(vaga.getDataPublicacao()));
		stm.setInt(5, vaga.getId());
		
		int linha = stm.executeUpdate();
		
		if(linha == 0) {
			throw new IdNotFoundException("Vaga não encontrada!");
		}
	}
	
	public void remover(int codigo) throws SQLException, IdNotFoundException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM TDSS_TB_VAGA WHERE CD_VAGA = ?");
		stm.setInt(1, codigo);
		
		int linha = stm.executeUpdate();
		
		if(linha == 0) {
			throw new IdNotFoundException("ID da vaga não encontrado!");
		}
	}
	
	
	private Vaga parse(ResultSet resultado) throws SQLException {
		int codigo = resultado.getInt("CD_VAGA");
		String dsTitulo = resultado.getString("DS_TITULO");
		String dsVaga = resultado.getString("DS_VAGA");
		Double salario = resultado.getDouble("VL_SALARIO");
		LocalDate dataPublicacao = resultado.getDate("DT_PUBLICACAO").toLocalDate();
		int codigoEmpresa = resultado.getInt("CD_EMPRESA");
		String nome = resultado.getString("NM_EMPRESA");
		int numeroFuncionario = resultado.getInt("NR_FUNCIONARIO");
		
		Empresa empresa = new Empresa();
		empresa.setId(codigoEmpresa);
		empresa.setNome(nome);
		empresa.setNumeroFuncionario(numeroFuncionario);
		
		
		
		Vaga vaga = new Vaga(codigo, dsTitulo, dsVaga, salario, dataPublicacao);
		
		vaga.setEmpresa(empresa);
		

		
		return vaga;
	}
}
