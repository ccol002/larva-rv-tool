package main;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Editor ed = new Editor();
		
		System.out.println(ed.insert("h"));
		System.out.println(ed.insert("e"));
		System.out.println(ed.insert("l"));
		System.out.println("Saved: " + ed.save());
		System.out.println(ed.insert("l"));
		System.out.println(ed.insert("o"));
		System.out.println("Undid upto: " + ed.undo());
		
		
		
//		System.out.println("Undid upto: " + ed.undoOne());
//		System.out.println("Undid upto: " + ed.undoOne());
//		System.out.println("Undid upto: " + ed.undoOne());
//		System.out.println("Undid upto: " + ed.undoOne());
		
		
	}

}
