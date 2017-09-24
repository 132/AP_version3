import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query<T> implements IQuery<T> {
	private Class<T> typeOfClass;
	private String query;
	protected Query(String q, Class<T> A) {
		this.query = q;
		this.typeOfClass = A;
	//	this.typeOfClass = T.getClass();
	}

	@Override
	public List<T> getResultList() {
		// SELECT * FROM TABLENAME WHERE;
		// TODO Auto-generated method stub
		List<T> out = new ArrayList<T>();

		
		//ResultSet result;
		 
	//	try 
	//	{
			IEntityManagerClass<T> retr = new IEntityManagerClass<T>(typeOfClass);
			try {
				Connection connection = DriverManager.getConnection(SecondMain.url, SecondMain.username, SecondMain.password);
				String q = ("SELECT * FROM " + typeOfClass.getName().toLowerCase() + ";");
				
				System.out.println(q);
				java.sql.PreparedStatement st = connection.prepareStatement(q);
				ResultSet result = st.executeQuery();
				ResultSetMetaData metaData = result.getMetaData();
				while(result.next())
				{
					out.add(retr.find(result.getObject(1)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		/*	int K = 1;
			while(retr.find(K)!=null)
			{
				out.add(retr.find(K));
				K++;
			}
			*/
		//	out.add(newElem.find());
		/*	Statement stmt;
			Class ThisClass = Class.forName(typeOfClass.getName());
			result = stmt.executeQuery("SELECT * FROM "+ typeOfClass.getName() + ";");
			
		    while (result.next()) 
		    {
		    	ResultSetMetaData metaData = result.getMetaData();
				int count = metaData.getColumnCount(); //number of column
				Map<String,Object> temp = new HashMap<String, Object>();
				String columnName[] = new String[count];
				
		//		for (int i = 1; i <= count; i++)
		//		{
		//		   columnName[i-1] = metaData.getColumnLabel(i);
		//		   System.out.println(columnName[i-1]);
		//		}
				Field ListField[] = ThisClass.getDeclaredFields();
				Types ListTypes[];
				for(int i = 1; i <= count;i++)
				{
					String NameofCol = metaData.getColumnName(i);
					int type = metaData.getColumnType(i);
					if (type == Types.VARCHAR || type == Types.CHAR) {
						System.out.println(result.getString(NameofCol));
						String stri = result.getString(NameofCol);
						temp.put(metaData.getColumnName(i),stri);
					}
					else if (type==Types.INTEGER){
						System.out.println(result.getInt(NameofCol));
						int inte= result.getInt(NameofCol);
						temp.put(metaData.getColumnName(i), inte);
					}
					else
					{
						Class<?> TypeSubClass = ListField[i-1].getClass();
						
					}
				}
				
			//	Constructor c = ThisClass.getConstructor(parameterTypes)

		//		Publisher temp = pu.find(result.getInt("publisher"));
		 //       Book book = new Book(result.getInt("id"),result.getString("title"),temp);
		  //      System.out.println(result.getInt("id"));
		   //     out.add(book);
		    * 
		    * 
		    */
			
//		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	   
		return out;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		try 
		{ 
	    	Connection connection = DriverManager.getConnection(SecondMain.url, SecondMain.username, SecondMain.password);
			java.sql.PreparedStatement st = connection.prepareStatement(query);
			st.execute();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

}
