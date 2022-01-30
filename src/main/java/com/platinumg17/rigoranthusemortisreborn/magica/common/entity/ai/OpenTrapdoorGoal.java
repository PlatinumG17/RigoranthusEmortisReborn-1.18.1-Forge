package com.platinumg17.rigoranthusemortisreborn.magica.common.entity.ai;

import net.minecraft.world.entity.Mob;

public class OpenTrapdoorGoal extends InteractTrapdoorGoal {
    private final boolean closeDoor;
    private int forgetTime;

    public OpenTrapdoorGoal(Mob p_i1644_1_, boolean p_i1644_2_) {
        super(p_i1644_1_);
        this.mob = p_i1644_1_;
        this.closeDoor = p_i1644_2_;
    }

    public boolean canContinueToUse() {
        return this.closeDoor && this.forgetTime > 0 && super.canContinueToUse();
    }

    public void start() {
        this.forgetTime = 20;
        this.setOpen(true);
    }

    public void stop() {
        this.setOpen(false);
    }

    public void tick() {
        --this.forgetTime;
        super.tick();
    }
}