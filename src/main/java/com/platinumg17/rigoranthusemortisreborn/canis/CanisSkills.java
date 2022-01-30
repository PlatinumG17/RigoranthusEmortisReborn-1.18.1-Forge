package com.platinumg17.rigoranthusemortisreborn.canis;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.*;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.Skill;
import com.platinumg17.rigoranthusemortisreborn.api.apicanis.registry.SkillInstance;
import com.platinumg17.rigoranthusemortisreborn.canis.common.lib.EmortisConstants;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.NeptunesBaneSkill;
import com.platinumg17.rigoranthusemortisreborn.canis.common.skill.CavalierSkill;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class CanisSkills {

    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(Skill.class, EmortisConstants.MOD_ID);

    public static final RegistryObject<Skill> BRAWLER = registerInst("brawler", BrawlerSkill::new);
    public static final RegistryObject<Skill> CALLOUSED_SOLES = registerInst("calloused_soles", CallousedSolesSkill::new);
    public static final RegistryObject<Skill> CAVALIER = registerInst("cavalier", CavalierSkill::new);
    public static final RegistryObject<Skill> CERBERUS = registerInst("cerberus", CerberusSkill::new);
    public static final RegistryObject<Skill> CHUNGUS_PUPPER = registerInst("chungus_pupper", ChungusPupperSkill::new);
    public static final RegistryObject<Skill> IRIDESCENCE = registerInst("iridescence", IridescenceSkill::new);
    public static final RegistryObject<Skill> MAD_DASH = registerInst("mad_dash", MadDashSkill::new);
    public static final RegistryObject<Skill> NEPTUNES_BANE = registerInst("neptunes_bane", NeptunesBaneSkill::new);
    public static final RegistryObject<Skill> OCEANIC_RAIDER = registerInst("oceanic_raider", OceanicRaiderSkill::new);
    public static final RegistryObject<Skill> PERNICIOUS_FANGS = registerInst("pernicious_fangs", PerniciousFangsSkill::new);
    public static final RegistryObject<Skill> RAPID_RECOVERY = registerInst("rapid_recovery", RapidRecoverySkill::new);
    public static final RegistryObject<Skill> SAVIOR = registerInst("savior", SaviorSkill::new);
    public static final RegistryObject<Skill> SENTINEL = registerInst("sentinel", SentinelSkill::new);
    public static final RegistryObject<Skill> SUMMONER = registerInst("summoner", SummonerSkill::new);
    public static final RegistryObject<Skill> SNARL = registerInst("snarl", SeizingSnarlSkill::new);
    public static final RegistryObject<Skill> UNDERTAKER = registerInst("undertaker", UndertakerSkill::new);
    public static final RegistryObject<Skill> PREDATOR = registerInst("predator", null);
    public static final RegistryObject<Skill> WAYWARD_TRAVELLER = registerInst("wayward_traveller", WaywardTravellerSkill::new);
    //public static final RegistryObject<Skill> RANGED_ATTACKER = registerInst("ranged_attacker", RangedAttacker::new);

    private static <T extends Skill> RegistryObject<Skill> registerInst(final String name, final BiFunction<Skill, Integer, SkillInstance> sup) {
        return register(name, () -> new Skill(sup));
    }

    private static <T extends Skill> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return SKILLS.register(name, sup);
    }
}