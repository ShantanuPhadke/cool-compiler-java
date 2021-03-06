(*
 *  CS164 Fall 94
 *
 *  Programming Assignment 1
 *    Implementation of a simple stack machine.
 *
 *  Skeleton file
 *)

class Main inherits IO {
	flag : Bool <- true;
	char : String;
	int: Int;
	l : List <- new List;	
	
	getInput(): String {
		in_string()
	};

	getIntInput(): Int {
		in_int()
	};

	main(): Object {
		while flag loop {
			out_string(">");
			char <- getInput();
			--int <- getIntInput();
			if char = "x" then flag <- false else
			if char = "s" then l <- (new StringCommand).init().apply_command(l, char, 0) else
			if char = "+" then l <- (new StringCommand).init().apply_command(l, char, 0) else
			if char = "d" then (new DisplayCommand).init().apply_command(l, char, 0) else
			if char = "e" then l <- (new EvalCommand).init().apply_command(l, char, 0) else
			l <- (new IntCommand).init().apply_command(l,"",new A2I.a2i(char))	
			fi fi fi fi fi;
		}
		pool
	};

	
};

class StackObject inherits Object {
      type: String;
      intValue: Int;
      stringValue: String;
      
      getType() : String { type };
      getIntValue() : Int { intValue };
      getStringValue() : String { stringValue };
      init(typeVal: String, intVal: Int, strValue: String) : StackObject {
      	{
			type <- typeVal;
			intValue <- intVal;
			stringValue <- strValue;
			self;
		}
      };
};

class List {
      isNil() : Bool { true };
      head() : StackObject { { abort(); new StackObject; } };
      tail() : List { {  self; } };
      cons(i : StackObject) : List {
      	(new Cons).init(i, self)
      };
};

class Cons inherits List {
      car : StackObject;
      cdr : List;
      isNil() : Bool { false };
      head() : StackObject { car };
      tail() : List { cdr };
      init(i: StackObject, rest: List) : List {
      	{
			car <- i;
			cdr <- rest;
			self;
		}
      };
};

class StackCommand inherits IO {
        head : StackObject;
	list : List;
	
	init() : StackCommand {
	   {
	       self;
	   }	      
	};

	apply_command(l: List, symbol: String, int: Int) : List {
		list <- new List.cons(new StackObject)
	};

        print_list(l: List) : Object {
		if l.isNil() then 0  else
	        if l.head().getType() = "int" then
		   {
	            out_int(l.head().getIntValue());
		       	out_string("\n");
		       	print_list(l.tail());
		   }
	        else {
	           	out_string(l.head().getStringValue());
		       	out_string("\n");
		       	print_list(l.tail());
		   }
	        fi fi
       };	
};

class IntCommand inherits StackCommand {
      newList : List;
      intStackObj : StackObject;
      apply_command(l: List, symbol: String, int: Int) : List {
         {
	     	intStackObj <- (new StackObject).init("int", int, symbol); 
            newList <- (new Cons).init(intStackObj, l);
	     	newList;
         }
      };
};

class StringCommand inherits StackCommand {
      newList : List;
      strStackObj : StackObject;
      apply_command(l: List, symbol: String, int: Int) : List {
         {
	     	strStackObj <- (new StackObject).init("str", int, symbol);
         	newList <- (new Cons).init(strStackObj, l);
	     	newList;
         }
      };
};

class DisplayCommand inherits StackCommand {
      apply_command(l: List, symbol: String, int: Int) : List {
          {
	      	print_list(l);
	      	l;
          }
      };
};

class EvalCommand inherits StackCommand {
     int1 : Int;
     int2 : Int;
     addResult : StackObject;
     stackObj1 : StackObject;
     stackObj2 : StackObject;
     
     evaluate_add(l: List) : List {
        {
     	    int1 <- l.tail().head().getIntValue();
	    	int2 <- l.tail().tail().head().getIntValue();
	    	addResult <- (new StackObject).init("int",int1+int2,"");
	    	(new Cons).init(addResult, l.tail().tail().tail());
        }
     };

     evaluate_swap(l: List) : List {
         {
            stackObj1 <- l.tail().head();
	     	stackObj2 <- l.tail().tail().head();
	     	l <- (new Cons).init(stackObj1, l.tail().tail().tail());
	     	l <- (new Cons).init(stackObj2, l);
	     	l;
	 	}
     };

     apply_command(l: List, symbol: String, int: Int) : List {
	 	if l.head().getStringValue() = "+" then evaluate_add(l) else
	 	if l.head().getStringValue() = "s" then evaluate_swap(l) else l
	 	fi fi
     };
};
