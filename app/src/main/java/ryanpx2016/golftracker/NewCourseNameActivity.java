package ryanpx2016.golftracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NewCourseNameActivity extends AppCompatActivity
{

    CourseList courselist;
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course_name);

        courselist = new CourseList();
        readData();

        Button saveCourseButton = (Button)findViewById(R.id.saveCourseButton);
        saveCourseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                nameText = (EditText) findViewById(R.id.editText);
                String name = nameText.getText().toString();

                if (name.trim().length() == 0)
                {
                    Toast.makeText(NewCourseNameActivity.this, "Please create a name", Toast.LENGTH_LONG).show();
                    return;
                }

                courselist.changeLast(name);
                saveData();

                startActivity(new Intent(NewCourseNameActivity.this, ResultsPageActivity.class));
            }
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

    public void saveData()
    {
        courselist.dataOUT = new ArrayList<String>();

        for(int i = 0; i < courselist.list.size(); i++)
        {
            GolfCourse course = courselist.list.get(i);
            courselist.dataOUT.add(course.getName());
            int[] parValues = course.getParNumbers();

            for(int num : parValues)
            {
                String str1 = "" + num;
                courselist.dataOUT.add(str1);
            }

            if(course.hasScores())
            {
                ArrayList<Score> scores = course.getScores();

                for(Score val : scores)
                {
                    String score = "" + val.getScore();
                    String month = "" + val.getMonth();
                    String day = "" + val.getDay();
                    String year = "" + val.getYear();
                    courselist.dataOUT.add(score);
                    courselist.dataOUT.add(month);
                    courselist.dataOUT.add(day);
                    courselist.dataOUT.add(year);
                }
            }
        } // puts all course objects in GolfCourse arraylist "list" into dataOUT string arraylist

        try
        {
            FileOutputStream outputFile = openFileOutput(courselist.getFileName(), Context.MODE_PRIVATE);
            for(int i = 0; i < courselist.dataOUT.size(); i++)
            {
                outputFile.write(courselist.dataOUT.get(i).getBytes());
                outputFile.write("\n".getBytes());
            }
            outputFile.flush();
            outputFile.close();
        }
        catch(FileNotFoundException e) {System.out.println(e.getMessage());}
        catch(IOException IO) {IO.printStackTrace();}
        // saves all data into file
    }

}