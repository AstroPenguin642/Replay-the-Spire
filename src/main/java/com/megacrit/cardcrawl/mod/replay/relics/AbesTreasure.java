package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.AbeCurse;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.ui.*;
import com.megacrit.cardcrawl.relics.*;

import java.util.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.io.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;

public class AbesTreasure extends AbstractRelic
{
    public static final String ID = "Abe's Treasure";
    
    public AbesTreasure() {
        super(ID, "abeTreasure.png", RelicTier.BOSS, LandingSound.SOLID);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Drowning", "#yDebuff that causes HP loss. Taking unblocked #yAttack damage increases #yDrowning, dealing unblocked #yAttack damage lowers it."));
        this.tips.add(new PowerTip("Abe's Revenge", "A #ySoulbound #yEthereal #rCurse that applies #yFrail."));
        this.initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
	
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new AbeCurse(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
    
    @Override
    public void atPreBattle() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PondfishDrowning(AbstractDungeon.player)));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new AbesTreasure();
    }
/*
    @Override
    public void onShuffle() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new AbeCurse(), 1, true, false));
    }
    */
	@Override
    public void renderLock(final SpriteBatch sb, final Color outlineColor) {
		final float rot = (float)ReflectionHacks.getPrivate((Object)this, (Class)AbstractRelic.class, "rotation");
        sb.setColor(outlineColor);
        sb.draw(ImageMaster.RELIC_LOCK_OUTLINE, this.currentX - 64.0f, this.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LOCK, this.currentX - 64.0f, this.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        if (this.hb.hovered) {
            String unlockReq = UnlockTracker.unlockReqs.get(this.relicId);
            if (unlockReq == null) {
                unlockReq = "Missing unlock req.";
            }
            if (InputHelper.mX < 1400.0f * Settings.scale) {
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW && InputHelper.mY < Settings.HEIGHT / 5.0f) {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY + 100.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
                }
                else {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
                }
            }
            else {
                TipHelper.renderGenericTip(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
            }
            float tmpX = this.currentX;
            float tmpY = this.currentY;
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LOCK, tmpX - 64.0f, tmpY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        }
        this.hb.render(sb);
    }
}
