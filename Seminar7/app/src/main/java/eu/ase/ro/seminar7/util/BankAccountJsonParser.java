package eu.ase.ro.seminar7.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BankAccountJsonParser {

    public static final String BANK_NAME = "bankName";
    public static final String CARD_HOLDER_NAME = "cardHolderName";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String EXPIRATION_MONTH = "expirationMonth";
    public static final String EXPIRATION_YEAR = "expirationYear";

    public static List<BankAccount> fromJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            return readBankAccounts(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<BankAccount> readBankAccounts(JSONArray array) throws JSONException {
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            BankAccount bankAccount = readBankAccount(array.getJSONObject(i));
            bankAccounts.add(bankAccount);
        }
        return bankAccounts;
    }

    private static BankAccount readBankAccount(JSONObject object) throws JSONException {
        String bankName = object.getString(BANK_NAME);
        String cardHolderName = object.getString(CARD_HOLDER_NAME);
        long cardNumber = object.getLong(CARD_NUMBER);
        int expirationMonth = object.getInt(EXPIRATION_MONTH);
        int expirationYear = object.getInt(EXPIRATION_YEAR);
        return new BankAccount(cardHolderName, cardNumber, expirationMonth, expirationYear, bankName);
    }
}
