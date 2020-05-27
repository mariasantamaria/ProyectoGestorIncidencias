package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Departamento;
import entidades.Grupousuario;
import entidades.GrupousuarioPK;
import entidades.Role;
import entidades.Usuario;
import exceptions.UsuarioException;
import services.DepartamentosService;
import services.RolesService;
import services.UsuarioService;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
@Named
@SessionScoped
public class BackingAltaUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3107444812162835812L;
	private Usuario usuario = new Usuario();
	private List<Departamento> listadoDepartamentos = null;
	private List<Role> listadoRoles = null;
	//select de la vista
	private int rolseleccionado = 1;
	private int departamentoseleccionado = 0;
	@EJB
	private UsuarioService usuarioService;
	@EJB
	private DepartamentosService depService;
	@EJB
	private RolesService rolService;
	public BackingAltaUsuario() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void ini() {
		//listado select 
		listadoRoles = rolService.getRoles();
		listadoDepartamentos = depService.getDepartamentos();
	}
	/****************getters and setters***********************/
	
	public Usuario getUsuario() {
		return usuario;
	}
	public List<Departamento> getListadoDepartamentos() {
		return listadoDepartamentos;
	}
	public void setListadoDepartamentos(List<Departamento> listadoDepartamentos) {
		this.listadoDepartamentos = listadoDepartamentos;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Role> getListadoRoles() {
		return listadoRoles;
	}
	public void setListadoRoles(List<Role> listadoRoles) {
		this.listadoRoles = listadoRoles;
	}
	
	public int getRolseleccionado() {
		return rolseleccionado;
	}
	public void setRolseleccionado(int rolseleccionado) {
		this.rolseleccionado = rolseleccionado;
	}
	public int getDepartamentoseleccionado() {
		return departamentoseleccionado;
	}
	public void setDepartamentoseleccionado(int departamentoseleccionado) {
		this.departamentoseleccionado = departamentoseleccionado;
	}
	/*****************************************************/
	public String salir() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/seguridad/identificate.jsp?faces-redirect=true";
	}
	public void altaUsuario() {
		//Grupousuario id = new Grupousuario();
		//GrupousuarioPK idPK = new GrupousuarioPK();
	try {
	Departamento departamentousuario = null;
	
	
			if (departamentoseleccionado != 0) {
			//	departamentousuario= depService.buscarDepartamentoById(departamentoseleccionado);
				System.out.println("dep select"+departamentoseleccionado);
				departamentousuario= depService.getDepartamentoById(departamentoseleccionado).get(0);
				System.out.println("departamento seleccionado"+departamentousuario.getDetalleDepartamento());
			}
			usuario.setDepartamentoBean(departamentousuario);
			usuario.setRoles(rolService.buscarRolById(rolseleccionado));
			//id.setRole(rolService.buscarRolById(rolseleccionado).get(0));
			//id.setUsuario(usuario);
			//id.setRole(rolService.buscarRolById(rolseleccionado).get(0));
			//idPK.setIdrol(rolService.buscarRolById(rolseleccionado).get(0).getIdRoles());
			//idPK.setIdusuario(usuario.getEmail());
			//id.setId(idPK);
			//usuario.setGrupousuario(id);
			usuarioService.insertarUsuario(usuario);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
					context.getViewRoot().getLocale());
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							archivomensajes.getString("generico.registroCreadoConExito"),
							archivomensajes.getString("generico.registroCreadoConExito")));
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (UsuarioException e) {
			String mensaje = e.getMessage();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
		} catch (Exception e) {
			String mensaje = e.getCause().getCause().getMessage();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
		}
		usuario = new Usuario();
		//id= new Grupousuario();
	}

}
