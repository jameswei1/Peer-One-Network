package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FIngerprint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
         Executor executor = Executors.newSingleThreadExecutor();
        final FragmentActivity activity = this;

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //TODO: Called when a biometric is recognized.
                gotomain();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //TODO: Called when a biometric is valid but not recognized.
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Welcome to My Subcription")
                .setSubtitle("Get ready to be amazed!")
                .setNegativeButtonText("Cancel")
                .build();

        findViewById(R.id.authenticateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }

    public void gotomain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
