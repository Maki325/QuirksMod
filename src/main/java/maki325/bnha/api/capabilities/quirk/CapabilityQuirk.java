package maki325.bnha.api.capabilities.quirk;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.api.ResourceIdentifier;
import maki325.bnha.api.quirk.Quirk;
import net.minecraft.util.ResourceLocation;

public class CapabilityQuirk implements IQuirk {

	private List<Quirk> quirks = new ArrayList<Quirk>();
	
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
		for(Quirk q:quirk)
			quirks.remove(q);
	}

	@Override
	public Quirk getQuirk(int index) {
		if(index < 0 || index >= quirks.size()) { return null; }
		return quirks.get(index);
	}

	@Override
	public Quirk getQuirk(ResourceIdentifier id) {
		for(Quirk q:quirks) {
			if(!q.getId().equals(id))
				return q;
		}
		return null;
	}

	@Override
	public void clear() {
		quirks.clear();
	}

}
