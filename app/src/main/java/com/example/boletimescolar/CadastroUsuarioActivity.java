package com.example.boletimescolar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ImageButton imageButton_voltarMainActivity;
    private Button button_salvarCadastro;
    private EditText inputText_Email, inputText_Password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        inicializarFirebase();

        imageButton_voltarMainActivity = (ImageButton) findViewById(R.id.imageButton_voltarMainActivity);
        button_salvarCadastro = (Button) findViewById(R.id.button_salvarCadastro);
        button_salvarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputText_Email.getText().toString().trim();
                String password = inputText_Password.getText().toString().trim();
                criarUser(email, password);
            }
        });
        inputText_Email = (EditText) findViewById(R.id.inputText_email);
        inputText_Password = (EditText) findViewById(R.id.inputText_password);


    }

    private void criarUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            alert("Usuário Cadastrado com Sucesso");
                            Intent i = new Intent(CadastroUsuarioActivity.this, InicioActivity2.class);
                            finish();
                        } else {
                            alert("Erro de Cadastro");
                        }
                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(CadastroUsuarioActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastroUsuarioActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //Metodo para voltar para a tela inicial do app (não logado/tela de cadastro e login)
    public void voltarMainActivity(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}