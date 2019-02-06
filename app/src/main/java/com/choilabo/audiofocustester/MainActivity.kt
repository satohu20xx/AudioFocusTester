package com.choilabo.audiofocustester

import android.media.AudioManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val statusView by lazy { findViewById<TextView>(R.id.status) }
    private val focusGain by lazy { findViewById<RadioGroup>(R.id.focusGain) }
    private val requestButton by lazy { findViewById<Button>(R.id.request) }
    private val abandonButton by lazy { findViewById<Button>(R.id.abandon) }

    private val audioFocusListener = object : AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(focusChange: Int) {
            addLog("onAudioFocusChange = ${getFocusChangeName(focusChange)}")
        }
    }

    private lateinit var audioFocusManager: AudioFocusManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioFocusManager = AudioFocusManager(this, audioFocusListener)

        requestButton.setOnClickListener {
            getFocusGain().also {
                addLog(
                    "request ${getFocusGainName(it)} is ${getAudioFocusResultName(
                        audioFocusManager.requestAudioFocus(
                            it
                        )
                    )}"
                )
            }
        }
        abandonButton.setOnClickListener {
            getFocusGain().also {
                addLog(
                    "abandon ${getFocusGainName(it)} is ${getAudioFocusResultName(
                        audioFocusManager.abandonAudioFocus(
                            it
                        )
                    )}"
                )
            }
        }
    }

    private fun addLog(log: String) {
        statusView.text = "${log}\n${statusView.text}"
    }

    private fun getFocusGain(): Int {
        return when (focusGain.checkedRadioButtonId) {
            R.id.gain -> AudioManager.AUDIOFOCUS_GAIN
            R.id.gainTransient -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            R.id.gainTransientMayDuck -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            R.id.gainTransientExclusive -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE
            else -> AudioManager.AUDIOFOCUS_NONE
        }
    }

    private fun getFocusChangeName(focusChange: Int): String {
        return when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> "Gain"
            AudioManager.AUDIOFOCUS_LOSS -> "Loss"
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> "LossTransient"
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> "LossTransientCanDuck"
            else -> "unkown"
        }
    }

    private fun getFocusGainName(focusGain: Int): String {
        return when (focusGain) {
            AudioManager.AUDIOFOCUS_NONE -> "None"
            AudioManager.AUDIOFOCUS_GAIN -> "Gain"
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> "GainTransient"
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK -> "GainTransientMayDuck"
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE -> "GainExclusive"
            else -> "unknown"
        }
    }

    private fun getAudioFocusResultName(result: Int): String {
        return when (result) {
            AudioManager.AUDIOFOCUS_REQUEST_FAILED -> "Failed"
            AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> "Granted"
            AudioManager.AUDIOFOCUS_REQUEST_DELAYED -> "Delayed"
            else -> "unknown"
        }
    }
}
