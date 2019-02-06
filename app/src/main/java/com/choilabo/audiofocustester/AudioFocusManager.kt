package com.choilabo.audiofocustester

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager

class AudioFocusManager(context: Context, private val listener: AudioManager.OnAudioFocusChangeListener) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as android.media.AudioManager

    private fun getAudioFocusRequest(focusGain: Int): AudioFocusRequest =
        AudioFocusRequest.Builder(focusGain)
            .setOnAudioFocusChangeListener(listener)
            .build()

    fun requestAudioFocus(focusGain: Int): Int {
        return audioManager.requestAudioFocus(getAudioFocusRequest(focusGain))
    }

    fun abandonAudioFocus(focusGain: Int): Int {
        return audioManager.abandonAudioFocusRequest(getAudioFocusRequest(focusGain))
    }
}