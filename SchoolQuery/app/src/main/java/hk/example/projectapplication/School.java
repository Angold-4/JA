package hk.example.projectapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//*************  一定要看  ************
// school 只有一个属性，就是 schoollist
// schoolList的结构: HashMap<String, String>, HashMap<String, String>, HashMap<String, String>,HashMap<String, String>,HashMap<String, String>
// HashMap的结构:SCHOOL_NO(属性名称),school_no(具体的数值), ENGLISH_NAME,english_name, ENGLISH_ADDRESS,english_address

public class School {

    public static ArrayList<HashMap<String, String>> schoolList = new ArrayList<>();

    public static String SCHOOL_NO  = "School_no";
    public static String ENGLISH_CATEGORY ="English_category";
    public static String ENGLISH_NAME ="English_name";
    public static String ENGLISH_ADDRESS ="English_address";
    public static String LONGITUDE ="Longitude";
    public static String LATITUDE ="Latitude";
    public static String EASTING ="Easting";
    public static String NORTHING ="Northing";
    public static String STUDENTS_GENDER ="Students_gender";
    public static String SESSION ="Session";
    public static String DISTRICT ="District";
    public static String FINANCE_TYPE ="Finance_type";
    public static String SCHOOL_LEVEL ="School_level";
    public static String TELEPHONE ="Telephone";
    public static String FAX_NUMBER ="Fax_number";
    public static String WEBSITE ="Website";
    public static String RELIGION ="Religion";


    public static String cn_ENGLISH_CATEGORY = "中文類別";
    public static String cn_ENGLISH_NAME ="中文名稱";
    public static String cn_ENGLISH_ADDRESS ="中文地址";
    public static String cn_LONGITUDE ="經度";
    public static String cn_LATITUDE ="緯度";
    public static String cn_EASTING ="坐標東";
    public static String cn_NORTHING ="坐標北";
    public static String cn_STUDENTS_GENDER ="就讀學生性別";
    public static String cn_SESSION ="學校授課時間";
    public static String cn_DISTRICT ="分區";
    public static String cn_FINANCE_TYPE ="資助種類";
    public static String cn_SCHOOL_LEVEL ="學校類型";
    public static String cn_TELEPHONE ="聯絡電話";
    public static String cn_FAX_NUMBER ="傳真號碼";
    public static String cn_WEBSITE ="網頁";
    public static String cn_RELIGION ="宗教";


    // Creates and add contact to contact list
    public static void addSchool(String a, String b, String c,String d, String e,String f, String g,
                                 String h, String i, String j,String k, String l,String m, String n,
                                 String o, String p, String q,String r, String s,String t,
                                 String u, String v, String w, String x,String y, String z,
                                 String aa, String ab, String ac, String ad, String ae,String af,
                                 String ag) {
        // Create contact
        HashMap<String, String> school = new HashMap<>();
        school.put(SCHOOL_NO, a);
        school.put(ENGLISH_CATEGORY,b);
        school.put(ENGLISH_NAME, d);
        school.put(ENGLISH_ADDRESS,f);
        school.put(LONGITUDE,h);
        school.put(LATITUDE, j);
        school.put(EASTING,  l);
        school.put(NORTHING, n);
        school.put(STUDENTS_GENDER, p);
        school.put(SESSION,r);
        school.put(DISTRICT,t);
        school.put(FINANCE_TYPE, v);
        school.put(SCHOOL_LEVEL,x);
        school.put(TELEPHONE, z);
        school.put(FAX_NUMBER, ab);
        school.put(WEBSITE, ad);
        school.put(RELIGION ,af);


        school.put(cn_ENGLISH_CATEGORY,c);
        school.put(cn_ENGLISH_NAME, e);
        school.put(cn_ENGLISH_ADDRESS,g);
        school.put(cn_LONGITUDE,i);
        school.put(cn_LATITUDE, k);
        school.put(cn_EASTING,  m);
        school.put(cn_NORTHING, o);
        school.put(cn_STUDENTS_GENDER, q);
        school.put(cn_SESSION,s);
        school.put(cn_DISTRICT,u);
        school.put(cn_FINANCE_TYPE, w);
        school.put(cn_SCHOOL_LEVEL,y);
        school.put(cn_TELEPHONE, aa);
        school.put(cn_FAX_NUMBER, ac);
        school.put(cn_WEBSITE, ae);
        school.put(cn_RELIGION ,ag);

        // Add contact to contact list
        schoolList.add(school);

    }
}
