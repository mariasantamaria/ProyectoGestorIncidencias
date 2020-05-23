package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Prioridad;

/**
 * Session Bean implementation class PrioridadService
 */
@Stateless
@LocalBean
public class PrioridadService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public PrioridadService() {
        // TODO Auto-generated constructor stub
    }
    //listar select
    @SuppressWarnings("unchecked")
   	public List<Prioridad> getPrioridad() {
   		return em.createNamedQuery("Prioridad.findAll").getResultList();
   	}
    @SuppressWarnings("unchecked")
	public List<Prioridad> buscarPrioridadById(int id) {
		return em.createQuery("select p from Prioridad p where p.idPrioridad=:id order by p.idPrioridad").setParameter("id", id).getResultList();
	}

}
