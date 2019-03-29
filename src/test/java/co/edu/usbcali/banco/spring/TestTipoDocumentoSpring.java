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

import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestTipoDocumentoSpring {
	
	long tdocId = 5L;
	
	@PersistenceContext
	EntityManager entityManager;


	@Test
	@DisplayName("Crear Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void aTest() {
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNull(tipoDocumento);
		
		tipoDocumento = new TipoDocumento();
		
		tipoDocumento = new TipoDocumento();
		tipoDocumento.setActivo("S");
		tipoDocumento.setNombre("TARJETA ANDINA");
		tipoDocumento.setTdocId(tdocId);
		
		entityManager.persist(tipoDocumento);
	}
	
	@Test
	@DisplayName("Modificar Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void bTest() {
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDocumento);
		
		tipoDocumento.setActivo("N");
		tipoDocumento.setNombre("TARJETA ANDINA2");
		
		entityManager.merge(tipoDocumento);
	}
	
	@Test
	@DisplayName("Eliminar Tipo Documento")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void cTest() {
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDocumento, "El tipo de documento no existe");
		entityManager.remove(tipoDocumento);
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestTipoDocumentoSpring.class);
	@Test
	@DisplayName("Consultar Todos Tipos de Documentos")
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	void dTest() {
		String JPQL = "SELECT tcod FROM TipoDocumento tcod";
		List<TipoDocumento> losTiposDocumentos = entityManager.createQuery(JPQL).getResultList();
		
		losTiposDocumentos.forEach(tipoDoc -> {
			log.info("Id: " + tipoDoc.getTdocId());
			log.info("Nombre: " + tipoDoc.getNombre());
		});
	}

}
