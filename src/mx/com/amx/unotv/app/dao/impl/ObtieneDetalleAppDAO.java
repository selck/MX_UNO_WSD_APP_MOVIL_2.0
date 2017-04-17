package mx.com.amx.unotv.app.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import mx.com.amx.unotv.app.dao.IObtieneDetalleAppDAO;
import mx.com.amx.unotv.app.dao.exception.DetalleNotaAppDAOException;
import mx.com.amx.unotv.app.dto.NoticiaDTO;

@Component
@Qualifier("obtieneDetalleAppDAO")
public class ObtieneDetalleAppDAO implements IObtieneDetalleAppDAO {
	
	private static Logger log=Logger.getLogger(ObtieneDetalleAppDAO.class);
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public NoticiaDTO obtieneDetalleNoticiaByFriendlyURL(String friendlyURL)
			throws DetalleNotaAppDAOException {
		StringBuffer sbQuery = new StringBuffer();
		try {
			
			NoticiaDTO dto = null;
			
			sbQuery.append(" SELECT  ");
			sbQuery.append(" N.FC_ID_CONTENIDO as id_contenido,   ");
			sbQuery.append(" CASE ");
			sbQuery.append(" WHEN TS.FC_ID_TIPO_SECCION ='especiales' THEN  ");
			sbQuery.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,'')) ||'/'");
			sbQuery.append(" ELSE ");
			sbQuery.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,'')) ||'/'");
			sbQuery.append(" END as url_nota, ");
			sbQuery.append(" N.FC_ID_CATEGORIA as id_categoria, ");  
			sbQuery.append(" N.FC_NOMBRE as nombre,   ");
			sbQuery.append(" C.FC_RUTA_DFP_APP as ruta_dfp,   ");
			sbQuery.append(" N.FC_TITULO as titulo,   ");
			sbQuery.append(" N.FC_DESCRIPCION as descripcion,    ");
			sbQuery.append(" N.FC_ESCRIBIO as escribio,   ");
			sbQuery.append(" N.FC_LUGAR as lugar,  ");
			sbQuery.append(" N.FC_FUENTE as fuente,  ");
			sbQuery.append(" N.FC_ID_TIPO_NOTA as id_tipo_nota,  ");
			sbQuery.append(" N.FC_IMAGEN_PRINCIPAL as imagen_principal,   ");
			sbQuery.append(" N.FC_IMAGEN_PIE as pie_imagen,  ");
			sbQuery.append(" N.FC_VIDEO_YOUTUBE as video_youtube,  ");
			sbQuery.append(" N.FC_ID_VIDEO_CONTENT as id_video_content,  ");
			sbQuery.append(" N.FC_ID_VIDEO_PLAYER as id_video_player,   ");
			sbQuery.append(" N.CL_GALERIA_IMAGENES as galeria,  ");
			sbQuery.append(" N.FC_INFOGRAFIA as imagen_infografia,  ");
			sbQuery.append(" ('<div class=\"content-article ')||N.FC_ID_CATEGORIA|| ('\">')||N.CL_RTF_CONTENIDO||('</div>') as contenido_nota,  ");
			sbQuery.append(" N.FD_FECHA_PUBLICACION as fecha_publicacion,   ");
			sbQuery.append(" N.FD_FECHA_MODIFICACION as fecha_modificacion,  ");
			sbQuery.append(" C.FC_DESCRIPCION as desc_categoria, ");
			sbQuery.append(" S.FC_DESCRIPCION as desc_seccion, ");
			sbQuery.append(" COALESCE(N.FC_PLACE_GALLERY, '') as posicion_galeria ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_H_NOTA N,    ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION TS,   ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_SECCION S,   ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_CATEGORIA  C   ");
			sbQuery.append(" WHERE ");
			sbQuery.append(" C.FC_ID_CATEGORIA=N.FC_ID_CATEGORIA   ");
			sbQuery.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION   ");
			sbQuery.append(" AND N.FC_NOMBRE = ? ");
			
			ArrayList<NoticiaDTO> listNoticias = (ArrayList<NoticiaDTO>)jdbcTemplate.query(sbQuery.toString(), 
											new Object[]{friendlyURL}, new BeanPropertyRowMapper<NoticiaDTO>(NoticiaDTO.class));
			
			if(listNoticias == null || listNoticias.size() == 0){
				throw new DetalleNotaAppDAOException("No se encontr� informaci�n para la nota con friendlyURL: "+friendlyURL);
			}else if(listNoticias.size()>0)
				dto = listNoticias.get(0);
			
			return dto;
		} catch (Exception e) {
			log.error("Exception en obtieneDetalleNoticiaByFriendlyURL: ",e);
			log.error("SQL: "+sbQuery);
			throw new DetalleNotaAppDAOException("No se pudo recuperar el detalle de la notica con la friendlyURL: "+friendlyURL, e);
		}
	}
	
	@Override
	public NoticiaDTO obtieneDetalle(String id_nota)
			throws DetalleNotaAppDAOException {
		StringBuffer sbQuery = new StringBuffer();
		try {
			
			NoticiaDTO dto = null;
			
			sbQuery.append(" SELECT  ");
			sbQuery.append(" N.FC_ID_CONTENIDO as id_contenido,   ");
			sbQuery.append(" CASE ");
			sbQuery.append(" WHEN TS.FC_ID_TIPO_SECCION ='especiales' THEN  ");
			sbQuery.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,'')) ||'/'");
			sbQuery.append(" ELSE ");
			sbQuery.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,'')) ||'/'");
			sbQuery.append(" END as url_nota, ");
			sbQuery.append(" N.FC_ID_CATEGORIA as id_categoria, ");  
			sbQuery.append(" N.FC_NOMBRE as nombre,   ");
			sbQuery.append(" C.FC_RUTA_DFP_APP as ruta_dfp,   ");
			sbQuery.append(" N.FC_TITULO as titulo,   ");
			sbQuery.append(" N.FC_DESCRIPCION as descripcion,    ");
			sbQuery.append(" N.FC_ESCRIBIO as escribio,   ");
			sbQuery.append(" N.FC_LUGAR as lugar,  ");
			sbQuery.append(" N.FC_FUENTE as fuente,  ");
			sbQuery.append(" N.FC_ID_TIPO_NOTA as id_tipo_nota,  ");
			sbQuery.append(" N.FC_IMAGEN_PRINCIPAL as imagen_principal,   ");
			sbQuery.append(" N.FC_IMAGEN_PIE as pie_imagen,  ");
			sbQuery.append(" N.FC_VIDEO_YOUTUBE as video_youtube,  ");
			sbQuery.append(" N.FC_ID_VIDEO_CONTENT as id_video_content,  ");
			sbQuery.append(" N.FC_ID_VIDEO_PLAYER as id_video_player,   ");
			sbQuery.append(" N.CL_GALERIA_IMAGENES as galeria,  ");
			sbQuery.append(" N.FC_INFOGRAFIA as imagen_infografia,  ");
			sbQuery.append(" ('<div class=\"content-article ')||N.FC_ID_CATEGORIA|| ('\">')||N.CL_RTF_CONTENIDO||('</div>') as contenido_nota,  ");
			sbQuery.append(" N.FD_FECHA_PUBLICACION as fecha_publicacion,   ");
			sbQuery.append(" N.FD_FECHA_MODIFICACION as fecha_modificacion,  ");
			sbQuery.append(" C.FC_DESCRIPCION as desc_categoria, ");
			sbQuery.append(" S.FC_DESCRIPCION as desc_seccion, ");
			sbQuery.append(" COALESCE(N.FC_PLACE_GALLERY, '') as posicion_galeria ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_H_NOTA N,    ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION TS,   ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_SECCION S,   ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_CATEGORIA  C   ");
			sbQuery.append(" WHERE ");
			sbQuery.append(" C.FC_ID_CATEGORIA=N.FC_ID_CATEGORIA   ");
			sbQuery.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION   ");
			sbQuery.append(" AND N.FC_ID_CONTENIDO = ? ");
			
			ArrayList<NoticiaDTO> listNoticias = (ArrayList<NoticiaDTO>)jdbcTemplate.query(sbQuery.toString(), 
											new Object[]{id_nota}, new BeanPropertyRowMapper<NoticiaDTO>(NoticiaDTO.class));
			
			if(listNoticias == null || listNoticias.size() == 0){
				throw new DetalleNotaAppDAOException("No se encontr� informaci�n para la nota con id: "+id_nota);
			}else if(listNoticias.size()>0)
				dto = listNoticias.get(0);
			
			return dto;
		} catch (Exception e) {
			log.error("Exception en obtieneDetalleNoticia: ",e);
			log.error("SQL: "+sbQuery);
			throw new DetalleNotaAppDAOException("No se pudo recuperar el detalle de la notica con id: "+id_nota, e);
		}
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
