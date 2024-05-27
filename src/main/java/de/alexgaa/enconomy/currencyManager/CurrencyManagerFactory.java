package de.alexgaa.enconomy.currencyManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyManagerFactory {

    public void initializeCurrencyMap() {
        if (CurrencyManager.currencyMap == null) {
            CurrencyManager.currencyMap = new HashMap<UUID, Float>();
        }
    }

    public void loadCurrencyMapFromFile(String jsonFilePath) {
        if (jsonFilePath == null) {
            return;
        }
        File jsonFile = new File(jsonFilePath);

        if (!jsonFile.exists() || jsonFile.isDirectory()) {
            return;
        }
        try {
            JsonParser parser = new JsonParser();

            FileReader reader = new FileReader(jsonFile);
            // Parse & convert object to JSON Array
            JsonArray jsonArray = (JsonArray) parser.parse(reader);
            initializeCurrencyMap();
            // Iterate over JSON array
            for (Object o : jsonArray) {
                JsonObject jsonObject = (JsonObject) o;

                // Get values from JSON object
                UUID playerUUID = UUID.fromString(jsonObject.get("player_uuid").getAsString());
                float money = jsonObject.get("money").getAsFloat();
                CurrencyManager.currencyMap.put(playerUUID, money);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveCurrencyMapToFile(String jsonFilePath) {
        if (CurrencyManager.currencyMap == null) {
            return;
        }
        if (jsonFilePath == null) {
            return;
        }
        // map not empty
        if (CurrencyManager.currencyMap.size() > 0) {
            JsonArray array = new JsonArray();
            for (HashMap.Entry<UUID, Float> entry : CurrencyManager.currencyMap.entrySet()) {
                JsonObject object = new JsonObject();
                object.addProperty("money", entry.getValue());
                object.addProperty("player_uuid", entry.getKey().toString());
                array.add(object);
            }
            try {
                FileWriter writer = new FileWriter(jsonFilePath);
                writer.write(array.toString());
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
