package com.sena.adsologin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button signUp;
    TextView singIn;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        // Inicialización de los elementos de la UI
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signUp = findViewById(R.id.sing_up);
        singIn = findViewById(R.id.sing_in);

        // Acción para el botón "Iniciar sesión"
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Acción para el botón "Registrarse"
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());

                // Validación de los campos de correo y contraseña
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registro.this, "Ingrese su email", Toast.LENGTH_SHORT).show();
                    return; // Detener la ejecución si el campo de email está vacío
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registro.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
                    return; // Detener la ejecución si el campo de contraseña está vacío
                }

                if (password.length() < 6) {
                    Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    return; // Detener la ejecución si la contraseña es demasiado corta
                }

                // Intentar registrar el usuario en Firebase
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registro.this, "Su registro fue exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registro.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Obtener y mostrar el mensaje de error de Firebase
                                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registro fallido";
                                    Toast.makeText(Registro.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
