package amp.triples

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var forecastSpaceData: ForecastSpaceDataService
    private lateinit var forecastGrib: ForecastGribService
    private lateinit var getCtprvnMesureSidoLIst: GetCtprvnMesureSidoLIstService
    private var parsedData = arrayListOf<JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {

        //  splash image 시작
        startActivity<SplashActivity>()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = this

        //  시스템 언어 확인
        if (applicationContext.resources.configuration.locales.get(0).toString() != "ko_KR") {

           longToast(getString(R.string.error_systemLanguage))
           finish()

        }

        //  my data 초기화
        myDataInit()

        //  service 초기화
        serviceInit()

        //  network 확인
        if (networkCheck()) {

            Log.i("NET", "connected")
            networkOnInit()

        } else {

            Log.i("NET", "disconnected")
            networkOffInit()

        }

        //  GPS 권한 확인
        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting()

        } else {

            checkRunTimePermission()

        }

        //  ForecastSpaceData request 보내고 파싱
        val url = RestPullManager.url(0)
        val contents = RestPullManager.request(url)
        parsedData.add(ParseForecastData.parseForecastSpace(JSONObject(contents)))

        //  GetCtprvnMesureSidoLIst request 보내고 파싱
        val url2 = RestPullManager.url(1)
        val contents2 = RestPullManager.request(url2)
        parsedData.add(ParseForecastData.parseForecastSpace(JSONObject(contents2)))

        //  ForecastGrib request 보내고 파싱
        val url3 = RestPullManager.url(2)
        val contents3 = RestPullManager.request(url3)
        parsedData.add(ParseForecastData.parseForecastSpace(JSONObject(contents3)))

        MyData.parseData = parsedData

        // fragment 연결
        val adapter = SwipePagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)

    }

    private fun myDataInit() {

        val sharedPref = getSharedPreferences(getString(R.string.myData_filename), Context.MODE_PRIVATE)

        val gpsEnabled = sharedPref.getBoolean(getString(R.string.myData_key_gpsEnabled), true)
        val ox = sharedPref.getInt(getString(R.string.myData_key_ox), 60)
        val oy = sharedPref.getInt(getString(R.string.myData_key_oy), 127)
        val sido = sharedPref.getString(getString(R.string.myData_key_location), "서울")!!

        MyData.gpsEnabled = gpsEnabled
        MyData.grid = Grid(ox, oy)
        MyData.sido = sido

    }

    override fun onStop() {

        super.onStop()

        val sharedPref = getSharedPreferences(getString(R.string.myData_filename), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putBoolean(getString(R.string.myData_key_gpsEnabled), MyData.gpsEnabled!!)
        editor.putInt(getString(R.string.myData_key_ox), MyData.grid!!.ox)
        editor.putInt(getString(R.string.myData_key_oy), MyData.grid!!.oy)
        editor.putString(getString(R.string.myData_key_location), MyData.sido!!)

        editor.apply()

    }

    private fun serviceInit() {

        val service1 = Service(
            getString(R.string.service1_url),
            getString(R.string.service1_name),
            getString(R.string.key_ForecastSpaceData)
        )

        val service2 = Service(
            getString(R.string.service2_url),
            getString(R.string.service2_name),
            getString(R.string.key_getCtprvnMesureSidoLIst)
        )

        val service3 = Service(
            getString(R.string.service3_url),
            getString(R.string.service3_name),
            getString(R.string.key_ForecastSpaceData)
        )

        forecastSpaceData = ForecastSpaceDataService(service1)
        getCtprvnMesureSidoLIst = GetCtprvnMesureSidoLIstService(service2)
        forecastGrib = ForecastGribService(service3)

        RestPullManager.serviceAdd(forecastSpaceData)
        RestPullManager.serviceAdd(getCtprvnMesureSidoLIst)
        RestPullManager.serviceAdd(forecastGrib)

    }

    private fun networkCheck() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false

    private fun networkOnInit() {

        //  GPS 설정을 하지 않은 경우 myData 에 저장된 위치로 request 한다.
        if (!MyData.gpsEnabled!!) {

            forecastSpaceData.serviceParam = ForecastSpaceDataParam(
                grid = Grid(MyData.grid!!.ox, MyData.grid!!.oy)
            )

            getCtprvnMesureSidoLIst.serviceParam = GetCtprvnMesureSidoLIstParam(
                sidoName = MyData.sido!!
            )

            forecastGrib.serviceParam = ForecastGribParam(
                grid = Grid(MyData.grid!!.ox, MyData.grid!!.oy)
            )

        }

    }

    private fun networkOffInit() {

        longToast(getString(R.string.error_network))
        finish()

    }

//    ==================================================== GPS ================================================================

    var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String>,
        grandResults: IntArray
    ) {
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.size == REQUIRED_PERMISSIONS.size) { // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true
            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) { //위치 값을 가져올 수 있음
            } else { // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[0]
                    )
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[1]
                    )
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun checkRunTimePermission() { //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
        ) { // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
        } else { //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    REQUIRED_PERMISSIONS[0]
                )
            ) { // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this@MainActivity, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this@MainActivity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else { // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this@MainActivity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    fun getCurrentAddress(latitude: Double, longitude: Double): String { //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>
        addresses = try {
            geocoder.getFromLocation(
                latitude,
                longitude,
                7
            )
        } catch (ioException: IOException) { //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
        if (addresses == null || addresses.size == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"
        }
        val address = addresses[0]
        return address.getAddressLine(0).toString() + "\n"
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                    + "위치 설정을 수정하실래요?"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(
                callGPSSettingIntent,
                GPS_ENABLE_REQUEST_CODE
            )
        }
        builder.setNegativeButton(
            "취소"
        ) { dialog, id -> dialog.cancel() }
        builder.create().show()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->  //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
    }

    fun checkLocationServicesStatus(): Boolean {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    companion object {
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        private const val PERMISSIONS_REQUEST_CODE = 100

        lateinit var instance: MainActivity
            private set
    }

}