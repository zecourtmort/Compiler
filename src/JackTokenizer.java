import java.io.*;
import java.util.*;

public class JackTokenizer{
	
	private Scanner scan;
    private String cmd;
    private char[] symboltable = {'{', '}', '(' ,')', '[' , ']', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~'};
	public JackTokenizer(String filename){
		File infile = new File(filename);
		try {
	           scan = new Scanner(infile);
	           String lines = "";
	           String line = "";
	           
	           while(scan.hasNext()) {
	               line = removeComments(scan.nextLine()).trim();
	               
	               if (line.length() > 0) {
	                   lines += line + "\n";
	               }	
	           }
	           scan = new Scanner(lines.trim());
	        }
	        
	        catch(FileNotFoundException e){
	            System.out.println(infile + " is not a valid file.");
	        }
	        /*cmds.add("add");
	        cmds.add("sub");
	        cmds.add("neg");
	        cmds.add("eq");
	        cmds.add("gt");
	        cmds.add("lt");
	        cmds.add("and");
	        cmds.add("or");
	        cmds.add("not");*/
	}
	
	public void advance(){
        cmd = scan.nextLine();
       String[] splitcmd = cmd.split(" ");
       /*arg1 = "";
       arg2 = -1;*/
       if(splitcmd.length > 3){
           throw new IllegalArgumentException("Too many arguments");
       }
       
	public boolean hasMoreTokens(){
		return scan.hasNext();
	}
	
	public tokenType tokenType(){
		return null;
	}
	public keyWord keyWord(){
		return null;
	}
	public char symbol(){
		return 'a';
	}
	public String identifier(){
		return null;
	}
	public int intVal(){
		return 0;
	}
	public String stringVal(){
		return null;
	}
	public static String removeComments(String s){
        int pos = s.indexOf("//");
        if (pos!=-1) s = s.substring(0, pos);
        return s;
    }
    
    public static String extension(String path) {
        int pos = path.indexOf(".");
        String fileExtension = "";
        if (pos!=-1) fileExtension = path.substring(pos, path.lastIndexOf(path));
        return fileExtension;
    }
}