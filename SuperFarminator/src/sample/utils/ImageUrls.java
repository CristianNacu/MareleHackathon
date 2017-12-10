package sample.utils;

import java.util.HashMap;

public class ImageUrls {
    public static HashMap<String,String> urls=init();
    private static HashMap<String ,String > init(){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("iarba","image/iarba.jpg");
        hashMap.put("porumb","image/porumb.jpg");
        hashMap.put("drum","image/drum.jpg");
        hashMap.put("struguri","image/struguri.jpg");
        return hashMap;
    }
    public static String getUrl(String type){
        return urls.get(type);
    }
}
