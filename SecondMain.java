import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class SecondMain {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		//ClassLoader classLoader = 	
		System.out.println("\n===================== This is second Main ========================\n");

		System.out.println("ok");

		Publisher xyz = new Publisher(1, "tri", null);
		
		Book tri1 = new Book(1,"trideptrai1", xyz);
		Book tri2 = new Book(2,"trideptrai2", xyz);
		Book tri3 = new Book(3,"trideptrai3", xyz);
		Book abc = new Book(4,"anbc", xyz);

		ArrayList<String> ccc = new ArrayList<>();
		System.out.println(ccc.getClass().getTypeParameters());
		
		
	//	System.out.println(abc.getClass().getSimpleName());
		IEntityManager<Publisher> pu = new IEntityManagerClass<Publisher>();
		pu.persist(xyz);
		IEntityManager<Book> bo = new IEntityManagerClass<Book>();
		bo.persist(tri1);
		bo.persist(tri2);
		bo.persist(tri3);
		bo.persist(abc);
		bo.remove(abc);
		System.out.println(bo.getClass());
		java.lang.reflect.Type type = bo.getClass().getGenericInterfaces()[0];
		java.lang.reflect.Field[] aClassFields = bo.getClass().getDeclaredFields();
		System.out.println(aClassFields[0].getGenericType() + ";;;;;;;;;;;;;;;;;;");
		
		if (type instanceof ParameterizedType) {
		    java.lang.reflect.Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
		    System.out.println(actualType + " >>>>>>>>>>>>>>>>>>>>>");
		}
		
		Book tri_2 = bo.find(2);
		
	}

}
