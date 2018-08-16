import java.io.PrintWriter;
import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * Class used to schedule ushers to shows
 * 
 * @author Johnn Gossick 
 * @version (a version number or a date)
 */
public class Scheduler
{
    /**
     * List of ushers
     */
    private ArrayList<Usher> ushers;

    /**
     * List of shows
     */
    private ArrayList<Show> shows;

    /**
     * Constructor for objects of class Scheduler
     */
    public Scheduler()
    {
        this.ushers= new ArrayList<Usher>();
        this.shows= new ArrayList<Show>();
    }

    /**
     * Method to read in a file, parsing all ushers and shows
     */
    public void readFile(String fileName)
    {

        Scanner sc= null;
        try
        {
            //create scanner from file
            sc= new Scanner(new FileReader(fileName));
            String line=""; //line 
            String delims = ","; //seperates by spaces 
            //loops through while there is a next line
            line=sc.nextLine();
            
            //Get names of shows
            String[] showStrings = line.split(delims);
            
            line=sc.nextLine();

            //get staffing needs for each show
            String[] usherTotals= line.split(delims);
            
            //make show list
            for(int i=1;i<showStrings.length;i++)
            {
               int usherTotal=Integer.parseInt(usherTotals[i]);
               String showString=showStrings[i];
               Show newShow= new Show(showString, usherTotal);
               shows.add(newShow);       
            }
            
            //parse ushers and availability
            while (sc.hasNextLine())
            {
                //goes through line by line
                line=sc.nextLine();

                String tokens[] = line.split(delims);
                
                String usherName=tokens[0];
                
                ArrayList<Show> availableShows=new ArrayList<Show>();
                
                for(int i=1;i<tokens.length;i++)
                {
                    //if there is text in the box, set as available
                    //to staff that show
                    String str=tokens[i];
                    Show currentShow=shows.get(i-1);
                    if(str != null && !str.isEmpty())
                    {
                        availableShows.add(currentShow);
                        currentShow.addAvailableUsher();
                    }      
                }
                //create usher and add to list
                Usher newUsher= new Usher(usherName,availableShows);
                ushers.add(newUsher);            
            }      
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        System.out.println("File "+fileName+" Read");
    }
    
    /**
     * Method to schedule the ushers set to work the shows
     */
    public void scheduleWorking()
    {
        //create priority queue
        PriorityQueue<Usher> events= new PriorityQueue<Usher>(this.ushers);
        
        Usher eventHead=events.peek();
        
        boolean hasMoreSpots=true;
        
        //loop until no more shows can be scheduled
        while(hasMoreSpots)
        {
            //priority queue for the next loop
            PriorityQueue<Usher> nextEvents= new PriorityQueue<Usher>();
            hasMoreSpots=false;
            //loop through priority queue
            while(eventHead!=null) 
            {
                Usher currentUsher=events.poll();
                //try to schedule a show for the usher
                boolean showWasScheduled=currentUsher.scheduleShow(); 
              
                //if the show was scheduled, add it to the next priority
                //queue and set to continue looping
                if(showWasScheduled)
                {
                   nextEvents.offer(currentUsher);
                   hasMoreSpots=true;
                }
                eventHead=events.peek();
            }     
            //switch to the next priority queue   
            events=nextEvents;
            eventHead=events.peek();
        }
        System.out.println("Scheduled Working");
    }
    
    /**
     * Method to schedule the alternates for the shows
     */
    public void scheduleAlternates()
    {
        //update priorities for alternate scheduling
        for(Usher u : this.ushers)
        {
            u.changeForAlternate();
        }

        //method is identical to scheduleWorking
        PriorityQueue<Usher> events= new PriorityQueue<Usher>(this.ushers);
        
        Usher eventHead=events.peek();
        
        boolean hasMoreSpots=true;
        
        while(hasMoreSpots)
        {
            PriorityQueue<Usher> nextEvents= new PriorityQueue<Usher>();
            hasMoreSpots=false;
            while(eventHead!=null) 
            {
                Usher currentUsher=events.poll();
                
                //schedule alternate shows instead of working shows
                boolean showWasScheduled=currentUsher.scheduleAlternateShow();           
                if(showWasScheduled)
                {
                   nextEvents.offer(currentUsher);
                   hasMoreSpots=true;
                }
                eventHead=events.peek();
            }     
            events=nextEvents;
            eventHead=events.peek();
        }
        System.out.println("Scheduled Alternates");
    }
    
    /**
     * Method to print the schedule by shows
     */ 
    public void printSchedule( String fileName)
    {       
        try
        {
            PrintWriter writer = new PrintWriter(fileName);
            for(int i=0;i<shows.size();i++)
            {
                Show currentShow=shows.get(i);
                currentShow.printString(writer);
                writer.println();
            } 
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println("File "+fileName+" Printed");
    }
    
    /**
     * Method to print the schedule by ushers
     */
    public void printUsherDetails(String fileName)
    {        
        try
        {
            PrintWriter writer = new PrintWriter(fileName);
            for(int i=0;i<ushers.size();i++)
            {
                Usher currentUsher=ushers.get(i);
                currentUsher.printString(writer);
                writer.println();
            }        
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println("File "+fileName+" Printed");        
    }
}
