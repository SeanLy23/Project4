import java.awt.BorderLayout;
import java.awt.CheckboxGroup;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MesonetFrame extends JFrame
{

    private JPanel contentPane;
    private ButtonGroup stats;
    private MapData mapTester;
    private ArrayList<JCheckBox> checkboxes;
    private JTextField textFieldFileName;

    
    /**
     * Create the frame.
     */
    public MesonetFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 930, 530);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        
        //default file location
        JFileChooser j = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        j.setCurrentDirectory(workingDirectory);
        
        //open data file menu item
        JMenuItem mntmOpenDataFile = new JMenuItem("Open Data File");
        mntmOpenDataFile.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
        
            
            j.showOpenDialog(null);
            
            //prepare data once file has been chosen
            if(j.getSelectedFile()!=null)
            {   
                //display current opened file name for convenience
                textFieldFileName.setText(j.getSelectedFile().getName());
           
                mapTester = new MapData(j.getSelectedFile().getAbsolutePath(), j.getSelectedFile().getName());
            try
            {
                mapTester.parseFile();
            }
            catch (IOException e1)
            {
                //Auto-generated catch block
                e1.printStackTrace();
            }
            }
            
        }
        });
        mnFile.add(mntmOpenDataFile);
        
        //exit button
        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            });
        mnFile.add(mntmExit);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        stats = new ButtonGroup();
        //panel for parameter check boxes
        JPanel panelSelectors = new JPanel();
        contentPane.add(panelSelectors, BorderLayout.WEST);
        GridBagLayout gbl_panelSelectors = new GridBagLayout();
        gbl_panelSelectors.columnWidths = new int[] {0, 0, 0, 50};
        gbl_panelSelectors.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20};
        gbl_panelSelectors.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panelSelectors.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panelSelectors.setLayout(gbl_panelSelectors);
        
        JLabel lblNewLabel = new JLabel("Parameters");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panelSelectors.add(lblNewLabel, gbc_lblNewLabel);
        
        JLabel lblStatistics = new JLabel("Statistics");
        GridBagConstraints gbc_lblStatistics = new GridBagConstraints();
        gbc_lblStatistics.insets = new Insets(0, 0, 5, 0);
        gbc_lblStatistics.gridx = 2;
        gbc_lblStatistics.gridy = 0;
        panelSelectors.add(lblStatistics, gbc_lblStatistics);
        
        JCheckBox chckbxTAIR = new JCheckBox("TAIR");
        GridBagConstraints gbc_chckbxTAIR = new GridBagConstraints();
        gbc_chckbxTAIR.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxTAIR.gridx = 0;
        gbc_chckbxTAIR.gridy = 2;
        panelSelectors.add(chckbxTAIR, gbc_chckbxTAIR);
       
        
        JRadioButton rdbtnMinimum = new JRadioButton("MINIMUM");
        GridBagConstraints gbc_rdbtnMinimum = new GridBagConstraints();
        gbc_rdbtnMinimum.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnMinimum.gridx = 2;
        gbc_rdbtnMinimum.gridy = 2;
        panelSelectors.add(rdbtnMinimum, gbc_rdbtnMinimum);
        
        JCheckBox chckbxTA9M = new JCheckBox("TA9M");
        GridBagConstraints gbc_chckbxTA9M = new GridBagConstraints();
        gbc_chckbxTA9M.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxTA9M.gridx = 0;
        gbc_chckbxTA9M.gridy = 4;
        panelSelectors.add(chckbxTA9M, gbc_chckbxTA9M);
        
        JRadioButton rdbtnAverage = new JRadioButton("AVERAGE");
        GridBagConstraints gbc_rdbtnAverage = new GridBagConstraints();
        gbc_rdbtnAverage.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnAverage.gridx = 2;
        gbc_rdbtnAverage.gridy = 4;
        panelSelectors.add(rdbtnAverage, gbc_rdbtnAverage);
        
        JCheckBox chckbxSRAD = new JCheckBox("SRAD");
        GridBagConstraints gbc_chckbxSRAD = new GridBagConstraints();
        gbc_chckbxSRAD.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxSRAD.gridx = 0;
        gbc_chckbxSRAD.gridy = 6;
        panelSelectors.add(chckbxSRAD, gbc_chckbxSRAD);
        
        JRadioButton rdbtnMaximum = new JRadioButton("MAXIMUM");
        GridBagConstraints gbc_rdbtnMaximum = new GridBagConstraints();
        gbc_rdbtnMaximum.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnMaximum.gridx = 2;
        gbc_rdbtnMaximum.gridy = 6;
        panelSelectors.add(rdbtnMaximum, gbc_rdbtnMaximum);
        
        JCheckBox chckbxWspd = new JCheckBox("WSPD");
        GridBagConstraints gbc_chckbxWspd = new GridBagConstraints();
        gbc_chckbxWspd.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxWspd.gridx = 0;
        gbc_chckbxWspd.gridy = 8;
        panelSelectors.add(chckbxWspd, gbc_chckbxWspd);
        
        JCheckBox chckbxPres = new JCheckBox("PRES");
        GridBagConstraints gbc_chckbxPres = new GridBagConstraints();
        gbc_chckbxPres.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxPres.gridx = 0;
        gbc_chckbxPres.gridy = 10;
        panelSelectors.add(chckbxPres, gbc_chckbxPres);
        
        JPanel panelCommands = new JPanel();
        contentPane.add(panelCommands, BorderLayout.SOUTH);
        panelCommands.setLayout(new BoxLayout(panelCommands, BoxLayout.X_AXIS));
        
        JPanel panelOutPut = new JPanel();
        contentPane.add(panelOutPut, BorderLayout.CENTER);
        GridBagLayout gbl_panelOutPut = new GridBagLayout();
        gbl_panelOutPut.rowWeights = new double[]{0.0, 1.0};
        gbl_panelOutPut.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        gbl_panelOutPut.columnWidths = new int[] {100, 100, 100, 100, 100, 100};
        gbl_panelOutPut.rowHeights = new int[] {10, 300};
        panelOutPut.setLayout(gbl_panelOutPut);
        
        JLabel lblStation = new JLabel("Station");
        GridBagConstraints gbc_lblStation = new GridBagConstraints();
        gbc_lblStation.insets = new Insets(0, 0, 5, 5);
        gbc_lblStation.gridx = 0;
        gbc_lblStation.gridy = 0;
        panelOutPut.add(lblStation, gbc_lblStation);
        
        JLabel lblParameter = new JLabel("Parameter");
        GridBagConstraints gbc_lblParameter = new GridBagConstraints();
        gbc_lblParameter.insets = new Insets(0, 0, 5, 5);
        gbc_lblParameter.gridx = 1;
        gbc_lblParameter.gridy = 0;
        panelOutPut.add(lblParameter, gbc_lblParameter);
        
        JLabel lblStatistics_1 = new JLabel("Statistics");
        GridBagConstraints gbc_lblStatistics_1 = new GridBagConstraints();
        gbc_lblStatistics_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblStatistics_1.gridx = 2;
        gbc_lblStatistics_1.gridy = 0;
        panelOutPut.add(lblStatistics_1, gbc_lblStatistics_1);
        
        JLabel lblValue = new JLabel("Value");
        GridBagConstraints gbc_lblValue = new GridBagConstraints();
        gbc_lblValue.insets = new Insets(0, 0, 5, 5);
        gbc_lblValue.gridx = 3;
        gbc_lblValue.gridy = 0;
        panelOutPut.add(lblValue, gbc_lblValue);
        
        JLabel lblReportingStation = new JLabel("Reporting Stations");
        GridBagConstraints gbc_lblReportingStation = new GridBagConstraints();
        gbc_lblReportingStation.insets = new Insets(0, 0, 5, 5);
        gbc_lblReportingStation.gridx = 4;
        gbc_lblReportingStation.gridy = 0;
        panelOutPut.add(lblReportingStation, gbc_lblReportingStation);
        
        JLabel lblDate = new JLabel("Date");
        GridBagConstraints gbc_lblDate = new GridBagConstraints();
        gbc_lblDate.insets = new Insets(0, 0, 5, 0);
        gbc_lblDate.gridx = 5;
        gbc_lblDate.gridy = 0;
        panelOutPut.add(lblDate, gbc_lblDate);
        
        JTextArea textAreaStation = new JTextArea();
        GridBagConstraints gbc_textAreaStation = new GridBagConstraints();
        gbc_textAreaStation.insets = new Insets(0, 0, 0, 5);
        gbc_textAreaStation.fill = GridBagConstraints.BOTH;
        gbc_textAreaStation.gridx = 0;
        gbc_textAreaStation.gridy = 1;
        textAreaStation.setEditable(false);
        panelOutPut.add(textAreaStation, gbc_textAreaStation);
        
        JTextArea textAreaParameter = new JTextArea();
        GridBagConstraints gbc_textAreaParameter = new GridBagConstraints();
        gbc_textAreaParameter.insets = new Insets(0, 0, 0, 5);
        gbc_textAreaParameter.fill = GridBagConstraints.BOTH;
        gbc_textAreaParameter.gridx = 1;
        gbc_textAreaParameter.gridy = 1;
        textAreaParameter.setEditable(false);
        panelOutPut.add(textAreaParameter, gbc_textAreaParameter);
        
        JTextArea textAreaStatistics = new JTextArea();
        textAreaStatistics.setText("");
        GridBagConstraints gbc_textAreaStatistics = new GridBagConstraints();
        gbc_textAreaStatistics.insets = new Insets(0, 0, 0, 5);
        gbc_textAreaStatistics.fill = GridBagConstraints.BOTH;
        gbc_textAreaStatistics.gridx = 2;
        gbc_textAreaStatistics.gridy = 1;
        textAreaStatistics.setEditable(false);
        panelOutPut.add(textAreaStatistics, gbc_textAreaStatistics);
        
        JTextArea textAreaValue = new JTextArea();
        textAreaValue.setText("");
        GridBagConstraints gbc_textAreaValue = new GridBagConstraints();
        gbc_textAreaValue.insets = new Insets(0, 0, 0, 5);
        gbc_textAreaValue.fill = GridBagConstraints.BOTH;
        gbc_textAreaValue.gridx = 3;
        gbc_textAreaValue.gridy = 1;
        textAreaValue.setEditable(false);
        panelOutPut.add(textAreaValue, gbc_textAreaValue);
        
        JTextArea textAreaReportingStation = new JTextArea();
        GridBagConstraints gbc_textAreaReportingStation = new GridBagConstraints();
        gbc_textAreaReportingStation.insets = new Insets(0, 0, 0, 5);
        gbc_textAreaReportingStation.fill = GridBagConstraints.BOTH;
        gbc_textAreaReportingStation.gridx = 4;
        gbc_textAreaReportingStation.gridy = 1;
        textAreaReportingStation.setEditable(false);
        panelOutPut.add(textAreaReportingStation, gbc_textAreaReportingStation);
        
        JTextArea textAreaDate = new JTextArea();
        textAreaDate.setText("");
        GridBagConstraints gbc_textAreaDate = new GridBagConstraints();
        gbc_textAreaDate.fill = GridBagConstraints.BOTH;
        gbc_textAreaDate.gridx = 5;
        gbc_textAreaDate.gridy = 1;
        textAreaDate.setEditable(false);
        panelOutPut.add(textAreaDate, gbc_textAreaDate);
        
        JButton btnCalculate = new JButton("Calculate");
        panelCommands.add(btnCalculate);
        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //clear texts for each new calculation
                textAreaDate.setText("");
                textAreaStation.setText("");
                textAreaValue.setText("");
                textAreaReportingStation.setText("");
                textAreaStatistics.setText("");
                textAreaParameter.setText("");
                
                //show error message if file has not been opened
                if (j.getSelectedFile()==null)
                {
                    JOptionPane.showMessageDialog(null, "A file has not been chosen.");
                }
                
                // if minimum is asked for
                if (rdbtnMinimum.isSelected())
                {   //loop through check boxes and display for all checked boxes
                    for(JCheckBox checkBox: checkboxes)
                    {
                        if (checkBox.isSelected())
                        {
                           textAreaDate.setText(textAreaDate.getText() + mapTester.getStatistics(StatsType.MINIMUM, checkBox.getText()).getUTCDateTimeString() +"\n");
                           textAreaStation.setText(textAreaStation.getText() + mapTester.getStatistics(StatsType.MINIMUM, checkBox.getText()).getStid()+"\n");
                           textAreaValue.setText(textAreaValue.getText() + mapTester.getStatistics(StatsType.MINIMUM, checkBox.getText()).getValue() +"\n");
                           textAreaReportingStation.setText(textAreaReportingStation.getText() + mapTester.getStatistics(StatsType.MINIMUM, checkBox.getText()).getNumberOfReportingStations() +"\n");
                           textAreaStatistics.setText(textAreaStatistics.getText() + mapTester.getStatistics(StatsType.MINIMUM, checkBox.getText()).getStatType().name() +"\n");
                           textAreaParameter.setText(textAreaParameter.getText() + checkBox.getText() +"\n");
                        }
                    }
                }
                //if maximum is asked for
                else if (rdbtnMaximum.isSelected()) 
                {   //loop through check boxes and display for all checked boxes
                    for(JCheckBox checkBox: checkboxes)
                    {
                        if (checkBox.isSelected())
                        {
                           textAreaDate.setText(textAreaDate.getText() + mapTester.getStatistics(StatsType.MAXIMUM, checkBox.getText()).getUTCDateTimeString() +"\n");
                           textAreaStation.setText(textAreaStation.getText() + mapTester.getStatistics(StatsType.MAXIMUM, checkBox.getText()).getStid()+"\n");
                           textAreaValue.setText(textAreaValue.getText() + mapTester.getStatistics(StatsType.MAXIMUM, checkBox.getText()).getValue() +"\n");
                           textAreaReportingStation.setText(textAreaReportingStation.getText() + mapTester.getStatistics(StatsType.MAXIMUM, checkBox.getText()).getNumberOfReportingStations() +"\n");
                           textAreaStatistics.setText(textAreaStatistics.getText() + mapTester.getStatistics(StatsType.MAXIMUM, checkBox.getText()).getStatType().name() +"\n");
                           textAreaParameter.setText(textAreaParameter.getText() + checkBox.getText() +"\n");
                        }
                    }
                    
                }
                //if average is asked for
                else if (rdbtnAverage.isSelected())
                {
                    for(JCheckBox checkBox: checkboxes)
                    {
                        if (checkBox.isSelected())
                        {
                           textAreaDate.setText(textAreaDate.getText() + mapTester.getStatistics(StatsType.AVERAGE, checkBox.getText()).getUTCDateTimeString() +"\n");
                           textAreaStation.setText(textAreaStation.getText() + mapTester.getStatistics(StatsType.AVERAGE, checkBox.getText()).getStid()+"\n");
                           textAreaValue.setText(textAreaValue.getText() + mapTester.getStatistics(StatsType.AVERAGE, checkBox.getText()).getValue() +"\n");
                           textAreaReportingStation.setText(textAreaReportingStation.getText() + mapTester.getStatistics(StatsType.AVERAGE, checkBox.getText()).getNumberOfReportingStations() +"\n");
                           textAreaStatistics.setText(textAreaStatistics.getText() + mapTester.getStatistics(StatsType.AVERAGE, checkBox.getText()).getStatType().name() +"\n");
                           textAreaParameter.setText(textAreaParameter.getText() + checkBox.getText() +"\n");
                        }
                    }
                    
                }
                //show error for no chosen statistic
                else 
                {
                    JOptionPane.showMessageDialog(null,"No Statistics Type Chosen");
                }
                
            }
            
            });
        
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                System.exit(0);
            }
            
            });
        panelCommands.add(btnExit);
        
        
        //add to button group to make mutually exclusive
        stats.add(rdbtnMinimum);
        stats.add(rdbtnMaximum);
        stats.add(rdbtnAverage);
        
        //add checkbox to arraylist in order to make looping through easy
        checkboxes = new ArrayList <JCheckBox>();
        checkboxes.add(chckbxPres);
        checkboxes.add(chckbxWspd);
        checkboxes.add(chckbxSRAD);
        checkboxes.add(chckbxTA9M);
        checkboxes.add(chckbxTAIR);
        
        //label for current file being analyzed
        JLabel lblNewLabelCurrentFile = new JLabel("Current File");
        GridBagConstraints gbc_lblNewLabelCurrentFile = new GridBagConstraints();
        gbc_lblNewLabelCurrentFile.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabelCurrentFile.gridx = 0;
        gbc_lblNewLabelCurrentFile.gridy = 12;
        panelSelectors.add(lblNewLabelCurrentFile, gbc_lblNewLabelCurrentFile);
        
        //will be used to display current mdf file being analyzed.
        textFieldFileName = new JTextField();
        GridBagConstraints gbc_textFieldFileName = new GridBagConstraints();
        gbc_textFieldFileName.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldFileName.gridx = 2;
        gbc_textFieldFileName.gridy = 12;
        textFieldFileName.setEditable(false);
        panelSelectors.add(textFieldFileName, gbc_textFieldFileName);
        textFieldFileName.setColumns(10);
    }
    
   
    
    
   

}
