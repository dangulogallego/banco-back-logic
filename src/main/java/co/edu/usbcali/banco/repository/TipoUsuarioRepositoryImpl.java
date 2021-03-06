package co.edu.usbcali.banco.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.banco.domain.TipoUsuario;

@Repository
@Scope("singleton")
public class TipoUsuarioRepositoryImpl implements TipoUsuarioRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(TipoUsuario tipoUsuario) {
		entityManager.persist(tipoUsuario);
	}

	@Override
	public void update(TipoUsuario tipoUsuario) {
		entityManager.merge(tipoUsuario);
	}

	@Override
	public void delete(TipoUsuario tipoUsuario) {
		entityManager.remove(tipoUsuario);
	}

	@Override
	public TipoUsuario findById(Long tiusId) {
		return entityManager.find(TipoUsuario.class, tiusId);
	}

	@Override
	public List<TipoUsuario> findAll() {
		return entityManager.createNamedQuery("TipoUsuario.findAll").getResultList();
	}

}
