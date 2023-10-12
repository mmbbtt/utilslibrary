package com.mbt.utilslibrary.consolemenu;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.mbt.utilslibrary.ActionResult;
import com.mbt.utilslibrary.BussinesException;
import com.mbt.utilslibrary.BussinesObjectsValidator;
import com.mbt.utilslibrary.EResult;
import com.mbt.utilslibrary.GenericActionResult;

/**
 * Clase base para recoger entradas por consola y devolver un objeto T construido a partir de la entrada.
 * 
 * @param <T>
 */
public abstract class ConsoleInput <T>
{
	private static Scanner scanner = null; //Escaner a usara para recoger la entrada por consola.
	
	/**
	 * Constructor obligatorio
	 * 
	 * @param inputScanner Escaner a usara para recoger la entrada por consola.
	 */
	public ConsoleInput(Scanner inputScanner)
	{
		scanner = inputScanner;
	}
	
	/**
	 * Método que crear la instancia del objeto T a partir de la entrada de consola.
	 * 
	 * @param paramsForConstructor Lista de parámetros necesarios para crear un objeto de la clase T
	 * @return Devuelve un objeto de la clase T.
	 */
	protected abstract T getInstanceOfT(String[] paramsForConstructor);
	
	/**
	 * Recoge una entrada de consola (hasta pulsar la tecla enter)
	 * 
	 * @param caption Título que se muestra por consola (p.e: "Introudce la fecha desde")
	 * @param inputValidator (Opcional) BussinesObjectsValidator para validar la entrada de consola..
	 * @return Un GenericActionResult<T> con un objeto T como resultObject, creado a partir de la entrada de consola.
	 */
	public GenericActionResult<T> getBussinesInut(String caption, BussinesObjectsValidator<String> inputValidator)
	{
		GenericActionResult<T> gar = new GenericActionResult<T>();
		
		//Mostrar el caption
		System.out.printf("%s:", caption);
		
		try
		{
			//Recoger entrada
			String input = null;
			do
			{
				input = scanner.nextLine();
			}
			while(input == null);
						
			//Validar entrada
			ActionResult resultValidation = new ActionResult();
			if(inputValidator != null)
			{
				resultValidation = inputValidator.validate(input);
				
				if(resultValidation.getResult() != EResult.OK)
				{
					gar.setActionResult(EResult.KO);
					Iterator<BussinesException> it = resultValidation.getExceptionIterator();
					while(it.hasNext())
					{
						BussinesException be = it.next();
						gar.addException(be);
					}
				}
			}	
			else
			{
				resultValidation.setActionResult(EResult.NOT_EXECUTED);
			}
			
			//Construir un objeto de la clase T
			if(resultValidation.getResult() != EResult.KO)
			{
				String[] inputParts = input.split(" ");
				
				T instanceOfT = this.getInstanceOfT(inputParts);
				
				gar.setActionResult(EResult.OK);
				gar.setResultObject(instanceOfT);
			}
		}
		catch(Exception e)
		{
			gar.setActionResult(EResult.KO);
			BussinesException be = new BussinesException(
				e.getMessage(),
				e,
				ECmdMessagesKeys.InternalError.stringValue
				);
			gar.addException(be);
		}
		
		return gar;
	}
	
	/**
	 * Crea un lista de selección para la consola.
	 * 
	 * Por ejemplo:
	 * 	Colores
	 * 	(1) Amarillo
	 *  (2) Rojo
	 *  (3) Azul
	 *  Seleccione un color (S para salir):
	 * 
	 * @param listItemsToSelect Items de la lista de selección (p.e: Amarillo, Rojo, Azul).
	 * @param caption Subtítulo del la lista de selección. (pe: "Colores")
	 * @param exitKeys Conjunto de teclas que se interpretan como la orden salir de la lista (p.e: S, s)
	 * @param inputText Texto donde se pide seleccionar una opación (p.e: "Seleccione un color (S para salir)").
	 * @return El número correspondiente al item seleccionado.
	 */
	public static GenericActionResult<String> selectItemList(List<String> listItemsToSelect, String caption, List<String> exitKeys, String inputText)
	{
		GenericActionResult<String> garSelectedItem = new GenericActionResult<String>();
		
		//Crear un menú con las entradas de listItemsToSelect
		Menu menuItemList = new Menu(caption, exitKeys, inputText);
		
		for(int i = 1; i <= listItemsToSelect.size(); i++)
		{
			MenuItem mi = new MenuItem(null, listItemsToSelect.get(i - 1), String.valueOf(i));
			menuItemList.addMenuItem(i, mi);
		}
		
		//Recoger el valor seleccionado
		garSelectedItem = menuItemList.run();
		
		return garSelectedItem;
	}
}
