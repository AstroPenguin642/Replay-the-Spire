package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.cards.red.DoubleTap;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.cards.red.Pummel;
import com.megacrit.cardcrawl.cards.red.SpotWeakness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.relics.*;

import blackrusemod.powers.*;

public class m_ScarletBlood extends M_MistRelic
{
    public static final String ID = "m_ScarletBlood";
    
    public m_ScarletBlood() {
        super(ID, "burningBlood.png", LandingSound.MAGICAL, blackrusemod.patches.AbstractCardEnum.SILVER, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void onGainStrength(ApplyPowerAction __instance) {
    	if (__instance.amount > 0) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.target, __instance.source, new KnivesPower(__instance.target, __instance.amount * 2), __instance.amount * 2));
    		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(__instance.target, this));
    	}
    }
    
	@Override
    public void onEquip() {
		AbstractDungeon.rareRelicPool.add("Red Skull");
		super.onEquip();
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new m_ScarletBlood();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Inflame());
        tmpPool.add(new Pummel());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new DemonForm());
        tmpPool.add(new DoubleTap());
        tmpPool.add(new LimitBreak());
        tmpPool.add(new MuscleTraining());
		return tmpPool;
	}
}

