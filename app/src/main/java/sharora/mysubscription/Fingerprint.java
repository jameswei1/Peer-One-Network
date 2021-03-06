package sharora.mysubscription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.os.Build;
import androidx.biometric.BiometricPrompt;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.time.LocalDate;
import java.time.Period;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fingerprint extends AppCompatActivity {
    public static final int ITEM_DELAY = 300;
    private boolean animationStarted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        final Executor executor = Executors.newSingleThreadExecutor();
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

                if (executor == null) {
                    throw new IllegalArgumentException("Executor must not be null");
                }
                else if (!isNetworkConnected()) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(activity, "Check internet connection", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    gotomain();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //TODO: Called when a biometric is valid but not recognized.
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Welcome to My Subcription")
                .setSubtitle("Developed by Sharan Rohit Raj & James Wei")
                .setNegativeButtonText("Cancel")
                .build();

        findViewById(R.id.authenticatebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }
    private void animate() {
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }


    public void gotomain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
