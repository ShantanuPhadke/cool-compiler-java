import java.util.HashMap;
import java.util.ArrayList;

class Environment {
    private HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>> objectEnvironment;
    private HashMap<AbstractSymbol, HashMap<AbstractSymbol, ArrayList<AbstractSymbol>>> methodEnvironment;
    
    public Environment() {
	this.objectEnvironment = new HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>>();
	this.methodEnvironment = new HashMap<AbstractSymbol, HashMap<AbstractSymbol, ArrayList<AbstractSymbol>>>();
    }

    // Returns an array of the names of inherited methods
    public void assignInheritedMethods(class_c clsC, AbstractSymbol classC, ClassTable classTable) {
	// ArrayList<AbstractSymbol> inheritedMethods = new ArrayList<AbstractSymbol>();
	HashMap<AbstractSymbol, AbstractSymbol> childToParent = classTable.getChildToParent();
	if (!this.methodEnvironment.containsKey(classC)) {
	    this.methodEnvironment.put(classC, new HashMap<AbstractSymbol, ArrayList<AbstractSymbol>>());
	}
	AbstractSymbol currentClass = childToParent.get(classC);
	while (currentClass != null && !currentClass.equals(TreeConstants.No_class) && !currentClass.equals(TreeConstants.SELF_TYPE)) {
	    // System.out.println("currentClass = " + currentClass.toString());
	    HashMap<AbstractSymbol, ArrayList<AbstractSymbol>> currentParentMethods = this.methodEnvironment.get(currentClass);
	    // System.out.println("currentParentMethods.keySet() = " + currentParentMethods.keySet());
	    if (currentParentMethods != null) {
		// inheritedMethods.addAll(currentParentMethods.keySet());
		for (AbstractSymbol methodId : currentParentMethods.keySet()) {
		    if (!this.methodEnvironment.get(classC).containsKey(methodId)) {
			this.methodEnvironment.get(classC).put(methodId, currentParentMethods.get(methodId));
		    } else {
			ArrayList<AbstractSymbol> expectedSignature = currentParentMethods.get(methodId);
			ArrayList<AbstractSymbol> currentSignature = this.methodEnvironment.get(classC).get(methodId);
			boolean equivalent = this.compareMethodSignatures(expectedSignature, currentSignature);
			if (!equivalent) {
			    classTable.semantError(clsC.getFilename(), clsC).println("Signature of redefined method " + methodId + " is noncompliant.");
			}
		    }
		}
	    }
	    currentClass = childToParent.get(currentClass);
	}
	// System.out.println("this.methodEnvironment.get(classC).keySet() = " + this.methodEnvironment.get(classC).keySet());
	// return inheritedMethods;
    }

    public void assignInheritedObjectTypes(AbstractSymbol classC, ClassTable classTable) {
		HashMap<AbstractSymbol, AbstractSymbol> childToParent = classTable.getChildToParent();
		if (!this.objectEnvironment.containsKey(classC)) {
		    this.objectEnvironment.put(classC, new HashMap<AbstractSymbol, AbstractSymbol>());
		}
		AbstractSymbol currentClass = childToParent.get(classC);
		AbstractSymbol currentChild = classC;
		
		while (currentClass != null && !currentClass.equals(TreeConstants.No_class) && !currentClass.equals(TreeConstants.SELF_TYPE)) {
		    if (this.objectEnvironment.containsKey(currentClass)) {
			    HashMap<AbstractSymbol, AbstractSymbol> currentParentObjects = this.objectEnvironment.get(currentClass);
			if (currentParentObjects != null) {
			    for (AbstractSymbol objectId : currentParentObjects.keySet()) {
				// System.out.println("\n currentClass = " + currentClass + ", objectId = " + objectId + ", this.objectEnvironment.get(classC).containsKey(objectId) = " + currentParentObjects.get(objectId) + "\n");
				if (!this.objectEnvironment.get(classC).containsKey(objectId)) {
				    this.objectEnvironment.get(classC).put(objectId, currentParentObjects.get(objectId));
				}
			    }
			}
		    }
		    currentChild = currentClass;
		    currentClass = childToParent.get(currentClass);
		}
    }

    public boolean assignObjectType(AbstractSymbol classC, AbstractSymbol objectId, AbstractSymbol type) {
		if (!this.objectEnvironment.containsKey(classC)) {
		    this.objectEnvironment.put(classC, new HashMap<AbstractSymbol, AbstractSymbol>());
		}

		if (!this.objectEnvironment.get(classC).containsKey(objectId)) {
		    this.objectEnvironment.get(classC).put(objectId, type);
		    return false;
		} else {
		    this.objectEnvironment.get(classC).put(objectId, type);
		    return true;
		}
    }

    public void removeObject(AbstractSymbol classC, AbstractSymbol objectId) {
		if (this.objectEnvironment.containsKey(classC)) {
		    this.objectEnvironment.get(classC).remove(objectId);
		}
    }

    /* public void assignInheritedMethodSignatureTypes(AbstractSymbol classC, AbstractSymbol methodF, ClassTable classTable) {
	
    } */

    // Assigns a list of types [t_1,...,t_n] where [t_1,...,t_n-1] are the
    // types of the inputs to a method in a  class and t_n is its return
    // type.

    public void  assignMethodSignatureTypes(AbstractSymbol classC, AbstractSymbol methodF, ArrayList<AbstractSymbol> signature) {
		if (!this.methodEnvironment.containsKey(classC)) {
		    this.methodEnvironment.put(classC, new HashMap<AbstractSymbol, ArrayList<AbstractSymbol>>());
		}
		if (!this.methodEnvironment.get(classC).containsKey(methodF)) {
			HashMap<AbstractSymbol, ArrayList<AbstractSymbol>> methodsToSignatures = this.methodEnvironment.get(classC);
			methodsToSignatures.put(methodF, signature);
		}
    }

    public boolean compareMethodSignatures(ArrayList<AbstractSymbol> signature1, ArrayList<AbstractSymbol> signature2) {
		if (signature1.size() != signature2.size()) {
		    return false;
		}
		for (int index = 0; index < signature1.size(); index+=1) {
		    AbstractSymbol signatureElement1 = signature1.get(index);
		    AbstractSymbol signatureElement2 = signature2.get(index);
		    // System.out.println("signatureElement1 = " + signatureElement1 + ", signatureElement2 = " + signatureElement2);
		    if (!signatureElement1.equals(signatureElement2)) {
			return false;
		    }
		}
		return true;
    }

    public AbstractSymbol getObjectType(AbstractSymbol classC, AbstractSymbol objectId) {
		if (this.objectEnvironment.containsKey(classC)) {
		    if (this.objectEnvironment.get(classC).containsKey(objectId)) {
			return this.objectEnvironment.get(classC).get(objectId);
		    }
		    return null;
		}
		return null;
    }

    public ArrayList<AbstractSymbol> getMethodSignature(AbstractSymbol classC, AbstractSymbol methodF) {
	// System.out.println("classC = " + classC + ", this.methodEnvironment.get(classC) = " + this.methodEnvironment.get(classC));
	// System.out.println("methodF = " + methodF);
		return this.methodEnvironment.get(classC).get(methodF);
    }

    public HashMap<AbstractSymbol, HashMap<AbstractSymbol, ArrayList<AbstractSymbol>>> getMethodEnvironment() {
		return this.methodEnvironment;
    }

    public HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>> getObjectEnvironment() {
		HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>> copy = new HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>>();
		for (AbstractSymbol className : this.objectEnvironment.keySet()) {
		    HashMap<AbstractSymbol, AbstractSymbol> copyBucket = new HashMap<AbstractSymbol, AbstractSymbol>();
		    copyBucket.putAll(this.objectEnvironment.get(className));
		    copy.put(className, copyBucket);
		}
		return copy;
    }

    public void setObjectEnvironment(HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>> newObjEnvironment) {
		this.objectEnvironment = newObjEnvironment;
    }
}