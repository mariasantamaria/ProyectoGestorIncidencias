package services;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;

import entidades.Usuario;
import exceptions.UsuarioException;

/**
 * Session Bean implementation class UsuarioService
 */
@Stateless
@LocalBean
public class UsuarioService {

	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    public UsuarioService() {
        // TODO Auto-generated constructor stub
    }
    
    public void insertarUsuario(Usuario usuario) throws RollbackException, UsuarioException{
    	Usuario usuarioDarAlta= em.find(Usuario.class, usuario.getEmail());
    	//comprobar que no este en la base de datos
    	if(usuarioDarAlta != null) {
    		throw new UsuarioException("Error. El usuario ya existe.");
    	}
    	//si se registra un tecnico debe insertarse un departamento, no puede quedar vacío
    	if (usuario.getRoles().get(0).getIdRoles() == 3  && usuario.getDepartamentoBean() == null) {
    		throw new UsuarioException("Debe asignar un departamento");
    	}
    	try {
    		em.persist(usuario);
		}catch (RollbackException rbe) {
			throw rbe;
		}
    }
    public EntityManager getEntityManager() {
		return em;
	}
    
}
