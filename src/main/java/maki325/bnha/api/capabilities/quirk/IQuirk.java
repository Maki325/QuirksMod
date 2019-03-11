package maki325.bnha.api.capabilities.quirk;

import java.util.List;

import maki325.bnha.api.ResourceIdentifier;
import maki325.bnha.api.quirk.Quirk;

public interface IQuirk {

	public List<Quirk> getQuirks();
	
	public void addQuirks(Quirk... quirk);
	
	public void removeQuirks(Quirk... quirk);
	
	public Quirk getQuirk(int index);
	
	public Quirk getQuirk(ResourceIdentifier id);
	
	public void clear();
	
}
