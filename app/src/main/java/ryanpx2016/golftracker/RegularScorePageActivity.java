package ryanpx2016.golftracker;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RegularScorePageActivity extends AppCompatActivity
{

    CourseList courselist;
    Score score;
    EditText par1, par2, par3, par4, par5, par6, par7, par8, par9;
    EditText stroke1, stroke2, stroke3, stroke4, stroke5, stroke6, stroke7, stroke8, stroke9;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_score_page);

        courselist = new CourseList();
        readData();

        Button saveAsNewCourseButton = (Button)findViewById(R.id.saveAsNewCourseButton);
        saveAsNewCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                par1 = (EditText) findViewById(R.id.par1);
                par2 = (EditText) findViewById(R.id.par2);
                par3 = (EditText) findViewById(R.id.par3);
                par4 = (EditText) findViewById(R.id.par4);
                par5 = (EditText) findViewById(R.id.par5);
                par6 = (EditText) findViewById(R.id.par6);
                par7 = (EditText) findViewById(R.id.par7);
                par8 = (EditText) findViewById(R.id.par8);
                par9 = (EditText) findViewById(R.id.par9);
                stroke1 = (EditText) findViewById(R.id.stroke1);
                stroke2 = (EditText) findViewById(R.id.stroke2);
                stroke3 = (EditText) findViewById(R.id.stroke3);
                stroke4 = (EditText) findViewById(R.id.stroke4);
                stroke5 = (EditText) findViewById(R.id.stroke5);
                stroke6 = (EditText) findViewById(R.id.stroke6);
                stroke7 = (EditText) findViewById(R.id.stroke7);
                stroke8 = (EditText) findViewById(R.id.stroke8);
                stroke9 = (EditText) findViewById(R.id.stroke9);

                if (par1.getText().toString().trim().length() == 0 ||
                    par2.getText().toString().trim().length() == 0 ||
                    par3.getText().toString().trim().length() == 0 ||
                    par4.getText().toString().trim().length() == 0 ||
                    par5.getText().toString().trim().length() == 0 ||
                    par6.getText().toString().trim().length() == 0 ||
                    par7.getText().toString().trim().length() == 0 ||
                    par8.getText().toString().trim().length() == 0 ||
                    par9.getText().toString().trim().length() == 0 ||
                    stroke1.getText().toString().trim().length() == 0 ||
                    stroke2.getText().toString().trim().length() == 0 ||
                    stroke3.getText().toString().trim().length() == 0 ||
                    stroke4.getText().toString().trim().length() == 0 ||
                    stroke5.getText().toString().trim().length() == 0 ||
                    stroke6.getText().toString().trim().length() == 0 ||
                    stroke7.getText().toString().trim().length() == 0 ||
                    stroke8.getText().toString().trim().length() == 0 ||
                    stroke9.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(RegularScorePageActivity.this, "Please fill in all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                int[] pars = new int[18];
                pars[0] = Integer.parseInt(par1.getText().toString());
                pars[1] = Integer.parseInt(par2.getText().toString());
                pars[2] = Integer.parseInt(par3.getText().toString());
                pars[3] = Integer.parseInt(par4.getText().toString());
                pars[4] = Integer.parseInt(par5.getText().toString());
                pars[5] = Integer.parseInt(par6.getText().toString());
                pars[6] = Integer.parseInt(par7.getText().toString());
                pars[7] = Integer.parseInt(par8.getText().toString());
                pars[8] = Integer.parseInt(par9.getText().toString());

                courselist.addCourse(new GolfCourse("newCourse", pars));
                saveData(); // creates the new course


                int[] strokes = new int[9];

                strokes[0] = Integer.parseInt(stroke1.getText().toString());
                strokes[1] = Integer.parseInt(stroke2.getText().toString());
                strokes[2] = Integer.parseInt(stroke3.getText().toString());
                strokes[3] = Integer.parseInt(stroke4.getText().toString());
                strokes[4] = Integer.parseInt(stroke5.getText().toString());
                strokes[5] = Integer.parseInt(stroke6.getText().toString());
                strokes[6] = Integer.parseInt(stroke7.getText().toString());
                strokes[7] = Integer.parseInt(stroke8.getText().toString());
                strokes[8] = Integer.parseInt(stroke9.getText().toString());

                int value = 0;
                for (int i = 0; i < 9; i++) {
                    value += (strokes[i] - pars[i]);
                }

                score = new Score(value);
                saveScore(); // saves score

                startActivity(new Intent(RegularScorePageActivity.this, NewCourseNameActivity.class));
            }
        });

        Button calculateScoreButton = (Button)findViewById(R.id.calculateScoreButton);
        calculateScoreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                par1 = (EditText)findViewById(R.id.par1);
                par2 = (EditText)findViewById(R.id.par2);
                par3 = (EditText)findViewById(R.id.par3);
                par4 = (EditText)findViewById(R.id.par4);
                par5 = (EditText)findViewById(R.id.par5);
                par6 = (EditText)findViewById(R.id.par6);
                par7 = (EditText)findViewById(R.id.par7);
                par8 = (EditText)findViewById(R.id.par8);
                par9 = (EditText)findViewById(R.id.par9);
                stroke1 = (EditText)findViewById(R.id.stroke1);
                stroke2 = (EditText)findViewById(R.id.stroke2);
                stroke3 = (EditText)findViewById(R.id.stroke3);
                stroke4 = (EditText)findViewById(R.id.stroke4);
                stroke5 = (EditText)findViewById(R.id.stroke5);
                stroke6 = (EditText)findViewById(R.id.stroke6);
                stroke7 = (EditText)findViewById(R.id.stroke7);
                stroke8 = (EditText)findViewById(R.id.stroke8);
                stroke9 = (EditText)findViewById(R.id.stroke9);

                if (par1.getText().toString().trim().length() == 0 ||
                    par2.getText().toString().trim().length() == 0 ||
                    par3.getText().toString().trim().length() == 0 ||
                    par4.getText().toString().trim().length() == 0 ||
                    par5.getText().toString().trim().length() == 0 ||
                    par6.getText().toString().trim().length() == 0 ||
                    par7.getText().toString().trim().length() == 0 ||
                    par8.getText().toString().trim().length() == 0 ||
                    par9.getText().toString().trim().length() == 0 ||
                    stroke1.getText().toString().trim().length() == 0 ||
                    stroke2.getText().toString().trim().length() == 0 ||
                    stroke3.getText().toString().trim().length() == 0 ||
                    stroke4.getText().toString().trim().length() == 0 ||
                    stroke5.getText().toString().trim().length() == 0 ||
                    stroke6.getText().toString().trim().length() == 0 ||
                    stroke7.getText().toString().trim().length() == 0 ||
                    stroke8.getText().toString().trim().length() == 0 ||
                    stroke9.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(RegularScorePageActivity.this, "Please fill in all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                int[] pars = new int[18];
                int[] strokes = new int[9];

                pars[0] = Integer.parseInt(par1.getText().toString());
                pars[1] = Integer.parseInt(par2.getText().toString());
                pars[2] = Integer.parseInt(par3.getText().toString());
                pars[3] = Integer.parseInt(par4.getText().toString());
                pars[4] = Integer.parseInt(par5.getText().toString());
                pars[5] = Integer.parseInt(par6.getText().toString());
                pars[6] = Integer.parseInt(par7.getText().toString());
                pars[7] = Integer.parseInt(par8.getText().toString());
                pars[8] = Integer.parseInt(par9.getText().toString());
                strokes[0] = Integer.parseInt(stroke1.getText().toString());
                strokes[1] = Integer.parseInt(stroke2.getText().toString());
                strokes[2] = Integer.parseInt(stroke3.getText().toString());
                strokes[3] = Integer.parseInt(stroke4.getText().toString());
                strokes[4] = Integer.parseInt(stroke5.getText().toString());
                strokes[5] = Integer.parseInt(stroke6.getText().toString());
                strokes[6] = Integer.parseInt(stroke7.getText().toString());
                strokes[7] = Integer.parseInt(stroke8.getText().toString());
                strokes[8] = Integer.parseInt(stroke9.getText().toString());

                int value = 0;
                for(int i = 0; i < 9; i++) {value += (strokes[i] - pars[i]);}

                score = new Score(value);
                saveScore();

                startActivity(new Intent(RegularScorePageActivity.this, ResultsPageActivity.class));
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

    public void saveScore()
    {
        score.dataOUT = new ArrayList<String>();

        String num = "" + score.getScore();
        String month = "" + score.getMonth();
        String day = "" + score.getDay();
        String year = "" + score.getYear();
        score.dataOUT.add(num);
        score.dataOUT.add(month);
        score.dataOUT.add(day);
        score.dataOUT.add(year);
        // puts all course objects in GolfCourse arraylist "list" into dataOUT string arraylist

        try
        {
            FileOutputStream outputFile = openFileOutput(score.getFileName(), Context.MODE_PRIVATE);
            for(int i = 0; i < score.dataOUT.size(); i++)
            {
                outputFile.write(score.dataOUT.get(i).getBytes());
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