import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationStructure {
	private String AnnotationName;
	private Map<String, String> KeyValues = new HashMap<String, String>();
	private String TypeofName;
	
	public AnnotationStructure(String InputName, Map<String, String> InputKeyValues) {
		this.AnnotationName = InputName;
		this.KeyValues = InputKeyValues;
	}
	public AnnotationStructure(AnnotationStructure A)
	{
		this.AnnotationName = A.AnnotationName;
		this.KeyValues  = A.KeyValues;
	}
	
	public AnnotationStructure(String Input) {
		String[] parts = Input.split("(?<=\\p{Punct})|(?=\\p{Punct})"); // split special character
//		System.out.print(parts[0]+"\n");
	//for(int i=0;i<parts.length;i++)
//		System.out.print(parts[i]+"\n");
		int j=0;
		//System.out.print(parts.length+"\n");
		while(j<parts.length)
		{
			parts[j] = parts[j].replaceAll("\\s+","");
			//System.out.print(parts[j]+"\n");
			if(parts[j].equals("@")) 
			{
				AnnotationName = parts[j+1];
				j++;
				//System.out.print(AnnotationName +"\n");
			}
			else if(parts[j].matches("\\w+") && j+3 < parts.length && !parts[j].equals("") && !parts[j+3].equals(""))
			{
				String name = parts[j].toString();
				String values = parts[j+3].toString();
				//System.out.print("ten: " + name +" ----- "+ values + "\n");
				KeyValues.put(name, values);
				j+=4;
			}
			j++;
		}
	}
	public void printCodeAnnotations() {
		// convert Map -> string and concat all keys and values
		String abc = KeyValues.entrySet().parallelStream().map((entry) -> //stream each entry, map it to string value
        ""+ entry.getKey() + "=\"" + entry.getValue().replaceAll("\"", "\\\\\"") + "\"")
        .collect(Collectors.joining(",")); //and 'collect' or put them together by joining
		
		System.out.print("@"+AnnotationName+"("+abc+")"+"\n");
		if(AnnotationName=="Id") TypeofName = "Integer " + KeyValues.get("name");
		else if(AnnotationName=="Many2One") TypeofName = KeyValues.get("target") + " " + KeyValues.get("name");
		else if(AnnotationName=="One2Many") TypeofName = "List<"+ KeyValues.get("target")+ "> " + KeyValues.get("name");
		else TypeofName = "String " + KeyValues.get("name");
		System.out.print(TypeofName + ";\n");
	}
}
