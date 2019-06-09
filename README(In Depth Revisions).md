# Usher_Scheduler_2.0

Created by Johnny Gossick '18 
README edited by Kathryn Haglich '19 (June 2019)

Hello fellow user! Welcome to the Usher Scheduler. This repository contains a program used to schedule ushering shifts for the performances at Lafayette College's William Center for the Arts.  

A note of caution for the user: it is HIGHLY ADVISED that the results should be examined by the user BEFORE sending the schedule to the ushers. The program is merely a tool to make efficiently an initial schedule, but human eyes and logic are necessary to ensure that no problems arise from a blind acceptance of output results. 

You've successfully downloaded the package Usher_Scheduler_2.0 from GitHub and are now ready for full use of the program. Be sure to take note as to where on your computer the package is being stored so that folders can be easily navigated and files can be found by both you and the computer. 

To make things easy, I've included example code as a model for how the program works. Going forward, please be aware that certain files/folders/location names may differ from yours. I will note where these occur and where you should fill in the appropriate information for your use. (Additionally, these examples are done on a Mac and may differ on other operating systems.) 



##########Building ##########
1) Open your terminal 

2) Before you begin, make sure you are running Java Version 1.8 or something. You can determine this by typing in the command line:
 
		java -version 
		
If you do not have 1.8 or something, continue to the next building steps. If you get errors, then come back to this step and ask Google or a CS Major for help. 

2) Navigate to the directory named "src"

	EXAMPLE: cd Desktop/Usher_Scheduler_2.0/src

"Desktop" is where I store the file for "Usher_Scheduler_2.0". This will change depending on where you keep the file. Remember that you need to include the entire file path from your home directory in order for the computer to find the file.

3) Enter the following command:

	javac GUI.java Controller.java Scheduler.java Show.java Usher.java
    
And honestly, sometimes if this doesn't work you can possibly continue to run the program using the non-GUI approach (Step 2a under Running). If you're really having trouble though, ask a CS major for help. 

##########Input Files##########

1) Export the results of the Doodle Poll as an Excel File. If you need help with this, the following website should be of some assistance: 

	https://help.doodle.com/hc/en-us/articles/360012048094-How-do-I-export-a-poll-
	
Now we need to clean the data for the algorithm to run properly. A selection of example input files are available in the directory named "example_input."  

2) The first row should contain event names, dates and times. DO NOT INCLUDE COMMAS (It'll mess up the file format later.) 

3) The second row should contain the number of ushers needed to work the corresponding event, excluding alternates.

4) The remaining rows should contain the ushers and their availability with "OK" used for events which they are available to usher and blank space used for events which they are not available to usher.

5) Remove the head ushers from the file. You'll schedule these manually. 

6) The last row of the file should be deleted f it contains the counts for the availability of each show. 

7) You might run into the situation where there are two or more events at the same time and an usher has indicated that they are available to work multiple of these shows. The program does not determine if two events are at the same time and therefore might schedule an usher for two or more concurrent events. One solution to this is to delete some the usher's availability in the Excel doc so that they are only available for one of the concurrent events. It is suggested that you edit the availability of concurrent events so that each event has close to the same number of available ushers.  
	This check can also be completed once the schedule is made and BEFORE it is sent to the ushers. 
	There are probably other ways of doing this too - you might have to get a little creative with this. And if you think you can implement this in the code itself - have fun. 
	
8) When saving the Excel Document, save as a ".csv" file 
	File > Save As > File Format > CSV 

Again, be aware as to where you saved your input file - you need to know the path to this when it 



##########Running##########

In the terminal navigate to the directory named "src."

1) Again navigate to the directory named "src" (Same thing as Step 2 in Building) 

2a) (This is the easier way to do it, if you can.) To run without the GUI, enter the following command in the terminal and include the absolute path to the input file as the third argument: 

	java Controller path-of-input-file 
    
 EXAMPLE:  java Controller ~/Desktop/UsheringBlock.csv
 
You must have the "~/" in your argument. "Desktop" should be replaced by the path to your input file, and "UsheringBlock.csv" should be replaced with your input file name.  

2b) To run without the GUI, enter the following command in the terminal and proceed to select the input file with the graphical file selector:

	java GUI
	
	

##########Output Files##########

You can find the output files in the directory named "output" in the Ushering_Scheduler_2.0 program. 

"scheduled_block.txt" contains the scheduled results in human-readable text for copying into an email. 
Remember to schedule the head ushers manually and check the schedule for mistakes before sending it out!

"usher_details.txt" contains the following data for each usher: 1) availability, 2) alternate shifts, and 3) scheduled shifts in human-readable text.  This file might be useful to keep for future records and data analysis.

You did it! Yay! 



##########Tinkering##########
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
