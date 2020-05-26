package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Incidencia;
import entidades.Usuario;
import services.ComentarioService;
import services.DepartamentosService;
import services.EstadoService;
import services.IncidenciasService;
import services.UsuarioService;
import entidades.Comentario;
import entidades.Departamento;
import entidades.Estadoincidencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


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
	private List<Departamento> listadoDepartamento=null;
	private String comentario;
	private Long idestado;
	private Long idIncidencia;
	private Long idDepartamento;
	private Usuario usuario;
	
	@EJB
	private IncidenciasService incidenciaService;
	@EJB
	private ComentarioService comentarioService;
	@EJB
	private UsuarioService usuarioService;
	@EJB
	private DepartamentosService departamentService;
	@EJB
	private EstadoService estadoService;
	
	

	public BackingEditarIncidencia() {
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		System.out.println("idincidencia"+this.idIncidencia);
		this.incidenciaseleccionada=this.incidenciaService.getIncidenciaById(this.idIncidencia);
		this.usuario=this.usuarioService.buscarUsuarioById(this.nombreusuario);
		System.out.println("----usuario----"+usuario.getNombre());
		System.out.println(incidenciaseleccionada.getDetalleIncidencia());
		listar();
		listarDepartamento();
	}
	public void prueba() {
		System.out.println("metodooo");
		//comporbar si el departamento no es nulo e insertarlo a la incidencia
		if(idDepartamento != null) {
			Departamento departamento=departamentService.buscarDepartamentoById(idDepartamento);
			incidenciaseleccionada.setDepartamento(departamento);
		}
		usuario=this.usuarioService.buscarUsuarioById(this.nombreusuario);
		Long idComentario=comentarioService.getId();
		
		Estadoincidencia estadoincidencia=estadoService.buscarEstadoPorId(idestado);
		System.out.println("estado incidencia"+estadoincidencia.getIdEstado());
	    Comentario comentarioincidencia= new Comentario();
		List <Comentario> listadoComentarioPorIncidencia=listar();
		comentarioincidencia.setDetallesComentario(comentario);
		comentarioincidencia.setFechaComentario(new Date());
		comentarioincidencia.setIdcomentario(idComentario);
		System.out.println("estado incidencia"+comentarioincidencia.getIdcomentario());
		comentarioincidencia.setIncidencia(incidenciaseleccionada);
		comentarioincidencia.setUsuario(usuario);
		try {
			comentarioService.insertarComentario(comentarioincidencia);
			listadoComentarioPorIncidencia.add(comentarioincidencia);
			incidenciaseleccionada.setComentarios(listadoComentarioPorIncidencia);
			incidenciaseleccionada.setEstadoincidencia(estadoincidencia);
			incidenciaService.actualizarIncidencia(incidenciaseleccionada);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
					context.getViewRoot().getLocale());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.registroCreadoConExito"),archivomensajes.getString("generico.registroCreadoConExito")));
		
		}catch (RollbackException e) {
			e.printStackTrace();
		} catch (Exception e) {
			String mensaje = e.getCause().getCause().getMessage();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));	
			
		}
		comentarioincidencia= new Comentario();
		comentario=null;
		
	}
	public List<Comentario> listar(){
		return this.listadoComentario=this.comentarioService.listarComentarioPorIncidencia(this.idIncidencia);
	}
	public List<Departamento> listarDepartamento(){
		return this.listadoDepartamento=this.departamentService.getDepartamentos();
	}
	/*****************getters and setters******************************/
	
	public String getNombreusuario() {
		return nombreusuario;
	}
	public List<Departamento> getListadoDepartamento() {
		return listadoDepartamento;
	}
	public void setListadoDepartamento(List<Departamento> listadoDepartamento) {
		this.listadoDepartamento = listadoDepartamento;
	}
	public Long getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public Long getIdestado() {
		return idestado;
	}
	public void setIdestado(Long idestado) {
		this.idestado = idestado;
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
