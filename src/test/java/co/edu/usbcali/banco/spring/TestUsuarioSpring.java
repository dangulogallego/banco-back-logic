package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.domain.Usuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestUsuarioSpring {
	
	String usuUsuario = "d.angulo";
	BigDecimal identificacion = new BigDecimal("1115078915");
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Test
	@DisplayName("Crear Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void aTest() {
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
		
		entityManager.persist(usuario);
	}
	
	@Test
	@DisplayName("Modificar Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void bTest() {
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		
		usuario.setActivo("N");
		usuario.setClave("123456");
		usuario.setNombre("Diego Angulo");
		
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsuario);
		
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.merge(usuario);
	}
	
	@Test
	@DisplayName("Eliminar Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void cTest() {
		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El usuario no existe");
		
		entityManager.remove(usuario);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestUsuarioSpring.class);
	@Test
	@DisplayName("Consultar Todos Los Usuarios")
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void dTest() {
		String JPQL = "SELECT usu FROM Usuario usu";
		List<Usuario> losUsuarios= entityManager.createQuery(JPQL).getResultList();
		
		losUsuarios.forEach(tipoDoc -> {
			log.info("Nombre: " + tipoDoc.getNombre());
			log.info("Identificación: " + tipoDoc.getIdentificacion());
		});
	}

}
