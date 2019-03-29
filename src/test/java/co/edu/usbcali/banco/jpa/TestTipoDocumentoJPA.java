package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import co.edu.usbcali.banco.domain.TipoDocumento;

class TestTipoDocumentoJPA {
	
	long tdocId = 4L;
	
	@Test
	@DisplayName("Crear Tipo Documento")
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNull(tipoDocumento);
		
		tipoDocumento = new TipoDocumento();
		tipoDocumento.setActivo("S");
		tipoDocumento.setNombre("TARJETA ANDINA");
		tipoDocumento.setTdocId(tdocId);
		
		entityManager.getTransaction().begin();
			entityManager.persist(tipoDocumento);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Modificar Tipo Documento")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDocumento, "El tipo de documento no existe");

		tipoDocumento.setActivo("N");
		
		entityManager.getTransaction().begin();
			entityManager.merge(tipoDocumento);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("Borrar Tipo Documento")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDocumento, "El tipo de documento no existe");
		
		entityManager.getTransaction().begin();
			entityManager.remove(tipoDocumento);
		entityManager.getTransaction().commit();
	}
	
	private final static Logger log = org.slf4j.LoggerFactory.getLogger(TestTipoDocumentoJPA.class);
	@Test
	@DisplayName("Consultar Todos Tipos de Documentos")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es nulo");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");
		
		String JPQL = "SELECT tdoc FROM TipoDocumento tdoc";
		List<TipoDocumento> losTiposDocumentos = entityManager.createQuery(JPQL).getResultList();
		
		losTiposDocumentos.forEach(tipoDoc -> {
			log.info("Id: " + tipoDoc.getTdocId());
			log.info("Nombre: " + tipoDoc.getNombre());
		});
	}
}
