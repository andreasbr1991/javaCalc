/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaCalc;

import java.awt.BorderLayout;
import static java.awt.Color.black;
import static java.awt.Color.white;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;

/**
 *
 * @author Andreas Bremholm Rosenlund
 */
public final class Calculator {
    JFrame calcFrame;
    JTextField monitor;
    JPanel mainPanel;
    JPanel calcGrid;
    JButton[] buttons;
    String[] buttonsText ={"<-","C","+/-","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","=","≈"};
    String res;
    String operator;
    ArrayList<Double> vals = new ArrayList<>();
    ArrayList<String> ops = new ArrayList<>();
    Double currRes;
    boolean resCheck = false;
    String op=null;
    Double val=null;
    int idxCount=0;
    
    public Calculator() {
        calcFrame=new JFrame();
        calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getButtons();
        getPanels();
        calcFrame.add(mainPanel);
        calcFrame.pack();
        calcFrame.setVisible(true);
        calcFrame.setTitle("Calculator");
    }

    public void getButtons() {
        Font font = new Font("Arial",Font.BOLD, 24);
        buttons = new JButton[buttonsText.length];
        for (int i = 0;i<buttons.length;i++) {
            buttons[i] = new JButton(buttonsText[i]);
        }
    }
    public void getPanels() {
        monitor = new JTextField("0");
        monitor.setBackground(white);
        monitor.setFont(new Font("Arial", Font.PLAIN, 20));
        calcGrid=new JPanel();
        calcGrid.setLayout(new GridLayout(6,3,0,0));
        for (JButton button : buttons) {
            calcGrid.add(button);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(new CalcAL(button));
            
            
        } 
        mainPanel= new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(monitor, BorderLayout.NORTH);
        mainPanel.add(calcGrid, BorderLayout.CENTER);
    }
    class CalcAL implements ActionListener {
        JButton button;
        
        private CalcAL(JButton button) {
            this.button = button; 
                    
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            operator=this.button.getText();
            Double resDob;
            String resDobStr;
            if (operator.matches("[0-9]") || 
                operator.equals(".")) {
                if (monitor.getText().isEmpty() || monitor.getText().equals("0")) {
                    res=operator;
                    monitor.setText(res);
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else {
                    res+=operator;
                    monitor.setText(res);
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            }
            else {
                if (operator.equals("<-")) {
                    monitor.setText(monitor.getText().substring(0,monitor.getText().length()-1));
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }   
                if (operator.equals("C")) {
                    res="0";
                    vals.clear();
                    ops.clear();
                    idxCount=0;
                    monitor.setText(res);
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                if (operator.equals("+/-")) {
                    resDob=-1*(Double.valueOf(res));
                    resDobStr=resDob.toString();
                    res=resDobStr.substring(0,resDobStr.length()-2);
                    monitor.setText(res);
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                if (operator.matches("\\+|-|\\*|/")) {
                    vals.add(idxCount,Double.valueOf(res));
                    ops.add(idxCount,operator);
                    idxCount+=1;
                    monitor.setText(null);
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                }
                if (operator.matches("=|≈")) {
                    vals.add(idxCount,Double.valueOf(res));
                    ops.add(idxCount,operator);
                    idxCount+=1;
                    currRes=vals.get(0);
                    res=calc();
                    monitor.setText(res);
                    mainPanel.remove(monitor);
                    mainPanel.add(monitor, BorderLayout.NORTH);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            }
        }
    }
            
    public String calc() {  
        for (int i = 0; i < vals.size() -1; i ++) { 
            
            val=vals.get(i+1);
            op=ops.get(i);
        
            if (op.equals("+")) {
                currRes += val;
            }
            if (op.equals("-")) {
                currRes -= val; 
            }
            if (op.equals("*")) {
                currRes *= val;
            }
            if (op.equals("/")) {
                currRes /= val;
            }
        }
        if (operator.equals("=")) {
                res=currRes.toString(); 
            }
        if (operator.equals("≈")) {
            Long resLong = Math.round(currRes);
            res=resLong.toString();   
        }
        return res;
    }


    public static void main(String[] args) {
        Calculator calcNew = new Calculator();
    }
}


