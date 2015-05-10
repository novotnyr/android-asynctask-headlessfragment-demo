package sk.upjs.ics.android.counter;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CountdownFragment extends Fragment {
    private CountdownAsyncTask countdownAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startCounting(int startNumber) {
        if(countdownAsyncTask != null && countdownAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            // task is already running
            return;
        }
        countdownAsyncTask = new CountdownAsyncTask();
        countdownAsyncTask.execute(startNumber);
    }

    private class CountdownAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int countStart = 0;
            if(params.length > 0) {
                countStart = params[params.length - 1];
            }
            for(int i = countStart; i >= 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Activity activity = getActivity();
            if(activity == null) {
                return;
            }
            if(!(activity instanceof OnCountdownListener)) {
                return;
            }
            OnCountdownListener onCountdownListener = (OnCountdownListener) activity;
            int lastProgressValue = values[values.length - 1];
            onCountdownListener.onCountdown(lastProgressValue);
        }
    }


    public interface OnCountdownListener {
        public void onCountdown(int currentNumber);
    }
}
