// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    private AbstractSymbol type;
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public void setType(AbstractSymbol type) {
    this.type = type;
    }
    public abstract void dump_with_types(PrintStream out, int n);
}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    private AbstractSymbol type;
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public void setType(AbstractSymbol type) {
    this.type = type;
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    private AbstractSymbol type;
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public void setType(AbstractSymbol type) {
    this.type = type;
    }
    public abstract void dump_with_types(PrintStream out, int n);
}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    protected class_c currentClass;
    protected Environment environment;
    protected PrintStream currentErrorStream;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    this.environment = new Environment();
    }

    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
        ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    
    public class_c getCurrentClass() {
    return currentClass;
    }

    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
    <p>
        Your checker should do the following two things:
    <ol>
    <li>Check that the program is semantically correct
    <li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
    </ol>
    <p>
    You are free to first do (1) and make sure you catch all semantic
        errors. Part (2) can be done in a second stage when you want
    to test the complete compiler.
    */
    public void semant() {
    // Declaring a SymbolTable to manage naming and scopes
    SymbolTable symTable = new SymbolTable();
    // Load in the classes to the top-most scope of the Symbol Table
    symTable.enterScope();
    /* ClassTable constructor may do some semantic analysis */
    ClassTable classTable = new ClassTable(classes, symTable, this.environment);
    ArrayList<AbstractSymbol> processedClasses = new ArrayList<AbstractSymbol>();
    /* some semantic analysis code may go here */
    boolean programHasMainClass = false;
    Enumeration<class_c>  enumeration = classes.getElements();
    while (enumeration.hasMoreElements()) {
         class_c currentClass = enumeration.nextElement();
         programHasMainClass = programHasMainClass || currentClass.getName().equals(TreeConstants.Main);
         this.currentClass = currentClass;
         // Looking for duplicate classes
         if (processedClasses.indexOf(currentClass.getName()) != -1) {
        classTable.semantError(currentClass.getFilename(), currentClass).println("Class " + currentClass.getName() + " was previously defined."); 
         } else {
         processedClasses.add(currentClass.getName());
         }
         // Used to check if there are any missing classes
         AbstractSymbol parent = currentClass.getParent();
         boolean isValid = classTable.isValidClass(parent);
         if (!isValid) {
         classTable.semantError(currentClass.getFilename(), currentClass).println("Class " + currentClass.getName() + " inherits from an undefined class " + parent.toString());
         }
         // Used to check if the class name is reserved
         boolean isReserved = classTable.isReservedTypeIdentifier(currentClass.getName());
         if (isReserved) {
         classTable.semantError(currentClass.getFilename(), currentClass).println("Redfinition of basic class " + currentClass.getName());
         }
         // Since this is the first time we loop over all of the classes
         symTable.addId(currentClass.getName(), currentClass);
         Features features = currentClass.getFeatures();
         Enumeration featuresEnumeration = features.getElements();
         while (featuresEnumeration.hasMoreElements()) {
        Feature f = (Feature)(featuresEnumeration.nextElement());
        Class featureClass = f.getClass();
        if (featureClass.equals(method.class)) {
             ArrayList<AbstractSymbol> signature = new ArrayList<AbstractSymbol>();
             Formals formals = ((method)(f)).getFormals();
             Enumeration formalsEnumeration = formals.getElements();
             AbstractSymbol returnType = ((method)(f)).getReturnType();
             while (formalsEnumeration.hasMoreElements()) {
             formalc fc = (formalc)(formalsEnumeration.nextElement());
             semantFormal(fc, symTable, classTable);
             signature.add(fc.getType());
             }
             signature.add(returnType);
             HashMap<AbstractSymbol, HashMap<AbstractSymbol, ArrayList<AbstractSymbol>>> methodEnv = this.environment.getMethodEnvironment();
             this.environment.assignMethodSignatureTypes(this.getCurrentClass().getName(), ((method)f).getName(), signature);
            }

        /* if (featureClass.equals(attr.class)) {
            this.environment.assignObjectType(this.getCurrentClass().getName(), ((attr)f).getName(), ((attr)f).getType());
            } */
        } 
    }
    
    if (!programHasMainClass) {
        classTable.semantError().println("class Main is not defined.");
    }

    ArrayList<AbstractSymbol> validClasses = classTable.getValidClasses();
    for (AbstractSymbol validClass : validClasses) { 
        this.environment.assignInheritedMethods(this.getCurrentClass(), validClass, classTable);
        /* if (validClass.toString().equals("Razz")) {
        System.out.println("validClass = " + validClass + ", this.methodEnvironment = " + this.environment.getMethodEnvironment().get(validClass));
        } */
    }

    ArrayList<class_c> ordered = classTable.getTopologicalOrdering();
    // System.out.println("ordered = " + ordered);
    // enumeration = classes.getElements();
    // while (enumeration.hasMoreElements()) {
    for (class_c currentClass : ordered) {
        //class_c currentClass = enumeration.nextElement();
        // System.out.println("currentClass.getName() = " + currentClass.getName());
        this.currentClass = currentClass;
        // Need to process methods separately from attributes because
        // methods have global scope, attributes have class scope.
        Features features = currentClass.getFeatures();
        Enumeration featuresEnumeration = features.getElements();

        // Enter a new scope for the current class
        symTable.enterScope();

        this.environment.assignInheritedObjectTypes(this.currentClass.getName(), classTable);
        
        while (featuresEnumeration.hasMoreElements()) {
        Feature f = (Feature)(featuresEnumeration.nextElement());
        Class featureClass = f.getClass();
        if (featureClass.equals(attr.class)) {
            semantAttr((attr)(f), symTable, classTable);
        }
        }
        
        //System.out.println("this.currentClass.getName() = " + this.currentClass.getName() + ", this.environment.getObjectEnvironment().get(this.currentClass.getName()).keySet() = " + this.environment.getObjectEnvironment().get(this.currentClass.getName()).keySet());
        
        // Exit scope each time we finish processing a class
        symTable.exitScope();
    }

    enumeration = classes.getElements();
    while (enumeration.hasMoreElements()) {
        //this.environment.assignInheritedMethods(currentClass.getName(), classTable);
        class_c currentClass = enumeration.nextElement();
        this.currentClass = currentClass;
        Features features = currentClass.getFeatures();
        Enumeration featuresEnumeration = features.getElements();
        while (featuresEnumeration.hasMoreElements()) {
        Feature f = (Feature)(featuresEnumeration.nextElement());
        Class featureClass = f.getClass();
        if (featureClass.equals(method.class)) {
            semantMethod((method)(f), symTable, classTable);
            }
        }

        // Make sure the currenttErrorStream is reset to null
        this.currentErrorStream = null;
    }
    
    // Exit the topmost scope
    symTable.exitScope();

    if (classTable.errors()) {
        System.err.println("Compilation halted due to static semantic errors.");
        System.exit(1);
    }
    }

    // All of the error related methods are below this point.
    public PrintStream initializeAndGetCurrentErrorStream(ClassTable classTable) {
    if (this.currentErrorStream == null) {
        this.currentErrorStream = classTable.semantError(this.currentClass);
    }
    return this.currentErrorStream;
    }

    public String getSelfObjectError() {
    return "'self' cannot be the name of an attribute.";
    }

    public String getAssignmentError(AbstractSymbol exprType, AbstractSymbol objectType, AbstractSymbol name) {
    return "Type " + exprType.toString() + " of assigned expression does not conform to declared type " + objectType.toString() + " of identifier " + name.toString()  + ".";
    }
    // All of the error related methods are above this point.

    public void semantAttr(attr a, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = a.getName();
    AbstractSymbol type = a.getType();
    Expression init = a.getInit();
    
    // self is a reserved keyword, so whereverthe program can define new objects we should make sure that the name of the new object isn't self.
    if (name.toString().equals("self")) {
        classTable.semantError(this.getCurrentClass().getFilename(), a).println(getSelfObjectError());
    } else {
        symTable.addId(name, a);
        boolean overlapDetected = this.environment.assignObjectType(this.getCurrentClass().getName(), name, type);
        if (overlapDetected) {
        classTable.semantError(this.getCurrentClass().getFilename(), a).println("Duplicate declaration for attribute " + name.toString() + ".");
        } else {
        semantExpression(init, symTable, classTable);
        }
    }
    }

    public void semantMethod(method m, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = m.getName();
    Formals formals = m.getFormals();
    AbstractSymbol returnType = m.getReturnType();
    Expression expr = m.getExpression();

    // System.out.println("methodName = " + name);

    symTable.addId(name, m);

    // Enter a new scope for the current method
    symTable.enterScope();

    // Extending the object environment with the formal parameters + self
    Enumeration formalsEnumeration = formals.getElements();
    this.environment.assignObjectType(this.getCurrentClass().getName(), TreeConstants.self, TreeConstants.SELF_TYPE);
    
    // Below HashSet tracks if there are multiple declarations of a formalc
    Environment checkpoint = this.environment;
    HashSet<AbstractSymbol> formalsCovered = new HashSet<AbstractSymbol>();
    ArrayList<AbstractSymbol> signature = new ArrayList<AbstractSymbol>();
    while (formalsEnumeration.hasMoreElements()) {
        formalc f = (formalc)(formalsEnumeration.nextElement());
        if (formalsCovered.contains(f.getName())) {
        classTable.semantError(this.getCurrentClass().getFilename(), f).println("Formal parameter " + f.getName() + " is defined multiple times.");
        } else {
        formalsCovered.add(f.getName());
        }

        if (f.getType().equals(TreeConstants.SELF_TYPE)) {
        classTable.semantError(this.getCurrentClass().getFilename(), f).println("Formal parameter " + f.getName() + " cannot have type SELF_TYPE.");
        }
        this.environment.assignObjectType(this.getCurrentClass().getName(), f.getName(), f.getType());
    }

    // Processing the various possible types of Expressions
    semantExpression(expr, symTable, classTable);

    // Check that the return type of the method expression conforms to the declared return type
    if (!returnType.equals(TreeConstants.SELF_TYPE) && !classTable.isValidClass(returnType)) {
        classTable.semantError(this.getCurrentClass().getFilename(), m).println("Undefined return type " + returnType + " in method main.");
    }

    AbstractSymbol exprType = expr.get_type();
    //System.out.println("exprType = " + exprType + ", returnType = " + returnType);

    if (returnType.equals(TreeConstants.SELF_TYPE) && !returnType.equals(exprType)) {
        classTable.semantError(this.getCurrentClass().getFilename(), m).println("Inferred return type " + exprType.toString() + " of method " + name.toString() + " does not conform to declared return type " + returnType.toString());
    } else {
        if (exprType.equals(TreeConstants.SELF_TYPE)) {
        exprType = this.getCurrentClass().getName();
        }
        if (returnType.equals(TreeConstants.SELF_TYPE)) {
        returnType = this.getCurrentClass().getName();
        }
        if (!classTable.isSubtype(exprType, returnType)) {
        classTable.semantError(this.getCurrentClass().getFilename(), m).println("Inferred return type " + exprType.toString() + " of method " + name.toString() + " does not conform to declared return type " + returnType.toString());
        }
    }

    // Resetting the environment to what it was prior to adding in the formal arguments and self
    this.environment = checkpoint;

    // Exit scope each time we finish processing a method
    symTable.exitScope();
    }

    public void semantFormal(formalc fc, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = fc.getName();
    if (name.equals(TreeConstants.self)) {
        classTable.semantError(this.getCurrentClass().getFilename(), fc).println("'self' cannot be the name of a formal parameter.");
    }
    AbstractSymbol type = fc.getType();
    symTable.addId(name, fc);
    // this.environment.assignObjectType(this.getCurrentClass().getName(), name, type); 
    }

    // Expression Related Semantic Analysis Methods Below
    public void semantExpressions(Expressions exprs, SymbolTable symTable, ClassTable classTable) {
    Enumeration expressionsEnumeration = exprs.getElements();
    while (expressionsEnumeration.hasMoreElements()) {
        Expression expr = (Expression)(expressionsEnumeration.nextElement());
        semantExpression(expr, symTable, classTable);
    }
    }
    
    // Used for error recovery. When the type of an expression cannot be
    // determined through any issues, default the expression type to
    // Object since it is the most generic type.
    public void assignDefaultType(Expression expr) {
    expr.set_type(TreeConstants.Object_);
    }

    public void semantExpression(Expression expr, SymbolTable symTable, ClassTable classTable) {
    Class exprClass = expr.getClass();
    if (exprClass.equals(assign.class)) {
        semantAssign((assign)(expr), symTable, classTable);
    } else if (exprClass.equals(static_dispatch.class)) {
        semantStaticDispatch((static_dispatch)(expr), symTable, classTable);    
    } else if (exprClass.equals(dispatch.class)) {
        semantDispatch((dispatch)(expr), symTable, classTable);
        } else if (exprClass.equals(cond.class)) {
        semantCond((cond)(expr), symTable, classTable);
        } else if (exprClass.equals(loop.class)) {
        semantLoop((loop)(expr), symTable, classTable);
        } else if (exprClass.equals(typcase.class)) {
        semantTypcase((typcase)(expr), symTable, classTable);
        } else if (exprClass.equals(block.class)) {
        semantBlock((block)(expr), symTable, classTable);
        } else if (exprClass.equals(let.class)) {
        semantLet((let)(expr), symTable, classTable);
        } else if (exprClass.equals(plus.class)) {
        semantPlus((plus)(expr), symTable, classTable);
        } else if (exprClass.equals(sub.class)) {
        semantSub((sub)(expr), symTable, classTable);
        } else if (exprClass.equals(mul.class)) {
        semantMul((mul)(expr), symTable, classTable);
        } else if (exprClass.equals(divide.class)) {
        semantDivide((divide)(expr), symTable, classTable);
        } else if (exprClass.equals(neg.class)) {
        semantNeg((neg)(expr), symTable, classTable);
        } else if (exprClass.equals(lt.class)) {
        semantLt((lt)(expr), symTable, classTable);
        } else if (exprClass.equals(eq.class)) {
        semantEq((eq)(expr), symTable, classTable);
        } else if (exprClass.equals(leq.class)) {
        semantLeq((leq)(expr), symTable, classTable);
        } else if (exprClass.equals(comp.class)) {
        semantComp((comp)(expr), symTable, classTable);
        } else if (exprClass.equals(int_const.class)) {
        semantIntConst((int_const)(expr), symTable);
        } else if (exprClass.equals(bool_const.class)) {
            semantBoolConst((bool_const)(expr), symTable);
        } else if (exprClass.equals(string_const.class)) {
            semantStringConst((string_const)(expr), symTable);
        } else if (exprClass.equals(new_.class)) {
        semantNew((new_)(expr), symTable);
        } else if (exprClass.equals(isvoid.class)) {
        semantIsVoid((isvoid)(expr), symTable, classTable);
        } else if (exprClass.equals(no_expr.class)) {
        semantNoExpr((no_expr)(expr), symTable);
        } else {
        semantObject((object)(expr), symTable, classTable);
    }
    }

    public void semantAssign(assign a, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = a.getName();
    if (name.equals(TreeConstants.self)) {
        classTable.semantError(this.getCurrentClass().getFilename(), a).println("Cannot assign to 'self'");
        this.assignDefaultType(a);
    } else {
        Expression expr = a.getExpression();
        semantExpression(expr, symTable, classTable);
    
        // Determine the type of the assign expression based on the
        // type of expr.
        // System.out.println("this.getCurrentClass().getName() = " + this.getCurrentClass().getName()  + ",  name = " + name);
        // System.out.println("this.environment.getObjectEnvironment() = " + this.environment.getObjectEnvironment());
        
        AbstractSymbol objectType = this.environment.getObjectType(this.getCurrentClass().getName(), name);
        AbstractSymbol exprType = expr.get_type();
        if (exprType.equals(TreeConstants.SELF_TYPE)) {
        exprType = this.getCurrentClass().getName();
        }

        boolean isSubtype = classTable.isSubtype(exprType, objectType);
        if (isSubtype) {
        a.set_type(exprType);
        } else {
        this.assignDefaultType(a);
        // System.out.println("exprType = " + exprType + ", objectType = " + objectType);
        classTable.semantError(this.getCurrentClass().getFilename(), a).println(getAssignmentError(exprType, objectType, name));
        }
    }
    }

    public void semantDispatchCommonChecks(static_dispatch sd, dispatch d, AbstractSymbol type, AbstractSymbol name, Expressions actual, ClassTable classTable) {
    ArrayList<AbstractSymbol> expectedSignature;
    if (type.equals(TreeConstants.SELF_TYPE)) {
        expectedSignature =  this.environment.getMethodSignature(this.getCurrentClass().getName(), name);
    } else {
        expectedSignature = this.environment.getMethodSignature(type, name);
    }
    
    ArrayList<AbstractSymbol> actualSignature = new ArrayList<AbstractSymbol>();
    if (expectedSignature == null) {
        this.initializeAndGetCurrentErrorStream(classTable).println(": Invalid class method combination, class = " + type + ", method = " + name.toString());
        if (sd != null) {
        this.assignDefaultType(sd);
        } else if (d != null) {
        this.assignDefaultType(d);
        }
    } else {
        Enumeration expressionsEnumeration = actual.getElements();
        while (expressionsEnumeration.hasMoreElements()) {
        Expression actualExpr = (Expression)(expressionsEnumeration.nextElement());
        // System.out.println("actualExpr = " + actualExpr);
        AbstractSymbol actualType = actualExpr.get_type();
        actualSignature.add(actualType);
        }

        // Number of arguments check
        if (actualSignature.size() != expectedSignature.size()-1) {
        if (sd != null) {
            classTable.semantError(this.getCurrentClass().getFilename(), sd).println("Incorrect number of arguments provided expected " + (expectedSignature.size()-1) + " actual " + actualSignature.size());
        } else if (d != null) {
            classTable.semantError(this.getCurrentClass().getFilename(), d).println("Incorrect number of arguments provided expected " + (expectedSignature.size()-1) + " actual " + actualSignature.size());
        }
        } else {
        // Arguments type check
        for (int index = 0; index < actualSignature.size(); index+=1) {
            AbstractSymbol expectedType = expectedSignature.get(index);
            AbstractSymbol actualType = actualSignature.get(index);
            // System.out.println("actualType = " + actualType);
            if (actualType.equals(TreeConstants.SELF_TYPE)) {
            actualType = this.getCurrentClass().getName();
            }
            if (!classTable.isSubtype(actualType, expectedType)) {
            this.initializeAndGetCurrentErrorStream(classTable).println(": Incorrect type for argument number " + index + " expected " + expectedType.toString() + " actual " + actualType.toString());
            }
        }
        // Set the type of the entire dispatch
        if (expectedSignature.get(expectedSignature.size()-1).equals(TreeConstants.SELF_TYPE)) {
            if (d != null) {
            d.set_type(type);
            }
            if (sd != null) {
            sd.set_type(type);
            }
        } else {
            if (d != null) {
            d.set_type(expectedSignature.get(expectedSignature.size()-1));
            }
            if (sd != null) {
            sd.set_type(expectedSignature.get(expectedSignature.size()-1));
            }
        }
        }
    }
    }
    
    public void semantStaticDispatch(static_dispatch sd, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = sd.getName();
    AbstractSymbol type = sd.getType();
    Expression expr = sd.getExpression();
    semantExpression(expr, symTable, classTable);
    AbstractSymbol exprType = expr.get_type();

    // System.out.println("exprClass = " + expr.getClass());
    // System.out.println("name = " + name + ", exprType = " + exprType  + ", type = " + type);
    
    boolean validType = true;
    if (exprType.equals(TreeConstants.SELF_TYPE)) {
        // exprType = this.getCurrentClass().getName();
        validType = classTable.isSubtype(this.getCurrentClass().getName(), type);
    } else {
        validType =  classTable.isSubtype(exprType, type);
    }
    
    if (!validType) {
        this.initializeAndGetCurrentErrorStream(classTable).println(": Invalid type for static dispatch, expected subtype of " + type + " actual " + expr.get_type());
        assignDefaultType(sd);
    } else {
        Expressions actual = sd.getActual();
        semantExpressions(actual, symTable, classTable);
        symTable.addId(name, sd);
        // Call the common dispatch logic
        semantDispatchCommonChecks(sd, null, exprType, name, actual, classTable);
    }
    }

    public void semantDispatch(dispatch d, SymbolTable symTable, ClassTable classTable) {
    Expression expr = d.getExpression();
    semantExpression(expr, symTable, classTable);
    AbstractSymbol exprType = expr.get_type();
    // System.out.println("expr.getClass() = " + expr.getClass() + ", exprType = " + exprType);
    /* if (exprType.equals(TreeConstants.SELF_TYPE)) {
        // System.out.println("this.getCurrentClass() = " + this.getCurrentClass());
        exprType = this.getCurrentClass().getName();
    } */
    AbstractSymbol name = d.getName();
    Expressions actual = d.getActual();
    semantExpressions(actual, symTable, classTable);
    symTable.addId(name, d);

    // Call the common dispatch logic
    semantDispatchCommonChecks(null, d, exprType, name, actual, classTable);
    }

    public void semantCond(cond c, SymbolTable symTable, ClassTable classTable) {
    Expression predicate = c.getPredicate();
    Expression then_exp = c.getThen();
    Expression else_exp = c.getElse();
    semantExpression(predicate, symTable, classTable);
    AbstractSymbol predicateType = predicate.get_type();
    if (!predicateType.equals(TreeConstants.Bool)) {
        classTable.semantError(this.getCurrentClass().getFilename(), c).println("Invalid type for predicate expected Bool actual" + predicateType);
         assignDefaultType(c);
    } else {
        semantExpression(then_exp, symTable, classTable);
        AbstractSymbol thenType = then_exp.get_type();
        semantExpression(else_exp, symTable, classTable);
        AbstractSymbol elseType = else_exp.get_type();
        
        if (thenType.equals(TreeConstants.SELF_TYPE)) {
        thenType = this.getCurrentClass().getName();
        }

        if (elseType.equals(TreeConstants.SELF_TYPE)) {
        elseType = this.getCurrentClass().getName();
        }

        AbstractSymbol lubType = classTable.leastUpperBound(thenType, elseType);
        c.set_type(lubType);
    }
    }

    public void semantLoop(loop l, SymbolTable symTable, ClassTable classTable) {
    Expression predicate = l.getPredicate();
    Expression body = l.getBody();
    semantExpression(predicate, symTable, classTable);
    AbstractSymbol predicateType = predicate.get_type();
    semantExpression(body, symTable, classTable);
    if (!predicateType.equals(TreeConstants.Bool)) {
         this.initializeAndGetCurrentErrorStream(classTable).println(": Invalid type for predicate expected Bool actual" + predicateType);
         assignDefaultType(l);
    } else {
        l.set_type(TreeConstants.Object_);
    }
    }

    public void semantBlock(block b, SymbolTable symTable, ClassTable classTable) {
    Expressions body = b.getBody();
    semantExpressions(body, symTable, classTable);
    int n = body.getLength() - 1;
    Expression exprn = (Expression)(body.getNth(n));
    AbstractSymbol expressionNType = exprn.get_type();
    b.set_type(expressionNType);
    }

    public void semantLet(let l, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol id = l.getIdentifier();
    AbstractSymbol type = l.getType();
    Expression init = l.getInit();
    Expression body = l.getBody();
    // Before processing the body, add in id to the symbol table
    symTable.addId(id, l);

    if (id.equals(TreeConstants.self)) {
        classTable.semantError(this.getCurrentClass().getFilename(), l).println("'self' cannot be bound in a 'let' expression.");
        this.assignDefaultType(l);
    } else {
        semantExpression(init, symTable, classTable);
        AbstractSymbol initType = init.get_type();
        if (!initType.equals(TreeConstants.No_type)) {
            if (type.equals(TreeConstants.SELF_TYPE) && !initType.equals(type)) {
            classTable.semantError(this.getCurrentClass().getFilename(), l).println("Actual expression type " + initType + " isn't subtype of expected type " + type);
            } else if (!classTable.isSubtype(initType, type)) {
            classTable.semantError(this.getCurrentClass().getFilename(), l).println("Actual expression type " + initType + " isn't subtype of expected type " + type);
            }
        }
 
        HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>> checkpoint = this.environment.getObjectEnvironment();
        // System.out.println("this.environment.getObjectEnvironment().get(this.getCurrentClass().getName()).keySet() = " + checkpoint.get(this.getCurrentClass().getName()).keySet());
        this.environment.assignObjectType(this.getCurrentClass().getName(), id, type);
        
        // System.out.println("this.environment.getObjectEnvironment().get(this.getCurrentClass().getName()).keySet() = " + checkpoint.get(this.getCurrentClass().getName()).keySet());

        semantExpression(body, symTable, classTable);
        l.set_type(body.get_type());
        // Before returning out, get out of the let expression scope
        this.environment.setObjectEnvironment(checkpoint);
        // System.out.println("this.environment.getObjectEnvironment().get(this.getCurrentClass().getName()).keySet() = " + checkpoint.get(this.getCurrentClass().getName()).keySet());
        }
    }

    public void semantArithCommon(Expression expr1, Expression expr2, Expression result, ClassTable classTable) {
    AbstractSymbol expression1Type = expr1.get_type();
    AbstractSymbol expression2Type = expr2.get_type();
    if (!expression1Type.equals(TreeConstants.Int)) {
        classTable.semantError(this.getCurrentClass().getFilename(), expr1).println("Invalid type for expression expected Int actual " +  expression1Type);
        assignDefaultType(result);
    } else if (!expression2Type.equals(TreeConstants.Int)) {
        classTable.semantError(this.getCurrentClass().getFilename(), expr2).println("Invalid type for expression expected Int actual " + expression2Type);
         assignDefaultType(result);
    } else {
         result.set_type(TreeConstants.Int);
    }
    }

    public void semantPlus(plus p, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = p.getExpression1();
    Expression expr2 = p.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
    semantArithCommon(expr1, expr2, p, classTable);
    }

    public void semantSub(sub s, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = s.getExpression1();
    Expression expr2 = s.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
    semantArithCommon(expr1, expr2, s, classTable);
    }

    public void semantMul(mul m, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = m.getExpression1();
    Expression expr2 = m.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
    semantArithCommon(expr1, expr2, m, classTable);
    }

    public void semantDivide(divide d, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = d.getExpression1();
    Expression expr2 = d.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
    semantArithCommon(expr1, expr2, d, classTable);
    }

    public void semantNeg(neg n, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = n.getExpression1();
    semantExpression(expr1, symTable, classTable);
    AbstractSymbol expression1Type = expr1.get_type();
    if (!expression1Type.equals(TreeConstants.Int)) {
        classTable.semantError(this.getCurrentClass().getFilename(), n).println("Invalid type for expression expected Int actual" + expression1Type);
    } else {
        n.set_type(TreeConstants.Int);
    }
    }

    // Common for leq and lt
    public void semantComparisonCommon(Expression expr1, Expression expr2, Expression result, ClassTable classTable) {
    AbstractSymbol expression1Type = expr1.get_type();
    AbstractSymbol expression2Type = expr2.get_type();
    if (!expression1Type.equals(TreeConstants.Int)) {
        classTable.semantError(this.getCurrentClass().getFilename(), expr1).println("Invalid type for expression expected Int actual " + expression1Type);
         assignDefaultType(expr1);
         
    } else if (!expression2Type.equals(TreeConstants.Int)) {
        classTable.semantError(this.getCurrentClass().getFilename(), expr2).println("Invalid type for expression expected Int actual " + expression2Type);
         assignDefaultType(expr2);
    } else {
         result.set_type(TreeConstants.Bool);
    }
    }

    public void semantLt(lt l, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = l.getExpression1();
    Expression expr2 = l.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
    semantComparisonCommon(expr1, expr2, l, classTable);
    }

    public boolean equalityTypesConform(AbstractSymbol type1, AbstractSymbol type2) {
    if (
        ( type1.equals(TreeConstants.Int) || type2.equals(TreeConstants.Int))
        || (type1.equals(TreeConstants.Bool) || type2.equals(TreeConstants.Bool))
        || (type1.equals(TreeConstants.Str) || type2.equals(TreeConstants.Str))
    ) {
        return type1.equals(type2);
    }
    return true;
    }

    public void semantEq(eq e, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = e.getExpression1();
    Expression expr2 = e.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
    AbstractSymbol expression1Type = expr1.get_type();
    AbstractSymbol expression2Type = expr2.get_type();
    boolean equalityTypesConform = equalityTypesConform(expression1Type, expression2Type);
    // System.out.println("expression1Type = " + expression1Type + ", expression2Type = " + expression2Type + ", equalityTypesConform = " + equalityTypesConform);
    if (!equalityTypesConform) {
        classTable.semantError(this.getCurrentClass().getFilename(), e).println("Invalid types for expressions in equality " + expression1Type + " and " + expression2Type);
        assignDefaultType(e);
    } else {
        e.set_type(TreeConstants.Bool);
    }
    }

    public void semantLeq(leq l, SymbolTable symTable, ClassTable classTable) {
    Expression expr1 = l.getExpression1();
    Expression expr2 = l.getExpression2();
    semantExpression(expr1, symTable, classTable);
    semantExpression(expr2, symTable, classTable);
        semantComparisonCommon(expr1, expr2, l, classTable);
    }

    public void semantComp(comp c, SymbolTable symTable, ClassTable classTable) {
    Expression expr = c.getExpression();
    semantExpression(expr, symTable, classTable);
    AbstractSymbol expressionType = expr.get_type();
    if (!expressionType.equals(TreeConstants.Bool)) {
        classTable.semantError(this.getCurrentClass().getFilename(), c).println("Invalid type for expression expected Bool actual " + expressionType);
    } else {
        c.set_type(TreeConstants.Bool);
    }
    }

    public void semantIntConst(int_const i, SymbolTable symTable) {
    AbstractSymbol token = i.getToken();
    symTable.addId(token, i);
    i.set_type(TreeConstants.Int);
    }

    public void semantBoolConst(bool_const b, SymbolTable symTable) {
    b.set_type(TreeConstants.Bool);
    }

    public void semantStringConst(string_const s, SymbolTable symTable) {
    AbstractSymbol token = s.getToken();
    symTable.addId(token, s);
    s.set_type(TreeConstants.Str);
    }

    public void semantNew(new_ n, SymbolTable symTable) {
    AbstractSymbol type = n.getType();
    symTable.addId(type, n);
    if (type.equals(TreeConstants.self)) {
        n.set_type(currentClass.getName());
    } else {
        n.set_type(type);
    }
    }

    public void semantIsVoid(isvoid iv, SymbolTable symTable, ClassTable classTable) {
    Expression expr = iv.getExpression();
    semantExpression(expr, symTable, classTable);
    iv.set_type(TreeConstants.Bool);
    }

    public void semantNoExpr(no_expr noExpr, SymbolTable symTable) {
    noExpr.set_type(TreeConstants.No_type);
    }

    public void semantObject(object o, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = o.getName();
    //System.out.println("o = " + o + ", name = " + name);
    if (name.toString().equals("self")) {
        o.set_type(TreeConstants.SELF_TYPE);
    } else {
        AbstractSymbol objectType = this.environment.getObjectType(this.getCurrentClass().getName(), name);
        // System.out.println("this.getCurrentClass().getName() = " + this.getCurrentClass().getName());
        // System.out.println("this.environment.getObjectEnvironment().keySet() = " + this.environment.getObjectEnvironment().get(this.getCurrentClass().getName()).keySet());
        if (objectType == null) {
        this.assignDefaultType(o);
        classTable.semantError(this.getCurrentClass().getFilename(), o).println("Undeclared identifier " + name.toString() + ".");
        } else {
        o.set_type(objectType);
        }
    }
    }
    // Most Expression Related Semantic Analysis Methods Above

    public void semantTypcase(typcase t, SymbolTable symTable, ClassTable classTable) {
    Expression expr = t.getExpression();
    Cases cases = t.getCases();
    semantExpression(expr, symTable, classTable);
    AbstractSymbol exprType = expr.get_type();

    Enumeration casesEnumeration = cases.getElements();
    
    ArrayList<AbstractSymbol> branchVariableTypes = new ArrayList<AbstractSymbol>();
    ArrayList<AbstractSymbol> branchTypes = new ArrayList<AbstractSymbol>();
    while (casesEnumeration.hasMoreElements()) {
        branch b = (branch)(casesEnumeration.nextElement());
        semantBranch(b, symTable, classTable);
        AbstractSymbol branchVariableType = b.get_type();
        AbstractSymbol branchType = b.getType();
        if (branchTypes.indexOf(branchType) < 0) {
        branchVariableTypes.add(branchVariableType);
        branchTypes.add(branchType);
        } else {
        classTable.semantError(this.getCurrentClass().getFilename(), b).println("Each branch variable needs to have a distinct type.");
        }
    }

    // Type of the entire case statement is the union of all branch types
    AbstractSymbol upperBound = branchVariableTypes.get(0);
    // System.out.println("upperBound = " + upperBound);
    for (int index = 1; index < branchVariableTypes.size(); index+=1) {
        AbstractSymbol branchType = branchVariableTypes.get(index);
        // System.out.println("branchType = " + branchType);
        upperBound = classTable.leastUpperBound(upperBound, branchType);
        // System.out.println("upperBound = " + upperBound);
    }
    t.set_type(upperBound);
    }

    public void semantBranch(branch b, SymbolTable symTable, ClassTable classTable) {
    AbstractSymbol name = b.getName();
    AbstractSymbol type = b.getType();
    Expression e = b.getExpression();
    symTable.addId(name, b);
    
    Environment checkpoint = this.environment;
    this.environment.assignObjectType(this.getCurrentClass().getName(), name, type);
    semantExpression(e, symTable, classTable);
    b.set_type(e.get_type());

    this.environment = checkpoint;
    }
}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public AbstractSymbol getFilename() { return filename; }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    // Newly added helper function to get features
    public Features getFeatures()       { return features; }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
        ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }

}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    // Newly Added getters for PA3J
    public AbstractSymbol getName()       { return name; }
    public Formals getFormals()           { return formals; }
    public AbstractSymbol getReturnType() { return return_type; }
    public Expression getExpression()     { return expr; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
        ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
    expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    // New getters added for PA3J
    public AbstractSymbol getName() { return name; }
    public AbstractSymbol getType() { return type_decl; }
    public Expression getInit()     { return init; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    init.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    public AbstractSymbol getName() { return name; }
    public AbstractSymbol getType() { return type_decl; }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected AbstractSymbol branch_type;
    protected Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    public AbstractSymbol getName()   { return name; }
    public AbstractSymbol getType()   { return type_decl; }
    public AbstractSymbol get_type()  { return branch_type; }
    public void set_type(AbstractSymbol type) { this.branch_type = type; }
    public Expression getExpression() { return expr; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    expr.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    public AbstractSymbol getName()   { return name; }
    public Expression getExpression() { return expr; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
    expr.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    public Expression getExpression() { return expr; }
    public AbstractSymbol getType()   { return type_name; }
    public AbstractSymbol getName()   { return name; }
    public Expressions getActual()    { return actual; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
    expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
        ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    dump_type(out, n);
    }

}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    public Expression getExpression() { return expr; }
    public AbstractSymbol getName()   { return name; }
    public Expressions getActual()    { return actual; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
    expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
        ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    dump_type(out, n);
    }

}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    public Expression getPredicate() { return pred; }
    public Expression getThen()      { return then_exp; }
    public Expression getElse()      { return else_exp; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
    pred.dump_with_types(out, n + 2);
    then_exp.dump_with_types(out, n + 2);
    else_exp.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    public Expression getPredicate() { return pred; }
    public Expression getBody()      { return body; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
    pred.dump_with_types(out, n + 2);
    body.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    public Expression getExpression() { return expr; }
    public Cases getCases()           { return cases; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
    expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
        ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
    dump_type(out, n);
    }

}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    public Expressions getBody() { return body; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
        ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
    dump_type(out, n);
    }

}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    public AbstractSymbol getIdentifier() { return identifier; }
    public AbstractSymbol getType()       { return type_decl; }
    public Expression getInit()           { return init; }
    public Expression getBody()           { return body; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
    dump_AbstractSymbol(out, n + 2, identifier);
    dump_AbstractSymbol(out, n + 2, type_decl);
    init.dump_with_types(out, n + 2);
    body.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
    e1.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public Expression getExpression1() { return e1; }
    public Expression getExpression2() { return e2; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
    e1.dump_with_types(out, n + 2);
    e2.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    public Expression getExpression() { return e1; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
    e1.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    public AbstractSymbol getToken() { return token; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
    dump_AbstractSymbol(out, n + 2, token);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    public Boolean getValue() { return val; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
    dump_Boolean(out, n + 2, val);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    public AbstractSymbol getToken() { return token; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, token.getString());
        out.println("\"");
        dump_type(out, n);
    }

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    public AbstractSymbol getType() { return type_name; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
    dump_AbstractSymbol(out, n + 2, type_name);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    public Expression getExpression() { return e1; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
    e1.dump_with_types(out, n + 2);
    dump_type(out, n);
    }

}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
        dump_type(out, n);
    }

}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    // New getters added for PA4J
    public AbstractSymbol getName() { return name; }
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
        dump_AbstractSymbol(out, n + 2, name);
        dump_type(out, n);
    }

}


