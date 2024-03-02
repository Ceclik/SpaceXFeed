package com.yyi.spacexfeed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.databinding.ActivityEditBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    private val imageIds = listOf(R.drawable.landing_icon, R.drawable.launch_icon)
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding){
        spaceEventIcon.setImageResource(imageIds[index])
        nextPictureButton.setOnClickListener {
            if(index == imageIds.size - 1) index = 0
            else index++
            spaceEventIcon.setImageResource(imageIds[index])
        }

        confirmButton.setOnClickListener {
            onConfirmButtonClick()
        }

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        toolbar.setNavigationOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun onConfirmButtonClick(){
        binding.apply {
            val spaceEvent = SpaceEvent(
                titleTextField.text.toString(), descriptionTextField.text.toString(),
                getCurrentDate(), imageIds[index])
            val editIntent = Intent().apply {
                putExtra("spaceEvent", spaceEvent)
            }
            setResult(RESULT_OK, editIntent)
            finish()
        }
    }

    private fun getCurrentDate(): String{
        val currentDate = Date()

        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)

        return dateText
    }
}