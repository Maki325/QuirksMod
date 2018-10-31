package maki325.bnha.api;

public class Pair<U, V> {

	private U u;
	private V v;
	
	public Pair(U u, V v) {
		this.u = u;
		this.v = v;
	}
	
	public U getFirst() {
		return u;
	}
	
	public V getSecond() {
		return v;
	}
	
}
