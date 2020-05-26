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
import java.util.List;
import java.util.ResourceBundle;

import entidades.Departamento;
import entidades.Incidencia;
import entidades.Prioridad;
import services.DepartamentosService;
import services.IncidenciasService;
import services.PrioridaddService;

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
	private String descripcion=null;
	private int idPrioridad=1;
	private int departamentoseleccionado = 0;
	private int prioridadseleccionada = 0;
	private List<Departamento> listadoDepartamentos = null;
	private String detalle=null;
	
	@EJB
	private IncidenciasService incidenciaService;
	@EJB
	private DepartamentosService depService;
	@EJB
	private PrioridaddService prioridadService;
	
	@PostConstruct
	public void ini() { 
		//listadoPrioridad = prioridadService.getPrioridad();
		listadoDepartamentos = depService.getDepartamentos();
		listadoPrioridad=prioridadService.getPrioridades();
	}
	/******************getter and setters****************************/
	
	
	public String getNombreusuario() {
		return nombreusuario;
	}
	
	public int getPrioridadseleccionada() {
		return prioridadseleccionada;
	}
	public void setPrioridadseleccionada(int prioridadseleccionada) {
		this.prioridadseleccionada = prioridadseleccionada;
	}
	public int getDepartamentoseleccionado() {
		return departamentoseleccionado;
	}
	public void setDepartamentoseleccionado(int departamentoseleccionado) {
		this.departamentoseleccionado = departamentoseleccionado;
	}
	public List<Departamento> getListadoDepartamentos() {
		return listadoDepartamentos;
	}
	public void setListadoDepartamentos(List<Departamento> listadoDepartamentos) {
		this.listadoDepartamentos = listadoDepartamentos;
	}
	public int getIdPrioridad() {
		return idPrioridad;
	}
	public void setIdPrioridad(int idPrioridad) {
		this.idPrioridad = idPrioridad;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
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
			Prioridad p=null;
			p= prioridadService.buscarPrioridadById(idPrioridad);
			incidenciaService.insertarIncidencia(nombreusuario, p,detalle,descripcion);
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
