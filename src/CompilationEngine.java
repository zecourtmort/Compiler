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
		
		while (tokens.get(token_pos+1).identifier.contains("constructor") || tokens.get(token_pos+1).identifier.contains("function") || tokens.get(token_pos+1).identifier.contains("method")) {
			compileSubroutine();
		}
		
		if (tokens.get(token_pos).identifier.equals("}")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
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
	
	public void compileSubroutine() throws IOException {
		writer.write("<subroutine>\n");
		
		if(tokens.get(token_pos).identifier.equals("constructor") || tokens.get(token_pos).identifier.equals("function") || tokens.get(token_pos).identifier.equals("method")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).identifier.equals("void") || tokens.get(token_pos).identifier.equals("int") || tokens.get(token_pos).identifier.equals("char") || tokens.get(token_pos).identifier.equals("boolean") || tokens.get(token_pos).type ==  tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).identifier.equals("(")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(!tokens.get(token_pos).identifier.equals(")")) {
			compileParameterList();
		}
		
		if(tokens.get(token_pos).identifier.equals(")")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		writer.write("<subroutineBody>\n");
		
		if(!tokens.get(token_pos).identifier.equals("}")) {
			compileStatements();
		}
		
		if(tokens.get(token_pos).identifier.equals("}")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		writer.write("</subroutineBody>");
		writer.write("<subroutineDec>");
		
	}
	public void compileParameterList() throws IOException{
		writer.write("<parameterList>");
		if(tokens.get(token_pos).identifier.equals("int") || tokens.get(token_pos).identifier.equals("char") || tokens.get(token_pos).identifier.equals("boolean") || tokens.get(token_pos).type ==  tokenTypes.IDENTIFIER) {
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
		
			if(tokens.get(token_pos).identifier.equals("int") || tokens.get(token_pos).identifier.equals("char") || tokens.get(token_pos).identifier.equals("boolean") || tokens.get(token_pos).type ==  tokenTypes.IDENTIFIER) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
		
			if(tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
		}
		
		writer.write("</parameterList>");
	}
	
	public void compileVarDec() throws IOException{
		writer.write("<variableDec>");
		
		if(tokens.get(token_pos).identifier.equals("var")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(tokens.get(token_pos).identifier.equals("void") ||tokens.get(token_pos).identifier.equals("int") || tokens.get(token_pos).identifier.equals("char") || tokens.get(token_pos).identifier.equals("boolean") || tokens.get(token_pos).type ==  tokenTypes.IDENTIFIER) {
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
		
			if(tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
		}
		writer.write("</variableDec>");
	}
	
	public void compileStatements() throws IOException{
		writer.write("<statementList>");
		
		while(!tokens.get(token_pos+1).identifier.equals("}")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		
			if(tokens.get(token_pos).identifier.equals("let")) {
				compileLet();
			}
			
			else if(tokens.get(token_pos).identifier.equals("if")) {
				compileIf();
			}
			
			else if(tokens.get(token_pos).identifier.equals("while")) {
				compileWhile();
			}
			
			else if(tokens.get(token_pos).identifier.equals("do")) {
				compileDo();
			}
			
			else if(tokens.get(token_pos).identifier.equals("return")) {
				compileReturn();
			}
		}
		writer.write("</statementList>");
	}
	
	public void compileDo() throws IOException{
		writer.write("<doStatement>");
		writer.write(tokens.get(token_pos).toString());
		token_pos++;
		
		if(tokens.get(token_pos+1).identifier.equals("(") && tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
			
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
			
			compileExpressionList();
			
			if (tokens.get(token_pos).identifier.equals(")")) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
			
		}
		
		else if(tokens.get(token_pos+1).identifier.equals(".") && tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
			
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
			
			if (tokens.get(token_pos).type == tokenTypes.IDENTIFIER) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
			
			if(tokens.get(token_pos).identifier.equals("(")) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
			
			compileExpressionList();
			
			
			
			if (tokens.get(token_pos).identifier.equals(")")) {
				writer.write(tokens.get(token_pos).toString());
				token_pos++;
			}
		}
		
		if (tokens.get(token_pos).identifier.equals(";")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
			
		
		writer.write("</doStatement>");
	}
	public void compileLet() throws IOException{
		writer.write("<letStatement>");
		if (tokens.get(token_pos).equals("let")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(t(0).type == tokenTypes.IDENTIFIER) { //I got mad about writing out tokens.get(token_pos) every single time. t(0) and t() do the same thing.
			writer.write(t(0).toString());
		}
		
		if(t(1).identifier.equals("[")){
			token_pos++;
			writer.write(t(0).toString());
			
			compileExpression();
			
			token_pos++;
			if (t(0).identifier.equals("]")) {
				writer.write(t(0).toString());
				token_pos++;
			}
		}
		
		if(t().identifier.equals("=")) {
			writer.write(t().toString());
		}
		
		compileExpression();
		
		token_pos++;
		if(t().identifier.equals(";")){
			writer.write(t().toString());
			token_pos++;
		}
		
		writer.write("</letStatement>");
	}
	
	public void compileWhile() throws IOException{
		writer.write("<whileStatement>");
		if (tokens.get(token_pos).equals("while")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		if(t(0).identifier.equals("(")){
			writer.write(t(0).toString());
		}
		
		compileExpression();
		token_pos++;
		
		if (t(0).identifier.equals(")")) {
			writer.write(t(0).toString());
			token_pos++;
		}
		
		
		if(t().identifier.equals("{")) {
			writer.write(t().toString());
		}
		
		if(!t(1).identifier.equals("}")) {
			compileStatements();
		}
		
		token_pos++;
		
		if(t().identifier.equals("}")){
			writer.write(t().toString());
			token_pos++;
		}
		
		writer.write("</whileStatement>");
	}
	
	public void compileReturn() throws IOException{
		writer.write("<returnStatement>");
		
		if(t().identifier.equals("return")) {
			writer.write(t().toString());
		}
		
		if(!t(1).identifier.equals(";")) {
			compileExpression();
		}
		
		token_pos++;
		if(t(0).identifier.equals(";")) {
			writer.write(t().toString());
			token_pos++;
		}
		
		writer.write("</returnStatement>");
	}
	
	public void compileIf() throws IOException{
		
	}
	public void compileExpression() throws IOException{
		
	}
	public void compileTerm() throws IOException{
		
	}
	public void compileExpressionList() throws IOException{
		
	}
	public String tabout() {
		String outstring = "";
		for (int i = 0; i < tab; i++) {
		outstring += "\t";	
		}
		return outstring;
	}
	
	public token t(int i) {
		return tokens.get(token_pos + i);
	}
	
	public token t() {
		return tokens.get(token_pos);
	}
}