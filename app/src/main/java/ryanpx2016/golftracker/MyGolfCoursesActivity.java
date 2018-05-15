package ryanpx2016.golftracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyGolfCoursesActivity extends AppCompatActivity
{

    CourseList courselist;
    GolfCourse course;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_golf_courses);

        courselist = new CourseList();
        readData();

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

        for(int i = 0; i < courselist.getList().size(); i++)
        {
            Button button = new Button(MyGolfCoursesActivity.this);
            button.setWidth(30);
            button.setHeight(30);
            button.setText(courselist.getList().get(i).getName());
            button.setPadding(0, 20, 0, 0);

            final int currentNum = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    course = courselist.getList().get(currentNum);
                    saveCourse();

                    startActivity(new Intent(MyGolfCoursesActivity.this, UniqueScorePageActivity.class));
                }
            });

            linearLayout.addView(button);
        }

        Button addCourseButton = (Button)findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {startActivity(new Intent(MyGolfCoursesActivity.this, AddCourseActivity.class));}
        });

        Button mainMenuButton = (Button)findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {startActivity(new Intent(MyGolfCoursesActivity.this, MainActivity.class));}
        });

        Button clearCoursesButton = (Button)findViewById(R.id.clearCoursesButton);
        clearCoursesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clearFILE();
                readData();
                startActivity(new Intent(MyGolfCoursesActivity.this, MyGolfCoursesActivity.class));
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

    private void clearFILE()
    {
        try
        {
            FileOutputStream fos = openFileOutput(courselist.getFileName(), Context.MODE_PRIVATE);
            fos.write("".getBytes());

            fos.flush();
            fos.close();
        }
        catch(FileNotFoundException e) {System.out.println(e.getMessage());}
        catch(IOException IO) {IO.printStackTrace();}
    }

    public void saveCourse()
    {
        course.dataOUT = new ArrayList<>();
        course.dataOUT.add(course.getName());

        int[] parValues = course.getParNumbers();

        for(int num : parValues)
        {
            String str = "" + num;
            course.dataOUT.add(str);
        }

        if(course.hasScores())
        {
            ArrayList<Score> scores = course.getScores();

            for (Score val : scores)
            {
                String score = "" + val.getScore();
                String month = "" + val.getMonth();
                String day = "" + val.getDay();
                String year = "" + val.getYear();
                course.dataOUT.add(score);
                course.dataOUT.add(month);
                course.dataOUT.add(day);
                course.dataOUT.add(year);
            }
        }
        // puts all course objects in GolfCourse arraylist "list" into dataOUT string arraylist

        try
        {
            FileOutputStream outputFile = openFileOutput(course.getFileName(), Context.MODE_PRIVATE);
            for(int i = 0; i < course.dataOUT.size(); i++)
            {
                outputFile.write(course.dataOUT.get(i).getBytes());
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