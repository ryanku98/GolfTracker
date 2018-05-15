package ryanpx2016.golftracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CourseList
{
    private final String FILENAME = "CourseData";
    private final String FILENAMECOPY = "CourseDataCopy";
    public ArrayList<GolfCourse> list = new ArrayList<>();
    public ArrayList<String> dataIN = new ArrayList<>();
    private ArrayList<String> dataINCOPY = new ArrayList<>(); // in case phone/app crashes while writing
    public ArrayList<String> dataOUT = new ArrayList<>();
    private ArrayList<String> dataOUTCOPY = new ArrayList<>(); // in case phone/app crashes while saving

    public CourseList(){}

    public ArrayList<GolfCourse> getList()      {return this.list;}
    public ArrayList<String> getData()          {return this.dataIN;}
    public void addCourse(GolfCourse newCourse) {this.list.add(newCourse);}
    public String getFileName()                 {return this.FILENAME;}
    public void changeLast(String name) // easily allows programmer to change the name of the last saved golf course
    {
        this.list.get(this.list.size() - 1).setName(name);
    }
    public void backupIN() // backups dataIN arraylist
    {
        int i = 0;
        this.dataINCOPY.clear();
        for(String str : this.dataIN)   this.dataINCOPY.add(str);
    }
    public void backupOUT() // backups dataOUT arraylist
    {
        int i = 0;
        this.dataOUTCOPY.clear();
        for(String str : this.dataOUT)  this.dataOUTCOPY.add(str);
    }
//    private void clearFILE()
//    {
//        try
//        {
//            FileOutputStream fos = openFileOutput(tester.getFileName(), Context.MODE_PRIVATE);
//            fos.write("".getBytes());
//
//            fos.flush();
//            fos.close();
//        }
//        catch(FileNotFoundException e) {System.out.println(e.getMessage());}
//        catch(IOException IO) {IO.printStackTrace();}
//    }
//    public void backupFILE() // backs up save file (coursedata)
//    {
//        String line = null;
//        try
//        {
//            FileInputStream fis = openFileInput(tester.getFileName());
//            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));

//            FileOutputStream outputFile = openFileOutput(courselist.getFileName(), Context.MODE_PRIVATE);
//            line = inputFile.readLine();

//            for(int x = 0; line != null; x++) // readLine() will return null at end of file
//            {
//                outputFile.write(line.getBytes());
//                line = inputFile.readLine(); // line = next line; will = null if end of file
//            }

//            inputFile.close();
//            outputFile.flush();
//            outputFile.close();
//        }   // sends all text to arraylist in order
//        catch(FileNotFoundException e) {System.out.println(e.getMessage());}
//        catch(IOException IO) {IO.printStackTrace();}
//    }
//
//
//
//    public void readData() // puts file data into dataIN arraylist
//    {
//        String line = null;
//        try
//        {
//            FileInputStream fis = openFileInput(courselist.getFileName());
//            BufferedReader inputFile = new BufferedReader(new InputStreamReader(fis));
//            line = inputFile.readLine();
//
//            for(int x = 0; line != null; x++) // readLine() will return null at end of file
//            {
//                courselist.dataIN.add(line);
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
//        while(i < courselist.dataIN.size()) // instantiate all objects
//        {
//            String newCourseName = courselist.dataIN.get(i++);
//            int[] parValues = new int[18];
//            for(int j = 0; j < 18; j++) // save parValue data
//            {
//                if(i >= courselist.dataIN.size() - 1) break;
//                parValues[j] = Integer.parseInt(courselist.dataIN.get(i++));
//            }
//
//            courselist.addCourse(new GolfCourse(newCourseName, parValues)); // create the new course
//
//            if(i >= courselist.dataIN.size() - 1) break; // if end of data list is already reached, stop looping
//            else
//            {
//                while(i < courselist.dataIN.size() - 1 &&
//                        courselist.dataIN.get(i).charAt(0) >= 48 &&
//                        courselist.dataIN.get(i).charAt(0) <= 57)
//                { // test if next line is a number (score)
//                    int num = Integer.parseInt(courselist.dataIN.get(i++));
//                    int month = Integer.parseInt(courselist.dataIN.get(i++));
//                    int day = Integer.parseInt(courselist.dataIN.get(i++));
//                    int year = Integer.parseInt(courselist.dataIN.get(i++));
//                    Score score = new Score(num);
//                    score.setMonth(month);
//                    score.setDay(day);
//                    score.setYear(year);
//                    courselist.list.get(courselist.list.size() - 1).addScore(score);
//                }
//            }
//        }
//    }

//    public void saveData()
//    {
//        courselist.dataOUT = new ArrayList<>();
//
//        for(int i = 0; i < courselist.list.size(); i++)
//        {
//            GolfCourse course = courselist.list.get(i);
//            courselist.dataOUT.add(course.getName());
//            int[] parValues = course.getParNumbers();
//
//            for(int num : parValues)
//            {
//                String str1 = "" + num;
//                courselist.dataOUT.add(str1);
//            }
//
//            if(course.hasScores())
//            {
//                ArrayList<Score> scores = course.getScores();
//
//                for(Score val : scores)
//                {
//                    String score = "" + val.getScore();
//                    String month = "" + val.getMonth();
//                    String day = "" + val.getDay();
//                    String year = "" + val.getYear();
//                    courselist.dataOUT.add(score);
//                    courselist.dataOUT.add(month);
//                    courselist.dataOUT.add(day);
//                    courselist.dataOUT.add(year);
//                }
//            }
//        } // puts all course objects in GolfCourse arraylist "list" into dataOUT string arraylist
//
//        try
//        {
//            FileOutputStream outputFile = openFileOutput(courselist.getFileName(), Context.MODE_PRIVATE);
//            for(int i = 0; i < courselist.dataOUT.size(); i++)
//            {
//                outputFile.write(courselist.dataOUT.get(i).getBytes());
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