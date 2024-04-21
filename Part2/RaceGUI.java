import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class RaceGUI extends JPanel {
    private JTextArea textArea;
    private Race race;
    private Horse horse1;
    private Horse horse2;
    private Horse horse3;

    public RaceGUI() {
        Character[] horseSymbols = {'♘', '♕','♔', '♖', '♗', '♙', '♚', '♛', '♜', '♝', '♞','♟'};

        setLayout(new BorderLayout());
        setBackground(new Color(0x123456));

        textArea = new JTextArea();
        textArea.setFont(new Font("monospaced", Font.PLAIN, 17));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu Customise = new JMenu("Customise Horses");

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
        add(menuBar, BorderLayout.NORTH);

        //Horse 1
        Customise_Horse1_Confidence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                        horse1.getSymbol() // default selection is the current symbol
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
                        horse2.getSymbol() // default selection is the current symbol
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
                        horse3.getSymbol() // default selection is the current symbol
                );
                if (symbol != null) {
                    horse3.setSymbol(symbol);
                    saveHorseDataToFile();
                }
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
            }
        });

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        startRaceGUI();
                    }
                }).start();
            }
        });
    }

    private double getConfidence(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message);
                if (input == null) {
                    // Handle the case when input dialog is canceled
                    // Return a default value or take appropriate action
                    return 0.0; // For example, returning 0.0 as default confidence
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

    private void startRaceGUI() {
        if (race == null) {
            race = new Race(30, textArea);
        }
        race = new Race(30, textArea);
        if (horse1 == null) {
            horse1 = new Horse('♘', "horse 1", 0.4);
        }
        if (horse2 == null) {
            horse2 = new Horse('♕', "horse 2", 0.5);
        }
        if (horse3 == null) {
            horse3 = new Horse('♔', "horse 3", 0.6);
        }
        // Set symbols for horse objects
        horse1.setSymbol('♘');
        horse2.setSymbol('♕');
        horse3.setSymbol('♔');

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        loadHorseDataFromFile();

        race.startRace();
        saveHorseDataToFile();
    }

    private void resetRace() {
        textArea.setText(" ");
        race = null;
        horse1 = new Horse('♘', "horse 1", 0.4);
        horse2 = new Horse('♕', "horse 2", 0.5);
        horse3 = new Horse('♔', "horse 3", 0.6);
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
