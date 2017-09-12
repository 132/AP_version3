import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.sun.imageio.plugins.common.InputStreamAdapter;

public class Mainv3 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String trideptrai = "\n@Table(name=\"book\")"
				+ "\npublic interface Book {"
				+ "\n@Id(name=\"id\")"
				+ "\nInteger id;"
				+ "\n@Column(name=\"title\",length=\"80\")"
				+ "\nString title;"
				+ "\n@Many2One(name=\"publisher\",target=\"Publisher\")"
				+ "\nPublisher publisher;"
				+ "\n}"
				
				+ "\n@Table(name=\"publisher\")"
				+ "\npublic interface Publisher {"
				+ "\n@Id(name=\"id\")"
				+ "\nInteger id;"
				+ "\n@Column(name=\"name\", length=\"80\")"
				+ "\nString title;"
				+ "\n@Many2One(name=\"books\", target=\"Book\", Mappedby=\"publisher\")"
				+ "\nList<Book> books;"
				+ "\n}";
		
	FileInputStream t = new FileInputStream("/home/bialan132/Public/EclipseFedora/AP_version3/bin/input.txt");
	Reader reader = new InputStreamReader(t);
	
	Parser parser = new Parser();
	Item  item=parser.parseItem(reader);
	System.out.println(item.print());
	}

}
