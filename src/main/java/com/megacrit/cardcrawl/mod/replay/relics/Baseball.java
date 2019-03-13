package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import replayTheSpire.patches.BottlePatches;

import java.util.*;

public class Baseball extends AbstractRelic
{
    public static final String ID = "Baseball";
    public AbstractCard card;
    
    public Baseball() {
        super("Baseball", "baseball.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        final ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        for (final AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
            if (abstractCard.cost > 0) {
                list.add(abstractCard);
            }
        }
        Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!list.isEmpty()) {
            list.get(0).cost = 0;
            list.get(0).costForTurn = 0;
            list.get(0).upgradedCost = true;
            list.get(0).isCostModified = true;
            this.card = list.get(0);
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)list.get(0));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(list.get(0).makeStatEquivalentCopy()));
        }
    }
    @Override
    public void onUnequip() {
    	if (this.card == null)
    		return;
    	this.card.cost = this.card.makeCopy().cost;
    	this.card.costForTurn = this.card.cost;
    }
    
    public AbstractRelic makeCopy() {
        return new Baseball();
    }
    
	public static void save(final SpireConfig config) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
            final Baseball relic = (Baseball)AbstractDungeon.player.getRelic(ID);
            config.setInt("baseball", AbstractDungeon.player.masterDeck.group.indexOf(relic.card));
        }
        else {
            config.remove("baseball");
        }
    }
    
    public static void load(final SpireConfig config) {
        if (AbstractDungeon.player.hasRelic(ID) && config.has("baseball")) {
            final Baseball relic = (Baseball)AbstractDungeon.player.getRelic(ID);
            final int cardIndex = config.getInt("baseball");
            if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
                relic.card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
                if (relic.card != null) {
                	relic.card.cost = 0;
                	relic.card.costForTurn = 0;
                	relic.card.upgradedCost = true;
                	relic.card.isCostModified = true;
                }
            }
        }
    }
    
    public static void clear() {
    }
}
