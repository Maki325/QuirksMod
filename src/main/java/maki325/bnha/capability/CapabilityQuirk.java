package maki325.bnha.capability;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.api.Quirk;

public class CapabilityQuirk implements IQuirk {

	List<Quirk> quirks = new ArrayList<Quirk>();
	
	@Override
	public List<Quirk> getQuirks() {
		return quirks;
	}
	@Override
	public void addQuirks(Quirk... quirk) {
		for(Quirk q:quirk) {
			if(!quirks.contains(q))
				quirks.add(q);
		}
	}
	@Override
	public void removeQuirks(Quirk... quirk) {
		for(Quirk q:quirk) {
			if(quirks.contains(quirk))
				quirks.remove(quirk);
		}
	}
	@Override
	public void reset() {
		quirks.clear();
	}

}
