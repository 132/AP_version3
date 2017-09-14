
public class JavaGenerator {

	public JavaGenerator() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * public interface InterfaceItem {
					public Object getKeyValue();//return the primary key value
					}
					method print() inside the following classes represent the code generator
					public class Item{..
					public String print(){
					//assignments contains list of assignment like "this.att1=att1"
					while(lista!=null){
					assignments+="\nthis."+lista.getNome()+" = "+lista.getNome()+";";
					lista=lista.next();}
					return "public class "+this.name+" implements InterfaceItem"+
					//the output will be: public tipo1 att1;public tipo2 att2;...
					{\npublic "+list_attribute.print().replaceAll(",",";\npublic ")+
					//the output will be: public name(tipo1 att1,tipo2 att2,..)
					";\npublic "+name+"("+list_attribute.print()+"){"+assignments+
					"\n}\npublic "+list_attribute.getType()+" getKeyValue(){"+
					return this."+list_attribute.getNome()+";}"+
					//two objects are equals if they have the same primary key
					"\npublic boolean equals(Object other){return (this==other)&&(other instanceof
					"+name+")&&(this.getKeyValue()==(("+name+")other).getKeyValue());}"+
					"\npublic int hashCode(){return
					Integer.parseInt(this.getKeyValue().toString());}\n}";
					}
					}
					public class ListAttribute {..
					public String getNome(){return this.nome;}
					public String getType(){return this.type.print();}
					public String print() {String res=type.print()+" "+nome;
					if(list_attribute!=null)res+=','+list_attribute.print();
					return res;
					}
					}
					public class Type{..
					public String print(){//according to type of read token,it prints the
					switch (type){
					 //corresponding type
					case FLOAT: return "Float";
					case STRING: return "String";
					3
					case DOUBLE: return "Double";
					default: return "Integer";}
					}
					}
					The class Collection<T> and Query<T> that are included in APLIQ:
					public class Collection<T extends InterfaceItem>
					{//container is of type Set<E>, there cannot be duplicates within it
					private Set<T> container;
					public Collection() {container=new HashSet<T>();}
					public T get(Object val){//search according primary key
					for(T element: container){
					if(element.getKeyValue().equals(val)) return element;}
					return null;
					}
					public void insert(T elem){container.add(elem);}
					public Query<T> query(){return new Query<T>(container);}
					}
					public class Query<T>{
					private List<T> container;
					private List<ConditionalClause<T>> conditionalclauses;
					private Comparator<T> orderclause;
					public Query(java.util.Collection<T> collection) {
					this.container=new ArrayList<T>(collection);
					this.conditionalclauses=new ArrayList<ConditionalClause<T>>();
					this.orderclause=null;
					}
					//the exeute() method return a List whose elements belong to container and
					satisfy all the clauses in the arrayList
					public List<T> execute() {
					List<T> result=new ArrayList<T>();
					for (T elem:container){ boolean satisfy=true;
					for(ConditionalClause<T> clause:conditionalclauses){
					satisfy=satisfy && clause.match(elem);
					}
					if(satisfy) result.add(elem);//if the elements satisfy all the clauses
					satisfy=true;
					 //then it is returned
					}
					if(orderclause!=null) result.sort(orderclause);
					return result;
					}
					}
					//the object instance of this class represent a single bond applied to result of
					the query
					public class ConditionalClause<T> {
					String attribute_name;Object value;TOKEN_TYPE code_relop;
					public ConditionalClause(String attribute_name, TOKEN_TYPE i,Object value) {
					this.attribute_name = attribute_name;this.value = value;
					this.code_relop = i;
					}
					public boolean match(T elem){//if elem satisfy the clause then returns true
					try{Field campo=elem.getClass().getDeclaredField(attribute_name);
					//attribute_value contains the value of attribute identified by "name" that
					belongs to Object “elem”
					Object attribute_value=campo.get(elem);
					double difference=0;
					if(attribute_value instanceof String)
					difference =((String) attribute_value).compareTo(value.toString()) ;
					else if(attribute_value instanceof Number){
					if(value instanceof Number)
					difference=((Number)attribute_value).doubleValue() -
					((Number)value).doubleValue();
					else return false;
					4
					}
					//code_relop identifies the relational operator of a clause.It uses the
					constants of enum TOKEN_TYPE to identify the corresponding operator
					switch(code_relop){
					case EQ: return (difference==0);
					case NEQ: return (difference!=0);
					case BG: return (difference>0);
					case BGE: return (difference>=0);
					case LS: return (difference<0);
					case LSE: return (difference<=0);
					default: return false;
					}
					}catch(Exception err){throw new RuntimeException();}
					}
					}
					*/
	 
	
}
