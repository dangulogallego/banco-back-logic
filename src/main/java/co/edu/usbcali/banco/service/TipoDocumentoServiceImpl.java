package co.edu.usbcali.banco.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.TipoDocumento;
import co.edu.usbcali.banco.repository.TipoDocumentoRepository;

@Service
@Scope("singleton")
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;
	
	@Autowired
	private Validator validator;
	
	public void validar(TipoDocumento tipoDoc) throws Exception {
	    try {
	        Set<ConstraintViolation<TipoDocumento>> constraintViolations = validator.validate(tipoDoc);

	        if (constraintViolations.size() > 0) {
	            StringBuilder strMessage = new StringBuilder();

	            for (ConstraintViolation<TipoDocumento> constraintViolation : constraintViolations) {
	                strMessage.append(constraintViolation.getPropertyPath()
	                                                     .toString());
	                strMessage.append(" - ");
	                strMessage.append(constraintViolation.getMessage());
	                strMessage.append(". \n");
	            }

	            throw new Exception(strMessage.toString());
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void save(TipoDocumento tipoDoc) throws Exception {
		if (tipoDoc == null) {
			throw new Exception("El tipo de documento es nulo");
		}
		validar(tipoDoc);
		tipoDocumentoRepository.save(tipoDoc);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void update(TipoDocumento tipoDoc) throws Exception {
		if (tipoDoc == null) {
			throw new Exception("El tipo de documento es nulo");
		}
		validar(tipoDoc);
		tipoDocumentoRepository.update(tipoDoc);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void delete(TipoDocumento tipoDoc) throws Exception {
		if (tipoDoc == null) {
			throw new Exception("El tipo de documento es nulo");
		}
		TipoDocumento entity = tipoDocumentoRepository.findById(tipoDoc.getTdocId());
		tipoDocumentoRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly=true)
	public TipoDocumento findById(Long tdocId) {
		return tipoDocumentoRepository.findById(tdocId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TipoDocumento> findAll() {
		return tipoDocumentoRepository.findAll();
	}

}
