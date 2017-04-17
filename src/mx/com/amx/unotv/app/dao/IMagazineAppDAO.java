package mx.com.amx.unotv.app.dao;

import java.util.ArrayList;

import mx.com.amx.unotv.app.dao.exception.MagazineAppDAOException;
import mx.com.amx.unotv.app.dto.NoticiaListDTO;

public interface IMagazineAppDAO {
	/**
	 * Metodo que obtiene las notas del magazine del home 
	 * @return ArrayList<NoticiaListDTO>
	 * @author Jesús Vicuña
	 * @throws MagazineAppDAOException
	 **/
	public ArrayList<NoticiaListDTO> obtieneMagazine( ) throws MagazineAppDAOException; 
}
