package com.mbt.utilslibrary.consolemenu;

import java.util.List;
import java.util.Scanner;

import com.mbt.utilslibrary.ActionResult;
import com.mbt.utilslibrary.GenericActionResult;

/**
 * Clase base para implementar el comando a ejecutar por un MenuItem cuando este es seleccionado.
 * 
 * @param <T>
 * 
 */
public abstract class  Command
{
	protected static Scanner scanner = null;
	
	/**
	 * Constructor obligatorio. 
	 * Requiere pasarle como argumento el java.util.Scanner a usar para la entrada de datos desde la consola.
	 * 
	 * @param inputScanner
	 */
	public Command(Scanner inputScanner)
	{
		scanner = inputScanner;
	}
	
	/**
	 * Devuelve en un String los valores introducidos por consola hasta que se pulsa el salto de línea.
	 * @return
	 */
	protected String readInputLine()
	{
		String line = null;
		
		do
		{
			line = scanner.nextLine();
		}
		while(line == null);
		
		return line;		
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
 	protected GenericActionResult<String> selectItemList(List<String> listItemsToSelect, String caption, List<String> exitKeys, String inputText)
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
		
	/**
	 * Método que será invodado desde el MenuItem al cual pertenece cuando este sea seleccionado.
	 * 
	 * @return
	 */
	public abstract ActionResult  execute();
}
