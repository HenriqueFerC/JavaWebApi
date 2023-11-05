package br.com.fiap.teste;

import java.sql.Connection;
import java.time.LocalDate;

import br.com.fiap.model.Empresa;
import br.com.fiap.model.Vaga;
import br.com.fiap.dao.EmpresaDao;
import br.com.fiap.dao.VagaDao;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.service.VagaService;

public class TerminalEmpresa {
	public static void main(String[] args) {
		Empresa empresa = new Empresa(1, "FIAP", 500);
		Connection conn = null;
		try {
			conn = ConnectionFactory.getConnection();
			EmpresaDao dao = new EmpresaDao(conn);
			//VagaService service = new VagaService();
			//service.cadastrar(vaga);
			dao.cadastrar(empresa);
			System.out.println("Gravado!");
		} catch (Exception e) {
			System.err.println((e.getMessage()));
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
