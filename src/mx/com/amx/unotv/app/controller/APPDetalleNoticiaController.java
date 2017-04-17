package mx.com.amx.unotv.app.controller;

import mx.com.amx.unotv.app.dao.IObtieneDetalleAppDAO;
import mx.com.amx.unotv.app.dto.response.RespuestaNoticiaDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequestMapping("appDetalleNoticiaController")
public class APPDetalleNoticiaController {
	protected static final Logger log=Logger.getLogger(APPDetalleNoticiaController.class);
	private IObtieneDetalleAppDAO obtieneDetalleAppDAO;
	
	@RequestMapping(value={"obtieneDetalleNoticia"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaDTO obtieneDetalleNoticia( @RequestBody String idNoticia ){
		RespuestaNoticiaDTO respuestaDTO = new RespuestaNoticiaDTO();
		log.info("obtieneDetalleNoticia [Controller]");
		log.info("idNoticia: "+idNoticia);

		try{
			respuestaDTO.setCodigo("0");
			respuestaDTO.setMensaje("OK");
			respuestaDTO.setNoticia(this.obtieneDetalleAppDAO.obtieneDetalle(idNoticia));
			respuestaDTO.setCausa_error("");
		} catch (Exception e){
			log.error(" Error appNoticiaController [obtieneDetalleNoticia]:", e);
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setNoticia(null);
		}
		return respuestaDTO;
	}
	
	@RequestMapping(value={"obtieneDetalleNoticiaByFriendlyURL"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaDTO obtieneDetalleNoticiaByFriendlyURL( @RequestBody String friendlyURL){
		RespuestaNoticiaDTO respuestaDTO = new RespuestaNoticiaDTO();
		log.info("obtieneDetalleNoticiaByFriendlyURL [Controller]");
		log.info("friendlyURL: "+friendlyURL);

		try{
			respuestaDTO.setCodigo("0");
			respuestaDTO.setMensaje("OK");
			respuestaDTO.setNoticia(this.obtieneDetalleAppDAO.obtieneDetalleNoticiaByFriendlyURL(friendlyURL));
			respuestaDTO.setCausa_error("");
		} catch (Exception e){
			log.error(" Error appNoticiaController [obtieneDetalleNoticiaByFriendlyURL]:", e);
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setNoticia(null);
		}
		return respuestaDTO;
	}
	/**
	 * @return the obtieneDetalleAppDAO
	 */
	public IObtieneDetalleAppDAO getObtieneDetalleAppDAO() {
		return obtieneDetalleAppDAO;
	}
	/**
	 * @param obtieneDetalleAppDAO the obtieneDetalleAppDAO to set
	 */
	@Autowired
	public void setObtieneDetalleAppDAO(IObtieneDetalleAppDAO obtieneDetalleAppDAO) {
		this.obtieneDetalleAppDAO = obtieneDetalleAppDAO;
	}
	
	
}
