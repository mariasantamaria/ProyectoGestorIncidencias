package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Usuario;
import services.UsuarioService;

import java.io.Serializable;

@Named
@SessionScoped
public class BackingInformacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979248911793517688L;
	ExternalContext usuarioEc= FacesContext.getCurrentInstance().getExternalContext();
	private String nombreusuario=usuarioEc.getRemoteUser();
	private Usuario usuario;
	@EJB
	private UsuarioService usuarioService;
	/**********************getters and setters **********************/
	@PostConstruct
	public void init() {
		this.usuario=this.usuarioService.buscarUsuarioById(this.nombreusuario);
		System.out.println("----usuario----"+usuario.getNombre());
	}
	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getNombreusuario() {
		return nombreusuario;
	}


	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}


	public BackingInformacion() {
		// TODO Auto-generated constructor stub
	}

}
