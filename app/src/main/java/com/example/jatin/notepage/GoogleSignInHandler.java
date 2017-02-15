package com.example.jatin.notepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

/**
 * Created by jatin on 15/02/17.
 */

public class GoogleSignInHandler {

    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;
    private GoogleSignInResult googleSignInResult;
    private FragmentActivity activity;

    public GoogleSignInHandler(FragmentActivity activity){

        this.activity = activity;

        //configure sign in to request user's id, email and basic
        //profile. Id and basic profile is included in Default Sign IN
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //build a googleapiclient with access to the google sign in api
        googleApiClient= new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public Intent getSignInIntent(){
        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    public boolean isSilentLogin(){
        OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if (pendingResult != null){

            if (pendingResult.isDone()){
                //immediate result available
                googleSignInResult = pendingResult.get();
                if (googleSignInResult.getSignInAccount() != null) return true;
                else return false;
            } else {
                //no immediate result
                pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        GoogleSignInHandler.this.googleSignInResult =googleSignInResult;
                    }
                });
                if(googleSignInResult != null)
                    if (googleSignInResult.getSignInAccount() != null) return true;
                return false;
            }

        } else return false;//no result from silent login
    }

    public GoogleSignInResult getGoogleSignInResult(){
        return googleSignInResult;
    }

}
