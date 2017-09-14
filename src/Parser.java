import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	ArrayList<InterfaceStructure> IL = new ArrayList<>();
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
	
	public ArrayList<InterfaceStructure> parseMain(Reader r)
	{
		scan =new Scanner(r);
		 //Parse Annotation:  Annotation -> @ Table ( name = " Book " )
		lookahead = scan.nextToken();
		
		return parseInterfaceList(IL);
	}

	public ArrayList<InterfaceStructure> parseInterfaceList(ArrayList<InterfaceStructure> IL)
	{

		switch (lookahead) {
		case EOF: System.out.println("Trideptrai ============1 ");
			return null;
		case AT:
			
			IL.add(parseInterface());
			//match(Scanner.TOKEN_TYPE.EOF);
			System.out.println("Trideptrai ============ 2 " + lookahead);
			
			if(lookahead == Scanner.TOKEN_TYPE.EOF)
			{
				System.out.println("Trideptrai ============ 3");
				return IL;
			}
				
			else 
			{
				System.out.println("Trideptrai ============ 4");
				
				return parseInterfaceList(IL);
			}
			
				
			//return (lookahead == Scanner.TOKEN_TYPE.EOF) ? IL : parseInterfaceList(IL,r);
		default:
			return null;
		}
	}
	
	public InterfaceStructure parseInterface()
	{//parse item definition,and return an abstract
		switch (lookahead) {
		case AT:
			AnnotationStructure A = new AnnotationStructure(parseAnnotaion());
			A.printCodeAnnotations();
			match(Scanner.TOKEN_TYPE.PUBLIC);
		//	match(Scanner.TOKEN_TYPE.INTERFACE);
			expect(Scanner.TOKEN_TYPE.NEWTYPE);
			String N = (String) scan.getTokenval();
			Scanner.symbol_table.put(N,Scanner.TOKEN_TYPE.NEWTYPE);
			match(Scanner.TOKEN_TYPE.NEWTYPE);
			match(Scanner.TOKEN_TYPE.OPEN_CURLYBRACKET);
			//the next attributes
			ArrayList<FieldStructure> F = new ArrayList<>();
			F = FieldList(F);
			
			System.out.println(F.size());
			F.get(0).printName();
			System.out.println(F.get(0).printName());
			
		
			
			for(int i =0 ; i<F.size();i++)
				F.get(i).printField();
			
			
			match(Scanner.TOKEN_TYPE.CLOSE_CURLYBRACKET);
		//	match(Scanner.TOKEN_TYPE.EOF);
			return new InterfaceStructure(A,N,F);
		default:
			return null;
		}
		
	}
	private AnnotationStructure parseAnnotaion()
	{
		switch (lookahead) {
		case CLOSE_BRACKET: return null;
		default:
			match(Scanner.TOKEN_TYPE.AT); 
			
			expect(Scanner.TOKEN_TYPE.NAME); 
			String name = (String) scan.getTokenval();
			System.out.println("Parsing Annotation Next NAME: " + lookahead + " " + name);
			match(Scanner.TOKEN_TYPE.NAME);
			System.out.println("Parsing Annotation Next OPENBRACKET: " + lookahead +  " " + scan.getTokenval());
			match(Scanner.TOKEN_TYPE.OPEN_BRACKET);
			
			Map<String,String> KeyValues = new HashMap<String, String>();
			KeyValues = KeyValuesList(KeyValues);
			
			System.out.println("Parsing Annotation Next CLOSEBRACKET: " + lookahead + " " + name);
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
			
			System.out.println("Parsing Annotation Next NAME: " + lookahead);
			expect(Scanner.TOKEN_TYPE.KEY);
			
			String K = (String) scan.getTokenval();
			System.out.println("Parsing Annotation Next NAME: " + lookahead + " " + K);
			match(Scanner.TOKEN_TYPE.KEY);
			match(Scanner.TOKEN_TYPE.ASSIGN);
			match(Scanner.TOKEN_TYPE.DQUOTE);
			System.out.println("Parsing Annotation Next NAME: " + lookahead);
			String V;
			switch (lookahead) {
			case NUMBER:
				
				expect(Scanner.TOKEN_TYPE.NUMBER);
				V = (String) String.valueOf(scan.getTokenval());
				System.out.println("Parsing Annotation Next NUMBER: " + lookahead + " " + V);
				match(Scanner.TOKEN_TYPE.NUMBER);
				break;
			case VALUE:
				
				expect(Scanner.TOKEN_TYPE.VALUE);
				V = (String) scan.getTokenval();
				System.out.println("Parsing Annotation Next NAME: " + lookahead + " " + V);
				match(Scanner.TOKEN_TYPE.VALUE);
				break;
			default:
				V= null;
				break;
			}
			System.out.println("Parsing Annotation Next : " + lookahead + " " + V);
			match(Scanner.TOKEN_TYPE.DQUOTE);
			System.out.println("Parsing Annotation Next : " + K + " " + V);
			KeyValues.put(K, V);
			if (lookahead == Scanner.TOKEN_TYPE.COMMA) {match(Scanner.TOKEN_TYPE.COMMA);  return KeyValuesList(KeyValues);}
			else return KeyValues;
		}
	}
	private ArrayList<FieldStructure> FieldList(ArrayList<FieldStructure> F)
	{
		switch(lookahead)
		{
		case CLOSE_CURLYBRACKET: return F;
		
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
				
				
				System.out.println("Parsing before TYPE: " + lookahead );
				ArrayList<Type> T = new ArrayList<>();
				T = TypeList(T);
				System.out.println("Number of TYPES " + T.size());
				
				
				System.out.println("Parsing Next NAME: " + lookahead );
				
				expect(Scanner.TOKEN_TYPE.NAME);
				
				String N=(String)scan.getTokenval();
				System.out.println("Parsing Next NAME: " + lookahead + " " + N);
				match(Scanner.TOKEN_TYPE.NAME);
				System.out.println("Parsing Next SEMICOLON: " + lookahead);
				match(Scanner.TOKEN_TYPE.SEMICOLON);
				System.out.println("Parsing after SEMICOLON: " + lookahead);
			return new FieldStructure(A,T,N);
		}
	}
	private ArrayList<Type> TypeList(ArrayList<Type> T)
	{	
		System.out.println("Check add TYPE " + lookahead);
		Type t = new Type(parseType());
				
		System.out.println("Check add TYPE 2 " + t.printType());
		if(!t.printType().equals("NULL")) 
		{
			
			System.out.println("\t Check add TYPE 1 " + lookahead);
			T.add(t);
			System.out.println("\t Check add TYPE 2 " + lookahead);
		}
		System.out.println("Parsing List TYPE: " + lookahead );
		switch (lookahead) {
		case CLOSE_CURLYBRACKET: return null;
		case NAME: return T;
		
		case LS:
			System.out.println("Parsing LS: " + lookahead + " " + scan.getTokenval());
			match(Scanner.TOKEN_TYPE.LS);
			System.out.println("Parsing NAME: " + lookahead + " " + scan.getTokenval());
			T = TypeList(T);
			return T;
	//		break;
		case BG:
			System.out.println("Parsing BG: " + lookahead + " " + scan.getTokenval());
			match(Scanner.TOKEN_TYPE.BG);
			System.out.println("Parsing NAME: " + lookahead + " " + scan.getTokenval());
			return (lookahead == Scanner.TOKEN_TYPE.NAME)? T : TypeList(T);
		default:
			return null;
		}
		
		//return T;
	}
	private Type parseType()
	{
		Scanner.TOKEN_TYPE T=lookahead;
		switch (lookahead) {
		case NAME: 
			System.out.println("trideptrai---------- Parsing Type: " + lookahead + " " + scan.getTokenval());
			String NameOut = scan.getTokenval().toString();
			match(Scanner.TOKEN_TYPE.NAME);
			
			System.out.println("trideptrai---------- Parsing Type next NAME: " + lookahead + " " + scan.getTokenval());
			
			return new Type(NameOut);
		case NEWTYPE: 
			
			for(int i=0;i<Scanner.symbol_table.size();i++)
			{
				System.out.println("NEWTYPE fucking complex1 " + scan.getTokenval());
				if(Scanner.symbol_table.get(scan.getTokenval()).equals(Scanner.TOKEN_TYPE.NEWTYPE))
				{
					System.out.println("NEWTYPE fucking complex2 "+ lookahead+ " " + scan.getTokenval().toString());
					String NewTypeOut = scan.getTokenval().toString();
					match(Scanner.TOKEN_TYPE.NEWTYPE);
					System.out.println("NEWTYPE fucking complex3 " + lookahead);
					
					return new Type(NewTypeOut);
				}
				
			}

			System.out.println("trideptrai---------- Parsing Type: " + lookahead + " " + scan.getTokenval());
			match(Scanner.TOKEN_TYPE.NEWTYPE);
			System.out.println("trideptrai---------- Parsing Type next NAME: " + lookahead + " " + scan.getTokenval());
			return new Type(T);
		case INT:	System.out.println("Check Type int");
		case STRING:
	//	case BOOK:
	//	case PUBLISHER:
		case FLOAT: 
		case DOUBLE:
		case LIST:	
			System.out.println("Parsing Type: " + lookahead + " " + scan.getTokenval());
			match(lookahead);
			System.out.println("Parsing Type: " + lookahead + " " + scan.getTokenval());
			return new Type(T);
		default:
			return new Type(Scanner.TOKEN_TYPE.NULL);
		}
		
	}
}
