package co.edu.usbcali.banco.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestTipoDocumentoRepository {

	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	
	long tdocId = 5L;


	@Test
	@DisplayName("Crear Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void aTest() {
		
		assertNotNull(tipoDocumentoRepository);
		
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(tdocId);
		assertNull(tipoDocumento);
		
		tipoDocumento = new TipoDocumento();
		
		tipoDocumento = new TipoDocumento();
		tipoDocumento.setActivo("S");
		tipoDocumento.setNombre("TARJETA ANDINA");
		tipoDocumento.setTdocId(tdocId);
		
		tipoDocumentoRepository.save(tipoDocumento);
	}
	
	@Test
	@DisplayName("Modificar Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void bTest() {
		
		assertNotNull(tipoDocumentoRepository);
		
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(tdocId);
		assertNotNull(tipoDocumento);
		
		tipoDocumento.setActivo("N");
		tipoDocumento.setNombre("TARJETA ANDINA2");
		
		tipoDocumentoRepository.update(tipoDocumento);
	}
	
	@Test
	@DisplayName("Eliminar Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void cTest() {
		
		assertNotNull(tipoDocumentoRepository);
		
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(tdocId);
		assertNotNull(tipoDocumento, "El tipo de documento no existe");
		tipoDocumentoRepository.delete(tipoDocumento);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestTipoDocumentoRepository.class);
	@Test
	@DisplayName("Consultar Todos Tipos de Documentos")
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void dTest() {
		
		assertNotNull(tipoDocumentoRepository);
		List<TipoDocumento> losTiposDocumentos = tipoDocumentoRepository.findAll();
		
		losTiposDocumentos.forEach(tipoDoc -> {
			log.info("Id: " + tipoDoc.getTdocId());
			log.info("Nombre: " + tipoDoc.getNombre());
		});
	}

}
