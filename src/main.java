import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.*;
public class main{
	
	
	public static void main(String args[]) throws FileNotFoundException, IOException{
		
		String path = "";

		//if (args.length != 1) {
            //System.out.println("Please enter: java VMtranslator FILENAME|DIRECTORY");
        //}
        //else{
            File infile = new File(args[0]);
            String outfiledirectory = "";
            File outfile;
            
            
            ArrayList<File> vm = new ArrayList<File>();
            JackTokenizer jin = new JackTokenizer(args[0]);
            
            if (infile.isFile()) {
                path = infile.getAbsolutePath();
                
               // if(!JackTokenizer.extension(path).equals(".jack")) {
               //     throw new IllegalArgumentException(".jack file required.");
               // }
                
                //vm.add(infile);
                //outfiledirectory = path.substring(0, path.lastIndexOf(".")) + ".asm";    
            }
            
            else if (infile.isDirectory()) {
                vm = getFiles(infile);
                
                if (vm.isEmpty()){
                    throw new IllegalArgumentException("No jack files here");
                }
                
                outfiledirectory = infile.getAbsolutePath() + "/" + infile.getName() + ".xml";
            }
                
            outfile = new File(outfiledirectory);
            
            
            while(jin.hasMoreTokens()) {
            	jin.advance();
            }
            
            CompilationEngine ce = new CompilationEngine(jin.getTokens(), outfiledirectory);
            
            //writer.writeInit();
            
            
            System.out.println("New file here: " + outfiledirectory);
       // }
	}
        
    public static ArrayList<File> getFiles(File dir) {
    	File[] fs = dir.listFiles();
        ArrayList<File> files = new ArrayList<File>();
        for (File f:fs){
            if (f.getName().endsWith(".jack")) files.add(f);
        }
        return files;
        
        
    }
}