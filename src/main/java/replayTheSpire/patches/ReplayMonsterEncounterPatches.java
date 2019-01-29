package replayTheSpire.patches;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.mod.replay.events.thecity.ReplayMapScoutEvent;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs.*;
import com.megacrit.cardcrawl.mod.replay.relics.AbesTreasure;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.helpers.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.badlogic.gdx.math.*;

import basemod.BaseMod;

import java.util.*;

public class ReplayMonsterEncounterPatches {
	
	//@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "getEncounter")
	/*public static class ReplayEncounterPatch {
		public static MonsterGroup Postfix(MonsterGroup __result, final String key) {
			switch (key) {
				case "Pondfish": {
					if (ReplayTheSpireMod.useBakuSkeleton) {
						return new MonsterGroup(new AbstractMonster[] {new CaptainAbe(170.0f, -80.0f), new PondfishBoss(0.0f, -650.0f)});
					} else {
						return new MonsterGroup(new AbstractMonster[] {new CaptainAbe(170.0f, -55.0f), new PondfishBoss(0.0f, -650.0f)});
					}
				}
				case "Fading Forest": {
					return new MonsterGroup(new AbstractMonster[] {new FadingForestBoss()});
				}
				case "Erikyupuro Louse Elite": case "Erikyupuro": {
					return new MonsterGroup(new AbstractMonster[] { new J_louse_1(-350.0f, 25.0f), new J_louse_2(-125.0f, 10.0f), new J_louse_3(80.0f, 30.0f) });
				}
				case "R_Hoarder": {
					return new MonsterGroup(new AbstractMonster[] {new R_Hoarder(0, 10)});
				}
				default: {
					return __result;
				}
			}
			
		}
	}*/
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "setBoss")
	public static class ReplaySetBossPatch {
		public static void Postfix(AbstractDungeon __Instance, final String key) {
			if (ReplayMapScoutEvent.bannedBoss.equals("you're fucked lol")) {
				ReplayMapScoutEvent.bannedBoss = "none";
				AbstractDungeon.bossKey = ("LordOfAnnihilation");
				final BaseMod.BossInfo bossInfo = BaseMod.getBossInfo(AbstractDungeon.bossKey);
	            if (bossInfo != null) {
	                DungeonMap.boss = bossInfo.bossMap;
	                DungeonMap.bossOutline = bossInfo.bossMapOutline;
	            } else {
	            	DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/OldBossIcon.png");
					DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/OldBossIcon.png");
	            }
			} else {
				if (ReplayMapScoutEvent.bannedBoss.equals(AbstractDungeon.bossKey) && AbstractDungeon.bossList.size() > 1 && !AbstractDungeon.bossList.get(1).equals(ReplayMapScoutEvent.bannedBoss)) {
					AbstractDungeon.bossKey = AbstractDungeon.bossList.get(1);
					if (AbstractDungeon.bossKey.equals("Awakened One")) {
			            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/awakened.png");
			            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/awakened.png");
			        }
			        else if (AbstractDungeon.bossKey.equals("Time Eater")) {
			            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/timeeater.png");
			            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/timeeater.png");
			        }
			        else if (AbstractDungeon.bossKey.equals("Donu and Deca")) {
			            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/donu.png");
			            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/donu.png");
			        } else {
			        	final BaseMod.BossInfo bossInfo = BaseMod.getBossInfo(AbstractDungeon.bossKey);
			            if (bossInfo != null) {
			                DungeonMap.boss = bossInfo.bossMap;
			                DungeonMap.bossOutline = bossInfo.bossMapOutline;
			            } else {
			            	DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/OldBossIcon.png");
							DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/OldBossIcon.png");
			            }
			        }
				}
				ReplayMapScoutEvent.bannedBoss = "none";
			}
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Painkiller Herb")) {
				DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/OldBossIcon.png");
				DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/OldBossIcon.png");
			}
		}
	}

	//@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.Exordium", method = "initializeBoss")
	public static class ReplayExordiumBossListPatch {
		public static void Prefix(Exordium __Instance) {
			//Exordium.bossList.add("Fading Forest");
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "populateMonsterList")
	public static class ReplayExordiumElitePatch {
		public static void Prefix(AbstractDungeon __Instance, final ArrayList<MonsterInfo> monsters, final int numMonsters, final boolean elites) {
			if (elites) {
				if (__Instance instanceof Exordium) {
					//ReplayTheSpireMod.logger.info("_SPIRITS");
					//ReplayTheSpireMod.logger.info(CardCrawlGame.playerPref.getInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0));
					if ((CardCrawlGame.playerPref.getInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0) > 0 && Settings.isStandardRun()) || AbstractDungeon.player.name.equals("Jrmiah")) {
					monsters.add(new MonsterInfo("Erikyupuro", monsters.get(0).weight * ((float)(5 + AbstractDungeon.ascensionLevel) / 25f)));
					ReplayMonsterEncounterPatches.normalizeWeights(monsters);
					}
				} else if (__Instance instanceof TheBeyond) {
					if (AbstractDungeon.player.masterDeck.size() < Math.min((AbstractDungeon.player.relics.size() * 2) + 5, 40) || (AbstractDungeon.player.name.equals("Rhapsody") && AbstractDungeon.player.masterDeck.size() < 40)) {
						monsters.add(new MonsterInfo("R_Hoarder", monsters.get(0).weight * (((float)(AbstractDungeon.player.relics.size())) / Math.min((float)AbstractDungeon.player.masterDeck.size() / 2f, 12f))));
						ReplayMonsterEncounterPatches.normalizeWeights(monsters);
					}
				}
			}
		}
	}

    
    public static void normalizeWeights(final ArrayList<MonsterInfo> list) {
        Collections.sort(list);
        float total = 0.0f;
        for (final MonsterInfo i : list) {
            total += i.weight;
        }
        for (final MonsterInfo monsterInfo : list) {
            final MonsterInfo i = monsterInfo;
            monsterInfo.weight /= total;
        }
    }
	//@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.TheCity", method = "initializeBoss")
	public static class ReplayCityBossListPatch {
		@SpireInsertPatch(rloc=1)
		public static void InsertAfterClear(TheCity __Instance) {
			if (!ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(AbesTreasure.ID)) {
				TheCity.bossList.add("Pondfish");
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.TheBeyond", method = "initializeBoss")
	public static class ReplayBeyondBossListPatch {
		public static void Postfix(TheBeyond __Instance) {
			if (AbstractDungeon.bossList.size() > 1 && AbstractDungeon.bossList.contains(ReplayMapScoutEvent.bannedBoss)) {
				while (AbstractDungeon.bossList.size() > 1 && AbstractDungeon.bossList.contains(ReplayMapScoutEvent.bannedBoss)) {
					AbstractDungeon.bossList.remove(ReplayMapScoutEvent.bannedBoss);
				}
			}
			else if (ReplayMapScoutEvent.bannedBoss.equals("you're fucked lol")) {
				TheBeyond.bossList.add(0, "LordOfAnnihilation");
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.actions.unique.SummonGremlinAction", method = "getRandomGremlin")
	public static class SummonRandomGremlinPatch {
		@SpireInsertPatch(rloc=4, localvars={"pool"})
		public static void Insert(SummonGremlinAction __Instance, int slot, ArrayList<String> pool) {
			pool.add("GremlinCook");
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "spawnGremlin")
	public static class SpawnGremlinPatch {
		@SpireInsertPatch(rloc=4, localvars={"gremlinPool"})
		public static void Insert(final float x, final float y, ArrayList<String> gremlinPool) {
			gremlinPool.add("GremlinCook");
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "spawnGremlins")
	public static class SpawnGremlinsPatch {
		@SpireInsertPatch(rloc=4, localvars={"gremlinPool"})
		public static void Insert(ArrayList<String> gremlinPool) {
			gremlinPool.add("GremlinCook");
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "getGremlin")
	public static class GetGremlinPatch {
		public static AbstractMonster Postfix(AbstractMonster __Result, final String key, final float xPos, final float yPos) {
			if (__Result == null) {
				if (key.equals("GremlinCook")) {
					return new GremlinCook(xPos, yPos);
				}
			}
			return __Result;
		}
	}
	
	
}