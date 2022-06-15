package tinylangvisitor;
import java.util.Stack;

import tinylangparser.TinyLangAst;
import tinylangparser.Type;
public class SymbolTable {

	//current function parameter values 
	private Stack<Scope> scopes = new Stack<Scope>();
	public void push() {
		Scope newScope = new Scope();
		scopes.add(newScope);
	}
	public void insertVariableDeclaration(String name,Type type) {
		getCurrentScope().addVariableDeclaration(name, type);
	}
	//add value to variable x
	public void insertVariableValue(String x,String value) {
		getCurrentScope().addVariableValue(x, value);
	}
	public void insertFunctionDeclaration(FunctionSignature functionSignature,Type type) {
		getCurrentScope().addFunctionDeclaration(functionSignature, type);
	}
	public void insertFunctionParameterNames(FunctionSignature functionSignature, Stack<String> functionParameterNames) {
		getCurrentScope().addFunctionParameterNames(functionSignature, functionParameterNames);
	}
	public void insertFunctionBlock(FunctionSignature functionSignature, TinyLangAst functionBlock) {
		getCurrentScope().addFunctionBlock(functionSignature, functionBlock);
		
	}
	public void deleteVariable(String name) {
		getCurrentScope().deleteVariable(name);
	}
	
	public boolean isVariableNameBinded(String name) {
		//check is identifier is already binded in current scope
		return getCurrentScope().isVariableNameBinded(name);
	}
	public Type getVariableType(String name) {
		return getCurrentScope().getVariableType(name);
	}
	public void pop(){
		scopes.pop();
	}
	public Stack<Scope> getScopes(){
		return scopes;	
	}
	public Scope getCurrentScope() {
		return scopes.peek();
	}



	
}
