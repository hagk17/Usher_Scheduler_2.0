import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 * Simple GUI for file selection and scheduling button
 *
 * @author Johnny
 * @version (a version number or a date)
 */
public class GUI extends Application
{
    File inputFile;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Usher Scheduler 2.0");
 
        //File selector
        final FileChooser fileChooser = new FileChooser();
 
        final Button openButton = new Button("Select a file to read...");
        
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        inputFile = file;
                    }
                }
            });
            
        //schedule button
        final Button scheduleButton = new Button("Schedule");
        
        scheduleButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    if (inputFile != null) {
                        schedule(inputFile);
                    }
                }
            });
 
        //grid setup
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(openButton, 0, 0);
        GridPane.setConstraints(scheduleButton, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton, scheduleButton);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
    
     public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Method to schedule given an input file
     */
    private void schedule(File file)
    {
        String readFileName = file.getAbsolutePath();
        String printFileName="../output/scheduled_block.txt";
        String usherFileName = "../output/usher_details.txt";
        runScheduler(readFileName, printFileName, usherFileName);
    }
    
    /**
     * Method to run the scheduler given filenames for input and output files
     */
    public static void runScheduler(String readFileName, String printFileName, String usherFileName)
    {
        System.out.println();
        Scheduler s = new Scheduler();
        s.readFile(readFileName);
        s.scheduleWorking();
        s.scheduleAlternates();
        s.printSchedule(printFileName);
        s.printUsherDetails(usherFileName);
    }
}
