package ryanpx2016.golftracker;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Score
{
    private int month;
    private int day;
    private int year;
    private int value; // actual score
    private final String FILENAME = "ScoreData";
    public ArrayList<String> dataIN = new ArrayList<>();
    public ArrayList<String> dataOUT = new ArrayList<>();

    public Score(int nums)      {this.setScore(nums);}

    public int getScore()       {return this.value;}
    public int getMonth()       {return this.month;}
    public int getDay()         {return this.day;}
    public int getYear()        {return this.year;}
    public void setScore(int s) {this.value = s;}
    public void setMonth(int m) {this.month = m;}
    public void setDay(int d)   {this.day = d;}
    public void setYear(int y)  {this.year = y;}
    public String getFileName() {return this.FILENAME;}
    public void setDate()
    {
        String dateandtime = new Date().toString();
        String date =   dateandtime.substring(4, 7) +   // month
                dateandtime.substring(8, 10) +  // day
                dateandtime.substring(24);      // year

        if(date.substring(0, 3).toLowerCase().equals("jan"))         this.setMonth(1);  // convert month data
        else if(date.substring(0, 3).toLowerCase().equals("feb"))    this.setMonth(2);  // to integer
        else if(date.substring(0, 3).toLowerCase().equals("mar"))    this.setMonth(3);
        else if(date.substring(0, 3).toLowerCase().equals("apr"))    this.setMonth(4);
        else if(date.substring(0, 3).toLowerCase().equals("may"))    this.setMonth(5);
        else if(date.substring(0, 3).toLowerCase().equals("jun"))    this.setMonth(6);
        else if(date.substring(0, 3).toLowerCase().equals("jul"))    this.setMonth(7);
        else if(date.substring(0, 3).toLowerCase().equals("aug"))    this.setMonth(8);
        else if(date.substring(0, 3).toLowerCase().equals("sep"))    this.setMonth(9);
        else if(date.substring(0, 3).toLowerCase().equals("oct"))    this.setMonth(10);
        else if(date.substring(0, 3).toLowerCase().equals("nov"))    this.setMonth(11);
        else if(date.substring(0, 3).toLowerCase().equals("dec"))    this.setMonth(12);

        this.setDay(Integer.parseInt(date.substring(3, 5)));    // convert day data to integer
        this.setYear(Integer.parseInt(date.substring(5)));      // convert year data to integer
    }
//  public void readScore()
//    {
//        String line = null;
//        try
//        {
//            FileInputStream fis = openFileInput(score.getFileName());
//            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));
//            line = inputFile.readLine();
//
//            for(int x = 0; line != null; x++) // readLine() will return null at end of file
//            {
//                score.dataIN.add(line);
//                line = inputFile.readLine(); // line = next line; will = null if end of file
//            }
//
//            inputFile.close();
//        }   // sends all text to arraylist in order
//        catch(FileNotFoundException e)    {System.out.println(e.getMessage());}
//        catch(IOException IO)             {IO.printStackTrace();}
//        // dataIN now has all data
//
//        int num = Integer.parseInt(score.dataIN.get(0));
//        int month = Integer.parseInt(score.dataIN.get(1));
//        int day = Integer.parseInt(score.dataIN.get(2));
//        int year = Integer.parseInt(score.dataIN.get(3));
//        score.setScore(num);
//        score.setMonth(month);
//        score.setDay(day);
//        score.setYear(year);
//    }
//
//    public void saveScore()
//    {
//        score.dataOUT = new ArrayList<String>();
//
//        String num = "" + score.getScore();
//        String month = "" + score.getMonth();
//        String day = "" + score.getDay();
//        String year = "" + score.getYear();
//        score.dataOUT.add(num);
//        score.dataOUT.add(month);
//        score.dataOUT.add(day);
//        score.dataOUT.add(year);
//        // puts all course objects in GolfCourse arraylist "list" into dataOUT string arraylist
//
//        try
//        {
//            FileOutputStream outputFile = openFileOutput(score.getFileName(), Context.MODE_PRIVATE);
//            for(int i = 0; i < score.dataOUT.size(); i++)
//            {
//                outputFile.write(score.dataOUT.get(i).getBytes());
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