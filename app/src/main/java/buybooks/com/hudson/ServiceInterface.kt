package buybooks.com.hudson

import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

interface ServiceInterface {
    fun getJsonObject(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun getJsonArray(path: String, params: JSONArray, completionHandler: JSONArray?.() -> Unit)
    fun postJsonObject(path: String, params: JSONArray, completionHandler: JSONArray?.() -> Unit)
}