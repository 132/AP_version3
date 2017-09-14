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

	public static void main(String[] args){
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
		
	ArrayList<InterfaceStructure> IL = new ArrayList<>();
	
//	FileInputStream t = new FileInputStream("/home/bialan132/Public/EclipseFedora/AP_version3/bin/input.txt");
//	Reader reader = new InputStreamReader(t);
	FileInputStream fis;
	try {
		fis = new FileInputStream("/home/bialan132/Public/EclipseFedora/AP_version3/bin/input.txt");
		
		
		System.out.println("Total file size to read (in bytes) : \n"
				+ fis.available());
		int content;
		while ((content = fis.read()) != -1) {
			// convert to char and display it
			System.out.print((char) content);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} 
	System.out.println("\n===========================================================");
	try {
		fis = new FileInputStream("/home/bialan132/Public/EclipseFedora/AP_version3/bin/input.txt");
		Reader reader = new InputStreamReader(fis);
		Parser parser = new Parser();
		IL = parser.parseMain(reader);
//		IL.get(0).
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

}
