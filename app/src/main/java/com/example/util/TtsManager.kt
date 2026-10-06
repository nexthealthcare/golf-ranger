package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TtsManager(context: Context) : TextToSpeech.OnInitListener {
  private var tts: TextToSpeech? = null
  private var isInitialized = false

  init {
    try {
      tts = TextToSpeech(context.applicationContext, this)
    } catch (e: Exception) {
      Log.e("TtsManager", "TTS init exception: ${e.message}")
    }
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = tts?.setLanguage(Locale.KOREAN)
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        tts?.setLanguage(Locale.US)
      }
      tts?.setSpeechRate(0.95f)
      tts?.setPitch(1.0f)
      isInitialized = true
    } else {
      Log.w("TtsManager", "TextToSpeech init failed with status: $status")
    }
  }

  fun speak(text: String) {
    if (isInitialized) {
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "GolfRangerVoiceId")
    }
  }

  fun stop() {
    try {
      tts?.stop()
    } catch (_: Exception) {}
  }

  fun shutdown() {
    try {
      tts?.stop()
      tts?.shutdown()
    } catch (_: Exception) {}
  }
}
