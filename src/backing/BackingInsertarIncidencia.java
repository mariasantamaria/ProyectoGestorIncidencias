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
import entidades.Usuario;
import services.ComentarioService;
import services.DepartamentosService;
import services.IncidenciasService;
import services.PrioridaddService;
import services.UsuarioService;

import java.io.Serializable;
import java.util.Date;
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
	private Incidencia i ;
	private Comentario c ;
	private String nombreusuario=usuarioEc.getRemoteUser();
	private String detalle;
	private String descripcion;
	private List<Prioridad> listadoPrioridades;
	private Long  prioridadseleccionada = 0L;
	@EJB
	private UsuarioService usuarioService;
	
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
	public Long getPrioridadseleccionada() {
		return prioridadseleccionada;
	}
	public void setPrioridadseleccionada(Long prioridadseleccionada) {
		this.prioridadseleccionada = prioridadseleccionada;
	}
	public void nuevaIncidencia() {
		Long idincidencia=incidenciaService.getId();
		Long idcomentario=comentarioService.getId();
		System.out.println("idincidencia"+idincidencia);
		System.out.println("idcomentario"+idcomentario);
		//Prioridad p=priService.buscarPrioridadById(prioridadseleccionada);
		Usuario usuario=usuarioService.getUsuarioById(nombreusuario).get(0);
		System.out.println("prioridad id"+prioridadseleccionada);
		System.out.println("detaleeee"+detalle);
		System.out.println("descripcion"+descripcion);
		try {
			  incidenciaService.insertarIncidencia(usuario,detalle,descripcion,prioridadseleccionada,idincidencia,idcomentario);
			  FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",context.getViewRoot().getLocale());
				context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.registroCreadoConExito"), archivomensajes.getString("generico.registroCreadoConExito")));
				
			 
		}catch (RollbackException e) {
			e.printStackTrace();
		} catch (Exception e) {
			String mensaje = e.getCause().getCause().getMessage();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
		}
		i = new Incidencia();
		detalle="";
		descripcion="";
		
	}
	/*public void insertarIncidencia() {
		System.out.println("--detallee-"+detalle);
		System.out.println("--descripcion"+descripcion);
		System.out.println("--prioridad-"+prioridadseleccionada);
		System.out.println("--nombre usuario"+nombreusuario);*/
		//Prioridad p = new Prioridad();
		//p= priService.buscarPrioridadById(prioridadseleccionada);
		//System.out.println("--prioridad p"+p.getIdPrioridad());
		//i = new Incidencia();
		//c= new Comentario();
		//i.setIdIncidencia(incidenciaService.getId());
		//i.setDetalleIncidencia(detalle);
		//System.out.println("--incidencia id --"+i.getIdIncidencia());
		//c.setIdcomentario(comentarioService.getId());
		//c.setDetallesComentario(descripcion);
		//System.out.println("--comentario id --"+c.getIdcomentario());
		//System.out.println("--comentario detalle --"+c.getDetallesComentario());
		
	/*	try {
			c.setDetallesComentario(descripcion);
			c.setIdcomentario(comentarioService.getId());
			c.setFechaComentario(new Date());
			c.setIncidencia(i);
			*/
			//c.setUsuario();
		//	incidenciaService.insertarIncidencia(nombreusuario);
		/*	FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",context.getViewRoot().getLocale());
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.registroCreadoConExito"), archivomensajes.getString("generico.registroCreadoConExito")));
				} catch (RollbackException e) {
					e.printStackTrace();
	
				} catch (Exception e) {
					String mensaje = e.getCause().getCause().getMessage();
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
				}
				i= new Incidencia();
				c= new Comentario();
			}*/
	
	
}
