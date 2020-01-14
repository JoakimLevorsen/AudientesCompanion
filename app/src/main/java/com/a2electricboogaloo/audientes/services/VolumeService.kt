package com.a2electricboogaloo.audientes.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.VolumeProviderCompat

class VolumeService : Service() {
    lateinit var mediaSession : MediaSessionCompat


    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "VolumeService")
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS) //TODO OBS TIMMY: Possibly: needs another FLAG
        mediaSession.setPlaybackState(PlaybackStateCompat.Builder().setState(PlaybackStateCompat.STATE_PLAYING, 0,
            0F
        ).build())


        
        val audio = this!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager //TODO OBS TIMMY: Changed "context" to "this" - no idea if thats okay...
        val maxVol = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC) //Gets bluetooth device max vol
        val currentVol = audio.getStreamVolume(AudioManager.STREAM_MUSIC) //Bluetooth device!
        
        val myVolumeProvider : VolumeProviderCompat = object : VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE,
            maxVol, currentVol) {
            override fun onAdjustVolume(direction : Int) {
            //TODO IMPORTANT STUFF
            }
        }

        mediaSession.setPlaybackToRemote(myVolumeProvider)
        mediaSession.isActive = true

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}