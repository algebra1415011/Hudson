package buybooks.com.hudson

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.volley.ServiceVolley
import org.json.JSONObject

class TopideaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topidea)
        var tableID:String =intent.getStringExtra("tableID")
        val service = ServiceVolley()
        val apiController = APIController(service)
        val path3 = "table/$tableID"
//        val path = "comments/pfa12"
        val params3 = JSONObject()

        apiController.getJsonObject(path3, params3) { response ->

            if (response != null) {
                Log.d("topidea response", "/post request OK! Response: $response"+response.getString("result"))
            }
        }
    }
}
