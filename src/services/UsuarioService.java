package services;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;

import entidades.Grupousuario;
import entidades.GrupousuarioPK;
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
    	//GrupousuarioPK id=usuarioDarAlta.getGrupousuario().getId();
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
    @SuppressWarnings("unchecked")
  	public List<Usuario> getUsuarioById(String email) {
      	return em.createQuery("Select u from Usuario u where u.email=:email").setParameter("email", email).getResultList();
      }
   	public Usuario buscarUsuarioById(String email) {
   		return em.find(Usuario.class, email);
   	}
    public EntityManager getEntityManager() {
		return em;
	}
    public void cambiarCon(String nuevacon, Usuario usuario) {
    	//comporbar que el usuario existe
    	//em.find(Usuario.class, usuario.getEmail());
    	usuario.setPassword(nuevacon);
    	try {
    		em.merge(usuario);
    	}catch(RollbackException rbe) {
    		throw rbe;
    	}
   }
    
    
}
