package ryanpx2016.golftracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResultsPageActivity extends AppCompatActivity
{

    CourseList courselist;
    Score score;
    TextView scorePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        courselist = new CourseList();
        readData();

        score = new Score(0);
        readScore();

        scorePreview = (TextView)findViewById(R.id.score);
        String value = "" + score.getScore();
        char first = value.charAt(0);
        if(first != '-') value = "+" + value;
        scorePreview.setText(value);

        clearScore();

        Button mainMenuButton = (Button)findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {startActivity(new Intent(ResultsPageActivity.this, MainActivity.class));}
        });
    }

    public void readData() // puts file data into dataIN arraylist
    {
        String line = null;
        try
        {
            FileInputStream fis = openFileInput(courselist.getFileName());
            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));
            line = inputFile.readLine();

            for(int x = 0; line != null; x++) // readLine() will return null at end of file
            {
                courselist.dataIN.add(line);
                line = inputFile.readLine(); // line = next line; will = null if end of file
            }

            inputFile.close();
        }   // sends all text to arraylist in order
        catch(FileNotFoundException e)    {System.out.println(e.getMessage());}
        catch(IOException IO)             {IO.printStackTrace();}
        // dataIN now has all data

        int i = 0;
        while(i < courselist.dataIN.size()) // instantiate all objects
        {
            String newCourseName = courselist.dataIN.get(i++);
            int[] parValues = new int[18];
            for(int j = 0; j < 18; j++) // save parValue data
            {
                if(i >= courselist.dataIN.size() - 1) break;
                parValues[j] = Integer.parseInt(courselist.dataIN.get(i++));
            }

            courselist.addCourse(new GolfCourse(newCourseName, parValues)); // create the new course

            if(i >= courselist.dataIN.size() - 1) break; // if end of data list is already reached, stop looping
            else
            {
                while(i < courselist.dataIN.size() - 1 &&
                        courselist.dataIN.get(i).charAt(0) >= 48 &&
                        courselist.dataIN.get(i).charAt(0) <= 57)
                { // test if next line is a number (score)
                    int num = Integer.parseInt(courselist.dataIN.get(i++));
                    int month = Integer.parseInt(courselist.dataIN.get(i++));
                    int day = Integer.parseInt(courselist.dataIN.get(i++));
                    int year = Integer.parseInt(courselist.dataIN.get(i++));
                    Score score = new Score(num);
                    score.setMonth(month);
                    score.setDay(day);
                    score.setYear(year);
                    courselist.list.get(courselist.list.size() - 1).addScore(score);
                }
            }
        }
    }

    public void readScore()
    {
        String line = null;
        try
        {
            FileInputStream fis = openFileInput(score.getFileName());
            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));
            line = inputFile.readLine();

            for(int x = 0; line != null; x++) // readLine() will return null at end of file
            {
                score.dataIN.add(line);
                line = inputFile.readLine(); // line = next line; will = null if end of file
            }

            inputFile.close();
        }   // sends all text to arraylist in order
        catch(FileNotFoundException e)    {System.out.println(e.getMessage());}
        catch(IOException IO)             {IO.printStackTrace();}
        // dataIN now has all data

        int num = Integer.parseInt(score.dataIN.get(0));
        int month = Integer.parseInt(score.dataIN.get(1));
        int day = Integer.parseInt(score.dataIN.get(2));
        int year = Integer.parseInt(score.dataIN.get(3));
        score.setScore(num);
        score.setMonth(month);
        score.setDay(day);
        score.setYear(year);
    }

    public void clearScore()
    {
        try
        {
            FileOutputStream fos = openFileOutput(score.getFileName(), Context.MODE_PRIVATE);
            fos.write("".getBytes());

            fos.flush();
            fos.close();
        }
        catch(FileNotFoundException e) {System.out.println(e.getMessage());}
        catch(IOException IO) {IO.printStackTrace();}
    }
}
