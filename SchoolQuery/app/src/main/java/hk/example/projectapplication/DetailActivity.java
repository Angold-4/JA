package hk.example.projectapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    private TextView schoolEnglishName;
    private TextView schoolChineseName;
    private TextView schoolNO;
    private TextView schoolCategory;
    private TextView schoolAddress;
    private TextView studentsGender;
    private TextView schoolDistrict;
    private TextView schoolSchedule;
    private TextView financeType;
    private TextView schoolPhone;
    private TextView schoolFaxNumber;
    private TextView schoolWebsite;
    private TextView schoolReligion;

    private String schoolEnName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        schoolEnglishName = findViewById(R.id.schoolEnName);
        schoolChineseName = findViewById(R.id.schoolCnName);
        schoolNO = findViewById(R.id.schoolNO);
        schoolCategory = findViewById(R.id.schoolCategory);
        schoolAddress = findViewById(R.id.schoolAddress);
        studentsGender = findViewById(R.id.studentsGender);
        schoolDistrict = findViewById(R.id.schoolDistrict);
        schoolSchedule = findViewById(R.id.schoolSchedule);
        financeType = findViewById(R.id.financeType);
        schoolPhone = findViewById(R.id.schoolPhone);
        schoolFaxNumber = findViewById(R.id.schoolFaxNumber);
        schoolWebsite = findViewById(R.id.schoolWebsite);
        schoolReligion = findViewById(R.id.schoolReligon);

        Intent intent = getIntent();
        String schoolNo = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //通过schoolNo查找school
        HashMap<String, String> school = null;
        for (HashMap<String, String> item : School.schoolList) {
            if (item.get(School.SCHOOL_NO).equals(schoolNo)) {
                school = item;
                break;
            }
        }

        //加载学校信息
        dataLoad(school);
    }

    private void dataLoad( HashMap<String,String> school ){
        if(school != null){
            //English
            if(MainActivity.language=="en"){
                schoolEnglishName.setText(school.get(School.ENGLISH_NAME));
                schoolChineseName.setText(school.get(School.cn_ENGLISH_NAME));
                schoolNO.setText(school.get(School.SCHOOL_NO));
                schoolCategory.setText(school.get(School.ENGLISH_CATEGORY));
                schoolAddress.setText(school.get(School.ENGLISH_ADDRESS));
                studentsGender.setText(school.get(School.STUDENTS_GENDER));
                schoolDistrict.setText(school.get(School.DISTRICT));
                schoolSchedule.setText(school.get(School.SESSION));
                financeType.setText(school.get(School.FINANCE_TYPE));
                schoolPhone.setText(school.get(School.TELEPHONE));
                schoolFaxNumber.setText(school.get(School.FAX_NUMBER));
                schoolWebsite.setText(school.get(School.WEBSITE));
                schoolReligion.setText(school.get(School.RELIGION));
                schoolEnName = school.get(School.ENGLISH_NAME);
            }
            //Chinese
            if(MainActivity.language=="zh"){
                schoolEnglishName.setText(school.get(School.ENGLISH_NAME));
                schoolChineseName.setText(school.get(School.cn_ENGLISH_NAME));
                schoolNO.setText(school.get(School.SCHOOL_NO));
                schoolCategory.setText(school.get(School.cn_ENGLISH_CATEGORY));
                schoolAddress.setText(school.get(School.cn_ENGLISH_ADDRESS));
                studentsGender.setText(school.get(School.cn_STUDENTS_GENDER));
                schoolDistrict.setText(school.get(School.cn_DISTRICT));
                schoolSchedule.setText(school.get(School.cn_SESSION));
                financeType.setText(school.get(School.cn_FINANCE_TYPE));
                schoolPhone.setText(school.get(School.cn_TELEPHONE));
                schoolFaxNumber.setText(school.get(School.cn_FAX_NUMBER));
                schoolWebsite.setText(school.get(School.cn_WEBSITE));
                schoolReligion.setText(school.get(School.cn_RELIGION));
                schoolEnName = school.get(School.cn_ENGLISH_NAME);
            }
        }
    }


    //专挑到地图APP，显示对应学校
    public void buttonClicked(View v) {
        if (v.getId() == R.id.mapicon) {
            showMap();
        }
    }
    public void showMap() {
        String url = "https://www.google.com/maps/search/" + schoolEnName;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}

