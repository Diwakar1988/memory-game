package com.github.diwakar1988.colormemory.ui;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * Created by diwakar.mishra on 12/10/16.
 */
public class GameBaseActivity extends AppCompatActivity {


    private ProgressDialog progress;

    protected void showProgress(String message) {
        if (progress == null) {
            progress = ProgressDialog.show(this, "",
                    message, true);
        } else if (progress.isShowing()) {
            progress.setMessage(message);
        } else {
            progress.setMessage(message);
            progress.show();
        }

    }

    protected void hideProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    private ActionBar actionBar;

    public void setUpTitleBar(String title, boolean backButton) {
        // Setup action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(backButton);
        if (!actionBar.isShowing()) {
            actionBar.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideActionBar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar.isShowing()) {
            getSupportActionBar().hide();
        }
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (!actionBar.isShowing()) {
            getSupportActionBar().show();
        }
    }

}
