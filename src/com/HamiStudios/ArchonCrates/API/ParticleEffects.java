package com.HamiStudios.ArchonCrates.API;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public enum ParticleEffects {


	HUGE_EXPLOSION("hugeexplosion"),
	LARGE_EXPLODE("largeexplode"),
	FIREWORKS_SPARK("fireworksSpark"),
	BUBBLE("bubble"),
	SUSPEND("suspend"),
	DEPTH_SUSPEND("depthSuspend"),
	TOWN_AURA("townaura"),
	CRIT("crit"),
	MAGIC_CRIT("magicCrit"),
	MOB_SPELL("mobSpell"),
	MOB_SPELL_AMBIENT("mobSpellAmbient"),
	SPELL("spell"),
	INSTANT_SPELL("instantSpell"),
	WITCH_MAGIC("witchMagic"),
	NOTE("note"),
	PORTAL("portal"),
	ENCHANTMENT_TABLE("enchantmenttable"),
	EXPLODE("explode"),
	FLAME("flame"),
	LAVA("lava"),
	FOOTSTEP("footstep"),
	SPLASH("splash"),
	LARGE_SMOKE("largesmoke"),
	CLOUD("cloud"),
	RED_DUST("reddust"),
	SNOWBALL_POOF("snowballpoof"),
	DRIP_WATER("dripWater"),
	DRIP_LAVA("dripLava"),
	SNOW_SHOVEL("snowshovel"),
	SLIME("slime"),
	HEART("heart"),
	ANGRY_VILLAGER("angryVillager"),
	HAPPY_VILLAGER("happerVillager"),
	ICONCRACK("iconcrack_"),
	TILECRACK("tilecrack_");

	private String particleName;
	ParticleEffects(String particleName) {
	      this.particleName = particleName;
	}

	public void sendToPlayer(Player player, Location location, double speed, int count) throws Exception {

		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
		ReflectionUtilities.setValue(packet, "a", particleName);
		ReflectionUtilities.setValue(packet, "b", (float) location.getX());
		ReflectionUtilities.setValue(packet, "c", (float) location.getY());
		ReflectionUtilities.setValue(packet, "d", (float) location.getZ());
		ReflectionUtilities.setValue(packet, "e", 0);
		ReflectionUtilities.setValue(packet, "f", 0);
		ReflectionUtilities.setValue(packet, "g", 0);
		ReflectionUtilities.setValue(packet, "h", speed);
		ReflectionUtilities.setValue(packet, "i", count);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	
	}


}

