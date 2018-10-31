package maki325.bnha.capability.quirk.factory;

import java.util.concurrent.Callable;

import maki325.bnha.capability.quirk.CapabilityQuirk;
import maki325.bnha.capability.quirk.IQuirk;

public class FactoryQuirk implements Callable<IQuirk> {

	@Override
	public IQuirk call() throws Exception {
		return new CapabilityQuirk();
	}
	  
}
