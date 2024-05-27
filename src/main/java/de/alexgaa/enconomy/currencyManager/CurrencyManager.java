package de.alexgaa.enconomy.currencyManager;

import java.util.HashMap;
import java.util.UUID;

public class CurrencyManager {
    //TODO: add null check for currencyMap before using methods

    static HashMap<UUID, Float> currencyMap;

    /**
     * adds a new entry in the currencyMap
     * 
     * @param playerID
     * @param moneyAmount
     */
    public static void addEntry(UUID playerID, float moneyAmount) {
        if (currencyMap == null || playerID == null) {
            return;
        }
        currencyMap.put(playerID, moneyAmount);
    }

    /**
     * sets the money of the given UUID to the given amount if an entry is existing
     * 
     * @param playerID
     * @param amount
     */
    public static void setMoneyAmount(UUID playerID, float amount) {
        if (playerID == null || !currencyMap.containsKey(playerID)) {
            return;
        }
        currencyMap.put(playerID, amount);
    }

    public static float getMoneyAmount(UUID playerID) {
        if (playerID == null) {
            return 0F;
        }
        if (currencyMap.containsKey(playerID)) {
            return currencyMap.get(playerID);
        }
        return 0F;
    }

    public static void addMoneyAmount(UUID playerID, float amount) {
        if (playerID == null || !currencyMap.containsKey(playerID)) {
            return;
        }
        float currentMoney = currencyMap.get(playerID);
        float newMoneyAmount = currentMoney + amount;
        currencyMap.put(playerID, newMoneyAmount);
    }

    public static void reduceMoneyAmount(UUID playerID, float amount) {
        if (playerID == null || !currencyMap.containsKey(playerID)) {
            return;
        }
        float currentMoney = currencyMap.get(playerID);
        float newMoneyAmount = currentMoney - amount;
        currencyMap.put(playerID, newMoneyAmount);
    }

    public static boolean ableToBuy(UUID playerID, float amountToBuy) {
        if (playerID == null || !currencyMap.containsKey(playerID)) {
            return false;
        }
        float money = currencyMap.get(playerID);
        if (money >= amountToBuy) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the player has enough money compared to the
     * amountToBuy and will reduce the amount if possible to buy
     * @param playerID
     * @param amountToBuy
     * @return
     */
    public static boolean purchaseIfPossible(UUID playerID, float amountToBuy) {
        if (ableToBuy(playerID, amountToBuy)) {
            reduceMoneyAmount(playerID, amountToBuy);
            return true;
        } else {
            return false;
        }

    }
}
