
/**
 * Write a description of class Horse here.
 *
 * @author Ahmed Ewas
 * @version 1
 */
public class Horse
{
    //Fields of class Horse
    private String horseName;
    private char horseSymbol;
    private int distance_travelled;
    private boolean fallen;
    private double horseConfidence;
<<<<<<< HEAD:Part1/Horse.java
=======
    private long finishTime;
    private Race race;
    private int wins;
    private int loses;
    private int index;
>>>>>>> gui-development:Part2/Horse.java

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    public Horse(char horseSymbol, String horseName, double horseConfidence, Race race, int index)
    {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.distance_travelled = 0;
        this.fallen = false;
    }



        this.race = race;
        this.index = index;
    }

    //Other methods of class Horse
    public void fall()
    {
        fallen = true;
        finishTime = System.currentTimeMillis();
    }
    public int getIndex() {
        return index;
    }

    public double getConfidence()
    {
        return horseConfidence;
    }

    public int getDistanceTravelled()
    {
        return distance_travelled;
    }

    public String getName()
    {
        return horseName;
    }
    public void setName(String name) {
        horseName = name;
    }

    public char getSymbol()
    {
        return horseSymbol;
    }

    public void goBackToStart()
    {
        distance_travelled = 0;
        fallen = false;
    }

    public boolean hasFallen()
    {
        return fallen;
    }

    public void moveForward()
    {
        distance_travelled = distance_travelled + 1;
        if(distance_travelled == race.getRaceLength())
        {
            finishTime = System.currentTimeMillis();
        }
    }
    public long getFinishTime()
    {
        return finishTime;
    }

    public void setConfidence(double newConfidence) {
        if (newConfidence < 0) {
            this.horseConfidence = 0;
        } else if (newConfidence > 1) {
        } else if (newConfidence >= 1) {
            this.horseConfidence = 1;
        } else {
            this.horseConfidence = newConfidence;
        }
    }

    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }
    public static void main(String[] args)
    {
        Horse horse = new Horse('*', "ahmed", 0.2);
        //Test 1
        horse.moveForward();
        horse.moveForward();
        System.out.println(horse.getDistanceTravelled()+"m");
        horse.goBackToStart();
        System.out.println(horse.getDistanceTravelled()+"m");

        System.out.println();
        //Test 2
        horse.setConfidence(1.8);
        horse.setSymbol('#');
        System.out.println(horse.getConfidence());
        System.out.println(horse.getSymbol());

        System.out.println();
        //Test 3
        System.out.println(horse.hasFallen());
        horse.fall();
        System.out.println(horse.hasFallen());
    public void increaseWins()
    {
        wins++;
    }
    public void increaseLoses()
    {
        loses++;
    }
    public double getWinRatio() {
        int totalRaces = wins + loses;
        if (totalRaces == 0) {
            return 0;
        }
        return (double) wins / totalRaces;
    }
    public int getWins()
    {
        return wins;
    }
    public int getLosses()
    {
        return loses;
    }
    public static void main(String[] args)
    {

    }
}
