package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Role;

/**
 * Session Bean implementation class RolesService
 */
@Stateless
@LocalBean
public class RolesService {

	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    public RolesService() {
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		return em.createNamedQuery("Role.findAll").getResultList();
	}
    @SuppressWarnings("unchecked")
	public List<Role> buscarRolById(int id) {
		return em.createQuery("select r from Role r where r.idRoles=:id order by r.idRoles").setParameter("id", id)
				.getResultList();
	}
}
