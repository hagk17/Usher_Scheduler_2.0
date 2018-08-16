import java.io.PrintWriter;
import java.io.File;
import java.lang.*;
import java.util.*;

/**
 * Class that represents a performance
 * 
 * @author Johnny Gossick
 * @version (a version number or a date)
 */
public class Show implements Comparable<Show>
{
    //string to represent a show
    private String showString;

    //number of ushers available to schedule a show
    private int availableUshers;

    ///number of ushers scheduled to usher a show
    private int usherCount;

    private int usherTotal;

    //list of ushers scheduled to usher the show
    private LinkedList<Usher> ushers;

    //the two alternate ushers
    private Usher[] alts;

    /**
     * Constructor for objects of class Show
     */
    public Show(String showString, int usherTotal)
    {
        this.usherTotal = usherTotal;
        this.showString = showString;
        this.ushers = new LinkedList<Usher>();
        this.usherCount = 0;
        this.alts=new Usher[2];
        this.alts[0]=null;
        this.alts[1]=null;
        this.availableUshers=0;
    }
    
    public int getUsherCount()
    {
        return this.usherCount;
    }
    
    public void addAvailableUsher()
    {
        this.availableUshers++;
        
    }
     
    public int getAvailableUshers()
    {
        return this.availableUshers;
    }
    
    public boolean hasScheduledSpace()
    {
        if(usherCount<usherTotal)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean hasAlternateSpace()
    {
        return alts[0]==null || alts[1]==null;
    }
    
    /**
     * Method to add an usher to the show as scheduled
     */
    public void addAsScheduled(Usher usher)
    {
        if(usherCount<usherTotal)
        {
            ushers.add(usher);
            usherCount++;
            usher.addToScheduled(this);
        }
       else
        {
            System.out.println("EEERRRORRR");
        }
    }
    
    /**
     * Method to add an usher to the show as an alternate
     */
    public void addAsAlternate(Usher usher)
    {
        if(alts[0]==null)
        {
            this.alts[0]=usher;
            usher.addToAlternate(this);
        }
        else if(alts[1]==null)
        {
            this.alts[1]=usher;
            usher.addToAlternate(this);
        }
       else
        {
            System.out.println("EEERRRORRR");
        }
    }
    
    public String getShowString()
    {    
       return this.showString;  
    }

    /**
     * Method to print the string that represents a show
     */
    public void printString(PrintWriter writer)
    {
        writer.println(this.showString);
        String printString="Ushers: ";
        ListIterator<Usher> itr=ushers.listIterator(0);
        
        while (itr.hasNext())
        {
            String nameString=itr.next().getName();
            printString=printString+ nameString+", ";
        }
        
        writer.println(printString);
        int usherDiff= usherTotal-usherCount;
        
        
        if(usherDiff>0)
        {
            printString = "NEED " + usherDiff + " MORE USHERS!!! (AND TWO ALTERNATES PLEASE!)";
            writer.println(printString);
        }
        else
        {
            printString = "Alternates: ";
            for(int i=0; i<this.alts.length; i++)
            {
                if(alts[i]==null)
                {
                    int altsNeeded=alts.length-i;
                    printString = printString + "NEED " + altsNeeded + " MORE ALTERNATE(S)!!!!";
                    break;
                }
                else
                {
                    printString = printString + alts[i].getName() + ", ";
                }
            }
            writer.println(printString);
        }
        writer.println("Head Usher: ");
    }
    
    /**
     * Method to compare two shows.  Used for priority queue
     */
    public int compareTo(Show otherShow)
    {
        int thisCount= this.availableUshers;
        int otherCount= otherShow.getAvailableUshers();
        
        if(thisCount<otherCount)
        {
            return -1;
        }
        else if(thisCount>otherCount)
        {
            return 1;
        }
        else 
        {
            return 0;
        }
    }
}
