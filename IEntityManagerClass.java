import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.lang.reflect.Type;
import java.nio.file.StandardOpenOption;

import com.sun.org.apache.bcel.internal.classfile.Field;



public class IEntityManagerClass<T> implements IEntityManager<T> {

	private Class<T> type;
	
	public IEntityManagerClass(T objectInstance){//Object value = field.get(objectInstance);
		
	}
	
	public <T> IEntityManagerClass(){
		this.type = T;
	}
	public IEntityManagerClass(Collection<?> c){		
	}
/*	public Class<T> getTypeParameterClass()
    {
       Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
        
    }
*/	
	
	@Override
	public void persist(T entity)  {
		// TODO Auto-generated method stub
		File file = new File("SQLGenerator.sql");

		StringBuilder sb = new StringBuilder();
	    Class<?> thisClass = null;
	    try {
	    	file.createNewFile();
			FileWriter writer = new FileWriter(file,true); 
			
	        thisClass = Class.forName(entity.getClass().getName());

	        java.lang.reflect.Field[] aClassFields = thisClass.getDeclaredFields();
	        sb.append("INSERT INTO " + entity.getClass().getSimpleName() + " VALUES(");
	        System.out.println(sb);
	        for(java.lang.reflect.Field f : aClassFields){
	            			//System.out.println("Field: " + fName);
	            			//sb.append("(" + f.getType() + ") " + fName + " = " + f.get(entity) + ", ");
	           
	            if(f.get(entity)!= null) 
	            {
	            	System.out.println(f.getType().getSimpleName());
		            if(f.getType().getSimpleName().equals("String"))
	            	{
		            	if(f==aClassFields[aClassFields.length-1]) sb.append(f.get(entity));
		            	else  sb.append("\'" + f.get(entity) + "\'"   + ", ");
	            	}// in case Else it because we will reference to other tables -> the id of those tables => these will be Integer
		            else if(f.getType().getSimpleName().equals("Integer"))
		            {
		            	if(f==aClassFields[aClassFields.length-1]) sb.append(f.get(entity));
		            	else sb.append(f.get(entity) + ", ");
		            }else
		            {
		            //	Class<?> thisClass2 = Class.forName(f.getName());
		    	        java.lang.reflect.Field[] aClassFields2 = f.getType().getDeclaredFields();
		    	        for(java.lang.reflect.Field f2 : aClassFields2)
		    	        	if(f2.getName().equals("id"))
		    	        		sb.append(f2.get(f.get(entity)));
		            }
				      //for(String f: lines)
				    
	            }
	            else sb.append("NULL");
	      	  
	        }
	        sb.append(");");
	        writer.write("\n" + sb.toString());
	        writer.flush();
	        writer.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    System.out.println(sb.toString());		
	/*
	    switch (NameOfTable) {
		case "Book":
			Insertion +=  "INSERT INTO "+ NameOfTable +" VALUES (";
			System.out.println(Insertion);
			break;
		case "Publisher":
		default:
			break;
		}
		*/
	}
	@Override
	public void remove(T entity) {
		// DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
	    Class<?> thisClass = null;
	    File file = new File("SQLGenerator.sql");
	    
	    try {
	    	file.createNewFile();
			FileWriter writer = new FileWriter(file,true); 
	        thisClass = Class.forName(entity.getClass().getName());
	        if(thisClass.getName().equals("Book"))
	        {
	        	java.lang.reflect.Field[] aClassFields = thisClass.getDeclaredFields();
		        sb.append("DELETE FROM " + entity.getClass().getSimpleName().toLowerCase() + " WHERE ");
		        System.out.println(sb);
		        for(java.lang.reflect.Field f : aClassFields)
		        	 if(f.get(entity)!= null && f.getName().equals("id")) 
		        		 sb.append(f.getName() + " = " + f.get(entity));
			         else continue;
		        sb.append(";");
		        writer.write("\n" + sb.toString());
		        writer.flush();
		        writer.close();	
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println(sb.toString());
	}
	@Override
	public T find(Object primaryKey) {
		// TODO Auto-generated method stub
		File file = new File("SQLGenerator.sql");
		
		System.out.println(this.getClass());
		Type genericType = this.getClass().getGenericSuperclass();
		System.out.println(genericType);          

		
	//	System.out.println(this.getTypeParameterClass());
			
	
		StringBuilder sb = new StringBuilder();
	    Class<?> thisClass = null;
	  //   thisMethod = null;
	    try {
	    	file.createNewFile();
			FileWriter writer = new FileWriter(file,true); 
			
			
			parameterized t1 = this.getClass().
			
	        //thisClass = Class.forName(this.getClass().getName());
	        Method ListMethod[] = this.getClass().getMethods();	
	        Method thisMethod = null;
	        for(Method M: ListMethod)
	        	if (M.getName().equals("find"))
	        		thisMethod = M;
	        System.out.println(thisMethod);
	        ParameterizedType returnType = null;//this.getClass().getGenericSuperclass();
	        	System.out.println(returnType);
	        
	        //ParameterizedType myListType = ((ParameterizedType) this.class.getDeclaredField("find".getGenericType());
	        //java.lang.reflect.Field[] aClassFields = thisMethod.getClass().getDeclaredFields();
	 //      sb.append("SELECT * FROM " + returnType.getClass().getSimpleName() + " WHERE ");
	        System.out.println(sb);
	  /*      for(java.lang.reflect.Field f : aClassFields){
	            			//System.out.println("Field: " + fName);
	            			//sb.append("(" + f.getType() + ") " + fName + " = " + f.get(entity) + ", ");
	           
	            if(f.get(entity)!= null) 
	            {
	            	System.out.println(f.getType().getSimpleName());
		            if(f.getType().getSimpleName().equals("String"))
	            	{
		            	if(f==aClassFields[aClassFields.length-1]) sb.append(f.get(entity));
		            	else  sb.append("\'" + f.get(entity) + "\'"   + ", ");
	            	}// in case Else it because we will reference to other tables -> the id of those tables => these will be Integer
		            else if(f.getType().getSimpleName().equals("Integer"))
		            {
		            	if(f==aClassFields[aClassFields.length-1]) sb.append(f.get(entity));
		            	else sb.append(f.get(entity) + ", ");
		            }else
		            {
		            //	Class<?> thisClass2 = Class.forName(f.getName());
		    	        java.lang.reflect.Field[] aClassFields2 = f.getType().getDeclaredFields();
		    	        for(java.lang.reflect.Field f2 : aClassFields2)
		    	        	if(f2.getName().equals("id"))
		    	        		sb.append(f2.get(f.get(entity)));
		            }
				      //for(String f: lines)
				    
	            }
	            else sb.append("NULL");
	      	  
	        }*/
	        sb.append(");");
	        writer.write("\n" + sb.toString());
	        writer.flush();
	        writer.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    System.out.println(sb.toString());	
	    return null;
	}
}
