package buybooks.com.hudson

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.volley.ServiceVolley
import com.google.zxing.Result
import kotlinx.android.synthetic.main.commentlayout.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject
import buybooks.com.hudson.model.User

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    private var dbHandler: DatabaseHelper? = null



    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {

        val service = ServiceVolley()
        val apiController = APIController(service)

        dbHandler = DatabaseHelper(this)
        var success: Boolean = false
        success = dbHandler!!.addUser(rawResult.text)
        val path1 = "user/${rawResult.text}"
        val params1 = JSONObject()
        if (success){
        apiController.getJsonObject(path1, params1) { response1 ->

                val path2 = "table/${response1?.getInt("tableID")}"
                val params2 = JSONObject()
                apiController.getJsonObject(path2, params2) { response2 ->
                    val path3 = "table/${response1?.getInt("tableID")}"
                    val params3 = JSONObject()
                    params3.put("tableID","${response2?.getInt("tableID")}")
                    params3.put("topicID","${response2?.getString("topicID")}")
                    params3.put("topUserID","${response2?.getString("topUserID")}")
                    params3.put("peopleCount","${response2!!.getInt("peopleCount")+1}")
                    params3.put("topUserName","${response2?.getString("topUserName")}")
                    params3.put("topUserIdea","${response2?.getString("topUserIdea")}")
                    apiController.put(path3,params3) {response3 ->
                        if (response3 != null) {
                            val i = Intent(this@ScanActivity,WelcomeActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    }

                }
        }
    }
    }
}