package com.example.cs356;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Tournament  extends JFrame implements ActionListener {

    JPanel pnl1, pnl2;
    JPanel[] pnls, mainPnls;
    JRadioButton[] teams, shfldTeams;
    JRadioButton extra = new JRadioButton("extra", true);
    JButton shfl;
    ButtonGroup[] bg;
    int mpcount = 1;
    final int teamNum = 16;
    String[] trnmntTeams = {"Empire", "CA", "LA", "CPP", "TA", "MN", "OL",
        "AB", "CD", "EF", "GH", "BO", "CHEVY", "FORD", "TX", "AR"};
    int altI = 0;

    Tournament2() {
        setSize(450, 300);
        setLocationRelativeTo(null);
        shfl = new JButton("press to shuffle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tournament");
        int tmp = teamNum;//num of teams
        while (tmp >= 1) {//number of panel levels
            mpcount++;
            tmp /= 2;
        }
        setLayout(new GridLayout(mpcount, 0));
        mainPnls = new JPanel[mpcount];
        mainPnls[0] = new JPanel();
        mainPnls[0].add(shfl);
        shfl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                makeShuffle();
            }
        });
        add(mainPnls[0]);

        int tn = teamNum;

        teams = new JRadioButton[2 * tn - 1];
        for (int altI = 0; altI < 2 * tn - 1; altI++) {
            teams[altI] = new JRadioButton();
            teams[altI].setVerticalTextPosition(SwingConstants.TOP);
            teams[altI].setHorizontalTextPosition(SwingConstants.CENTER);
            teams[altI].setActionCommand("" + altI);
            teams[altI].addActionListener(this);
        }
        altI = 0;
        for (int mpan = 1; mpan < mpcount; mpan++) {
            pnls = new JPanel[tn];
            bg = new ButtonGroup[tn];
            mainPnls[mpan] = new JPanel();
            int j = 0;
            for (int i = 0; i < tn; i++) {
                teams[altI].setText("t" + (i + 1));
                if (i % 2 == 0) {
                    bg[j] = new ButtonGroup();
                    bg[j].add(teams[altI]);
                    if (tn > 1) {
                        bg[j].add(teams[altI + 1]);
                    }
                    pnls[j] = new JPanel(new GridLayout(1, 2));
                    pnls[j].add(teams[altI]);
                    if (tn > 1) {
                        pnls[j].add(teams[altI + 1]);
                    }
                    pnls[j].setBorder(BorderFactory.createEtchedBorder());
                    mainPnls[mpan].add(pnls[j]);
                }
                j++;
                altI++;
            }
            tn /= 2;
            add(mainPnls[mpan]);
        }
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        int input = Integer.parseInt(ae.getActionCommand());
        if (input != altI - 1) {
            teams[teamNum + input / 2].setText(teams[input].getText());
        } else {
            shfl.setText("the Winner is " + teams[input].getText());
        }
    }

    public String[] makeShuffle() {
        shfl.setText("shuffled");
        shfl.setEnabled(false);
        Collections.shuffle(Arrays.asList(trnmntTeams));
        for (int i = 0; i < teamNum; i++) {
            teams[i].setText(trnmntTeams[i]);
        }
        repaint();
        return trnmntTeams;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Tournament2();
            }
        });
    }
}