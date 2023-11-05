package br.com.fiap.model;

public class Empresa {
		
	private int id;
	private String nome;
	private int numeroFuncionario;
	
	public Empresa() {
		super();
	}

	public Empresa(int id, String nome, int numeroFuncionario) {
		super();
		this.id = id;
		this.nome = nome;
		this.numeroFuncionario = numeroFuncionario;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getNumeroFuncionario() {
		return numeroFuncionario;
	}
	public void setNumeroFuncionario(int numeroFuncionario) {
		this.numeroFuncionario = numeroFuncionario;
	}
}
