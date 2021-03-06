package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.PondfishDrowning;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.replayxover.downfallbs;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Fish_DragToHell extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_fish_1";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    
    public SS_Fish_DragToHell() {
        super(ID, "replay/ss_fish_drown", COST, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
       //this.tags.add(downfallen.STUDY_PONDFISH);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 6;
        this.exhaust = true;
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_skill.png", "expansioncontentResources/images/1024/bg_boss_skill.png");

    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PondfishDrowning(m, this.magicNumber), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Fish_DragToHell();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Fish_DragToHell.cardStrings.NAME;
        DESCRIPTION = SS_Fish_DragToHell.cardStrings.DESCRIPTION;
    }
}
