package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Incidencia;
import entidades.Usuario;
import services.ComentarioService;
import services.IncidenciasService;
import services.UsuarioService;
import entidades.Comentario;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class BackingEditarIncidencia implements Serializable {

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	/**
	 * 
	 */
	ExternalContext usuarioEc= FacesContext.getCurrentInstance().getExternalContext();
	private static final long serialVersionUID = -4895169041712182676L;
	private String nombreusuario=usuarioEc.getRemoteUser();
	private Incidencia incidenciaseleccionada = new Incidencia();
	private List<Comentario> listadoComentario=null;
	private String comentario;
	private Long estado;
	private Long idIncidencia;
	private Usuario usuario;
	
	@EJB
	private IncidenciasService incidenciaService;
	@EJB
	private ComentarioService comentarioService;
	@EJB
	private UsuarioService usuarioService;
	

	public BackingEditarIncidencia() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		System.out.println("idincidencia"+this.idIncidencia);
		this.incidenciaseleccionada=this.incidenciaService.getIncidenciaById(this.idIncidencia);
		System.out.println(incidenciaseleccionada.getDetalleIncidencia());
		listar();
	}
	
	public List<Comentario> listar(){
		return this.listadoComentario=this.comentarioService.listarComentarioPorIncidencia(this.idIncidencia);
	}
	
	/*****************getters and setters******************************/
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public Incidencia getIncidenciaseleccionada() {
		return incidenciaseleccionada;
	}


	public List<Comentario> getListadoComentario() {
		return listadoComentario;
	}

	public void setListadoComentario(List<Comentario> listadoComentario) {
		this.listadoComentario = listadoComentario;
	}

	public void setIncidenciaseleccionada(Incidencia incidenciaseleccionada) {
		this.incidenciaseleccionada = incidenciaseleccionada;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Long getIdIncidencia() {
		return idIncidencia;
	}


	public void setIdIncidencia(Long idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	/**********************************/
}
