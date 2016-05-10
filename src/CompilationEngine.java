import java.io.*;
import java.util.*;

public class CompilationEngine {
	ArrayList<token> tokens;
	FileWriter writer;
	int token_pos = 0;
	int tab = 0;
	
	public CompilationEngine(ArrayList<token> in, String outfile) throws FileNotFoundException, IOException{
		File out = new File(outfile + ".xml");
		if (!out.exists()) {
			out.createNewFile();
		}
		writer = new FileWriter(out.getAbsoluteFile());
		tokens = in;
		for(int i = 0; i < in.size(); i++) {
			token currentToken = in.get(i);
			token nextToken = in.get(i);
			System.out.println("<" + currentToken.type + "> " + currentToken.identifier + " </" + currentToken.type + ">");
			String compileThis = currentToken.identifier;
		}
		
		compileClass();
	}
	public void compileClass() throws IOException{
		if (tokens.get(token_pos).identifier.contains("class")) {
			writer.write("<class>");
		}
		
		
		writer.write("</class>");
	}
	public void compileClassVarDec(){
		
	}
	public void compileSubroutine(){
		
	}
	public void compileParameterList(){
		
	}
	public void compileVarDec(){
		
	}
	public void compileStatements(){
		
	}
	public void compileDo(){
		
	}
	public void compileLet(){
		
	}
	public void compileWhile(){
		
	}
	public void compileReturn(){
		
	}
	public void compileIf(){
		
	}
	public void CompileExpression(){
		
	}
	public void CompileTerm(){
		
	}
	public void CompileExpressionList(){
		
	}
}