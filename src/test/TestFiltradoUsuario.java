package test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Incidencia;
import entidades.Usuario;

public class TestFiltradoUsuario {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("Proyecto");
		EntityManager em=emf.createEntityManager();
		/*ArrayList<Incidencia> listado= new ArrayList<Incidencia>();
		String usuario="ADMIN";
		String tipo="1";
		Incidencia i= new Incidencia();
		i=em.find(Incidencia.class, 1L);
		System.out.println(i.getIdIncidencia());*/
		String nombreusuario="ADMIN";
		Usuario u=em.find(Usuario.class, nombreusuario);
    	Long rol =u.getRoles().get(0).getIdRoles();
    	if(rol == 1) {
    		System.out.println("tiene opcion a elminar");
    	}
    	System.out.println("el rol del admin es: "+ rol);
		em.close();
		emf.close();

	}

}
