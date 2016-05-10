
public class token {
	tokenTypes type;
	String identifier;
	
	public token(tokenTypes t, String identifier){
		this.type = t;
		this.identifier=identifier;
	}
	
	@Override
	public String toString() {
		return "<" + type + "> " + identifier + " </" + type + ">" + "\n";
	}
	public String toStringOpen() {
		return "<" + type + "> " + identifier;
	}
	public String toStringClose() {
		return " </" + type + ">" + "\n";
	}
}
//[ token 1 , token 2, token 3, ... , token n];