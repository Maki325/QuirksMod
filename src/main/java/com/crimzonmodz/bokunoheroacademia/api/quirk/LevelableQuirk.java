package com.crimzonmodz.bokunoheroacademia.api.quirk;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public abstract class LevelableQuirk extends Quirk {

    protected int level = 0;
    protected double xp = 0, nextXp = 10, xpPerUse = 10;

    public LevelableQuirk(ResourceLocation id) { super(id); }

    public LevelableQuirk(ResourceLocation id, String name) { super(id, name); }

    public void updateXP() {
        this.xp += this.xpPerUse;
        if(this.xp >= this.nextXp) {
            this.xp = 0;
            this.level++;
            this.nextXp *= this.level;
            onLevelUp();
        }
    }

    public abstract void onLevelUp();

    @Override public CompoundNBT save() {
        CompoundNBT tag = super.save();
        tag.putDouble("xp", this.xp);
        tag.putDouble("nextXp", this.nextXp);
        tag.putDouble("xpPerUse", this.xpPerUse);
        tag.putInt("level", this.level);
        return tag;
    };

    @Override public void load(CompoundNBT tag) {
        super.load(tag);
        this.xp = tag.getDouble("xp");
        this.nextXp = tag.getDouble("nextXp");
        this.xpPerUse = Math.max(tag.getDouble("xpPerUse"), 10);
        this.level = tag.getInt("level");
    };

    public void setXpPerUse(double xp) { this.xpPerUse = Math.max(xp, 10); }
    public double getXpPerUse() { return this.xpPerUse; }

    public double getXp() { return this.xp; }
    public double getNextXp() { return this.nextXp; }
    public int getLevel() { return this.level; }

    @Override
    public String toString() { return super.toString() + ", Level: " + level + ", XP: " + xp; }

}
