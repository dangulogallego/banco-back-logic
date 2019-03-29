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

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestClienteSpring {
	
	long clieId = 14200L;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Test
	@DisplayName("Crear Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void aTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente);
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 12");
		cliente.setEmail("diego@com");
		cliente.setNombre("Diego");
		cliente.setTelefono("312");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 2L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.persist(cliente);
	}
	
	
	@Test
	@DisplayName("Modificar Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void bTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		cliente.setActivo("N");
		cliente.setDireccion("Calle 13");
		cliente.setEmail("diego@com");
		cliente.setNombre("Diego Angulo");
		cliente.setTelefono("312");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.merge(cliente);
	}
	
	@Test
	@DisplayName("Eliminar Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void cTest() {
		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		entityManager.remove(cliente);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestClienteSpring.class);
	@Test
	@DisplayName("Eliminar Cliente")
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void dTest() {
		
		String JPQL = "SELECT cli FROM Cliente cli";
		List<Cliente> losClientes = entityManager.createQuery(JPQL).getResultList();
		
		losClientes.forEach(cliente -> {
			log.info("Id: " + cliente.getClieId());
		});
	}

}
