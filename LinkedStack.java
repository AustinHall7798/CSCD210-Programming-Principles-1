// Austin Hall
// CSCD 300
// 5/16/19

public class LinkedStack {

	private class Node {
		private Object data;
		private Node next;
		public Node(Object e, Node n) {
			this.data = e;
			this.next = n;
		}
	}
	
	private Node top;
	private int size;
	
	public LinkedStack() {
		this.top = null;
		this.size = 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.top == null || this.size == 0;
	}
	
	public void push(Object elm) { //Equivalent to addFirst
		Node nn = new Node(elm, this.top);
		this.top = nn;
		this.size++;
	}
	
	public Object pop() throws EmptyStackException {
		if(isEmpty()) {
			throw new EmptyStackException("Stack is empty");
		}
		Object temp = this.top.data;
		this.top = this.top.next;
		this.size--;
		return temp;
	}
	
	public Object top() {
		if(isEmpty()) {
			throw new EmptyStackException("Stack is empty");
		}
		return this.top.data;
	}
	
	public void clearStack() {
		if(!isEmpty()) {
			this.top = null;
		}
	}
	
	class EmptyStackException extends RuntimeException {
		public EmptyStackException(String err) {
			super(err);
		}
	}
}
