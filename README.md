# Horse Race Simulator 
## Intro
This program simulates a horse race. Each horse has a confidence value, the higher the confidence the more likely it is for the horse to fall. 
## Part 1
To run the race for Part 1:
1. Open the Horse and Race file.
2. Create an instance of the Race and in the constructor pass in an integer that will represent the length of the race (in meters).
3. Create 3 horse instances and pass a character, string, and double to represent the symbol, name, and confidence respectively.
4. After that, make sure to use the addHorse method that belongs to the race class to add each horse created to the race.
5. Similar to what is in the main method, make sure to include a file writer that writes the horse's confidence to a file based on what is in the map. Also include a file reader that reads from that file the values of confidence for each horse.
6. Set the confidence for each horse using the values obtained while reading the file. This will mean when the horse wins its confidence is increased by 0.1 and if it loses it decreases by 0.1.
7. Finally, run the Race class.

## Part 2
1. Open the Horse, Race, and RaceGUI files.
2. Create a JFrame.
3. Set the program to terminate when we exit from the frame.
4. Set a size for the frame and make it visible.
5. Then create an instance of RaceGUI and add that instance to the frame.
6. You can then run the file, you can start the race from the bottom button, or place a bet from the left or reset the race from the right. Additionally, you can customize the horse or track and view statistics from the menu.
7. Initial horses have already been set but you can change these by customizing each horse to your liking.
8. By resetting the race, the confidence values and any other customizations made after the file was run will be reset.
9. Statistics can be viewed after a race has ended.
10. As for the odds, it will appear as N/A if that horse has a win ratio of 0.