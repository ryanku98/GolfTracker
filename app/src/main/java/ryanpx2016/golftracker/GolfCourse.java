package ryanpx2016.golftracker;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GolfCourse
{
    private String name;
    private final String FILENAME = "GolfCourseData";
    public ArrayList<String> dataIN = new ArrayList<>();
    public ArrayList<String> dataOUT = new ArrayList<>();
    private int[] parNumbers = new int[18];
    private ArrayList<Score> pastScores = new ArrayList<>();

    public GolfCourse(String title, int[] nums)
    {
        this.name = title;
        this.parNumbers = nums;
    }

    public String getName()                 {return this.name;}
    public String getFileName()             {return this.FILENAME;}
    public void setName(String title)       {this.name = title;}
    public int[] getParNumbers()            {return this.parNumbers;}
    public void setParNumbers(int[] nums)   {this.parNumbers = nums;}
    public ArrayList<Score> getScores()     {return pastScores;}
    public void addScore(Score newScore)    {this.pastScores.add(newScore);}
    public void resetScores()               {this.pastScores.clear();}
    public void printScores()
    {
        for(int i = 0; i < this.pastScores.size(); i++)
        {
            Score score = this.pastScores.get(i);
            System.out.println("Score: " + score.getScore());
            System.out.println(score.getMonth()+"/"+score.getDay()+"/"+score.getYear());
        }
    }
    public boolean hasScores()
    {
        if(this.pastScores.size() > 0)  return true;
        return false;
    }

//    public void readCourse()
//    {
//        String line = null;
//        try
//        {
//            FileInputStream fis = openFileInput(course.getFileName());
//            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));
//            line = inputFile.readLine();
//
//            for(int x = 0; line != null; x++) // readLine() will return null at end of file
//            {
//                course.dataIN.add(line);
//                line = inputFile.readLine(); // line = next line; will = null if end of file
//            }
//
//            inputFile.close();
//        }   // sends all text to arraylist in order
//        catch(FileNotFoundException e)    {System.out.println(e.getMessage());}
//        catch(IOException IO)             {IO.printStackTrace();}
//        // dataIN now has all data
//
//        int i = 0;
//        while(i < course.dataIN.size()) // instantiate all objects
//        {
//            String newCourseName = course.dataIN.get(i++);
//            int[] parValues = new int[18];
//            for(int j = 0; j < 18; j++) // save parValue data
//            {
//                if(i >= course.dataIN.size() - 1) break;
//                parValues[j] = Integer.parseInt(course.dataIN.get(i++));
//            }
//            course = new GolfCourse(newCourseName, parValues);
//
//            if(i >= course.dataIN.size() - 1) break; // if end of data list is already reached, stop looping
//            else
//            {
//                while(i < course.dataIN.size() - 1 &&
//                        course.dataIN.get(i).charAt(0) >= 48 &&
//                        course.dataIN.get(i).charAt(0) <= 57)
//                { // test if next line is a number (score)
//                    int num = Integer.parseInt(course.dataIN.get(i++));
//                    int month = Integer.parseInt(course.dataIN.get(i++));
//                    int day = Integer.parseInt(course.dataIN.get(i++));
//                    int year = Integer.parseInt(course.dataIN.get(i++));
//                    Score score = new Score(num);
//                    score.setMonth(month);
//                    score.setDay(day);
//                    score.setYear(year);
//                    course.addScore(score);
//                }
//            }
//        }
//    }
//
//    public void saveCourse()
//    {
//        course.dataOUT = new ArrayList<>();
//        course.dataOUT.add(course.getName());
//
//        int[] parValues = course.getParNumbers();
//
//        for(int num : parValues)
//        {
//            String str = "" + num;
//            course.dataOUT.add(str);
//        }
//
//        if(course.hasScores())
//        {
//            ArrayList<Score> scores = course.getScores();
//
//            for (Score val : scores)
//            {
//                String score = "" + val.getScore();
//                String month = "" + val.getMonth();
//                String day = "" + val.getDay();
//                String year = "" + val.getYear();
//                course.dataOUT.add(score);
//                course.dataOUT.add(month);
//                course.dataOUT.add(day);
//                course.dataOUT.add(year);
//            }
//        }
//        // puts all course objects in GolfCourse arraylist "list" into dataOUT string arraylist
//
//        try
//        {
//            FileOutputStream outputFile = openFileOutput(course.getFileName(), Context.MODE_PRIVATE);
//            for(int i = 0; i < course.dataOUT.size(); i++)
//            {
//                outputFile.write(course.dataOUT.get(i).getBytes());
//                outputFile.write("\n".getBytes());
//            }
//            outputFile.flush();
//            outputFile.close();
//        }
//        catch(FileNotFoundException e) {System.out.println(e.getMessage());}
//        catch(IOException IO) {IO.printStackTrace();}
//        // saves all data into file
//    }
}