import java.awt.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SQLGenerator extends JavaGenerator{
	//private ArrayList<InterfaceStructure> data;
	public SQLGenerator(ArrayList<InterfaceStructure> in) {
		super(in);
		// TODO Auto-generated constructor stub
	}

	public void printNewType()
	{
		String NameFile = "SQLGenerator.sql";
		System.out.println(NameFile + "==================");
		ArrayList<String> lines = new ArrayList<>();
		for(InterfaceStructure Inter : data)
		{	
			ArrayList<FieldStructure> ListofFields = new ArrayList<>(Inter.printField());
			lines.add("CREATE TABLE " + Inter.printName() + " (");			
			for(int ifield=0; ifield<ListofFields.size();ifield++)
			{
				String lineField = null;
				if (ListofFields.get(ifield).printSQL()!= null)
				{
					lineField = " " + ListofFields.get(ifield).printName() + " ";
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
