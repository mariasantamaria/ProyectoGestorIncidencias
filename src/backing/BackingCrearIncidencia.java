package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entidades.Departamento;
import entidades.Incidencia;
import entidades.Prioridad;
import entidades.Usuario;
import exceptions.UsuarioException;
import services.DepartamentosService;
import services.IncidenciasService;
import services.PrioridadService;
import services.RolesService;

@Named
@SessionScoped
public class BackingCrearIncidencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5763876649862282855L;

	public BackingCrearIncidencia() {
		// TODO Auto-generated constructor stub
	}
	ExternalContext usuarioEc= FacesContext.getCurrentInstance().getExternalContext();
	private String nombreusuario=usuarioEc.getRemoteUser();
	private List<Prioridad> listadoPrioridad=null;
	private List<Departamento> listadoDepartamento=null;
	private Incidencia i = new Incidencia();
	private String descripcion;
	private Long idPrioridad=1L;
	@EJB
	private DepartamentosService depService;
	@EJB
	private PrioridadService prioridadService;
	@EJB
	private IncidenciasService incidenciaService;
	@PostConstruct
	public void ini() {
		//listado select 
		listadoPrioridad = prioridadService.getPrioridad();
		listadoDepartamento = depService.getDepartamentos();
	}
	/******************getter and setters****************************/

	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public List<Prioridad> getListadoPrioridad() {
		return listadoPrioridad;
	}
	public void setListadoPrioridad(List<Prioridad> listadoPrioridad) {
		this.listadoPrioridad = listadoPrioridad;
	}
	public List<Departamento> getListadoDepartamento() {
		return listadoDepartamento;
	}
	public void setListadoDepartamento(List<Departamento> listadoDepartamento) {
		this.listadoDepartamento = listadoDepartamento;
	}
	public Incidencia getI() {
		return i;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/****************************************************************/
	/*****realizar una incidencia********/
	public void insertarIncidencia() {
		try {
			incidenciaService.insertarIncidencia(nombreusuario, idPrioridad, descripcion);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",context.getViewRoot().getLocale());
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.registroCreadoConExito"), archivomensajes.getString("generico.registroCreadoConExito")));
				} catch (RollbackException e) {
					e.printStackTrace();
	
				} catch (Exception e) {
					String mensaje = e.getCause().getCause().getMessage();
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
				}
				i = new Incidencia();
			}

}
