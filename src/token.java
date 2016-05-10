
public class token {
	tokenTypes type;
	String identifier;
	
	public token(tokenTypes t, String identifier){
		this.type = t;
		this.identifier=identifier;
	}
	@Override
	public String toString() {
		return "token [type=" + type + ", identifier=" + identifier + "]\n";
	}
}
//[ token 1 , token 2, token 3, ... , token n];