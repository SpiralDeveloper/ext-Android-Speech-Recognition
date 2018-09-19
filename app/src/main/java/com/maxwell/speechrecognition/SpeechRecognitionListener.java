package com.maxwell.speechrecognition;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Maxwell on 14-Jan-18.
 */

final class SpeechRecognitionListener implements RecognitionListener {

    private OnSpeechRecognitionListener onSpeechRecognitionListener;
    private Context context;
    private RecognitionProgressView recognitionProgressView;

    SpeechRecognitionListener(OnSpeechRecognitionListener onSpeechRecognizerListener, Context context,RecognitionProgressView recognitionProgressView){
        this.onSpeechRecognitionListener = onSpeechRecognizerListener;
        this.context = context;
        this.recognitionProgressView=recognitionProgressView;
    }

    OnSpeechRecognitionListener getOnSpeechRecognitionListener(){
        return onSpeechRecognitionListener;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        if(recognitionProgressView!=null)
            recognitionProgressView.onReadyForSpeech(bundle);

    }

    @Override
    public void onBeginningOfSpeech() {
        if(recognitionProgressView!=null)
            recognitionProgressView.onBeginningOfSpeech();
    }

    @Override
    public void onRmsChanged(float v) {
        if(recognitionProgressView!=null)
            recognitionProgressView.onRmsChanged(v);
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        if(recognitionProgressView!=null)
            recognitionProgressView.onBufferReceived(bytes);
    }

    @Override
    public void onEndOfSpeech() {
        if(recognitionProgressView!=null)
            recognitionProgressView.onEndOfSpeech();
    }

    @Override
    public void onError(int i) {

        if(recognitionProgressView!=null)
            recognitionProgressView.onError(i);

        String errorMessage = "";
        int errorCode = -1;

        switch (i) {
            case SpeechRecognizer.ERROR_AUDIO:
                errorCode = SpeechRecognizer.ERROR_AUDIO;
                errorMessage = context.getString(R.string.error_audio);
                break;

            case SpeechRecognizer.ERROR_CLIENT:
                errorCode = SpeechRecognizer.ERROR_CLIENT;
                errorMessage = context.getString(R.string.error_client);
                break;

            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorCode = SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS;
                errorMessage = context.getString(R.string.error_permission);
                break;

            case SpeechRecognizer.ERROR_NETWORK:
                errorCode = SpeechRecognizer.ERROR_NETWORK;
                errorMessage = context.getString(R.string.error_network);
                break;

            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorCode = SpeechRecognizer.ERROR_NETWORK_TIMEOUT;
                errorMessage = context.getString(R.string.error_network);
                break;

            case SpeechRecognizer.ERROR_NO_MATCH:
                errorCode = SpeechRecognizer.ERROR_NO_MATCH;
                errorMessage = context.getString(R.string.error_no_match);
                break;

            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorCode = SpeechRecognizer.ERROR_RECOGNIZER_BUSY;
                errorMessage = context.getString(R.string.error_recognizer_busy);
                break;

            case SpeechRecognizer.ERROR_SERVER:
                errorCode = SpeechRecognizer.ERROR_SERVER;
                errorMessage = context.getString(R.string.error_server);
                break;

            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorCode = SpeechRecognizer.ERROR_SPEECH_TIMEOUT;
                errorMessage = context.getString(R.string.error_no_input);
                break;

            default:
                errorMessage = context.getString(R.string.error_undefined);
                break;
        }

        onSpeechRecognitionListener.OnSpeechRecognitionError(errorCode, errorMessage);
    }

    @Override
    public void onResults(Bundle bundle) {
        if(recognitionProgressView!=null)
            recognitionProgressView.onResults(bundle);
        //sentence with highest confidence score is in position 0
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if(matches != null && matches.size() > 0){
            String sentence = matches.get(0);

            Log.i(SpeechRecognitionListener.class.getSimpleName(), sentence);
            onSpeechRecognitionListener.OnSpeechRecognitionFinalResult(sentence);

        }else onError(SpeechRecognizer.ERROR_NO_MATCH);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        if(recognitionProgressView!=null)
            recognitionProgressView.onPartialResults(bundle);
        //sentence with highest confidence score is in position 0
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if(matches != null && matches.size() > 0){
            String word = matches.get(0);

            Log.i(SpeechRecognitionListener.class.getSimpleName(), word);
            onSpeechRecognitionListener.OnSpeechRecognitionCurrentResult(word);

        }else onError(SpeechRecognizer.ERROR_NO_MATCH);
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        if(recognitionProgressView!=null)
            recognitionProgressView.onEvent(i,bundle);
    }
}
