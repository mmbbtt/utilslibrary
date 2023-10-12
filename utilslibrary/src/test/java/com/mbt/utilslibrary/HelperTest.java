package com.mbt.utilslibrary;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Test;


public class HelperTest 
{
	
	@Test
	public void stringIsNullOrEmptyTest() 
	{
		String sNull = null;
		String sEmpty = "";
		String sNotEmpty = " ";
		
		assertTrue(
			   Helper.stringIsNullOrEmpty(sNull)
			&& Helper.stringIsNullOrEmpty(sEmpty)
			&& !Helper.stringIsNullOrEmpty(sNotEmpty)
			);
	}
	
	@Test
	public void getDefaultDatePatternTest()
	{
		assertEquals(Helper.getDefaultDatePattern(), "dd/MM/yyyy");
	}
	
	@Test
	public void string2DateTest() 
	{
		LocalDate date = null;
		
		try 
		{
			date = Helper.string2Date("21/12/2023");
		} 
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
		
		assertTrue(date != null);
	}
	
	

}
