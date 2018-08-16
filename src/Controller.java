
/**
 * Write a description of class Controller here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Controller
{
    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.out.println("Invalid args, enter path of the file to be read");
            return;
        }

        String readFileName = args[0];
        String printFileName="../output/scheduled_block.txt";
        String usherFileName = "../output/usher_details.txt";
        runScheduler(readFileName, printFileName, usherFileName);
    }
    
    public static void runScheduler(String readFileName, String printFileName, String usherFileName)
    {
        Scheduler s= new Scheduler();
        s.readFile(readFileName);
        s.scheduleWorking();
        s.scheduleAlternates();
        s.printSchedule(printFileName);
        s.printUsherDetails(usherFileName);
    }
}
