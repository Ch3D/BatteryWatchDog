package com.ch3d.ifttt.batterywatchdog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.ch3d.ifttt.batterywatchdog.model.ReportData
import com.ch3d.ifttt.batterywatchdog.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var initialData: ReportData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialData = ReportData(
                getIftttKey() ?: "",
                getCustomEventName() ?: "",
                getCustomDeviceName() ?: "",
                "")

        if (getCustomDeviceName() == null) {
            saveCustomDeviceName(PrefrencesProvider.getDefaultDeviceName())
        }
        if (getCustomEventName() == null) {
            saveCustomEventName(ReportData.EVENT_BATTERY_LOW)
        }

        switch_reporting.isChecked = isReportingEnabled()

        switch_reporting.setOnCheckedChangeListener { compoundButton, checked ->
            edit_key.isEnabled = checked
            edit_custom_device_name.isEnabled = checked && isCustomNameEnabled()
            edit_custom_event.isEnabled = checked && isCustomEventEnabled()
            btn_save.isEnabled = checked && hasChanges()
            checkbox_custom_device_name.isEnabled = checked
            checkbox_custom_event.isEnabled = checked

            setReportinEnabled(checked)
        }

        edit_key.setText(getIftttKey())
        edit_key.addTextChangedListener(getTextWatcher())

        with(edit_custom_device_name) {
            setText(getCustomDeviceName())
            addTextChangedListener(getTextWatcher())
            isEnabled = isCustomNameEnabled()
        }

        with(edit_custom_event) {
            setText(getCustomEventName())
            addTextChangedListener(getTextWatcher())
            isEnabled = isCustomEventEnabled()
        }

        with(checkbox_custom_device_name) {
            isChecked = isCustomNameEnabled()
            setOnCheckedChangeListener { compoundButton, checked ->
                edit_custom_device_name.isEnabled = checked
            }
        }

        with(checkbox_custom_event) {
            isChecked = isCustomEventEnabled()
            setOnCheckedChangeListener { compoundButton, checked ->
                edit_custom_event.isEnabled = checked
            }
        }

        btn_save.isEnabled = false
        btn_save.setOnClickListener {
            saveIftttKey(edit_key.text())

            val enabledCustomName = checkbox_custom_device_name.isChecked
            setCustomNameEnabled(enabledCustomName)
            if (enabledCustomName) {
                saveCustomDeviceName(edit_custom_device_name.text())
            }

            val enabledCustomEvent = checkbox_custom_event.isChecked
            setCustomEventEnabled(enabledCustomEvent)
            if (enabledCustomEvent) {
                saveCustomEventName(edit_custom_event.text())
            }

            Toast.makeText(this@MainActivity, R.string.main_toast_data_saved, LENGTH_SHORT).show()

            btn_save.isEnabled = false
            initialData = ReportData(
                    edit_key.text(),
                    edit_custom_event.text(),
                    edit_custom_device_name.text(),
                    "")
            hideKeyboard()
        }
    }

    private fun getTextWatcher(): TextWatcher {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkChanges()
            }
        }
        return textWatcher
    }

    fun checkChanges() {
        btn_save.isEnabled = hasChanges()
    }

    fun hasChanges(): Boolean {
        val updatedData = ReportData(
                edit_key.text(),
                edit_custom_event.text(),
                edit_custom_device_name.text(),
                "")
        return !initialData.equals(updatedData)
    }
}