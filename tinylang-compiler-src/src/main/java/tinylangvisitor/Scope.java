package tinylangvisitor;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import tinylangparser.TinyLangAst;
import tinylangparser.Type;
public class Scope {
	//Signature
	//name binding  i.e. name |->  object e.g. variable,function etc
	Map<String,Type> variableDeclaration = new HashMap<String,Type>();
	Map<FunctionSignature,Type> functionDeclaration = new HashMap<FunctionSignature,Type>();
	Map<FunctionSignature,Stack<String>> functionParameterNames = new HashMap<FunctionSignature,Stack<String>>();
	Map<FunctionSignature,TinyLangAst> functionBlock= new HashMap<FunctionSignature,TinyLangAst>();

	// map := variable ↦ value
	Map<String,String> variableValues = new HashMap<String,String>();

	//  map := function name ↦ value

	public void addVariableDeclaration(String variableName,Type type) {
		variableDeclaration.put(variableName, type);
	}
	//add function declaration
	public void addFunctionDeclaration(FunctionSignature functionSignature,Type type) {
		functionDeclaration.put(functionSignature, type);
	}
	public TinyLangAst getBlock(FunctionSignature functionSignature) {
		return functionBlock.get(functionSignature);
	}
	public Stack<String> getParameterNames(FunctionSignature functionSignature) {
		return functionParameterNames.get(functionSignature);
	}
	
	
	
	//add value to variable x
	public void addVariableValue(String x,String value) {
		variableValues.put(x, value);
	}
	public void deleteVariable(String variableName) {
		variableValues.remove(variableName);
		variableDeclaration.remove(variableName);	
	}
	public void addFunctionParameterNames(FunctionSignature functionSignature, Stack<String> variableNames) {
		functionParameterNames.put(functionSignature, variableNames);
	}
	public void addFunctionBlock(FunctionSignature functionSignature, TinyLangAst block) {
		functionBlock.put(functionSignature, block);
	}
	
	public boolean isFunctionAlreadyDefined(FunctionSignature functionSignature) {
		return functionDeclaration.containsKey(functionSignature);
	}
	

	//check if name is binded to an entity
	public boolean isVariableNameBinded(String name) {
		return variableDeclaration.containsKey(name);
	}
	//check if value of variable x is null (does not exists)
	public boolean isVariableValueNull(String x) {
		return variableValues.containsKey(x);
	}
	public Type getVariableType(String name) {
		if(isVariableNameBinded(name))
			return variableDeclaration.get(name);
		else 
			throw new java.lang.RuntimeException("entity with identifier "+name+" does not exist");
	}
	//get value associated with variable x
	public String getVariableValue(String x) {
		if(isVariableValueNull(x)) 
			return variableValues.get(x);
		else 
			throw new java.lang.RuntimeException("entity with identifier "+x+" is associated with no value");	
	}
	public Type getFunctionType(FunctionSignature functionSignature) {
		if(isFunctionAlreadyDefined(functionSignature))
			return functionDeclaration.get(functionSignature);
		else 
			throw new java.lang.RuntimeException("function with identifier "+functionSignature.getFunctionName()
												+" and type(s) "+functionSignature.getParametersTypes() +" does not exist");
	}
	public Map<FunctionSignature,Type> getFunctionDeclaration(){
		return functionDeclaration;
	}


	
}
