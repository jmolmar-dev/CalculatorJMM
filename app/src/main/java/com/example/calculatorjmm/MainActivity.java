package com.example.calculatorjmm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    /*Variables Globales que usaremos posteriormente en la ejecucion del programa*/
    String currentInput ="";
    String operator = "";
    Integer firstNumber = 0;
    Integer secondNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i("Ciclo de Vida", "Ha entrado en el metodo onCreate");



        /*Obtenemos los componentes de la vista XML mediante findbyID*/
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btnDivision = findViewById(R.id.btnDivision);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnC = findViewById(R.id.btnC);
        Button btn0 = findViewById(R.id.btn0);
        Button btnEqual = findViewById(R.id.btnEqual);
        Button btnAdd = findViewById(R.id.btnAdd);

        /*Definiremos una instancia de OnClickListener, en la cual incluiremos un metodo onClick que se asignara posteriormente a todos los botones numericos*/
        View.OnClickListener numberListener = new View.OnClickListener(){
            public void onClick (View view){
                /*Castearemos la vista que entra como parametro de entrada a una instancia de Boton, para poder obtener el texto que tiene*/
                Button button = (Button) view;
                /*Dicho contenido se almacena en una variable global para ser tratado posteriormente y se establece en uno de los textView*/
                currentInput += button.getText().toString();
                tv2.setText(currentInput);
            }
        };

        /*Asignamos el OnClickListener de arriba a cada uno de nuestros botones numericos*/
        btn1.setOnClickListener(numberListener);
        btn2.setOnClickListener(numberListener);
        btn3.setOnClickListener(numberListener);
        btn4.setOnClickListener(numberListener);
        btn5.setOnClickListener(numberListener);
        btn6.setOnClickListener(numberListener);
        btn7.setOnClickListener(numberListener);
        btn8.setOnClickListener(numberListener);
        btn9.setOnClickListener(numberListener);
        btn0.setOnClickListener(numberListener);

        /*Crearemos otro OnClickListener, esta vez con otro medoto OnClick que se asignara en este caso a los botones que contienen los operadores
         * (+,-,* y %)*/
        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                /*Se evalua que exista algun numero introducido*/
                if (!currentInput.isEmpty()){
                    /*En caso de que ya haya sido introducido algo, lo parseamos a Integer y se almacenara en una variable, para poder ser tratado posteriormente*/
                    firstNumber = Integer.parseInt(currentInput);
                    /*Almacenaremos en operador en otra variable distinta con el mismo proposito que con el numero*/
                    operator = button.getText().toString();
                    /*Tras introducir el primer numero  pulsar uno de los operadores, limpiaremos la pantalla de cara a introducir el segundo numero*/
                    currentInput = "";
                    tv2.setText(currentInput);
                }
            }
        };

        /*El OnClickListener será asignado a los botones donde se encuentran los distintos operadores*/
        btnAdd.setOnClickListener(operationListener);
        btnSubtract.setOnClickListener(operationListener);
        btnMultiply.setOnClickListener(operationListener);
        btnDivision.setOnClickListener(operationListener);

        /*A continuación trataremos la funcionalidad del boton = */
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Se evalua que el TextView vuelva a tener algo (en este caso el segundo numero) y que la variable donde se almaceno el primero ya tenga algo*/
                if (!currentInput.isEmpty() && firstNumber != null){
                    /*En caso afirmativo, parsearemos el segundo numero para operar con ambos, y estableceremos dos variables distintas, un integer donde almacenar el resultado
                     * y un boolean que nos ayudara en la logica de nuestro programa*/
                    secondNumber = Integer.parseInt(currentInput);
                    Integer result = 0;
                    boolean validOperation = true;

                    switch (operator) {
                        case "+":
                            result = firstNumber + secondNumber;
                            break;
                        case "-":
                            result = firstNumber - secondNumber;
                            break;
                        case "×":
                            result = firstNumber * secondNumber;
                            break;
                        case "÷":
                            /*En caso de que se intente dividir por cero, se controla de esta forma*/
                            if (secondNumber != 0) {
                                result = firstNumber / secondNumber;
                            } else {
                                validOperation = false;
                                tv2.setText("Error");
                                currentInput = "";
                                operator = "";
                                firstNumber = null;
                                secondNumber = null;

                            }
                            break;
                        default:
                            validOperation = false;
                            break;
                    }

                    /*Si nuestra operacion es valida, se mostrará el resultado por pantalla a través del TextView,
                    y se reiniciarán las variables para las proximas operaciones*/
                    if (validOperation){
                        tv2.setText(result.toString());
                        currentInput = "";
                        operator = "";
                        firstNumber = null;
                        secondNumber = null;
                    }

                }
            }
        });

        /*Incluimos la funcionalidad en el boton de limpiar la pantalla*/
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*En caso de pulsar el boton C o de limpiar la pantalla, se resetearan todas las variables que se usan en las demas funcionalidad, y nos volvera a aparecer un
                 * 0 por pantalla*/
                currentInput = "";
                operator = "";
                firstNumber = null;
                secondNumber = null;
                tv2.setText("0");
            }

        });

    }
}