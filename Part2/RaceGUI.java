import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

public class RaceGUI extends JPanel {
    private JTextArea textArea;
    private Race race;
    private Horse horse1;
    private Horse horse2;
    private Horse horse3;
    private Color initialTrackColour;
    private JPanel horsePanel;
    private JTextArea horse1Field, horse2Field, horse3Field;
    private double balance;
    private JTextPane moneyField;
    private boolean raceStarted = false;
    private List<Bet> bets = new ArrayList<>();
    private JPanel bettingPanel;
    private JLabel horse1OddsLabel;
    private JLabel horse2OddsLabel;
    private JLabel horse3OddsLabel;

    private class Bet {
        int horseIndex;
        double betAmount;

        Bet(int horseIndex, double betAmount) {
            this.horseIndex = horseIndex;
            this.betAmount = betAmount;
        }
    }

    public RaceGUI() {
        Character[] horseSymbols = {'♘', '♕', '♔', '♖', '♗', '♙', '♚', '♛', '♜', '♝', '♞', '♟'};
        final double max_balance = 100.0;
        balance = max_balance;

        textArea = new JTextArea();
        textArea.setFont(new Font("monospaced", Font.PLAIN, 17));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setForeground(Color.WHITE);

        race = new Race(30, textArea);

        horse1 = new Horse('♘', "Horse 1", 0.4, race, 1 - 1);
        horse2 = new Horse('♕', "Horse 2", 0.5, race, 2 - 1);
        horse3 = new Horse('♔', "Horse 3", 0.6, race, 3 - 1);

        initialTrackColour = new Color(0x123456);
        setLayout(new BorderLayout());
        setBackground(initialTrackColour);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
        race = new Race(30, textArea);
        horsePanel = new JPanel();
        bettingPanel = new JPanel();

        horse1OddsLabel = new JLabel();
        horse2OddsLabel = new JLabel();
        horse3OddsLabel = new JLabel();

        horse1Field = new JTextArea();
        horse2Field = new JTextArea();
        horse3Field = new JTextArea();
        horsePanel.setLayout(new GridLayout(1, 3));
        horsePanel.add(horse1Field);
        horsePanel.add(horse2Field);
        horsePanel.add(horse3Field);

        JMenuBar menuBar = new JMenuBar();
        JMenu Customise = new JMenu("Customise Horses");
        JMenu Statistics = new JMenu("Statistics");
        JMenu Customise_Track = new JMenu("Customise Track");
        JMenuItem Customise_Track_Length = new JMenuItem("Change Length");
        JMenuItem Display_Statistics = new JMenuItem("Display Statistics");
        JMenuItem Display_Records = new JMenuItem("Display Records");
        Statistics.add(Display_Statistics);
        Statistics.add(Display_Records);
        Display_Statistics.setEnabled(false);
        Display_Records.setEnabled(false);

        JMenu Customise_Horse1 = new JMenu("Customise Horse 1");
        JMenuItem Customise_Horse1_Confidence = new JMenuItem("Change Confidence");
        JMenuItem Customise_Horse1_Name = new JMenuItem("Change Name");
        JMenuItem Customise_Horse1_Symbol = new JMenuItem("Change Symbol");
        Customise.add(Customise_Horse1);
        Customise_Horse1.add(Customise_Horse1_Confidence);
        Customise_Horse1.add(Customise_Horse1_Name);
        Customise_Horse1.add(Customise_Horse1_Symbol);

        JMenu Customise_Horse2 = new JMenu("Customise Horse 2");
        JMenuItem Customise_Horse2_Confidence = new JMenuItem("Change Confidence");
        JMenuItem Customise_Horse2_Name = new JMenuItem("Change Name");
        JMenuItem Customise_Horse2_Symbol = new JMenuItem("Change Symbol");
        Customise.add(Customise_Horse2);
        Customise_Horse2.add(Customise_Horse2_Confidence);
        Customise_Horse2.add(Customise_Horse2_Name);
        Customise_Horse2.add(Customise_Horse2_Symbol);

        JMenu Customise_Horse3 = new JMenu("Customise Horse 3");
        JMenuItem Customise_Horse3_Confidence = new JMenuItem("Change Confidence");
        JMenuItem Customise_Horse3_Name = new JMenuItem("Change Name");
        JMenuItem Customise_Horse3_Symbol = new JMenuItem("Change Symbol");
        Customise.add(Customise_Horse3);
        Customise_Horse3.add(Customise_Horse3_Confidence);
        Customise_Horse3.add(Customise_Horse3_Name);
        Customise_Horse3.add(Customise_Horse3_Symbol);

        menuBar.add(Customise);
        menuBar.add(Customise_Track);
        menuBar.add(Statistics);

        add(menuBar, BorderLayout.NORTH);
        Horse[] horses = {horse1, horse2, horse3};
        for (Horse horse : horses) {
            JButton betButton = new JButton("Bet on " + horse.getName());
            betButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    placeBet(horse);
                }
            });
            bettingPanel.add(betButton);
        }
        moneyField = new JTextPane();
        moneyField.setText("Balance: " + max_balance);
        moneyField.setEditable(false);
        bettingPanel.add(moneyField);
        add(bettingPanel, BorderLayout.WEST);

        //Horse 1
        Customise_Horse1_Confidence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (horse1 == null) {
                    horse1 = new Horse('♘', "horse 1", 0.4, race, 1 - 1);
                }
                double confidence = getConfidence("Enter confidence for horse 1:");
                horse1.setConfidence(confidence);
                saveHorseDataToFile();
            }
        });
        Customise_Horse1_Name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter name for horse 1:");
                horse1.setName(name);
            }
        });
        Customise_Horse1_Symbol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Character symbol = (Character) JOptionPane.showInputDialog(
                        null,
                        "Select a symbol for horse 1:",
                        "Change Symbol",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        horseSymbols,
                        horse1.getSymbol()
                );
                if (symbol != null) {
                    horse1.setSymbol(symbol);
                    saveHorseDataToFile();
                }
            }
        });

        //Horse 2
        Customise_Horse2_Confidence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (horse2 == null) {
                    horse2 = new Horse('♕', "horse 2", 0.5, race, 2 - 1);
                }
                double confidence = getConfidence("Enter confidence for horse 2:");
                horse2.setConfidence(confidence);
                saveHorseDataToFile();
            }
        });
        Customise_Horse2_Name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter name for horse 2:");
                horse2.setName(name);
            }
        });
        Customise_Horse2_Symbol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Character symbol = (Character) JOptionPane.showInputDialog(
                        null,
                        "Select a symbol for horse 2:",
                        "Change Symbol",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        horseSymbols,
                        horse2.getSymbol()
                );
                if (symbol != null) {
                    horse2.setSymbol(symbol);
                    saveHorseDataToFile();
                }
            }
        });

        //Horse 3
        Customise_Horse3_Confidence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (horse3 == null) {
                    horse3 = new Horse('♔', "horse 3", 0.6, race, 3 - 1);
                }
                double confidence = getConfidence("Enter confidence for horse 3:");
                horse3.setConfidence(confidence);
                saveHorseDataToFile();
            }
        });
        Customise_Horse3_Name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter name for horse 3:");
                horse3.setName(name);
            }
        });
        Customise_Horse3_Symbol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Character symbol = (Character) JOptionPane.showInputDialog(
                        null,
                        "Select a symbol for horse 3:",
                        "Change Symbol",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        horseSymbols,
                        horse3.getSymbol()
                );
                if (symbol != null) {
                    horse3.setSymbol(symbol);
                    saveHorseDataToFile();
                }
            }
        });
        JMenuItem chooseColour = new JMenuItem("Choose Track Background Colour");
        JMenuItem raceColour = new JMenuItem("Choose Race Colour");
        chooseColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(RaceGUI.this, "Choose Track Background Colour", getBackground());
                if (selectedColor != null) {
                    setBackground(selectedColor);
                }
            }
        });


        Customise_Track.add(Customise_Track_Length);
        Customise_Track_Length.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter new track length:");
                try {
                    int length = Integer.parseInt(input);
                    if (length < 10) {
                        JOptionPane.showMessageDialog(null, "Track length must be greater than 0");
                    } else {
                        if (race != null) {
                            race.setRaceLength(length);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });
        raceColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(RaceGUI.this, "Choose Race Colour", getForeground());
                if (selectedColor != null) {
                    textArea.setForeground(selectedColor);
                }
            }
        });
        Customise_Track.add(chooseColour);
        Customise_Track.add(raceColour);

        Display_Statistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });
        Display_Records.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRecords();
            }
        });


        JButton button = new JButton("Start race");
        add(button, BorderLayout.SOUTH);

        JButton button2 = new JButton("Reset Race");
        add(button2, BorderLayout.EAST);

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File file = new File("mapData.txt");
                if (file.exists()) {
                    file.delete();
                }
                resetRace();
                double confidence1 = getConfidence("Enter confidence for horse 1:");
                double confidence2 = getConfidence("Enter confidence for horse 2:");
                double confidence3 = getConfidence("Enter confidence for horse 3:");
                horse1.setConfidence(confidence1);
                horse2.setConfidence(confidence2);
                horse3.setConfidence(confidence3);
                saveHorseDataToFile();
                setBackground(initialTrackColour);
            }
        });

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        startRaceGUI();
                        Display_Statistics.setEnabled(true);
                        Display_Records.setEnabled(true);
                    }
                }).start();
            }
        });
    }

    private void raceEnded() {
        raceStarted = false;
        calculateRewards();
        enableBetButtons();

        JButton horse1BetButton = (JButton) bettingPanel.getComponent(0);
        JButton horse2BetButton = (JButton) bettingPanel.getComponent(1);
        JButton horse3BetButton = (JButton) bettingPanel.getComponent(2);
        updateOdds(horse1, horse1BetButton);
        updateOdds(horse2, horse2BetButton);
        updateOdds(horse3, horse3BetButton);
    }

    private void placeBet(Horse horse) {
        if (raceStarted) {
            JOptionPane.showMessageDialog(null, "Betting is closed. Race has already started.");
            return;
        }

        double bet = getBetAmount();
        if (bet > balance) {
            JOptionPane.showMessageDialog(null, "You do not have enough money to place this bet");
            return;
        } else if (bet < 0) {
            JOptionPane.showMessageDialog(null, "You cannot bet a negative amount");
            return;
        }

        balance -= bet;
        bets.add(new Bet(horse.getIndex(), bet));
        updateBalanceDisplay();
        JOptionPane.showMessageDialog(null, "Bet placed on " + horse.getName() + " for " + String.format("%.2f", bet));
    }

    private void disableBetButtons() {
        for (Component component : bettingPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton betButton = (JButton) component;
                betButton.setEnabled(false);
            }
        }
    }

    private void enableBetButtons() {
        for (Component component : bettingPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton betButton = (JButton) component;
                betButton.setEnabled(true);
            }
        }
    }

    private void calculateRewards() {
        String result = race.getRaceResult();
        Horse[] horses = {horse1, horse2, horse3};

        for (Bet bet : bets) {
            double betAmount = bet.betAmount;
            int horseIndex = bet.horseIndex;
            double confidence = horses[horseIndex].getConfidence();
            double winRatio = horses[horseIndex].getWinRatio();
            double odds = confidence / (winRatio + 0.0001);
            double winnings = betAmount * odds;

            if (winnings > 0 && result.contains(String.valueOf(horseIndex + 1))) {
                balance = balance + winnings; // Only add the winnings, not the original bet amount
                JOptionPane.showMessageDialog(null, "You have won " + String.format("%.2f", winnings) + " on horse " + (horseIndex + 1) + "!");
            } else {
                JOptionPane.showMessageDialog(null, "Your bet on horse " + (horseIndex + 1) + " has lost.");
            }
        }
        bets.clear();
        updateBalanceDisplay();
    }

    private double getBetAmount() {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog("Enter bet amount:");
                if (input == null) {
                    return 0.0;
                }
                double bet = Double.parseDouble(input.trim());
                return bet;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }
        }
    }

    private void updateBalanceDisplay() {
        String formattedBalance = String.format("%.2f", balance);
        moneyField.setText("Balance: " + formattedBalance);
    }


    private void showRecords() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Race Records");
        dialog.setSize(370, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);

        JPanel recordsPanel = new JPanel(new GridLayout(3, 1));

        JTextArea horse1Record = new JTextArea(horse1.getName() + ":\n Wins: " + horse1.getWins() + "\n Losses: " + horse1.getLosses() + "\n Win Ratio: " + String.format("%.2f", horse1.getWinRatio()));
        JTextArea horse2Record = new JTextArea(horse2.getName() + ":\n Wins: " + horse2.getWins() + "\n Losses: " + horse2.getLosses() + "\n Win Ratio: " + String.format("%.2f", horse2.getWinRatio()));
        JTextArea horse3Record = new JTextArea(horse3.getName() + ":\n Wins: " + horse3.getWins() + "\n Losses: " + horse3.getLosses() + "\n Win Ratio: " + String.format("%.2f", horse3.getWinRatio()));

        horse1Record.setFont(new Font("Arial", Font.PLAIN, 15));
        horse2Record.setFont(new Font("Arial", Font.PLAIN, 15));
        horse3Record.setFont(new Font("Arial", Font.PLAIN, 15));

        horse1Record.setEditable(false);
        horse2Record.setEditable(false);
        horse3Record.setEditable(false);

        recordsPanel.add(horse1Record);
        recordsPanel.add(horse2Record);
        recordsPanel.add(horse3Record);

        dialog.add(recordsPanel);

        dialog.setVisible(true);
    }

    private void startRace() {
        raceStarted = true;
        disableBetButtons();
        race.startRace();
    }

    private void showStatistics() {
        JDialog dialog = new JDialog();
        String DNF = "DNF";
        dialog.setTitle("Race Statistics");
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);

        JPanel statsPanel = new JPanel(new GridLayout(3, 1));

        long startTime = race.getStartTime();

        long horse1FinishTime = horse1.getFinishTime();
        long horse2FinishTime = horse2.getFinishTime();
        long horse3FinishTime = horse3.getFinishTime();
        double horse1Time;
        double horse2Time;
        double horse3Time;

        if (horse1FinishTime >= 0) {
            horse1Time = (horse1FinishTime - startTime) / 1000.0;
        } else {
            horse1Time = -1;
        }

        if (horse2FinishTime >= 0) {
            horse2Time = (horse2FinishTime - startTime) / 1000.0;
        } else {
            horse2Time = -1;
        }

        if (horse3FinishTime >= 0) {
            horse3Time = (horse3FinishTime - startTime) / 1000.0;
        } else {
            horse3Time = -1;
        }

        double horse1Distance = horse1.getDistanceTravelled();
        double horse2Distance = horse2.getDistanceTravelled();
        double horse3Distance = horse3.getDistanceTravelled();
        double horse1Speed;
        double horse2Speed;
        double horse3Speed;

        if (horse1Time >= 0) {
            horse1Speed = horse1Distance / horse1Time;
        } else {
            horse1Speed = -1;
        }

        if (horse2Time >= 0) {
            horse2Speed = horse2Distance / horse2Time;
        } else {
            horse2Speed = -1;
        }

        if (horse3Time >= 0) {
            horse3Speed = horse3Distance / horse3Time;
        } else {
            horse3Speed = -1;
        }

        double horse1TimeRounded = Math.round(horse1Time * 100.0) / 100.0;
        double horse2TimeRounded = Math.round(horse2Time * 100.0) / 100.0;
        double horse3TimeRounded = Math.round(horse3Time * 100.0) / 100.0;

        double horse1SpeedRounded = Math.round(horse1Speed * 100.0) / 100.0;
        double horse2SpeedRounded = Math.round(horse2Speed * 100.0) / 100.0;
        double horse3SpeedRounded = Math.round(horse3Speed * 100.0) / 100.0;

        JTextArea horse1Label = new JTextArea(horse1.getName() + ":\nRace time- " + (horse1Time >= 0 ? horse1TimeRounded + " seconds" : DNF) + "\nAverage speed- " + (horse1Speed >= 0 ? horse1SpeedRounded + " m/s" : "N/A"));
        JTextArea horse2Label = new JTextArea(horse2.getName() + ":\nRace time- " + (horse2Time >= 0 ? horse2TimeRounded + " seconds" : DNF) + "\nAverage speed- " + (horse2Speed >= 0 ? horse2SpeedRounded + " m/s" : "N/A"));
        JTextArea horse3Label = new JTextArea(horse3.getName() + ":\nRace time- " + (horse3Time >= 0 ? horse3TimeRounded + " seconds" : DNF) + "\nAverage speed- " + (horse3Speed >= 0 ? horse3SpeedRounded + " m/s" : "N/A"));
        horse1Label.setFont(new Font("Arial", Font.PLAIN, 15));
        horse2Label.setFont(new Font("Arial", Font.PLAIN, 15));
        horse3Label.setFont(new Font("Arial", Font.PLAIN, 15));

        horse1Label.setEditable(false);
        horse2Label.setEditable(false);
        horse3Label.setEditable(false);


        statsPanel.add(horse1Label);
        statsPanel.add(horse2Label);
        statsPanel.add(horse3Label);

        dialog.add(statsPanel);

        dialog.setVisible(true);
    }

    private double getConfidence(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message);
                if (input == null) {
                    return 0.0;
                }
                double confidence = Double.parseDouble(input.trim());
                if (confidence < 0 || confidence > 1) {
                    JOptionPane.showMessageDialog(null, "Confidence must be between 0 and 1");
                    continue;
                }
                return confidence;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }
        }
    }

    public void startRaceGUI() {
        if (horsePanel != null && horsePanel.getParent() == RaceGUI.this) {
            remove(horsePanel);
            revalidate();
            repaint();
        }
        if (race == null) {
            race = new Race(30, textArea);
        }
        if (horse1 == null) {
            horse1 = new Horse('♘', "horse 1", 0.4, race, 1 - 1);
        }
        if (horse2 == null) {
            horse2 = new Horse('♕', "horse 2", 0.5, race, 2 - 1);
        }
        if (horse3 == null) {
            horse3 = new Horse('♔', "horse 3", 0.6, race, 3 - 1);
        }
        horse1.setSymbol('♘');
        horse2.setSymbol('♕');
        horse3.setSymbol('♔');

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        loadHorseDataFromFile();

        startRace();
        raceEnded();
        saveHorseDataToFile();
    }
    private void updateOdds(Horse horse, JButton betButton) {
        double winRatio = horse.getWinRatio();
        if (winRatio == 0) {
            betButton.setText("Bet on " + horse.getName() + " (Odds: N/A)");
        }
        else{
            int odds = (int) Math.round(1 / winRatio);
            betButton.setText("Bet on " + horse.getName() + " (Odds: " + odds + "/1)");
        }
    }

    private void resetRace() {
        if (raceStarted) {
            JOptionPane.showMessageDialog(null, "Cannot reset race while it's ongoing.");
            return;
        }
        raceEnded();
        enableBetButtons();
        textArea.setText("");
        race = new Race(30, textArea);
        horse1 = new Horse('♘', "horse 1", 0.4, race, 1 - 1);
        horse2 = new Horse('♕', "horse 2", 0.5, race, 2 - 1);
        horse3 = new Horse('♔', "horse 3", 0.6, race, 3 - 1);

        if (horsePanel.getParent() == RaceGUI.this) {
            remove(horsePanel);
            revalidate();
            repaint();
        }
    }

    private void saveHorseDataToFile() {
        File file = new File("mapData.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.valueOf(horse1.getConfidence()));
            writer.newLine();
            writer.write(String.valueOf(horse2.getConfidence()));
            writer.newLine();
            writer.write(String.valueOf(horse3.getConfidence()));
            writer.newLine();
            writer.write(horse1.getSymbol());
            writer.newLine();
            writer.write(horse2.getSymbol());
            writer.newLine();
            writer.write(horse3.getSymbol());
        } catch (IOException j) {
            System.out.println("Error writing to file");
        }
    }

    private void loadHorseDataFromFile() {
        File file = new File("mapData.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                if ((line = reader.readLine()) != null) {
                    double confidence1 = Double.parseDouble(line);
                    horse1.setConfidence(confidence1);
                }
                if ((line = reader.readLine()) != null) {
                    double confidence2 = Double.parseDouble(line);
                    horse2.setConfidence(confidence2);
                }
                if ((line = reader.readLine()) != null) {
                    double confidence3 = Double.parseDouble(line);
                    horse3.setConfidence(confidence3);
                }
                if ((line = reader.readLine()) != null) {
                    char symbol1 = line.charAt(0);
                    horse1.setSymbol(symbol1);
                }
                if ((line = reader.readLine()) != null) {
                    char symbol2 = line.charAt(0);
                    horse2.setSymbol(symbol2);
                }
                if ((line = reader.readLine()) != null) {
                    char symbol3 = line.charAt(0);
                    horse3.setSymbol(symbol3);
                }
            } catch (IOException | NumberFormatException ex) {
                System.out.println("Error reading from file: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setVisible(true);
        RaceGUI gui = new RaceGUI();
        frame.add(gui);
    }
}

