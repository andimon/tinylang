package tinylangvisitor;

import java.util.Objects;
import java.util.Stack;

import tinylangparser.Type;

public class FunctionSignature {
    private String functionName = "";
    private final int hashCode;
    Stack<Type> parameterType = new Stack<Type>();

    public FunctionSignature(String functionName, Stack<Type> parameterType) {
        //set functionName
        this.functionName = functionName;
        //set parameter types stack
        this.parameterType = parameterType;
        //set hash
        hashCode = Objects.hash(functionName, parameterType);
    }

    public String getFunctionName() {
        return functionName;
    }

    public Stack<Type> getParametersTypes() {
        return parameterType;
    }

    /*
     *functions that allows us to use classes
     *as map keys where 2 object keys are
     *equivalent iff they have same attribute values
     *rather than same object address value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FunctionSignature that = (FunctionSignature) o;
        return functionName.equals(that.functionName) && parameterType.equals(that.parameterType);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
