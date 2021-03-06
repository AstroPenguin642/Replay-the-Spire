package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;

import replayTheSpire.ReplayTheSpireMod;
import sneckomod.SneckoMod;

public class sneckobs {
	public static void makeSneky(AbstractCard c) {
		if (ReplayTheSpireMod.foundmod_downfall) {
			c.tags.add(SneckoMod.SNEKPROOF);
			if (!c.rawDescription.contains("Snekproof")) {
				c.rawDescription = "sneckomod:Snekproof. NL " + c.rawDescription;
				c.initializeDescription();
			}
			return;
		}
		if (!ReplayTheSpireMod.foundmod_snecko) {
			return;
		}
		c.tags.add(SneckoMod.SNEKPROOF);
		if (!c.rawDescription.contains("Snekproof")) {
			c.rawDescription = "Snekproof. NL " + c.rawDescription;
			c.initializeDescription();
		}
	}
	public static boolean isSneky(AbstractCard c) {
		if (ReplayTheSpireMod.foundmod_downfall) {
			return (c.tags.contains(SneckoMod.SNEKPROOF));
		}
		if (!ReplayTheSpireMod.foundmod_snecko) {
			return false;
		}
		return (c.tags.contains(SneckoMod.SNEKPROOF));
	}
}
