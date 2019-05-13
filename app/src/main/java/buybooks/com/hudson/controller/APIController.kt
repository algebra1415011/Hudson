package buybooks.com.hudson.controller

import buybooks.com.hudson.ServiceInterface
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface{
    override fun getJsonArray(path: String, params: JSONArray, completionHandler: (response: JSONArray?) -> Unit) {
        service.getJsonArray(path, params, completionHandler)
    }




    private val service: ServiceInterface = serviceInjection

    override fun getJsonObject(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.getJsonObject(path, params, completionHandler)
    }
}