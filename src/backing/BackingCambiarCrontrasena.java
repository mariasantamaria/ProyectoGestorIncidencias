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
	private String anterior;
	private String nueva;
	private String repetida;
	@EJB
	private UsuarioService usuarioService;
	
	
	/*************************metodos*******************************************/
	public void cambiarContrasena() {
		System.out.println("--nombre usuario--"+nombreusuario);
		System.out.println("--contraseña antigua--"+anterior);
		System.out.println("--contraseña nueva--"+nueva);
		System.out.println("--contraseña repetida--"+repetida);
	}
	public void contrasenaUsuario () {
		Usuario usuario=usuarioService.buscarUsuarioById(nombreusuario);
		if(nueva.equals(repetida)) {
			usuarioService.cambiarCon(nueva,usuario);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
					context.getViewRoot().getLocale());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.registroActualizado"),archivomensajes.getString("generico.registroActualizado")));
		
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
					context.getViewRoot().getLocale());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,archivomensajes.getString("generico.errorRepeticion"),archivomensajes.getString("generico.errorRepeticion")));
		
		}
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
	public String getAnterior() {
		return anterior;
	}
	public void setAnterior(String anterior) {
		this.anterior = anterior;
	}
	public String getNueva() {
		return nueva;
	}
	public void setNueva(String nueva) {
		this.nueva = nueva;
	}
	public String getRepetida() {
		return repetida;
	}
	public void setRepetida(String repetida) {
		this.repetida = repetida;
	}
	
}
