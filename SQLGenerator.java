import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
/*
CREATE TABLE book ( id INT NOT NULL PRIMARY KEY);

CREATE TABLE publisher ( id INT NOT NULL PRIMARY KEY);

ALTER TABLE `book`
ADD COLUMN `title` VARCHAR(80),
ADD COLUMN  publisher_id INT,
ADD FOREIGN KEY (publisher_id) REFERENCES `publisher`(`id`);

ALTER TABLE `publisher`
ADD COLUMN `name` VARCHAR(80),
ADD COLUMN  book_id INT,
ADD FOREIGN KEY (book_id) REFERENCES `publisher`(`id`);
*/


public class SQLGenerator extends JavaGenerator{
	//private ArrayList<InterfaceStructure> data;
	public ArrayList<String> lines = new ArrayList<>();
	public SQLGenerator(ArrayList<InterfaceStructure> in) throws IOException {
		super(in);
		// generate new table only with id
		//	CREATE TABLE book ( id INT NOT NULL PRIMARY KEY);
		//	CREATE TABLE publisher ( id INT NOT NULL PRIMARY KEY);

		String NameFile = "SQLGenerator.sql";
		System.out.println(NameFile + "==================");
		File file = new File(NameFile);
		file.createNewFile();
		// Path file = Paths.get(NameFile);
			
	     // creates a FileWriter Object
	     FileWriter writer = new FileWriter(file); 
	      
		for(InterfaceStructure Inter : data)
		{	
			lines.add("CREATE TABLE " + Inter.printAnnotation().getValue("name") + " ( id INT NOT NULL PRIMARY KEY");	
			
			lines.set(lines.size()-1, lines.get(lines.size()-1).split("\\,")[0]);
			lines.add(");\n");

			System.out.println(lines.toString());
		
		}
		try {
			//Files.write(file, lines, Charset.forName("UTF-8"));		    
		      // Writes the content to the file
		      for(String f: lines)
		    	  writer.write(f+"\n");
		      
		      writer.flush();
		      writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	public String printSQL(FieldStructure f)
	{
		// Many2One
		if(f.Type.size()==1)
		{
			System.out.println("================== " + f.Type.size());
			switch (f.Type.get(0).printType()) {
			case "String": 
				String len = f.Annotation.getValue("length");
				if (len.contains(".")) 
				{
					return "VARCHAR("+(len.split("\\."))[0]+")";
				}
				return "VARCHAR(" + len + ")";
			case "Integer": 
				if (f.Annotation.getValue("name").equals("id"))
					return "INT";
			default: // not a common type in SQL -> foreign key
				System.out.println("Check Type in coverting to SQL: "+ Scanner.symbol_table.get(f.Type.get(0).printType()));
				System.out.println("Check Type in new things "+ f.Annotation.getValue("target")+ " " + f.Type.get(0).printType());
				if(Scanner.symbol_table.get(f.Type.get(0).printType()).equals(Scanner.TOKEN_TYPE.NEWTYPE) && f.Annotation.getValue("target").equals(f.Type.get(0).printType()))
				{
					System.out.println(" = =======================================");
					
					//String n = f.Type.get(0).printType();//this.Annotation.getValue("target");
					String name = f.Annotation.getValue("name");
					String target = f.Annotation.getValue("target");
		//			String mappedBy = f.Annotation.getValue("mappedBy");
					String out = "INT," + "ADD FOREIGN KEY (" + name + ") REFERENCES `" + target.toLowerCase() + "`(`id`)";
					System.out.println(out);
					return out;
				}
				System.out.println("deo muon thay dong nay dau nha");
				return null;
					
			}
		}
		else	// One2Many		Type.size > 1 Ex List List Book
		{
			// ALTER TABLE table1 ADD COLUMN Age TINYINT UNSIGNED DEFAULT 0; add a new field to table1
		//	String src = this.Annotation.getValue("target");		// Book
		//	String dest = this.Annotation.getValue("mappedBy");	// publisher
			//String 
			
			/*
			System.out.println("Check Type in coverting to SQL: "+ Scanner.symbol_table.get(f.Type.get(f.Type.size()-1).printType()));
			System.out.println("Check Type in new things "+ f.Annotation.getValue("target")+ " " + f.Type.get(f.Type.size()-1).printType());
			if(Scanner.symbol_table.get(f.Type.get(f.Type.size()-1).printType()).equals(Scanner.TOKEN_TYPE.NEWTYPE) && f.Annotation.getValue("target").equals(f.Type.get(f.Type.size()-1).printType()))
			{
				
				//String n = f.Type.get(0).printType();//this.Annotation.getValue("target");
				String name = f.Annotation.getValue("name");
				String target = f.Annotation.getValue("target");
				String mappedBy = f.Annotation.getValue("mappedBy");
				return "INT,ADD FOREIGN KEY (" + name + ") REFERENCES `" + target.toLowerCase() + "`(`id`)";
			}
			*/
			return null;
				
		}
	}
	public void printNewType()
	{
		String NameFile = "SQLGenerator.sql";
		System.out.println(NameFile + "==================");
		
		for(InterfaceStructure Inter : data)
		{	
			ArrayList<FieldStructure> ListofFields = new ArrayList<>(Inter.printField());
			lines.add("ALTER TABLE `" + Inter.printAnnotation().getValue("name") + "` ");			
			for(int ifield=1; ifield<ListofFields.size();ifield++)
			{
				String lineField = null;
				if (printSQL(ListofFields.get(ifield))!= null)
				{
					lineField = "ADD COLUMN " + ListofFields.get(ifield).printAnnotaion().getValue("name") + " ";
					String a = printSQL(ListofFields.get(ifield));
					lineField += a;
					System.out.println(".................... " + a);
					System.out.println(".................... " + lineField);
					if(ifield+1<ListofFields.size())
						lineField += ",";
					System.out.println(".................... " + lineField);
					lines.add(lineField);	
					System.out.println(lines.get(lines.size()-1));
					// add reference key
					
				}
				else
				{
					lines.set(lines.size()-1,lines.get(lines.size()-1).split("\\,")[0]);
				}
			}
			//lines.set(lines.size()-1, lines.get(lines.size()-1).split("\\,")[0]);
			lines.add(";\n");

			System.out.println(lines.toString());
		}
		//Path file = Paths.get(NameFile);
		try {
			File file = new File(NameFile);
			file.createNewFile();
			// Path file = Paths.get(NameFile);
				
		     // creates a FileWriter Object
		     FileWriter writer = new FileWriter(file); 
		      
		      // Writes the content to the file
		      for(String f: lines)
		    	  writer.write(f+"\n");
		      writer.flush();
		      writer.close();
		//	Files.write(file, lines, StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	/*
	public void printNewType()
	{
		String NameFile = "SQLGenerator.sql";
		System.out.println(NameFile + "==================");
		ArrayList<String> lines = new ArrayList<>();
		for(InterfaceStructure Inter : data)
		{	
			ArrayList<FieldStructure> ListofFields = new ArrayList<>(Inter.printField());
			lines.add("CREATE TABLE " + Inter.printAnnotation().getValue("name") + " (");			
			for(int ifield=0; ifield<ListofFields.size();ifield++)
			{
				String lineField = null;
				if (ListofFields.get(ifield).printSQL()!= null)
				{
					lineField = " " + ListofFields.get(ifield).printAnnotaion().getValue("name") + " ";
					lineField += ListofFields.get(ifield).printSQL();
					if(ifield+1<ListofFields.size())
						lineField += ",";
					lines.add(lineField);	
				}
				
			}
			lines.set(lines.size()-1, lines.get(lines.size()-1).split("\\,")[0]);
			lines.add(");\n");

			System.out.println(lines.toString());
		}
		Path file = Paths.get(NameFile);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
*/