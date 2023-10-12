package com.mbt.utilslibrary;

/**
 * Clase para manejar el resultado de una acción (la ejecución de un método), que devuelve un objeto del tipo E.
 * Extiende a un ActionResult añadiendo el objeto devuelto por la acción.
 * 
 * @param <E>
 */
public class GenericActionResult <E>  extends ActionResult 
{
	protected E resultObject = null;
	
	/**
	 * Devuelve el objeto resultado tras la ejecución de la acción.
	 * 
	 * @return
	 */
	public E getResultObject() 
	{
		return resultObject;
	}

	/**
	 * Establece el objeto resultado tras la ejecución de la acción.
	 * @param resultObject
	 */
	public void setResultObject(E resultObject) 
	{
		this.resultObject = resultObject;
	}
}
