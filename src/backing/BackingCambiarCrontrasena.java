package backing;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entidades.Usuario;
import services.UsuarioService;

import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class BackingCambiarCrontrasena implements Serializable {

	/**
	 * 
	 */
	ExternalContext usuarioEc= FacesContext.getCurrentInstance().getExternalContext();
	private static final long serialVersionUID = -3880944066814470620L;
	private String nombreusuario=usuarioEc.getRemoteUser();
	
	private String nueva;
	@EJB
	private UsuarioService usuarioService;
	
	
	/*************************metodos*******************************************/
	public void cambiarContrasena() {
		System.out.println("--nombre usuario--"+nombreusuario);
		System.out.println("--contraseña nueva--"+nueva);
	}
	public void contrasenaUsuario () {
		Usuario usuario=usuarioService.buscarUsuarioById(nombreusuario);
			usuarioService.cambiarCon(nueva,usuario);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
					context.getViewRoot().getLocale());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.registroActualizado"),archivomensajes.getString("generico.registroActualizado")));
		
	}
	/******************getters and setters********************************/
	

	public BackingCambiarCrontrasena() {
		// TODO Auto-generated constructor stub
	}
	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public void setNueva(String nueva) {
		this.nueva = nueva;
	}
	
}
