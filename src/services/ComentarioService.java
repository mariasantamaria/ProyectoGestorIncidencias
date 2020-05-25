package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import entidades.Comentario;

/**
 * Session Bean implementation class ComentarioService
 */
@Stateless
@LocalBean
public class ComentarioService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public ComentarioService() {
        // TODO Auto-generated constructor stub
    }
    public void insertarComentario (Comentario c) throws RollbackException {
    	try {
    		em.persist(c);
    		
    	}catch(RollbackException rbe) {
    		throw rbe;
    	}
    }
    public Long getId() {
    	Query consulta = em.createQuery("select max(c.idcomentario) from Comentario c");
    	Long max = (Long) consulta.getSingleResult();
    	System.out.println("ultimo id comentario:"+max);
    	return max+1;
    }
    @SuppressWarnings("unchecked")
	public List<Comentario> listarComentarioPorIncidencia(Long idincidencia){
    	Query consulta = em.createQuery("select c from Comentario c where c.incidencia.idIncidencia = :idincidencia order by c.fechaComentario");
    	consulta.setParameter("idincidencia", idincidencia);	
    	return consulta.getResultList();
    }
}
