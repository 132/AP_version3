import java.util.ArrayList;

public class InterfaceStructure {
	private AnnotationStructure Annotation;
	private String Name;
	private ArrayList<FieldStructure> Inside;
	
	public InterfaceStructure(AnnotationStructure A, String N, ArrayList<FieldStructure> I)
	{
		this.Annotation = A;
		this.Name = N;
		this.Inside = I;
	}
	public InterfaceStructure(InterfaceStructure P)
	{
		this.Annotation = P.Annotation;
		this.Name = P.Name;
		this.Inside = P.Inside;
	}
	
	public String printName()
	{
		return this.Name;
	}
	public AnnotationStructure printAnnotation()
	{
		return this.Annotation;
	}
	public ArrayList<FieldStructure> printField()
	{
		return this.Inside;
	}
	
	public void printInterface()
	{
		System.out.println("\nInterface  ---------------------------");
		System.out.println(this.Annotation);
		System.out.println(this.Name);
		
		for(int i=0; i<this.Inside.size();i++)
		{
			System.out.println("\tFileds  ----------------------------");
			Inside.get(i).printField();
		}
	}
}
