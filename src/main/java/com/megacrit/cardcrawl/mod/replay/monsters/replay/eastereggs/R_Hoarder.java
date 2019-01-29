package com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.Hallucinations;
import com.megacrit.cardcrawl.mod.replay.cards.curses.LoomingEvil;
import com.megacrit.cardcrawl.mod.replay.cards.curses.SpreadingInfection;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
//import com.megacrit.cardcrawl.monsters.EnemyType;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class R_Hoarder extends AbstractMonster
{
    public static final String ID = "R_Hoarder";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP = 300;
    private static final float HB_X = 0.0f;
    private static final float HB_Y = -40.0f;
    private static final float HB_W = 430.0f;
    private static final float HB_H = 360.0f;
    private static final int TERRIFY_DUR = 3;
    private static final int DROOL_STR = 3;
    private static final int SLAM_DMG = 25;
    private static final int NOM_DMG = 5;
    private static final int A_2_SLAM_DMG = 30;
    private int slamDmg;
    private int nomDmg;
    private static final byte ROAR = 2;
    private static final byte SLAM = 3;
    private static final byte DROOL = 4;
    private static final byte NOMNOMNOM = 5;
    private boolean roared;
    private int turnCount;
    
    public R_Hoarder(final float x, final float y) {
        super(R_Hoarder.NAME, R_Hoarder.ID, 300, 0.0f, -40.0f, 360.0f, 440.0f, "images/monsters/beyond/The_Collector.png", x, y);
        this.roared = false;
        this.turnCount = 1;
        this.type = EnemyType.ELITE;
        //this.loadAnimation("images/monsters/theForest/maw/skeleton.atlas", "images/monsters/theForest/maw/skeleton.json", 1.0f);
        //final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //e.setTime(e.getEndTime() * MathUtils.random());
        this.dialogX = -160.0f * Settings.scale;
        this.dialogY = 40.0f * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.slamDmg = 30;
            this.nomDmg = 6;
        }
        else {
            this.slamDmg = 25;
            this.nomDmg = 5;
        }
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(350);
        }
        else {
            this.setHp(300);
        }
        this.damage.add(new DamageInfo(this, this.slamDmg));
        this.damage.add(new DamageInfo(this, this.nomDmg));
    }
    
    @Override
    public void usePreBattleAction() {
         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TimeCollectorPower(this, 12)));
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 2: {
            	if (AbstractDungeon.player.drawPile.size() >= 40) {
            		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new LoomingEvil(), 1, true, true));
            		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Hallucinations(), 2, true, true));
            		if (AbstractDungeon.player.drawPile.size() >= 55) {
            			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new SpreadingInfection(), 1, true, true));
            			if (AbstractDungeon.player.drawPile.size() >= 70) {
            				for (int i=0; i < (AbstractDungeon.player.drawPile.size() - 60)/10; i++) {
            					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse().makeCopy(), 1, true, true));
            				}
            			}
            		}
            	} else {
            		int limiter = 40;
                    if (AbstractDungeon.player.drawPile.size() < 30) {
                    	limiter -= 5;
                    }
                    if (AbstractDungeon.player.drawPile.size() <= 25) {
                    	limiter -= 5;
                    }
                    if (AbstractDungeon.player.drawPile.size() < 20) {
                    	limiter -= 5;
                    }
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, R_Hoarder.DIALOG[0], 1.0f, 2.0f));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(3.5f));
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, R_Hoarder.DIALOG[1] + limiter + R_Hoarder.DIALOG[2], 1.0f, 2.0f));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(3.0f));
                    int deckcount = limiter - AbstractDungeon.player.drawPile.size();
                    for (int i = 0; i < deckcount; i++) {
                    	  AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
    	      			  int r = MathUtils.random((AbstractDungeon.ascensionLevel >= 18) ? 9 : 8);
    	      			  if (r == 6 || r == 7)
    	      			  {
    	      				  rarity = AbstractCard.CardRarity.RARE;
    	      			  } else {
    	      				  if (r >= ((AbstractDungeon.ascensionLevel >= 18) ? 4 : 3)) {
    	      					  rarity = AbstractCard.CardRarity.UNCOMMON;
    	      				  }
    	      			  }
    	      			  AbstractCard c = AbstractDungeon.getCard(rarity).makeCopy();
    	      			  if (r > 6) {
    	      				  c = AbstractDungeon.getColorlessCardFromPool(rarity).makeCopy();
        				  }
    	      			  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, false));
                    }
            	}
                this.roared = true;
                break;
            }
            case 3: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            }
            case 4: {
            	for (int i = 0; i < ((AbstractDungeon.ascensionLevel >= 18) ? 3 : 2); i++) {
	            	switch (AbstractDungeon.miscRng.random(0, 11)) {
		    			case 0: case 5:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_VajraPower(this), 1));
		    				break;
		    			case 1:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_ThreadPower(this), 1));
		    				break;
		    			case 2:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_OrichalcumPower(this), 6));
		    				break;
		    			case 3: case 6:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_SmoothStonePower(this), 1));
		    				break;
		    			case 4:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_Mango(this, 14), 14));
		    				break;
		    			case 7:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_BronzeScales(this), 3));
		    				break;
		    			case 8:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_HourglassPower(this), 3));
		    				break;
		    			case 9:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_SelfFormingClay(this), 3));
		    				break;
		    			case 10:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_GiryaPower(this), 3));
		    				break;
		    			case 11:
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RP_IncenseBurner(this)));
		    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1), 1));
		    				break;
		    		}
            	}
                break;
            }
            case 5: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                int amt = 5;
                if (this.hasPower("Dexterity")) {
                	amt += this.getPower("Dexterity").amount;
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, amt));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, amt));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    @Override
    protected void getMove(final int num) {
        ++this.turnCount;
        if (!this.roared) {
            this.setMove(MOVES[0], (byte)2, Intent.STRONG_DEBUFF);
            return;
        }
        if (num < 50 && !this.lastMove((byte)5)) {
        	this.setMove((byte)5, Intent.ATTACK_DEFEND, this.damage.get(1).base, 2, true);
            return;
        }
        if (this.lastMove((byte)3) || this.lastMove((byte)5)) {
            this.setMove(MOVES[1], (byte)4, Intent.BUFF);
            return;
        }
        this.setMove((byte)3, Intent.ATTACK, this.damage.get(0).base);
    }
    /*
    @Override
    public void die() {
        super.die();
        CardCrawlGame.sound.play("MAW_DEATH");
    }
    */
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("R_Hoarder");
        NAME = R_Hoarder.monsterStrings.NAME;
        MOVES = R_Hoarder.monsterStrings.MOVES;
        DIALOG = R_Hoarder.monsterStrings.DIALOG;
    }
}
