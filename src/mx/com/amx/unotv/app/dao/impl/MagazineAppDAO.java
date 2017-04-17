package mx.com.amx.unotv.app.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import mx.com.amx.unotv.app.dao.IMagazineAppDAO;
import mx.com.amx.unotv.app.dao.exception.MagazineAppDAOException;
import mx.com.amx.unotv.app.dto.NoticiaListDTO;

@Component
@Qualifier("magazineAppDAO")
public class MagazineAppDAO implements IMagazineAppDAO {
	
	private static Logger log=Logger.getLogger(MagazineAppDAO.class);
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public ArrayList<NoticiaListDTO> obtieneMagazine()
			throws MagazineAppDAOException {
		StringBuffer sbQuery = new StringBuffer();
		try {
		    
			sbQuery.append(" SELECT ");
			sbQuery.append(" A.FC_ID_CONTENIDO as id_contenido, ");
			sbQuery.append(" A.FC_TITULO as titulo, ");
			sbQuery.append(" A.FC_DESCRIPCION as descripcion, ");
			sbQuery.append(" FC_IMAGEN_PRINCIPAL as imagen_principal, ");
			sbQuery.append(" REPLACE(FC_IMAGEN_PRINCIPAL,'Principal', 'Miniatura') as imagen_miniatura, ");
			sbQuery.append(" A.FD_FECHA_PUBLICACION as fecha_publicacion, ");
			sbQuery.append(" A.FC_ID_TIPO_NOTA as id_tipo_nota, ");
			sbQuery.append(" A.FC_ID_CATEGORIA as id_categoria, ");
			sbQuery.append(" C.FC_ID_SECCION as id_seccion, ");
			sbQuery.append(" C.FC_DESCRIPCION as desc_categoria, ");
			sbQuery.append(" S.FC_DESCRIPCION as desc_seccion ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_C_CATEGORIA C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_SECCION S, ");
			sbQuery.append(" WPDB2INS.UNO_MX_N_NOTA A  ");
			sbQuery.append(" LEFT OUTER JOIN WPDB2INS.UNO_MX_I_NOTA_MAGAZINE B ON A.FC_ID_CONTENIDO = B.FC_ID_CONTENIDO ");
			sbQuery.append(" WHERE  FC_ID_MAGAZINE = 'magazine-home-2' ");
			sbQuery.append(" AND C.FC_ID_CATEGORIA=A.FC_ID_CATEGORIA ");
			sbQuery.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION ");
			sbQuery.append(" ORDER BY B.FI_ORDEN ASC ");
			
			return (ArrayList<NoticiaListDTO>) jdbcTemplate.query(sbQuery.toString(), new BeanPropertyRowMapper<NoticiaListDTO>(NoticiaListDTO.class));
		} catch (Exception e) {
			log.error("Exception en obtieneMagazine: ",e);
			log.error("SQL: "+sbQuery);
			throw new MagazineAppDAOException("No se pudo recuperar el magazine", e);
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
