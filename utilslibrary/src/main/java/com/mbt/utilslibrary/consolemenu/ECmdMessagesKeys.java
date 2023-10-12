package com.mbt.utilslibrary.consolemenu;

public enum ECmdMessagesKeys 
{
	InternalError("InternalError"),
	NullParameter("NullParameter"),
	IncorrectParameter("IncorrectParameter"),
	IncorrectNumberOfParameters("IncorrectNumberOfParameters"),
	PropertyNotFound("PropertyNotFound"),
	EmptyProperty("EmptyProperty"),
	IncorrectValueOfPropery("IncorrectValueOfPropery"),
	IndexOfcolumnNotFound("IndexOfcolumnNotFound"),
	IncorrectFormatOfColumn("IncorrectFormatOfColumn"),
	EmptyColumn("EmptyColumn"),
	ReadColumnInEmptyRow("ReadColumnInEmptyRow"),
	IdenxOfColumnOutOfRange("IdenxOfUniversalSupplyPointCodeOutOfRange"),
	ValueLessThanMin("ValueLessThanMin"),
	ValueGreaterThanMax("ValueGreaterThanMax"),
	ValueNotValidFor("ValueNotValidFor"),
	FileNotExists("FileNotExists"),
	MandatoryFieldEmpty("MandatoryFieldEmpty"),
	BillGenerateOk("BillGenerateOk"),
	BillGenerateKo("BillGenerateKo"),
	CsvImportKo("CsvImportKo"),
	RowsAffected("RowsAffected");
	
	public final String stringValue;
	
	private ECmdMessagesKeys(String stringValue)
	{
		this.stringValue = stringValue;
	}
}
