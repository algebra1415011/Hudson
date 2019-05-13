package buybooks.com.hudson.volley

import android.util.Log
import buybooks.com.hudson.ServiceInterface
import buybooks.com.hudson.singleton.BackendVolley
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class ServiceVolley : ServiceInterface {


    override fun getJsonArray(path: String, params: JSONArray, completionHandler: JSONArray?.() -> Unit) {
        val jsonArrayRequest = object : JsonArrayRequest(Method.GET, basePath + path, params,
                Response.Listener<JSONArray> { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
//                headers.put("Content-Type", "appli cation/json")
                return headers
            }
        }



        BackendVolley.instance?.addToRequestQueue(jsonArrayRequest, TAG)
    }

    val TAG = ServiceVolley::class.java.simpleName
    val basePath = "https://hudson-server.herokuapp.com/hudson/"
//    https://hudson-server.herokuapp.com/hudson/comments/pfa12
//    https://hudson-server.herokuapp.com/hudson/comments/pfa12
//get request for testing
//    https://postman-echo.com/get?foo1=bar1&foo2=bar2

//    for post request
//    https://postman-echo.com/post

    override fun getJsonObject(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.GET, basePath + path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
//                headers.put("Content-Type", "application/json")
                return headers
            }
        }



        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }




}