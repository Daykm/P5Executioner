package com.daykm.p5executioner;

import com.daykm.p5executioner.json.JsonCombo;
import com.daykm.p5executioner.json.JsonPersonaDetail;
import com.daykm.p5executioner.json.JsonSkillDetail;
import com.daykm.p5executioner.json.JsonSpecialCombo;
import com.daykm.p5executioner.proto.Arcana;
import com.daykm.p5executioner.proto.ArcanaCombo;
import com.daykm.p5executioner.proto.DLCPersona;
import com.daykm.p5executioner.proto.Data;
import com.daykm.p5executioner.proto.Persona;
import com.daykm.p5executioner.proto.RareComboModifier;
import com.daykm.p5executioner.proto.Skill;
import com.daykm.p5executioner.proto.SpecialCombo;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okio.Okio;
import okio.Source;

public class Main {

	private static final String OUTPUT = "gen/src/main/assets/";
	private static final String INPUT = "transformer/json/";

	public static void main(String[] args) throws Exception {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		Moshi moshi = new Moshi.Builder().build();

		Data data = Data.newBuilder()
				.addAllArcanaCombos(createArcanaCombos(moshi))
				.addAllPersonas(createPersonas(moshi))
				.addAllSkills(createSkills(moshi))
				.addAllRareModifiers(createRareCombos(moshi))
				.addAllSpecialCombos(createSpecialCombos(moshi))
				.addAllDlcPersonae(createDlcPersonae(moshi))
				.build();

		Files.write(Paths.get(OUTPUT + "data.pb"), data.toByteArray());

		data = Data.parseFrom(Files.readAllBytes(Paths.get(OUTPUT + "data.pb")));

		System.out.print("finished");
	}

	public static List<DLCPersona> createDlcPersonae(Moshi moshi) throws Exception {
		JsonAdapter<List<List<String>>> adapter = moshi.adapter(Types.newParameterizedType(List.class,
				Types.newParameterizedType(List.class, String.class)));

		List<List<String>> serieses =
				adapter.fromJson(Okio.buffer(Okio.source(new File(INPUT + "dlcPersonae.json"))));

		List<DLCPersona> protoPersonae = new ArrayList<>(serieses.size());
		for (List<String> series : serieses) {
			protoPersonae.add(DLCPersona.newBuilder().addAllPersonaeInSeries(series).build());
		}
		return protoPersonae;
	}

	public static List<SpecialCombo> createSpecialCombos(Moshi moshi) throws Exception {

		JsonAdapter<List<JsonSpecialCombo>> adapter =
				moshi.adapter(Types.newParameterizedType(List.class, JsonSpecialCombo.class));

		List<JsonSpecialCombo> combos =
				adapter.fromJson(Okio.buffer(Okio.source(new File(INPUT + "specialCombos.json"))));

		List<SpecialCombo> protoCombos = new ArrayList<>(combos.size());

		for (JsonSpecialCombo combo : combos) {
			protoCombos.add(
					SpecialCombo.newBuilder().setResult(combo.result).addAllSources(combo.sources).build());
		}

		return protoCombos;
	}

	public static List<ArcanaCombo> createArcanaCombos(Moshi moshi) throws Exception {
		JsonAdapter<List<JsonCombo>> adapter =
				moshi.adapter(Types.newParameterizedType(List.class, JsonCombo.class));

		List<JsonCombo> json =
				adapter.fromJson(Okio.buffer(Okio.source(new File(INPUT + "arcanaCombos.json"))));

		List<ArcanaCombo> protoCombos = new ArrayList<>(json.size());

		for (JsonCombo combo : json) {
			protoCombos.add(ArcanaCombo.newBuilder()
					.setFirst(Arcana.valueOf(combo.source[0].toUpperCase()))
					.setSecond(Arcana.valueOf(combo.source[1].toUpperCase()))
					.setResult(Arcana.valueOf(combo.result.toUpperCase()))
					.build());
		}

		return protoCombos;
	}

	public static List<Skill> createSkills(Moshi moshi) throws Exception {

		ParameterizedType type =
				Types.newParameterizedType(Map.class, String.class, JsonSkillDetail.class);

		JsonAdapter<Map<String, JsonSkillDetail>> adapter = moshi.adapter(type);

		Source source = Okio.source(new File(INPUT + "skills.json"));
		Map<String, JsonSkillDetail> skills = adapter.fromJson(Okio.buffer(source));

		List<Skill> protoSkills = new ArrayList<>(skills.size());

		for (String key : skills.keySet()) {
			JsonSkillDetail detail = skills.get(key);

			Skill.PersonaAndLevel.Builder builder = Skill.PersonaAndLevel.newBuilder();
			for (String persona : detail.personas.keySet()) {
				builder.setPersona(persona).setLevel(detail.personas.get(persona));
			}
			protoSkills.add(Skill.newBuilder()
					.addAllTalks(detail.talks != null ? detail.talks : new ArrayList<String>(0))
					.setCost(detail.cost != null ? detail.cost : 0)
					.setName(key)
					.addPersonaeWhoLearn(builder)
					.build());
		}
		return protoSkills;
	}

	public static List<RareComboModifier> createRareCombos(Moshi moshi) throws Exception {

		ParameterizedType type = Types.newParameterizedType(Map.class, String.class,
				Types.newParameterizedType(List.class, Integer.class));

		JsonAdapter<Map<String, List<Integer>>> adapter = moshi.adapter(type);

		Source source = Okio.source(new File(INPUT + "rareCombos.json"));
		Map<String, List<Integer>> rareCombos = adapter.fromJson(Okio.buffer(source));

		List<RareComboModifier> protoModifiers = new ArrayList<>(rareCombos.size());

		for (String key : rareCombos.keySet()) {
			protoModifiers.add(RareComboModifier.newBuilder()
					.setArcana(Arcana.valueOf(key.toUpperCase()))
					.addAllModifiers(rareCombos.get(key))
					.build());
		}

		return protoModifiers;
	}

	public static List<Persona> createPersonas(Moshi moshi) throws Exception {
		ParameterizedType type =
				Types.newParameterizedType(Map.class, String.class, JsonPersonaDetail.class);

		JsonAdapter<Map<String, JsonPersonaDetail>> adapter = moshi.adapter(type);

		Source source = Okio.source(new File(INPUT + "personae.json"));

		Map<String, JsonPersonaDetail> personae = adapter.fromJson(Okio.buffer(source));

		List<Persona> protoPersonas = new ArrayList<>(personae.size());

		for (String key : personae.keySet()) {
			JsonPersonaDetail detail = personae.get(key);
			Persona.Builder builder = Persona.newBuilder();

			List<Persona.Affinities.AffinityOption> options = new ArrayList<>();

			for (String affinity : detail.elems) {
				switch (affinity) {
					case "ab":
						options.add(Persona.Affinities.AffinityOption.ABSORB);
						break;
					case "-":
						options.add(Persona.Affinities.AffinityOption.NONE);
						break;
					case "wk":
						options.add(Persona.Affinities.AffinityOption.WEAK);
						break;
					case "rs":
						options.add(Persona.Affinities.AffinityOption.RESIST);
						break;
					case "rp":
						options.add(Persona.Affinities.AffinityOption.REPEL);
						break;
					case "nu":
						options.add(Persona.Affinities.AffinityOption.NULL);
						break;
					default:
						options.add(Persona.Affinities.AffinityOption.UNRECOGNIZED);
				}
			}

			builder.setAffinities(Persona.Affinities.newBuilder()
					.setPhysical(options.get(0))
					.setGun(options.get(1))
					.setFire(options.get(2))
					.setIce(options.get(3))
					.setElectric(options.get(4))
					.setWind(options.get(5))
					.setPsychic(options.get(6))
					.setNuclear(options.get(7))
					.setBless(options.get(8))
					.setCurse(options.get(9)));

			builder.setStats(Persona.Stats.newBuilder()
					.setStrength(detail.stats[0])
					.setMagic(detail.stats[1])
					.setEndurance(detail.stats[2])
					.setAgility(detail.stats[3])
					.setLuck(detail.stats[4]));

			for (String skill : detail.skills.keySet()) {
				builder.addSkills(
						Persona.LearnedSkills.newBuilder().setName(skill).setLevel(detail.skills.get(skill)));
			}

			protoPersonas.add(builder.setName(key)
					.setLevel(detail.level)
					.setArcana(Arcana.valueOf(detail.arcana.toUpperCase()))
					.build());
		}
		return protoPersonas;
	}
}
