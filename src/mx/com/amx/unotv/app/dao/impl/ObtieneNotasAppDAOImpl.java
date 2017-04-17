package mx.com.amx.unotv.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import mx.com.amx.unotv.app.dao.IObtieneNotasAppDAO;
import mx.com.amx.unotv.app.dao.exception.ObtieneNotasAppDAOException;
import mx.com.amx.unotv.app.dto.NoticiaListDTO;
@Component
@Qualifier("obtieneNotasAppDAO")
public class ObtieneNotasAppDAOImpl implements IObtieneNotasAppDAO {
	
	private static Logger logger=Logger.getLogger(ObtieneNotasAppDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public ArrayList<NoticiaListDTO> obtieneNotas(String fecha)
			throws ObtieneNotasAppDAOException {
		StringBuffer sbQuery = new StringBuffer();
		try {
			
			List<Object> array = new ArrayList<Object>();
			
			sbQuery.append(" SELECT ");
			sbQuery.append(" C.FC_DESCRIPCION as desc_categoria, ");
			sbQuery.append(" S.FC_DESCRIPCION as desc_seccion, ");
			sbQuery.append(" N.FC_ID_CATEGORIA as id_categoria, ");
			sbQuery.append(" C.FC_ID_SECCION as id_seccion, ");
			sbQuery.append(" N.FC_ID_CONTENIDO as id_contenido, ");
			sbQuery.append(" N.FC_TITULO as titulo, ");
			sbQuery.append(" N.FC_DESCRIPCION as descripcion, ");
			sbQuery.append(" N.FC_IMAGEN_PRINCIPAL as imagen_principal, ");
			sbQuery.append(" REPLACE(N.FC_IMAGEN_PRINCIPAL,'Principal', 'Miniatura') as imagen_miniatura, ");
			sbQuery.append(" N.FD_FECHA_PUBLICACION as fecha_publicacion, ");
			sbQuery.append(" N.FC_ID_TIPO_NOTA as id_tipo_nota ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA as N, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_CATEGORIA as C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_SECCION S ");
			sbQuery.append(" WHERE C.FC_ID_CATEGORIA=N.FC_ID_CATEGORIA ");
			sbQuery.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION ");
			sbQuery.append(" AND N.FI_BAN_INFINITO_HOME=1 ");
			sbQuery.append(" AND N.FC_ID_CONTENIDO NOT IN (select FC_ID_CONTENIDO from wpdb2ins.UNO_MX_I_NOTA_MAGAZINE where FC_ID_MAGAZINE='magazine-home-2') ");
			if(fecha != null && !fecha.trim().equals("")){
				Timestamp fechaConsulta = Timestamp.valueOf(fecha);
				sbQuery.append(" AND N.FD_FECHA_PUBLICACION < ? ");
				array.add(fechaConsulta);
			}
			
			sbQuery.append(" ORDER BY N.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 16 ROWS ONLY OPTIMIZE FOR 15 ROWS ");
			
			BeanPropertyRowMapper<NoticiaListDTO> mapper = new BeanPropertyRowMapper<NoticiaListDTO>(NoticiaListDTO.class);
			ArrayList<NoticiaListDTO> lst = (ArrayList<NoticiaListDTO>) jdbcTemplate.query(sbQuery.toString(), 
													array.toArray(), mapper);
			
			return lst;
		} catch (Exception e) {
			logger.error("Exception en obtienePreferidos: ",e);
			logger.error("SQL: "+sbQuery);
			throw new ObtieneNotasAppDAOException("No se pudo recuperar la lista de notas", e);
		}
	}

	@Override
	public ArrayList<NoticiaListDTO> obtieneNotasBySeccion(String seccion,
			String fecha) throws ObtieneNotasAppDAOException {
				
		StringBuffer sbQuery = new StringBuffer();
		List<Object> array = new ArrayList<Object>();
		
		try {
			
			array.add(seccion);
			
			sbQuery.append(" SELECT ");
			sbQuery.append(" N.FC_ID_CATEGORIA as id_categoria,  ");
			sbQuery.append(" C.FC_ID_SECCION as id_seccion, ");
			sbQuery.append(" C.FC_DESCRIPCION as desc_categoria, ");
			sbQuery.append(" S.FC_DESCRIPCION as desc_seccion, ");
			sbQuery.append(" N.FC_ID_CONTENIDO as id_contenido, ");
			sbQuery.append(" N.FC_TITULO as titulo, ");
			sbQuery.append(" N.FC_DESCRIPCION as descripcion, ");
			sbQuery.append(" N.FC_IMAGEN_PRINCIPAL as imagen_principal, ");
			sbQuery.append(" REPLACE(N.FC_IMAGEN_PRINCIPAL,'Principal', 'Miniatura') as imagen_miniatura, ");
			sbQuery.append(" N.FD_FECHA_PUBLICACION as fecha_publicacion, ");
			sbQuery.append(" N.FC_ID_TIPO_NOTA as id_tipo_nota ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA N, WPDB2INS.UNO_MX_C_CATEGORIA C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_SECCION S ");
			sbQuery.append(" WHERE N.FC_ID_CATEGORIA = C.FC_ID_CATEGORIA  ");	
			sbQuery.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION ");
			sbQuery.append(" AND C.FC_ID_SECCION = ? ");						
			if(fecha != null && !fecha.trim().equals("")){
				Timestamp fechaConsulta = Timestamp.valueOf(fecha);
				sbQuery.append(" AND N.FD_FECHA_PUBLICACION < ? ");
				array.add(fechaConsulta);
			}
			sbQuery.append(" ORDER BY N.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 16 ROWS ONLY OPTIMIZE FOR 15 ROWS ");
			
			return (ArrayList<NoticiaListDTO>)this.jdbcTemplate.query(
					sbQuery.toString(), array.toArray(),
			        new RowMapper<NoticiaListDTO>() {
			            public NoticiaListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	NoticiaListDTO preferidosDTO = new NoticiaListDTO();
			            	preferidosDTO.setId_contenido(rs.getString("id_contenido"));
			            	preferidosDTO.setTitulo(rs.getString("titulo"));
			            	preferidosDTO.setDescripcion(rs.getString("descripcion"));
			            	preferidosDTO.setFecha_publicacion(rs.getString("fecha_publicacion"));
			            	preferidosDTO.setId_tipo_nota(rs.getString("id_tipo_nota"));
			            	preferidosDTO.setImagen_principal(rs.getString("imagen_principal"));
			            	preferidosDTO.setImagen_miniatura(rs.getString("imagen_miniatura"));
			            	preferidosDTO.setId_categoria(rs.getString("id_categoria"));
			            	preferidosDTO.setId_seccion(rs.getString("id_seccion"));
			            	preferidosDTO.setDesc_categoria(rs.getString("desc_categoria"));
			            	preferidosDTO.setDesc_seccion(rs.getString("desc_seccion"));
			                return preferidosDTO;
			            }
			        });
		} catch (Exception e) {
			logger.error("Exception en obtieneNotasBySeccion: ",e);
			logger.error("SQL: "+sbQuery);
			throw new ObtieneNotasAppDAOException("No se pudo recuperar la lista de notas", e);
		}
	}
	
	public String getRutaFDP(String tipoConsulta, String idConsulta) throws ObtieneNotasAppDAOException{
		String ruta="";
		StringBuffer sbQuery = new StringBuffer();
		
		try {	
			if(tipoConsulta.equalsIgnoreCase("categoria")){
				sbQuery.append(" select C.FC_RUTA_DFP_APP as ruta_dfp   ");
				sbQuery.append(" FROM WPDB2INS.UNO_MX_C_CATEGORIA C ");	
				sbQuery.append(" WHERE ");
				sbQuery.append(" C.FC_ID_CATEGORIA = ? ");			
			}else{
				sbQuery.append(" select C.FC_RUTA_DFP_APP as ruta_dfp   ");
				sbQuery.append(" FROM WPDB2INS.UNO_MX_C_CATEGORIA C ");	
				sbQuery.append(" WHERE ");
				sbQuery.append(" C.FC_ID_SECCION = ? ");
				sbQuery.append(" group by C.FC_RUTA_DFP_APP ");
			}
			ruta = (String) this.jdbcTemplate
				    .queryForObject(sbQuery.toString(), new Object[]{idConsulta}, String.class);
		} catch (Exception e) {
			logger.error("Exception en getRutaFDP: ",e);
			logger.error("SQL: "+sbQuery);
			throw new ObtieneNotasAppDAOException(e.getMessage());
		}
		return ruta;
	}
	@Override
	public ArrayList<NoticiaListDTO> obtieneNotasByCategoria(
			String categoria, String fecha)
			throws ObtieneNotasAppDAOException {
		StringBuffer sbQuery = new StringBuffer();
		List<Object> array = new ArrayList<Object>();
		
		try {
			array.add(categoria);
			
			sbQuery.append(" SELECT ");
			sbQuery.append(" N.FC_ID_CATEGORIA as id_categoria,  ");
			sbQuery.append(" C.FC_ID_SECCION as id_seccion, ");
			sbQuery.append(" C.FC_DESCRIPCION as desc_categoria, ");
			sbQuery.append(" S.FC_DESCRIPCION as desc_seccion, ");
			sbQuery.append(" N.FC_ID_CONTENIDO as id_contenido, ");
			sbQuery.append(" N.FC_TITULO as titulo, ");
			sbQuery.append(" N.FC_DESCRIPCION as descripcion, ");
			sbQuery.append(" N.FC_IMAGEN_PRINCIPAL as imagen_principal, ");
			sbQuery.append(" REPLACE(N.FC_IMAGEN_PRINCIPAL,'Principal', 'Miniatura') as imagen_miniatura, ");
			sbQuery.append(" N.FD_FECHA_PUBLICACION as fecha_publicacion, ");
			sbQuery.append(" N.FC_ID_TIPO_NOTA as id_tipo_nota ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA N, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_CATEGORIA C, ");	
			sbQuery.append(" WPDB2INS.UNO_MX_C_SECCION S ");
			sbQuery.append(" WHERE ");
			sbQuery.append(" N.FC_ID_CATEGORIA = C.FC_ID_CATEGORIA  ");
			sbQuery.append(" AND C.FC_ID_SECCION=S.FC_ID_SECCION AND");
			sbQuery.append(" N.FC_ID_CATEGORIA = ? ");			
			if(fecha != null && !fecha.trim().equals("")){
				Timestamp fechaConsulta = Timestamp.valueOf(fecha);
				sbQuery.append(" AND N.FD_FECHA_PUBLICACION < ? ");
				array.add(fechaConsulta);
			}
			sbQuery.append(" ORDER BY N.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 16 ROWS ONLY OPTIMIZE FOR 15 ROWS ");
			
			logger.info("SQL: "+sbQuery);
			for (Object object : array) {
				logger.info("Objeto de array: "+object);
			}
			BeanPropertyRowMapper<NoticiaListDTO> mapper = new BeanPropertyRowMapper<NoticiaListDTO>(NoticiaListDTO.class);
			ArrayList<NoticiaListDTO> lst = (ArrayList<NoticiaListDTO>) jdbcTemplate.query(sbQuery.toString(), 
													array.toArray(), mapper);
			return lst;
		} catch (Exception e) {
			logger.error("Exception en obtieneNotasByCategoria: ",e);
			logger.error("SQL: "+sbQuery);
			throw new ObtieneNotasAppDAOException(e.getMessage());
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
