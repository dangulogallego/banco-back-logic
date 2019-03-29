package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.domain.Usuario;

class TestUsuarioJPA {
	
	String usuUsuario = "d.angulo";
	BigDecimal identificacion = new BigDecimal("1115078915");
	
	@Test
	@DisplayName("Crear Usuario")
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNull(usuario);
		
		usuario = new Usuario();
		usuario.setActivo("S");
		usuario.setClave("12345");
		usuario.setIdentificacion(identificacion);
		usuario.setNombre("Diego");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 3L);
		assertNotNull(tipoUsuario);
		
		usuario.setTipoUsuario(tipoUsuario);
		usuario.setUsuUsuario(usuUsuario);
		
		entityManager.getTransaction().begin();
			entityManager.persist(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Modificar Usuario")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		
		usuario.setActivo("N");
		usuario.setClave("123456");
		usuario.setNombre("Diego Angulo");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsuario);
		
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
			entityManager.merge(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Borrar Usuario")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		
		entityManager.getTransaction().begin();
			entityManager.remove(usuario);
		entityManager.getTransaction().commit();
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestUsuarioJPA.class);
	@Test
	@DisplayName("Consultar Todos Usuarios")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		String JPQL = "SELECT usu FROM Usuario usu";
		List<Usuario> losUsuarios= entityManager.createQuery(JPQL).getResultList();
		
		losUsuarios.forEach(tipoDoc -> {
			log.info("Nombre: " + tipoDoc.getNombre());
			log.info("Identificación: " + tipoDoc.getIdentificacion());
		});
		
	}

}
