package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import entidades.Comentario;
import entidades.Estadoincidencia;
import entidades.Incidencia;
import entidades.Prioridad;
import entidades.Usuario;
/**
 * Session Bean implementation class IncidenciasService
 */
@Stateless
@LocalBean
public class IncidenciasService {
	@PersistenceContext(unitName = "Proyecto")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public IncidenciasService() {
        // TODO Auto-generated constructor stub
    }
    //listado solo por tipo de incidencia
    @SuppressWarnings("unchecked")
	public List<Incidencia> listadoIncidencias(int primerResultado, int maxResultados,String tipoincidencia) {
    	Query consulta = em.createQuery("select i from Incidencia i where UPPER(i.estadoincidencia.idEstado) LIKE UPPER(:tipoincidencia) order by i.fechaIncidencia desc ");
    	consulta.setParameter("tipoincidencia",tipoincidencia);
    	consulta.setFirstResult(primerResultado);
    	consulta.setMaxResults(maxResultados);
    	List<Incidencia> listaIncidencias = consulta.getResultList();
    	return listaIncidencias;
    	
	}
    //listado filtrando por el tipo de incidencia y en función del usuario logeado
    /**VISUALIZACION DE INCIDENCIAS POR ROLES
     * - ADMINISTRADOR 1, MANAGER 2: TODAS
     * - TECNICO 3: SU DEPARTAMENTO Y LAS CREADAS POR ÉL
     * - USUARIO 4: LAS CREADAS POR ÉL*/
    @SuppressWarnings("unchecked")
   	public List<Incidencia> listadoIncidenciasPorUsuario (int primerResultado, int maxResultados,String tipoincidencia, String usuario) {
    	Usuario usuariologeado=em.find(Usuario.class, usuario);
       //TECNICO -OBTENER ROL 3
       	if (usuariologeado.getGrupousuario().getRole().getIdRoles() == 3) {
       		System.out.println("tecnico");
       		//SEGUNDO FILTRO DEPARTAMENTO
       		//Long numerodepartamento=usuariologeado.getDepartamentoBean().getIdDepartamento();
       		String departamento=usuariologeado.getDepartamentoBean().getDetalleDepartamento();
       		System.out.println("tecnico"+departamento);
       		Query consultatecnico = em.createQuery("select i " + 
           			"from Incidencia i " + 
           			"where upper(i.estadoincidencia.idEstado) like upper(:tipoincidencia) " +
           			"and upper( i.usuarioBean.email) = upper(:usuario) "+
           			"or i.departamento.detalleDepartamento = (:numero) "+
           			"order by i.fechaIncidencia desc ");
       		consultatecnico.setParameter("tipoincidencia",tipoincidencia);
       		consultatecnico.setParameter("usuario",usuario);
       		consultatecnico.setParameter("numero", departamento);
       		consultatecnico.setFirstResult(primerResultado);
       		consultatecnico.setMaxResults(maxResultados);
        	List<Incidencia> listaIncidenciasTecnico = consultatecnico.getResultList();
        	return listaIncidenciasTecnico;
       		
       	}else if (usuariologeado.getGrupousuario().getRole().getIdRoles() == 4) {
       		//filtrar por tipo y su usario
       		System.out.println("usuario"+usuario);
       		Query consultausuario = em.createQuery("select i " + 
           			"from Incidencia i " + 
           			"where upper(i.estadoincidencia.idEstado) like upper(:tipoincidencia) " +
           			"and upper( i.usuarioBean.email) = upper(:usuario) "+
           			"order by i.fechaIncidencia desc ");
       		consultausuario.setParameter("tipoincidencia",tipoincidencia);
       		consultausuario.setParameter("usuario",usuario);
       		consultausuario.setFirstResult(primerResultado);
       		consultausuario.setMaxResults(maxResultados);
        	List<Incidencia> listaIncidenciasUsuario = consultausuario.getResultList();
        	return listaIncidenciasUsuario;
       	}else {
       	 //PRIMER CASO ADMIN O MANAGER
       		System.out.println("admin o manager listado");
       		Query consulta = em.createQuery("select i " + 
           			"from Incidencia i " + 
           			"where upper(i.estadoincidencia.idEstado) like upper(:tipoincidencia) " + 
           			"order by i.fechaIncidencia desc ");
           	consulta.setParameter("tipoincidencia",tipoincidencia);
           	consulta.setFirstResult(primerResultado);
           	consulta.setMaxResults(maxResultados);
        	List<Incidencia> listaIncidencias = consulta.getResultList();
        	return listaIncidencias;
       	}  	
       	
   	}
    
    @SuppressWarnings("unchecked")
	public long getTotalPorTipo(String tipoincidencia , String usuario) {
    	Usuario usuariologeado=em.find(Usuario.class, usuario);
    	List<Incidencia> listadoTotal= new ArrayList<Incidencia>();
    	if (usuariologeado.getGrupousuario().getRole().getIdRoles() == 3) {
    		System.out.println("tecnico");
       		//SEGUNDO FILTRO DEPARTAMENTO
       		//Long numerodepartamento=usuariologeado.getDepartamentoBean().getIdDepartamento();
    		String departamento=usuariologeado.getDepartamentoBean().getDetalleDepartamento();
       		Query consultatecnicot = em.createQuery("select i " + 
           			"from Incidencia i " + 
           			"where upper(i.estadoincidencia.idEstado) like upper(:tipoincidencia) " +
           			"and upper( i.usuarioBean.email) = upper(:usuario) "+
           			"or i.departamento.detalleDepartamento= (:numero) "+
           			"order by i.fechaIncidencia desc ");
       		consultatecnicot.setParameter("tipoincidencia",tipoincidencia);
       		consultatecnicot.setParameter("usuario",usuario);
       		consultatecnicot.setParameter("numero",departamento);
        	List<Incidencia> listatotalTecnico = consultatecnicot.getResultList();
        	return listatotalTecnico.size();
       		
       	}else if (usuariologeado.getGrupousuario().getRole().getIdRoles() == 4) {
       		//filtrar por tipo y su usario
       		System.out.println("usuario");
       		Query consultausuario = em.createQuery("select i " + 
           			"from Incidencia i " + 
           			"where upper(i.estadoincidencia.idEstado) like upper(:tipoincidencia) " +
           			"and upper( i.usuarioBean.email) = upper(:usuario) "+
           			"order by i.fechaIncidencia desc ");
       		consultausuario.setParameter("tipoincidencia",tipoincidencia);
       		consultausuario.setParameter("usuario",usuario);
        	List<Incidencia> listatotalusuarios = consultausuario.getResultList();
        	return listatotalusuarios.size();
       	}else {
       	System.out.println("admin o manager paginado");
       	 //PRIMER CASO ADMIN O MANAGER
       		Query consulta = em.createQuery("select i " + 
           			"from Incidencia i " + 
           			"where upper(i.estadoincidencia.idEstado) like upper(:tipoincidencia) " + 
           			"order by i.fechaIncidencia desc ");
           	consulta.setParameter("tipoincidencia",tipoincidencia);
           	listadoTotal = consulta.getResultList();
        	return listadoTotal.size();
       	}  	
    	
	}
    //insertar incidencia
    /*ListComentarios
     * Departamento- 
     * detaleincidencia-String
     * estadoIncidencia-nueva-1
     * fechaIncidencia-Date
     * idIncidencia
     * prioridad
     * departamento null -lo asigna el manager
     * usuario que realia la incidencia*/
    public void insertarIncidencia(String usuario, Prioridad p, Incidencia nuevaincidencia, Comentario nuevocomentario ) throws RollbackException   {
    	Usuario usarioIncidencia =em.find(Usuario.class, usuario);
    	//nueva
    	Estadoincidencia estadoIncidencia= em.find(Estadoincidencia.class,1L);
    	System.out.println("id:"+nuevaincidencia.getIdIncidencia());
    	nuevaincidencia.setDepartamento(null);
    	nuevaincidencia.setPrioridadBean(p);
    	//ASUNTO DE LA INCIDENCIA
    	nuevaincidencia.setEstadoincidencia(estadoIncidencia);
    	nuevaincidencia.setFechaIncidencia(new Date());
    	nuevaincidencia.setUsuarioBean(usarioIncidencia);
    	//if(c.getDetallesComentario() != null) {
   
    	nuevocomentario.setFechaComentario(new Date());
    	nuevocomentario.setIncidencia(nuevaincidencia);
    	nuevocomentario.setUsuario(usarioIncidencia);
    	nuevaincidencia.addComentario(nuevocomentario);
    	
    	//}
    	//Comentario c= new Comentario();
    	try {	
			em.persist(nuevaincidencia);
			em.persist(nuevocomentario);
			List<Comentario> listacomentarioscomentarios=nuevaincidencia.getComentarios();
			listacomentarioscomentarios.add(nuevocomentario);
			nuevaincidencia.setComentarios(listacomentarioscomentarios);
    		em.persist(nuevaincidencia);
		}catch (RollbackException rbe) {
			throw rbe;
		}
    	
    }
    //actualizar incidencia al editar
    public Incidencia actualizarIncidencia (Incidencia i) throws EJBException {
    	try {
			return  em.merge(i);	
		} catch (EJBException e) {
			// TODO: handle exception
			throw e;
		}
    }
    public Long getId() {
    	Query consulta = em.createQuery("select max(i.idIncidencia) from Incidencia i ");
    	Long max = (Long) consulta.getSingleResult();
    	System.out.println("ultimo id incidencia:"+max);
    	return max+1;
    }
    public Incidencia getIncidenciaById(long idincidencia) {
		return em.find(Incidencia.class, idincidencia);
	}
    public long getTotalFiltro(String tipo) {
    	Query consulta = em.createQuery("SELECT count(inc) FROM Incidencia inc where UPPER(inc.estadoincidencia.idEstado) LIKE UPPER(:tipo)");
    	consulta.setParameter("tipo",tipo);
    	return (Long) consulta.getSingleResult(); 	 	
	}
    public long getTotalFiltroEliminar(String cerrada, String rechazada) {
    	Query consulta = em.createQuery("SELECT count(inc) FROM Incidencia inc where UPPER(inc.estadoincidencia.idEstado) LIKE UPPER(:rechazada) or UPPER(inc.estadoincidencia.idEstado) LIKE UPPER(:cerrada)");
    	consulta.setParameter("rechazada",rechazada);
    	consulta.setParameter("cerrada",cerrada);
    	return (Long) consulta.getSingleResult(); 	 	
	}
    @SuppressWarnings("unchecked")
   	public List<Incidencia> listadoIncidenciasEliminar(int primerResultado, int maxResultados,String cerrada, String rechazada) {
       	Query consulta = em.createQuery("select i from Incidencia i where UPPER(i.estadoincidencia.idEstado) LIKE UPPER(:rechazada) or UPPER(i.estadoincidencia.idEstado) LIKE UPPER(:cerrada) order by i.fechaIncidencia desc ");
       	consulta.setParameter("rechazada",rechazada);
    	consulta.setParameter("cerrada",cerrada);
       	consulta.setFirstResult(primerResultado);
       	consulta.setMaxResults(maxResultados);
       	List<Incidencia> listaIncidencias = consulta.getResultList();
       	return listaIncidencias;
       	
   	}
    //eliminar
    public void eliminarIncidencia(Long idincidencia) throws RollbackException {
    	Incidencia incidenciaEliminar =em.find(Incidencia.class,idincidencia);
    	try {
			em.remove(incidenciaEliminar);
		} catch (RollbackException e) {
			throw e;
		}
    }
}
