package com.mbt.utilslibrary.consolemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.mbt.utilslibrary.BussinesException;
import com.mbt.utilslibrary.EResult;
import com.mbt.utilslibrary.GenericActionResult;
import com.mbt.utilslibrary.Helper;

/**
 * Clase para implentar menús de consola.
 * 
 * Por ejemplo, para un menú como el siguiente:
 * 	Archivo
 * 	(1) Nuevo
 *  (2) Abrir
 *  Introduce una opación (s) para salir:
 *  
 */
public class Menu 
{
	Map<Integer, MenuItem> menuItems = new TreeMap<Integer, MenuItem>();  //Items del menú

	private String name; //Nombre o título del menú
	private List<String> exitKeys = new ArrayList<String>(); //Conjunto de teclas que se interpretan como la orden salir del menú (p.e: s, S)
	private String inputText; // Texto a mostrar para pedir al usuario que seleccione una opción del menú (p.e:"Introduce una opación (s) para salir")
	private static Scanner scanner = null;	//Scanner que se va a usar para obtener las entradas por consola.
	
	public String getName() { return name; }
	public Scanner getScanner() { return scanner; }
	
	/**
	 * Constructor obligatorio.
	 * 
	 * Inicialza el java.util.Scanner que se va a usar para las entradas desde consola.
	 *  
	 * @param name Nombre del menú a mostrar antes de las opciones de menú (p.e: "Archivo")
	 * @param exitKeys Teclas o texto como selección para salir/finalizar (p.e: s, S)
	 * @param inputText Texto a mostrar para pedir al usuario que seleccione una opción (p.e:"Introduce una opación (s) para salir")
	 */
	public Menu (String name, List<String> exitKeys, String inputText)
	{
		this.name = name;
		this.exitKeys = exitKeys;
		this.inputText = inputText;
		
		scanner =  new Scanner(System.in);
	}
	
	/**
	 * Añade un item de menú al menú.
	 * 
	 * @param position Posición del item en el menú, que además será la clave del item en el menú (la tecla que hay que pulsar para seleccionarlo).
	 * @param menuItem Nombre del Item. El que se mostrará en el menú.
	 */
	public void addMenuItem(Integer position, MenuItem menuItem)
	{
		this.menuItems.put(position, menuItem);
	}

	/**
	 * Devuelve true si la key pasada por argumento es una clave de salida del menú.
	 * 
	 * @param key
	 * @return
	 */
	protected boolean isExitKey(String key) 
	{
		if(exitKeys.contains(key))
			return true;
		else 
			return false;
	}
	
	/**
	 * Devuelve true, si el número recogido por consola se corresponde a una opación del menú (si es la clave
	 * de un MenuIten del menú).
	 * 
	 * @param key
	 * @return
	 */
	protected boolean isValidOption(Integer key)
	{
		if(this.menuItems.containsKey(key))
			return true;
		else
			return false;
				
	}
	
	/**
	 * Devuelve true, si el texto recogido por consola se corresponde a una opación del menú (si es la clave
	 * de un MenuIten del menú).
	 * 
	 * @param key
	 * @return
	 */
	protected boolean isValidOption(String key)
	{
		boolean isValid = false;
		
		try
		{
			int iKey = Integer.parseInt(key);
			isValid = isValidOption(iKey);
		}
		catch(NumberFormatException e)
		{
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Hace un borrado de la consoal.
	 * 
	 */
	protected final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");
	       
	        if (os.contains("Windows"))
	        {
	        	String[] cmdarray = {"cls"};
	            Runtime.getRuntime().exec(cmdarray);
	        }
	        else
	        {
	        	String[] cmdarray = {"clear"};
	            Runtime.getRuntime().exec(cmdarray);
	        }
	    }
	    catch (final Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Visualiza el menú en la consola.
	 * 
	 */
	protected void printMenu()
	{
		if(!Helper.stringIsNullOrEmpty(this.name))
		{
			System.out.printf("=== %s ===\n", this.name);
		}
		
		for(Integer i : this.menuItems.keySet())
		{
			System.out.printf(" [%d] %s \n", i, this.menuItems.get(i).getName());
		}
		
		System.out.printf("%s: ", this.inputText);
	}
	
	/**
	 * Muestra por consola el menú y espera por la selección del usuario.
	 * Una vez el usuario introduce una opción válida, ejectua el comando asociado a la misma.
	 * Devuelve el resultado de la ejecución del comando.
	 *
	 * @return
	 */
	public GenericActionResult<String> run()
	{
		GenericActionResult<String>  result = new GenericActionResult<String> ();
		
		try 
		{
			String choice= null;
			
			do
			{
				//Limpiar consola
				Menu.clearConsole();
				
				//Mostrar el menú
				this.printMenu();
				
				//Recoger entrada
				choice = getScanner().nextLine();

			}
			while(
				   !this.isValidOption(choice)
				&& !this.isExitKey(choice)
				);
			
			if(this.isExitKey(choice))
			{
				result.setActionResult(EResult.OK);
				result.setResultObject(choice);
			}
			else
			{
				MenuItem selectedItem = this.menuItems.get(Integer.parseInt(choice));
				
				result = selectedItem.onItemSelectedHandler();
			}
		} 
		catch (Exception e)
		{
			result.setActionResult(EResult.KO);
			
			BussinesException be = new BussinesException(
				 "Se ha producido un error inesperado al ejecutar Menu.run()"
				,e
				,"InternalError"
				);
			be.addUserMessageArgument(String.format(
					"Menu.run(): %s"
					,e.getMessage())
					);
			result.addException(be);
			
			result.addException(be);
		}
		
		return result;
	}
}
