package course.labs.permissionslab

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PhoneStatusActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_status_activity)

        val getPhoneNumButton = findViewById<View>(R.id.get_phone_number_button) as Button
        // TODO - Add onClickListener to the getPhoneNumButton to call loadPhoneNumber()
        // First Check whether for [READ_PHONE_STATE, SEND_SMS, READ_PHONE_NUMBERS] has been provided if not request permission, else call
        getPhoneNumButton.setOnClickListener {
            Log.i(TAG, "Get Phone Number Clicked")

            // check for permission
            var phoneStatePerm = checkSelfPermission("READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED
            var sendSMSPerm = checkSelfPermission("SEND_SMS") == PackageManager.PERMISSION_GRANTED
            var readPhoneNumPerm = checkSelfPermission("READ_PHONE_NUMBERS") == PackageManager.PERMISSION_GRANTED
            var msgPermissions = phoneStatePerm.toString() + ' ' +
                    sendSMSPerm.toString() + ' ' +
                    readPhoneNumPerm.toString()
            Log.i(TAG, msgPermissions)
            Log.i(TAG, checkSelfPermission("READ_PHONE_STATE").toString())

            if (phoneStatePerm && sendSMSPerm && readPhoneNumPerm) {
                // if permission given, call below method, else request permission
                loadPhoneNumber()
            } else {
                requestPermissions(arrayOf("READ_PHONE_STATE", "SEND_SMS", "READ_PHONE_NUMBERS"),
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE)
            }
        }


        val goToDangerousActivityButton = findViewById<View>(R.id.go_to_dangerous_activity_button) as Button
        // TODO - Add onClickListener to the goToDangerousActivityButton to call startGoToDangerousActivity()
        goToDangerousActivityButton.setOnClickListener {
            Log.i(TAG, "Go To DangerousActivity Clicked")

            startGoToDangerousActivity()
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadPhoneNumber() {

        val tMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val mPhoneNumber = tMgr.line1Number

        val box = findViewById<View>(R.id.text) as TextView
        box.text = "Phone Number: $mPhoneNumber"

        Log.i(TAG, "Phone Number loaded")
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    loadPhoneNumber()

                } else {

                    Log.i(TAG, "Phone Number was not loaded --- Permission was not granted")

                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    private fun startGoToDangerousActivity() {

        Log.i(TAG, "Entered startGoToDangerousActivity()")

        // TODO - Start the GoToDangerousActivity
        val dangActivityIntent = Intent(this, GoToDangerousActivity::class.java)
        startActivity(dangActivityIntent)
    }

    companion object {

        private val TAG = "Lab-Permissions"
        val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1
    }

}
