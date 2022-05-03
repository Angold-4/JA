package hk.example.projectapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonHandler {
    private static final String TAG = "JsonHandlerThread";

    public static String readJsonFile(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void createArray(String input){
        //通过网络获取内容
        String schoolStr = input; //返回String
        Log.e(TAG, "Get the school info: " + schoolStr);

        if (schoolStr != null) {
            try {
                JSONArray schools = new JSONArray(schoolStr);

                // looping through All Contacts
                for (int count = 1; count < schools.length(); count=count+1) {

                    //在提取JSON array中的 JSONObject
                    JSONObject sch = schools.getJSONObject(count);

                    String a = sch.getString("A");
                    String b = sch.getString("B");
                    String c = sch.getString("C");
                    String d = sch.getString("D");
                    String e = sch.getString("E");
                    String f = sch.getString("F");
                    String g = sch.getString("G");
                    String h = sch.getString("H");
                    String i = sch.getString("I");
                    String j = sch.getString("J");
                    String k = sch.getString("K");
                    String l = sch.getString("L");
                    String m = sch.getString("M");
                    String n = sch.getString("N");
                    String o = sch.getString("O");
                    String p = sch.getString("P");
                    String q = sch.getString("Q");
                    String r = sch.getString("R");
                    String s = sch.getString("S");
                    String t = sch.getString("T");
                    String u = sch.getString("U");
                    String v = sch.getString("V");
                    String w = sch.getString("W");
                    String x = sch.getString("X");
                    String y = sch.getString("Y");
                    String z = sch.getString("Z");
                    String aa = sch.getString("AA");
                    String ab = sch.getString("AB");
                    String ac = sch.getString("AC");
                    String ad = sch.getString("AD");
                    String ae= sch.getString("AE");
                    String af = sch.getString("AF");
                    String ag = sch.getString("AG");


                    // Add contact (name, email, address) to contact list
                    School.addSchool(a, b, c, d, e, f,  g, h, i, j, k, l, m,  n, o, p, q, r, s, t,
                            u, v, w, x, y, z, aa, ab, ac, ad, ae, af, ag);

                    //For test
                    System.out.println("Good");
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from file.");
        }
    }
}