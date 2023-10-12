package com.mbt.utilslibrary;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Clase con métodos útiles generales.
 * 
 */
public final class Helper 
{
	protected static Logger logger = LogManager.getLogger(Helper.class);
	protected static String defaultDatePattern = null;
	
	/**
	 * Devuevle el patrón por defecto que usan los métodos de esta clase para formatear fechas.
	 * Si no está establecido, lo inicializa leyendo su valor del archivo de propiedades utilslibrary.properties.
	 * 
	 * @return
	 */
	public static String getDefaultDatePattern()
 	{
 		if(Helper.defaultDatePattern == null)
 		{
 			Helper.defaultDatePattern = "dd/MM/yyyy";
 			
 			PropertiesFileReader pfr;
 			
			try 
			{
				pfr = PropertiesFileReaderFactory.getPropertiesFileReader("utilslibrary.properties");
				Helper.defaultDatePattern = pfr.getProperty("DefaultDatePattern");
			} 
			catch (IOException e) 
			{
				logger.debug("El método getDefaultDatePattern() intentó obtener el patrón por defecto para el formateo de fechas de archivo de propiedades utilslibrary.properties, pero este no existe. Se estaclece el patrón por defecto a dd/MM/yyyy.");
			}
 		}
 		
 		return defaultDatePattern;
 	}
	
	/**
	 * Establece el patrón por defecto que usan los métodos de esta clase para formatear fechas.
	 * @param defaultDatePattern
	 */
	public static void setDefaultDatePattern(String defaultDatePattern) 
	{
		Helper.defaultDatePattern = defaultDatePattern;
	}

	/**
	 * Constructor por defecto.
	 */
	private Helper() {}
	
	/**
	 * Devuelve true si la cadena pasada por argumento es nula o está vacía.
	 * 
	 * @param s
	 * @return
	 */
 	public static boolean stringIsNullOrEmpty(String s)
	{
		if(s == null)
			return true;
		else if(s == "")
			return true;
		else
			return false;
	}
 	
 	
	/**
	 * Convierte a un LocalDate el texto pasado por argumento, usando el patrón de formato de fechas pasado por argumento.
	 * Si se pasa un patrón de fechas nulo, usa el patrón por defecto de formateo de fechas de esta clase.
	 * 
	 * @param sDate
	 * @param sDatePattern
	 * @return
	 */
	public static LocalDate string2Date(String sDate, String sDatePattern) 
	{
		String datePattern = sDatePattern;
		
		if(Helper.stringIsNullOrEmpty(datePattern))
		{
			datePattern = Helper.getDefaultDatePattern();
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);

		return LocalDate.parse(sDate, dtf);
	}
	
	/**
	 * Convierte a un LocalDate el texto pasado por argumento, usando el patrón por defecto de formateo de fechas de esta clase.
	 * @param sDate
	 * @return
	 */
	public static LocalDate string2Date(String sDate)
	{
		return Helper.string2Date(sDate, null);
	}
	
}
