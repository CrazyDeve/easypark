package com.benben.carmanager.utils;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.benben.carmanager.R;

import java.util.HashMap;

public class LocalTrueFalseMediaPlayer {
    private HashMap<Integer, Integer> soundmap;//管理音频的字典
    private SoundPool soundPool;    //音频池
    private Context context;
    private float volue=0.1f;

    public LocalTrueFalseMediaPlayer(Context context){
        this.context=context;

    }

    //播放正确或错误音频
    public void play(final boolean trueOrfalse){
        //获取系统当前音量
        AudioManager mAudioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
        int current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        Log.i("hahaha",current+"");
        volue=(float) (current/30.0);//范围不一样，进行转换
        new Thread(new Runnable() {
            @Override
            public void run() {
                //回答错误，播放错误音频
                if (trueOrfalse) {//如果回答正确，播放正确音频
                  soundPool.play(soundmap.get(1),volue,volue,0,0,1);
                } else {
                    soundPool.play(soundmap.get(2),volue,volue,0,0,1);
                }
            }}).start();
    }
}

