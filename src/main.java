import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.*;
public class main{
	
	public static void main(String args[]) throws FileNotFoundException, IOException{
		if (args.length != 1) {
            System.out.println("Please enter: java VMtranslator FILENAME|DIRECTORY");
        }
        else{
            File infile = new File(args[0]);
            String outfiledirectory = "";
            File outfile;
            
            ArrayList<File> vm = new ArrayList<File>();
            
            if (infile.isFile()) {
                String path = infile.getAbsolutePath();
                
                if(!JackTokenizer.extension(path).equals(".vm")) {
                    throw new IllegalArgumentException(".vm file required.");
                }
                
                //vm.add(infile);
                //outfiledirectory = path.substring(0, path.lastIndexOf(".")) + ".asm";    
            }
            
            else if (infile.isDirectory()) {
                vm = getFiles(infile);
                
                if (vm.isEmpty()){
                    throw new IllegalArgumentException("No vm files here");
                }
                
                outfiledirectory = infile.getAbsolutePath() + "/" + infile.getName() + ".asm";
            }
                
            outfile = new File(outfiledirectory);
            writer = new CompilationEngine(infile,outfile);
            
            //writer.writeInit();
            for (File f: vm) {
                Parser parser = new Parser(f);
                int type;
                
                while (parser.hasMoreCommands()) {
                    parser.advance();
                    type = parser.commandType();
                    
                    if (type == Parser.ARITHMETIC) {
                        writer.writeArithmetic(parser.arg1());
                    }
                    else if (type == Parser.POP || type == Parser.PUSH){
                        writer.writePushPop(type, parser.arg1(), parser.arg2());
                    }
                    else if (type == Parser.LABEL){
                        writer.writeLabel(parser.arg1());
                    }
                    else if (type == Parser.GOTO) {
                        writer.writeGoto(parser.arg1());
                    }
                    else if (type == Parser.IF) {
                        writer.writeIf(parser.arg1());
                    }
                    else if (type == Parser.RETURN) {
                        writer.writeReturn();
                    }
                    else if (type == Parser.FUNCTION) {
                        writer.writeFunction(parser.arg1(),parser.arg2());
                    } 
                    else if (type == Parser.CALL) {
                        writer.writeCall(parser.arg1(),parser.arg2());
                    }
                }
            }
            writer.close();
            
            System.out.println("New file here: " + outfiledirectory);
	}
        
    public static ArrayList<File> getFiles(File dir) {
    	File[] fs = dir.listFiles();
        ArrayList<File> files = new ArrayList<File>();
        for (File f:fs){
            if (f.getName().endsWith(".vm")) files.add(f);
        }
        return files;
        
        
    }
}