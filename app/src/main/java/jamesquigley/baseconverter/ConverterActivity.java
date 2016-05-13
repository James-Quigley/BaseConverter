package jamesquigley.baseconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConverterActivity extends AppCompatActivity {
    private EditText inputValue;
    private TextView binaryValue,decimalValue,hexValue;
    int radioValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        binaryValue = (TextView) findViewById(R.id.convertedBinary);
        decimalValue = (TextView) findViewById(R.id.convertedDecimal);
        hexValue = (TextView) findViewById(R.id.convertedHex);

        inputValue = (EditText) findViewById(R.id.valueInput);
        inputValue.addTextChangedListener(valueWatcher);
    }

    public void convert(){
        String val = inputValue.getText().toString();
        if(!val.equals("")) {
            switch (radioValue) {
                case 0:
                    if (!isValidBinary(val)) {
                        binaryError();
                        break;
                    }
                    try {
                        binaryValue.setText(val);
                        decimalValue.setText(binaryToDecimal(val));
                        hexValue.setText(binaryToHex(val));
                    }catch(Exception e){
                        overflow();
                    }
                    break;
                case 1:
                    if(!isValidDecimal(val)) {
                        decimalError();
                        break;
                    }
                    try {
                        binaryValue.setText(decimalToBinary(val));
                        decimalValue.setText(val);
                        hexValue.setText(decimalToHex(val));
                    }catch (Exception e){
                        overflow();
                    }
                    break;
                case 2:
                    if(!isValidHex(val)){
                        hexError();
                        break;
                    }
                    try {
                        binaryValue.setText(hexToBinary(val));
                        decimalValue.setText(hexToDecimal(val));
                        hexValue.setText(val);
                    }catch(Exception e){
                        overflow();
                    }
                    break;
            }
        }
        else{
            binaryValue.setText("0");
            decimalValue.setText("0");
            hexValue.setText("0");
        }
    }

    private void overflow(){
        binaryValue.setText("Overflow");
        decimalValue.setText("Overflow");
        hexValue.setText("Overflow");
    }
    private String binaryToDecimal(String binaryValue){
        return Integer.toString(Integer.parseInt(binaryValue,2));
    }

    private String binaryToHex(String binaryValue){
        int decimal = Integer.parseInt(binaryValue,2);
        return Integer.toString(decimal,16);
    }

    private void binaryError(){
        binaryValue.setText("Invalid Binary Value");
        decimalValue.setText("Invalid Binary Value");
        hexValue.setText("Invalid Binary Value");
    }

    private boolean isValidBinary(String binaryString){
        boolean var = true;
        for (int i = 0; i < binaryString.length(); i++){
            if (binaryString.charAt(i) != '0' && binaryString.charAt(i) != '1'){
                var = false;
                break;
            }
        }
        return var;
    }

    private boolean isValidDecimal(String decimalString){
        boolean var = true;
        for (int i = 0; i < decimalString.length(); i++){
            if (!Character.isDigit(decimalString.charAt(i))){
                var = false;
                break;
            }
        }
        return var;
    }

    private void decimalError(){
        binaryValue.setText("Invalid Decimal Value");
        decimalValue.setText("Invalid Decimal Value");
        hexValue.setText("Invalid Decimal Value");
    }

    private String decimalToBinary(String decimalString){
        return Integer.toBinaryString(Integer.parseInt(decimalString));
    }

    private String decimalToHex(String decimalString){
        return Integer.toHexString(Integer.parseInt(decimalString));
    }

    private boolean isValidHex(String hexString){
        boolean var = true;
        for (int i = 0; i < hexString.length(); i++){
            if (!Character.isDigit(hexString.charAt(i)) && !Character.isAlphabetic(hexString.charAt(i))){
                var = false;
                break;
            }
        }
        return var;
    }

    private void hexError(){
        binaryValue.setText("Invalid Hex Value");
        decimalValue.setText("Invalid Hex Value");
        hexValue.setText("Invalid Hex Value");
    }

    private String hexToDecimal(String hexString){
        return Integer.toString(Integer.parseInt(hexString,16));
    }

    private String hexToBinary(String hexString){
        return Integer.toString(Integer.parseInt(hexString, 16), 2);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioBinary:
                if (checked)
                    radioValue = 0;
                    break;
            case R.id.radioDecimal:
                if (checked)
                    radioValue = 1;
                    break;
            case R.id.radioHex:
                if(checked)
                    radioValue = 2;
                    break;
        }
        convert();
    }

    private final TextWatcher valueWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            convert();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
