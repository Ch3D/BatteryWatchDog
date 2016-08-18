package com.ch3d.ifttt.batterywatchdog

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (getCustomDeviceName() == null) {
            saveCustomDeviceName(PrefrencesProvider.getDefaultDeviceName())
        }

        edit_key.setText(getIftttKey())
        edit_custom_device_name.setText(getCustomDeviceName())
        edit_custom_device_name.isEnabled = isCustomNameEnabled()
        checkbox_custom_device_name.isChecked = isCustomNameEnabled()

        checkbox_custom_device_name.setOnCheckedChangeListener { compoundButton, checked ->
            edit_custom_device_name.isEnabled = checked
            setCustomNameEnabled(checked)
        }

        btn_save.setOnClickListener {
            saveIftttKey(edit_key.text.toString())

            val enabledCustomName = checkbox_custom_device_name.isEnabled
            setCustomNameEnabled(enabledCustomName)
            if (enabledCustomName) {
                saveCustomDeviceName(edit_custom_device_name.text.toString())
            }

            Toast.makeText(this@MainActivity, R.string.main_hint_ifttt_key, LENGTH_SHORT).show()
        }

        startService(Intent(this, BatteryService::class.java))
    }
}