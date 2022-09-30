package com.example.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

 class MainActivity : AppCompatActivity() {
     private var b: Int = 0
     private var a: Int = 0
     private var num4: Int=0
     var count:Int =0
     var count2:Int =1
     private lateinit var num3:TextView

     private lateinit var num5:TextView
     private var SPEECH_REQUEST_CODE =102
     lateinit var tss:TextToSpeech
     lateinit var Edit:TextView
     private var string ="multiply"
     lateinit var n:TextView

     lateinit var n2:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        num5 =findViewById<TextView>(R.id.ans3)



        a = ran(1,10)
        b = ran(1,10)
         num4 =a*b


     n=findViewById<TextView>(R.id.Text)
     n2 =findViewById<TextView>(R.id.Text2)
         Edit =findViewById(R.id.ans)

        n.text =a.toString()
        n2.text = b.toString()

     call()


    }
     fun call(){
         speak()
         val hmm= object : Thread(){
             override fun run() {
                 Thread.sleep(8000)
                 displaySpeechRecognizer()
             }
         }
         hmm.start()



     }
    fun ran(a:Int, b:Int):Int{

       var num=  (a..b).shuffled().last()
        return num
    }
    fun speak(){
        tss = TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
            if(it ==TextToSpeech.SUCCESS){
                tss.language = Locale.US
                with(tss) {
                    setSpeechRate(1.0f)

                    speak(n.text.toString(),TextToSpeech.QUEUE_ADD,null)
                    speak(string.toString(),TextToSpeech.QUEUE_ADD,null)
                    speak(n2.text.toString(),TextToSpeech.QUEUE_ADD,null)
                }
            }

        })

    }
    private  fun displaySpeechRecognizer(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this,"speech recognition is not available",Toast.LENGTH_LONG).show()
        }
        else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
               intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                   RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Your answer")


           startActivityForResult(intent, SPEECH_REQUEST_CODE)}


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==SPEECH_REQUEST_CODE && resultCode ==Activity.RESULT_OK){
        val sp =data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)


                Edit.text = sp?.get(0).toString()
                check(sp?.get(0).toString())


        }


    }

     private fun check(value: String) {

         if(num4.toString() ==value)
         {
          num5.text ="Correct"
             tss = TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
                 if(it ==TextToSpeech.SUCCESS){
                     tss.language = Locale.US
                     with(tss) {
                         setSpeechRate(1.0f)
                         speak(num5.text.toString(),TextToSpeech.QUEUE_ADD,null)
                     }
                 }
             })



         }
         else{
             num5.text ="InCorrect"
             tss = TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
                 if(it ==TextToSpeech.SUCCESS){
                     tss.language = Locale.US
                     with(tss) {
                         setSpeechRate(1.0f)
                         speak(num5.text.toString(),TextToSpeech.QUEUE_ADD,null)
                     }
                 }
             })
             count++
             Toast.makeText(this,"the value of count is"+count,Toast.LENGTH_LONG).show()

             if(count<10){
                 var hm =object :Thread() {
                     override fun run() {
                         Thread.sleep(2000)
                         displaySpeechRecognizer()
                     }

                 }
                 hm.start()
                 }
             else{
                 tss = TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
                     if(it ==TextToSpeech.SUCCESS){
                         tss.language = Locale.US
                         with(tss) {
                             setSpeechRate(1.0f)
                             speak("Your cross the limit of 10 chances",TextToSpeech.QUEUE_ADD,null)
                         }
                     }
                 })
             }
             }


         }
     }


