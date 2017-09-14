import java.util.ArrayList;

public class FieldStructure {
	private AnnotationStructure Annotation;
	private ArrayList<Type> Type = new ArrayList<>();
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
	
}
