package test;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entidades.Comentario;
import entidades.Departamento;
import entidades.Estadoincidencia;
import entidades.GrupousuarioPK;
import entidades.Incidencia;
import entidades.Role;
import entidades.Usuario;
import entidades.Prioridad;
import services.IncidenciasService;
public class TestCrearIncidencia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("Proyecto");//pasamo el nombre de la unidad de persistencia
		EntityManager em= emf.createEntityManager();
		EntityTransaction tx= em.getTransaction();
		//CREAR INCIDENCIA
	/*	Departamento departamento =null;
		String detalleIncidencia="Ordenador roto";
		String detalleComentario="Comentario al crear incidencia";
		Estadoincidencia estado=em.find(Estadoincidencia.class, 1L);
		Date fecha= new Date();
		Prioridad prioridad= em.find(Prioridad.class, 3L);
		Usuario u =em.find(Usuario.class, "USUARIO2");
		Comentario c= new Comentario();
		Incidencia i = new Incidencia();
		i.setIdIncidencia(17L);
		i.setDepartamento(departamento);
		i.setDetalleIncidencia(detalleIncidencia);
		i.setEstadoincidencia(estado);
		i.setFechaIncidencia(fecha);
		i.setPrioridadBean(prioridad);
		i.setUsuarioBean(u);
		c.setIdcomentario(4L);
		c.setFechaComentario(fecha);
		c.setIncidencia(i);
		c.setUsuario(u);
		c.setDetallesComentario(detalleComentario);*/
		Estadoincidencia estado=new Estadoincidencia();
		String descripcion="RECHAZADA";
		Long idestado=6L;
		estado.setIdEstado(idestado);
		estado.setDescripcionEstado(descripcion);
		tx.begin();	
		//em.persist(usuario);
		//em.persist(i);
		//em.persist(c);
		em.persist(estado);
		System.out.println("finalizado con exito");
		tx.commit(); 
		em.close();
		emf.close();
	}

}
