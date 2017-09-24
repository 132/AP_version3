
public class Book extends Publisher implements SupClass{
protected Integer id;
protected String title;
protected Publisher publisher;
public Book(){}
public Book(Integer id_temp,String title_temp,Publisher publisher_temp){
this.id =id_temp;
this.title =title_temp;
this.publisher =publisher_temp;
}}
