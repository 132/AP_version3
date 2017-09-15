import java.util.Map;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;


/*
 * 
 * SQL server/Oracle/MS Access
 */
public class Type{ //represents a kind of abstract node derived by no-terminal
//	Scanner.TOKEN_TYPE type; //symbol(TYPE)
	String type;
	public Type(Scanner.TOKEN_TYPE in) 
	{
		this.type = in.toString();
	}
	public Type(Type a) 
	{

		this.type = a.type;
	}
	public Type(String a)
	{
		this.type = a;
	}
	public String printType()
	{
		for(Map.Entry<String, Scanner.TOKEN_TYPE> entry : Scanner.symbol_table.entrySet())
		{
			if(this.type.equals(entry.getValue().toString()))
			{
				System.out.println("Check printType: "+ entry.getKey());
				this.type = entry.getKey();
			}
		}
		return this.type;
	}
	public Boolean checkType()
	{
		Scanner.TOKEN_TYPE check = Scanner.symbol_table.get(this.type);
		if(this.type.equals(Scanner.TOKEN_TYPE.NEWTYPE.toString()))
		{
			for(int i=0; i<Scanner.symbol_table.size();i++)
				if(check.equals(Scanner.TOKEN_TYPE.NEWTYPE))
					return true;
				else
					return false;
		}
		else 
			return true;
		return null;
	}
	/*
	public String convertSQLType()
	{
		switch (this.type) {
		case "String": return "VARCHAR";
		case "Integer": return "INT";
		case "List": return null;
		default:
			return "FOREIGN KEY";
		}
	}
	*/
}
