package maki325.bnha.api;

public class LevelUp {
	
	private double cooldownMultiplier, activatedMultiplier;
	
	public LevelUp(double cooldownMultiplier, double activatedMultiplier) {
		this.cooldownMultiplier = cooldownMultiplier;
		this.activatedMultiplier = activatedMultiplier;
	}
	
	public double getCooldownMultiplier() {
		return cooldownMultiplier;
	}
	
	public double getActivatedMultiplier() {
		return activatedMultiplier;
	}

}
