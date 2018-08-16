import java.lang.*;
import java.io.*;
import java.util.*;
/**
 * Class to represent an usher
 * 
 * @author Johnny Gossick 
 * @version (a version number or a date)
 */
public class Usher implements Comparable<Usher>
{
    //priorityqueue of available shows to staff
    private PriorityQueue<Show> availableShows;

    //available shows that were not scheduled
    private ArrayList<Show> notScheduledShows;

    //all available shows
    private ArrayList<Show> availableShowsCopy;

    //number of shows scheduled
    private int showsScheduledCount;

    //number of shows that can be scheduled
    private int openShowCount;

    //name of usher
    private String name;

    //scheduled shows
    private ArrayList<Show> scheduledShows;

    //alternate shows
    private ArrayList<Show> alternateShows;

    /**
     * Constructor for objects of class Usher
     */
    public Usher(String name,ArrayList<Show> availableShows)
    {
        this.name=name;
        this.openShowCount=availableShows.size();
        this.availableShows=new PriorityQueue<Show>(availableShows);
        this.availableShowsCopy= new ArrayList<Show>(availableShows);
        this.scheduledShows= new ArrayList<Show>();
        this.showsScheduledCount=0;
        this.alternateShows= new ArrayList<Show>();
        this.notScheduledShows= new ArrayList<Show>();
    }
    
    /**
     * Change openshowcounts and available shows to schedule alternates
     */
    public void changeForAlternate()
    {
        this.openShowCount=availableShowsCopy.size() - this.scheduledShows.size();
        this.availableShows = new PriorityQueue<Show>(notScheduledShows);
    }
    
    /**
     * Method to check is the usher has more available shows
     */
    public boolean hasMoreAvailableShows()
    {
        if (openShowCount>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Method to add show to scheduled list
     */
    public void addToScheduled(Show s)
    {
        this.scheduledShows.add(s);
        this.showsScheduledCount++;
    }
    
    /**
     * Method to add show to alternate list
     */
    public void addToAlternate(Show s)
    {
       this.alternateShows.add(s); 
       this.showsScheduledCount++;
    }
    
    /**
     * Method to schedule the usher for a show
     */
    public boolean scheduleShow()
    {
       Show eventHead=this.availableShows.peek();
       while (eventHead!=null)
       {
           Show currentShow=eventHead;
           if(currentShow.hasScheduledSpace())
           {
               currentShow.addAsScheduled(this);
               this.openShowCount--;
               
               availableShows.poll();
               return true;
           }
           else
           {
               this.openShowCount--;
               this.notScheduledShows.add(availableShows.poll());
           }
           eventHead=availableShows.peek();
       }
       return false;
    }
    
    /**
     * Method to schedule the usher for an alternate show
     */
    public boolean scheduleAlternateShow()
    {
       Show eventHead=this.availableShows.peek();
       while (eventHead!=null)
       {
           Show currentShow=eventHead;
           if(currentShow.hasAlternateSpace())
           {
               currentShow.addAsAlternate(this);
               this.openShowCount--;
               
               availableShows.poll();
               return true;
           }
           else
           {
               this.openShowCount--;
               availableShows.poll();
           }
           eventHead=availableShows.peek();
       }
       return false;
    }
    
    /**
     * Method to compare two different ushers for use in a priority queue
     */
    public int compareTo(Usher otherUsher)
    {
        int thisCount = this.getShowsScheduledCount();
        int otherCount = otherUsher.getShowsScheduledCount();
        //first sort by the shows that have been scheduled
        //less shows scheduled goes first
        if(thisCount<otherCount)
        {
            return -1;
        }
        else if(thisCount>otherCount)
        {
            return 1;
        }
        
        //second, sort by the number of availabilities
        //less availabilities goes first
        if(thisCount>=1)
        {
            thisCount= this.getOpenShowCount();
            otherCount= otherUsher.getOpenShowCount();
            if(thisCount<otherCount)
            {
                return 1;
            }
            else if(thisCount>otherCount)
            {
                return -1;
            }
        }
        return 0;
    }
    
    /**
     * Method to print the string that represents an usher
     */
    public void printString(PrintWriter writer)
    {
        writer.println(this.name);
        
        String printString= "Scheduled Shows("+scheduledShows.size()+") :";
        writer.println(printString);
        printString="";
        for(int i=0;i<scheduledShows.size();i++)
        {
            printString=printString+scheduledShows.get(i).getShowString()+", ";
        }
        writer.println(printString);
        
        printString= "Alternate Shows("+alternateShows.size()+") :";
        writer.println(printString);
        printString="";
        for(int i=0;i<alternateShows.size();i++)
        {
            printString=printString+alternateShows.get(i).getShowString()+", ";
        }
        writer.println(printString);
        
        printString= "Available Shows("+availableShowsCopy.size()+") :";
        writer.println(printString);
        printString="";
        for(int i=0;i<availableShowsCopy.size();i++)
        {
            printString=printString+availableShowsCopy.get(i).getShowString()+", ";
        }
        writer.println(printString);
        
    }

    //get methods
    public int getOpenShowCount()
    {
        return this.openShowCount;
    }
    
    public ArrayList<Show> getAvailableShows()
    {
        return this.availableShowsCopy;
    }
    
    public ArrayList<Show> getAlternateShows()
    {
        return this.alternateShows;
    }
    
    public int getShowsScheduledCount()
    {
        return this.showsScheduledCount;
    }

    public String getName() 
    {
       return this.name;
    }
  }
