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
		writer.close();
		
	}
	public void compileClass() throws IOException{
		if (tokens.get(token_pos).identifier.contains("class")) {
			writer.write("<class>\n");
			//tab++;
			writer.write(tabout() + tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if (tokens.get(token_pos).type == tokenTypes.IDENTIFIER){
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if (tokens.get(token_pos).identifier.contains("{")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		
		while (tokens.get(token_pos+1).identifier.contains("static") || tokens.get(token_pos+1).identifier.contains("field")) {
			compileClassVarDec();
		}
		
		
		
		token_pos--;
		writer.write("</class>");
	}
	public void compileClassVarDec() throws IOException{
		writer.write("<ClassVarDec>\n");
		
		if (tokens.get(token_pos).identifier.contains("static") || tokens.get(token_pos).identifier.contains("field")) {
			//tab++;
			writer.write(tabout() + tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).identifier.equals("int") 
				|| tokens.get(token_pos).identifier.equals("boolean") 
				|| tokens.get(token_pos).identifier.equals("char") 
				|| tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		while(tokens.get(token_pos+1).identifier.equals(",")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).identifier.equals(";")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		writer.write("</classVarDec>\n");
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
	public String tabout() {
		String outstring = "";
		for (int i = 0; i < tab; i++) {
		outstring += "\t";	
		}
		return outstring;
	}
}