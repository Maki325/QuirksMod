package maki325.bnha.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QuirkRegistry {

	private static List<Quirk> QUIRKS = new ArrayList<Quirk>();

	//private static List<? extends Quirk> QUIRKS_INSTANCES = new ArrayList<? extends Quirk>();
	private static List<Class<? extends Quirk>> QUIRKS_INSTANCES = new ArrayList<Class<? extends Quirk>>();
	
	private static HashMap<Quirk, Pair<Integer, Integer>> UVS = new HashMap<Quirk, Pair<Integer, Integer>>();
	
	private static HashMap<Quirk, List<Method>> methods= new HashMap<Quirk, List<Method>>();
	
	public static int getIndex(Quirk q) {
		int i = 0;
		for(Quirk q1:QUIRKS) {
			if(q.isSimilar(q1)) {
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	public static void addQuirk(Quirk q) {
		QUIRKS.add(q);
	}
	
	public static List<Quirk> getQuirks() {
		return QUIRKS;
	}
	
	public static Quirk getQuirkByName(String name) {
		for(Quirk q:QUIRKS) {
			if(name.equalsIgnoreCase(q.getName())) {
				return q;
			}
		}
		return null;
	}
	
	/*public static <T extends Quirk> void registerQuirkInstance(T t) {
		QUIRKS_INSTANCES.add(t);
	}*/
	
	/*public static List<Class<? extends Quirk>> getQuirkInstances() {
		return QUIRKS_INSTANCES;
	}*/
	
	public static void registerQuirkInstance(Class<? extends Quirk> q) {
		QUIRKS_INSTANCES.add(q);
	}
	
	public static List<Class<? extends Quirk>> getQuirkInstances() {
		return QUIRKS_INSTANCES;
	}
	
	public static void setUV(Quirk q, int u, int v) {
		UVS.put(q, new Pair<Integer, Integer>(u, v));
	}
	
	public static Pair<Integer, Integer> getUV(Quirk q) {
		return UVS.get(q);
	}
	
}
