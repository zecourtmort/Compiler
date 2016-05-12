import java.io.*;
import java.util.*;

public class CompilationEngine {
	ArrayList<token> tokens;
	FileWriter writer;
	int token_pos = 0;
	int tab = 0;
	String OPS = "+-*/&|<>=";
	String UNOPS = "~-";
	
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
		writer.write("</class>\n");
	}
	public void compileClassVarDec() throws IOException{
		writer.write("<ClassVarDec>\n");
		
		token_pos++;
		
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
		writer.write("<subroutineDec>\n");
		
		token_pos++;
		
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
			
		}
		
		if(!tokens.get(token_pos).identifier.equals(")")) {
			compileParameterList();
		}
		
		token_pos++;
		if(tokens.get(token_pos).identifier.equals(")")) {
			writer.write(tokens.get(token_pos).toString());
			token_pos++;
		}
		
		writer.write("<subroutineBody>\n");
		
		if(t().identifier.equals("{")) {
			writer.write(t().toString());
		}
		
		while(t(1).identifier.equals("var")){
			compileVarDec();
		}
		
		
		if(!tokens.get(token_pos+1).identifier.equals("}")) {
			compileStatements();
		}
		
		token_pos++;
		if(tokens.get(token_pos).identifier.equals("}")) {
			writer.write(tokens.get(token_pos).toString());
		}
		
		writer.write("</subroutineBody>\n");
		writer.write("</subroutineDec>\n");
		
	}
	public void compileParameterList() throws IOException{
		writer.write("<parameterList>\n");
		
		token_pos++;
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
		
		writer.write("</parameterList>\n");
	}
	
	public void compileVarDec() throws IOException{
		writer.write("<variableDec>\n");
		token_pos++;
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
		writer.write("</variableDec>\n");
	}
	
	public void compileStatements() throws IOException{
		writer.write("<statementList>\n");
		
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
		writer.write("</statementList>\n");
	}
	
	public void compileDo() throws IOException{
		writer.write("<doStatement>\n");
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
			
		
		writer.write("</doStatement>\n");
	}
	public void compileLet() throws IOException{
		writer.write("<letStatement>\n");
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
		
		writer.write("</letStatement>\n");
	}
	
	public void compileWhile() throws IOException{
		writer.write("<whileStatement>\n");
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
		
		writer.write("</whileStatement>\n");
	}
	
	public void compileReturn() throws IOException{
		writer.write("<returnStatement>\n");
		
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
		
		writer.write("</returnStatement>\n");
	}
	
	public void compileIf() throws IOException{
		writer.write("<ifStatement>\n");
		if(t().identifier.equals("if")) {
			writer.write(t().toString());
			token_pos++;
		}
		
		if(t().identifier.equals("(")) {
			writer.write(t().toString());
		}
		
		compileExpression();
		
		token_pos++;
		
		if(t().identifier.equals(")")) {
			writer.write(t().toString());
			token_pos++;
		}
		
		if(t().identifier.equals("{")) {
			writer.write(t().toString());
		}
		
		if(!t().identifier.equals("}")) {
			compileStatements();
		}
		
		if(t().identifier.equals("}")) {
			writer.write(t().toString());
			token_pos++;
		}
		
		if(t().identifier.equals("else")) {
			writer.write(t().toString());
			token_pos++;
			
			if(t().identifier.equals("{")) {
				writer.write(t().toString());
			}
			
			if(!t().identifier.equals("}")) {
				compileStatements();
			}
			
			if(t().identifier.equals("}")) {
				writer.write(t().toString());
				token_pos++;
			}
			
		}
		
		writer.write("</ifStatement>\n");
	}
	
	public void compileExpression() throws IOException{
		writer.write("<expression>\n");
		
		compileTerm();
		
		while (OPS.contains(t(1).identifier)) {
			token_pos++;
			writer.write(t().toString());
			compileTerm();
		}
		writer.write("</expression>\n");
	}
	
	public void compileTerm() throws IOException{
		writer.write("<term>\n");
		if (UNOPS.contains(t(1).identifier)) {
			token_pos++;
			writer.write(t().toString());
		}
		
		token_pos++;
		if (t().type == tokenTypes.INT_CONST) {
			writer.write(t().toString());
		}
		else if(t().type == tokenTypes.STRING_CONST) {
			writer.write(t().toString());
		}
		else if(t().type == tokenTypes.IDENTIFIER && t(1).identifier.equals("[")) {
			writer.write(t().toString());
			token_pos++;
			writer.write(t().toString());
			
			compileExpression();
			
			token_pos++;
			if(t().identifier.equals("]")) {
				writer.write(t().toString());
			}
		}
		
		else if(t().type == tokenTypes.IDENTIFIER && t(1).identifier.equals("(")) {
			writer.write(t().toString());
			token_pos++;
			writer.write(t().toString());
			
			compileExpressionList();
			
			token_pos++;
			if(t().identifier.equals(")")) {
				writer.write(t().toString());
			}
		}
		
		else if(t().type == tokenTypes.IDENTIFIER && t(1).identifier.equals(".")) {
			writer.write(t().toString());
			token_pos++;
			writer.write(t().toString());
			
			token_pos++;
			
			if (t().type == tokenTypes.IDENTIFIER) {
				writer.write(t().toString());
			}
			
			token_pos++;
			if(t().identifier.equals("(")) {
				writer.write(t().toString());
			}
			
			compileExpressionList();
			
			token_pos++;
			if(t().identifier.equals(")")) {
				writer.write(t().toString());
			}
		}
		
		else if(t().type == tokenTypes.IDENTIFIER) {
			writer.write(t().toString());
		}
		
		else if(t().identifier.equals("(")) {
			writer.write(t().toString());
			token_pos++;
			
			compileExpression();
			
			token_pos++;
			
			if(t().identifier.equals(")")) {
				writer.write(t().toString());
			}
		}
		else {
			System.out.println("you may have screwed up somewhere.");
		}
		
		writer.write("</term>\n");
	}
	
	public void compileExpressionList() throws IOException{
		writer.write("<expressionList>\n");
		
		if(t(1).identifier.equals(")")) {
			writer.write("</expressionList>\n");
			return;
		}
		
		compileExpression();
		
		while(t(1).identifier.equals(",")) {
			token_pos++;
			writer.write(t().toString());
			
			compileExpression();
		}
		writer.write("</expressionList>\n");
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