package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * Classe que mapeia a entidade Questao.
 * 
 * @author Denys
 */
@Entity
public class Questao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idQuestao")
	private int id;

	private boolean blMultiplaEscolha;

	private byte[] bnImagem;

	private String dsEnunciado;

	private int idUsuario;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="QuestaoEtiqueta"
		, joinColumns={
			@JoinColumn(name="idQuestao")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idEtiqueta")
			}
		)
	private List<Etiqueta> etiquetas;

	public Questao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getBlMultiplaEscolha() {
		return this.blMultiplaEscolha;
	}

	public void setBlMultiplaEscolha(boolean blMultiplaEscolha) {
		this.blMultiplaEscolha = blMultiplaEscolha;
	}

	public byte[] getBnImagem() {
		return this.bnImagem;
	}

	public void setBnImagem(byte[] bnImagem) {
		this.bnImagem = bnImagem;
	}

	public String getDsEnunciado() {
		return this.dsEnunciado;
	}

	public void setDsEnunciado(String dsEnunciado) {
		this.dsEnunciado = dsEnunciado;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<Etiqueta> getEtiquetas() {
		return this.etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questao other = (Questao) obj;
		if (id != other.id)
			return false;
		return true;
	}
}