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
    //listado filtrando por el tipo de incidencia y en funci�n del usuario logeado
    /**VISUALIZACION DE INCIDENCIAS POR ROLES
     * - ADMINISTRADOR 1, MANAGER 2: TODAS
     * - TECNICO 3: SU DEPARTAMENTO Y LAS CREADAS POR �L
     * - USUARIO 4: LAS CREADAS POR �L*/
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
       		System.out.println("admin o manager");
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
       	System.out.println("admin o manager");
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
    public void insertarIncidencia(String usuario, Long idPrioridad, String detalleIncidencia ) throws RollbackException   {
    	Usuario usarioIncidencia =em.find(Usuario.class, usuario);
    	//nueva
    	Estadoincidencia estadoIncidencia= em.find(Estadoincidencia.class,1L);
    	Prioridad prioridad =em.find(Prioridad.class, idPrioridad);
    	Incidencia i = new Incidencia();
    	i.setDepartamento(null);
    	i.setPrioridadBean(prioridad);
    	//ASUNTO DE LA INCIDENCIA
    	i.setDetalleIncidencia(detalleIncidencia);
    	i.setEstadoincidencia(estadoIncidencia);
    	i.setFechaIncidencia(new Date());
    	i.setUsuarioBean(usarioIncidencia);
    	//Comentario c= new Comentario();
    	try {	
			em.persist(i);
		}catch (RollbackException rbe) {
			throw rbe;
		}
    	
    }
    //insertar comentario
    
}
