package ar.web;



import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.internal.JsonWithPaddingInterceptor;

import ar.dominio.Articulo;
import ar.persistencia.GestorDatos;

/*import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;*/

@Path("articulo")
public class ServicioRest {
	
	//GestorDatos gd= new GestorDatos();
	GestorDatos gd= GestorDatos.getInstance();

	/*@GET
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	public String getArticulo(@QueryParam("paramClave") String clave) {
		Articulo artI= gd.leerArticulo(clave);
		if (artI != null) {
			return artI.toString();
		}else {
			return "No existe el articulo indicado";
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=ISO-8859-1")
	public Articulo getArticulo_JSON(@QueryParam("paramClave") String clave) {
		return gd.leerArticulo(clave);
	}
	

	@GET
	@Produces(MediaType.APPLICATION_XML + "; charset=ISO-8859-1")
	public Articulo getArticulo_XML(@QueryParam("paramClave") String clave) {
		return gd.leerArticulo(clave);
	} */
	@GET
	@Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON} )
	public Articulo getArticulo_XML(@DefaultValue("Indefinido ameo")
									@QueryParam("paramClave") String clave
									) {
		if (clave.equals("indefinido ameo"))
			return null;
			
		return gd.leerArticulo(clave);
	} 
	
	@Path("inventario")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public int getTotalArticulos() { //(@QueryParam("paramClave") String clave) {
			
		return gd.getTotArticulos();
	} 

	/*
	 * @Path("{claveArt}")
	 * 
	 * @GET
	 * 
	 * @Produces(value =
	 * {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON} )
	 * public Articulo getArticuloPath(@DefaultValue("Indefinido ameo")
	 * 
	 * @PathParam("claveArt") String clave ) { if (clave.equals("indefinido ameo"))
	 * return null;
	 * 
	 * return gd.leerArticulo(clave); }
	 */
	
	//@Path("{claveArt}")
	@POST
	@Produces(value = MediaType.TEXT_PLAIN)//(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON} )
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String insertarArticulo //(@QueryParam("Art") Articulo artParam ) {
											( @DefaultValue("nulo")
										      @FormParam("code") String claveParam,
										      @DefaultValue("")
										      @FormParam("name") String nombreParam,
										      @DefaultValue("")
										      @FormParam("desc") String descriParam,
										      @DefaultValue("0.0")
										      @FormParam("cost") BigDecimal costoParam
											)  {
			if (claveParam.contentEquals("nulo")) 
				return "clave no especificada";
			
			Articulo art = new Articulo(claveParam,nombreParam,descriParam,costoParam,costoParam,0);
			if (gd.insertarArticulo(art)==false) 
					return "error al insertar el articulo";
			
			return "articulo insertado correctamente";
	} 
	
	// ojo, deben coincidir los nombres de atrib de obj c/ los de params sino no sabe!
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertarArticuloJason (Articulo artParam ) {// (@QueryParam("Art") Articulo artParam ) {

		if (gd.insertarArticulo(artParam)==false) 
			return "error al insertar el articulo";
									
		return "articulo insertado correctamente";
	} 
	
	@DELETE
	@Path("{cveArt}")
	@Produces(MediaType.TEXT_PLAIN)
	public String BORRARArticuloPathJason (@PathParam("cveArt") String cveParam ) {

		Articulo arti = gd.eliminarArticulo(cveParam);
		if (arti!=null) 
			return arti.toString(); 
		//if (gd.eliminarArticulo(cveParam)) return "articulo eliminado correctamente.";
										
		return "error al intentar borrar el articulo. probablemente el articulo no existe.";
	} 
	
	
	/*@POST
	@Produces(value = MediaType.TEXT_PLAIN)//(value = {MediaType.TEXT_XML,MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON} )
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String insertarArticulo //(@QueryParam("Art") Articulo artParam ) {
											( @DefaultValue("nulo")
										      @FormParam("code") String claveParam,
										      @DefaultValue("")
										      @FormParam("name") String nombreParam,
										      @DefaultValue("")
										      @FormParam("desc") String descriParam,
										      @DefaultValue("0.0")
										      @FormParam("cost") BigDecimal costoParam
											)  {
			if (claveParam.contentEquals("nulo")) 
				return "clave no especificada";
			
			Articulo art = new Articulo(claveParam,nombreParam,descriParam,costoParam,costoParam,0);
			if (gd.insertarArticulo(art)==false) 
					return "error al insertar el articulo";
			
			return "articulo insertado correctamente";
	} */
	
	
	@Path("categorias")
	@GET
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	public String getCategorias(@QueryParam("paramClave") String clave) {
	
			char[] arrTmp= gd.getCategoriasByProductClave(clave);
			if (arrTmp!=null )  
				return new String(arrTmp); //arrTmp.toString();
			
		return "No existe el articulo indicado o no existen categorias para dicho articulo";

	}
	
	
	// ojo, deben coincidir los nombres de atrib de obj c/ los de params sino no sabe!
	/*
	 * @Path("inscat")
	 * 
	 * @POST
	 * 
	 * @Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1") public String
	 * insertarCatsByArticuloJason (@FormParam("clabe") String klab) { char[]
	 * cats=new char[] {'a','b'}; if (!gd.insertCategoriasByProductClave(klab,cats))
	 * return "error al insertar el articulo";
	 * 
	 * return "articulo insertado correctamente"; }
	 */
	
		// ojo, deben coincidir los nombres de atrib de obj c/ los de params sino no sabe!
		
		/* Obs: 1) Siempre cualquier array recibirlo como List<String>
		 * Obs: 2) En postman debo poner asi 1-array:a valor 2-array:b 3-array:z
		 * 			( pero postman aveces anda mal o qda mareado, OJO! , reiniciarlo
		 * 			 o probar con 1-array:sin valor , pero es err de postman! )
		 * obs: 3) Debo convert altipo de estructura y/o tipo de datos nec, a mano
		 * obs: 4) un MISMO path puede difererir en verbo http y jersey los reconoce <>s
		 * 			( si quiero otro post get mas para categorias debo modif el path )
		 * */
		@Path("categorias")//("categorias") 
		@POST
		@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		//public String insertarCatsByArticuloJason (@FormParam("clabe") String klab) {
		public String insertarCatsByArticuloJason ( @FormParam("clabe") String klab, 
												   @FormParam("array")  List<String> cats) {//char[] cats ) {
			
			char[] arrTmp = new char[cats.size()];
			int i=0;
			for (String strCat : cats) {
				arrTmp[i]= strCat.charAt(0);
				i++;
			}
			
			if (!gd.insertCategoriasByProductClave(klab,arrTmp))
				return "error al insertar el articulo";
			
			return "articulo insertado correctamente";
		} 
	
		@Path("todos")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String getArticuloDataForComboK_V() {
								//@QueryParam("paramClave") String clave) {
			Map<String, String> allArtsKVMap = gd.getAllArtsClaveValorThisRepositorioMapToObject();

			/* if (artI != null) { return artI.toString(); }else { return
			 * "No existe el articulo indicado"; } */			
			
			return convertMapToJsonArrOfObjsJson(allArtsKVMap);
		}	
		
		//creo un array de n objetos json k-v
		public static String convertMapToJsonArrOfObjsJson(Map<String, String> mapa) {
			JsonObjectBuilder joBuilder;
			JsonArrayBuilder jArrBuilder=Json.createArrayBuilder();
			
			for (Map.Entry<String, String> entryArtElem:mapa.entrySet()) {
				joBuilder = Json.createObjectBuilder();
				joBuilder.add("clave", entryArtElem.getKey());
				joBuilder.add("valor", entryArtElem.getValue());
				jArrBuilder.add(joBuilder.build());
							}
			return jArrBuilder.build().toString();
		}
}
