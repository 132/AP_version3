public class Type{ //represents a kind of abstract node derived by no-terminal
//	Scanner.TOKEN_TYPE type; //symbol(TYPE)
	String type;
	public Type(Scanner.TOKEN_TYPE type) 
	{
		this.type=type.toString();
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
		return this.type.toString();
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
}
