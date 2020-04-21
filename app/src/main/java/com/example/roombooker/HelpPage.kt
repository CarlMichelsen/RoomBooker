package com.example.roombooker

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class HelpPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_page)

        var bobCake: Switch = findViewById<Switch>(R.id.bobCake);
        var bobCakeText: TextView = findViewById<TextView>(R.id.bobCakeTextView);


        var benCake: Switch = findViewById<Switch>(R.id.benCake);
        var benCakeText: TextView = findViewById<TextView>(R.id.benCakeTextView);

        bobCake.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) bobCakeText.text = "Bob likes cake"
            else bobCakeText.text = "Bob does NOT like cake"
        })


        benCake.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) benCakeText.text = "Ben likes cake"
            else benCakeText.text = "Ben does NOT like cake"
        })
    }

    fun confirmButtonMethodKotlin(view: View) {
        var textField: EditText = findViewById<EditText>(R.id.textInputField)
        var textOutput: TextView = findViewById<TextView>(R.id.textViewOutput)

        textOutput.text = textField.text
    }

    fun backButtonMethod(view: View) {
        finish()
    }


}
