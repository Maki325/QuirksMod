package maki325.bnha.api.capabilities.quirk;

import java.util.concurrent.Callable;

public class FactoryQuirk implements Callable<IQuirk> {

	@Override
	public IQuirk call() throws Exception {
		return new CapabilityQuirk();
	}
	  
}