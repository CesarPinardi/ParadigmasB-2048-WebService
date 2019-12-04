
/**AQUI PROVAVELMENTE VAMOS IGNORAR ESSE PKG, SO COMENTAR CASO DE ERRO
 * SE NAO, COLOCAR O PKG QUE TIVER NA TUA MAQUINA
 */
package com.example.a2048_botoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;

public class mainAction extends AppCompatActivity {

    //herdam de view por isso eh possivel o casting
    private Button buttonDir;
    private Button buttonEsq;
    private Button buttonCima;
    private Button buttonBaixo;
    private CharSequence ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** AQUI COLOCAR O NOME DO NOSSO LAYOUT */
        setContentView(R.layout.activity_main);

        
        /**AQUI COLOCAR AS COISAS QUE TEM NO NOSSO LAYOUT
         * (4 BOTOES DIRECIONAIS)
        */
       buttonDir = (Button) findViewById(R.id.button);
        buttonEsq = (Button) findViewById(R.id.button2);
        buttonCima = (Button) findViewById(R.id.button3);
        buttonBaixo = (Button) findViewById(R.id.button4);


        /*buttonConfirm = (Button) findViewById(R.id.button6); //botao de confirmar o que foi escrito no text
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);*/

    }

    public void verificarMovimento(String movimento){
        HttpReturn mov = new HttpReturn(movimento);
        try {
            mov.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* AQUI VAI COLOCAR OS NOSSOS BOTOES NO LUGAR DO DELE*/ 


    /**PROVAVELMENTE NAO VAMOS USAR ESSE AQUI, IGNORA E COMENTA POR ENQUANTO
    public void retornoCAM(View v){
        super.onBackPressed();
    }*/

    //AQUI COLOCAR O BOTAO PRA CIMA E ASSIM EM DIANTE (ESQ/DIR/BAIXO)

    public void upmov(View v){
        
        verificarMovimento("cima");
    }

    //funçao mover para esquerda
    public void leftmov(View v){

        verificarMovimento("esquerda");
    }

    //funçao mover para direita
    public void rightmov(View v){
        verificarMovimento("direita");
    }

    //funçao mover para baixo
    public void downmov(View v){
        verificarMovimento("baixo");
    }

    /*public void confirmIP(View v){
        textView.setText(editText.getText());
        ip = textView.getText();
        System.out.println("IP confirmado: "+ip);
    }*/

}