package co.edu.usbcali.banco.service;

import java.util.List;

import co.edu.usbcali.banco.domain.TipoDocumento;

public interface TipoDocumentoService {
	
	public void save(TipoDocumento tipoDoc) throws Exception;
	public void update(TipoDocumento tipoDoc) throws Exception;
	public void delete(TipoDocumento tipoDoc) throws Exception;
	public TipoDocumento findById(Long tdocId);
	public List<TipoDocumento> findAll();
	
}
