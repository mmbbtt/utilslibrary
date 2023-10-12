package com.mbt.utilslibrary;

/**
 * Enumeración para tipar el resultado de una acción.
 *
 */
public enum EResult 
{
	/**
	 * Acción no ejecutada
	 */
	NOT_EXECUTED,
	/**
	 * Accoión ejecutada correctamente.
	 */
	OK,
	/**
	 * Se han producido errores al ejecutar la acción.
	 */
	KO;
	
	public static EResult Boolean2EResult(Boolean value)
	{
		if(null == value)
			return EResult.NOT_EXECUTED;
		else if(Boolean.TRUE == value)
			return EResult.OK;
		else
			return EResult.KO;
	}
	
	public static Boolean EResult2Boolean(EResult value)
	{
		if(EResult.NOT_EXECUTED == value)
			return null;
		else if(EResult.OK == value)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
}
