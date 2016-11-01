package com.example.pc_samir.anomaliabouguer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final double GE = 978032.67715;
    public static final double K = 0.001931851353;
    public static final double e2 = 0.0066938002290;

    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.text);
    }

    //Metodo que será chamado quando clicar no botão (onClick)
    public void calcularTudo(View v) {
        // Definimos o lat: 30° (precisa pegar o valor do edit)
        EditText editLat = (EditText) findViewById(R.id.edit_lat);
        String latStr = editLat.getText().toString(); //"30"
        double lat = Double.parseDouble(latStr); // 30

        EditText editH = (EditText) findViewById(R.id.edit_h);
        String hStr = editH.getText().toString();
        double h = Double.parseDouble(hStr);
        //Calculamos a gravidade teorica
        double valorGravTeorica = calculaGravidadeTeorica(lat,h);

        // Colocamos o resultado no final do textView (t)
        t.append("Gravidade Teórica: " + valorGravTeorica);
    }

    private double calculaGravidadeTeorica(double lat,double h) {
        double latR = Math.toRadians(lat);//////////

        t.setText("Correções Gravimétricas (mGal):");

        t.append("\n\n"); //adiciona duas quebras de linha
        t.append("Correção Atm. = " + String.valueOf(0.876-0.000099*h+0.00000000356*Math.pow(h,2)));
        t.append("\n\n");
        t.append("Correção Ar-Livre = " + String.valueOf((-1)*(0.3087691-0.0004398*(Math.pow(Math.sin(latR),2))*h+0.000000072125*Math.pow(h,2))));
        t.append("\n\n");
        double R = 1.0;
        double mi = 1.0;
        double lambda = 0.0;
        t.append("Correção C. de Bouguer = " + String.valueOf(2*Math.PI*0.000000000006673*2670*(mi*h - lambda*R)));
        t.append("\n\n");
        return GE*(1 + K *Math.pow(Math.sin(latR),2)*Math.pow(1-e2*Math.pow(Math.sin(latR),2),-0.5));
    }
}
