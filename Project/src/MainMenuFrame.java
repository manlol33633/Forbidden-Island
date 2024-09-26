import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenuFrame extends JFrame implements ActionListener {
    private JLabel screenLabel, inputLabel, outputLabel, inputBaseLabel;
    private JTextField inputField;
    private JScrollPane scrollPane;
    private JTextArea outputArea;
    private JCheckBox binCheck, hexCheck, decCheck;
    private JButton printButton;
    private JRadioButton dec_irbutton, bin_irbutton, hex_irbutton;
    private ButtonGroup  input_radioGroup;
    private String binIn, hexIn;
    private int decimal, baseIn;
    private boolean wantHex, wantBin, wantDec;
    private boolean hexInput, binInput, decInput;
    private int number;

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 960;
    public MainMenuFrame(String framename){
        super(framename);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        //JButton btn = new JButton("Click Here");
        //btn.setBounds(50, 100, 95, 30);
        //add(btn);
        setResizable(false);
        add(new GamePanel());
        //setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
