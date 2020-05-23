package test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entidades.Departamento;
import entidades.GrupousuarioPK;
import entidades.Role;
import entidades.Usuario;

public class TestUsuario {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("Proyecto");//pasamo el nombre de la unidad de persistencia
		EntityManager em= emf.createEntityManager();
		EntityTransaction tx= em.getTransaction();
		GrupousuarioPK id= new GrupousuarioPK();
		String nombre, apellido1,apellido2,email, telefono;
		Long idrol, iddepartamento;
		email="TEST";
		apellido1="TEST";
		apellido2="TEST";
		nombre="TESTTECNICO";
		idrol=3L;
		iddepartamento=1L;
		Usuario usuario= new Usuario();
		usuario.setApellido1(apellido1);
		usuario.setApellido2(apellido2);
		usuario.setEmail(email);
		usuario.setNombre(nombre);
		Departamento d= em.find(Departamento.class, iddepartamento);
		usuario.setDepartamentoBean(d);
		Role r=em.find(Role.class, idrol);
		ArrayList<Role> roles= new ArrayList<Role>();
		roles.add(r);
		usuario.setRoles(roles);
		tx.begin();	
		em.persist(usuario);
		System.out.println("finalizado con exito");
		tx.commit(); 
		em.close();
		emf.close();
	}

}
