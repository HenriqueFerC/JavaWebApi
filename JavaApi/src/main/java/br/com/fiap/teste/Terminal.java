package br.com.fiap.teste;

import java.time.LocalDate;
import java.util.List;

import br.com.fiap.model.Empresa;
import br.com.fiap.model.Vaga;
import br.com.fiap.service.VagaService;

public class Terminal {
	public static void main(String[] args) {
		Empresa empresa = new Empresa(1, "FIAP", 500);
		try {
			VagaService service = new VagaService();
			List<Vaga> lista = service.pesquisarPorNome("USP");
			for(Vaga vaga:lista) {
				System.out.println(vaga.getId());
				System.out.println(vaga.getDsTitulo());
			}
			
		} catch (Exception e) {
			System.err.println((e.getMessage()));
		}
	}
}
