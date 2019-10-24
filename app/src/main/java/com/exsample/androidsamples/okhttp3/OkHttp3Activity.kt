package com.exsample.androidsamples.okhttp3

import QiitaResponse
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.okhttp3.model.DirectionsResponse
import com.exsample.androidsamples.recyclerView.RecyclerViewAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.ok_http_activity.*
import okhttp3.*
import java.io.IOException

class OkHttp3Activity: BaseActivity() {

    private val customAdapter by lazy { QiitaViewAdapter(this) }
    private var progressDialog: MaterialDialog? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ok_http_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
    }

    private fun initData() {
        showProgress()
        updateData()
        directions()
    }

    private fun directions() {
        val resultJson = "{\"geocoded_waypoints\":[{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJC3Cf2PuLGGAROO00ukl8JwA\",\"types\":[\"establishment\",\"point_of_interest\",\"subway_station\",\"train_station\",\"transit_station\"]},{\"geocoder_status\":\"OK\",\"place_id\":\"ChIJ35ov0dCOGGARKvdDH7NPHX0\",\"types\":[\"establishment\",\"point_of_interest\",\"tourist_attraction\"]}],\"routes\":[{\"bounds\":{\"northeast\":{\"lat\":35.71190929999999,\"lng\":139.8106634},\"southwest\":{\"lat\":35.6800946,\"lng\":139.7666604}},\"copyrights\":\"地図データ©2019Google\",\"legs\":[{\"distance\":{\"text\":\"7.8km\",\"value\":7767},\"duration\":{\"text\":\"18分\",\"value\":1094},\"end_address\":\"日本、〒131-0045東京都墨田区押上１丁目１−２\",\"end_location\":{\"lat\":35.70965169999999,\"lng\":139.8106634},\"start_address\":\"日本、東京都千代田区丸の内１丁目東京駅\",\"start_location\":{\"lat\":35.6829696,\"lng\":139.7666604},\"steps\":[{\"distance\":{\"text\":\"0.2km\",\"value\":173},\"duration\":{\"text\":\"1分\",\"value\":49},\"end_location\":{\"lat\":35.6843045,\"lng\":139.7675584},\"html_instructions\":\"\\u003cb\\u003e北東\\u003c/b\\u003e方向に\\u003cb\\u003e丸の内室町線\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e都道407号\\u003c/b\\u003eを進んで\\u003cb\\u003e永代通り\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e国道1号\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e国道20号\\u003c/b\\u003eに向かう\",\"polyline\":{\"points\":\"qixxEsdatYEIIUEKGGIIKGSKaA_@k@WsAi@\"},\"start_location\":{\"lat\":35.6829696,\"lng\":139.7666604},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"76m\",\"value\":76},\"duration\":{\"text\":\"1分\",\"value\":26},\"end_location\":{\"lat\":35.6842478,\"lng\":139.7683223},\"html_instructions\":\"\\u003cb\\u003e丸の内一丁目（交差点）\\u003c/b\\u003eを\\u003cb\\u003e右折\\u003c/b\\u003eして\\u003cb\\u003e丸の内室町線\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e永代通り\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e国道1号\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e国道20号\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e都道407号\\u003c/b\\u003eに入る\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"{qxxEgjatYQQBOL_AHu@\"},\"start_location\":{\"lat\":35.6843045,\"lng\":139.7675584},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.4km\",\"value\":400},\"duration\":{\"text\":\"1分\",\"value\":70},\"end_location\":{\"lat\":35.6875614,\"lng\":139.769927},\"html_instructions\":\"\\u003cb\\u003e左折\\u003c/b\\u003eして\\u003cb\\u003e丸の内室町線\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e江戸通り\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e都道407号\\u003c/b\\u003eに入る(\\u003cb\\u003e東京都道407号\\u003c/b\\u003eの表示)\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"qqxxE_oatYq@QwA]IA{@Ua@MICcBg@_@GSGOEA?_@Ka@MCAUOECa@]AASSGEa@[IGa@[\"},\"start_location\":{\"lat\":35.6842478,\"lng\":139.7683223},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.4km\",\"value\":350},\"duration\":{\"text\":\"1分\",\"value\":84},\"end_location\":{\"lat\":35.684813,\"lng\":139.7714249},\"html_instructions\":\"\\u003cb\\u003e新常盤橋（交差点）\\u003c/b\\u003eを\\u003cb\\u003e右折\\u003c/b\\u003eして\\u003cb\\u003e外堀通り\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e都道405号\\u003c/b\\u003eに入る(\\u003cb\\u003e鍛冶橋\\u003c/b\\u003eの表示)\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"gfyxEayatYIKHMZWRMt@a@nAs@NGb@Sb@SDAx@YrAi@FCj@Q\\\\KD?L?J@H?F@\"},\"start_location\":{\"lat\":35.6875614,\"lng\":139.769927},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.4km\",\"value\":391},\"duration\":{\"text\":\"1分\",\"value\":41},\"end_location\":{\"lat\":35.6841621,\"lng\":139.7755493},\"html_instructions\":\"\\u003cb\\u003e左折\\u003c/b\\u003eして\\u003cb\\u003e首都高速都心環状線\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003eC1\\u003c/b\\u003eに入る\\u003cdivstyle=\\\"font-size:0.9em\\\"\\u003e有料区間\\u003c/div\\u003e\",\"maneuver\":\"ramp-left\",\"polyline\":{\"points\":\"auxxEkbbtYROFG?ABEBEDI@E@EBOBSFk@D[Hm@BY?AFm@@CBc@BQBk@@E?M?EACDe@?EBSBWL{ABS@O?Q@i@@K@MAQ?GCWAKCWAQ\"},\"start_location\":{\"lat\":35.684813,\"lng\":139.7714249},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.6km\",\"value\":645},\"duration\":{\"text\":\"1分\",\"value\":42},\"end_location\":{\"lat\":35.6816035,\"lng\":139.78043},\"html_instructions\":\"\\u003cb\\u003e江戸橋JCT\\u003c/b\\u003eで\\u003cb\\u003e左\\u003c/b\\u003e車線を使用して\\u003cb\\u003e首都高速６号\\u003c/b\\u003e、\\u003cb\\u003e向島\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e首都高速７号\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e箱崎\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e首都高速９号\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e首都高速湾岸線\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e東北自動車道\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e常磐自動車道\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e京葉道路\\u003c/b\\u003e方面の標識に従う\\u003cdivstyle=\\\"font-size:0.9em\\\"\\u003e有料区間\\u003c/div\\u003e\\u003cdivstyle=\\\"font-size:0.9em\\\"\\u003e時間帯や曜日によっては通行止めになる場合があります\\u003c/div\\u003e\",\"maneuver\":\"ramp-left\",\"polyline\":{\"points\":\"_qxxEe|btYGKCKCO?AMsAGk@AKAGAG?GCMGk@?EIq@Ec@Em@?E?EAU?O?C?C@UBK?A@I?CBM@CBI?CN_@DMHOBGRc@Rc@P_@@EBER[PYRUFEJITMVKDATGJCxCk@TEVGVIHANGBA^QBARO\"},\"start_location\":{\"lat\":35.6841621,\"lng\":139.7755493},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"3.8km\",\"value\":3808},\"duration\":{\"text\":\"4分\",\"value\":225},\"end_location\":{\"lat\":35.7051475,\"lng\":139.7965523},\"html_instructions\":\"\\u003cb\\u003e向島線\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e首都高速６号\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003eルート6\\u003c/b\\u003eを進む\\u003cdivstyle=\\\"font-size:0.9em\\\"\\u003e有料区間\\u003c/div\\u003e\",\"polyline\":{\"points\":\"_axxEuzctYr@q@VWDEZ[r@{@h@m@V_@@ALQHMN[J[BIF[BO?CB[?IAO?QAGEU?AGYACISy@{BGOGQYy@Qg@Oc@GQKUOa@ACO_@Mg@?AQk@ACGS[aAK[AEEICKI[GQM]AAQg@CIWq@Oe@Um@M_@Si@Um@GQIQ?AMWOYGIEKCCCEKMMMKSe@y@AAQYw@iACCKOIMMQY_@Wa@W_@QWOSY_@QUGIW_@W[AACCEGGGEEKMQQIGGEMGGEMEMCEAAAMAIAIAI?U@M@I@GBKB[JQFKHEBIDYPUNSLOLIFGDEDWTQREFSVMPORW`@GHOTSXQVSXMNSTQPSRCBGFED]ZOLEDEBMN]XSPGFWPKJYNMFIBMDu@HWAQ?MCMGCAQIGCIEKGGGGGGGOSGCEACCKOGMMOKMQSKGGGOIGCEAMGMCOAQ?Q?_@@y@JYBG@E?c@Dc@DM@O@S?M@_@?c@?UAW?O?E?E?CA_@AOAMAKAICWCUEEC}@Si@OCAe@MOGMEQGGCOKQIMIWOQKMIMGAA?AAAA?A?IIKIEECEe@a@]_@a@a@IKg@g@k@i@a@]MKiA{@SOi@a@[W_@YUQUQYU[UQMMKCAWQQKYQe@UMGSKUKIE_@QQIOIq@]UISMUKIEMGSIUKUKSGYKWIUI[ISGMEc@Mc@MQEs@U[MaAa@s@[s@]OKSKWOECyBwAq@c@YO\"},\"start_location\":{\"lat\":35.6816035,\"lng\":139.78043},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.9km\",\"value\":875},\"duration\":{\"text\":\"4分\",\"value\":229},\"end_location\":{\"lat\":35.7109785,\"lng\":139.8020741},\"html_instructions\":\"\\u003cb\\u003e605-駒形\\u003c/b\\u003eを\\u003cb\\u003e浅草通り\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e東京スカイツリー\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e上野\\u003c/b\\u003e方面に向かって進む\\u003cdivstyle=\\\"font-size:0.9em\\\"\\u003e有料区間\\u003c/div\\u003e\",\"maneuver\":\"ramp-left\",\"polyline\":{\"points\":\"et|xEm_gtYC@A?C@AAE?OIGEu@_@a@S_@QIEWKaAc@KEw@_@a@Qo@[WMCAMKCCCEAA?CCEAGAI?EIGOGSMWOFOc@W]W}@q@m@c@}@q@ECw@m@AA}@o@OQMOa@c@EEMOm@i@MOMIeAgAg@g@EMCMEQAIAK?O?I?I@M@IBQHk@\"},\"start_location\":{\"lat\":35.7051475,\"lng\":139.7965523},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"65m\",\"value\":65},\"duration\":{\"text\":\"1分\",\"value\":16},\"end_location\":{\"lat\":35.7115394,\"lng\":139.8022711},\"html_instructions\":\"\\u003cb\\u003e墨田区役所前（交差点）\\u003c/b\\u003eを\\u003cb\\u003e左折\\u003c/b\\u003eして\\u003cb\\u003e墨堤通り\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e枕橋\\u003c/b\\u003e/\\u003cwbr/\\u003e\\u003cb\\u003e都道461号\\u003c/b\\u003eに入る\\u003cdivstyle=\\\"font-size:0.9em\\\"\\u003eそのまま墨堤通り/\\u003cwbr/\\u003e都道461号を進む\\u003c/div\\u003e\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"sx}xE}ahtYOEGAICm@OKEKCGA\"},\"start_location\":{\"lat\":35.7109785,\"lng\":139.8020741},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.2km\",\"value\":180},\"duration\":{\"text\":\"1分\",\"value\":32},\"end_location\":{\"lat\":35.7109879,\"lng\":139.8041434},\"html_instructions\":\"\\u003cb\\u003e右折\\u003c/b\\u003eする\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"c|}xEechtYFUPy@PaAZ_BF[Ha@ReA\"},\"start_location\":{\"lat\":35.7115394,\"lng\":139.8022711},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.1km\",\"value\":110},\"duration\":{\"text\":\"1分\",\"value\":26},\"end_location\":{\"lat\":35.71190929999999,\"lng\":139.8045869},\"html_instructions\":\"\\u003cb\\u003e左折\\u003c/b\\u003eして\\u003cb\\u003e小梅牛島通り\\u003c/b\\u003eに向かう\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"ux}xE{nhtYCAcAa@oBu@\"},\"start_location\":{\"lat\":35.7109879,\"lng\":139.8041434},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.4km\",\"value\":373},\"duration\":{\"text\":\"2分\",\"value\":144},\"end_location\":{\"lat\":35.7107858,\"lng\":139.8084677},\"html_instructions\":\"\\u003cb\\u003e右折\\u003c/b\\u003eして\\u003cb\\u003e小梅牛島通り\\u003c/b\\u003eに入る\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"m~}xEuqhtYTiAXmAZ{A^eBReAt@yDPy@VwA?[\"},\"start_location\":{\"lat\":35.71190929999999,\"lng\":139.8045869},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.1km\",\"value\":108},\"duration\":{\"text\":\"1分\",\"value\":39},\"end_location\":{\"lat\":35.70981190000001,\"lng\":139.8084809},\"html_instructions\":\"\\u003cb\\u003e押上二丁目（交差点）\\u003c/b\\u003eを\\u003cb\\u003e右折\\u003c/b\\u003eする\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"mw}xE}iitY`@?v@?Z?lAA\"},\"start_location\":{\"lat\":35.7107858,\"lng\":139.8084677},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.2km\",\"value\":213},\"duration\":{\"text\":\"1分\",\"value\":71},\"end_location\":{\"lat\":35.70965169999999,\"lng\":139.8106634},\"html_instructions\":\"\\u003cb\\u003eとうきょうスカイツリー駅（交差点）\\u003c/b\\u003eを\\u003cb\\u003e左折\\u003c/b\\u003eする\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"iq}xE_jitYV??S?I?G@ENeAD[@IBU?SKwBASGeB\"},\"start_location\":{\"lat\":35.70981190000001,\"lng\":139.8084809},\"travel_mode\":\"DRIVING\"}],\"traffic_speed_entry\":[],\"via_waypoint\":[]}],\"overview_polyline\":{\"points\":\"qixxEsdatY]s@UQuAk@_CaAQQBOVuBiCo@qBi@gD}@gA[_As@[Yk@c@k@g@d@e@hDkBfAg@~@[zAm@hA]h@@F@ROFIN[d@{DPgBDeABi@BYVwCDuAG}@Ei@KWYqC[uCK}AAm@Fm@H_@^aA|@oBj@aAZ[`@W~@YnDq@n@Q|@]bB{A~BkCp@aAZw@Je@Fy@Aa@G]Ss@yCkIQe@]gA[eAsAeEwBcGg@sAk@gAa@e@eAiBiD_FmBkCoAwAg@[c@Mk@Ec@BQDg@N]P_Aj@u@h@u@v@gCpDgApAe@d@aCvBc@\\\\g@VWHu@HWA_@CQIo@[OOW[MEOSs@_Ak@]SI]Ec@?yALkAJuAH{C?s@E{@KcCm@gA]{@a@eAo@_@W{@w@qBuBmAgAwAgAyBcB}C}B_B{@sAo@kDcB_Aa@{Ag@aBe@u@SoAc@uB}@cAi@kDwBkAs@E@E?UI_CkAoB{@aD{AYWGSAOYOk@]FOc@W{AiAkBuA}BcBeAkAiAiAsAqAg@g@EMI_@CU@q@D[Hk@OEQEm@OWIGAFUb@{Bb@{B\\\\gBgAc@oBu@TiAt@iDzB_LVwA?[xA?hBAV??S?QPkAFe@Bi@UqF\"},\"summary\":\"向島線/首都高速６号/ルート6\",\"warnings\":[],\"waypoint_order\":[]}],\"status\":\"OK\"}"
        val response = Gson().fromJson(resultJson, DirectionsResponse::class.java)
    }

    private fun updateData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://qiita.com/api/v2/items?page=1&per_page=20")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                handler.post {
                    hideProgress()
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter.refresh(listOf())
                }
            }
            override fun onResponse(call: Call, response: Response) {
                handler.post {
                    hideProgress()
                    swipeRefreshLayout.isRefreshing = false
                    response.body?.string()?.also {
                        val gson = Gson()
                        val type = object : TypeToken<List<QiitaResponse>>() {}.type
                        val list = gson.fromJson<List<QiitaResponse>>(it, type)

                        val qiita = gson.fromJson(StringBuilder().append("{\"items\":").append(it).append("}").toString(), Qiita::class.java)
                        customAdapter.refresh(qiita.items)
                    } ?: run {
                        customAdapter.refresh(listOf())
                    }
                }
            }
        })
    }

    private fun showProgress() {
        hideProgress()
        progressDialog = MaterialDialog(this).apply {
            cancelable(false)
            setContentView(LayoutInflater.from(this@OkHttp3Activity).inflate(R.layout.progress_dialog, null, false))
            show()
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, OkHttp3Activity::class.java))
    }
}