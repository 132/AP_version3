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
		System.out.println("\t" + this.Annotation);
	//	System.out.println("\t -----------" + this.Type.size()+" =========");
		for(int j=0;j<this.Type.size();j++)
		{
			//System.out.print("\t trideptrai =========");
			System.out.println("\t" + this.Type.get(j).toString());
		}
		
		System.out.println("\t" + this.Name);
	}
	
}
