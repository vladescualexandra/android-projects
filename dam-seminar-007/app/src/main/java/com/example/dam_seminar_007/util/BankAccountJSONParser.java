package com.example.dam_seminar_007.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankAccountJSONParser {

    public static final String CARD_HOLDER_NAME = "cardHolderName";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String EXPIRATION_MONTH = "expirationMonth";
    public static final String EXPIRATION_YEAR = "expirationYear";
    public static final String BANK_NAME = "bankName";

    public static List<BankAccount> fromJson(String json){
        if(json == null || json.isEmpty()){
            return new ArrayList<>();
        }

        try {
            JSONArray array = new JSONArray(json);
            return getBankAccountListFromJson(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<BankAccount> getBankAccountListFromJson(JSONArray array) throws JSONException {
        List<BankAccount> results = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            BankAccount account = getBankAccountFromJson(object);
            results.add(account);

        }
        return results;
    }

    private static BankAccount getBankAccountFromJson(JSONObject object) throws JSONException {
        String cardHolderName = object.getString(CARD_HOLDER_NAME);
        long cardNumber = object.getLong(CARD_NUMBER);
        int expirationMonth = object.getInt(EXPIRATION_MONTH);
        int expirationYear = object.getInt(EXPIRATION_YEAR);
        String bankName = object.getString(BANK_NAME);

        return new BankAccount(cardHolderName, cardNumber, expirationMonth, expirationYear, bankName);
    }

}
