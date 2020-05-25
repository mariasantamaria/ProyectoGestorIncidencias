package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entidades.Prioridad;
/**
 * Session Bean implementation class PrioridaddService
 */
@Stateless
@LocalBean
public class PrioridaddService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public PrioridaddService() {
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
	public List<Prioridad> getPrioridades(){
    	return em.createNamedQuery("Prioridad.findAll").getResultList();
    }
    public Prioridad buscarPrioridadById(int id) {
		return (Prioridad) em.createQuery("select p from Prioridad p where p.idPrioridad=:id order by p.idPrioridad").setParameter("id", id).getSingleResult();
	}
    

}
