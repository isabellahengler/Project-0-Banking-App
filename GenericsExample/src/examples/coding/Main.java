package examples.coding;

 class Main {
	
	public static void main(String[] args ) {
		MyClass <Integer> obj = new MyClass<>(10);
		MyClass<Double> obj2 = new MyClass <> (20.0); 
		
		obj.showType(); 
		obj2.showType();
	
	}

}
