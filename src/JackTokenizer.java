import java.io.*;
import java.util.*;
public class JackTokenizer{
	
	private Scanner scan;
    private String tkn;
    private token[] Tokens;
    private String[] tokenType = {"keyword", "symbol", "identifier", "int_const", "string_const" };
    private String[] keyword = {"class","method","function","constructor","int","boolean","char",
    							"void","var","static","field","let","do","if","else","while",
    							"return","true","false","null","this"};
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
	}
	
	public void advance(){
        tkn = scan.next();
        String[] tknsplit;
        if(tkn.contains("(")||tkn.contains(")")){
        	tknsplit=tkn.split("(");
        	token hold = new token(tokenTypes.IDENTIFIER, tknsplit[0]);
        }
	}
       
	public boolean hasMoreTokens(){
		return scan.hasNext();
	}
	
	public tokenTypes tokenType(){
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