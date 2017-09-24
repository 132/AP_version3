import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


public class JavaGenerator {
	protected ArrayList<InterfaceStructure> data;
	
	public JavaGenerator(ArrayList<InterfaceStructure> in) {
		this.data = in;
		try {
			initialization(this.data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	private void initialization(ArrayList<InterfaceStructure> Meta) throws Exception
	{
		for(InterfaceStructure Inter : Meta)
		{
			
			String NameFile = "src/" + Inter.printName()+".java";
			System.out.println(NameFile + "==================");
			ArrayList<String> lines = new ArrayList<>();
			lines.add("");
			lines.add("public class " + Inter.printName() + "{");		// extends because I call Publisher
			lines.add("}");
			Path file = Paths.get(NameFile);
/*			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
				File helloWorldJava = new File(NameFile);
				DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
	            
	            // This sets up the class path that the compiler will use.
	            // I've added the .jar file that contains the DoStuff interface within in it...
	            List<String> optionList = new ArrayList<String>();
	            optionList.add("-classpath");
	            optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");
	            
	            Iterable<? extends JavaFileObject> compilationUnit
	                    = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
	            JavaCompiler.CompilationTask task = compiler.getTask(
	                null, 
	                fileManager, 
	                diagnostics, 
	                optionList, 
	            //    null,
	                null, 
	                compilationUnit);
	//			Mainv3.runProcess("javac /home/bialan132/Public/EclipseFedora/AP_version3/src/"+  Inter.printName()+".java");
	//		Mainv3.runProcess("cp /home/bialan132/Public/EclipseFedora/AP_version3/src/"+  Inter.printName()+".java" + " ../bin/");
			} catch (IOException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
	}

	public void printNewType() throws Exception
	{
		for(InterfaceStructure Inter : this.data)
		{
			
		//	String NameFile = "src/" + Inter.printName()+".java";
			 File file1 = new File("src/" + Inter.printName() +".java");
			System.out.println( Inter.printName() + "==================");
			ArrayList<FieldStructure> ListofFields = new ArrayList<>(Inter.printField());
			
			ArrayList<String> lines = new ArrayList<>();
			lines.add("");
			lines.add("public class " + Inter.printName() + "{");

			
			ArrayList<String> Constructor = new ArrayList<>();
			String linesConstructor = "public " + Inter.printName() + "("; 
			Constructor.add(linesConstructor);
			
			//lines.add("}");
		//	Path file = Paths.get(NameFile);
		
			for(FieldStructure Field : ListofFields)
			{
				
				
				ArrayList<Type> type = new ArrayList<Type>(Field.printType());

				String lineField = "protected ";
				for(int itype=0;itype<type.size();itype++)
				{
				
					if(type.get(itype).printType().equals("List"))
					{
						lines.set(0, "import java.util.List;" + "\nimport java.util.ArrayList;");
					}
					
					System.out.println(type.get(itype).printType() + " ----------------------------------------------------");
					
					if(Scanner.symbol_table.get(type.get(itype).printType().toString()).equals(Scanner.TOKEN_TYPE.NEWTYPE) && type.size()==1)
						lines.set(1,"public class " + Inter.printName() + " extends " + type.get(itype).printType().toString() +" implements SupClass{");
					lineField += type.get(itype).printType();
					linesConstructor += type.get(itype).printType();
					
					if(itype+1<type.size())
					{
						lineField += "<";
						linesConstructor+= "<";
					}
				}
				// for to close > in type
				for(int closeBG=0;closeBG<type.size()-1;closeBG++)
				{
					lineField += ">";
					linesConstructor += ">";
				}
				lineField += " " + Field.printName() + ";";
				
				lines.add(lineField);
				
				if (ListofFields.get(ListofFields.size()-1).equals(Field))
					linesConstructor += " " + Field.printName() + "_temp";
				else	linesConstructor += " " + Field.printName() + "_temp,";
				Constructor.add("this." +Field.printName()+ " =" + Field.printName()+"_temp;");
			}
			//lines.add("}\n");
			lines.add("public " + Inter.printName() + "(){}");
			linesConstructor += "){";
			Constructor.add("}}");
			Constructor.set(0, linesConstructor);
			// 	this.id this.title this.book
			
			
			
			//lines = (ArrayList<String>) Arrays.asList(lines);
			System.out.println(lines.toString());
			System.out.println(Constructor.toString());
			
			try {
				  file1.createNewFile();
			      
			      // creates a FileWriter Object
			      FileWriter writer = new FileWriter(file1); 
			      
			      // Writes the content to the file
			      for(String f: lines)
			    	  writer.write(f+"\n");
			      for(String f: Constructor)
			    	  writer.write(f+"\n");
			      writer.flush();
			      writer.close();
			      
			//	Files.write(file, lines, Charset.forName("UTF-8"));
			//	Files.write(file, Constructor, StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/*
	 * 
	public interface InterfaceItem {
		public Object getKeyValue();//return the primary key value
	}
	
	method print() inside the following classes represent the code generator
	public class Item{..
		public String print(){
					//assignments contains list of assignment like "this.att1=att1"
			while(lista!=null){
				assignments+="\nthis."+lista.getNome()+" = "+lista.getNome()+";";
				lista=lista.next();
			}
			return "public class "+this.name+" implements InterfaceItem"+
					//the output will be: public tipo1 att1;public tipo2 att2;...
					{\npublic "+list_attribute.print().replaceAll(",",";\npublic ")+
					//the output will be: public name(tipo1 att1,tipo2 att2,..)
					";\npublic "+name+"("+list_attribute.print()+"){"+assignments+
					"\n}\npublic "+list_attribute.getType()+" getKeyValue(){"+
					return this."+list_attribute.getNome()+";}"+
					//two objects are equals if they have the same primary key
					"\npublic boolean equals(Object other){return (this==other)&&(other instanceof
					"+name+")&&(this.getKeyValue()==(("+name+")other).getKeyValue());}"+
					"\npublic int hashCode(){return
					Integer.parseInt(this.getKeyValue().toString());}\n}";
					}
					}
					public class ListAttribute {..
					public String getNome(){return this.nome;}
					public String getType(){return this.type.print();}
					public String print() {String res=type.print()+" "+nome;
					if(list_attribute!=null)res+=','+list_attribute.print();
					return res;
					}
					}
					public class Type{..
					public String print(){//according to type of read token,it prints the
					switch (type){
					 //corresponding type
					case FLOAT: return "Float";
					case STRING: return "String";
					3
					case DOUBLE: return "Double";
					default: return "Integer";}
					}
					}
					The class Collection<T> and Query<T> that are included in APLIQ:
					public class Collection<T extends InterfaceItem>
					{//container is of type Set<E>, there cannot be duplicates within it
					private Set<T> container;
					public Collection() {container=new HashSet<T>();}
					public T get(Object val){//search according primary key
					for(T element: container){
					if(element.getKeyValue().equals(val)) return element;}
					return null;
					}
					public void insert(T elem){container.add(elem);}
					public Query<T> query(){return new Query<T>(container);}
					}
					public class Query<T>{
					private List<T> container;
					private List<ConditionalClause<T>> conditionalclauses;
					private Comparator<T> orderclause;
					public Query(java.util.Collection<T> collection) {
					this.container=new ArrayList<T>(collection);
					this.conditionalclauses=new ArrayList<ConditionalClause<T>>();
					this.orderclause=null;
					}
					//the exeute() method return a List whose elements belong to container and
					satisfy all the clauses in the arrayList
					public List<T> execute() {
					List<T> result=new ArrayList<T>();
					for (T elem:container){ boolean satisfy=true;
					for(ConditionalClause<T> clause:conditionalclauses){
					satisfy=satisfy && clause.match(elem);
					}
					if(satisfy) result.add(elem);//if the elements satisfy all the clauses
					satisfy=true;
					 //then it is returned
					}
					if(orderclause!=null) result.sort(orderclause);
					return result;
					}
					}
					//the object instance of this class represent a single bond applied to result of
					the query
					public class ConditionalClause<T> {
					String attribute_name;Object value;TOKEN_TYPE code_relop;
					public ConditionalClause(String attribute_name, TOKEN_TYPE i,Object value) {
					this.attribute_name = attribute_name;this.value = value;
					this.code_relop = i;
					}
					public boolean match(T elem){//if elem satisfy the clause then returns true
					try{Field campo=elem.getClass().getDeclaredField(attribute_name);
					//attribute_value contains the value of attribute identified by "name" that
					belongs to Object “elem”
					Object attribute_value=campo.get(elem);
					double difference=0;
					if(attribute_value instanceof String)
					difference =((String) attribute_value).compareTo(value.toString()) ;
					else if(attribute_value instanceof Number){
					if(value instanceof Number)
					difference=((Number)attribute_value).doubleValue() -
					((Number)value).doubleValue();
					else return false;
					4
					}
					//code_relop identifies the relational operator of a clause.It uses the
					constants of enum TOKEN_TYPE to identify the corresponding operator
					switch(code_relop){
					case EQ: return (difference==0);
					case NEQ: return (difference!=0);
					case BG: return (difference>0);
					case BGE: return (difference>=0);
					case LS: return (difference<0);
					case LSE: return (difference<=0);
					default: return false;
					}
					}catch(Exception err){throw new RuntimeException();}
					}
					}
					*/
	 
	
}
