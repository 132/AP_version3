import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Hashtable;

	
public class Scanner{
	public enum TOKEN_TYPE {  INTERFACE, FIELD, AT, LIST,PUBLIC,// TABLE,//EOL, 
		BOOK, PUBLISHER,
		
		//ITEM,KEY,SORT,
		ASSIGN,COMMA,
		AND, OR,DQUOTE,DOT,WHERE, SEMICOLON,
		OPEN_CURLYBRACKET,CLOSE_CURLYBRACKET,OPEN_BRACKET,CLOSE_BRACKET,
		OPEN_SQUAREBRACKET, CLOSE_SQUAREBRACKET,
		INT,STRING,FLOAT,DOUBLE,
		EOF,EOA,INVALID_CHAR,NO_TOKEN,
		EQ,BG,BGE,LS,LSE,NEQ,
		STRING_VALUE,NAME,NUMBER,
		};
	
	private StreamTokenizer input;	
	private Hashtable<String,TOKEN_TYPE> symbol_table;//hash table with String
	
	public Scanner(Reader r)
	{
	 //index
		// definition of streamTokenizer
		input = new StreamTokenizer(r);
		input.wordChars('a','z'); // specify the range of characters used in words
		
		input.wordChars('A', 'Z');
		input.eolIsSignificant(false);//ignore end of line and not returned as tokens
		input.parseNumbers();//the number should be parsed by tokenizer
		//each of these character is treated by the tokenizer as singol character-token
		char[] ordinaryChar={'{','}','[',']','(',')',';','.','>','<','=','!','"','@',','};
		for (char c:ordinaryChar) input.ordinaryChar(c);
		
		symbol_table=new Hashtable<String,TOKEN_TYPE>();//load keywords in the symbol
	//	symbol_table.put("item",TOKEN_TYPE.ITEM);//table for deciding which string
		symbol_table.put("int",TOKEN_TYPE.INT);//forms a keywords and which one forms.
		symbol_table.put("float",TOKEN_TYPE.FLOAT);//In this way you avoid the
		symbol_table.put("double",TOKEN_TYPE.DOUBLE);//innapropriate use of keywords
		symbol_table.put("String",TOKEN_TYPE.STRING);//as attribute name or class name
		symbol_table.put("and",TOKEN_TYPE.AND);
		symbol_table.put("or", TOKEN_TYPE.OR);
		symbol_table.put("list",TOKEN_TYPE.LIST);
		symbol_table.put("interface",TOKEN_TYPE.INTERFACE);
		symbol_table.put("public",TOKEN_TYPE.PUBLIC);
		symbol_table.put("@", TOKEN_TYPE.AT);
		symbol_table.put("Book", TOKEN_TYPE.BOOK);
		symbol_table.put("Publisher", TOKEN_TYPE.PUBLISHER);
	//	symbol_table.put("table", TOKEN_TYPE.TABLE);
	}
	public TOKEN_TYPE nextToken()	// return the type of the token
	{
		try {
			switch(input.nextToken())
			{
			case StreamTokenizer.TT_EOF: return TOKEN_TYPE.EOF;
			//if the string is not a keyword then insert a new record in the symbol table with associated token type “name”.
			//Finally return the token type by looking for symbol table record through string index.
			case StreamTokenizer.TT_WORD:
				if(symbol_table.get(input.sval)==null)			// compare with keywords if not it will be a name
					symbol_table.put(input.sval, TOKEN_TYPE.NAME);
				return (symbol_table.get(input.sval));
				
			case StreamTokenizer.TT_NUMBER: return TOKEN_TYPE.NUMBER;
			case '{': return TOKEN_TYPE.OPEN_CURLYBRACKET;
			case '}': return TOKEN_TYPE.CLOSE_CURLYBRACKET;
			case '[': return TOKEN_TYPE.OPEN_SQUAREBRACKET;
			case ']': return TOKEN_TYPE.CLOSE_SQUAREBRACKET;
				//if(input.nextToken()==StreamTokenizer.TT_WORD && input.sval.compareTo("KEY")==0&&input.nextToken()==']')//case [KEY]
				//	return TOKEN_TYPE.KEY; else return TOKEN_TYPE.INVALID_CHAR;
			case ';': return TOKEN_TYPE.SEMICOLON;
			case ',': return TOKEN_TYPE.COMMA;
			case '.': return TOKEN_TYPE.DOT;
			case '(': return TOKEN_TYPE.OPEN_BRACKET;
			case ')': return TOKEN_TYPE.CLOSE_BRACKET;
			case '=': 
				if(input.nextToken()=='=') return TOKEN_TYPE.EQ;
				else return TOKEN_TYPE.ASSIGN;					//  =
			case '!': 
				if(input.nextToken()=='=') return TOKEN_TYPE.NEQ;//case !=
				else return TOKEN_TYPE.NO_TOKEN;
			case '>': 
				if(input.nextToken()=='=') return TOKEN_TYPE.BGE;//case >=
				else {input.pushBack(); return TOKEN_TYPE.BG;}//case >
			case '<': 
				if(input.nextToken()=='=') return TOKEN_TYPE.LSE;//case <=
				else {input.pushBack(); return TOKEN_TYPE.LS;}//case <
			case '"': return TOKEN_TYPE.DQUOTE;
			case '@': return TOKEN_TYPE.AT;
		//		if(input.nextToken()==StreamTokenizer.TT_WORD )//case [KEY]
		//			return TOKEN_TYPE.ANNOTATION;
		//		else 
		//			return TOKEN_TYPE.INVALID_CHAR;
			
			default : return TOKEN_TYPE.NO_TOKEN; 
			}
		} catch (IOException e) 
		{
			e.printStackTrace();return TOKEN_TYPE.EOF;
		}
	}
	
	public Object getTokenval() 
	{//return the value of current token
		if(input.ttype==StreamTokenizer.TT_NUMBER) return this.input.nval;
		else return this.input.sval;
	}
}
