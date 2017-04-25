package com.daykm.p5executioner;
import com.daykm.p5executioner.json.Combo;
import com.daykm.p5executioner.json.CombosJson;
import com.daykm.p5executioner.json.PersonaDetail;
import com.daykm.p5executioner.json.SkillDetail;
import com.daykm.p5executioner.json.SpecialCombo;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import okio.Okio;
import okio.Source;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		Moshi moshi = new Moshi.Builder().build();

		createArcanaCombos(moshi);
		createRareCombos(moshi);
		createPersonas(moshi);
		createSkills(moshi);
		createDlcPersonae(moshi);
		createSpecialCombos(moshi);
	}

	public static void createDlcPersonae(Moshi moshi) throws Exception {
		JsonAdapter<List<List<String>>> adapter = moshi.adapter(Types.newParameterizedType(List.class,
				Types.newParameterizedType(List.class, String.class)));

		List<List<String>> serieses =
				adapter.fromJson(Okio.buffer(Okio.source(new File("data/proto/json/dlcPersonae.json"))));

		Data.DLCPersonae.Builder builder = Data.DLCPersonae.newBuilder();

		for (List<String> series : serieses) {
			builder.addList(Data.DLCPersonaSeries.newBuilder().addAllSeries(series));
		}

		Files.write(Paths.get("data/proto/out/dlcPersonae.bin"), builder.build().toByteArray());

		Data.DLCPersonae out =
				Data.DLCPersonae.parseFrom(Files.readAllBytes(Paths.get("data/proto/out/dlcPersonae.bin")));

		for (Data.DLCPersonaSeries combo : out.getListList()) {
			System.out.println(combo.getSeriesList());
		}
	}

	public static void createSpecialCombos(Moshi moshi) throws Exception {

		JsonAdapter<List<SpecialCombo>> adapter =
				moshi.adapter(Types.newParameterizedType(List.class, SpecialCombo.class));

		List<SpecialCombo> combos =
				adapter.fromJson(Okio.buffer(Okio.source(new File("data/proto/json/specialCombos.json"))));

		Data.SpecialCombos.Builder builder = Data.SpecialCombos.newBuilder();

		for (SpecialCombo combo : combos) {
			builder.addList(
					Data.SpecialCombo.newBuilder().setResult(combo.result).addAllSources(combo.sources));
		}

		Files.write(Paths.get("data/proto/out/specialCombos.bin"), builder.build().toByteArray());

		Data.SpecialCombos out = Data.SpecialCombos.parseFrom(
				Files.readAllBytes(Paths.get("data/proto/out/specialCombos.bin")));

		for (Data.SpecialCombo combo : out.getListList()) {
			System.out.println("First: "
					+ combo.getSources(0)
					+ ", Second: "
					+ combo.getSources(1)
					+ ", Result: "
					+ combo.getResult());
		}
	}

	public static void createArcanaCombos(Moshi moshi) throws Exception {

		JsonAdapter<CombosJson> adapter = moshi.adapter(CombosJson.class);

		CombosJson json =
				adapter.fromJson(Okio.buffer(Okio.source(new File("data/proto/json/arcanaCombos.json"))));

		List<Data.Combo> combos = new ArrayList<>(json.data.length);

		for (Combo combo : json.data) {
			combos.add(Data.Combo.newBuilder()
					.addAllSources(Arrays.asList(combo.source))
					.setArcana(combo.result)
					.build());
		}

		byte[] bytes = Data.Combos.newBuilder().addAllList(combos).build().toByteArray();

		Files.write(Paths.get("data/proto/out/combos.bin"), bytes);

		Data.Combos out =
				Data.Combos.parseFrom(Files.readAllBytes(Paths.get("data/proto/out/combos.bin")));

		for (Data.Combo combo : out.getListList()) {
			System.out.println("First: "
					+ combo.getSources(0)
					+ ", Second: "
					+ combo.getSources(1)
					+ ", Arcana: "
					+ combo.getArcana());
		}
	}

	public static void createSkills(Moshi moshi) throws Exception {

		ParameterizedType type = Types.newParameterizedType(Map.class, String.class, SkillDetail.class);

		JsonAdapter<Map<String, SkillDetail>> adapter = moshi.adapter(type);

		Source source = Okio.source(new File("data/proto/json/skills.json"));
		Map<String, SkillDetail> skills = adapter.fromJson(Okio.buffer(source));

		Data.Skills.Builder builder = Data.Skills.newBuilder();

		for (String key : skills.keySet()) {
			SkillDetail detail = skills.get(key);
			builder.putSkills(key, Data.SkillDetail.newBuilder()
					.setEffect(detail.effect)
					.setElement(detail.element)
					.putAllPersona(detail.personas)
					.build());
		}

		Files.write(Paths.get("data/proto/out/skills.bin"), builder.build().toByteArray());

		Data.Skills out =
				Data.Skills.parseFrom(Files.readAllBytes(Paths.get("data/proto/out/skills.bin")));

		System.out.println("Rare combos count: " + out.getSkillsMap().toString());
	}

	public static void createRareCombos(Moshi moshi) throws Exception {

		ParameterizedType type = Types.newParameterizedType(Map.class, String.class,
				Types.newParameterizedType(List.class, Integer.class));

		JsonAdapter<Map<String, List<Integer>>> adapter = moshi.adapter(type);

		Source source = Okio.source(new File("data/proto/json/rareCombos.json"));
		Map<String, List<Integer>> rareCombos = adapter.fromJson(Okio.buffer(source));

		Data.RareCombos.Builder builder = Data.RareCombos.newBuilder();

		for (String key : rareCombos.keySet()) {
			builder.putCombos(key, Data.RareCombo.newBuilder().addAllCombos(rareCombos.get(key)).build());
		}

		Files.write(Paths.get("data/proto/out/rareCombos.bin"), builder.build().toByteArray());

		Data.RareCombos out =
				Data.RareCombos.parseFrom(Files.readAllBytes(Paths.get("data/proto/out/rareCombos.bin")));

		System.out.println("Rare combos count: " + out.getCombosCount());
	}

	public static void createPersonas(Moshi moshi) throws Exception {
		ParameterizedType type =
				Types.newParameterizedType(Map.class, String.class, PersonaDetail.class);

		JsonAdapter<Map<String, PersonaDetail>> adapter = moshi.adapter(type);

		Source source = Okio.source(new File("data/proto/json/personae.json"));

		Map<String, PersonaDetail> personae = adapter.fromJson(Okio.buffer(source));

		Data.Personae.Builder builder = Data.Personae.newBuilder();

		for (String key : personae.keySet()) {
			PersonaDetail detail = personae.get(key);
			builder.putPersonae(key, Data.PersonaDetail.newBuilder()
					.setArcana(detail.arcana)
					.setLevel(detail.level)
					.addAllStats(Arrays.asList(detail.stats))
					.putAllSkills(detail.skills)
					.build());
		}

		Files.write(Paths.get("data/proto/out/personae.bin"), builder.build().toByteArray());

		Data.Personae out =
				Data.Personae.parseFrom(Files.readAllBytes(Paths.get("data/proto/out/personae.bin")));

		System.out.println(out.getPersonaeMap().toString());
	}



}
