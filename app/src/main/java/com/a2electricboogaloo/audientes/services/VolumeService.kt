package com.a2electricboogaloo.audientes.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.VolumeProviderCompat
import com.a2electricboogaloo.audientes.model.VolumeObservable

class VolumeService : Service() {
    lateinit var mediaSession: MediaSessionCompat


    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "VolumeService")
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder().setState(
                PlaybackStateCompat.STATE_PLAYING, 0,
                0F
            ).build()
        )

        val audio = this!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVol = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        var currentVol = audio.getStreamVolume(AudioManager.STREAM_MUSIC)

        //Note: VolumeProviderCompat inherently (apparently...) adjusts another audio stream (besides media, STREAM_MUSIC)

        val myVolumeProvider: VolumeProviderCompat = object : VolumeProviderCompat(
            VOLUME_CONTROL_RELATIVE,
            maxVol, currentVol
        ) {
            override fun onAdjustVolume(direction: Int) {
                VolumeObservable.getShared().didUpdate()
                if (direction > 0) {
                    //VOLUME IS ADJUSTED WITH +1
                    audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        0
                    )
                } else if (direction < 0) {
                    //VOLUME IS ADJUSTED WITH -1
                    audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        0
                    )
                }
                currentVol = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
                currentVolume = currentVol
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