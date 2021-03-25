package fr.leo.calculimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import fr.leo.calculimc.R;

public class MainActivity extends AppCompatActivity {
    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    private final String txtInit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        envoyer = (Button)findViewById(R.id.button1);
        reset = (Button)findViewById(R.id.button2);
        taille = (EditText)findViewById(R.id.editTextNumber2);
        poids = (EditText)findViewById(R.id.editTextNumber1);
        commentaire = (CheckBox)findViewById(R.id.checkBox);
        group = (RadioGroup)findViewById(R.id.RadioGroup);
        result = (TextView)findViewById(R.id.textView);

        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);
    }

    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String t = taille.getText().toString();
            String p = poids.getText().toString();
            if (taille.getText().toString().isEmpty() && poids.getText().toString().isEmpty())
                result.setText(R.string.err_empty);
            else {
                float tValue = Float.valueOf(t);
                if (tValue <= 0)
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.err_taille), Toast.LENGTH_SHORT).show();
                else {
                    float pValue = Float.valueOf(p);
                    if (pValue <= 0)
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.err_poids), Toast.LENGTH_SHORT).show();
                    else {
                        if (group.getCheckedRadioButtonId() == R.id.radioButton2)
                            tValue = (float) (tValue * 0.01);
                        float imc = pValue / (tValue * tValue);
                        String resultat =  getResources().getString(R.string.result_imc) + " " +imc + ". ";
                        if (commentaire.isChecked())
                            resultat += interpreteIMC(imc);
                        result.setText(resultat);
                    }
                }
            }
            if (t.contains("."))
                group.check(R.id.radioButton1);
        }
        ;
    };

    private String interpreteIMC(float imc) {
        if(imc<16.5)
            return getResources().getString(R.string.imc_famine);
        if(imc>=16.5&&imc<18.5)
            return getResources().getString(R.string.imc_maigreur);
        if(imc>=18.5&&imc<25)
            return getResources().getString(R.string.imc_normal);
        if(imc>=25&&imc<30)
            return getResources().getString(R.string.imc_surpoids);
        if(imc>=30&&imc<35)
            return getResources().getString(R.string.imc_modere);
        if(imc>=35&&imc<40)
            return getResources().getString(R.string.imc_severe);
        if(imc>=40)
            return getResources().getString(R.string.imc_morbide);
        else return getResources().getString(R.string.err);
    }

    private View.OnClickListener resetListener = new View.OnClickListener() {

@Override
public void onClick(View v) {
        poids.getText().clear();
        taille.getText().clear();
        result.setText(txtInit);
        }
        };

private View.OnClickListener checkedListener = new View.OnClickListener() {

@Override
public void onClick(View v) {
        if(((CheckBox)v).isChecked()) {
        result.setText(txtInit);
        }
        }
        };

private TextWatcher textWatcher = new TextWatcher() {

@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
        result.setText(txtInit);
        }

@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

@Override
public void afterTextChanged(Editable s) {}
        };
}