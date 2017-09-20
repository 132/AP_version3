import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.corba.se.pept.transport.Connection;
import com.sun.imageio.plugins.common.InputStreamAdapter;
import com.sun.org.apache.bcel.internal.generic.LoadClass;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.net.URL;
import java.net.URLClassLoader;

public class Mainv3 {
	private static void printLines(String name, InputStream ins) throws Exception 
	{
	    String line = null;
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) 
	    {
	        System.out.println(name + " " + line);
	    }
	}
	public static void runProcess(String command) throws Exception 
	{
		Process pro = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", pro.getInputStream());
		printLines(command + " stderr:", pro.getErrorStream());
		pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
	}
	
	public static void main(String[] args) throws Exception
	{	
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
		try 
		{
			fis = new FileInputStream("/home/bialan132/Public/EclipseFedora/AP_version3/bin/input.txt");
			
			
			System.out.println("Total file size to read (in bytes) : \n"
					+ fis.available());
			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				System.out.print((char) content);
			}
	
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
		System.out.println("\n===========================================================");
		try
		{
			fis = new FileInputStream("/home/bialan132/Public/EclipseFedora/AP_version3/bin/input.txt");
			Reader reader = new InputStreamReader(fis);
			Parser parser = new Parser();
			IL = parser.parseMain(reader);
			System.out.println("\n===================================================================================");
	
			System.out.println(IL.size() + " ------------------------------------------------------------------------------");
			for(int i=0; i<IL.size();i++)
			{
				System.out.println(i + " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				IL.get(i).printInterface();
			}
			System.out.println("\n===================================================================================");
			
			
			JavaGenerator writeJava= new JavaGenerator(IL);
			
			writeJava.printNewType();
			
			
			SQLGenerator writeSQL = new SQLGenerator(IL);
			writeSQL.printNewType();
			
			
			
		/*	try {
				File f1 = new File("/home/bialan132/Public/EclipseFedora/AP_version3/src/Book.java");
				File f2 = new File("/home/bialan132/Public/EclipseFedora/AP_version3/src/Publisher.java");
				//if(f1.exists() && f1.isDirectory() && f2.exists() && f2.isDirectory()) { 
					System.out.println("Existing some important files ........................................................");
				    // do something
				runProcess("javac  /home/bialan132/Public/EclipseFedora/AP_version3/src/Book.java");
				runProcess("javac  /home/bialan132/Public/EclipseFedora/AP_version3/src/Publisher.java");
				
				 URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
	                // Load the class from the classloader by name....
	                
	                java.lang.ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
	                
	                //Get the URLs
	                URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
	         
	                for(int i=0; i< urls.length; i++)
	                {
	                    System.out.println(urls[i].getFile());
	                }       
	                File compileFile = new File("src/Book.class");
	                compileFile.renameTo(new File("bin/Book.class"));
	                
	                Class<?> loadedClass = classLoader.loadClass("Book");
	                // Create a new instance...
	                Object obj = loadedClass.newInstance();
	                System.out.println(obj.getClass().toString());
			//	}
			} catch (Exception e) {
				e.printStackTrace();
			}
		*/
		
			// Compile new Classes
			 /** Compilation Requirements *********************************************************************************************/
		try {	
			
			for(InterfaceStructure in : IL) 
			{
				File f1 = new File("/home/bialan132/Public/EclipseFedora/AP_version3/src/Book.java");
				File f2 = new File("/home/bialan132/Public/EclipseFedora/AP_version3/src/Publisher.java");
				System.out.println(f1.isDirectory());
				System.out.println(f1.exists());
				System.out.println(f2.isDirectory());
				System.out.println(f2.exists());
				
				
				File helloWorldJava = new File("src/"+in.printName()+".java");
						
				DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
	            
	            // This sets up the class path that the compiler will use.
	            // I've added the .jar file that contains the DoStuff interface within in it...
	            List<String> optionList = new ArrayList<String>();
	            optionList.add("-classpath");
	            optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");
	            
	            Iterable<? extends JavaFileObject> compilationUnit
	                    = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
	            JavaCompiler.CompilationTask task = compiler.getTask(
	                null, 
	                fileManager, 
	                diagnostics, 
	                optionList, 
	            //    null,
	                null, 
	                compilationUnit);
	           
	            /********************************************************************************************* Compilation Requirements **/
	            if (task.call()) 
	            {
	                /** Load and execute *************************************************************************************************/
	                System.out.println("Yipe");
	                System.out.println("tridpetraivodoi");
	                // Create a new custom class loader, pointing to the directory that contains the compiled
	                // classes, this should point to the top of the package structure!
	                URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
	                // Load the class from the classloader by name....
	                
	                java.lang.ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
	                
	                //Get the URLs
	                URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
	         
	                for(int i=0; i< urls.length; i++)
	                {
	                    System.out.println(urls[i].getFile());
	                }       
	                File compileFile = new File("src/" + in.printName() + ".class");
	                compileFile.renameTo(new File("bin/"+ in.printName() + ".class"));
	                
	                Class<?> loadedClass = classLoader.loadClass(in.printName());
	                // Create a new instance...
	                Object obj = loadedClass.newInstance();
	                System.out.println(obj.getClass().toString());
	                
	                // Santity check
	             
	                /************************************************************************************************* Load and execute **/
	            } else
	            {
	                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) 
	                {
	                    System.out.format("Error on line %d in %s%n",
	                            diagnostic.getLineNumber(),
	                            diagnostic.getSource().toUri());
	                }
	            }
	            fileManager.close();
			}
	        }catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exp) 
	        {
	            exp.printStackTrace();
	        }
			
		
	
/*
 * '
			try {
				File f1 = new File("/home/bialan132/Public/EclipseFedora/AP_version3/src/Book.java");
				File f2 = new File("/home/bialan132/Public/EclipseFedora/AP_version3/src/Publisher.java");
				if(f1.exists() && f1.isDirectory() && f2.exists() && f2.isDirectory()) { 
					System.out.println("Existing some important files ");
				    // do something
				runProcess("javac  /home/bialan132/Public/EclipseFedora/AP_version3/src/Book.java");
				runProcess("javac  /home/bialan132/Public/EclipseFedora/AP_version3/src/Publisher.java");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	*/		
			
	//		IL.get(0).
			//Book trideptraivodoi;
			/*
			 * Exercise 4 so fucking hard
			 */
			// 
/*			
			ArrayList<Class> ListofClass = new ArrayList<>();
			for(InterfaceStructure I : IL)
			{
				URL classUrl = new URL("file:///home/bialan132/Public/EclipseFedora/AP_version3/src/Book.class");
				URL[] classUrls = { classUrl };
				URLClassLoader ucl = new URLClassLoader(classUrls);
		        Class c = ucl.loadClass("IndependentClass");
				Class a;
				ClassLoader newClass = new ClassLoader();
				a = newClass.loadClass("Book");
				ListofClass.add(a);
			//	Class c = ucl.loadClass("com.mypackage.IndependentClass");
			}*/
			System.out.println("==============================================================");
		//	System.out.println(ListofClass.get(0).getSimpleName());
			System.out.println("==============================================================");
			// Second Main
			System.out.println("\n===================== Complete Generator ========================\n");

	/*		try {
		//		runProcess("javac -cp . /home/bialan132/Public/EclipseFedora/AP_version3/src/SecondMain.java");
		//		runProcess("java -cp /home/bialan132/Public/EclipseFedora/AP_version3/src SecondMain");
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
	 */
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
