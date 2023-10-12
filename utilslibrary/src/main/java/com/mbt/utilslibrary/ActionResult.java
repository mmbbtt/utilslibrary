package com.mbt.utilslibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase para manejar el resultado de una acción (la ejecución de un método).
 * 
 */
public class ActionResult 
{
	protected EResult result = EResult.NOT_EXECUTED;
	protected List<BussinesException> exceptions = new ArrayList<BussinesException>();

	/**
	 * Resultado de la ejecución de la acción.
	 * 
	 * @return
	 */
	public EResult getResult() 
	{
		return result;
	}
	
	/**
	 * Número de excepciones que se han producido cuando el resultado es EResult.KO
	 * 
	 * @return
	 */
	public int getExceptionsCount()
	{
		return this.exceptions.size();
	}
	
	/**
	 * Devuelve la excepción almacenada en el índice pasado por argumento.
	 * @param index
	 * @return
	 */
	public BussinesException getException(int index)
	{
		return this.exceptions.get(index);
	}
	
	/**
	 * Devulve un iterador con las excpeciones producidas.
	 * @return
	 */
	public Iterator<BussinesException> getExceptionIterator()
	{
		return this.exceptions.iterator();
	}
	
	/**
	 * Devuelve una cadena de texto con los mensajes de error de las excepciones.
	 * Un línea por cada expeción.
	 * 
	 * @return
	 */
	public String getExceptionsMessages()
	{
		StringBuffer sbMessages = new StringBuffer();
		
		for(BussinesException be : exceptions)
		{
			sbMessages.append(be.getMessage());
			sbMessages.append("\n");
		}
		
		return sbMessages.toString();
	}
	
	/**
	 * Añade una excepción a la lista de excepciones.
	 * 
	 * @param exception
	 * @return
	 */
	public int addException(BussinesException exception)
	{
		int index = -1;
		
		if(exception != null)
		{
			this.exceptions.add(exception);
			index = this.getExceptionsCount() - 1;
		}
		
		return index;
	}
	
	/**
	 * Añade las excpeciones asociadas al ActionResult pasado por argumento.
	 * 
	 * @param otherActionResult
	 * @return
	 */
	public int addExceptionsFrom(ActionResult otherActionResult)
	{
		int count = 0;
		
		if(otherActionResult != null && otherActionResult.getExceptionsCount() > 0)
		{
			for(BussinesException be : otherActionResult.exceptions)
			{
				this.addException(be);
				count ++;
			}
		}
		
		return count;
	}
	
	//-> Constructores
	
	/**
	 * Constructor para crear un objeto ActionResult a partir del resultado y excepción pasadas por argumento.
	 * 
	 * @param result
	 * @param exception
	 */
	public ActionResult(EResult result, BussinesException exception)
	{
		this.result = result;
		this.addException(exception);
	}
	
	/**
	 * Constructor para crear un objeto ActionResult a partir del resultado pasado por argumento.
	 * @param result
	 */
	public ActionResult(EResult result)
	{
		this.result = result;
	}
	
	/**
	 * Constructor por defecto.
	 * 
	 */
	public ActionResult()
	{
		
	}
	
	//<-
	
	/**
	 * Establece el resultado y la excepción del ActionResult.
	 * 
	 * @param result
	 * @param exception
	 */
	public void setActionResult(EResult result, BussinesException exception)
	{
		this.result = result;
		
		if(exception != null)
		{
			this.addException(exception);
		}
	}
	
	/**
	 * Establece el resultado del ActionResult
	 * 
	 * @param result
	 */
	public void setActionResult(EResult result)
	{
		this.result = result;
	}
	
	/**
	 * Establece el resultado y la excepción del ActionResult.
	 *  result = true -> EResult.OK
	 *  result = false -> EResult.KO
	 *  
	 * @param result
	 * @param e
	 */
	public void setActionResult(Boolean result, BussinesException e)
	{
		setActionResult(EResult.Boolean2EResult(result), e);
	}
	
	/**
	 * Establece el resultado.
	 *  result = true -> EResult.OK
	 *  result = false -> EResult.KO
	 *  
	 * @param result
	 * @param e
	 */
	public void setActionResult(Boolean result)
	{
		setActionResult(EResult.Boolean2EResult(result), null);
	}
	
	/**
	 * Devuevle una cadena de texto, en la que hay una línea por excepción asociada 
	 * al actionResult pasado por argumento, con el mensaje de error localizado, extraido del
	 * bundleReader pasado por argumento.
	 * 
	 * @param actionResult 	Action result del cual se van a extrarer los mensajes de error.
	 * @param bundleReader ResourceBundleReader incializado con el archivo de propiedades con los mensajes internacionalizados
	 * 
	 * @return
	 */
	public static String getLocalUserMessage(ActionResult actionResult, ResourceBundleReader bundleReader)
	{
		StringBuilder sbResult = new StringBuilder();
		
		Iterator<BussinesException> it =actionResult.getExceptionIterator();
		while(it.hasNext())
		{
			BussinesException be = it.next();
			sbResult.append(BussinesException.getLocalUserMessage(be, bundleReader));
		}
		
		return sbResult.toString();
	}
}
