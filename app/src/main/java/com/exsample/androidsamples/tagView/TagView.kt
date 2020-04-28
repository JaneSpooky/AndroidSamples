package com.exsample.androidsamples.tagView

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.setPadding
import androidx.core.view.size
import com.exsample.androidsamples.R
import com.exsample.androidsamples.SampleApplication.Companion.density
import timber.log.Timber

class TagView: LinearLayout {

    private var fontSize = 20F
    private var fontColor = -1
    private val horizontalLinearLayouts = mutableListOf<LinearLayout>()
    private val textViews = mutableListOf<TextView>()

    private var textViewBackgroundDrawable : Drawable? = null
    private var textViewBackgroundColor = -1

    private var tagMargin = 10
    private var tagPadding = 10

    private val paint by lazy { Paint().apply {
        textSize = fontSize
    } }

    private var currentSize = 0

    var callback : OnClickListener? = null

    constructor(ctx: Context) : super(ctx) {
        initialize(ctx)
    }
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs) {
        initialize(ctx, attrs)
    }
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        initialize(ctx, attrs, defStyleAttr)
    }

    private fun initialize(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
//        var typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.View, defStyleAttr, 0)
//        fontSize = typedArray.getDimension(R.styleable.TagView_textSize, 10F)
//        tagMargin = typedArray.getDimensionPixelSize(R.styleable.TagView_tagMargin, 0)
//        tagPadding = typedArray.getDimensionPixelSize(R.styleable.TagView_tagPadding, 0)
//        fontColor = typedArray.getColor(R.styleable.TagView_textColor, -1)
//        textViewBackgroundColor = typedArray.getColor(R.styleable.TagView_backgroundColor, -1)
//        textViewBackgroundDrawable = typedArray.getDrawable(R.styleable.TagView_backgroundDrawable)
//        typedArray.recycle()
        Timber.d("fontSize:$fontSize tagMargin:$tagMargin tagPadding:$tagPadding fontColor:$fontColor textViewBackgroundColor:$textViewBackgroundColor textViewBackgroundDrawable:$textViewBackgroundDrawable")
    }

    init {
        orientation = VERTICAL
    }

    fun clear() {
        horizontalLinearLayouts.forEach {
            it.removeAllViews()
        }
        removeAllViews()
        horizontalLinearLayouts.clear()
        textViews.clear()
        currentSize = 0
    }

    fun setTags(tags: List<String>) {
        clear()
        tags.forEach {
            addTag(it)
        }
    }

    fun addTag(tag: String) {
        if (tag.isEmpty())
            return
        if (hasToAddHorizontalLinearLayout(tag))
            addHorizontalLinearLayout()
        val textWidth = getTextWidth(tag) + tagPadding * 2
        val textView = makeTextView(tag, currentSize)
        horizontalLinearLayouts.last().addView(textView, LayoutParams(if (textWidth < width) LayoutParams.WRAP_CONTENT else LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 0, tagMargin, 0)
//            setPadding(tagPadding)
        })
        textViews.add(textView)
        currentSize++
    }

    private fun hasToAddHorizontalLinearLayout(tag: String): Boolean {
        if (horizontalLinearLayouts.size == 0)
            return true
        if (horizontalLinearLayouts.last().childCount == 0)
            return false
        val remainingWidth = width// - horizontalLinearLayouts.last().children.map { it.width }.sum() - horizontalLinearLayouts.last().size * tagMargin
        val textWidth = getTextWidth(tag) + tagMargin + tagPadding * 2
        Timber.d("width:$width remainingWidth:$remainingWidth textWidth:$textWidth")
        return textWidth > remainingWidth
    }

    private fun getTextWidth(text: String) : Int =
        (paint.measureText(text) * density).toInt()

    private fun addHorizontalLinearLayout() {
        val linearLayout = LinearLayout(context).apply {
            orientation = HORIZONTAL
        }
        addView(linearLayout, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        horizontalLinearLayouts.add(linearLayout)
    }

    private fun makeTextView(tag: String, index: Int): TextView =
        TextView(context).apply {
            text = tag
            textSize = fontSize
            if (fontColor != -1)
                setTextColor(fontColor)
            if (textViewBackgroundDrawable != null)
                background = textViewBackgroundDrawable
            else if (textViewBackgroundColor != -1)
                setBackgroundColor(textViewBackgroundColor)
            ellipsize = TextUtils.TruncateAt.END
            maxLines = 1
            setOnClickListener {
                callback?.onClick(index, tag)
            }
        }

    interface OnClickListener {
        fun onClick(position: Int, tag: String)
    }
}