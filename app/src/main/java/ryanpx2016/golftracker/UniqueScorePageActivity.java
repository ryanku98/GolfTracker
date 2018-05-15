package ryanpx2016.golftracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UniqueScorePageActivity extends AppCompatActivity
{

    GolfCourse course;
    Score score;
    TextView courseTitle, par1, par2, par3, par4, par5, par6, par7, par8, par9;
    EditText stroke1, stroke2, stroke3, stroke4, stroke5, stroke6, stroke7, stroke8, stroke9;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_score_page);

        course = new GolfCourse("", new int[9]);
        readCourse();

        par1 = (TextView)findViewById(R.id.par1);
        par2 = (TextView)findViewById(R.id.par2);
        par3 = (TextView)findViewById(R.id.par3);
        par4 = (TextView)findViewById(R.id.par4);
        par5 = (TextView)findViewById(R.id.par5);
        par6 = (TextView)findViewById(R.id.par6);
        par7 = (TextView)findViewById(R.id.par7);
        par8 = (TextView)findViewById(R.id.par8);
        par9 = (TextView)findViewById(R.id.par9);

        int[] pars = course.getParNumbers();
        ArrayList<String> parVal = new ArrayList<>();
        for(int num : pars) parVal.add("" + num);

        par1.setText(parVal.get(0));
        par2.setText(parVal.get(1));
        par3.setText(parVal.get(2));
        par4.setText(parVal.get(3));
        par5.setText(parVal.get(4));
        par6.setText(parVal.get(5));
        par7.setText(parVal.get(6));
        par8.setText(parVal.get(7));
        par9.setText(parVal.get(8));

        courseTitle = (TextView)findViewById(R.id.courseTitle);
        courseTitle.setText(course.getName());

        Button calculateScoreButton = (Button)findViewById(R.id.calculateScoreButton);
        calculateScoreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                stroke1 = (EditText)findViewById(R.id.stroke1);
                stroke2 = (EditText)findViewById(R.id.stroke2);
                stroke3 = (EditText)findViewById(R.id.stroke3);
                stroke4 = (EditText)findViewById(R.id.stroke4);
                stroke5 = (EditText)findViewById(R.id.stroke5);
                stroke6 = (EditText)findViewById(R.id.stroke6);
                stroke7 = (EditText)findViewById(R.id.stroke7);
                stroke8 = (EditText)findViewById(R.id.stroke8);
                stroke9 = (EditText)findViewById(R.id.stroke9);

                if (stroke1.getText().toString().trim().length() == 0 ||
                    stroke2.getText().toString().trim().length() == 0 ||
                    stroke3.getText().toString().trim().length() == 0 ||
                    stroke4.getText().toString().trim().length() == 0 ||
                    stroke5.getText().toString().trim().length() == 0 ||
                    stroke6.getText().toString().trim().length() == 0 ||
                    stroke7.getText().toString().trim().length() == 0 ||
                    stroke8.getText().toString().trim().length() == 0 ||
                    stroke9.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(UniqueScorePageActivity.this, "Please fill in all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

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
                for(int i = 0; i < 9; i++) {value += (strokes[i] - course.getParNumbers()[i]);}

                score = new Score(value);
                saveScore();

                startActivity(new Intent(UniqueScorePageActivity.this, ResultsPageActivity.class));
            }
        });

    }

    public void readCourse()
    {
        String line = null;
        try
        {
            FileInputStream fis = openFileInput(course.getFileName());
            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));
            line = inputFile.readLine();

            for(int x = 0; line != null; x++) // readLine() will return null at end of file
            {
                course.dataIN.add(line);
                line = inputFile.readLine(); // line = next line; will = null if end of file
            }

            inputFile.close();
        }   // sends all text to arraylist in order
        catch(FileNotFoundException e)    {System.out.println(e.getMessage());}
        catch(IOException IO)             {IO.printStackTrace();}
        // dataIN now has all data

        int i = 0;
        while(i < course.dataIN.size()) // instantiate all objects
        {
            String newCourseName = course.dataIN.get(i++);
            int[] parValues = new int[18];
            for(int j = 0; j < 18; j++) // save parValue data
            {
                if(i >= course.dataIN.size() - 1) break;
                parValues[j] = Integer.parseInt(course.dataIN.get(i++));
            }
            course = new GolfCourse(newCourseName, parValues);

            if(i >= course.dataIN.size() - 1) break; // if end of data list is already reached, stop looping
            else
            {
                while(i < course.dataIN.size() - 1 &&
                        course.dataIN.get(i).charAt(0) >= 48 &&
                        course.dataIN.get(i).charAt(0) <= 57)
                { // test if next line is a number (score)
                    int num = Integer.parseInt(course.dataIN.get(i++));
                    int month = Integer.parseInt(course.dataIN.get(i++));
                    int day = Integer.parseInt(course.dataIN.get(i++));
                    int year = Integer.parseInt(course.dataIN.get(i++));
                    Score score = new Score(num);
                    score.setMonth(month);
                    score.setDay(day);
                    score.setYear(year);
                    course.addScore(score);
                }
            }
        }
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
