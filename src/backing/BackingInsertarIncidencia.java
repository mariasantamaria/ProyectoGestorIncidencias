package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Incidencia;
import entidades.Prioridad;
import services.ComentarioService;
import services.DepartamentosService;
import services.IncidenciasService;
import services.PrioridaddService;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class BackingInsertarIncidencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5517664588558550391L;
	ExternalContext usuarioEc= FacesContext.getCurrentInstance().getExternalContext();
	private Incidencia i;
	private Comentario c;
	private String nombreusuario=usuarioEc.getRemoteUser();
	private String detalle;
	private String descripcion;
	private List<Prioridad> listadoPrioridades = null;
	private int prioridadseleccionada = 0;
	
	@EJB
	private DepartamentosService depService;
	@EJB
	private PrioridaddService priService;
	@EJB
	private IncidenciasService incidenciaService;
	@EJB
	private ComentarioService comentarioService;

	public BackingInsertarIncidencia() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void ini() {
		listadoPrioridades = priService.getPrioridades();
	}
	/***********************************************/
	
	public List<Prioridad> getListadoPrioridades() {
		return listadoPrioridades;
	}
	public Comentario getC() {
		return c;
	}
	public void setC(Comentario c) {
		this.c = c;
	}
	public Incidencia getI() {
		return i;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setListadoPrioridades(List<Prioridad> listadoPrioridades) {
		this.listadoPrioridades = listadoPrioridades;
	}
	public int getPrioridadseleccionada() {
		return prioridadseleccionada;
	}
	public void setPrioridadseleccionada(int prioridadseleccionada) {
		this.prioridadseleccionada = prioridadseleccionada;
	}
	
	public void insertarIncidencia() {
		try {
			Prioridad p=null;
			i= new Incidencia();
			c= new Comentario();
			System.out.println("prioridad seleccionada"+prioridadseleccionada);
			p= priService.buscarPrioridadById(prioridadseleccionada);
			System.out.println("idincidencia"+i.getIdIncidencia());
			i.setIdIncidencia(incidenciaService.getId());
			i.setDetalleIncidencia(detalle);
			System.out.println("idincidencia"+i.getIdIncidencia());
			c.setIdcomentario(comentarioService.getId());
			System.out.println("idcomentario"+c.getIdcomentario());
			c.setDetallesComentario(descripcion);
			incidenciaService.insertarIncidencia(nombreusuario, p,i, c);
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
				 c= new Comentario();
			}
	
	
}
