package com.c1ctech.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button mScheduleJobButton;
    private Button mCancelJobButton;
    static final int JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mScheduleJobButton = (Button) findViewById(R.id.schedule_job);
        mCancelJobButton = (Button) findViewById(R.id.cancel_all);


        final JobInfo jobInfo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(this, JobSchedulerService.class))
                    .setPeriodic(15 * 60 * 1000, 7 * 60 * 1000)
                    .build();

        } else {
            jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(this, JobSchedulerService.class))
                    .setPeriodic(3000)
                    .build();
        }


        final JobScheduler jobScheduler =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        mScheduleJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobScheduler.schedule(jobInfo);

            }
        });

        mCancelJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobScheduler.cancel(JOB_ID);
                Toast.makeText(getApplicationContext(), " Job Cancelled", Toast.LENGTH_LONG).show();
            }
        });


    }
}
