package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entidades.Estadoincidencia;
import entidades.Usuario;

/**
 * Session Bean implementation class EstadoService
 */
@Stateless
@LocalBean
public class EstadoService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public EstadoService() {
        // TODO Auto-generated constructor stub
    }
    public Estadoincidencia buscarEstadoPorId(Long idestado) { 	
		return em.find(Estadoincidencia.class,idestado);
    	
    }
    @SuppressWarnings("unchecked")
	public List <Estadoincidencia> listadoEstado(String nombreusuario, Long estadoactual){
    	Usuario usuariologeado=em.find(Usuario.class, nombreusuario);
    	if((usuariologeado.getGrupousuario().getRole().getIdRoles() == 1 | usuariologeado.getGrupousuario().getRole().getIdRoles() == 2 )& (estadoactual == 4)) {  		
    		if(estadoactual == 4) {
    			Query consulta = em.createQuery("select e from Estadoincidencia e where e.idEstado = :idestado ");
    	    	consulta.setParameter("idestado",5);
    	    	return consulta.getResultList();
    		}
    	}
    	if(estadoactual == 1) {
			Query consulta = em.createQuery("select e from Estadoincidencia e where e.idEstado = :idestado ");
	    	consulta.setParameter("idestado",6);
	    	return consulta.getResultList();
		}
		if(estadoactual == 2) {
			Query consulta = em.createQuery("select e from Estadoincidencia e where e.idEstado = :idestado ");
	    	consulta.setParameter("idestado",6);
	    	return consulta.getResultList();
		}
		if(estadoactual == 3) {
			Query consulta = em.createQuery("select e from Estadoincidencia e where e.idEstado = :idestado or e.idEstado= :idestado2 ");
	    	consulta.setParameter("idestado",2);
	    	consulta.setParameter("idestado2",2);
	    	return consulta.getResultList();
		}
    	return null;
    }

}//fin de la Clase EstadoService
