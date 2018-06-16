package maki325.bnha.capability.factory;

import java.util.concurrent.Callable;

import maki325.bnha.capability.CapabilityQuirk;
import maki325.bnha.capability.IQuirk;

public class FactoryQuirk implements Callable<IQuirk> {

	  @Override
	  public IQuirk call() throws Exception {
	    return new CapabilityQuirk();
	  }
	  
}
