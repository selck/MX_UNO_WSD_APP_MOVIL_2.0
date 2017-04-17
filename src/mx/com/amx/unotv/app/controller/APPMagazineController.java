package mx.com.amx.unotv.app.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import mx.com.amx.unotv.app.dao.IMagazineAppDAO;
import mx.com.amx.unotv.app.dto.NoticiaListDTO;
import mx.com.amx.unotv.app.dto.response.RespuestaNoticiaListDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequestMapping("appMagazineController")
public class APPMagazineController {
	
	private static final Logger log=Logger.getLogger(APPMagazineController.class);
	private IMagazineAppDAO magazineAppDAO;
	
	@RequestMapping(value={"pruebaGetError"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaListDTO pruebaGetError( HttpServletResponse response ){
		log.info("pruebaGetError [Controller]");
		RespuestaNoticiaListDTO respuestaDTO = new RespuestaNoticiaListDTO();
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		try {
			Integer.parseInt("ola veda");
		} catch (Exception e) {
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			respuestaDTO.setListaNotas(null);
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setCausa_error(e.getCause() == null?e.getMessage():e.getCause().toString());
			respuestaDTO.setListaNotas(null);
			log.error("Ya seteamos todo, ahora un return");
		}
		response.setHeader("codigo2", codigo);
		response.setHeader("mensaje2", msj);
		response.setHeader("causa_error2", causa_error);
		response.setStatus(status_peticion);
		return respuestaDTO;
	}
	
	@RequestMapping(value={"obtieneMagazine"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaNoticiaListDTO obtieneMagazine( HttpServletResponse response ){
		RespuestaNoticiaListDTO respuestaDTO = new RespuestaNoticiaListDTO();
		log.info("obtieneMagazine [Controller]");
		try{
			ArrayList<NoticiaListDTO> list=this.magazineAppDAO.obtieneMagazine( );
			respuestaDTO.setListaNotas(list);
			respuestaDTO.setCodigo("0");
			respuestaDTO.setMensaje("OK");
			respuestaDTO.setListaNotas(this.magazineAppDAO.obtieneMagazine( ));
			respuestaDTO.setCausa_error("");
		} catch (Exception e){
			log.error(" Error obtieneMagazine [Controller]:", e);
			respuestaDTO.setListaNotas(null);
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setListaNotas(null);
		}
		return respuestaDTO;
	}
	
	
	/**
	 * @return the magazineAppDAO
	 */
	public IMagazineAppDAO getMagazineAppDAO() {
		return magazineAppDAO;
	}
	/**
	 * @param magazineAppDAO the magazineAppDAO to set
	 */
	@Autowired
	public void setMagazineAppDAO(IMagazineAppDAO magazineAppDAO) {
		this.magazineAppDAO = magazineAppDAO;
	}
	
	
}
