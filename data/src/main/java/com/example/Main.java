package com.example;

import com.daykm.p5executioner.data.Combo;
import com.daykm.p5executioner.data.CombosJson;
import com.daykm.p5executioner.data.Data;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okio.Okio;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		Moshi moshi = new Moshi.Builder().build();

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
}
