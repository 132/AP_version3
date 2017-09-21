import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SecondMain {

	public static String url = "jdbc:mysql://localhost:3306/trideptrai?verifyServerCertificate=false&useSSL=true";
	public static String username = "root";
	public static String password = "trideptraivodoi";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//String url = "jdbc:mysql://localhost:3306/javabase";
		// connect to databse trideptrai


		System.out.println("Connecting database...");

	//	try ( Connection connection = DriverManager.getConnection(url, username, password)) {
			
		    System.out.println("Database connected!");
	
		    //......................... Ex4 ..................................
		  //ClassLoader classLoader = 	
			System.out.println("\n===================== This is second Main ========================\n");
			Class BookClass = Class.forName("Book");
			Class PublisherClass = Class.forName("Publisher");

			Publisher xyz = new Publisher(1, "tri", null);
			Book tri1 = new Book(1,"trideptrai1", xyz);
			Book tri2 = new Book(2,"trideptrai2", xyz);
			Book tri3 = new Book(3,"trideptrai3", xyz);
			Book abc = new Book(4,"anbc", xyz);

			IEntityManager<Publisher> pu = new IEntityManagerClass<Publisher>(Publisher.class);	
			IEntityManager<Book> bo = new IEntityManagerClass<Book>(Book.class);
			pu.persist(xyz);
		//	bo.persist(tri1);
		//	bo.persist(tri2);
		//	bo.persist(tri3);
		//	bo.persist(abc);
		//	bo.remove(abc);
		/*	System.out.println(bo.getClass());
			java.lang.reflect.Type type = bo.getClass().getGenericInterfaces()[0];
			System.out.println("----------------------------------------------------------------");
			
			
			System.out.println(bo.getClass().getGenericSuperclass().getTypeName()+ " --------- hop");
			
			
			java.lang.reflect.Field[] aClassFields = bo.getClass().getDeclaredFields();
			System.out.println(aClassFields[0].getGenericType() + ";;;;;;;;;;;;;;;;;;");
			
			System.out.println(bo.getClass() + "-------------------------------------");
			
			System.out.println(type.getTypeName());
			if (type instanceof ParameterizedType) {
			    java.lang.reflect.Type actualType = ((ParameterizedType) type).getOwnerType();
			    System.out.println(actualType + " >>>>>>>>>>>>>>>>>>>>>");
			}
			*/
			Book tri_2 = bo.find(1);
			
			System.out.println(tri_2.id + " " + tri_2.title + " " + tri_2.publisher.name);
			
		    bo.createQuery("SELECT * FROM book;");
		    Statement stmt; 
			
//		    stmt = connection.createStatement();
		    
		    //stmt.execute("CREATE TABLE Persons (PersonID int, Lastname VARCHAR(30));");
		    //System.out.println("CREATE TABLE Persons (PersonID int, Lastname VARCHAR(30));");
		    
	//	    ResultSet result = stmt.executeQuery("SELECT * FROM book;");
		    List<Book> ListBook=new ArrayList<Book>();
		    /*
		    while (result.next()) 
		    {
		     //   Field ListField[] = BookClass.getDeclaredFields();
		    //    ListField[0].setInt("id",result.getInt("id"));
        	//	ListField[1].set("title", result.getString("title"));
			//	ListField[2].setInt("publisher", result.getInt("publisher"));	
				
				Publisher temp = pu.find(result.getInt("publisher"));	
		        Book book = new Book(result.getInt("id"),result.getString("title"),temp);
		        System.out.println(result.getInt("id"));
		        ListBook.add(book);
		    }
		*/
//		    connection.close();
///		} catch (SQLException e) {
//		    throw new IllegalStateException("Cannot connect the database!", e);
//		}
		
		
	//	Query<Book> QueryBo = bo.createQuery("SELECT * FROM book;");
	//	List<Book> BooksBo = QueryBo.getResultList();
	//	QueryBo.execute();
		
	}

}
