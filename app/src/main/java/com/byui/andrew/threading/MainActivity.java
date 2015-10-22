package com.byui.andrew.threading;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class MainActivity extends AppCompatActivity {

    FileOutputStream outputStream;
    ArrayList numList;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void create(View v) {
        try {
            outputStream = openFileOutput("numbers.txt", Context.MODE_PRIVATE);
            for (int i = 0; i <= 10; i++) {
                outputStream.write(i);
            }
            outputStream.close();
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
                String line;
                while ((line = bReader.readLine()) != null) {
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
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                numList);

        lv.setAdapter(arrayAdapter);
    }


}



