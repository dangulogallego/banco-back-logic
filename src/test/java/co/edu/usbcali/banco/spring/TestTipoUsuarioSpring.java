package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

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

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestTipoUsuarioSpring {
	
	long tiusId = 14200L;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Test
	@DisplayName("Crear Tipo Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void aTest() {
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, tiusId);
		assertNull(tipoUsuario);
		
		tipoUsuario = new TipoUsuario();
		tipoUsuario.setActivo("Y");
		tipoUsuario.setNombre("Beta Tester");
		tipoUsuario.setTiusId(tiusId);
		
		entityManager.persist(tipoUsuario);
	}
	
	@Test
	@DisplayName("Modificar Tipo Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void bTest() {
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, tiusId);
		assertNotNull(tipoUsuario);
		
		tipoUsuario.setActivo("N");
		
		entityManager.merge(tipoUsuario);
	}
	
	@Test
	@DisplayName("Eliminar Usuario")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void cTest() {
		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, tiusId);
		assertNotNull(tipoUsuario);
		
		entityManager.remove(tipoUsuario);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestTipoUsuarioSpring.class);
	@Test
	@DisplayName("Consultar Todos Los Tipos de Usuarios")
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void dTest() {
		String JPQL = "SELECT tUsu FROM TipoUsuario tUsu";
		List<TipoUsuario> losTiposUsuarios= entityManager.createQuery(JPQL).getResultList();
		
		losTiposUsuarios.forEach(tipoUsu -> {
			log.info("Nombre: " + tipoUsu.getNombre());
			log.info("Id: " + tipoUsu.getTiusId());
		});
	}

}
