package dev.inkwell.hermes;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Hermes {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().normalize().resolve("hermes.json");

    public static Collection<ModContainer> getViewedMods() {
        Collection<ModContainer> viewedMods = new LinkedHashSet<>();

        try (Reader reader = Files.newBufferedReader(FILE)) {
            for (JsonElement element : new JsonParser().parse(reader).getAsJsonArray()) {
                String modId = element.getAsString();
                Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(modId);

                modContainer.ifPresent(viewedMods::add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return viewedMods;
    }

    public static List<ModContainer> getMods() {
        List<ModContainer> mods = new ArrayList<>();

        for (ModContainer modContainer : FabricLoader.getInstance().getAllMods()) {
            if (modContainer.getMetadata().containsCustomValue("hermes")) {
                mods.add(modContainer);
            }
        }

        return mods;
    }

    public static void save(Collection<ModContainer> viewedMods) {
        JsonArray array = new JsonArray();

        for (ModContainer container : viewedMods) {
            array.add(container.getMetadata().getId());
        }

        try {
            Files.write(FILE, GSON.toJson(array).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
