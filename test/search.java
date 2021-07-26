package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class search {
	//Creates a list of each individual results
	public static ArrayList<results> listOfresults = new ArrayList<results>();
	public static String searchUrl = "https://api.themoviedb.org/3/search/multi?api_key=<<api_key>>&language=en-US&query=\"%s\"&page=1";
	public static String imgURL = "https://image.tmdb.org/t/p/w500";
	public static String noImg = "https://bit.ly/3r3A5cS";
	public static results r;
	
	//Main to test with search word
	public static void main(String[] args) throws Exception {
		getResults("die hard");
		printEverything();
	}
	public static void clear(){
            listOfresults.clear();
        }
	//Gets Results
	public static void getResults(String s) throws IOException, JSONException {
		//talks to api and gets info from the api
		 s = s.replace(' ', '+');
		 URL obj = new URL(String.format(searchUrl, s));
		 System.out.println(String.format(searchUrl, s));
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	     con.setRequestProperty("User-Agent", "Mozilla/5.0");
	     BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	response.append(inputLine);
	     }
	     in.close();
	     //gets basic info like number of pages and total of results
	     JSONObject myResponse = new JSONObject(response.toString());
	     System.out.println("page- "+myResponse.getInt("page"));
	     System.out.println("total_results- "+myResponse.getInt("total_results"));
	     int totalResults = myResponse.getInt("total_results");
	     int totalPages =  myResponse.getInt("total_pages");
	     System.out.println("total_results- "+totalPages);
	     
	     //gets the ID and type of each result
	     JSONArray results = new JSONArray(myResponse.getJSONArray("results").toString());
	     //this is the loop that get any number of results just change the '3' to any number of results you want
	     if(totalResults > 10) {
	    	 for(int i = 0; i < 20; i++) {
		    	 JSONObject array = new JSONObject(results.get(i).toString());
		    	 //checks if result is movie or show
		    	 if(array.getString("media_type").equals("movie")) {
		    		 getMovieInfo(array.getInt("id"));
		    	 }
		    	 else if(array.getString("media_type").equals("tv")) {
		    		 getShowInfo(array.getInt("id"));
		    	 }
		     }
	     }
	     else {
	     for(int i = 0; i < totalResults; i++) {
	    	 JSONObject array = new JSONObject(results.get(i).toString());
	    	 //checks if result is movie or show
	    	 if(array.getString("media_type").equals("movie")) {
	    		 getMovieInfo(array.getInt("id"));
	    	 }
	    	 else if(array.getString("media_type").equals("tv")) {
	    		 getShowInfo(array.getInt("id"));
	    	 }
	     }
	     }
	     
	}
	//if the its a movie it uses the movie query to get all the info we need.
	public static void getMovieInfo(int ID) throws IOException, JSONException {
		String movieURL = "https://api.themoviedb.org/3/movie/"+ID+"?api_key=<<api_key>>&language=en-US";
		URL obj = new URL(movieURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	    con.setRequestProperty("User-Agent", "Mozilla/5.0");
	    BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	response.append(inputLine);
	     }
	     in.close();
	     
	     JSONObject movieR = new JSONObject(response.toString());
	     r = new results();
	     r.setId(ID);
	     r.setType("Movie");
	     r.setName(movieR.getString("title"));
	     r.setDesc(movieR.getString("overview"));
	     if(movieR.isNull("backdrop_path")) {
	    	 r.setImgLnk(noImg);
	     }
	     else
	     r.setImgLnk(imgURL+movieR.getString("backdrop_path"));
	     r.getProvider();
	     listOfresults.add(r);
	}
	//if the its a show it uses the show query to get all the info we need.
	public static void getShowInfo(int ID) throws IOException, JSONException {
		String showURL = "https://api.themoviedb.org/3/tv/"+ID+"?api_key=<<api_key>>&language=en-US";
		URL obj = new URL(showURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	    con.setRequestProperty("User-Agent", "Mozilla/5.0");
	    BufferedReader in = new BufferedReader(
	             new InputStreamReader(con.getInputStream()));
	     String inputLine;
	     StringBuffer response = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	response.append(inputLine);
	     }
	     in.close();
	     
	     JSONObject showR = new JSONObject(response.toString());
	     
	     r = new results();
	     r.setId(ID);
	     r.setType("TV");
	     r.setName(showR.getString("original_name"));
	     r.setDesc(showR.getString("overview"));
	     if(showR.isNull("backdrop_path")) {
	    	 r.setImgLnk(noImg);
	     }
	     else
	     r.setImgLnk(imgURL+showR.getString("backdrop_path"));
	     r.getProvider();
	     listOfresults.add(r);
	}
	
	//prints everything in the console 
	public static void printEverything() {
		for(int i = 0; i < listOfresults.size(); i++) {
			listOfresults.get(i).printObject();
			System.out.println("~~~~~~~~~~~~~~~~~~~");
		}
	}
	
}