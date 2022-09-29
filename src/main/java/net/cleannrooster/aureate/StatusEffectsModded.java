package net.cleannrooster.aureate;

import net.cleannrooster.aureate.entities.EtherealWolf;
import net.cleannrooster.aureate.entities.MirrorZombie;
import net.cleannrooster.aureate.items.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatusEffectsModded {

    public static final StatusEffect ETHEREAL = new EtherealBarrier();
    public static final StatusEffect FANOFKNIVES = new FanOfKnives();
    public static final StatusEffect SQUALL = new Squall();
    public static final StatusEffect MAIMED = new Maimed();
    public static final StatusEffect ETHEREALWOLF = new EtherealWolf();
    public static final StatusEffect MIRROR = new MirrorZombie();
    public static final StatusEffect FLURRYING = new Flurrying();
    public static final StatusEffect BLOODTHIRST = new Bloodthirst();
    public static final StatusEffect SEETHING = new Seething();
    public static final StatusEffect FISTFLURRY = new FistFlurry();












    public static void registerStatusEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "ethereal"), ETHEREAL);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "fanofknives"), FANOFKNIVES);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "squall"), SQUALL);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "ruptured"), MAIMED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "etherealwolf"), ETHEREALWOLF);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "mirror"), MIRROR);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "flurrying"), FLURRYING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "bloodthirst"), BLOODTHIRST);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "seething"), SEETHING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("aureate", "fistflurry"), FISTFLURRY);








    }
}
