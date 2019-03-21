package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;

public class RidThyselfOfStatusCardsPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "ReplayTheSpireMod:SystemScan";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RidThyselfOfStatusCardsPower(final AbstractCreature owner, final int amount) {
        this.name = RidThyselfOfStatusCardsPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("artifact");
        this.type = PowerType.BUFF;
    }
    
    @Override
    public void onSpecificTrigger() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        else {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = RidThyselfOfStatusCardsPower.DESCRIPTIONS[0] + this.amount + RidThyselfOfStatusCardsPower.DESCRIPTIONS[1];
        }
        else {
            this.description = RidThyselfOfStatusCardsPower.DESCRIPTIONS[0] + this.amount + RidThyselfOfStatusCardsPower.DESCRIPTIONS[2];
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = RidThyselfOfStatusCardsPower.powerStrings.NAME;
        DESCRIPTIONS = RidThyselfOfStatusCardsPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new RidThyselfOfStatusCardsPower(owner, amount);
	}
}
