package test;

import java.util.*;
import java.net.*;
import java.io.*;
import org.json.*;

public class results
{
    int id;
    String name;
    String imgLnk;
    String type;
    String desc;
    String getURL;
    ArrayList<provider> listOfP;
    String imgURL;
    String Key;
    
    public results() {
        this.listOfP = new ArrayList<provider>();
        this.imgURL = "https://image.tmdb.org/t/p/w500";
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int i) {
        this.id = i;
    }
    
    public String getNmae() {
        return this.name;
    }
    
    public void setName(final String n) {
        this.name = n;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String t) {
        this.type = t;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String d) {
        this.desc = d;
    }
    
    public String getImgLink() {
        return this.imgLnk;
    }
    
    public void setImgLnk(final String l) {
        this.imgLnk = l;
    }
    
    public void displayTest() {
        System.out.print("the test worked so far...");
    }
    
    public void printObject() {
        System.out.println("ID = " + this.id);
        System.out.println("Name = " + this.name);
        System.out.println("Type = " + this.type);
        System.out.println("Desc = " + this.desc);
        System.out.println("ImgLoaction = " + this.imgLnk);
        for (int i = 0; i < this.listOfP.size(); ++i) {
            this.listOfP.get(i).display();
        }
    }
    
    public void getProvider() throws IOException, JSONException {
        if (this.type.equals("Movie")) {
            this.getURL = "https://api.themoviedb.org/3/movie/" + this.id + "/watch/providers?api_key=<<api_key>>";
        }
        else if (this.type.equals("TV")) {
            this.getURL = "https://api.themoviedb.org/3/tv/" + this.id + "/watch/providers?api_key=<<api_key>>";
        }
        final URL obj = new URL(this.getURL);
        final HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        final StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        final JSONObject provider = new JSONObject(response.toString());
        final JSONObject providerR = new JSONObject(provider.getJSONObject("results").toString());
        if (providerR.toString().equals("{}")) {
            final int id = 0;
            final String name = "No media found";
            final String logo = "sorry";
            final provider p = new provider(id, name, logo);
            this.listOfP.add(p);
        }
        else if (!providerR.has("US")) {
            final int id = 0;
            final String name = "Not available for streaming in the US";
            final String logo = "sorry";
            final provider p = new provider(id, name, logo);
            this.listOfP.add(p);
        }
        else {
            final JSONObject US = new JSONObject(providerR.getJSONObject("US").toString());
            if (!US.has("flatrate")) {
                final int id2 = 0;
                final String name2 = "Not available for streaming";
                final String logo2 = "sorry";
                final provider p2 = new provider(id2, name2, logo2);
                this.listOfP.add(p2);
            }
            else {
                final JSONArray providerInfo = new JSONArray(US.getJSONArray("flatrate").toString());
                for (int i = 0; i < providerInfo.length(); ++i) {
                    final JSONObject array = new JSONObject(providerInfo.get(i).toString());
                    final int id3 = array.getInt("provider_id");
                    final String name3 = array.getString("provider_name");
                    final String logo3 = String.valueOf(this.imgURL) + array.getString("logo_path");
                    final provider p3 = new provider(id3, name3, logo3);
                    this.listOfP.add(p3);
                }
            }
        }
    }
}
