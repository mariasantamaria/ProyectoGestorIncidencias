package entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the INCIDENCIAS database table.
 * @NamedQuery(name="Autor.findByName", query="select a from Autor a where UPPER(a.nombre) LIKE UPPER(:nombre)"),

 */

@Entity
@Table(name="INCIDENCIAS")

@NamedQueries({
@NamedQuery(name="Incidencia.findAll", query="SELECT i FROM Incidencia i"),

@NamedQuery(name="Incidencia.findByTipo", query="SELECT inc FROM Incidencia inc where UPPER(inc.estadoincidencia.idEstado) LIKE UPPER(:tipo)"),

})
public class Incidencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idIncidencia;

	@Column(name="DETALLE")
	private String detalleIncidencia;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA")
	private Date fechaIncidencia;

	//bi-directional many-to-one association to Comentario
	@OneToMany(mappedBy="incidencia", cascade={CascadeType.ALL})
	private List<Comentario> comentarios;

	//bi-directional many-to-one association to Departamento
	@ManyToOne
	@JoinColumn(name="DEPARTAMENTOASIGNADO")
	private Departamento departamento;

	//bi-directional many-to-one association to Estadoincidencia
	@ManyToOne
	@JoinColumn(name="ESTADO")
	private Estadoincidencia estadoincidencia;

	//bi-directional many-to-one association to Prioridad
	@ManyToOne
	@JoinColumn(name="PRIORIDAD")
	private Prioridad prioridadBean;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USUARIO")
	private Usuario usuarioBean;

	public Incidencia() {
	}

	public Long getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(Long idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getDetalleIncidencia() {
		return this.detalleIncidencia;
	}

	public void setDetalleIncidencia(String detalleIncidencia) {
		this.detalleIncidencia = detalleIncidencia;
	}

	public Date getFechaIncidencia() {
		return this.fechaIncidencia;
	}

	public void setFechaIncidencia(Date fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}

	public List<Comentario> getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Comentario addComentario(Comentario comentario) {
		getComentarios().add(comentario);
		comentario.setIncidencia(this);

		return comentario;
	}

	public Comentario removeComentario(Comentario comentario) {
		getComentarios().remove(comentario);
		comentario.setIncidencia(null);

		return comentario;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Estadoincidencia getEstadoincidencia() {
		return this.estadoincidencia;
	}

	public void setEstadoincidencia(Estadoincidencia estadoincidencia) {
		this.estadoincidencia = estadoincidencia;
	}

	public Prioridad getPrioridadBean() {
		return this.prioridadBean;
	}

	public void setPrioridadBean(Prioridad prioridadBean) {
		this.prioridadBean = prioridadBean;
	}

	public Usuario getUsuarioBean() {
		return this.usuarioBean;
	}

	public void setUsuarioBean(Usuario usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

}