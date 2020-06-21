package com.bukapi.sf;
import java.util.*;
import org.codehaus.jettison.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import com.sforce.soap.partner.*;
import com.sforce.ws.ConnectionException;


public class QueryGenerator {
	PartnerConnection connection;
	String sObject;
	List<String> columnList = new ArrayList<String>();
	String filterCondition;
	String LastQuery;
	String metaInfo;
	String st = "Id";
public 	QueryGenerator(PartnerConnection connection,String sObject) throws ConnectionException,JSONException
{
	this.connection = connection;
	this.sObject = sObject;
	doinit();	
}



public String getQuery() throws ConnectionException 
{
	LastQuery = " SELECT " + st + " FROM " + sObject;
//	LastQuery = " SELECT " + () + " FROM " + sObject;
	
	return LastQuery;


}





public void doinit() throws ConnectionException,JSONException 
{
  columnList = getColumnFromSF(sObject);
}


public List<String> getColumnFromSF(String sObject) throws ConnectionException,JSONException
{
	List<String> columns = new ArrayList<String>();
	DescribeSObjectResult result = connection.describeSObject(sObject);
	Field[] fields =  result.getFields();
	JSONObject metaobject = new JSONObject();
	
	for (int i = 0;i<fields.length;i++)
	{
		if(fields[i].getType() != FieldType.address  && fields[i].getType() != FieldType.location )
		{
			metaobject.put(fields[i].getName(), fields[i].getType());
			columns.add(fields[i].getName());
			
		 if ( i > 1) {
			st =  st + "," + fields[i].getName();
		
		 }
		} 
		else
		{
			System.out.println("Discarding Address and Location FIelds:");
		}
	}
	
	this.metaInfo = st.toString();
	
	//this.metaInfo = st;
	System.out.println(" Meta " + st);
	
	return columns;
}


public String getMetaString()
{


	return this.metaInfo;


}
	


}
