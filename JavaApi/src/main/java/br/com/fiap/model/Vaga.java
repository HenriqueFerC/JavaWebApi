package br.com.fiap.model;

import java.time.LocalDate;

public class Vaga {
	
	private int id;
	private String dsTitulo;
	private String dsVaga;
	private double salario;
	private LocalDate dataPublicacao;
	private Empresa empresa;
	
	public Vaga() {
		super();
	}

	public Vaga(int id, String dsTitulo, String dsVaga, double salario, LocalDate dataPublicacao) {
		super();
		this.id = id;
		this.dsTitulo = dsTitulo;
		this.dsVaga = dsVaga;
		this.salario = salario;
		this.dataPublicacao = dataPublicacao;
	}
	
	public Vaga(int id, String dsTitulo, String dsVaga, double salario, LocalDate dataPublicacao, Empresa empresa) {
		super();
		this.id = id;
		this.dsTitulo = dsTitulo;
		this.dsVaga = dsVaga;
		this.salario = salario;
		this.dataPublicacao = dataPublicacao;
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDsTitulo() {
		return dsTitulo;
	}
	public void setDsTitulo(String dsTitulo) {
		this.dsTitulo = dsTitulo;
	}
	public String getDsVaga() {
		return dsVaga;
	}
	public void setDsVaga(String dsVaga) {
		this.dsVaga = dsVaga;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
}
