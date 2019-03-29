package co.edu.usbcali.banco.service;

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
class TestClienteService {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	long clieId = 14200L;
	
	@Test
	@DisplayName("Crear Cliente")
	void aTest() throws Exception {
		
		assertNotNull(clienteService);
		assertNotNull(tipoDocumentoService);
		
		Cliente cliente = clienteService.findById(clieId);
		assertNull(cliente, "El cliente ya existe");
		
		cliente = new Cliente();
		cliente.setActivo("S");
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 12");
		cliente.setEmail("diego@com");
		cliente.setNombre("Diego");
		cliente.setTelefono("31244");
		
		TipoDocumento tipoDocumento = tipoDocumentoService.findById(2L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		clienteService.save(cliente);
	}
	
	
	@Test
	@DisplayName("Modificar Cliente")
	void bTest() throws Exception {
		
		assertNotNull(clienteService);
		assertNotNull(tipoDocumentoService);
		
		Cliente cliente = clienteService.findById(clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		cliente.setActivo("N");
		cliente.setDireccion("Calle 13");
		cliente.setEmail("diego@com");
		cliente.setNombre("Diego Angulo");
		cliente.setTelefono("312999");
		
		TipoDocumento tipoDocumento = tipoDocumentoService.findById(1L);
		assertNotNull(tipoDocumento);
		
		cliente.setTipoDocumento(tipoDocumento);
		
		clienteService.update(cliente);
	}
	
	@Test
	@DisplayName("Eliminar Cliente")
	void cTest() throws Exception {
		
		assertNotNull(clienteService);
		
		Cliente cliente = clienteService.findById(clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		clienteService.delete(cliente);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestClienteService.class);
	@Test
	@DisplayName("Eliminar Cliente")
	void dTest() {
		
		assertNotNull(clienteService);
		

		List<Cliente> losClientes = clienteService.findAll();
		
		losClientes.forEach(cliente -> {
			log.info("Id: " + cliente.getClieId());
		});
	}

}
