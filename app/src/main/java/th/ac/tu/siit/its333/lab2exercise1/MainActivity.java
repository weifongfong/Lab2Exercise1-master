package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    char Temp;
    double Result=0,Mem=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void updateAnsDisplay(Double Result) {
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Double.toString(Result));
    }

    public void recalculate() {

        double Operand=0,Operand2,tempResult;
        char Operator;
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");
        tempResult = expr.length()>0?Double.parseDouble(tokens[0]):0.0;
        for(int i = 1;i < tokens.length; i++){
            if(i%2==1){
                switch (tokens[i].toString().toCharArray()[0]){
                    case '+':
                        tempResult += Double.parseDouble(tokens[i+1]);
                        break;
                    case '-':
                        tempResult -= Double.parseDouble(tokens[i+1]);
                        break;
                    case '*':
                        tempResult *= Double.parseDouble(tokens[i+1]);
                        break;
                    case '/':
                        tempResult /= Double.parseDouble(tokens[i+1]);
                        break;
                    default:
                        break;
                }
            }
        }
        Result = tempResult;
        updateAnsDisplay(Result);
    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        Temp = d.toCharArray()[0];
        //calculate the result if possible and update tvAns
        if(Temp != '+' &&Temp != '-' &&Temp != '*' &&Temp != '/')
            recalculate();
    }

    public void operatorClicked(View v) {
        //IF the last character in expr is not an operator and expr is not "",
        //THEN append the clicked operator and updateExprDisplay,
        //ELSE do nothing
        String d = ((TextView)v).getText().toString();
        //Toast t = Toast.makeText(this.getApplicationContext(),
        //        expr.toString().substring(expr.toString().length()-1),
        //        Toast.LENGTH_SHORT);
        //t.show();
        if(expr.toString()!="" && Temp!='+'&&Temp!='-'&&Temp!='*'&&Temp!='/'){
            //if(expr.toString().substring(expr.length()-1)=="+"&&expr.toString().substring(expr.length()-1)=="+"&&expr.toString().substring(expr.length()-1)=="*");
            //append the clicked digit to expr
            expr.append(d);
            Temp = d.toCharArray()[0];
            //update tvExpr
            updateExprDisplay();
        }

    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplays
        expr = new StringBuffer();
        updateExprDisplay();
        Result = 0;
        updateAnsDisplay(Result);
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            updateExprDisplay();
            Temp = '0';
            if(expr.length()%2==1||expr.length()==0)
                recalculate();
        }
    }

    public void equalClicked(View v){
        expr = new StringBuffer();
        expr.append(Double.toString(Result));
        updateExprDisplay();
        recalculate();
        updateAnsDisplay(0.0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}