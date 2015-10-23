package com.byui.andrew.threading;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FileOutputStream fOut;
    ArrayList<String> numList = new ArrayList<>();
    private ListView lv;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v){
        switch(v.getId()) {
            case R.id.create:
                create(v);
                break;
            case R.id.load:
                load(v);
                break;
            case R.id.clear:
                clear(v);
                break;
        }
    }

    public void create(View v) {
        try {
            fOut = openFileOutput("numbers.txt", Context.MODE_PRIVATE);
            for (int i = 1; i <= 10; i++) {
                String num = new String(Integer.toString(i) + "\n");
                System.out.println(num);
                fOut.write(num.getBytes());
            }
            fOut.close();
            Thread.sleep(250);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException s) {
            s.printStackTrace();
        }
    }

    public void load(View v) {
        lv = (ListView) findViewById(R.id.listView);
        try {
            FileInputStream fin = openFileInput("numbers.txt");
            if (fin != null) {
                InputStreamReader instream = new InputStreamReader(fin);
                BufferedReader bReader = new BufferedReader(instream);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                    System.out.println(line);
                    numList.add(line);
                }
            }
            Thread.sleep(250);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException s){
            s.printStackTrace();
        }
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                numList);

        lv.setAdapter(arrayAdapter);
    }

    public void clear(View v){
        numList.clear();
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();
    }

}



