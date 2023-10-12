package com.mbt.utilslibrary;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Clase para manejar los errores de negocio (reglas de negocio incumplidas).
 * Extiende a una RuntimeException, añadiendo:
 * - Una clave de tipo String, usada para obtener el mensaje de la expeción de un archivo propeties con los mensajes internacinalizados.
 * - Colección de objetos a pasar como argumentos a un ResourceBlunder para obtener el mensaje internacionalizado de la excepción.
 * 
 */
public class BussinesException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private String userMessageKey = null;
	private List<Object> userMessageArguments = new ArrayList<Object>();
	
	/**
	 * Clave de la excepción en el archivo properties de mensajes internacionalizados.
	 * @return
	 */
	public String getUserMessageKey() { return this.userMessageKey; }
	
	/**
	 * Número de argumentos requeridos por el mensaje internacionalizado.
	 * @return
	 */
	public int getUserMessageArgumentsCount()
	{
		return this.userMessageArguments.size();
	}
	
	/**
	 * Arragy de argumentos para el mensaje internacionalizado.
	 * @return
	 */
	public Object[] getUserMessageArguments()
	{
		return this.userMessageArguments.toArray();
	}
	
	/**
	 * Añade un argumento para el mensaje internacionalizado.
	 * @param userMessageArgument
	 * @return
	 */
	public int addUserMessageArgument(Object userMessageArgument)
	{
		int index = -1;
		
		if(userMessageArgument != null)
		{
			this.userMessageArguments.add(userMessageArgument);
		}
		
		return index;
	}
	
	//-> Constructores
	
	/**
	 * Constructor para crear una BussinesException a partir de la excepción origen, que va a tener un mensaje internacionalizado.
	 * 
	 * @param errorMessage	Descripción del error.
	 * @param innerException Excepción origen del error.
	 * @param userMessageKey Clave de la excepción en el archivo properties de mensajes internacionalizados.
	 */
	public BussinesException(String errorMessage, Exception innerException, String userMessageKey) 
	{
		super(errorMessage, innerException);
		
		this.userMessageKey = userMessageKey;
	}
	
	/**
	 * Constructor para crear una BussinesException a partir de la excepción origen.
	 * 
	 * @param errorMessage Descripción del error.
	 * @param innerException Excepción origen del error.
	 */
	public BussinesException(String errorMessage, Exception innerException)
	{
		super(errorMessage, innerException);
		
		this.userMessageKey = null;
	}
	
	/**
	 * Constructor para crear una BussinesException que va a tener un mensaje internacionalizado.
	 * 
	 * @param errorMessage Descripción del error.
	 * @param userMessageKey Clave de la excepción en el archivo properties de mensajes internacionalizados.
	 */
	public BussinesException(String errorMessage, String userMessageKey) 
	{
		super(errorMessage);
		
		this.userMessageKey = userMessageKey;
	}
	
	/**
	 * Constructor para crear una BussinesException sin mensaje internacionalizado.
	 * 
	 * @param errorMessage Descripción del error.
	 */
	public BussinesException(String errorMessage)
	{
		super(errorMessage);
		
		this.userMessageKey = null;
	}
	
	//<-
	
	/**
	 * Devuevle una cadena de texto con el mensaje de error localizado de la BussinesException.
	 * 
	 * El mensaje se extrae del bundleReader pasado por argumento.
	 * En el caso de no existir una entrada en dicho archivo con clave igual a la de la BussinesException, devuelve el Exception.Message
	 * 
	 * @param bussinesException BussinesException de la cual se va a extrarer el mensaje de error.
	 * @param bundleReader ResourceBundleReader incializado con el archivo de propiedades con los mensajes internacionalizados
	 * 
	 * @return
	 */
	public static String getLocalUserMessage(BussinesException bussinesException, ResourceBundleReader bundleReader)
	{	
		String lum = bundleReader.getLocalizedMessage(
			 bussinesException.userMessageKey
			,bussinesException.getUserMessageArguments()
			);
		
		if(Helper.stringIsNullOrEmpty(lum))
		{
			lum = bussinesException.getMessage();
			
			Logger logger = LogManager.getLogger(BussinesException.class);
			logger.error(String.format(
					"Error en getLocalUserMessage(): El archivo de propiedades %s no contiene la clave %s"
					,bundleReader.getName()
					,bussinesException.getUserMessageKey()
					));
		}
		
		return lum;
	}
}
