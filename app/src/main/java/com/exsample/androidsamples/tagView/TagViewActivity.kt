package com.exsample.androidsamples.tagView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import kotlinx.android.synthetic.main.tag_view_activity.*

class TagViewActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_view_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        initTagView()
    }

    private fun initClick() {
        addShortTagButton.setOnClickListener {
            tagView.addTag(getTag(TagType.SHORT))
        }
        addLongButton.setOnClickListener {
            tagView.addTag(getTag(TagType.LONG))
        }
        addSuperLongTagButton.setOnClickListener {
            tagView.addTag(getTag(TagType.SUPER_LONG))
        }
        clearTagButton.setOnClickListener {
            tagView.clear()
        }
    }

    private fun initTagView() {
        tagView.apply {
            setTags(makeTags())
            callback = object : TagView.OnClickListener {
                override fun onClick(position: Int, tag: String) {
                    Toast.makeText(this@TagViewActivity, tag, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getTag(type: TagType): String {
        val list = tags[type.index]
        return list[(Math.random() * 1000).toInt() % list.size]
    }

    private fun makeTags(): List<String> {
        val result = mutableListOf<String>()
        for (i in 0 until 5) {
            val list = tags[(Math.random() * 1000).toInt() % tags.size]
            result.add(list[(Math.random() * 1000).toInt() % list.size])
        }
        return result
    }

    enum class TagType(val index: Int) {
        SHORT(0),
        LONG(1),
        SUPER_LONG(2)
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, TagViewActivity::class.java))
        val tags = listOf(
            listOf("世界","聖","長曾我部","巽","司波","度会","漣","八神","碇","天馬","成瀬","暁","桐ケ谷","仁","武蔵","伴","阿久津","榊","御子柴","圷","空閑","黒瀬","沢渡","進","鉄","藤堂","東郷","高杉","流川","月城","冷泉","碇","水樹","一ノ瀬","舘","氷室","不破","月島","馬渕","亘","九重","綿谷","篁","桐生","一青","影山","二階堂","隼","皐月","鏡"),
            listOf("キクチ ナオユキ","フクオカ コウジ","コジマ ノゾミ","シミズ ヒロヤ","エノモト スミオ","ナカガワ アツシ","オオモリ フサミ","ミヤシタ ユキ","サイトウ コハル","ハシモト ユキロウ","ウツミ ヒデヒロ","サカキハラ キョウア","モテキ ヨシハル","フジハラ ナミ","アサダ コウジ","アライ グンイチ","センダ シゲヨシ","オガサハラ マサヒロ","ホリエ チョウイチロウ","ソノダ ミノブ","イシバシ エリコ","ヒグチ コウジ","マツバラ マサヒロ","ヒラマツ キクエ","イワサキ モヘイ","カン アツコ","カタギリ トシノリ","ナカハラ ミキオ","カワハラ トミオ","タキサワ リサ","ウエハラ アキヒサ","カン マキコ","ハラ ショウゾウ","マキノ コウスケ","マツオカ コウコ","カワノ ミツオ","ヒラマツ ユミ","オカベ セイコ","ホシノ ヤスヘイ","ツダ キクコ","ヒロセ レナ","イシハラ ナオヒロ","ウチヤマ トモコ","イシダ ヨシヒロ","ナガオ トシジ","タケシタ カツノブ","ソノダ イエツグ","ハラグチ フミユキ","ヒロタ ノリアキ","ノナカ ユウコウ"),
            listOf("福岡県嘉麻市鴨生5-3-9 パークハイツ鴨生","山形県新庄市栄町8-13-8-207","岐阜県安八郡輪之内町藻池新田3-2-9 ラ・カルム藻池新田 313","滋賀県東浅井郡虎姫町西大井6-4-4","大分県佐伯市日の出町5-14-2 エスポワール日の出町 111号室","秋田県横手市十文字町麻当2-13-8","石川県鳳珠郡能登町秋吉9-6-4","高知県四万十市西土佐中半5-2-8-1409","石川県輪島市気勝平町1-8-6","兵庫県豊岡市竹野町椒7-10-8","東京都東大和市仲原4105","長野県須坂市北横町6-12-7","東京都あきる野市渕上2-3-9","北海道雨竜郡北竜町美葉牛5-3-5","岐阜県本巣郡北方町芝原東町4-8-9","京都府京都市上京区花開院町3-15-7-8F","岐阜県関市洞戸片1-14-9-413号室","福井県大飯郡高浜町中津海2-2-6","東京都杉並区阿佐谷北7-5-3","滋賀県近江八幡市長光寺町8-12-1 セトル長光寺町","富山県富山市開4-11-6 東横イン・開 1006","埼玉県比企郡吉見町今泉4-4-1","新潟県上越市浦川原区六日町1-10-3 浦川原区六日町ハイツB 911号室","東京都江東区新砂9-4-9","岐阜県大垣市切石町3-7-8 レジデンス切石町 1514","茨城県石岡市弓弦9-12-5","福島県大沼郡会津美里町八木沢3828","岐阜県関市洞戸片6-2-4","愛知県愛知郡長久手町熊張6-5-8","東京都八王子市中山4489","福島県伊達市梁川町赤五輪7-14-6","東京都八丈島八丈町末吉8-15-8","愛知県犬山市常福寺洞7-8-7","福岡県飯塚市西徳前2-12-2","東京都足立区西綾瀬5906","大分県大分市鳴川1934","徳島県徳島市国府町府中5-8-8","新潟県北蒲原郡聖籠町二本松5-12-8 ＴＯＰ・二本松 405","兵庫県姫路市庄田9-10-9","佐賀県佐賀市富士町藤瀬1-13-1","愛知県瀬戸市祖母懐町4-1-3","新潟県新潟市親松1-8-5","岡山県岡山市富原8-1-3-413号室","北海道札幌市中央区宮の森二条5-7-4","北海道留萌郡小平町鬼鹿広富5-9-5","大分県大分市久原北5502","東京都八王子市上恩方町4-3-5-3F","北海道砂川市南吉野町3-4-7","福岡県柳川市材木町1-9-4","東京都国分寺市新町1-8-7 新町ハイツB 1212号室")
        )
    }
}