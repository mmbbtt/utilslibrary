package com.mbt.utilslibrary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Factoría de objetos PropertiesFileReader.
 * 
 */
public class PropertiesFileReaderFactory 
{
	private static final Logger logger = LogManager.getLogger(PropertiesFileReaderFactory.class);
	
	private static Map<String, PropertiesFileReader> propertiesFileReaders = new HashMap<String, PropertiesFileReader>();
	
	/**
	 * Devuelve un objeto PropertiesFileReader inicializado para leer el archivo de propiedades con nombre igual al pasado por argumento.
	 * La primera vez que se invoca este métido, crea el objeto PropertiesFileReader y lo almacena en una caché estática.
	 * Las invocaciones posteriore devuelven el objeto de la caché, inicializado en la primera llamada.
	 * 
	 * @param fileNme
	 * @return
	 */
	public static PropertiesFileReader getPropertiesFileReader(String fileNme) throws IOException
	{
		PropertiesFileReader pfr = null;
		
		if(!propertiesFileReaders.containsKey(fileNme))
		{
			try 
			{
				pfr = new PropertiesFileReader(fileNme);
			} 
			catch (IOException e) 
			{
				logger.error(
					String.format("Error en getPropertiesFileReader(): %s", e.getMessage())
					,e
					);
				
				throw e;
			}
			
			propertiesFileReaders.put(fileNme, pfr);
		}
		
		return propertiesFileReaders.get(fileNme);
	}
}
