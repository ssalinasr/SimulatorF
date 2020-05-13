/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import conexiones.DBConsultas;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class FrameBase extends javax.swing.JFrame {
    
    private double goalteam1;
    private double goalteam2;
    
    private Thread actions1;
    private Thread actions2;
    private Thread timer;
    
    private double levelteam1;
    private double levelteam2;
    private double teamdiff1;
    private double teamdiff2;
    
    private int standardmatch;
    private static int duration = 10;
    private static int timeelapsed = 420;
    
    private boolean statusOne = true;
    private boolean statusTwo = true;
    
    private double at1;
    private double md1;
    private double df1;
    
    private double at2;
    private double md2;
    private double df2;
    
    private double at1last;
    private double md1last;
    private double df1last;

    private double at2last;
    private double md2last;
    private double df2last;    
    
    private String lastTeam1;
    private String lastTeam2;
    
    private String [][] database;
    
    
    
    

    /**
     * Creates new form FrameBase
     */
    public FrameBase() throws SQLException {
        initComponents();
        setLocationRelativeTo(null);
        this.BtnResetMatch.setEnabled(false);
        this.BtnRunMatch.setEnabled(false);
        this.BtnSwitch.setEnabled(false);
        this.standardmatch = 0;
        this.BtnLast.setEnabled(false);
        this.database = new String[145][4];
        this.AtkTeam1.setEditable(false);
        this.AtkTeam2.setEditable(false);
        this.DefTeam1.setEditable(false);
        this.DefTeam2.setEditable(false);
        this.MidTeam1.setEditable(false);
        this.MidTeam2.setEditable(false);

    }
    
    public void getDifference(){
        try{
        this.at1 = Double.parseDouble(this.AtkTeam1.getText());
        this.md1 = Double.parseDouble(this.MidTeam1.getText());
        this.df1 = Double.parseDouble(this.DefTeam1.getText());
        
        this.at2 = Double.parseDouble(this.AtkTeam2.getText());
        this.md2 = Double.parseDouble(this.MidTeam2.getText());
        this.df2 = Double.parseDouble(this.DefTeam2.getText());
        
        this.at1last = this.at1;
        this.md1last = this.md1;
        this.df1last = this.df1;
        
        this.at2last = this.at2;
        this.md2last = this.md2;
        this.df2last = this.df2;
        
        this.lastTeam1 = this.NameTeam1.getText();
        this.lastTeam2 = this.NameTeam2.getText();
        
        this.levelteam1 = (at1+md1+df1)/3;
        this.levelteam2 = (at2+md2+df2)/3;
        
        this.teamdiff1 = (this.levelteam1 - this.levelteam2);
        this.teamdiff2 = (this.levelteam2 - this.levelteam1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Empty values","Warning",0);
        }
        
    }
    
    public void insertTeam1() throws SQLException{
        flagSorter s = new flagSorter();
        this.NameTeam1.setText(this.TxtNameTeam1.getText());
        ImageIcon flag = s.setFlags(this.TxtNameTeam1.getText());
        try{
        Image img = flag.getImage().getScaledInstance(this.FlagTeam1.getWidth(),
                this.FlagTeam1.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon sizedFlag = new ImageIcon(img);
        this.FlagTeam1.setIcon(sizedFlag);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Failed at setting flag, F","Warning",0);
            this.FlagTeam1.setIcon(null);
        }
        
        if(this.FlagTeam1.getIcon() != null && this.FlagTeam2.getIcon() != null){
            this.BtnRunMatch.setEnabled(true);
            this.BtnSwitch.setEnabled(true);
        }

        String name = this.TxtNameTeam1.getText();        
        
        DBConsultas r = new DBConsultas();
        ResultSet rs = r.getTeams();
        for(int i = 0; i < database.length; i++){
        while(rs.next()){
            this.database[i][0] = rs.getString("team_name");
            this.database[i][1] = Integer.toString(rs.getInt("team_def"));
            this.database[i][2] = Integer.toString(rs.getInt("team_mid"));
            this.database[i][3] = Integer.toString(rs.getInt("team_atk")); 
            if(this.database[i][0].equals(name)){
                int defval1 = Integer.parseInt(this.database[i][1]);
                int midval1 = Integer.parseInt(this.database[i][2]);
                int atkval1 = Integer.parseInt(this.database[i][3]);
               
                this.AtkTeam1.setText(Integer.toString(atkval1));
                this.DefTeam1.setText(Integer.toString(defval1));
                this.MidTeam1.setText(Integer.toString(midval1));
            }
        }    
        }
        
    }
    
    public void insertTeam2() throws SQLException{
        flagSorter s = new flagSorter();
        this.NameTeam2.setText(this.TxtNameTeam2.getText());
        ImageIcon flag = s.setFlags(this.TxtNameTeam2.getText());
        try{
        Image img = flag.getImage().getScaledInstance(this.FlagTeam2.getWidth(),
                this.FlagTeam2.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon sizedFlag = new ImageIcon(img);
        this.FlagTeam2.setIcon(sizedFlag);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Failed at setting flag, F","Warning",0);
            this.FlagTeam2.setIcon(null);
        }   
        
        if(this.FlagTeam1.getIcon() != null && this.FlagTeam2.getIcon() != null){
            this.BtnRunMatch.setEnabled(true);
            this.BtnSwitch.setEnabled(true);
        }
        
        String name = this.TxtNameTeam2.getText();
        
        DBConsultas r = new DBConsultas();
        ResultSet rs = r.getTeams();
        for(int i = 0; i < database.length; i++){
        while(rs.next()){
            this.database[i][0] = rs.getString("team_name");
            this.database[i][1] = Integer.toString(rs.getInt("team_def"));
            this.database[i][2] = Integer.toString(rs.getInt("team_mid"));
            this.database[i][3] = Integer.toString(rs.getInt("team_atk")); 
            if(this.database[i][0].equals(name)){
                int defval1 = Integer.parseInt(this.database[i][1]);
                int midval1 = Integer.parseInt(this.database[i][2]);
                int atkval1 = Integer.parseInt(this.database[i][3]);
               
                this.AtkTeam2.setText(Integer.toString(atkval1));
                this.DefTeam2.setText(Integer.toString(defval1));
                this.MidTeam2.setText(Integer.toString(midval1));
            }
        }    
        }
    }
    
    public void switchTeams() throws SQLException{
        String nameteam1 = this.TxtNameTeam1.getText();
        String nameteam2 = this.TxtNameTeam2.getText(); 
        this.TxtNameTeam1.setText(nameteam2);
        this.TxtNameTeam2.setText(nameteam1);
        
        String atk1 = this.AtkTeam1.getText();
        String def1 = this.DefTeam1.getText();
        String mid1 = this.MidTeam1.getText();
        
        String atk2 = this.AtkTeam2.getText();
        String def2 = this.DefTeam2.getText();
        String mid2 = this.MidTeam2.getText();
        
        this.AtkTeam1.setText(atk2);
        this.DefTeam1.setText(def2);
        this.MidTeam1.setText(mid2);
        
        this.AtkTeam2.setText(atk1);
        this.DefTeam2.setText(def1);
        this.MidTeam2.setText(mid1);
        
        
        flagSorter p = new flagSorter();
        this.NameTeam1.setText(this.TxtNameTeam1.getText());
        ImageIcon flag = p.setFlags(this.TxtNameTeam1.getText());
        try{
        Image img = flag.getImage().getScaledInstance(this.FlagTeam1.getWidth(),
                this.FlagTeam1.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon sizedFlag = new ImageIcon(img);
        this.FlagTeam1.setIcon(sizedFlag);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Failed at setting flag, F","Warning",0);
            this.FlagTeam1.setIcon(null);
        }
                
       
        this.NameTeam2.setText(this.TxtNameTeam2.getText());
        ImageIcon flag2 = p.setFlags(this.TxtNameTeam2.getText());
        try{
        Image img = flag2.getImage().getScaledInstance(this.FlagTeam2.getWidth(),
                this.FlagTeam2.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon sizedFlag = new ImageIcon(img);
        this.FlagTeam2.setIcon(sizedFlag);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Failed at setting flag, F","Warning",0);
            this.FlagTeam2.setIcon(null);
        } 
        
    }
    
    public double generateRandomValue(double teamdiff){
        double rand = 0;
        if(teamdiff <= 0){
            rand = Math.random()*0.0325;
        }
        else{
            if(teamdiff > 0 && teamdiff <= 10){
                rand = Math.random()*0.0377;
            }
            else{
                if(teamdiff > 10 && teamdiff <= 20){
                    rand = Math.random()*0.0485;
                }
                else{
                    if(teamdiff > 20 && teamdiff <= 30){
                        rand = Math.random()*0.0556;
                    }
                    else{
                        if(teamdiff > 30 && teamdiff <= 40){
                            rand = Math.random()*0.0635;
                        }
                        else{
                            if(teamdiff > 40 && teamdiff <= 50){
                                rand = Math.random()*0.0795;
                            }
                            else{
                                if(teamdiff > 50 && teamdiff <= 60){
                                    rand = Math.random()*0.1165;
                                }
                                else{
                                    if(teamdiff > 60){
                                        rand = Math.random()*0.1335;                                    }
                                    else{
                                        System.out.println("Oops...");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return rand;
        
    }
    
    public void goalConditional(){
        
        int rand = (int)Math.floor(Math.random()*27);
        if(rand % 23 == 0 && this.goalteam1 < 3){
            this.goalteam1 = this.goalteam1/1.035;
        }
        if(rand % 19 == 0 && (this.goalteam1 >= 3 && this.goalteam1 < 6)){
           this.goalteam1 = this.goalteam1/1.015; 
        }
        if(rand % 17 == 0 && (this.goalteam1 >= 6 && this.goalteam1 < 9)){
           this.goalteam1 = this.goalteam1/1.006;  
        }
        if(rand == 26 && this.goalteam1 > 1 && this.goalteam1 < 4){
            this.goalteam1 -= (Math.PI-3)*3.000001;
        }
    }
        
    public void goalConditional2(){
        
        int rand = (int)Math.floor(Math.random()*27);
        if(rand % 17 == 0 && this.goalteam1 < 3){
            this.goalteam2 = this.goalteam2/1.035;
        }
        if(rand % 19 == 0 && (this.goalteam2 >= 3 && this.goalteam2 < 6)){
           this.goalteam2 = this.goalteam2/1.015; 
        }
        if(rand % 23 == 0 && (this.goalteam2 >= 6 && this.goalteam2 < 9)){
           this.goalteam2 = this.goalteam2/1.006;  
        }
        if(rand == 26 && this.goalteam2 > 1 && this.goalteam2 < 4){
            this.goalteam2 -= (Math.PI-3)*3.000001;
        }
    }
    
    
    public void timerRunner() throws InterruptedException{
        String text;
        String minutes;
        String seconds;
            for(int j = 0; j < 60; j++){
                if(j<10){
                    minutes = "0"+j;
                }
                else{
                    minutes = ""+j;
                }
                for(int k = 0; k < 60; k++){
                    if(k < 10){
                        seconds = "0"+k;
                    }
                    else{
                        seconds = ""+k;
                    }
                    
                    text = minutes + ":" + seconds;
                    this.LblTimer.setText(text);
                    this.standardmatch++;
                    Thread.sleep(duration);
                }
            }
        } 
    
    
    public void enableInputs(){
        
        this.TxtNameTeam1.setEnabled(true);
        this.TxtNameTeam2.setEnabled(true);
        this.AtkTeam1.setEnabled(true);
        this.DefTeam1.setEnabled(true);
        this.MidTeam1.setEnabled(true);        
        this.AtkTeam2.setEnabled(true);
        this.DefTeam2.setEnabled(true);
        this.MidTeam2.setEnabled(true);
        
    }
    
    public void unableInputs(){
        
        this.TxtNameTeam1.setEnabled(false);
        this.TxtNameTeam2.setEnabled(false);
        this.AtkTeam1.setEnabled(false);
        this.DefTeam1.setEnabled(false);
        this.MidTeam1.setEnabled(false);        
        this.AtkTeam2.setEnabled(false);
        this.DefTeam2.setEnabled(false);
        this.MidTeam2.setEnabled(false);
        
    }
    
    public void enableInserts(){
        
        this.BtnInsertTeam1.setEnabled(true);
        this.BtnInsertTeam2.setEnabled(true);
        
    }
    
    public void unableInserts(){
        
        this.BtnInsertTeam1.setEnabled(false);
        this.BtnInsertTeam2.setEnabled(false);
        
    }
    
    public void returnValuesToZero(){
        this.goalteam1 = 0;
        this.goalteam2 = 0;
        this.standardmatch = 0;
        this.levelteam1 = 0;
        this.levelteam2 = 0;
        this.teamdiff1 = 0;
        this.teamdiff2 = 0;
        this.at1 = 0;
        this.at2 = 0;
        this.df1 = 0;
        this.df2 = 0;
        this.md1 = 0;
        this.md2 = 0;
        this.FlagTeam1.setIcon(null);
        this.FlagTeam2.setIcon(null);
        this.NameTeam1.setText("TeamName1");
        this.NameTeam2.setText("TeamName2");
        this.LblTimer.setText("00:00");
        this.GoalTeam1.setText("0");
        this.GoalTeam2.setText("0");
        this.TxtNameTeam1.setText("");
        this.TxtNameTeam2.setText("");
        this.AtkTeam1.setText("");
        this.AtkTeam2.setText("");
        this.DefTeam1.setText("");
        this.DefTeam2.setText("");
        this.MidTeam1.setText("");
        this.MidTeam2.setText("");
    }
    
    public void useLastValues(){
        this.TxtNameTeam1.setText(this.lastTeam1);
        this.TxtNameTeam2.setText(this.lastTeam2);
        
        this.AtkTeam1.setText(this.at1last+"");
        this.DefTeam1.setText(this.df1last+"");
        this.MidTeam1.setText(this.md1last+"");
        
        this.AtkTeam2.setText(this.at2last+"");
        this.DefTeam2.setText(this.df2last+"");
        this.MidTeam2.setText(this.md2last+"");
        
        this.BtnLast.setEnabled(false);
    }
    
    public boolean verifyValues(double at, double df, double md){
        boolean v = true;
        
        if(at <= 0 || at > 99){
            v = false;
        }
        if(df <= 0 || df > 99){
            v = false;
        }
        if(md <= 0 || md > 99){
            v = false;
        }
            
        return v;
    }
    
    public void goalsOne() throws InterruptedException{
        
        while(this.standardmatch < timeelapsed){
            this.goalteam1 = this.goalteam1 + this.generateRandomValue(teamdiff1);
            this.GoalTeam1.setText(Integer.toString((int)Math.floor(this.goalteam1)));
            this.goalConditional();
            Thread.sleep(duration);
            //System.out.println(this.goalteam1);
        }
        this.statusOne = false;
        this.actions1.interrupt();
        this.timer.interrupt();
        System.out.println("Team 1 actions have been finished...");
        this.enableInputs();
        this.enableInserts();
    }
    
    public void goalsTwo() throws InterruptedException{
                     
        while(this.standardmatch < timeelapsed){
            this.goalteam2 = this.goalteam2 + this.generateRandomValue(teamdiff2);
            this.GoalTeam2.setText(Integer.toString((int)Math.floor(this.goalteam2)));
            this.goalConditional2();
            Thread.sleep(duration);
            //System.out.println(this.goalteam2);
        }
        this.statusTwo = false;
        Thread.sleep(1000);
        this.showWinner(); 
        this.actions2.interrupt();
        System.out.println("Team 2 actions have been finished...");
        this.BtnResetMatch.setEnabled(true);


    }
    
    public void showWinner(){
        String win;
        int value1 = Integer.parseInt(this.GoalTeam1.getText());
        int value2 = Integer.parseInt(this.GoalTeam2.getText());
        if(value1  > value2){
            win = "El ganador es: \n"
                + this.NameTeam1.getText()+"\n"
                + "Por: " + value1 +"-"+value2;
        }
        else{
            if(value2 > value1){
            win = "El ganador es: \n"
                + this.NameTeam2.getText()+"\n"
                + "Por: " + value1 +"-"+value2;
            }
            else{
            win = "Empate: \n"
                + "Por: " + value1 +"-"+value2;
            }
        }
        JOptionPane.showMessageDialog(null,win,"Ganador",1);

    }
    
    
    public void threads(){
        timer = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    while(statusOne && statusTwo){
                    timerRunner();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Thread 'timer' error...");
                }
            }
        });
        
        actions1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(statusOne){
                    goalsOne();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Thread 'Actions 1' error...");
                }
            }
        });
        
        actions2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(statusTwo){
                    goalsTwo();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Thread 'Actions 2' error...");
                }
            }
        });
        this.BtnLast.setEnabled(false);
        this.unableInputs();
        this.unableInserts();
        
        this.actions1.start();
        this.actions2.start();
        this.timer.start();
       
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NameTeam1 = new javax.swing.JLabel();
        NameTeam2 = new javax.swing.JLabel();
        TxtNameTeam1 = new javax.swing.JTextField();
        TxtNameTeam2 = new javax.swing.JTextField();
        FlagTeam1 = new javax.swing.JLabel();
        FlagTeam2 = new javax.swing.JLabel();
        GoalTeam1 = new javax.swing.JLabel();
        GoalTeam2 = new javax.swing.JLabel();
        Line = new javax.swing.JLabel();
        LblState1 = new javax.swing.JLabel();
        LblState2 = new javax.swing.JLabel();
        DF1 = new javax.swing.JLabel();
        MD1 = new javax.swing.JLabel();
        AT1 = new javax.swing.JLabel();
        DF2 = new javax.swing.JLabel();
        MD2 = new javax.swing.JLabel();
        AT2 = new javax.swing.JLabel();
        DefTeam1 = new javax.swing.JTextField();
        MidTeam1 = new javax.swing.JTextField();
        AtkTeam1 = new javax.swing.JTextField();
        DefTeam2 = new javax.swing.JTextField();
        MidTeam2 = new javax.swing.JTextField();
        AtkTeam2 = new javax.swing.JTextField();
        LblTimer = new javax.swing.JLabel();
        BtnInsertTeam1 = new javax.swing.JButton();
        BtnInsertTeam2 = new javax.swing.JButton();
        BtnRunMatch = new javax.swing.JButton();
        BtnResetMatch = new javax.swing.JButton();
        BtnLast = new javax.swing.JButton();
        BtnSwitch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulador");
        setResizable(false);

        NameTeam1.setText("TeamName1");

        NameTeam2.setText("TeamName2");

        GoalTeam1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GoalTeam1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GoalTeam1.setText("0");

        GoalTeam2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        GoalTeam2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GoalTeam2.setText("0");

        Line.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Line.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Line.setText("-");
        Line.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        LblState1.setText("State1");

        LblState2.setText("State2");

        DF1.setText("DF");

        MD1.setText("MD");

        AT1.setText("AT");

        DF2.setText("DF");

        MD2.setText("MD");

        AT2.setText("AT");

        LblTimer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LblTimer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LblTimer.setText("00:00");

        BtnInsertTeam1.setText("Insert Team");
        BtnInsertTeam1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInsertTeam1ActionPerformed(evt);
            }
        });

        BtnInsertTeam2.setText("Insert Team");
        BtnInsertTeam2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInsertTeam2ActionPerformed(evt);
            }
        });

        BtnRunMatch.setText("Run");
        BtnRunMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRunMatchActionPerformed(evt);
            }
        });

        BtnResetMatch.setText("Reset");
        BtnResetMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResetMatchActionPerformed(evt);
            }
        });

        BtnLast.setText("Last Values");
        BtnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLastActionPerformed(evt);
            }
        });

        BtnSwitch.setText("Switch Places");
        BtnSwitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSwitchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(FlagTeam1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NameTeam1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(GoalTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Line, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GoalTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(LblTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NameTeam2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(FlagTeam2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BtnRunMatch, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(LblState1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TxtNameTeam1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(DF1)
                                        .addComponent(MD1)
                                        .addComponent(AT1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(BtnInsertTeam1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                        .addComponent(AtkTeam1)
                                        .addComponent(MidTeam1)
                                        .addComponent(DefTeam1)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnSwitch, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LblState2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BtnInsertTeam2, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(MidTeam2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(DefTeam2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(AtkTeam2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DF2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(MD2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(AT2, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(TxtNameTeam2)
                            .addComponent(BtnResetMatch, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FlagTeam1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FlagTeam2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                            .addComponent(GoalTeam1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(GoalTeam2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(Line, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LblTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NameTeam2, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(NameTeam1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtNameTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtNameTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblState1)
                    .addComponent(LblState2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DF1)
                    .addComponent(DF2)
                    .addComponent(DefTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DefTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MD1)
                    .addComponent(MD2)
                    .addComponent(MidTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MidTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AT1)
                    .addComponent(AT2)
                    .addComponent(AtkTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AtkTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnInsertTeam1)
                    .addComponent(BtnInsertTeam2)
                    .addComponent(BtnSwitch))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRunMatch)
                    .addComponent(BtnResetMatch)
                    .addComponent(BtnLast))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInsertTeam1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInsertTeam1ActionPerformed
        try {
            this.insertTeam1();
        } catch (SQLException ex) {
            Logger.getLogger(FrameBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnInsertTeam1ActionPerformed

    private void BtnInsertTeam2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInsertTeam2ActionPerformed
        try {
            this.insertTeam2();
        } catch (SQLException ex) {
            Logger.getLogger(FrameBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnInsertTeam2ActionPerformed

    private void BtnRunMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRunMatchActionPerformed
        
        this.getDifference();
        if(this.verifyValues(this.at1, this.df1, this.md1) &&
                this.verifyValues(this.at2, this.df2, this.md2)){
        this.BtnResetMatch.setEnabled(true);
        this.BtnRunMatch.setEnabled(false);
        this.BtnSwitch.setEnabled(false);
        this.threads();

        }
        else{
            JOptionPane.showMessageDialog(null,"Some of values are wrong...","Warning",0);
        }
    }//GEN-LAST:event_BtnRunMatchActionPerformed

    private void BtnResetMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResetMatchActionPerformed
        this.returnValuesToZero();
        this.BtnResetMatch.setEnabled(false);
        this.statusOne = true;
        this.statusTwo = true;
        this.BtnLast.setEnabled(true);
    }//GEN-LAST:event_BtnResetMatchActionPerformed

    private void BtnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLastActionPerformed
        this.useLastValues();
    }//GEN-LAST:event_BtnLastActionPerformed

    private void BtnSwitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSwitchActionPerformed
        try {
            this.switchTeams();
        } catch (SQLException ex) {
            Logger.getLogger(FrameBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnSwitchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrameBase().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(FrameBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AT1;
    private javax.swing.JLabel AT2;
    private javax.swing.JTextField AtkTeam1;
    private javax.swing.JTextField AtkTeam2;
    private javax.swing.JButton BtnInsertTeam1;
    private javax.swing.JButton BtnInsertTeam2;
    private javax.swing.JButton BtnLast;
    private javax.swing.JButton BtnResetMatch;
    private javax.swing.JButton BtnRunMatch;
    private javax.swing.JButton BtnSwitch;
    private javax.swing.JLabel DF1;
    private javax.swing.JLabel DF2;
    private javax.swing.JTextField DefTeam1;
    private javax.swing.JTextField DefTeam2;
    private javax.swing.JLabel FlagTeam1;
    private javax.swing.JLabel FlagTeam2;
    private javax.swing.JLabel GoalTeam1;
    private javax.swing.JLabel GoalTeam2;
    private javax.swing.JLabel LblState1;
    private javax.swing.JLabel LblState2;
    private javax.swing.JLabel LblTimer;
    private javax.swing.JLabel Line;
    private javax.swing.JLabel MD1;
    private javax.swing.JLabel MD2;
    private javax.swing.JTextField MidTeam1;
    private javax.swing.JTextField MidTeam2;
    private javax.swing.JLabel NameTeam1;
    private javax.swing.JLabel NameTeam2;
    private javax.swing.JTextField TxtNameTeam1;
    private javax.swing.JTextField TxtNameTeam2;
    // End of variables declaration//GEN-END:variables
}
