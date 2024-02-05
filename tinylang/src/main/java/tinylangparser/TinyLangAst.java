package tinylangparser;

import java.util.LinkedList;


import java.util.List;

public class TinyLangAst {
    private final TinyLangAstNodes associatedNodeType;
    private String associatedNodeValue = "";
    private int lineNumber = 0;
    TinyLangAst parent;
    List<TinyLangAst> children;

    public TinyLangAst(TinyLangAstNodes associatedNodeType, int lineNumber) {
        this.associatedNodeType = associatedNodeType;
        this.lineNumber = lineNumber;
        this.children = new LinkedList<TinyLangAst>();
    }


    public TinyLangAst(TinyLangAstNodes associatedNodeType, String associatedNodeValue, int lineNumber) {
        this.associatedNodeType = associatedNodeType;
        this.associatedNodeValue = associatedNodeValue;
        this.lineNumber = lineNumber;
        this.children = new LinkedList<TinyLangAst>();
    }

    //add root of a subtree to abstract syntax tree
    public void addSubtree(TinyLangAst subTree) {
        this.children.add(subTree);
    }

    public void addChild(TinyLangAstNodes associatedNodeType, int lineNumber) {
        TinyLangAst childNode = new TinyLangAst(associatedNodeType, lineNumber);
        childNode.parent = this;
        this.children.add(childNode);
    }

    public void addChild(TinyLangAstNodes associatedNodeType, String associatedNodeValue, int lineNumber) {
        TinyLangAst childNode = new TinyLangAst(associatedNodeType, associatedNodeValue, lineNumber);
        childNode.parent = this;
        this.children.add(childNode);
    }

    // setters and getters
    public TinyLangAstNodes getAssociatedNodeType() {
        return associatedNodeType;
    }

    ;

    public String getAssociatedNodeValue() {
        return associatedNodeValue;
    }

    // get children
    public List<TinyLangAst> getChildren() {
        return children;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}