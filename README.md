# Usher_Scheduler_2.0

This repository contains a program used to schedule ushering shifts for the performances at Lafayette College's William Center for the Arts.  

# Building 

In the terminal, navigate to the directory named "src" and enter the following command: 
    javac GUI.java Controller.java Scheduler.java Show.java Usher.java

# Input Files

A selection of example input files are available in the directory named "example_input."  

Export the results of the doodle poll as an exel file: 
    https://help.doodle.com/customer/portal/articles/645357-how-do-i-export-a-poll-

Edit the excel file in the following ways:
    -The first line of the file should contain the event names/dates.  Omit all commas!
    -The second line of the file should contain the number of ushers needed to work the corresponding event, excluding alternates.
    -The remaining lines of the file should contain the ushers and their availability with "OK" used for events which they are available to usher and blank space used for events which they are not available to usher.
    -Make sure to also delete the last line of the file if it contains the counts for the availability of each show.
    -Remove the head ushers from the file.  They have to be scheduled manually.
    -If there are two or more events at the same time and an usher has indicated that they are available to work multiple of these shows, then you have to fix this.  The program does not determine if two events are at the same time and therefore might schedule an usher for two or more concurrent events.  Delete some of the usher's availability so that they are only available for one of the concurrent events.  I suggest to edit the availability of concurrent events so that each event has close to the same number of available ushers.  You might have to get a little creative with this.  If you come up with a better way of dealing with concurrent shows, let me know.

Export the edited excel file as a ".csv" file.

# Running

In the terminal navigate to the directory named "src."

To run the scheduler without the GUI, enter the following command in the terminal and include the path to the input file as the third argument: 
    java Controller path-of-input-file

To run the scheduler with the GUI, enter the following command in the terminal and proceed to select the input file with the graphical file selector:
    java GUI

# Output Files

Output files are stored in the directory named "output."

"scheduled_block.txt" contains the scheduled results in human-readable text for copying into an email. Remember to schedule the head ushers manually and check the schedule before sending it out!

"usher_details.txt" contains each ushers, availability, alternate shifts, and scheduled shifts in human-readable text.  This file might be useful to keep for future records.

# Tinkering 

Feel free to tinker with the source code.  The underlying "rule" is that the ushers with the least availability and the shows with the least available ushers to work them are scheduled first.  Here is some mock-psuedocode:

Read in the usher and show data from a file.  
    Each show stores how many ushers are needed to staff it.  The shows are stored in a list.

Each usher stores the shows that they are available to staff in a priority queue.  Shows with the least ushers available to staff them are given a higher priority.  The ushers are stored in a list.

Loop until no more ushers can be scheduled to a show:
    Insert all the ushers into a priority queue. Ushers with fewer scheduled shows have a higher priority.  If two ushers have the same number of shows, then the one with the fewer number of available shows has the highest priority.
        For each usher in the priority queue:
            Schedule a show for the usher.  Choose the show with the least ushers available to staff it.

Repeat the above loop to schedule the alternates.

Output two files:
    One file details the schedule in relation to shows.
    The other file details the schedule in relation to ushers.
