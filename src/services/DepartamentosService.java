package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Departamento;

/**
 * Session Bean implementation class DepartamentosService
 */
@Stateless
@LocalBean
public class DepartamentosService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public DepartamentosService() {
        // TODO Auto-generated constructor stub
    }
    //nullpointer
    public Departamento buscarDepartamentoById(Long id) {
    	Departamento d= em.find(Departamento.class, id);
    	return d;
    }
    @SuppressWarnings("unchecked")
	public List<Departamento> getDepartamentoById(int id) {
		return em.createQuery("select dep from Departamento dep where dep.idDepartamento=:id order by dep.idDepartamento").setParameter("id", id).getResultList();
	}
    
    //listar departamentos select
	@SuppressWarnings("unchecked")
	public List<Departamento> getDepartamentos() {
		return em.createNamedQuery("Departamento.findAll").getResultList();
	}

}
