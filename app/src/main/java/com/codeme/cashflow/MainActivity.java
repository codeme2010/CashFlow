package com.codeme.cashflow;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Fragment> fragmentList = null;
    ArrayList<String> titleList = null;
    SectionsPagerAdapter spa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        fragmentList = new ArrayList<>();
        fragmentList.add(new fragment1());
        fragmentList.add(new fragment2());
        fragmentList.add(new fragment3());
        fragmentList.add(new fragment4());

        titleList = new ArrayList<>();
        titleList.add("记账");
        titleList.add("回款明细");
        titleList.add("月报表");
        titleList.add("账户信息");

        spa = new SectionsPagerAdapter(getSupportFragmentManager(),fragmentList, titleList);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(spa);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        openDatabase();
    }

    // 复制小于1M的数据库程序
    private void openDatabase() {
//        app.DATABASE_PATH=MainActivity.this.getFilesDir().toString();
        try {
            File dir = new File(App.DATABASE_PATH);
            if (!dir.exists())
                dir.mkdir();
            if (!(new File(App.databaseFilename)).exists()) {
                // 获得封装dictionary.db文件的InputStream对象
                InputStream is = getResources().openRawResource(R.raw.cashflow);
                FileOutputStream fos = new FileOutputStream(App.databaseFilename);
                byte[] buffer = new byte[7168];
                int count = 0;
                // 开始复制dictionary.db文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                (Toast.makeText(this, "数据库创建成功",Toast.LENGTH_LONG)).show();
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            (Toast.makeText(this, "数据库创建错误：" + e.getMessage(),
                    Toast.LENGTH_LONG)).show();
        }
    }
}
