import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private HashMap<Horse, Double> map = new HashMap<>();
    private JTextArea textArea;
    private long startTime;
    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, JTextArea textArea)
    {
        this.raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
        this.textArea = textArea;
    }
    public HashMap<Horse, Double> getMap()
    {
        return this.map;
    }
    public void setRaceLength(int length) {
        this.raceLength = length;
    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            lane1Horse = theHorse;
        }
        else if (laneNumber == 2)
        {
            lane2Horse = theHorse;
        }
        else if (laneNumber == 3)
        {
            lane3Horse = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        startTime = System.currentTimeMillis();
        boolean finished = false;
        String winner = null;
        //reset all the lanes (all horses not fallen and back to 0).
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();
        while (!finished)
        {
            //move each horse
            boolean moved1 = moveHorse(lane1Horse);
            boolean moved2 = moveHorse(lane2Horse);
            boolean moved3 = moveHorse(lane3Horse);

            updateGUI();
            //print the race positions
            printRace();

            //if any of the three horses has won the race is finished
            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
            {
                finished = true;
            }
            if (raceWonBy(lane1Horse)) {
                winner = lane1Horse.getName();
                lane1Horse.setConfidence(lane1Horse.getConfidence() + 0.1);
                lane1Horse.increaseWins();
            } else if (raceWonBy(lane2Horse)) {
                winner = lane2Horse.getName();
                lane2Horse.setConfidence(lane2Horse.getConfidence() + 0.1);
                lane2Horse.increaseWins();
            } else if (raceWonBy(lane3Horse)) {
                winner = lane3Horse.getName();
                lane3Horse.setConfidence(lane3Horse.getConfidence() + 0.1);
                lane3Horse.increaseWins();
            }
            if(lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen())
            {
                //System.out.println("All horses have fallen. ");
                //System.out.println("No winner has been declared. ");
                lane1Horse.setConfidence(lane1Horse.getConfidence() - 0.1);
                lane2Horse.setConfidence(lane2Horse.getConfidence() - 0.1);
                lane3Horse.setConfidence(lane3Horse.getConfidence() - 0.1);
                map.put(lane1Horse, lane1Horse.getConfidence());
                map.put(lane2Horse, lane2Horse.getConfidence());
                map.put(lane3Horse, lane3Horse.getConfidence());
                lane1Horse.increaseLoses();
                lane2Horse.increaseLoses();
                lane3Horse.increaseLoses();
                return;
            }
            if(finished)
            {
                if(lane1Horse.hasFallen())
                {
                    lane1Horse.setConfidence(lane1Horse.getConfidence() - 0.1);
                }
                if(lane2Horse.hasFallen())
                {
                    lane2Horse.setConfidence(lane2Horse.getConfidence() - 0.1);
                }
                if(lane3Horse.hasFallen())
                {
                    lane3Horse.setConfidence(lane3Horse.getConfidence() - 0.1);
                }
                if(!raceWonBy(lane1Horse))
                {
                    lane1Horse.increaseLoses();
                }
                if(!raceWonBy(lane2Horse))
                {
                    lane2Horse.increaseLoses();
                }
                if(!raceWonBy(lane3Horse))
                {
                    lane3Horse.increaseLoses();
                }
            }
            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
        //System.out.println("The winner is " + winner);
        map.put(lane1Horse, lane1Horse.getConfidence());
        map.put(lane2Horse, lane2Horse.getConfidence());
        map.put(lane3Horse, lane3Horse.getConfidence());

    }
    public long getStartTime() {
        return startTime;
    }
    public int getRaceLength() {
        return raceLength;
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */

    private boolean moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen

        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            double rand = Math.random();
            if (rand < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                updateGUI();
            }
            else if (rand < theHorse.getConfidence())
            {
                theHorse.moveForward();
                updateGUI();
                return true;
            }

        }
        return false;
    }

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public String getRaceResult() {
        StringBuilder result = new StringBuilder();

        if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
            result.append("All horses have fallen. No winner has been declared.\n");
        } else {
            if (raceWonBy(lane1Horse)) {
                result.append("The winner is ").append(lane1Horse.getName()).append("\n");
            } else if (raceWonBy(lane2Horse)) {
                result.append("The winner is ").append(lane2Horse.getName()).append("\n");
            } else if (raceWonBy(lane3Horse)) {
                result.append("The winner is ").append(lane3Horse.getName()).append("\n");
            }
        }

        return result.toString();
    }
    private void updateGUI()
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StringBuilder raceString = new StringBuilder();

                raceString.append(multiplePrint('=', raceLength + 3)).append("\n");
                raceString.append(printLane(lane1Horse)).append(" ").append(lane1Horse.getName()).append(" (Current confidence: ").append(String.format("%.1f", lane1Horse.getConfidence())).append(")\n");
                raceString.append(printLane(lane2Horse)).append(" ").append(lane2Horse.getName()).append(" (Current confidence: ").append(String.format("%.1f", lane2Horse.getConfidence())).append(")\n");
                raceString.append(printLane(lane3Horse)).append(" ").append(lane3Horse.getName()).append(" (Current confidence: ").append(String.format("%.1f", lane3Horse.getConfidence())).append(")\n");
                raceString.append(multiplePrint('=', raceLength + 3)).append("\n");

                if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen()) {
                    raceString.append("All horses have fallen. No winner has been declared.\n");
                } else if (raceWonBy(lane1Horse)) {
                    raceString.append("The winner is ").append(lane1Horse.getName()).append("\n");
                } else if (raceWonBy(lane2Horse)) {
                    raceString.append("The winner is ").append(lane2Horse.getName()).append("\n");
                } else if (raceWonBy(lane3Horse)) {
                    raceString.append("The winner is ").append(lane3Horse.getName()).append("\n");
                }

                textArea.setText(raceString.toString());
            }
        });
    }

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        //System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        //System.out.println();

        printLane(lane1Horse);
        // System.out.print(" " + lane1Horse.getName()+" (Current confidence: "+lane1Horse.getConfidence()+")");
        //System.out.println();

        printLane(lane2Horse);
        //System.out.print(" " + lane2Horse.getName()+" (Current confidence: "+lane2Horse.getConfidence()+")");
        //System.out.println();

        printLane(lane3Horse);
        //System.out.print(" " + lane3Horse.getName()+" (Current confidence: "+lane3Horse.getConfidence()+")");
        //System.out.println();

        multiplePrint('=',raceLength+3); //bottom edge of track
        //System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private String printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        StringBuilder laneString = new StringBuilder();
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //print a | for the beginning of the lane
        laneString.append('|');

        //print the spaces before the horse
        laneString.append(multiplePrint(' ',spacesBefore));

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            laneString.append('❌');
        }
        else
        {
            laneString.append(theHorse.getSymbol());
        }

        //print the spaces after the horse
        laneString.append(multiplePrint(' ',spacesAfter));

        //print the | for the end of the track
        laneString.append('|');
        return laneString.toString();
    }


    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private String multiplePrint(char aChar, int times)
    {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < times)
        {
            result.append(aChar);
            i = i + 1;
        }
        return result.toString();
    }
    public static void main(String[] args)
    {

    }
}