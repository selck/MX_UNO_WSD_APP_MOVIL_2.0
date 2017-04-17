package mx.com.amx.unotv.app.dao;

import java.util.ArrayList;

import mx.com.amx.unotv.app.dao.exception.ObtieneNotasAppDAOException;
import mx.com.amx.unotv.app.dto.NoticiaListDTO;
import mx.com.amx.unotv.app.dto.response.RespuestaNoticiaListDFPDTO;

public interface IObtieneNotasAppDAO {
	
	/**
	 * Metodo que regresa el listado de las últimas 15 notas cargadas en unotv.
	 * @return ArrayList<NoticiaListDTO>
	 * @author Jesús Vicuña
	 * @throws ObtieneNotasAppDAOException
	 * @param String fechaConsulta
	 **/
	public ArrayList<NoticiaListDTO> obtieneNotas(String fechaConsulta  ) throws ObtieneNotasAppDAOException; 
	/**
	 * Metodo que regresa el listado de las últimas 15 notas cargadas en unotv acorde a la seccion especificada.
	 * @return ArrayList<NoticiaListDTO>
	 * @author Jesús Vicuña
	 * @throws ObtieneNotasAppDAOException
	 * @param String seccion
	 * @param String fechaConsulta
	 **/
	public ArrayList<NoticiaListDTO> obtieneNotasBySeccion(String seccion,String fechaConsulta  ) throws ObtieneNotasAppDAOException;
	/**
	 * Metodo que regresa el listado de las últimas 15 notas cargadas en unotv acorde a la categoría especificada.
	 * @return ArrayList<NoticiaListDTO>
	 * @author Jesús Vicuña
	 * @throws ObtieneNotasAppDAOException
	 * @param String categoria
	 * @param String fechaConsulta
	 **/
	public ArrayList<NoticiaListDTO> obtieneNotasByCategoria(String categoria, String fechaConsulta  ) throws ObtieneNotasAppDAOException;
	/**
	 * Metodo que regresa la ruta FDP acorde a los parametros enviados (Seccion o Categoría)
	 * @return String
	 * @author Jesús Vicuña
	 * @throws ObtieneNotasAppDAOException
	 * @param String tipoConsulta
	 * @param String idConsulta
	 **/
	public String getRutaFDP(String tipoConsulta, String idConsulta) throws ObtieneNotasAppDAOException;
	
}
