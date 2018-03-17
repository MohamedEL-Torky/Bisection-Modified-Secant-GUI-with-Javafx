/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bisection1;

import java.beans.Expression;
import static java.lang.Double.isNaN;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.lang.Math;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author Mohamed ELTorky
 */
public class BiSection1 extends Application {
    
    private TableView table = new TableView();
    
    private final ObservableList<EquationParameters> data =
        FXCollections.observableArrayList(
        );
    
    @Override
    public void start(Stage primaryStage){
        
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        
       TextField equation = new TextField();
        TextField aValue = new TextField();
        TextField bValue = new TextField();
        TextField tolValue = new TextField();
        
        pane.add(new Label("Equation:"), 0, 0);
        pane.add(equation, 1, 0);
        pane.add(new Label("A Value:"), 0, 1); 
        pane.add(aValue, 1, 1);
        pane.add(new Label("B Value:"), 0, 2);
        pane.add(bValue, 1, 2);
        pane.add(new Label("Tolerance:"), 0, 3);
        pane.add(tolValue, 1, 3);
        
        table.setEditable(true);
 
        TableColumn tI = new TableColumn("i");
        tI.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("i"));
        
        TableColumn tA = new TableColumn("a");
        tA.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("a"));
        
        TableColumn tB = new TableColumn("b");
        tB.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("b"));
        
        TableColumn tC = new TableColumn("c");
        tC.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("c"));
        
        TableColumn tFA = new TableColumn("f(a)");
        tFA.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("fa"));
        
        TableColumn tFB = new TableColumn("f(b)");
        tFB.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("fb"));
        
        TableColumn tFC = new TableColumn("f(c)");
        tFC.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("fc"));
        
        TableColumn tFCE = new TableColumn("|f(c)|<= e");
        tFCE.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("fce"));
        
        TableColumn tCI = new TableColumn("|Ci-Ci1-1|");
        tCI.setCellValueFactory(
                new PropertyValueFactory<EquationParameters, String>("ci"));
        
        table.setItems(data);
        table.getColumns().addAll(tI, tA, tB, tC, tFA, tFB, tFC, tFCE, tCI);
        
        table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.add(table, 0, 5, 5, 5);
        
        Button btnCalcBisection = new Button("Bisection");
        Button btnCalcSecant = new Button("Modified Secant");
        
        //equation,aValue,bValue,tolValue
//        fA 
//        fB
//        fC
//        c

        btnCalcBisection.setOnAction(e -> {    
            if(equation.getText().equals("") || aValue.getText().equals("")
            || bValue.getText().equals("") || tolValue.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Kindly, check your input.");
                    
                
            }
            else{
                //Intalize all the variables and parse it.
                String eq = equation.getText();
                double A = Double.parseDouble(new DecimalFormat("##.####").format(Double.parseDouble(aValue.getText())));
                double B = Double.parseDouble(new DecimalFormat("##.####").format(Double.parseDouble(bValue.getText())));
                double TOL = Double.parseDouble(new DecimalFormat("##.####").format(Double.parseDouble(tolValue.getText())));
                double C = Double.parseDouble(new DecimalFormat("##.####").format((A + B) /2));
                double Ci = 0;
                double maxItrations = Math.log((B - A) / TOL) / (Math.log(2));
                if(maxItrations < 1 || isNaN(maxItrations)){
                    JOptionPane.showMessageDialog(null, "Please check your "
                            + "equationor The values of A, B and the tolerance. "
                            + "Because maxItrations is less than 0.");
                }
                for ( int i = 0; i<table.getItems().size(); i++) {
                       table.getItems().clear();
                }
                for(int i = 0; i < maxItrations; i++){
                    //Using exp4j to eavluate the exprsion
                    try{
                        net.objecthunter.exp4j.Expression eA = new ExpressionBuilder(eq)
                        .variables("x")
                        .build()
                        .setVariable("x", A);
                        double resultOfA = Double.parseDouble(new DecimalFormat("##.####").format(eA.evaluate()));

                        net.objecthunter.exp4j.Expression eB = new ExpressionBuilder(eq)
                        .variables("x")
                        .build()
                        .setVariable("x", B);
                        double resultOfB = Double.parseDouble(new DecimalFormat("##.####").format(eB.evaluate()));

                        net.objecthunter.exp4j.Expression eC = new ExpressionBuilder(eq)
                        .variables("x")
                        .build()
                        .setVariable("x", C);
                        double resultOfC = Double.parseDouble(new DecimalFormat("##.####").format(eC.evaluate()));
                        //Check the user input if there is any Zero it's not valid
                        //or if the all functions outputs are the same, also it's
                        //not a valid sloution, then break and print a message
                        if ((Math.signum(resultOfC) == Math.signum(resultOfB)
                                && Math.signum(resultOfC) == Math.signum(resultOfA))
                                || resultOfC == 0 || resultOfB == 0 || resultOfA == 0){
                            JOptionPane.showMessageDialog(null, "Wrong input, check values of A and B");
                            break;
                        }

                        //add elemnts to the table
                        data.add(new EquationParameters(
                                Integer.toString(i+1),
                                Double.toString(A),
                                Double.toString(B),
                                Double.toString(C),
                                Double.toString(resultOfA),
                                Double.toString(resultOfB),
                                Double.toString(resultOfC),
                                Math.abs(resultOfC) <= TOL ? "YES": "NO",
                                Math.abs(Ci-C-1) <= TOL ? "YES": "NO" ));
                        //Check who will replace the A or the B in next itreation
                        if(Math.signum(resultOfC) == Math.signum(resultOfA)){
                            A = C;
                        }
                        else{
                            B = C;
                        }
                        //Get the new value of the C and save the current one
                        Ci = C;
                        C = Double.parseDouble(new DecimalFormat("##.####").format((A + B) /2));
                        //Check the Tol if it got passed or not.
                        if(Math.abs(resultOfC) <= TOL){
                            JOptionPane.showMessageDialog(null, resultOfC+" <= "+TOL
                                    +" loop broke at rotaion i: "+(i+1));
                            break;
                        }
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Please check your "
                                + "equation. Note that x in small letter "
                                + "is only acceptable.");
                        break;
                    }
                }
            }
        });
        
                btnCalcSecant.setOnAction(e -> {    
            if(equation.getText().equals("") || aValue.getText().equals("")
            || bValue.getText().equals("") || tolValue.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Kindly, check your input.");
                
            }
            else{
                
                String eq = equation.getText();
                double A = Double.parseDouble(new DecimalFormat("##.####").format(Double.parseDouble(aValue.getText())));
                double B = Double.parseDouble(new DecimalFormat("##.####").format(Double.parseDouble(bValue.getText())));
                double TOL = Double.parseDouble(new DecimalFormat("##.####").format(Double.parseDouble(tolValue.getText())));
                double C = 0;
                double Ci = 0;
                for ( int i = 0; i<table.getItems().size(); i++) {
                       table.getItems().clear();
                }
                for(int i = 0; i < 10; i++){
                    //Using exp4j to 
                    try{
                        net.objecthunter.exp4j.Expression eA = new ExpressionBuilder(eq)
                        .variables("x")
                        .build()
                        .setVariable("x", A);
                        double resultOfA = Double.parseDouble(new DecimalFormat("##.####").format(eA.evaluate()));

                        net.objecthunter.exp4j.Expression eB = new ExpressionBuilder(eq)
                        .variables("x")
                        .build()
                        .setVariable("x", B);
                        double resultOfB = Double.parseDouble(new DecimalFormat("##.####").format(eB.evaluate()));

                        C = Double.parseDouble(new DecimalFormat("##.####").format(B - (resultOfB*(B-A)) / (resultOfB - resultOfA)));

                        net.objecthunter.exp4j.Expression eC = new ExpressionBuilder(eq)
                        .variables("x")
                        .build()
                        .setVariable("x", C);
                        double resultOfC = Double.parseDouble(new DecimalFormat("##.####").format(eC.evaluate()));
                        
                        //Check the user input if there is any Zero it's not valid
                        //or if the all functions outputs are the same, also it's
                        //not a valid sloution, then break and print a message
                        if ((Math.signum(resultOfC) == Math.signum(resultOfB)
                                && Math.signum(resultOfC) == Math.signum(resultOfA))
                                || resultOfC == 0 || resultOfB == 0 || resultOfA == 0){
                            JOptionPane.showMessageDialog(null, "Wrong input, check values of A and B");
                            break;
                        }

                        data.add(new EquationParameters(
                                Integer.toString(i+1),
                                Double.toString(A),
                                Double.toString(B),
                                Double.toString(C),
                                Double.toString(resultOfA),
                                Double.toString(resultOfB),
                                Double.toString(resultOfC),
                                Math.abs(resultOfC) <= TOL ? "YES": "NO",
                                Math.abs(Ci-C-1) <= TOL ? "YES": "NO" ));

                        if(Math.signum(resultOfC) == Math.signum(resultOfA)){
                            A = C;
                        }
                        else{
                            B = C;
                        }
                        Ci = C;
                        C = Double.parseDouble(new DecimalFormat("##.####").format(B - (resultOfB*(B-A)) / (resultOfB - resultOfA)));
                        if(Math.abs(resultOfC) <= TOL){
                            JOptionPane.showMessageDialog(null, resultOfC+" <= "+TOL
                                    +" loop broke at rotaion i: "+(i+1));
                            break;
                        }
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Please check your "
                            + "equation. Note that x in small letter "
                            + "is only acceptable.");
                        break;
                    }
                }
            }
            
        });
        
        
        pane.add(btnCalcBisection, 1, 4);
        pane.add(btnCalcSecant, 2, 4);
        
        Scene scene = new Scene(pane);
        
        primaryStage.setTitle("BiSection Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
        public static class EquationParameters {
 
        private final SimpleStringProperty i;
        private final SimpleStringProperty a;
        private final SimpleStringProperty b;
        private final SimpleStringProperty c;
        private final SimpleStringProperty fa;
        private final SimpleStringProperty fb;
        private final SimpleStringProperty fc;
        private final SimpleStringProperty fce;
        private final SimpleStringProperty ci;
 
        private EquationParameters(String I, String A, String B,String C, String FA, String FB,String FC, String FCE, String CI) {
            this.i = new SimpleStringProperty(I);
            this.a = new SimpleStringProperty(A);
            this.b = new SimpleStringProperty(B);
            this.c = new SimpleStringProperty(C);
            this.fa = new SimpleStringProperty(FA);
            this.fb = new SimpleStringProperty(FB);
            this.fc = new SimpleStringProperty(FC);
            this.fce = new SimpleStringProperty(FCE);
            this.ci = new SimpleStringProperty(CI);
        }
 
        public String getI() {
            return i.get();
        }
 
        public void setI(String I) {
            i.set(I);
        }
 
        public String getA() {
            return a.get();
        }
 
        public void setA(String A) {
            a.set(A);
        }
 
        public String getB() {
            return b.get();
        }
 
        public void setB(String B) {
            b.set(B);
        }
        public String getC() {
            return c.get();
        }
 
        public void setC(String C) {
            c.set(C);
        }
        public String getFa() {
            return fa.get();
        }
 
        public void setFa(String FA) {
            fa.set(FA);
        }
        public String getFb() {
            return fb.get();
        }
 
        public void setFb(String FB) {
            fb.set(FB);
        }
        public String getFc() {
            return fc.get();
        }
 
        public void setFc(String FC) {
            fc.set(FC);
        }
        public String getFce() {
            return fce.get();
        }
 
        public void setFce(String FCE) {
            fce.set(FCE);
        }
        public String getCi() {
            return ci.get();
        }
 
        public void setCi(String CI) {
            ci.set(CI);
        }
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
