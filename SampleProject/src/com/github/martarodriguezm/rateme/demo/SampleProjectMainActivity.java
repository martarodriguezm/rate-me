package com.github.martarodriguezm.rateme.demo;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.martarodriguezm.rateme.OnRatingListener;
import com.github.martarodriguezm.rateme.RateMeDialog;
import com.github.martarodriguezm.rateme.RateMeDialogTimer;



public class SampleProjectMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_project);
    }

    public void onPlainRateMeButtonClick(View view) {
        showPlainRateMeDialog();
    }

    public void onCustomRateMeButtonClick(View view) {
        showCustomRateMeDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rateme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.RateMe: {
            showPlainRateMeDialog();
            return true;
        }
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        final int launchTimes = 3;
        final int installDate = 7;

        RateMeDialogTimer.onStart(this);
        if (RateMeDialogTimer.shouldShowRateDialog(this, installDate, launchTimes)) {
            showPlainRateMeDialog();
        }

    }

    private void showPlainRateMeDialog() {
        new RateMeDialog.Builder(getPackageName(), getString(R.string.app_name))
                .build()
                .show(getFragmentManager(), "plain-dialog");
    }

    private void showCustomRateMeDialog() {
        new RateMeDialog.Builder(getPackageName(), getString(R.string.app_name))
                .setHeaderBackgroundColor(getResources().getColor(R.color.dialog_primary))
                .setBodyBackgroundColor(getResources().getColor(R.color.dialog_primary_light))
                .setBodyTextColor(getResources().getColor(R.color.dialog_text_foreground))
                .enableFeedbackByEmail("email@example.com")
                .showAppIcon(R.mipmap.ic_launcher)
                .setShowShareButton(true)
                .setRateButtonBackgroundColor(getResources().getColor(R.color.dialog_primary))
                .setRateButtonPressedBackgroundColor(getResources().getColor(R.color.dialog_primary_dark))
                .setOnRatingListener(new OnRatingListener() {
                    @Override
                    public void onRating(RatingAction action, float rating) {
                        Toast.makeText(SampleProjectMainActivity.this,
                                "Rate Me action: " + action + " (rating: " + rating + ")", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        // Nothing to write
                    }
                })
                .build()
                .show(getFragmentManager(), "custom-dialog");
    }
}
