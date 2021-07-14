package br.com.meeting.model;

public class Token {

	public String sub;
    public String nomeCompleto;
    public String username;
    public String email;
    public String localTrabalho;
    public int nome;
    public String perfil;
    public int iat;
    public int exp;
    
    
	/**
	 * 
	 */
	public Token() {
	}
	
	
	/**
	 * @param sub
	 * @param nomeCompleto
	 * @param username
	 * @param email
	 * @param localTrabalho
	 * @param nome
	 * @param perfil
	 * @param iat
	 * @param exp
	 */
	public Token(String sub, String nomeCompleto, String username, String email, String localTrabalho, int nome,
			String perfil, int iat, int exp) {
		super();
		this.sub = sub;
		this.nomeCompleto = nomeCompleto;
		this.username = username;
		this.email = email;
		this.localTrabalho = localTrabalho;
		this.nome = nome;
		this.perfil = perfil;
		this.iat = iat;
		this.exp = exp;
	}


	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocalTrabalho() {
		return localTrabalho;
	}
	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	public int getNome() {
		return nome;
	}
	public void setNome(int nome) {
		this.nome = nome;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public int getIat() {
		return iat;
	}
	public void setIat(int iat) {
		this.iat = iat;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	
	
	
}
