package com.mbt.utilslibrary;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



/**
 * Clase de utilidad para leer archivos de propiedades (clave=valor)
 * 
 * Usa java.util.Properties para leer las propiedades del fichero y, a medida que se consultan,
 * las almacena en una caché estática, de forma que la segunda vez que se pida una propiedad, 
 * ésta será devuelta de la caché y no del fichero.
 * 
 */
public class PropertiesFileReader 
{
	private static final Logger logger = LogManager.getLogger(PropertiesFileReader.class);
	private Properties pf = new Properties();
	private String namePropertiesFile;
	private static Map<String, String> propertiesCache = new HashMap<String, String>();

	/**
	 * Devuelve el nombre del archivo de propiedades con el que se ha inicializado la instancia de esta clase.
	 * 
	 * @return
	 */
	public String getNamePropertiesFile(){ return namePropertiesFile; }
	
	/**
	 * Constructor obligatorio.
	 * 
	 * @param nameOfPropertiesFile Nombre del archivo de propiedades a leer.
	 * @throws IOException Cuando no encuetra el fichero (en el classpath)
	 */
	public PropertiesFileReader(String nameOfPropertiesFile) throws IOException
	{
		logger.debug(String.format("Leyendo el fichero de propiedades %s ...", nameOfPropertiesFile));
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream isProp = loader.getResourceAsStream(nameOfPropertiesFile);
		if(isProp == null)
		{
			throw new IOException(String.format("El fichero %s no existe", nameOfPropertiesFile));
		}
		pf.load(isProp);
		
		logger.debug("Fichero de propiedades leido.");
	}

	/**
	 * Limpia los recursos internos usados por el objeto.
	 */
	public void dispose()
	{
		propertiesCache.clear();
		propertiesCache = null;
		pf.clear();
		pf = null;
	}
	
	/**
	 * Devuelve el valor de la propiedad con clave = name.
	 * Si el archivo de propiedades no contiene una entrada para la clave pasada por argumento, 
	 * devuelve null.
	 * 
	 * @param name Clave de la propiedad
	 * @return
	 */
	public String getProperty(String name) 
	{
		String value = null;
		
		if(!propertiesCache.containsKey(name))
		{
			if(pf.containsKey(name))
			{
				value = pf.getProperty(name);
				propertiesCache.put(name, value);
			}
		}
		else
		{
			value = propertiesCache.get(name);
		}
		
		return value;
	}
	
	/**
	 * Devuelve el valor de la propiedad con clave = name.
	 * En el caso de que la clave no exista en el archivo de propiedades, devuelve el valor por defecto pasado por argumento.
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String name, String defaultValue) 
	{
		String value = null;
		
		value = this.getProperty(name);
		
		if(Helper.stringIsNullOrEmpty(value))
		{
			value = defaultValue;
		}
		
		return value;
	}
	
	public void clearCache()
	{
		propertiesCache.clear();
	}
}
