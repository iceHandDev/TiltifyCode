package me.tiltifyapi.tiltify;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.bukkit.plugin.java.JavaPlugin;

public class Tiltify extends JavaPlugin {
    JSONArray donationCache;
    JSONArray donationWorkdown;


    @Override
    public void onEnable() {
        // Plugin startup logic



        Unirest.config().enableCookieManagement(false);

        getServer().getScheduler().runTaskTimer(this, (Runnable) () -> {

                Unirest.get("https://tiltify.com/api/v3/campaigns/118180/donations?count=50")
                        .header("Authorization","Bearer ")
                        .asJsonAsync(new Callback<JsonNode>() {
                            @Override
                            public void completed(HttpResponse<JsonNode> response) {
                                int i,x;

                                JSONObject jsonObject = response.getBody().getObject();

                                JSONArray responseJSONArray = jsonObject.getJSONArray("data");

                                for (i=0;i<responseJSONArray.length();i++) {
                                    boolean matchFound = false;
                                    JSONObject compareObj = responseJSONArray.getJSONObject(i);
                                    getServer().broadcastMessage(String.valueOf(i));
                                    //the loop stops as it reaches the line below, not even printing it to the console. I believe it is due to the reference of the variable donationCache but it should be acessable from this scope!
                                    getServer().broadcastMessage(String.valueOf(donationCache.length()));
                                    /*
                                    for (x=0;x<donationCache.length();x++) {
                                        getServer().broadcastMessage("TEST1");
                                        if (donationCache.getInt(x) == compareObj.getInt("id")) {
                                            //an id that was already used has been found, delete it from the response array
                                            matchFound = true;
                                            getServer().broadcastMessage("A Match Was Found");
                                            //responseJSONArray.remove(i);
                                        }
                                    }



                                    if (!matchFound || donationCache.length() == 0) {
                                        //if a match was not found, it is safe to now add this ID to the cache array and the workDown array
                                        donationCache.put(compareObj.getInt("id"));
                                        donationWorkdown.put(compareObj.getFloat("amount"));
                                        getServer().broadcastMessage("Match was added to donationCache and donationWorkdown");
                                    }
                                    */

                                }

                            }
                        });
        }, 0, 20 * 3);
        /*
        getServer().getScheduler().runTaskTimer(this, (Runnable) () -> {

            if (donationWorkdown.length() > 0) {
                getServer().broadcastMessage("donationCache is greater than 0");
                float donationVal = donationWorkdown.getFloat(0);
                if ((donationVal % 1 == 0.01) || donationVal == 1337) {
                        //trigger something
                    getServer().broadcastMessage(String.valueOf(donationVal));
                }
            }

        }, 0,20);
        */
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
