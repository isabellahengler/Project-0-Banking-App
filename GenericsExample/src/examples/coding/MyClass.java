package examples.coding;

public class MyClass <T> {
	
	T ob; 
	
	MyClass(T ob){
		
		this.ob = ob; 
	}
	
	void showType () {
		System.out.println(ob.getClass().getName()); 
		
	}

}
