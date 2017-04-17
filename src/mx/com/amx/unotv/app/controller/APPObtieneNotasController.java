package mx.com.amx.unotv.app.controller;

import mx.com.amx.unotv.app.dao.IObtieneNotasAppDAO;
import mx.com.amx.unotv.app.dto.response.RespuestaNoticiaListDFPDTO;
import mx.com.amx.unotv.app.dto.response.RespuestaNoticiaListDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequestMapping("appObtieneNotasController")
public class APPObtieneNotasController {
	
	private static final Logger log=Logger.getLogger(APPObtieneNotasController.class);
	private IObtieneNotasAppDAO obtieneNotasAppDAO;
	
	@RequestMapping(value={"obtieneNotas"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaListDTO obtieneNotas( @RequestBody String fecha ){
		RespuestaNoticiaListDTO respuestaDTO = new RespuestaNoticiaListDTO();
		log.info("obtieneNotas [Controller]");
		log.info("fecha: "+fecha);
		try{
			respuestaDTO.setCodigo("0");
			respuestaDTO.setMensaje("OK");
			respuestaDTO.setListaNotas(this.obtieneNotasAppDAO.obtieneNotas(fecha));
			respuestaDTO.setCausa_error("");
		} catch (Exception e){
			log.error(" Error appObtieneNotasController [obtieneNotas]:", e);
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setListaNotas(null);
		}
		return respuestaDTO;
	}
	
	@RequestMapping(value={"obtieneNotasBySeccion"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaListDFPDTO obtieneNotasBySeccion( @RequestParam("seccion") String seccion, @RequestParam("fecha") String fecha){
		RespuestaNoticiaListDFPDTO respuestaDTO = new RespuestaNoticiaListDFPDTO();
		log.info("obtieneNotasBySeccion [Controller]");
		log.info("seccion: "+seccion);
		log.info("fecha: "+fecha);
		try{
			respuestaDTO.setCodigo("0");
			respuestaDTO.setMensaje("OK");
			respuestaDTO.setListaNotas(this.obtieneNotasAppDAO.obtieneNotasBySeccion(seccion,fecha));
			respuestaDTO.setRuta_dfp(this.obtieneNotasAppDAO.getRutaFDP("seccion", seccion));
			respuestaDTO.setCausa_error("");
		} catch (Exception e){
			log.error(" Error appObtieneNotasController [obtieneNotasBySeccion]:", e);
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setListaNotas(null);
		}
		return respuestaDTO;
	}
	
	@RequestMapping(value={"obtieneNotasByCategoria"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaListDFPDTO obtieneNotasByCategoria( @RequestParam("categoria") String categoria, @RequestParam("fecha") String fecha){
		RespuestaNoticiaListDFPDTO respuestaDTO = new RespuestaNoticiaListDFPDTO();
		log.info("obtieneNotasByCategoria [Controller]");
		log.info("categoria: "+categoria);
		log.info("fecha: "+fecha);
		try{
			respuestaDTO.setCodigo("0");
			respuestaDTO.setMensaje("OK");
			respuestaDTO.setListaNotas(this.obtieneNotasAppDAO.obtieneNotasByCategoria(categoria, fecha));
			respuestaDTO.setRuta_dfp(this.obtieneNotasAppDAO.getRutaFDP("categoria", categoria));
			respuestaDTO.setCausa_error("");
		} catch (Exception e){
			log.error(" Error appObtieneNotasController [obtieneNotasByCategoria]:", e);
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setListaNotas(null);
		}
		return respuestaDTO;
	}

	/**
	 * @return the obtieneNotasAppDAO
	 */
	public IObtieneNotasAppDAO getObtieneNotasAppDAO() {
		return obtieneNotasAppDAO;
	}

	/**
	 * @param obtieneNotasAppDAO the obtieneNotasAppDAO to set
	 */
	@Autowired
	public void setObtieneNotasAppDAO(IObtieneNotasAppDAO obtieneNotasAppDAO) {
		this.obtieneNotasAppDAO = obtieneNotasAppDAO;
	}
	
	/**/
	
}	
