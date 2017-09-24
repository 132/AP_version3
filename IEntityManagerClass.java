import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.TableColumn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.lang.reflect.Type;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.bcel.internal.classfile.Field;



public class IEntityManagerClass<T> implements IEntityManager<T>{

	protected Class<T> type;
	protected int waitPublisher = 0;
	public IEntityManagerClass(Class<?> tem)
	{
		this.type = (Class<T>) tem;
		System.out.println(type.getName() + " Value of Class let check it out");
	}

	public IEntityManagerClass(){		
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
	//	File file = new File("SQLGenerator.sql");
		
	/*	System.out.println(this.getClass());
		Type genericType = this.getClass().getGenericSuperclass();
		System.out.println(genericType);          
		
	    Class<?> thisClass = null;
	    */
	  //   thisMethod = null;
		
		T out = null;
	    try { 
	    	out = type.newInstance();
	    	System.out.println(out.getClass().getSimpleName());
	    	Connection connection = DriverManager.getConnection(SecondMain.url, SecondMain.username, SecondMain.password);
	   // 	file.createNewFile();
		//	FileWriter writer = new FileWriter(file,true); 
		//	thisClass = Class.forName(this.getClass().getName());
			/*
	        //thisClass = Class.forName(this.getClass().getName());
	        Method ListMethod[] = this.getClass().getMethods();	
	        Method thisMethod = null;
	        for(Method M: ListMethod)
	        	if (M.getName().equals("find"))
	        		thisMethod = M;
	        System.out.println(thisMethod);
	        ParameterizedType returnType = null;//this.getClass().getGenericSuperclass();
	        	System.out.println(returnType);
	        	*/
			
			String q = ("SELECT * FROM " + type.getName().toLowerCase() + " WHERE id = " + primaryKey + ";");
			
			System.out.println(q);
			java.sql.PreparedStatement st = connection.prepareStatement(q);
			ResultSet result = st.executeQuery();
			ResultSetMetaData metaData = result.getMetaData();
			if(result.next())
			{
				java.lang.reflect.Field[] ListFields = type.getDeclaredFields();
				System.out.println(type.getName());
				
				int specialPoision = -1;
				int icol = 1;
				//for(int icol=1; icol<= metaData.getColumnCount(); icol++)	// check the
				while(icol<=metaData.getColumnCount())
				{
					System.out.println(result.getString(2));
					System.out.println(ListFields[icol-1].getType().getSimpleName());
					System.out.println(metaData.getColumnTypeName(icol));
					if((ListFields[icol-1].getType().getSimpleName().equals("Integer") && metaData.getColumnTypeName(icol).equals("INT")) || (ListFields[icol-1].getType().getSimpleName().equals("String") && metaData.getColumnTypeName(icol).equals("VARCHAR")))
					{
						System.out.println("in side ..........");
						ListFields[icol-1].set(out,result.getObject(icol));
					}
					else if(metaData.getColumnTypeName(icol).toString().toUpperCase().equals("INT") && waitPublisher == 0)	// reference key Book -> primary key Publisher
					{
						System.out.println(icol + " position of the reference key ");
						specialPoision = icol;		// the place of reference key
						System.out.println(result.getObject(icol) + "=======");
						Class<?> tem = ListFields[icol-1].getType();
						Class B = Class.forName(tem.getName());
						System.out.println(tem.getName());
						
						IEntityManagerClass<T> a = new IEntityManagerClass<T>(tem);
						ListFields[icol-1].set(out,a.find(result.getObject(icol)));
						icol++;
					}
					else if(metaData.getColumnTypeName(icol).toString().toUpperCase().equals("INT") && waitPublisher == 1)	// Publisher -> reference key Book -> primary key Publisher
					{
						ListFields[icol-1].set(out,null);
					}
					else specialPoision = icol;
					System.out.println("End 1 for.............................................................");
					icol++;
				}
				if(specialPoision == -1)	specialPoision = icol-1;
				if(metaData.getColumnCount()<ListFields.length)	// List<Book>
				{		
					
					//continue;
					System.out.println("Big Problem............................................................");
					System.out.println(ListFields[icol-1].getGenericType());
					String[] Types_ = ListFields[specialPoision].getGenericType().toString().split("\\W");
					String TempClass = Types_[Types_.length-1];
				/*	System.out.println(Types_[Types_.length-1]);
					System.out.println(ListFields[icol-1].getClass());
					System.out.println(ListFields[icol-1].getDeclaringClass());
					System.out.println(ListFields[icol-1].getType());
					String ChuPubli = ListFields[icol-1].getDeclaringClass().toString().toLowerCase();
					*/
					
					Class<?> tem = ListFields[specialPoision].getType();	// Type List
					System.out.println(tem.getSimpleName());
					
					Class<?> tem2 = Class.forName(TempClass);				// Book List
					System.out.println(tem2);
					java.lang.reflect.Field[] TempClassFields = tem2.getDeclaredFields();
				
					// select * from book,publisher 
				//	String que = "select * from " +  tem2.getSimpleName().toLowerCase() +","+ type.getName().toLowerCase() + " where " +  tem2.getSimpleName().toLowerCase()+ "."+ TempClassFields[specialPoision].getName() +" = "+ type.getName().toLowerCase() +".id;";
					String que = "select * from " +  tem2.getSimpleName().toLowerCase() + " where " +  tem2.getSimpleName().toLowerCase()+ "."+ TempClassFields[specialPoision].getName() +" = "+ primaryKey +";";
					System.out.println(que);
					java.sql.PreparedStatement secConnec = connection.prepareStatement(que);
					ResultSet secResult = secConnec.executeQuery();
					ResultSetMetaData secMetaData = secResult.getMetaData();
					
					System.out.println(secMetaData.getColumnCount());
					System.out.println(secMetaData.getColumnName(1));
					System.out.println(secMetaData.getColumnName(2));
					int count= 0;
					List<T> ListBook = new ArrayList<>();
					while(secResult.next())
					{
						IEntityManagerClass<T> retrBook = new IEntityManagerClass<T>(tem2);
						retrBook.waitPublisher = 1;
						System.out.println("The number: " + secResult.getObject(1) + " ====");
						ListBook.add(retrBook.find(secResult.getObject(1)));
						count++;
					}
					System.out.println(count + " The number of rows .................");
					
					ListFields[icol-1].set(out,ListBook);
					
					for(int iT=0; iT<ListBook.size();iT++)
					{
					//	elemBook = (T) tem2.newInstance();
						java.lang.reflect.Field[] ListField_elemBook = tem2.getDeclaredFields();
						for(java.lang.reflect.Field f : ListField_elemBook)
						{
							if(f.getType().getSimpleName().toString().equals(type.getName()))
							{
								System.out.println(f.getType().getSimpleName()+ " ..................................===============================");
								f.set(ListBook.get(iT),out);
								
							}
								
						}
						
					}
					ListFields[icol-1].set(out,ListBook);	// add Book into Publisher
					
				}
			}
			else
				return null;
	//        writer.write(sb.toString());
	 //       writer.flush();
	  //      writer.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return (T) out;
	}


	@Override
	public Query<T> createQuery(String query) {
		// return data from Query SQL
		Query<T> out = new Query<>(query, type);
		
		// TODO Auto-generated method stub
		return out;
	}
}
