import java.awt.List;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Scanner.TOKEN_TYPE;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

/*
 *		Interface -> Annotation Name Inside
 *		Inside    -> Annotation Types Name
 *		Types     -> Type Types | Empty
 */

public class Parser {
	//lookahed contains the current read token
	private Scanner scan;
	private Scanner.TOKEN_TYPE lookahead;
	//check if the current token is just what expected, then consume it.
	
	private void match(Scanner.TOKEN_TYPE t) throws SyntaxException
	{
		if(lookahead!=t) throw new SyntaxException("matching expected "+t);
			lookahead = scan.nextToken();
	}
	
	private void expect(Scanner.TOKEN_TYPE t)
	{//doesn’t consume the token
		if(lookahead!=t) throw new SyntaxException("matching failed:expected "+t);
	}
	
	public InterfaceStructure parseItem(Reader r)
	{//parse item definition,and return an abstract
		scan =new Scanner(r);
		 //Parse Annotation:  Annotation -> @ Table ( name = " Book " )
		lookahead = scan.nextToken();
		AnnotationStructure A = new AnnotationStructure(parseAnnotaion());
		match(Scanner.TOKEN_TYPE.PUBLIC);
		match(Scanner.TOKEN_TYPE.INTERFACE);
		expect(Scanner.TOKEN_TYPE.NAME);
		String N = (String) scan.getTokenval();
		match(Scanner.TOKEN_TYPE.NAME);
		match(Scanner.TOKEN_TYPE.OPEN_CURLYBRACKET);
		//the next attributes
		ArrayList<FieldStructure> F = new ArrayList<>();
		F = FieldList(F);
		match(Scanner.TOKEN_TYPE.CLOSE_CURLYBRACKET);
		match(Scanner.TOKEN_TYPE.EOF);
		return new InterfaceStructure(A,N,F);
	}
	private AnnotationStructure parseAnnotaion()
	{
		switch (lookahead) {
		case CLOSE_BRACKET: return null;
		default:
			match(Scanner.TOKEN_TYPE.AT); 
			expect(Scanner.TOKEN_TYPE.NAME); 
			String name = (String) scan.getTokenval();
			match(Scanner.TOKEN_TYPE.NAME);
			match(Scanner.TOKEN_TYPE.OPEN_BRACKET);
			Map<String,String> KeyValues = new HashMap<String, String>();
			KeyValues = KeyValuesList(KeyValues);
			match(Scanner.TOKEN_TYPE.CLOSE_BRACKET);
			return new AnnotationStructure(name,KeyValues);
		}
	}
	private Map<String,String> KeyValuesList(Map<String,String> KeyValues)
	{
		switch (lookahead) {
		case CLOSE_BRACKET:	return null;
		default:
		//	Map<String,String> out = new HashMap<String,String>();
			
			expect(Scanner.TOKEN_TYPE.NAME);
			String K = (String) scan.getTokenval();
			match(Scanner.TOKEN_TYPE.NAME);
			match(Scanner.TOKEN_TYPE.DQUOTE);
			expect(Scanner.TOKEN_TYPE.NAME);
			String V = (String) scan.getTokenval();
			match(Scanner.TOKEN_TYPE.NAME);
			match(Scanner.TOKEN_TYPE.DQUOTE);
			KeyValues.put(K, V);
			return (lookahead == Scanner.TOKEN_TYPE.COMMA) ? KeyValuesList(KeyValues) : KeyValues;
		}
	}
	private ArrayList<FieldStructure> FieldList(ArrayList<FieldStructure> F)
	{
		switch(lookahead)
		{
		case CLOSE_CURLYBRACKET: return null;
		
		default:
			FieldStructure temp = new FieldStructure(parseField());
			F.add(temp);
			return (lookahead == Scanner.TOKEN_TYPE.AT) ? FieldList(F) : F;
		}
		
	}
	private FieldStructure parseField()
	{//parse attribute definitions
		switch(lookahead)
		{
			case CLOSE_CURLYBRACKET: return null; //case Ԑ(empty string)
			default:
				AnnotationStructure A = new AnnotationStructure(parseAnnotaion());
				ArrayList<Type> T = new ArrayList<>();
				T = TypeList(T);
				expect(Scanner.TOKEN_TYPE.NAME);
				String N=(String)scan.getTokenval();
				match(Scanner.TOKEN_TYPE.NAME);
				match(Scanner.TOKEN_TYPE.SEMICOLON);
				match(Scanner.TOKEN_TYPE.CLOSE_CURLYBRACKET);
			return new FieldStructure(A,T,N);
		}
	}
	private ArrayList<Type> TypeList(ArrayList<Type> T)
	{		
		T.add(parseType());
		switch (lookahead) {
		case CLOSE_CURLYBRACKET: return null;
		case NAME: return T;
		case LS:
			match(Scanner.TOKEN_TYPE.LS);
			T.add(parseType());
			match(Scanner.TOKEN_TYPE.BG);
		default:
			return null;
		}
	}
	private Type parseType()
	{
		
		Scanner.TOKEN_TYPE T=lookahead;
		switch (lookahead) {
		case NAME: return null;
		case INT:
		case STRING:
		case BOOK:
		case PUBLISHER:
		case FLOAT: 
		case DOUBLE:
		case LIST:
			match(lookahead);
			break;
		default:
			break;
		}
		return new Type(T);
	}
}
