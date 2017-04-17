package mx.com.amx.unotv.app.dao;

import mx.com.amx.unotv.app.dao.exception.DetalleNotaAppDAOException;
import mx.com.amx.unotv.app.dto.NoticiaDTO;

public interface IObtieneDetalleAppDAO {
	/**
	 * Metodo que obtiene el detalle de una Nota
	 * @return RespuestaNoticia
	 * @param id_nota "Id de la nota a buscar"
	 * @author Jesús Vicuña
	 * @throws DetalleNotaAppDAOException
	 **/
	public NoticiaDTO obtieneDetalle(String id_nota ) throws DetalleNotaAppDAOException;
	/**
	 * Metodo que obtiene el detalle de una Nota
	 * @return RespuestaNoticia
	 * @param friendlyURL "friendlyURL de la nota a buscar"
	 * @author Jesús Vicuña
	 * @throws DetalleNotaAppDAOException
	 **/
	public NoticiaDTO obtieneDetalleNoticiaByFriendlyURL(String friendlyURL ) throws DetalleNotaAppDAOException;
}
