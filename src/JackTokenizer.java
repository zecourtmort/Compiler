import java.io.*;
import java.util.*;
import java.lang.Object;
public class JackTokenizer{
	
	private Scanner scan;
    private String tkn;
    private ArrayList<token> Tokens=new ArrayList();
    private ArrayList<String> kword = new ArrayList();
    private ArrayList<String> symbols = new ArrayList();
    private String[] tokenType = {"keyword", "symbol", "identifier", "int_const", "string_const" };
    private String[] keyword = {"class","method","function","constructor","int","boolean","char",
    							"void","var","static","field","let","do","if","else","while",
    							"return","true","false","null","this"};
    private String[] symboltable = {"{", "}", "(" ,"'", "[" , "]", ".", ",", ";", "+", "-", "*", "/", "&", "|", "<", ">", "=", "~"};
	public JackTokenizer(String filename){
		File infile = new File(filename);
	    for(int i = 0; i < keyword.length; i++){
			kword.add(keyword[i]);
		}
	    for(int j = 0; j < symboltable.length; j++){
			symbols.add(symboltable[j]);
		}
		try {
	           scan = new Scanner(infile);
	          String lines = "";
	           String line = "";
	           boolean multiLineComment = false;
	           while(scan.hasNext()) {
	               line = removeComments(scan.nextLine()).trim();	
	               boolean end = false;
	               int posstar = line.indexOf("/*");
	               	if(line.contains("*/")){
	               		end = true;
	               	}
	               if(posstar !=-1){
	               	line=line.substring(0, posstar);
	               	while(!end){
	               		String hol;
	               		if(scan.hasNext()){
	               			hol = scan.nextLine();
		               		if(hol.contains("*/")){
		               			end = true;
		               		}
	               		}
	               		else{
	               			end = true;
	               		}
	               	}
	               }
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
        String tkns;
        String tknsplit[];
        System.out.println(tkn);
        if(kword.contains(tkn)){
        	token keyword = new token(tokenTypes.KEYWORD, tkn);
        	Tokens.add(keyword);
        }
        else if(symbols.contains(tkn)){
        		if(tkn.contains("<")){
        			Tokens.add(new token(tokenTypes.SYMBOL,"&lt;"));
        		}
        		else if(tkn.contains(">")){
        			Tokens.add(new token(tokenTypes.SYMBOL,"&gt;"));
        		}
        		else if(tkn.contains("&")){
        			Tokens.add(new token(tokenTypes.SYMBOL,"&amp;"));
        		}
        		else if(tkn.contains("\"")){
        			Tokens.add(new token(tokenTypes.SYMBOL,"&quot;"));
        		}
        		else{
        			token symbol = new token(tokenTypes.SYMBOL, tkn);
        			Tokens.add(symbol);
        		}
        }
        else if(tkn.contains(".")){
        		//System.out.println("here");
        		tkns = tkn.replace("."," ");
        		//System.out.println(tkns);
        		tknsplit = tkns.split(" ");
        		//System.out.println(tknsplit[0]);
        		if(tknsplit[0].matches(".\\d.*")){
        			Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[0]));
        		}
        		else if(kword.contains(tknsplit[0])){
        			Tokens.add(new token(tokenTypes.KEYWORD,tknsplit[0]));
        		}
        		else{
        			Tokens.add(new token(tokenTypes.IDENTIFIER, tknsplit[0]));
        		}
        		//System.out.println(identifier.toString());
        		//System.out.println(Tokens.size());
        		//Tokens.add(identifier);
        		//System.out.println(Tokens.size());
        		Tokens.add(new token(tokenTypes.SYMBOL, "."));
        		//System.out.println(Tokens.size());
        		parseParens(tknsplit[1]);

        }
        else if(tkn.contains("[")){
        	int arguments=0;
        	int tknsp=1;
        	int total;
        	String[]args;
        	for(int i = 0; i<tkn.length();i++){
        		if(tkn.charAt(i)=='['){
        			arguments++;
        		}
        	}
    		tkns = tkn.replace("["," ");
    		tknsplit= tkns.split(" ");
    		total = arguments;
    		if(tknsplit[0].matches(".\\d.*")){
    			Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[0]));
    		}
    		else if(kword.contains(tknsplit[0])){
    			Tokens.add(new token(tokenTypes.KEYWORD,tknsplit[0]));
    		}
    		else{
    			Tokens.add(new token(tokenTypes.IDENTIFIER, tknsplit[0]));
    		}
    		Tokens.add(new token(tokenTypes.SYMBOL,"["));
        	while(arguments > 0){
        		String hold = tknsplit[tknsp].replace("]", " ");
        		args=hold.split(" ");
        		Tokens.add(new token(tokenTypes.IDENTIFIER,args[0]));
        		arguments--;
        		tknsp++;
        	}
        	for(int j = 0; j < total;j++){
        		Tokens.add(new token(tokenTypes.SYMBOL,"]"));
        	}
        	if(tkn.contains(";")){
        		Tokens.add(new token(tokenTypes.SYMBOL,";"));
        	}
        }
        else if(tkn.contains("(")){
        	parseParens(tkn);
        }
        else{
        	if(tkn.matches(".*\\d.*")){
        		if(tkn.contains(")")){
        			int arguments=0;
                	for(int i = 0; i<tkn.length();i++){
                		if(tkn.charAt(i)==')'){
                			arguments++;
                		}
                	}
                	tkns = tkn.replace(")"," ");
                	tknsplit = tkns.split(" ");
                	Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[0]));
                	for(int j = 0; j<arguments;j++){
                		Tokens.add(new token(tokenTypes.SYMBOL, ")"));
                	}
                	if(tknsplit.length>1){
                		if(tknsplit[1].contains(";")){
                			Tokens.add(new token(tokenTypes.SYMBOL, ";"));
                		}
                		else if(tknsplit[1].contains(",")){
                			Tokens.add(new token(tokenTypes.SYMBOL, ","));
                		}
                	}
        		}
        		else if(tkn.contains(";")){
        		
            		tkns = tkn.replace(";","");

        			token integerconst = new token(tokenTypes.INT_CONST, tkns);
        			Tokens.add(integerconst);
        			token semi = new token(tokenTypes.SYMBOL, ";");
        			Tokens.add(semi);
        		}
        		else if(tkn.contains(",")){
        			tkns = tkn.replace(",","");
        			token integerconst = new token(tokenTypes.INT_CONST, tkns);
        			Tokens.add(integerconst);
        			Tokens.add(new token(tokenTypes.SYMBOL, ","));
        		}
        		else{
        			token integerconst = new token(tokenTypes.INT_CONST, tkn);
        			Tokens.add(integerconst);
        		}
        	}
        	else{
        		if(tkn.contains(")")){
        			tkns = tkn.replace(")"," ");
        			tknsplit = tkns.split(" ");
        			Tokens.add(new token(tokenTypes.IDENTIFIER,tknsplit[0]));
        			Tokens.add(new token(tokenTypes.SYMBOL, ")"));
        			if(tknsplit.length>1){
        				if(tknsplit[1].contains(";")){
                			token semi = new token(tokenTypes.SYMBOL, ";");
                			Tokens.add(semi);
        				}
        			}
        		}
        		else if(tkn.contains(";")){
        			tkns = tkn.replace(";","");
            		if(kword.contains(tkns)){
            			Tokens.add(new token(tokenTypes.KEYWORD,tkns));
            		}
            		else{
        			token identifier = new token(tokenTypes.IDENTIFIER, tkns);
        			Tokens.add(identifier);
            		}
        			token semi = new token(tokenTypes.SYMBOL, ";");
        			Tokens.add(semi);
        		}
        		else if(tkn.contains(",")){
        			tkns = tkn.replace(",","");
        			token identifier = new token(tokenTypes.IDENTIFIER, tkns);
        			Tokens.add(identifier);
        			token comma = new token(tokenTypes.SYMBOL, ",");
        			Tokens.add(comma);
        		}
        		else{
        			token identifier = new token(tokenTypes.IDENTIFIER, tkn);
        			Tokens.add(identifier);
        		}
        	}
        }
        //System.out.println(Tokens.toString());
	}
       
	public ArrayList<token> getTokens() {
		return Tokens;
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
	public String removeComments(String s){
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
    public boolean parseParens(String tkn){
    	if(tkn.charAt(0)=='('){
    		boolean end = false;
    		int args=0;
    		//System.out.println("ifififififif");
    		//if(!tkn.contains("()")){
    		//}
				for(int i = 0; i<tkn.length();i++){
					if(tkn.charAt(i)=='('){
						args++;
					}
				}
	    		String holdst = tkn.replace("(", "");
				for(int j = 0; j<args;j++){
					Tokens.add(new token(tokenTypes.SYMBOL, "("));
				}

    		if(holdst.contains(")")){
    			holdst = holdst.replace(")", "");
    			if(holdst.matches(".\\d.*")){
    				Tokens.add(new token(tokenTypes.INT_CONST,holdst));
    			}
    			else if(kword.contains(holdst)){
    				Tokens.add(new token(tokenTypes.KEYWORD, holdst));
    			}
    			else{
    				Tokens.add(new token(tokenTypes.IDENTIFIER, holdst));
    			}
    			Tokens.add(new token(tokenTypes.SYMBOL,")"));
    			if(tkn.contains(";")){
    				Tokens.add(new token(tokenTypes.SYMBOL,";"));
    			}
    			end = true;
    		}
    		else{
    			if(holdst.matches(".\\d.*")){
    				Tokens.add(new token(tokenTypes.INT_CONST,holdst));
    			}
    			else if(kword.contains(holdst)){
    				Tokens.add(new token(tokenTypes.KEYWORD, holdst));
    			}
    			else{
    				Tokens.add(new token(tokenTypes.IDENTIFIER, holdst));
    			}
    		}
    		while (!end){
    			System.out.println("looping");
    			int args1 = 0;
    			String hol = scan.next();
    			System.out.println(hol);
    			if(hol.contains("(")){
    				for(int i = 0; i<hol.length();i++){
    					if(hol.charAt(i)=='('){
    						args1++;
    					}
    				}
    				hol = hol.replace("(","");
    				for(int j = 0; j<args1;j++){
    					Tokens.add(new token(tokenTypes.SYMBOL, "("));
    				}
        			if(hol.matches(".*\\d.*")){
        				
        				Tokens.add(new token(tokenTypes.INT_CONST,hol));
        			}
        			else if(kword.contains(hol)){
        				Tokens.add(new token(tokenTypes.KEYWORD,hol));
        			}
        			else{
        				
        				Tokens.add(new token(tokenTypes.IDENTIFIER, hol));
        			}
    				
    			}
    			else if(hol.contains(")")){
    				end = true;
    				hol = hol.replace(")","");

        			if(hol.matches(".*\\d.*")){
        				Tokens.add(new token(tokenTypes.INT_CONST,hol));
        			}
        			else if(kword.contains(hol)){
        				Tokens.add(new token(tokenTypes.KEYWORD,hol));
        			}
        			else{
        				System.out.println("IN HERE");
        				Tokens.add(new token(tokenTypes.IDENTIFIER, hol));
        			}
    				Tokens.add(new token(tokenTypes.SYMBOL, ")"));
    			}
    			else if(kword.contains(hol)){
    	        	token keyword = new token(tokenTypes.KEYWORD, hol);
    	        	Tokens.add(keyword);
    	        }
    	        else if(symbols.contains(hol)){
            		if(hol.contains("<")){
            			Tokens.add(new token(tokenTypes.SYMBOL,"&lt;"));
            		}
            		else if(hol.contains(">")){
            			Tokens.add(new token(tokenTypes.SYMBOL,"&gt;"));
            		}
            		else{
            			token symbol = new token(tokenTypes.SYMBOL, hol);
            			Tokens.add(symbol);
            		}
    	        }
    	        else{
    	        	//System.out.println("here");
        			if(hol.matches(".\\d.*")){
        				System.out.println("issue");
        				Tokens.add(new token(tokenTypes.INT_CONST,hol));
        			}
        			else if(kword.contains(hol)){
        				Tokens.add(new token(tokenTypes.KEYWORD, hol));
        			}
        			else{
        				System.out.println("issuess");
        				Tokens.add(new token(tokenTypes.IDENTIFIER, hol));
        			}
    	        }
    		}
    		return true;
    	}
    	else{
        String tkns;
        String tknsplit[];
    	int args = 0;
    	for(int i = 0; i<tkn.length();i++){
			if(tkn.charAt(i)=='('){
				args++;
			}
		}
    	tkns=tkn.replace("("," ");
    	tknsplit = tkns.split(" ");
    	System.out.println(tknsplit.length);
    	if(tknsplit[1].contains("\"")){
    		String hol;
    		System.out.println("here?");
    		token rightparen=null;
    		token semico = null;
    		boolean sem = false;
    		boolean righ = false;
    		boolean end = false;
    		String strcon=tknsplit[1];
 
    		while(!end){
    			hol = scan.next();
    			strcon=strcon+" "+hol; 
    			if(hol.contains("\"")){
    				end = true;	
    			}
    		}
    		strcon=strcon.replace("\"","");
    		if(strcon.contains(")")){
    			strcon=strcon.replace(")", "");
    			righ = true;
    			rightparen = new token(tokenTypes.SYMBOL, ")");
    		}
    		if(strcon.contains(";")){
    			strcon=strcon.replace(";","");
    			sem = true;
    			semico = new token(tokenTypes.SYMBOL,";");
    		}
    		token str= new token(tokenTypes.STRING_CONST,strcon);
    		Tokens.add(str);
    		if(righ){
    			Tokens.add(rightparen);
    		}
    		if(sem){
    			Tokens.add(semico);
    		}
    	}
    	else{
			boolean rightparen=false;
			int number = 0;
			boolean semi=false;
    		if(tknsplit[0].matches(".\\d.*")){
    			Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[0]));
    		}
    		else if(kword.contains(tknsplit[0])){
    			Tokens.add(new token(tokenTypes.KEYWORD, tknsplit[0]));
    		}
    		else{
    			System.out.println("issue here");
    			Tokens.add(new token(tokenTypes.IDENTIFIER, tknsplit[0]));
    		}
    		for(int k = 0; k < args; k ++){
    			Tokens.add(new token(tokenTypes.SYMBOL, "("));
    		}
    		if(tknsplit.length>1){
    			if(tknsplit[tknsplit.length-1].contains(",")){
        			String strcon=tknsplit[tknsplit.length-1].replace(",","");
        			System.out.println("		" +tknsplit[tknsplit.length-1]);
        			System.out.println(strcon);
        			if(strcon.matches(".\\d.*")||strcon.contains("0")){
        				System.out.println("eh");
        				Tokens.add(new token(tokenTypes.INT_CONST,strcon));
        			}
        			else if(kword.contains(strcon)){
        				Tokens.add(new token(tokenTypes.KEYWORD, strcon));
        			}
        			else{
        				System.out.println("beh");
        				Tokens.add(new token(tokenTypes.IDENTIFIER, strcon));
        			}
        			Tokens.add(new token(tokenTypes.SYMBOL,","));
        		}
    			else{
    				System.out.println("		" +tknsplit[tknsplit.length-1]);
    				if(tknsplit[tknsplit.length-1].contains(")")){
    					for(int i = 0;i<tknsplit[tknsplit.length-1].length();i++ ){
    						if(tknsplit[tknsplit.length-1].charAt(i)==')'){
    							number++;
    						}
    					}
    					rightparen=true;
    					tknsplit[tknsplit.length-1]=tknsplit[tknsplit.length-1].replace(")","");
    				}
    				if(tknsplit[tknsplit.length-1].contains(";")){
    					semi=true;
    					tknsplit[tknsplit.length-1]=tknsplit[tknsplit.length-1].replace(";", "");
    				}
    			}
    			if(tknsplit[tknsplit.length-1].matches("")){
    				
    			}
    			else if(tknsplit[tknsplit.length-1].matches(".\\d.*")||tknsplit[tknsplit.length-1].contains("5")){
        			Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[tknsplit.length-1]));
        		}
        		else if(kword.contains(tknsplit[tknsplit.length-1])){
        			Tokens.add(new token(tokenTypes.KEYWORD, tknsplit[tknsplit.length-1]));
        		}
        		else{
        			
        			Tokens.add(new token(tokenTypes.IDENTIFIER, tknsplit[tknsplit.length-1]));
        		}
        		if(rightparen){
        			for(int i = 0; i<number;i++){
        				Tokens.add(new token(tokenTypes.SYMBOL,")"));
        			}
        		}
        		if(semi){
        			Tokens.add(new token(tokenTypes.SYMBOL,";"));
        		}
    			}
    		else if(tkn.contains("()")){
    		token paren2 = new token(tokenTypes.SYMBOL, ")");
    		Tokens.add(paren2);
    			if(tkn.contains(";")){
    				Tokens.add(new token(tokenTypes.SYMBOL,";"));
    			}
    		}
    		else{
            if(tkn.contains(")")){
            	if(tkn.contains("(")){
            		tkn = tkn.replace("(", " ");
            		String[] inter  = tkn.split(" ");
            		//Tokens.add(new token(tokenTypes.SYMBOL,"("));
            		tkn = inter[1];
            	}
            	tkns = tkn.replace(")"," ");
            	tknsplit = tkns.split(" ");
        		if(tknsplit[0].matches(".\\d.*")){
        			Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[0]));
        		}
    			else if(kword.contains(tknsplit[0])){
    				Tokens.add(new token(tokenTypes.KEYWORD, tknsplit[0]));
    			}
        		else{
        			Tokens.add(new token(tokenTypes.IDENTIFIER, tknsplit[0]));
        		}
            	Tokens.add(new token(tokenTypes.SYMBOL,")"));
            	for(int i = 0; i < tknsplit.length; i++){
            		
            	}
            	if(tkn.contains(";")){
            		Tokens.add(new token(tokenTypes.SYMBOL,";"));
            	}
            }
            else{
        		if(tknsplit[1].contains(",")){
        			String strcon=tknsplit[1].replace(",","");
        			if(strcon.matches(".\\d.*")){
        				Tokens.add(new token(tokenTypes.INT_CONST,strcon));
        			}
        			else if(kword.contains(strcon)){
        				Tokens.add(new token(tokenTypes.KEYWORD, strcon));
        			}
        			else{
        				Tokens.add(new token(tokenTypes.IDENTIFIER, strcon));
        			}
        			Tokens.add(new token(tokenTypes.SYMBOL,","));
        		}
        		else{
        			
        			if(tknsplit[1].matches(".\\d.*")){
        				Tokens.add(new token(tokenTypes.INT_CONST,tknsplit[1]));
        			}
        			else if(kword.contains(tknsplit[1])){
        				Tokens.add(new token(tokenTypes.KEYWORD, tknsplit[1]));
        			}
        			else{
        				System.out.println("here after");
        				Tokens.add(new token(tokenTypes.IDENTIFIER, tknsplit[1]));
        			}
        		}
            }
    	return true;
    	}
    	return true;
    }
    }
		return false;
    }
}