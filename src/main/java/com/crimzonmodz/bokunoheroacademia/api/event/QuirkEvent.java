package com.crimzonmodz.bokunoheroacademia.api.event;

import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class QuirkEvent {

    public static class Change extends Event {

        private final Quirk before;
        private final Quirk after;
        private final PlayerEntity player;

        public Change(PlayerEntity player, Quirk before, Quirk after) {
            this.player = player;
            this.before = before;
            this.after = after;
        }

        public PlayerEntity getPlayer() {
            return player;
        }

        public Quirk getBefore() {
            return before;
        }

        public Quirk getAfter() {
            return after;
        }
    }

    public static class CreateQuirk extends Event {

        private Quirk quirk;

        public CreateQuirk(Quirk quirk) {
            this.quirk = quirk;
        }

        public Quirk getQuirk() {
            return quirk;
        }

        public void setQuirk(Quirk quirk) {
            this.quirk = quirk;
        }
    }

}
