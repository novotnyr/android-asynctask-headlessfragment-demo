package sk.upjs.ics.android.counter;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements CountdownFragment.OnCountdownListener{

    public static final String COUNTDOWN_FRAGMENT_TAG = "countdownFragment";
    private TextView countdownTextView;

    private CountdownFragment countdownFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.countdownTextView = (TextView) findViewById(R.id.countdownTextView);

        FragmentManager fragmentManager = getFragmentManager();
        countdownFragment = (CountdownFragment) fragmentManager.findFragmentByTag(COUNTDOWN_FRAGMENT_TAG);
        if(countdownFragment == null) {
            countdownFragment = new CountdownFragment();
            fragmentManager.beginTransaction()
                    .add(countdownFragment, COUNTDOWN_FRAGMENT_TAG)
                    .commit();
        }
    }

    public void startCountdown(View view) {
        countdownFragment.startCounting(50);
    }

    @Override
    public void onCountdown(int currentNumber) {
        countdownTextView.setText(Integer.toString(currentNumber));
    }
}
