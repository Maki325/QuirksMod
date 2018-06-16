package maki325.bnha.capability;

import java.util.List;

import maki325.bnha.api.Quirk;

public interface IQuirk {

	public List<Quirk> getQuirks();
	
	public void addQuirks(Quirk... quirk);
	
	public void removeQuirks(Quirk... quirk);
	
	public void reset();
	
}
