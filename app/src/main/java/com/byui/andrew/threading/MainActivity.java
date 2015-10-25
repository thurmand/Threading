package com.byui.andrew.threading;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public CreateLoadTask createLoadTask = new CreateLoadTask(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * buttonOnClick
     *
     * All the buttons are directed here and then the appropriate method is called
     *
     * @param v takes a View
     */
    public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.createLoad:
                createLoadTask.execute(this);
                break;
            case R.id.clear:
                createLoadTask.clear();
                break;
        }
    }
}


class CreateLoadTask extends AsyncTask<MainActivity, Integer, Void> {

    private ProgressBar pBar;
    private ArrayList<String> numList = new ArrayList<>();
    private MainActivity mainActivity;
    private ArrayAdapter<String> arrayAdapter;
    int progress = 5;

    /**
     * Constructor that takes current activity
     * @param activity current activity
     */
    public CreateLoadTask(MainActivity activity) {
        mainActivity = activity;
    }

    /**
     * OVERRIDE
     * doInBackground
     *
     * Calls the two methods create and load
     *
     * @param V Main Activity
     * @return nothing
     */
    @Override
    protected Void doInBackground(MainActivity... V) {
        create(V[0]);
        load(V[0]);
        return null;
    }

    /**
     * OVERRIDE
     * onProgressUpdate
     *
     * updates the progress bar
     *
     * @param progress the number the progress is at
     */
    @Override
    protected void onProgressUpdate(Integer... progress) {
        pBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        pBar.setProgress(progress[0]);
    }

    /**
     * OVERRIDE
     * onPostExecute
     *
     * Displays the list of numbers and clears the progress bar
     *
     * @param result idk
     */
    @Override
    protected void onPostExecute(Void result) {
        ListView lv = (ListView) mainActivity.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(
                mainActivity,
                android.R.layout.simple_list_item_1,
                numList);
        lv.setAdapter(arrayAdapter);
        pBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Create
     *
     * Creates a new file called numbers.txt and puts numbers in it
     *
     * @param v MainActivity
     */
    public void create(MainActivity v) {
        FileOutputStream fOut;
        try {
            fOut = v.openFileOutput("numbers.txt", Context.MODE_PRIVATE);
            for (int i = 1; i <= 10; i++) {
                String num = (Integer.toString(i) + "\n");
                fOut.write(num.getBytes());
                Thread.sleep(250);
                publishProgress(progress += 5);
            }
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException s) {
            s.printStackTrace();
        }
    }
    /**
     * load
     *
     * Loads the file numbers.txt and applies it the listView field
     *
     * @param v MainActivity
     */
    public void load(MainActivity v) {
        try {
            FileInputStream fin = v.openFileInput("numbers.txt");
            if (fin != null) {
                InputStreamReader instream = new InputStreamReader(fin);
                BufferedReader bReader = new BufferedReader(instream);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                    numList.add(line);
                    Thread.sleep(250);
                    publishProgress(progress += 5);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException s) {
            s.printStackTrace();
        }
    }

    /**
     * clear
     *
     * Clears the view list and the arraylist
     */
    public void clear() {
        numList.clear();
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();
    }
}



