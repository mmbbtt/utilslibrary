package com.mbt.utilslibrary;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Factoría de objetos ResourceBundleReader.
 * 
 */
public class ResourceBundleReaderFactory 
{
	private static final Logger logger = LogManager.getLogger(ResourceBundleReaderFactory.class);
	
	private static Map<String, ResourceBundleReader> resourceBundleReaders = new HashMap<String, ResourceBundleReader>();
	
	/**
	 * Devuelve el ResourceBundleReader de nombre baseBundleName y Locale locale.
	 * 
	 * Si es la primera vez que se invoca este método para un baseBundleName y locale específicos, 
	 * inicializa un objeto ResourceBundleReader para ese baseBundleName y locale.
	 * Debe existir un archivo de recursos de nombre baseBundleName_<locale.toString()>.properties en el clashpath
	 * 
	 * @param baseBundleName
	 * @param locale
	 * @return
	 * @throws NullPointerException Cuando baseBundleName y/o locale es nulo.
	 * @throws MissingResourceException Cuando no existe el archivo de recursos.
	 */
	public static ResourceBundleReader getResourceBundleReader(String baseBundleName, Locale locale) throws NullPointerException, MissingResourceException
	{
		String bundleKey = ResourceBundleReader.getBundleKey(baseBundleName, locale);
		
		if(!resourceBundleReaders.containsKey(bundleKey))
		{
			ResourceBundle bundle = null;
			
			try
			{
				bundle = ResourceBundle.getBundle(baseBundleName, locale);
			}
			catch(NullPointerException npe)
			{
				logger.error("Error en getResourceBundleReader(): baseBundleName y/o locale nulos.", npe);
				
				throw npe;
			}
			catch(MissingResourceException  mre)
			{
				logger.error(String.format(
					"Error en getResourceBundleReader(): El archivo de recursos %s_%s.properties no existe"
					,baseBundleName
					,locale.toString()
					)
					, mre);
				
				throw mre;
			}
			
			ResourceBundleReader resourceBundleReader = new ResourceBundleReader(bundle);
			resourceBundleReaders.put(bundleKey, resourceBundleReader);
		}
		
		return resourceBundleReaders.get(bundleKey);
	}
}
