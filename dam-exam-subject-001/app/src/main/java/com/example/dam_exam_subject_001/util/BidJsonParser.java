package com.example.dam_exam_subject_001.util;

import com.example.dam_exam_subject_001.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BidJsonParser {

    private static final String BID_HOUSE = "bid_house";
    private static final String BIDS = "bids";
    private static final String BID_NAME = "name";
    private static final String BID_STATE = "state";
    private static final String BID_OBJECT = "object";
    private static final String BID_DATE = "date";
    private static final String BID_TIME = "time";
    private static final String BID_REGISTERED = "registered";
    private static final String BID_BID = "bid";

    public static List<Bid> fromJson(String json) {
        try {
            JSONObject bid_house = new JSONObject(json);
            JSONObject obj = bid_house.getJSONObject(BID_HOUSE);
            JSONArray array = obj.getJSONArray(BIDS);
            return readArray(array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Bid> readArray(JSONArray array) throws JSONException {
        List<Bid> bids = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Bid bid = readObject(array.getJSONObject(i));
            if (bid != null) {
                bids.add(bid);
            }
        }
        return bids;
    }

    private static Bid readObject(JSONObject object) throws JSONException {
        String name = object.getString(BID_NAME);
        State state = object.getInt(BID_STATE) == 1 ? State.IN_PROGRESS : State.CLOSED;
        int obj = object.getInt(BID_OBJECT);
        String strObj = null;
        if (obj == 1) strObj = "object1";
        if (obj == 2) strObj = "object2";
        if (obj == 3) strObj = "object3";

        Date date = Bid.fromString(object.getString(BID_DATE));
        String time = object.getString(BID_TIME);
        boolean registered = object.getBoolean(BID_REGISTERED);
        int bid = object.getInt(BID_BID);

        return new Bid(name, state, strObj, date, time, registered, bid);
    }


}
