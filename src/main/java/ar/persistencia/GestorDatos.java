package ar.persistencia;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ar.dominio.Articulo;



public class GestorDatos {
	private GestorDatos() {
		repositorioArticulos=cargarArticulos();
		}
	private static GestorDatos GD=null;
	
	private Map<String , Articulo> repositorioArticulos = null;//cargarArticulos();
	
	public static synchronized GestorDatos getInstance() {
		if (GD==null) 
			GD=new GestorDatos();
		return GD; 
	}
	
	
	public static Map<String , Articulo> cargarArticulos() {
		
		Map<String , Articulo> articulosTest=new TreeMap();
		
		articulosTest.put("C-100P", new Articulo("C-100P", "Cuaderno Profesional", "100 hojas, carta", new BigDecimal("35.20"),new BigDecimal("18.90"), 20));
		articulosTest.put("C-200P", new Articulo("C-200P", "Cuaderno Profesional", "200 hojas, carta", new BigDecimal("35.20"),new BigDecimal("38.90"), 16));
		articulosTest.put("G-BI", new Articulo("G-BI", "goma de borrar", "lapiz tinta", new BigDecimal("35.20"),new BigDecimal("3.50"), 57));
		char[] categorias_LH_B = new char[]{'1','2','3'};
		articulosTest.put("L-HB", new Articulo("L-HB", "lápiz mediano", "calidad standard", new BigDecimal("35.20"),new BigDecimal("4.90"), 130,categorias_LH_B));

		return articulosTest;
	}
	
	public  Articulo leerArticulo(String clave) {
		return repositorioArticulos.get(clave);
	}
	
	public int getTotArticulos() {
		return repositorioArticulos.size();
	}
	
/*	public Articulo insertarArticulo(Articulo art) {
		return repositorioArticulos.put(art.getClave(), art);
	} */
	public boolean insertarArticulo(Articulo art) {
		/* boolean r=false;
		if (repositorioArticulos.get(art.getClave())==null) { //no existia
			repositorioArticulos.put(art.getClave(), art);
			r=true;
		}
		return r; */
		//o mas facil:
		if (repositorioArticulos.get(art.getClave())==null) { //no existia
			repositorioArticulos.put(art.getClave(), art);
			return true;
		}
		return false;
	}
	
	
	/*
	 * public boolean eliminarArticulo(String cve) {//Articulo
	 * eliminarArticulo(String cve) { //if (repositorioArticulos.get(cve)!=null)
	 * //existe return repositorioArticulos.remove(cve,
	 * repositorioArticulos.get(cve));//remove(cve); //return null; }
	 */
	
	public  Articulo eliminarArticulo(String cve) {
		if (repositorioArticulos.get(cve)!=null)  //existe
			return repositorioArticulos.remove(cve);
		return null;	
	}
	
	public char[] getCategoriasByProductClave(String clvParam){
		Articulo art = repositorioArticulos.get(clvParam); 
		if (art!=null ) { //existe
			return art.getCategorias();
			}
		return null;	
	}
	
	
	public boolean insertCategoriasByProductClave(String clvParam, char[] cats) {
		Articulo art = repositorioArticulos.get(clvParam); 
		if (art!=null ) { //existe
			Articulo artNew=new Articulo(art.getClave(),art.getNombre(),art.getDescripcion(),art.getCosto(),art.getPrecio(),art.getStock(),cats);
			repositorioArticulos.replace(clvParam, artNew);
			return true;
			}
		return false;
	}
	
	//este metodo lo hago para devolver valores para un combo x ej. all arts k-v
	public Map<String, String> getAllArtsClaveValorThisRepositorioMapToObject(){
		Map<String, String> mapResultArtKV = new HashMap<String, String>();
		for (Map.Entry<String, Articulo> entryArtElem:repositorioArticulos.entrySet()) {
			mapResultArtKV.put(entryArtElem.getKey(), entryArtElem.getValue().getNombre()) ;
		}
		return mapResultArtKV;
	}
}
