import java.util.*;

class Complain{
	private int idx;
	private int tabNum;
	private String content;

	//For testing
	//print Complain
	public void printComplain(){
		System.out.println("Complain " + this.idx + " from table " + this.tabNum);
		System.out.println("\tContent: " + this.content);
	}

	//constructor
	public Complain(int idx, int tabNum, String content){
		this.idx = idx;
		this.tabNum = tabNum;
		this.content = new String(content);
	}

	/**
	 * @return the idx
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx the idx to set
	 */
	public void setIdx(int idx) {
		this.idx = idx;
	}

	/**
	 * @return the tabNum
	 */
	public int getTabNum() {
		return tabNum;
	}

	/**
	 * @param tabNum the tabNum to set
	 */
	public void setTabNum(int tabNum) {
		this.tabNum = tabNum;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}

class ComplainList{
	static int nextComplainIdx = 1;

	static List<Complain> complainList = new LinkedList<Complain>();

	public static void addComplain(int tabNum, String content){
		complainList.add(new Complain(nextComplainIdx, tabNum, content));

		nextComplainIdx++;
	}
	public static void removeComplain(int complainIdx){
		Iterator<Complain> tempComplainListIterator = complainList.iterator();
		while(tempComplainListIterator.hasNext())
			if(tempComplainListIterator.next().getIdx() == complainIdx)
				tempComplainListIterator.remove();
	}
	/*
	public static Complain getComplain(int complainIdx){
		Iterator<Complain> tempComplainListIterator = complainList.iterator();

		while(tempComplainListIterator.hasNext()){
			Complain tempComplain = tempComplainListIterator.next();

			if(tempComplain.getIdx() == complainIdx)
				return tempComplain;
		}
		
		return null;
	}
	*/

	//methods for testing
	public static void printComplainList(){
		Iterator<Complain> tempComplainListIterator = complainList.iterator();
	
		System.out.println("----------Complain list----------");
		while(tempComplainListIterator.hasNext()){
			tempComplainListIterator.next().printComplain();

			System.out.println();
		}
	}
}
