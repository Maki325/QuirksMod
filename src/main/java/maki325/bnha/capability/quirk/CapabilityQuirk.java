package maki325.bnha.capability.quirk;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.api.Quirk;

public class CapabilityQuirk implements IQuirk {

	private List<Quirk> quirks = new ArrayList<Quirk>();
	
	private int points = 0;
	
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
	public void reset() {
		quirks.clear();
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public void addPoints(int points) {
		this.points += points;
	}

	@Override
	public void removePoints(int points) {
		this.points -= points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

}
