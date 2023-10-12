package com.mbt.utilslibrary;

/**
 * Interfaz para validadores de objetos de negocio
 * 
 * @param <E> Tipo del objeto de negocio a validar
 */
public interface BussinesObjectsValidator<E>
{
	/**
	 * Valida que el objeto pasado por argumento cumple las reglas de negocio.
	 * Devuelve un ActionResult con resultado OK o KO según cumpla o no las reglas.
	 * Y, en caso de no cumplier las reglas, contiene las BussinesExceptions que no cumple.
	 * 
	 * @param objectToValidate Objeto a validar.
	 * @return 
	 */
	ActionResult validate(E objectToValidate);
}
