package com.mbt.utilslibrary;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase de utilidad para leer archivos de recursos de mensajes localizados.
 * 
 */
public class ResourceBundleReader
{
	private ResourceBundle bundle;
	private MessageFormat formatter;
	private String name;
	
	/**
	 * Devuevle el nombre del archivo de recursos localizados.
	 * 
	 * @return
	 */
	public String getName() { return this.name; }
	
	/**
	 * Constructor obligatorio.
	 * 
	 * @param bundle Archivo de recursos localizado.
	 */
	public ResourceBundleReader(ResourceBundle bundle)
	{
		this.bundle = bundle;
		formatter = new MessageFormat(""); 
		formatter.setLocale(this.bundle.getLocale());
		
		this.name = ResourceBundleReader.getBundleKey(bundle.getBaseBundleName(), bundle.getLocale());
	}
	
	/**
	 * Devuelve el valor asociado a la clave pasada por argumento del archivo de recursos asociado al Reader.
	 * Si se le pasan argumentos, estos se usan para formatear el valor devuelto.
	 * 
	 * @param localizedMessageKey Clave en el archivo de recursos
	 * @param messageArguments	Argumentos del valor asociados a la clave.
	 * @return
	 */
	public String getLocalizedMessage(String localizedMessageKey, Object[] messageArguments)
	{
		String localizedMessage = localizedMessageKey;
		
		localizedMessage = this.bundle.getString(localizedMessageKey);
		
		if((messageArguments != null) && (messageArguments.length > 0))
		{
			this.formatter.applyPattern(localizedMessage);
			localizedMessage = this.formatter.format(messageArguments);
		}

		return localizedMessage;
	}
	
	/**
	 * Devuelve la clave de un objeto ResourceBundleReader con nombre base baseBundleName y Locale locale.
	 * 
	 * La que usa ResourceBundleReaderFactory para los ResourceBundleReader que tiene inicializados.
	 * 
	 * @param baseBundleName
	 * @param locale
	 * @return
	 */
	public static String getBundleKey(String baseBundleName, Locale locale)
	{
		return String.format("%s_%s", baseBundleName, locale.toString());
	}
	
}
