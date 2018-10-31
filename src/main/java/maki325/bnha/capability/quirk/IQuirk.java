package maki325.bnha.capability.quirk;

import java.util.List;

import maki325.bnha.api.Quirk;

public interface IQuirk {

	public List<Quirk> getQuirks();
	
	public void addQuirks(Quirk... quirk);
	
	public void removeQuirks(Quirk... quirk);
	
	public void reset();

	public int getPoints();
	
	public void addPoints(int points);
	
	public void removePoints(int points);
	
	public void setPoints(int points);
	
}
