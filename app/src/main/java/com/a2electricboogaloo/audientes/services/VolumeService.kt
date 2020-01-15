//TODO TIM: Clean up note
//package com.a2electricboogaloo.audientes.services
//
//import android.app.Service
//import android.content.Context
//import android.content.Intent
//import android.media.AudioManager
//import android.os.IBinder
//import android.support.v4.media.session.MediaSessionCompat
//import android.support.v4.media.session.PlaybackStateCompat
//import androidx.media.VolumeProviderCompat
//
//class VolumeService : Service() {
//    lateinit var mediaSession : MediaSessionCompat
//
//
//    override fun onCreate() {
//        super.onCreate()
//        mediaSession = MediaSessionCompat(this, "VolumeService")
//        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
//        mediaSession.setPlaybackState(PlaybackStateCompat.Builder().setState(PlaybackStateCompat.STATE_PLAYING, 0,
//            0F
//        ).build())
//
//        //TODO TIM: FIX VolumeService adjusts both media volume and "app/connected/external" volume...
//        //TODO TIM: Communicate to HomeFragemnt (Volume Slider) of changes
//
//        val audio = this!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        val maxVol = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC) //Gets bluetooth device max vol
//        var currentVol = audio.getStreamVolume(AudioManager.STREAM_MUSIC) //Bluetooth device!
//
//        val myVolumeProvider : VolumeProviderCompat = object : VolumeProviderCompat(VOLUME_CONTROL_RELATIVE,
//            maxVol, currentVol) {
//            override fun onAdjustVolume(direction : Int) {
//                if (direction > 0) {
//                    //VOLUME IS ADJUSTED WITH +1
//                    audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
//                } else if (direction < 0) {
//                    //VOLUME IS ADJUSTED WITH -1
//                    audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
//                }
//                currentVol = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
//                currentVolume = currentVol
//            }
//        }
//
//        mediaSession.setPlaybackToRemote(myVolumeProvider)
//        mediaSession.isActive = true
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mediaSession.release()
//    }
//
//    override fun onBind(p0: Intent?): IBinder? {
//        return null
//    }
//}