import java.util.ArrayList;

public class FieldStructure {
	protected AnnotationStructure Annotation;
	protected ArrayList<Type> Type = new ArrayList<>();
	private String Name;
	
	public FieldStructure(AnnotationStructure A, ArrayList<Type> T, String V)
	{
		this.Annotation = A;
		this.Type = T;
		this.Name = V;
	}
	public FieldStructure(FieldStructure A)
	{
		this.Annotation = A.Annotation;
		this.Type = A.Type;
		this.Name = A.Name;
	}
	
	
	public AnnotationStructure printAnnotaion()
	{
		return this.Annotation;
	}
	public ArrayList<Type> printType()
	{
		return this.Type;
	}
	public String printName()
	{
		return this.Name;
	}
	public void printField() 
	{
		this.Annotation.printCodeAnnotations();
		System.out.println("\t -----------" + this.Type.size()+" =========");

		for(int j=0;j<this.Type.size();j++)
		{
			//System.out.print("\t trideptrai =========");
			if(this.Type.get(j).checkType())
				System.out.print(this.Type.get(j).printType() + " ");
		}
		
		System.out.println("\t" + this.Name);
	}
	public String printJAVA()
	{
		//import java.util.ArrayList;
		if(this.Type.size()==1)
		{
			switch (Type.get(0).printType()) {
			case "String": 
				String len = this.Annotation.getValue("length");
				if (len.contains(".")) 
				{
					return "VARCHAR("+(len.split("\\."))[0]+")";
				}
				return "VARCHAR(" + len + ")";
			case "Integer": 
				if (this.Annotation.getValue("name").equals("id"))
					return "INT NOT NULL PRIMARY KEY";
				else return "INT";
			default: // not a common type in SQL -> foreign key
				System.out.println("Check Type in coverting to SQL: "+ Scanner.symbol_table.get(Type.get(0).printType()));
				System.out.println("Check Type in new things "+ this.Annotation.getValue("target")+ " " + Type.get(0).printType());
				if(Scanner.symbol_table.get(Type.get(0).printType()).equals(Scanner.TOKEN_TYPE.NEWTYPE) && this.Annotation.getValue("target").equals(Type.get(0).printType()))
				{
					String NameTable = Type.get(0).printType();//this.Annotation.getValue("target");
					return "FOREIGN KEY REFERENCES " + NameTable;
				}
				return null;
					
			}
		}
		else	// One2Many
		{
			return null;
		}
		
	}
	
	
}
