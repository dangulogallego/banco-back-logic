package co.edu.usbcali.banco.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
class TestClienteRepository {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	long clieId = 14200L;
	
	@Test
	@DisplayName("Crear Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void aTest() {
		
		assertNotNull(clienteRepository);
		assertNotNull(tipoDocumentoRepository);
		
		Cliente cliente = clienteRepository.findById(clieId);
		assertNull(cliente, "El cliente ya existe");
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 12");
		cliente.setEmail("diego@com");
		cliente.setNombre("Diego");
		cliente.setTelefono("312");
		
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(2L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		clienteRepository.save(cliente);
	}
	
	
	@Test
	@DisplayName("Modificar Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void bTest() {
		
		assertNotNull(clienteRepository);
		assertNotNull(tipoDocumentoRepository);
		
		Cliente cliente = clienteRepository.findById(clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		cliente.setActivo("N");
		cliente.setDireccion("Calle 13");
		cliente.setEmail("diego@com");
		cliente.setNombre("Diego Angulo");
		cliente.setTelefono("312");
		
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(1L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		clienteRepository.update(cliente);
	}
	
	@Test
	@DisplayName("Eliminar Cliente")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void cTest() {
		
		assertNotNull(clienteRepository);
		
		Cliente cliente = clienteRepository.findById(clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		clienteRepository.delete(cliente);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestClienteRepository.class);
	@Test
	@DisplayName("Eliminar Cliente")
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void dTest() {
		
		assertNotNull(clienteRepository);
		

		List<Cliente> losClientes = clienteRepository.findAll();
		
		losClientes.forEach(cliente -> {
			log.info("Id: " + cliente.getClieId());
		});
	}

}
