package eecs40;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorForm {
    public JPanel panel1;
    private JTextField textField1;
    private JButton a0Button;
    private JButton button2;
    private JButton button3;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton cButton;
    private JButton backspaceButton;
    private JButton button15;
    private JButton button16;
    private JButton button17;
    private JButton button18;
    private JButton button19;
    private JButton button20;
    private JButton modButton1;
    private JButton sinButton;
    private JButton cosButton;
    private JButton tanButton;
    private JButton logButton;
    private JButton lnButton;
    private JButton sqrtButton;
    private JButton factButton;
    private JRadioButton exprRadioButton;
    private JRadioButton simpleRadioButton;
    private ExprCalculator calc = new ExprCalculator();

    public CalculatorForm() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calc.acceptInput(((JButton)e.getSource()).getText());
                textField1.setText(calc.getDisplayString());
            }
        };

        exprRadioButton.addActionListener(listener);
        simpleRadioButton.addActionListener(listener);
        modButton1.addActionListener(listener);
        sinButton.addActionListener(listener);
        cosButton.addActionListener(listener);
        tanButton.addActionListener(listener);
        logButton.addActionListener(listener);
        lnButton.addActionListener(listener);
        sqrtButton.addActionListener(listener);
        factButton.addActionListener(listener);
        a0Button.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);
        a7Button.addActionListener(listener);
        a8Button.addActionListener(listener);
        a9Button.addActionListener(listener);
        a4Button.addActionListener(listener);
        a5Button.addActionListener(listener);
        a6Button.addActionListener(listener);
        a1Button.addActionListener(listener);
        a2Button.addActionListener(listener);
        a3Button.addActionListener(listener);
        cButton.addActionListener(listener);
        backspaceButton.addActionListener(listener);
        button15.addActionListener(listener);
        button16.addActionListener(listener);
        button17.addActionListener(listener);
        button18.addActionListener(listener);
        button19.addActionListener(listener);
        button20.addActionListener(listener);
    }
}
